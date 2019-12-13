package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductSetDetails;
import com.epmweb.server.repository.ProductSetDetailsRepository;
import com.epmweb.server.service.ProductSetDetailsService;
import com.epmweb.server.service.dto.ProductSetDetailsDTO;
import com.epmweb.server.service.mapper.ProductSetDetailsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductSetDetailsCriteria;
import com.epmweb.server.service.ProductSetDetailsQueryService;

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
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductSetDetailsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductSetDetailsResourceIT {

    private static final Integer DEFAULT_SUB_GROUP_NO = 1;
    private static final Integer UPDATED_SUB_GROUP_NO = 2;
    private static final Integer SMALLER_SUB_GROUP_NO = 1 - 1;

    private static final Integer DEFAULT_SUB_GROUP_MIN_COUNT = 1;
    private static final Integer UPDATED_SUB_GROUP_MIN_COUNT = 2;
    private static final Integer SMALLER_SUB_GROUP_MIN_COUNT = 1 - 1;

    private static final BigDecimal DEFAULT_SUB_GROUP_MIN_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUB_GROUP_MIN_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SUB_GROUP_MIN_TOTAL = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_MIN_COUNT = 1;
    private static final Integer UPDATED_MIN_COUNT = 2;
    private static final Integer SMALLER_MIN_COUNT = 1 - 1;

    private static final Integer DEFAULT_MAX_COUNT = 1;
    private static final Integer UPDATED_MAX_COUNT = 2;
    private static final Integer SMALLER_MAX_COUNT = 1 - 1;

    private static final Boolean DEFAULT_IS_OPTIONAL = false;
    private static final Boolean UPDATED_IS_OPTIONAL = true;

    @Autowired
    private ProductSetDetailsRepository productSetDetailsRepository;

    @Autowired
    private ProductSetDetailsMapper productSetDetailsMapper;

    @Autowired
    private ProductSetDetailsService productSetDetailsService;

    @Autowired
    private ProductSetDetailsQueryService productSetDetailsQueryService;

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

    private MockMvc restProductSetDetailsMockMvc;

    private ProductSetDetails productSetDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductSetDetailsResource productSetDetailsResource = new ProductSetDetailsResource(productSetDetailsService, productSetDetailsQueryService);
        this.restProductSetDetailsMockMvc = MockMvcBuilders.standaloneSetup(productSetDetailsResource)
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
    public static ProductSetDetails createEntity(EntityManager em) {
        ProductSetDetails productSetDetails = new ProductSetDetails()
            .subGroupNo(DEFAULT_SUB_GROUP_NO)
            .subGroupMinCount(DEFAULT_SUB_GROUP_MIN_COUNT)
            .subGroupMinTotal(DEFAULT_SUB_GROUP_MIN_TOTAL)
            .minCount(DEFAULT_MIN_COUNT)
            .maxCount(DEFAULT_MAX_COUNT)
            .isOptional(DEFAULT_IS_OPTIONAL);
        return productSetDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSetDetails createUpdatedEntity(EntityManager em) {
        ProductSetDetails productSetDetails = new ProductSetDetails()
            .subGroupNo(UPDATED_SUB_GROUP_NO)
            .subGroupMinCount(UPDATED_SUB_GROUP_MIN_COUNT)
            .subGroupMinTotal(UPDATED_SUB_GROUP_MIN_TOTAL)
            .minCount(UPDATED_MIN_COUNT)
            .maxCount(UPDATED_MAX_COUNT)
            .isOptional(UPDATED_IS_OPTIONAL);
        return productSetDetails;
    }

    @BeforeEach
    public void initTest() {
        productSetDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSetDetails() throws Exception {
        int databaseSizeBeforeCreate = productSetDetailsRepository.findAll().size();

        // Create the ProductSetDetails
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);
        restProductSetDetailsMockMvc.perform(post("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSetDetails testProductSetDetails = productSetDetailsList.get(productSetDetailsList.size() - 1);
        assertThat(testProductSetDetails.getSubGroupNo()).isEqualTo(DEFAULT_SUB_GROUP_NO);
        assertThat(testProductSetDetails.getSubGroupMinCount()).isEqualTo(DEFAULT_SUB_GROUP_MIN_COUNT);
        assertThat(testProductSetDetails.getSubGroupMinTotal()).isEqualTo(DEFAULT_SUB_GROUP_MIN_TOTAL);
        assertThat(testProductSetDetails.getMinCount()).isEqualTo(DEFAULT_MIN_COUNT);
        assertThat(testProductSetDetails.getMaxCount()).isEqualTo(DEFAULT_MAX_COUNT);
        assertThat(testProductSetDetails.isIsOptional()).isEqualTo(DEFAULT_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void createProductSetDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSetDetailsRepository.findAll().size();

        // Create the ProductSetDetails with an existing ID
        productSetDetails.setId(1L);
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSetDetailsMockMvc.perform(post("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSubGroupMinTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetDetailsRepository.findAll().size();
        // set the field null
        productSetDetails.setSubGroupMinTotal(null);

        // Create the ProductSetDetails, which fails.
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);

        restProductSetDetailsMockMvc.perform(post("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSetDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].subGroupNo").value(hasItem(DEFAULT_SUB_GROUP_NO)))
            .andExpect(jsonPath("$.[*].subGroupMinCount").value(hasItem(DEFAULT_SUB_GROUP_MIN_COUNT)))
            .andExpect(jsonPath("$.[*].subGroupMinTotal").value(hasItem(DEFAULT_SUB_GROUP_MIN_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].minCount").value(hasItem(DEFAULT_MIN_COUNT)))
            .andExpect(jsonPath("$.[*].maxCount").value(hasItem(DEFAULT_MAX_COUNT)))
            .andExpect(jsonPath("$.[*].isOptional").value(hasItem(DEFAULT_IS_OPTIONAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get the productSetDetails
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details/{id}", productSetDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSetDetails.getId().intValue()))
            .andExpect(jsonPath("$.subGroupNo").value(DEFAULT_SUB_GROUP_NO))
            .andExpect(jsonPath("$.subGroupMinCount").value(DEFAULT_SUB_GROUP_MIN_COUNT))
            .andExpect(jsonPath("$.subGroupMinTotal").value(DEFAULT_SUB_GROUP_MIN_TOTAL.intValue()))
            .andExpect(jsonPath("$.minCount").value(DEFAULT_MIN_COUNT))
            .andExpect(jsonPath("$.maxCount").value(DEFAULT_MAX_COUNT))
            .andExpect(jsonPath("$.isOptional").value(DEFAULT_IS_OPTIONAL.booleanValue()));
    }


    @Test
    @Transactional
    public void getProductSetDetailsByIdFiltering() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        Long id = productSetDetails.getId();

        defaultProductSetDetailsShouldBeFound("id.equals=" + id);
        defaultProductSetDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultProductSetDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductSetDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductSetDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductSetDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo equals to DEFAULT_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.equals=" + DEFAULT_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo equals to UPDATED_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.equals=" + UPDATED_SUB_GROUP_NO);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo not equals to DEFAULT_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.notEquals=" + DEFAULT_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo not equals to UPDATED_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.notEquals=" + UPDATED_SUB_GROUP_NO);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo in DEFAULT_SUB_GROUP_NO or UPDATED_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.in=" + DEFAULT_SUB_GROUP_NO + "," + UPDATED_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo equals to UPDATED_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.in=" + UPDATED_SUB_GROUP_NO);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo is not null
        defaultProductSetDetailsShouldBeFound("subGroupNo.specified=true");

        // Get all the productSetDetailsList where subGroupNo is null
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo is greater than or equal to DEFAULT_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.greaterThanOrEqual=" + DEFAULT_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo is greater than or equal to UPDATED_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.greaterThanOrEqual=" + UPDATED_SUB_GROUP_NO);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo is less than or equal to DEFAULT_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.lessThanOrEqual=" + DEFAULT_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo is less than or equal to SMALLER_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.lessThanOrEqual=" + SMALLER_SUB_GROUP_NO);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo is less than DEFAULT_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.lessThan=" + DEFAULT_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo is less than UPDATED_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.lessThan=" + UPDATED_SUB_GROUP_NO);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupNo is greater than DEFAULT_SUB_GROUP_NO
        defaultProductSetDetailsShouldNotBeFound("subGroupNo.greaterThan=" + DEFAULT_SUB_GROUP_NO);

        // Get all the productSetDetailsList where subGroupNo is greater than SMALLER_SUB_GROUP_NO
        defaultProductSetDetailsShouldBeFound("subGroupNo.greaterThan=" + SMALLER_SUB_GROUP_NO);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount equals to DEFAULT_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.equals=" + DEFAULT_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount equals to UPDATED_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.equals=" + UPDATED_SUB_GROUP_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount not equals to DEFAULT_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.notEquals=" + DEFAULT_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount not equals to UPDATED_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.notEquals=" + UPDATED_SUB_GROUP_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount in DEFAULT_SUB_GROUP_MIN_COUNT or UPDATED_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.in=" + DEFAULT_SUB_GROUP_MIN_COUNT + "," + UPDATED_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount equals to UPDATED_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.in=" + UPDATED_SUB_GROUP_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount is not null
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.specified=true");

        // Get all the productSetDetailsList where subGroupMinCount is null
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount is greater than or equal to DEFAULT_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.greaterThanOrEqual=" + DEFAULT_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount is greater than or equal to UPDATED_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.greaterThanOrEqual=" + UPDATED_SUB_GROUP_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount is less than or equal to DEFAULT_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.lessThanOrEqual=" + DEFAULT_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount is less than or equal to SMALLER_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.lessThanOrEqual=" + SMALLER_SUB_GROUP_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount is less than DEFAULT_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.lessThan=" + DEFAULT_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount is less than UPDATED_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.lessThan=" + UPDATED_SUB_GROUP_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinCount is greater than DEFAULT_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("subGroupMinCount.greaterThan=" + DEFAULT_SUB_GROUP_MIN_COUNT);

        // Get all the productSetDetailsList where subGroupMinCount is greater than SMALLER_SUB_GROUP_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("subGroupMinCount.greaterThan=" + SMALLER_SUB_GROUP_MIN_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal equals to DEFAULT_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.equals=" + DEFAULT_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal equals to UPDATED_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.equals=" + UPDATED_SUB_GROUP_MIN_TOTAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal not equals to DEFAULT_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.notEquals=" + DEFAULT_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal not equals to UPDATED_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.notEquals=" + UPDATED_SUB_GROUP_MIN_TOTAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal in DEFAULT_SUB_GROUP_MIN_TOTAL or UPDATED_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.in=" + DEFAULT_SUB_GROUP_MIN_TOTAL + "," + UPDATED_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal equals to UPDATED_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.in=" + UPDATED_SUB_GROUP_MIN_TOTAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal is not null
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.specified=true");

        // Get all the productSetDetailsList where subGroupMinTotal is null
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal is greater than or equal to DEFAULT_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.greaterThanOrEqual=" + DEFAULT_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal is greater than or equal to UPDATED_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.greaterThanOrEqual=" + UPDATED_SUB_GROUP_MIN_TOTAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal is less than or equal to DEFAULT_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.lessThanOrEqual=" + DEFAULT_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal is less than or equal to SMALLER_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.lessThanOrEqual=" + SMALLER_SUB_GROUP_MIN_TOTAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal is less than DEFAULT_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.lessThan=" + DEFAULT_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal is less than UPDATED_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.lessThan=" + UPDATED_SUB_GROUP_MIN_TOTAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsBySubGroupMinTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where subGroupMinTotal is greater than DEFAULT_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldNotBeFound("subGroupMinTotal.greaterThan=" + DEFAULT_SUB_GROUP_MIN_TOTAL);

        // Get all the productSetDetailsList where subGroupMinTotal is greater than SMALLER_SUB_GROUP_MIN_TOTAL
        defaultProductSetDetailsShouldBeFound("subGroupMinTotal.greaterThan=" + SMALLER_SUB_GROUP_MIN_TOTAL);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount equals to DEFAULT_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.equals=" + DEFAULT_MIN_COUNT);

        // Get all the productSetDetailsList where minCount equals to UPDATED_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.equals=" + UPDATED_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount not equals to DEFAULT_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.notEquals=" + DEFAULT_MIN_COUNT);

        // Get all the productSetDetailsList where minCount not equals to UPDATED_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.notEquals=" + UPDATED_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount in DEFAULT_MIN_COUNT or UPDATED_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.in=" + DEFAULT_MIN_COUNT + "," + UPDATED_MIN_COUNT);

        // Get all the productSetDetailsList where minCount equals to UPDATED_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.in=" + UPDATED_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount is not null
        defaultProductSetDetailsShouldBeFound("minCount.specified=true");

        // Get all the productSetDetailsList where minCount is null
        defaultProductSetDetailsShouldNotBeFound("minCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount is greater than or equal to DEFAULT_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.greaterThanOrEqual=" + DEFAULT_MIN_COUNT);

        // Get all the productSetDetailsList where minCount is greater than or equal to UPDATED_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.greaterThanOrEqual=" + UPDATED_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount is less than or equal to DEFAULT_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.lessThanOrEqual=" + DEFAULT_MIN_COUNT);

        // Get all the productSetDetailsList where minCount is less than or equal to SMALLER_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.lessThanOrEqual=" + SMALLER_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount is less than DEFAULT_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.lessThan=" + DEFAULT_MIN_COUNT);

        // Get all the productSetDetailsList where minCount is less than UPDATED_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.lessThan=" + UPDATED_MIN_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMinCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where minCount is greater than DEFAULT_MIN_COUNT
        defaultProductSetDetailsShouldNotBeFound("minCount.greaterThan=" + DEFAULT_MIN_COUNT);

        // Get all the productSetDetailsList where minCount is greater than SMALLER_MIN_COUNT
        defaultProductSetDetailsShouldBeFound("minCount.greaterThan=" + SMALLER_MIN_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount equals to DEFAULT_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.equals=" + DEFAULT_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount equals to UPDATED_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.equals=" + UPDATED_MAX_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount not equals to DEFAULT_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.notEquals=" + DEFAULT_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount not equals to UPDATED_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.notEquals=" + UPDATED_MAX_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount in DEFAULT_MAX_COUNT or UPDATED_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.in=" + DEFAULT_MAX_COUNT + "," + UPDATED_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount equals to UPDATED_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.in=" + UPDATED_MAX_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount is not null
        defaultProductSetDetailsShouldBeFound("maxCount.specified=true");

        // Get all the productSetDetailsList where maxCount is null
        defaultProductSetDetailsShouldNotBeFound("maxCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount is greater than or equal to DEFAULT_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.greaterThanOrEqual=" + DEFAULT_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount is greater than or equal to UPDATED_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.greaterThanOrEqual=" + UPDATED_MAX_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount is less than or equal to DEFAULT_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.lessThanOrEqual=" + DEFAULT_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount is less than or equal to SMALLER_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.lessThanOrEqual=" + SMALLER_MAX_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount is less than DEFAULT_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.lessThan=" + DEFAULT_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount is less than UPDATED_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.lessThan=" + UPDATED_MAX_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByMaxCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where maxCount is greater than DEFAULT_MAX_COUNT
        defaultProductSetDetailsShouldNotBeFound("maxCount.greaterThan=" + DEFAULT_MAX_COUNT);

        // Get all the productSetDetailsList where maxCount is greater than SMALLER_MAX_COUNT
        defaultProductSetDetailsShouldBeFound("maxCount.greaterThan=" + SMALLER_MAX_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailsByIsOptionalIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where isOptional equals to DEFAULT_IS_OPTIONAL
        defaultProductSetDetailsShouldBeFound("isOptional.equals=" + DEFAULT_IS_OPTIONAL);

        // Get all the productSetDetailsList where isOptional equals to UPDATED_IS_OPTIONAL
        defaultProductSetDetailsShouldNotBeFound("isOptional.equals=" + UPDATED_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByIsOptionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where isOptional not equals to DEFAULT_IS_OPTIONAL
        defaultProductSetDetailsShouldNotBeFound("isOptional.notEquals=" + DEFAULT_IS_OPTIONAL);

        // Get all the productSetDetailsList where isOptional not equals to UPDATED_IS_OPTIONAL
        defaultProductSetDetailsShouldBeFound("isOptional.notEquals=" + UPDATED_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByIsOptionalIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where isOptional in DEFAULT_IS_OPTIONAL or UPDATED_IS_OPTIONAL
        defaultProductSetDetailsShouldBeFound("isOptional.in=" + DEFAULT_IS_OPTIONAL + "," + UPDATED_IS_OPTIONAL);

        // Get all the productSetDetailsList where isOptional equals to UPDATED_IS_OPTIONAL
        defaultProductSetDetailsShouldNotBeFound("isOptional.in=" + UPDATED_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailsByIsOptionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        // Get all the productSetDetailsList where isOptional is not null
        defaultProductSetDetailsShouldBeFound("isOptional.specified=true");

        // Get all the productSetDetailsList where isOptional is null
        defaultProductSetDetailsShouldNotBeFound("isOptional.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductSetDetailsShouldBeFound(String filter) throws Exception {
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSetDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].subGroupNo").value(hasItem(DEFAULT_SUB_GROUP_NO)))
            .andExpect(jsonPath("$.[*].subGroupMinCount").value(hasItem(DEFAULT_SUB_GROUP_MIN_COUNT)))
            .andExpect(jsonPath("$.[*].subGroupMinTotal").value(hasItem(DEFAULT_SUB_GROUP_MIN_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].minCount").value(hasItem(DEFAULT_MIN_COUNT)))
            .andExpect(jsonPath("$.[*].maxCount").value(hasItem(DEFAULT_MAX_COUNT)))
            .andExpect(jsonPath("$.[*].isOptional").value(hasItem(DEFAULT_IS_OPTIONAL.booleanValue())));

        // Check, that the count call also returns 1
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductSetDetailsShouldNotBeFound(String filter) throws Exception {
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductSetDetails() throws Exception {
        // Get the productSetDetails
        restProductSetDetailsMockMvc.perform(get("/api/product-set-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        int databaseSizeBeforeUpdate = productSetDetailsRepository.findAll().size();

        // Update the productSetDetails
        ProductSetDetails updatedProductSetDetails = productSetDetailsRepository.findById(productSetDetails.getId()).get();
        // Disconnect from session so that the updates on updatedProductSetDetails are not directly saved in db
        em.detach(updatedProductSetDetails);
        updatedProductSetDetails
            .subGroupNo(UPDATED_SUB_GROUP_NO)
            .subGroupMinCount(UPDATED_SUB_GROUP_MIN_COUNT)
            .subGroupMinTotal(UPDATED_SUB_GROUP_MIN_TOTAL)
            .minCount(UPDATED_MIN_COUNT)
            .maxCount(UPDATED_MAX_COUNT)
            .isOptional(UPDATED_IS_OPTIONAL);
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(updatedProductSetDetails);

        restProductSetDetailsMockMvc.perform(put("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductSetDetails testProductSetDetails = productSetDetailsList.get(productSetDetailsList.size() - 1);
        assertThat(testProductSetDetails.getSubGroupNo()).isEqualTo(UPDATED_SUB_GROUP_NO);
        assertThat(testProductSetDetails.getSubGroupMinCount()).isEqualTo(UPDATED_SUB_GROUP_MIN_COUNT);
        assertThat(testProductSetDetails.getSubGroupMinTotal()).isEqualTo(UPDATED_SUB_GROUP_MIN_TOTAL);
        assertThat(testProductSetDetails.getMinCount()).isEqualTo(UPDATED_MIN_COUNT);
        assertThat(testProductSetDetails.getMaxCount()).isEqualTo(UPDATED_MAX_COUNT);
        assertThat(testProductSetDetails.isIsOptional()).isEqualTo(UPDATED_IS_OPTIONAL);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSetDetails() throws Exception {
        int databaseSizeBeforeUpdate = productSetDetailsRepository.findAll().size();

        // Create the ProductSetDetails
        ProductSetDetailsDTO productSetDetailsDTO = productSetDetailsMapper.toDto(productSetDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSetDetailsMockMvc.perform(put("/api/product-set-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSetDetails in the database
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSetDetails() throws Exception {
        // Initialize the database
        productSetDetailsRepository.saveAndFlush(productSetDetails);

        int databaseSizeBeforeDelete = productSetDetailsRepository.findAll().size();

        // Delete the productSetDetails
        restProductSetDetailsMockMvc.perform(delete("/api/product-set-details/{id}", productSetDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSetDetails> productSetDetailsList = productSetDetailsRepository.findAll();
        assertThat(productSetDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
