package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ReviewLines;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.Reviews;
import com.epmweb.server.repository.ReviewLinesRepository;
import com.epmweb.server.service.ReviewLinesService;
import com.epmweb.server.service.dto.ReviewLinesDTO;
import com.epmweb.server.service.mapper.ReviewLinesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ReviewLinesCriteria;
import com.epmweb.server.service.ReviewLinesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReviewLinesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ReviewLinesResourceIT {

    private static final Integer DEFAULT_PRODUCT_RATING = 1;
    private static final Integer UPDATED_PRODUCT_RATING = 2;
    private static final Integer SMALLER_PRODUCT_RATING = 1 - 1;

    private static final String DEFAULT_PRODUCT_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELLER_RATING = 1;
    private static final Integer UPDATED_SELLER_RATING = 2;
    private static final Integer SMALLER_SELLER_RATING = 1 - 1;

    private static final String DEFAULT_SELLER_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELIVERY_RATING = 1;
    private static final Integer UPDATED_DELIVERY_RATING = 2;
    private static final Integer SMALLER_DELIVERY_RATING = 1 - 1;

    private static final String DEFAULT_DELIVERY_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_REVIEW = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ReviewLinesRepository reviewLinesRepository;

    @Autowired
    private ReviewLinesMapper reviewLinesMapper;

    @Autowired
    private ReviewLinesService reviewLinesService;

    @Autowired
    private ReviewLinesQueryService reviewLinesQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReviewLinesMockMvc;

    private ReviewLines reviewLines;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewLinesResource reviewLinesResource = new ReviewLinesResource(reviewLinesService, reviewLinesQueryService);
        this.restReviewLinesMockMvc = MockMvcBuilders.standaloneSetup(reviewLinesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewLines createEntity(EntityManager em) {
        ReviewLines reviewLines = new ReviewLines()
            .productRating(DEFAULT_PRODUCT_RATING)
            .productReview(DEFAULT_PRODUCT_REVIEW)
            .sellerRating(DEFAULT_SELLER_RATING)
            .sellerReview(DEFAULT_SELLER_REVIEW)
            .deliveryRating(DEFAULT_DELIVERY_RATING)
            .deliveryReview(DEFAULT_DELIVERY_REVIEW)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return reviewLines;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReviewLines createUpdatedEntity(EntityManager em) {
        ReviewLines reviewLines = new ReviewLines()
            .productRating(UPDATED_PRODUCT_RATING)
            .productReview(UPDATED_PRODUCT_REVIEW)
            .sellerRating(UPDATED_SELLER_RATING)
            .sellerReview(UPDATED_SELLER_REVIEW)
            .deliveryRating(UPDATED_DELIVERY_RATING)
            .deliveryReview(UPDATED_DELIVERY_REVIEW)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return reviewLines;
    }

    @BeforeEach
    public void initTest() {
        reviewLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewLines() throws Exception {
        int databaseSizeBeforeCreate = reviewLinesRepository.findAll().size();

        // Create the ReviewLines
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(reviewLines);
        restReviewLinesMockMvc.perform(post("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewLines testReviewLines = reviewLinesList.get(reviewLinesList.size() - 1);
        assertThat(testReviewLines.getProductRating()).isEqualTo(DEFAULT_PRODUCT_RATING);
        assertThat(testReviewLines.getProductReview()).isEqualTo(DEFAULT_PRODUCT_REVIEW);
        assertThat(testReviewLines.getSellerRating()).isEqualTo(DEFAULT_SELLER_RATING);
        assertThat(testReviewLines.getSellerReview()).isEqualTo(DEFAULT_SELLER_REVIEW);
        assertThat(testReviewLines.getDeliveryRating()).isEqualTo(DEFAULT_DELIVERY_RATING);
        assertThat(testReviewLines.getDeliveryReview()).isEqualTo(DEFAULT_DELIVERY_REVIEW);
        assertThat(testReviewLines.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
        assertThat(testReviewLines.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testReviewLines.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createReviewLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewLinesRepository.findAll().size();

        // Create the ReviewLines with an existing ID
        reviewLines.setId(1L);
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(reviewLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewLinesMockMvc.perform(post("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList
        restReviewLinesMockMvc.perform(get("/api/review-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].productRating").value(hasItem(DEFAULT_PRODUCT_RATING)))
            .andExpect(jsonPath("$.[*].productReview").value(hasItem(DEFAULT_PRODUCT_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].sellerRating").value(hasItem(DEFAULT_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].sellerReview").value(hasItem(DEFAULT_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].deliveryRating").value(hasItem(DEFAULT_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].deliveryReview").value(hasItem(DEFAULT_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get the reviewLines
        restReviewLinesMockMvc.perform(get("/api/review-lines/{id}", reviewLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviewLines.getId().intValue()))
            .andExpect(jsonPath("$.productRating").value(DEFAULT_PRODUCT_RATING))
            .andExpect(jsonPath("$.productReview").value(DEFAULT_PRODUCT_REVIEW.toString()))
            .andExpect(jsonPath("$.sellerRating").value(DEFAULT_SELLER_RATING))
            .andExpect(jsonPath("$.sellerReview").value(DEFAULT_SELLER_REVIEW.toString()))
            .andExpect(jsonPath("$.deliveryRating").value(DEFAULT_DELIVERY_RATING))
            .andExpect(jsonPath("$.deliveryReview").value(DEFAULT_DELIVERY_REVIEW.toString()))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getReviewLinesByIdFiltering() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        Long id = reviewLines.getId();

        defaultReviewLinesShouldBeFound("id.equals=" + id);
        defaultReviewLinesShouldNotBeFound("id.notEquals=" + id);

        defaultReviewLinesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReviewLinesShouldNotBeFound("id.greaterThan=" + id);

        defaultReviewLinesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReviewLinesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating equals to DEFAULT_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.equals=" + DEFAULT_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating equals to UPDATED_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.equals=" + UPDATED_PRODUCT_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating not equals to DEFAULT_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.notEquals=" + DEFAULT_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating not equals to UPDATED_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.notEquals=" + UPDATED_PRODUCT_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsInShouldWork() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating in DEFAULT_PRODUCT_RATING or UPDATED_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.in=" + DEFAULT_PRODUCT_RATING + "," + UPDATED_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating equals to UPDATED_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.in=" + UPDATED_PRODUCT_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating is not null
        defaultReviewLinesShouldBeFound("productRating.specified=true");

        // Get all the reviewLinesList where productRating is null
        defaultReviewLinesShouldNotBeFound("productRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating is greater than or equal to DEFAULT_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.greaterThanOrEqual=" + DEFAULT_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating is greater than or equal to UPDATED_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.greaterThanOrEqual=" + UPDATED_PRODUCT_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating is less than or equal to DEFAULT_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.lessThanOrEqual=" + DEFAULT_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating is less than or equal to SMALLER_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.lessThanOrEqual=" + SMALLER_PRODUCT_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating is less than DEFAULT_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.lessThan=" + DEFAULT_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating is less than UPDATED_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.lessThan=" + UPDATED_PRODUCT_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByProductRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where productRating is greater than DEFAULT_PRODUCT_RATING
        defaultReviewLinesShouldNotBeFound("productRating.greaterThan=" + DEFAULT_PRODUCT_RATING);

        // Get all the reviewLinesList where productRating is greater than SMALLER_PRODUCT_RATING
        defaultReviewLinesShouldBeFound("productRating.greaterThan=" + SMALLER_PRODUCT_RATING);
    }


    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating equals to DEFAULT_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.equals=" + DEFAULT_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating equals to UPDATED_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.equals=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating not equals to DEFAULT_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.notEquals=" + DEFAULT_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating not equals to UPDATED_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.notEquals=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsInShouldWork() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating in DEFAULT_SELLER_RATING or UPDATED_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.in=" + DEFAULT_SELLER_RATING + "," + UPDATED_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating equals to UPDATED_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.in=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating is not null
        defaultReviewLinesShouldBeFound("sellerRating.specified=true");

        // Get all the reviewLinesList where sellerRating is null
        defaultReviewLinesShouldNotBeFound("sellerRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating is greater than or equal to DEFAULT_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.greaterThanOrEqual=" + DEFAULT_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating is greater than or equal to UPDATED_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.greaterThanOrEqual=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating is less than or equal to DEFAULT_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.lessThanOrEqual=" + DEFAULT_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating is less than or equal to SMALLER_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.lessThanOrEqual=" + SMALLER_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating is less than DEFAULT_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.lessThan=" + DEFAULT_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating is less than UPDATED_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.lessThan=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesBySellerRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where sellerRating is greater than DEFAULT_SELLER_RATING
        defaultReviewLinesShouldNotBeFound("sellerRating.greaterThan=" + DEFAULT_SELLER_RATING);

        // Get all the reviewLinesList where sellerRating is greater than SMALLER_SELLER_RATING
        defaultReviewLinesShouldBeFound("sellerRating.greaterThan=" + SMALLER_SELLER_RATING);
    }


    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating equals to DEFAULT_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.equals=" + DEFAULT_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating equals to UPDATED_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.equals=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating not equals to DEFAULT_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.notEquals=" + DEFAULT_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating not equals to UPDATED_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.notEquals=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsInShouldWork() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating in DEFAULT_DELIVERY_RATING or UPDATED_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.in=" + DEFAULT_DELIVERY_RATING + "," + UPDATED_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating equals to UPDATED_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.in=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating is not null
        defaultReviewLinesShouldBeFound("deliveryRating.specified=true");

        // Get all the reviewLinesList where deliveryRating is null
        defaultReviewLinesShouldNotBeFound("deliveryRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating is greater than or equal to DEFAULT_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.greaterThanOrEqual=" + DEFAULT_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating is greater than or equal to UPDATED_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.greaterThanOrEqual=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating is less than or equal to DEFAULT_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.lessThanOrEqual=" + DEFAULT_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating is less than or equal to SMALLER_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.lessThanOrEqual=" + SMALLER_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating is less than DEFAULT_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.lessThan=" + DEFAULT_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating is less than UPDATED_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.lessThan=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByDeliveryRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where deliveryRating is greater than DEFAULT_DELIVERY_RATING
        defaultReviewLinesShouldNotBeFound("deliveryRating.greaterThan=" + DEFAULT_DELIVERY_RATING);

        // Get all the reviewLinesList where deliveryRating is greater than SMALLER_DELIVERY_RATING
        defaultReviewLinesShouldBeFound("deliveryRating.greaterThan=" + SMALLER_DELIVERY_RATING);
    }


    @Test
    @Transactional
    public void getAllReviewLinesByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultReviewLinesShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the reviewLinesList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultReviewLinesShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultReviewLinesShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the reviewLinesList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultReviewLinesShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultReviewLinesShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the reviewLinesList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultReviewLinesShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where thumbnailUrl is not null
        defaultReviewLinesShouldBeFound("thumbnailUrl.specified=true");

        // Get all the reviewLinesList where thumbnailUrl is null
        defaultReviewLinesShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllReviewLinesByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultReviewLinesShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the reviewLinesList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultReviewLinesShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultReviewLinesShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the reviewLinesList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultReviewLinesShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultReviewLinesShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultReviewLinesShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultReviewLinesShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewLinesList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultReviewLinesShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultReviewLinesShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the reviewLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultReviewLinesShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedBy is not null
        defaultReviewLinesShouldBeFound("lastEditedBy.specified=true");

        // Get all the reviewLinesList where lastEditedBy is null
        defaultReviewLinesShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllReviewLinesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultReviewLinesShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewLinesList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultReviewLinesShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultReviewLinesShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewLinesList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultReviewLinesShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultReviewLinesShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultReviewLinesShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultReviewLinesShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewLinesList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultReviewLinesShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultReviewLinesShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the reviewLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultReviewLinesShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewLinesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        // Get all the reviewLinesList where lastEditedWhen is not null
        defaultReviewLinesShouldBeFound("lastEditedWhen.specified=true");

        // Get all the reviewLinesList where lastEditedWhen is null
        defaultReviewLinesShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewLinesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        reviewLines.setStockItem(stockItem);
        stockItem.setStockItemOnReviewLine(reviewLines);
        reviewLinesRepository.saveAndFlush(reviewLines);
        Long stockItemId = stockItem.getId();

        // Get all the reviewLinesList where stockItem equals to stockItemId
        defaultReviewLinesShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the reviewLinesList where stockItem equals to stockItemId + 1
        defaultReviewLinesShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllReviewLinesByReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);
        Reviews review = ReviewsResourceIT.createEntity(em);
        em.persist(review);
        em.flush();
        reviewLines.setReview(review);
        reviewLinesRepository.saveAndFlush(reviewLines);
        Long reviewId = review.getId();

        // Get all the reviewLinesList where review equals to reviewId
        defaultReviewLinesShouldBeFound("reviewId.equals=" + reviewId);

        // Get all the reviewLinesList where review equals to reviewId + 1
        defaultReviewLinesShouldNotBeFound("reviewId.equals=" + (reviewId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReviewLinesShouldBeFound(String filter) throws Exception {
        restReviewLinesMockMvc.perform(get("/api/review-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].productRating").value(hasItem(DEFAULT_PRODUCT_RATING)))
            .andExpect(jsonPath("$.[*].productReview").value(hasItem(DEFAULT_PRODUCT_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].sellerRating").value(hasItem(DEFAULT_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].sellerReview").value(hasItem(DEFAULT_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].deliveryRating").value(hasItem(DEFAULT_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].deliveryReview").value(hasItem(DEFAULT_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restReviewLinesMockMvc.perform(get("/api/review-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReviewLinesShouldNotBeFound(String filter) throws Exception {
        restReviewLinesMockMvc.perform(get("/api/review-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReviewLinesMockMvc.perform(get("/api/review-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReviewLines() throws Exception {
        // Get the reviewLines
        restReviewLinesMockMvc.perform(get("/api/review-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        int databaseSizeBeforeUpdate = reviewLinesRepository.findAll().size();

        // Update the reviewLines
        ReviewLines updatedReviewLines = reviewLinesRepository.findById(reviewLines.getId()).get();
        // Disconnect from session so that the updates on updatedReviewLines are not directly saved in db
        em.detach(updatedReviewLines);
        updatedReviewLines
            .productRating(UPDATED_PRODUCT_RATING)
            .productReview(UPDATED_PRODUCT_REVIEW)
            .sellerRating(UPDATED_SELLER_RATING)
            .sellerReview(UPDATED_SELLER_REVIEW)
            .deliveryRating(UPDATED_DELIVERY_RATING)
            .deliveryReview(UPDATED_DELIVERY_REVIEW)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(updatedReviewLines);

        restReviewLinesMockMvc.perform(put("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isOk());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeUpdate);
        ReviewLines testReviewLines = reviewLinesList.get(reviewLinesList.size() - 1);
        assertThat(testReviewLines.getProductRating()).isEqualTo(UPDATED_PRODUCT_RATING);
        assertThat(testReviewLines.getProductReview()).isEqualTo(UPDATED_PRODUCT_REVIEW);
        assertThat(testReviewLines.getSellerRating()).isEqualTo(UPDATED_SELLER_RATING);
        assertThat(testReviewLines.getSellerReview()).isEqualTo(UPDATED_SELLER_REVIEW);
        assertThat(testReviewLines.getDeliveryRating()).isEqualTo(UPDATED_DELIVERY_RATING);
        assertThat(testReviewLines.getDeliveryReview()).isEqualTo(UPDATED_DELIVERY_REVIEW);
        assertThat(testReviewLines.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
        assertThat(testReviewLines.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testReviewLines.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewLines() throws Exception {
        int databaseSizeBeforeUpdate = reviewLinesRepository.findAll().size();

        // Create the ReviewLines
        ReviewLinesDTO reviewLinesDTO = reviewLinesMapper.toDto(reviewLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewLinesMockMvc.perform(put("/api/review-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewLines in the database
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviewLines() throws Exception {
        // Initialize the database
        reviewLinesRepository.saveAndFlush(reviewLines);

        int databaseSizeBeforeDelete = reviewLinesRepository.findAll().size();

        // Delete the reviewLines
        restReviewLinesMockMvc.perform(delete("/api/review-lines/{id}", reviewLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReviewLines> reviewLinesList = reviewLinesRepository.findAll();
        assertThat(reviewLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
