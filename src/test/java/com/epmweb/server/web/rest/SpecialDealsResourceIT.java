package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.SpecialDeals;
import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.domain.Orders;
import com.epmweb.server.domain.BuyingGroups;
import com.epmweb.server.domain.CustomerCategories;
import com.epmweb.server.domain.Customers;
import com.epmweb.server.domain.ProductCategory;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.repository.SpecialDealsRepository;
import com.epmweb.server.service.SpecialDealsService;
import com.epmweb.server.service.dto.SpecialDealsDTO;
import com.epmweb.server.service.mapper.SpecialDealsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.SpecialDealsCriteria;
import com.epmweb.server.service.SpecialDealsQueryService;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SpecialDealsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class SpecialDealsResourceIT {

    private static final String DEFAULT_DEAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_DISCOUNT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISCOUNT_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DISCOUNT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISCOUNT_PERCENTAGE = new BigDecimal(1 - 1);

    private static final String DEFAULT_DISCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SpecialDealsRepository specialDealsRepository;

    @Autowired
    private SpecialDealsMapper specialDealsMapper;

    @Autowired
    private SpecialDealsService specialDealsService;

    @Autowired
    private SpecialDealsQueryService specialDealsQueryService;

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

    private MockMvc restSpecialDealsMockMvc;

    private SpecialDeals specialDeals;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialDealsResource specialDealsResource = new SpecialDealsResource(specialDealsService, specialDealsQueryService);
        this.restSpecialDealsMockMvc = MockMvcBuilders.standaloneSetup(specialDealsResource)
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
    public static SpecialDeals createEntity(EntityManager em) {
        SpecialDeals specialDeals = new SpecialDeals()
            .dealDescription(DEFAULT_DEAL_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .discountAmount(DEFAULT_DISCOUNT_AMOUNT)
            .discountPercentage(DEFAULT_DISCOUNT_PERCENTAGE)
            .discountCode(DEFAULT_DISCOUNT_CODE)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return specialDeals;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpecialDeals createUpdatedEntity(EntityManager em) {
        SpecialDeals specialDeals = new SpecialDeals()
            .dealDescription(UPDATED_DEAL_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .discountCode(UPDATED_DISCOUNT_CODE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return specialDeals;
    }

    @BeforeEach
    public void initTest() {
        specialDeals = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialDeals() throws Exception {
        int databaseSizeBeforeCreate = specialDealsRepository.findAll().size();

        // Create the SpecialDeals
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);
        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isCreated());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialDeals testSpecialDeals = specialDealsList.get(specialDealsList.size() - 1);
        assertThat(testSpecialDeals.getDealDescription()).isEqualTo(DEFAULT_DEAL_DESCRIPTION);
        assertThat(testSpecialDeals.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSpecialDeals.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSpecialDeals.getDiscountAmount()).isEqualTo(DEFAULT_DISCOUNT_AMOUNT);
        assertThat(testSpecialDeals.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testSpecialDeals.getDiscountCode()).isEqualTo(DEFAULT_DISCOUNT_CODE);
        assertThat(testSpecialDeals.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testSpecialDeals.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testSpecialDeals.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createSpecialDealsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialDealsRepository.findAll().size();

        // Create the SpecialDeals with an existing ID
        specialDeals.setId(1L);
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDealDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialDealsRepository.findAll().size();
        // set the field null
        specialDeals.setDealDescription(null);

        // Create the SpecialDeals, which fails.
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialDealsRepository.findAll().size();
        // set the field null
        specialDeals.setStartDate(null);

        // Create the SpecialDeals, which fails.
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialDealsRepository.findAll().size();
        // set the field null
        specialDeals.setEndDate(null);

        // Create the SpecialDeals, which fails.
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        restSpecialDealsMockMvc.perform(post("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList
        restSpecialDealsMockMvc.perform(get("/api/special-deals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialDeals.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealDescription").value(hasItem(DEFAULT_DEAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].discountCode").value(hasItem(DEFAULT_DISCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get the specialDeals
        restSpecialDealsMockMvc.perform(get("/api/special-deals/{id}", specialDeals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialDeals.getId().intValue()))
            .andExpect(jsonPath("$.dealDescription").value(DEFAULT_DEAL_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.discountAmount").value(DEFAULT_DISCOUNT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.discountCode").value(DEFAULT_DISCOUNT_CODE))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getSpecialDealsByIdFiltering() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        Long id = specialDeals.getId();

        defaultSpecialDealsShouldBeFound("id.equals=" + id);
        defaultSpecialDealsShouldNotBeFound("id.notEquals=" + id);

        defaultSpecialDealsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSpecialDealsShouldNotBeFound("id.greaterThan=" + id);

        defaultSpecialDealsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSpecialDealsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByDealDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where dealDescription equals to DEFAULT_DEAL_DESCRIPTION
        defaultSpecialDealsShouldBeFound("dealDescription.equals=" + DEFAULT_DEAL_DESCRIPTION);

        // Get all the specialDealsList where dealDescription equals to UPDATED_DEAL_DESCRIPTION
        defaultSpecialDealsShouldNotBeFound("dealDescription.equals=" + UPDATED_DEAL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDealDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where dealDescription not equals to DEFAULT_DEAL_DESCRIPTION
        defaultSpecialDealsShouldNotBeFound("dealDescription.notEquals=" + DEFAULT_DEAL_DESCRIPTION);

        // Get all the specialDealsList where dealDescription not equals to UPDATED_DEAL_DESCRIPTION
        defaultSpecialDealsShouldBeFound("dealDescription.notEquals=" + UPDATED_DEAL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDealDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where dealDescription in DEFAULT_DEAL_DESCRIPTION or UPDATED_DEAL_DESCRIPTION
        defaultSpecialDealsShouldBeFound("dealDescription.in=" + DEFAULT_DEAL_DESCRIPTION + "," + UPDATED_DEAL_DESCRIPTION);

        // Get all the specialDealsList where dealDescription equals to UPDATED_DEAL_DESCRIPTION
        defaultSpecialDealsShouldNotBeFound("dealDescription.in=" + UPDATED_DEAL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDealDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where dealDescription is not null
        defaultSpecialDealsShouldBeFound("dealDescription.specified=true");

        // Get all the specialDealsList where dealDescription is null
        defaultSpecialDealsShouldNotBeFound("dealDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllSpecialDealsByDealDescriptionContainsSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where dealDescription contains DEFAULT_DEAL_DESCRIPTION
        defaultSpecialDealsShouldBeFound("dealDescription.contains=" + DEFAULT_DEAL_DESCRIPTION);

        // Get all the specialDealsList where dealDescription contains UPDATED_DEAL_DESCRIPTION
        defaultSpecialDealsShouldNotBeFound("dealDescription.contains=" + UPDATED_DEAL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDealDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where dealDescription does not contain DEFAULT_DEAL_DESCRIPTION
        defaultSpecialDealsShouldNotBeFound("dealDescription.doesNotContain=" + DEFAULT_DEAL_DESCRIPTION);

        // Get all the specialDealsList where dealDescription does not contain UPDATED_DEAL_DESCRIPTION
        defaultSpecialDealsShouldBeFound("dealDescription.doesNotContain=" + UPDATED_DEAL_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where startDate equals to DEFAULT_START_DATE
        defaultSpecialDealsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the specialDealsList where startDate equals to UPDATED_START_DATE
        defaultSpecialDealsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where startDate not equals to DEFAULT_START_DATE
        defaultSpecialDealsShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the specialDealsList where startDate not equals to UPDATED_START_DATE
        defaultSpecialDealsShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultSpecialDealsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the specialDealsList where startDate equals to UPDATED_START_DATE
        defaultSpecialDealsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where startDate is not null
        defaultSpecialDealsShouldBeFound("startDate.specified=true");

        // Get all the specialDealsList where startDate is null
        defaultSpecialDealsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where endDate equals to DEFAULT_END_DATE
        defaultSpecialDealsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the specialDealsList where endDate equals to UPDATED_END_DATE
        defaultSpecialDealsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where endDate not equals to DEFAULT_END_DATE
        defaultSpecialDealsShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the specialDealsList where endDate not equals to UPDATED_END_DATE
        defaultSpecialDealsShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultSpecialDealsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the specialDealsList where endDate equals to UPDATED_END_DATE
        defaultSpecialDealsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where endDate is not null
        defaultSpecialDealsShouldBeFound("endDate.specified=true");

        // Get all the specialDealsList where endDate is null
        defaultSpecialDealsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount equals to DEFAULT_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.equals=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount equals to UPDATED_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.equals=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount not equals to DEFAULT_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.notEquals=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount not equals to UPDATED_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.notEquals=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount in DEFAULT_DISCOUNT_AMOUNT or UPDATED_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.in=" + DEFAULT_DISCOUNT_AMOUNT + "," + UPDATED_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount equals to UPDATED_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.in=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount is not null
        defaultSpecialDealsShouldBeFound("discountAmount.specified=true");

        // Get all the specialDealsList where discountAmount is null
        defaultSpecialDealsShouldNotBeFound("discountAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount is greater than or equal to DEFAULT_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.greaterThanOrEqual=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount is greater than or equal to UPDATED_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.greaterThanOrEqual=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount is less than or equal to DEFAULT_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.lessThanOrEqual=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount is less than or equal to SMALLER_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.lessThanOrEqual=" + SMALLER_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount is less than DEFAULT_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.lessThan=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount is less than UPDATED_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.lessThan=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountAmount is greater than DEFAULT_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldNotBeFound("discountAmount.greaterThan=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the specialDealsList where discountAmount is greater than SMALLER_DISCOUNT_AMOUNT
        defaultSpecialDealsShouldBeFound("discountAmount.greaterThan=" + SMALLER_DISCOUNT_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage equals to DEFAULT_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.equals=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage equals to UPDATED_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.equals=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage not equals to DEFAULT_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.notEquals=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage not equals to UPDATED_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.notEquals=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage in DEFAULT_DISCOUNT_PERCENTAGE or UPDATED_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.in=" + DEFAULT_DISCOUNT_PERCENTAGE + "," + UPDATED_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage equals to UPDATED_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.in=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage is not null
        defaultSpecialDealsShouldBeFound("discountPercentage.specified=true");

        // Get all the specialDealsList where discountPercentage is null
        defaultSpecialDealsShouldNotBeFound("discountPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage is greater than or equal to DEFAULT_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.greaterThanOrEqual=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage is greater than or equal to UPDATED_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.greaterThanOrEqual=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage is less than or equal to DEFAULT_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.lessThanOrEqual=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage is less than or equal to SMALLER_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.lessThanOrEqual=" + SMALLER_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage is less than DEFAULT_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.lessThan=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage is less than UPDATED_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.lessThan=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountPercentage is greater than DEFAULT_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldNotBeFound("discountPercentage.greaterThan=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the specialDealsList where discountPercentage is greater than SMALLER_DISCOUNT_PERCENTAGE
        defaultSpecialDealsShouldBeFound("discountPercentage.greaterThan=" + SMALLER_DISCOUNT_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountCode equals to DEFAULT_DISCOUNT_CODE
        defaultSpecialDealsShouldBeFound("discountCode.equals=" + DEFAULT_DISCOUNT_CODE);

        // Get all the specialDealsList where discountCode equals to UPDATED_DISCOUNT_CODE
        defaultSpecialDealsShouldNotBeFound("discountCode.equals=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountCode not equals to DEFAULT_DISCOUNT_CODE
        defaultSpecialDealsShouldNotBeFound("discountCode.notEquals=" + DEFAULT_DISCOUNT_CODE);

        // Get all the specialDealsList where discountCode not equals to UPDATED_DISCOUNT_CODE
        defaultSpecialDealsShouldBeFound("discountCode.notEquals=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountCode in DEFAULT_DISCOUNT_CODE or UPDATED_DISCOUNT_CODE
        defaultSpecialDealsShouldBeFound("discountCode.in=" + DEFAULT_DISCOUNT_CODE + "," + UPDATED_DISCOUNT_CODE);

        // Get all the specialDealsList where discountCode equals to UPDATED_DISCOUNT_CODE
        defaultSpecialDealsShouldNotBeFound("discountCode.in=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountCode is not null
        defaultSpecialDealsShouldBeFound("discountCode.specified=true");

        // Get all the specialDealsList where discountCode is null
        defaultSpecialDealsShouldNotBeFound("discountCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllSpecialDealsByDiscountCodeContainsSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountCode contains DEFAULT_DISCOUNT_CODE
        defaultSpecialDealsShouldBeFound("discountCode.contains=" + DEFAULT_DISCOUNT_CODE);

        // Get all the specialDealsList where discountCode contains UPDATED_DISCOUNT_CODE
        defaultSpecialDealsShouldNotBeFound("discountCode.contains=" + UPDATED_DISCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByDiscountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where discountCode does not contain DEFAULT_DISCOUNT_CODE
        defaultSpecialDealsShouldNotBeFound("discountCode.doesNotContain=" + DEFAULT_DISCOUNT_CODE);

        // Get all the specialDealsList where discountCode does not contain UPDATED_DISCOUNT_CODE
        defaultSpecialDealsShouldBeFound("discountCode.doesNotContain=" + UPDATED_DISCOUNT_CODE);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice is not null
        defaultSpecialDealsShouldBeFound("unitPrice.specified=true");

        // Get all the specialDealsList where unitPrice is null
        defaultSpecialDealsShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultSpecialDealsShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the specialDealsList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultSpecialDealsShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultSpecialDealsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the specialDealsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSpecialDealsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultSpecialDealsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the specialDealsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultSpecialDealsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultSpecialDealsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the specialDealsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSpecialDealsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedBy is not null
        defaultSpecialDealsShouldBeFound("lastEditedBy.specified=true");

        // Get all the specialDealsList where lastEditedBy is null
        defaultSpecialDealsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultSpecialDealsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the specialDealsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultSpecialDealsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultSpecialDealsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the specialDealsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultSpecialDealsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultSpecialDealsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the specialDealsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSpecialDealsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultSpecialDealsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the specialDealsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultSpecialDealsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultSpecialDealsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the specialDealsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSpecialDealsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        // Get all the specialDealsList where lastEditedWhen is not null
        defaultSpecialDealsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the specialDealsList where lastEditedWhen is null
        defaultSpecialDealsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialDealsByCartDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        ShoppingCarts cartDiscount = ShoppingCartsResourceIT.createEntity(em);
        em.persist(cartDiscount);
        em.flush();
        specialDeals.addCartDiscount(cartDiscount);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long cartDiscountId = cartDiscount.getId();

        // Get all the specialDealsList where cartDiscount equals to cartDiscountId
        defaultSpecialDealsShouldBeFound("cartDiscountId.equals=" + cartDiscountId);

        // Get all the specialDealsList where cartDiscount equals to cartDiscountId + 1
        defaultSpecialDealsShouldNotBeFound("cartDiscountId.equals=" + (cartDiscountId + 1));
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByOrderDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        Orders orderDiscount = OrdersResourceIT.createEntity(em);
        em.persist(orderDiscount);
        em.flush();
        specialDeals.addOrderDiscount(orderDiscount);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long orderDiscountId = orderDiscount.getId();

        // Get all the specialDealsList where orderDiscount equals to orderDiscountId
        defaultSpecialDealsShouldBeFound("orderDiscountId.equals=" + orderDiscountId);

        // Get all the specialDealsList where orderDiscount equals to orderDiscountId + 1
        defaultSpecialDealsShouldNotBeFound("orderDiscountId.equals=" + (orderDiscountId + 1));
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByBuyingGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        BuyingGroups buyingGroup = BuyingGroupsResourceIT.createEntity(em);
        em.persist(buyingGroup);
        em.flush();
        specialDeals.setBuyingGroup(buyingGroup);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long buyingGroupId = buyingGroup.getId();

        // Get all the specialDealsList where buyingGroup equals to buyingGroupId
        defaultSpecialDealsShouldBeFound("buyingGroupId.equals=" + buyingGroupId);

        // Get all the specialDealsList where buyingGroup equals to buyingGroupId + 1
        defaultSpecialDealsShouldNotBeFound("buyingGroupId.equals=" + (buyingGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByCustomerCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        CustomerCategories customerCategory = CustomerCategoriesResourceIT.createEntity(em);
        em.persist(customerCategory);
        em.flush();
        specialDeals.setCustomerCategory(customerCategory);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long customerCategoryId = customerCategory.getId();

        // Get all the specialDealsList where customerCategory equals to customerCategoryId
        defaultSpecialDealsShouldBeFound("customerCategoryId.equals=" + customerCategoryId);

        // Get all the specialDealsList where customerCategory equals to customerCategoryId + 1
        defaultSpecialDealsShouldNotBeFound("customerCategoryId.equals=" + (customerCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        specialDeals.setCustomer(customer);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long customerId = customer.getId();

        // Get all the specialDealsList where customer equals to customerId
        defaultSpecialDealsShouldBeFound("customerId.equals=" + customerId);

        // Get all the specialDealsList where customer equals to customerId + 1
        defaultSpecialDealsShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        specialDeals.setProductCategory(productCategory);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long productCategoryId = productCategory.getId();

        // Get all the specialDealsList where productCategory equals to productCategoryId
        defaultSpecialDealsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the specialDealsList where productCategory equals to productCategoryId + 1
        defaultSpecialDealsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSpecialDealsByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        specialDeals.setStockItem(stockItem);
        specialDealsRepository.saveAndFlush(specialDeals);
        Long stockItemId = stockItem.getId();

        // Get all the specialDealsList where stockItem equals to stockItemId
        defaultSpecialDealsShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the specialDealsList where stockItem equals to stockItemId + 1
        defaultSpecialDealsShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpecialDealsShouldBeFound(String filter) throws Exception {
        restSpecialDealsMockMvc.perform(get("/api/special-deals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialDeals.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealDescription").value(hasItem(DEFAULT_DEAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].discountCode").value(hasItem(DEFAULT_DISCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restSpecialDealsMockMvc.perform(get("/api/special-deals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpecialDealsShouldNotBeFound(String filter) throws Exception {
        restSpecialDealsMockMvc.perform(get("/api/special-deals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpecialDealsMockMvc.perform(get("/api/special-deals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSpecialDeals() throws Exception {
        // Get the specialDeals
        restSpecialDealsMockMvc.perform(get("/api/special-deals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        int databaseSizeBeforeUpdate = specialDealsRepository.findAll().size();

        // Update the specialDeals
        SpecialDeals updatedSpecialDeals = specialDealsRepository.findById(specialDeals.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialDeals are not directly saved in db
        em.detach(updatedSpecialDeals);
        updatedSpecialDeals
            .dealDescription(UPDATED_DEAL_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .discountCode(UPDATED_DISCOUNT_CODE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(updatedSpecialDeals);

        restSpecialDealsMockMvc.perform(put("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isOk());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeUpdate);
        SpecialDeals testSpecialDeals = specialDealsList.get(specialDealsList.size() - 1);
        assertThat(testSpecialDeals.getDealDescription()).isEqualTo(UPDATED_DEAL_DESCRIPTION);
        assertThat(testSpecialDeals.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSpecialDeals.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSpecialDeals.getDiscountAmount()).isEqualTo(UPDATED_DISCOUNT_AMOUNT);
        assertThat(testSpecialDeals.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testSpecialDeals.getDiscountCode()).isEqualTo(UPDATED_DISCOUNT_CODE);
        assertThat(testSpecialDeals.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testSpecialDeals.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testSpecialDeals.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialDeals() throws Exception {
        int databaseSizeBeforeUpdate = specialDealsRepository.findAll().size();

        // Create the SpecialDeals
        SpecialDealsDTO specialDealsDTO = specialDealsMapper.toDto(specialDeals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialDealsMockMvc.perform(put("/api/special-deals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialDealsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialDeals in the database
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialDeals() throws Exception {
        // Initialize the database
        specialDealsRepository.saveAndFlush(specialDeals);

        int databaseSizeBeforeDelete = specialDealsRepository.findAll().size();

        // Delete the specialDeals
        restSpecialDealsMockMvc.perform(delete("/api/special-deals/{id}", specialDeals.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpecialDeals> specialDealsList = specialDealsRepository.findAll();
        assertThat(specialDealsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
