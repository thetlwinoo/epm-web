package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Suppliers;
import com.epmweb.server.domain.User;
import com.epmweb.server.domain.SupplierCategories;
import com.epmweb.server.domain.DeliveryMethods;
import com.epmweb.server.domain.Cities;
import com.epmweb.server.repository.SuppliersRepository;
import com.epmweb.server.service.SuppliersService;
import com.epmweb.server.service.dto.SuppliersDTO;
import com.epmweb.server.service.mapper.SuppliersMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.SuppliersCriteria;
import com.epmweb.server.service.SuppliersQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SuppliersResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class SuppliersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_INTERNATIONAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_INTERNATIONAL_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_DAYS = 1;
    private static final Integer UPDATED_PAYMENT_DAYS = 2;
    private static final Integer SMALLER_PAYMENT_DAYS = 1 - 1;

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SERVICE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_RATING = 1;
    private static final Integer UPDATED_CREDIT_RATING = 2;
    private static final Integer SMALLER_CREDIT_RATING = 1 - 1;

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private SuppliersService suppliersService;

    @Autowired
    private SuppliersQueryService suppliersQueryService;

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

    private MockMvc restSuppliersMockMvc;

    private Suppliers suppliers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuppliersResource suppliersResource = new SuppliersResource(suppliersService, suppliersQueryService);
        this.restSuppliersMockMvc = MockMvcBuilders.standaloneSetup(suppliersResource)
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
    public static Suppliers createEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .name(DEFAULT_NAME)
            .supplierReference(DEFAULT_SUPPLIER_REFERENCE)
            .bankAccountName(DEFAULT_BANK_ACCOUNT_NAME)
            .bankAccountBranch(DEFAULT_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(DEFAULT_BANK_ACCOUNT_CODE)
            .bankAccountNumber(DEFAULT_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(DEFAULT_BANK_INTERNATIONAL_CODE)
            .paymentDays(DEFAULT_PAYMENT_DAYS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .websiteURL(DEFAULT_WEBSITE_URL)
            .webServiceUrl(DEFAULT_WEB_SERVICE_URL)
            .creditRating(DEFAULT_CREDIT_RATING)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return suppliers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suppliers createUpdatedEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .name(UPDATED_NAME)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountBranch(UPDATED_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(UPDATED_BANK_ACCOUNT_CODE)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(UPDATED_BANK_INTERNATIONAL_CODE)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .websiteURL(UPDATED_WEBSITE_URL)
            .webServiceUrl(UPDATED_WEB_SERVICE_URL)
            .creditRating(UPDATED_CREDIT_RATING)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return suppliers;
    }

    @BeforeEach
    public void initTest() {
        suppliers = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuppliers() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);
        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isCreated());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate + 1);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSuppliers.getSupplierReference()).isEqualTo(DEFAULT_SUPPLIER_REFERENCE);
        assertThat(testSuppliers.getBankAccountName()).isEqualTo(DEFAULT_BANK_ACCOUNT_NAME);
        assertThat(testSuppliers.getBankAccountBranch()).isEqualTo(DEFAULT_BANK_ACCOUNT_BRANCH);
        assertThat(testSuppliers.getBankAccountCode()).isEqualTo(DEFAULT_BANK_ACCOUNT_CODE);
        assertThat(testSuppliers.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testSuppliers.getBankInternationalCode()).isEqualTo(DEFAULT_BANK_INTERNATIONAL_CODE);
        assertThat(testSuppliers.getPaymentDays()).isEqualTo(DEFAULT_PAYMENT_DAYS);
        assertThat(testSuppliers.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testSuppliers.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSuppliers.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testSuppliers.getWebsiteURL()).isEqualTo(DEFAULT_WEBSITE_URL);
        assertThat(testSuppliers.getWebServiceUrl()).isEqualTo(DEFAULT_WEB_SERVICE_URL);
        assertThat(testSuppliers.getCreditRating()).isEqualTo(DEFAULT_CREDIT_RATING);
        assertThat(testSuppliers.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSuppliers.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
        assertThat(testSuppliers.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSuppliers.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createSuppliersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();

        // Create the Suppliers with an existing ID
        suppliers.setId(1L);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setName(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setPaymentDays(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setPhoneNumber(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setValidFrom(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setValidTo(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc.perform(post("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountBranch").value(hasItem(DEFAULT_BANK_ACCOUNT_BRANCH)))
            .andExpect(jsonPath("$.[*].bankAccountCode").value(hasItem(DEFAULT_BANK_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankInternationalCode").value(hasItem(DEFAULT_BANK_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteURL").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get the suppliers
        restSuppliersMockMvc.perform(get("/api/suppliers/{id}", suppliers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suppliers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.supplierReference").value(DEFAULT_SUPPLIER_REFERENCE))
            .andExpect(jsonPath("$.bankAccountName").value(DEFAULT_BANK_ACCOUNT_NAME))
            .andExpect(jsonPath("$.bankAccountBranch").value(DEFAULT_BANK_ACCOUNT_BRANCH))
            .andExpect(jsonPath("$.bankAccountCode").value(DEFAULT_BANK_ACCOUNT_CODE))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.bankInternationalCode").value(DEFAULT_BANK_INTERNATIONAL_CODE))
            .andExpect(jsonPath("$.paymentDays").value(DEFAULT_PAYMENT_DAYS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER))
            .andExpect(jsonPath("$.websiteURL").value(DEFAULT_WEBSITE_URL))
            .andExpect(jsonPath("$.webServiceUrl").value(DEFAULT_WEB_SERVICE_URL))
            .andExpect(jsonPath("$.creditRating").value(DEFAULT_CREDIT_RATING))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getSuppliersByIdFiltering() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        Long id = suppliers.getId();

        defaultSuppliersShouldBeFound("id.equals=" + id);
        defaultSuppliersShouldNotBeFound("id.notEquals=" + id);

        defaultSuppliersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSuppliersShouldNotBeFound("id.greaterThan=" + id);

        defaultSuppliersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSuppliersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSuppliersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name equals to DEFAULT_NAME
        defaultSuppliersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the suppliersList where name equals to UPDATED_NAME
        defaultSuppliersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name not equals to DEFAULT_NAME
        defaultSuppliersShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the suppliersList where name not equals to UPDATED_NAME
        defaultSuppliersShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSuppliersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the suppliersList where name equals to UPDATED_NAME
        defaultSuppliersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name is not null
        defaultSuppliersShouldBeFound("name.specified=true");

        // Get all the suppliersList where name is null
        defaultSuppliersShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name contains DEFAULT_NAME
        defaultSuppliersShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the suppliersList where name contains UPDATED_NAME
        defaultSuppliersShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where name does not contain DEFAULT_NAME
        defaultSuppliersShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the suppliersList where name does not contain UPDATED_NAME
        defaultSuppliersShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference equals to DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.equals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.equals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference not equals to DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.notEquals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference not equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.notEquals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference in DEFAULT_SUPPLIER_REFERENCE or UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.in=" + DEFAULT_SUPPLIER_REFERENCE + "," + UPDATED_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.in=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference is not null
        defaultSuppliersShouldBeFound("supplierReference.specified=true");

        // Get all the suppliersList where supplierReference is null
        defaultSuppliersShouldNotBeFound("supplierReference.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference contains DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.contains=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference contains UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.contains=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllSuppliersBySupplierReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierReference does not contain DEFAULT_SUPPLIER_REFERENCE
        defaultSuppliersShouldNotBeFound("supplierReference.doesNotContain=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the suppliersList where supplierReference does not contain UPDATED_SUPPLIER_REFERENCE
        defaultSuppliersShouldBeFound("supplierReference.doesNotContain=" + UPDATED_SUPPLIER_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName equals to DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.equals=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.equals=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName not equals to DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.notEquals=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName not equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.notEquals=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName in DEFAULT_BANK_ACCOUNT_NAME or UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.in=" + DEFAULT_BANK_ACCOUNT_NAME + "," + UPDATED_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.in=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName is not null
        defaultSuppliersShouldBeFound("bankAccountName.specified=true");

        // Get all the suppliersList where bankAccountName is null
        defaultSuppliersShouldNotBeFound("bankAccountName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName contains DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.contains=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName contains UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.contains=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountName does not contain DEFAULT_BANK_ACCOUNT_NAME
        defaultSuppliersShouldNotBeFound("bankAccountName.doesNotContain=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the suppliersList where bankAccountName does not contain UPDATED_BANK_ACCOUNT_NAME
        defaultSuppliersShouldBeFound("bankAccountName.doesNotContain=" + UPDATED_BANK_ACCOUNT_NAME);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch equals to DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.equals=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.equals=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch not equals to DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.notEquals=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch not equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.notEquals=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch in DEFAULT_BANK_ACCOUNT_BRANCH or UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.in=" + DEFAULT_BANK_ACCOUNT_BRANCH + "," + UPDATED_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch equals to UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.in=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch is not null
        defaultSuppliersShouldBeFound("bankAccountBranch.specified=true");

        // Get all the suppliersList where bankAccountBranch is null
        defaultSuppliersShouldNotBeFound("bankAccountBranch.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch contains DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.contains=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch contains UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.contains=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountBranchNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountBranch does not contain DEFAULT_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldNotBeFound("bankAccountBranch.doesNotContain=" + DEFAULT_BANK_ACCOUNT_BRANCH);

        // Get all the suppliersList where bankAccountBranch does not contain UPDATED_BANK_ACCOUNT_BRANCH
        defaultSuppliersShouldBeFound("bankAccountBranch.doesNotContain=" + UPDATED_BANK_ACCOUNT_BRANCH);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode equals to DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.equals=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.equals=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode not equals to DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.notEquals=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode not equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.notEquals=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode in DEFAULT_BANK_ACCOUNT_CODE or UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.in=" + DEFAULT_BANK_ACCOUNT_CODE + "," + UPDATED_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode equals to UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.in=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode is not null
        defaultSuppliersShouldBeFound("bankAccountCode.specified=true");

        // Get all the suppliersList where bankAccountCode is null
        defaultSuppliersShouldNotBeFound("bankAccountCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode contains DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.contains=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode contains UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.contains=" + UPDATED_BANK_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountCode does not contain DEFAULT_BANK_ACCOUNT_CODE
        defaultSuppliersShouldNotBeFound("bankAccountCode.doesNotContain=" + DEFAULT_BANK_ACCOUNT_CODE);

        // Get all the suppliersList where bankAccountCode does not contain UPDATED_BANK_ACCOUNT_CODE
        defaultSuppliersShouldBeFound("bankAccountCode.doesNotContain=" + UPDATED_BANK_ACCOUNT_CODE);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber equals to DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.equals=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.equals=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber not equals to DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.notEquals=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber not equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.notEquals=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber in DEFAULT_BANK_ACCOUNT_NUMBER or UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.in=" + DEFAULT_BANK_ACCOUNT_NUMBER + "," + UPDATED_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.in=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber is not null
        defaultSuppliersShouldBeFound("bankAccountNumber.specified=true");

        // Get all the suppliersList where bankAccountNumber is null
        defaultSuppliersShouldNotBeFound("bankAccountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber contains DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.contains=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber contains UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.contains=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankAccountNumber does not contain DEFAULT_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldNotBeFound("bankAccountNumber.doesNotContain=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the suppliersList where bankAccountNumber does not contain UPDATED_BANK_ACCOUNT_NUMBER
        defaultSuppliersShouldBeFound("bankAccountNumber.doesNotContain=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode equals to DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.equals=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.equals=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode not equals to DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.notEquals=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode not equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.notEquals=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode in DEFAULT_BANK_INTERNATIONAL_CODE or UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.in=" + DEFAULT_BANK_INTERNATIONAL_CODE + "," + UPDATED_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode equals to UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.in=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode is not null
        defaultSuppliersShouldBeFound("bankInternationalCode.specified=true");

        // Get all the suppliersList where bankInternationalCode is null
        defaultSuppliersShouldNotBeFound("bankInternationalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode contains DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.contains=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode contains UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.contains=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }

    @Test
    @Transactional
    public void getAllSuppliersByBankInternationalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where bankInternationalCode does not contain DEFAULT_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldNotBeFound("bankInternationalCode.doesNotContain=" + DEFAULT_BANK_INTERNATIONAL_CODE);

        // Get all the suppliersList where bankInternationalCode does not contain UPDATED_BANK_INTERNATIONAL_CODE
        defaultSuppliersShouldBeFound("bankInternationalCode.doesNotContain=" + UPDATED_BANK_INTERNATIONAL_CODE);
    }


    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.equals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.equals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays not equals to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.notEquals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays not equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.notEquals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays in DEFAULT_PAYMENT_DAYS or UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.in=" + DEFAULT_PAYMENT_DAYS + "," + UPDATED_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.in=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is not null
        defaultSuppliersShouldBeFound("paymentDays.specified=true");

        // Get all the suppliersList where paymentDays is null
        defaultSuppliersShouldNotBeFound("paymentDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is greater than or equal to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.greaterThanOrEqual=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is greater than or equal to UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.greaterThanOrEqual=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is less than or equal to DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.lessThanOrEqual=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is less than or equal to SMALLER_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.lessThanOrEqual=" + SMALLER_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is less than DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.lessThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is less than UPDATED_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.lessThan=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPaymentDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where paymentDays is greater than DEFAULT_PAYMENT_DAYS
        defaultSuppliersShouldNotBeFound("paymentDays.greaterThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the suppliersList where paymentDays is greater than SMALLER_PAYMENT_DAYS
        defaultSuppliersShouldBeFound("paymentDays.greaterThan=" + SMALLER_PAYMENT_DAYS);
    }


    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments not equals to DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.notEquals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments not equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.notEquals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments is not null
        defaultSuppliersShouldBeFound("internalComments.specified=true");

        // Get all the suppliersList where internalComments is null
        defaultSuppliersShouldNotBeFound("internalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments contains DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.contains=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments contains UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.contains=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSuppliersByInternalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where internalComments does not contain DEFAULT_INTERNAL_COMMENTS
        defaultSuppliersShouldNotBeFound("internalComments.doesNotContain=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the suppliersList where internalComments does not contain UPDATED_INTERNAL_COMMENTS
        defaultSuppliersShouldBeFound("internalComments.doesNotContain=" + UPDATED_INTERNAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber is not null
        defaultSuppliersShouldBeFound("phoneNumber.specified=true");

        // Get all the suppliersList where phoneNumber is null
        defaultSuppliersShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultSuppliersShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the suppliersList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultSuppliersShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber not equals to DEFAULT_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.notEquals=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber not equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.notEquals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the suppliersList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber is not null
        defaultSuppliersShouldBeFound("faxNumber.specified=true");

        // Get all the suppliersList where faxNumber is null
        defaultSuppliersShouldNotBeFound("faxNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByFaxNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber contains DEFAULT_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.contains=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber contains UPDATED_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.contains=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSuppliersByFaxNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where faxNumber does not contain DEFAULT_FAX_NUMBER
        defaultSuppliersShouldNotBeFound("faxNumber.doesNotContain=" + DEFAULT_FAX_NUMBER);

        // Get all the suppliersList where faxNumber does not contain UPDATED_FAX_NUMBER
        defaultSuppliersShouldBeFound("faxNumber.doesNotContain=" + UPDATED_FAX_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL equals to DEFAULT_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.equals=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteURL equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.equals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL not equals to DEFAULT_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.notEquals=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteURL not equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.notEquals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL in DEFAULT_WEBSITE_URL or UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.in=" + DEFAULT_WEBSITE_URL + "," + UPDATED_WEBSITE_URL);

        // Get all the suppliersList where websiteURL equals to UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.in=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL is not null
        defaultSuppliersShouldBeFound("websiteURL.specified=true");

        // Get all the suppliersList where websiteURL is null
        defaultSuppliersShouldNotBeFound("websiteURL.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL contains DEFAULT_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.contains=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteURL contains UPDATED_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.contains=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebsiteURLNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where websiteURL does not contain DEFAULT_WEBSITE_URL
        defaultSuppliersShouldNotBeFound("websiteURL.doesNotContain=" + DEFAULT_WEBSITE_URL);

        // Get all the suppliersList where websiteURL does not contain UPDATED_WEBSITE_URL
        defaultSuppliersShouldBeFound("websiteURL.doesNotContain=" + UPDATED_WEBSITE_URL);
    }


    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl equals to DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.equals=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.equals=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl not equals to DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.notEquals=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl not equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.notEquals=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl in DEFAULT_WEB_SERVICE_URL or UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.in=" + DEFAULT_WEB_SERVICE_URL + "," + UPDATED_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl equals to UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.in=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl is not null
        defaultSuppliersShouldBeFound("webServiceUrl.specified=true");

        // Get all the suppliersList where webServiceUrl is null
        defaultSuppliersShouldNotBeFound("webServiceUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl contains DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.contains=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl contains UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.contains=" + UPDATED_WEB_SERVICE_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByWebServiceUrlNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where webServiceUrl does not contain DEFAULT_WEB_SERVICE_URL
        defaultSuppliersShouldNotBeFound("webServiceUrl.doesNotContain=" + DEFAULT_WEB_SERVICE_URL);

        // Get all the suppliersList where webServiceUrl does not contain UPDATED_WEB_SERVICE_URL
        defaultSuppliersShouldBeFound("webServiceUrl.doesNotContain=" + UPDATED_WEB_SERVICE_URL);
    }


    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.equals=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.equals=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating not equals to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.notEquals=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating not equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.notEquals=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating in DEFAULT_CREDIT_RATING or UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.in=" + DEFAULT_CREDIT_RATING + "," + UPDATED_CREDIT_RATING);

        // Get all the suppliersList where creditRating equals to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.in=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is not null
        defaultSuppliersShouldBeFound("creditRating.specified=true");

        // Get all the suppliersList where creditRating is null
        defaultSuppliersShouldNotBeFound("creditRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is greater than or equal to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.greaterThanOrEqual=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is greater than or equal to UPDATED_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.greaterThanOrEqual=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is less than or equal to DEFAULT_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.lessThanOrEqual=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is less than or equal to SMALLER_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.lessThanOrEqual=" + SMALLER_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is less than DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.lessThan=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is less than UPDATED_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.lessThan=" + UPDATED_CREDIT_RATING);
    }

    @Test
    @Transactional
    public void getAllSuppliersByCreditRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where creditRating is greater than DEFAULT_CREDIT_RATING
        defaultSuppliersShouldNotBeFound("creditRating.greaterThan=" + DEFAULT_CREDIT_RATING);

        // Get all the suppliersList where creditRating is greater than SMALLER_CREDIT_RATING
        defaultSuppliersShouldBeFound("creditRating.greaterThan=" + SMALLER_CREDIT_RATING);
    }


    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag not equals to DEFAULT_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.notEquals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag not equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.notEquals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the suppliersList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSuppliersShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSuppliersByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where activeFlag is not null
        defaultSuppliersShouldBeFound("activeFlag.specified=true");

        // Get all the suppliersList where activeFlag is null
        defaultSuppliersShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultSuppliersShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the suppliersList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultSuppliersShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultSuppliersShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the suppliersList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultSuppliersShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultSuppliersShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the suppliersList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultSuppliersShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where thumbnailUrl is not null
        defaultSuppliersShouldBeFound("thumbnailUrl.specified=true");

        // Get all the suppliersList where thumbnailUrl is null
        defaultSuppliersShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllSuppliersByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultSuppliersShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the suppliersList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultSuppliersShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllSuppliersByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultSuppliersShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the suppliersList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultSuppliersShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom not equals to DEFAULT_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the suppliersList where validFrom not equals to UPDATED_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSuppliersShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the suppliersList where validFrom equals to UPDATED_VALID_FROM
        defaultSuppliersShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validFrom is not null
        defaultSuppliersShouldBeFound("validFrom.specified=true");

        // Get all the suppliersList where validFrom is null
        defaultSuppliersShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo equals to DEFAULT_VALID_TO
        defaultSuppliersShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo not equals to DEFAULT_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the suppliersList where validTo not equals to UPDATED_VALID_TO
        defaultSuppliersShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSuppliersShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the suppliersList where validTo equals to UPDATED_VALID_TO
        defaultSuppliersShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSuppliersByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where validTo is not null
        defaultSuppliersShouldBeFound("validTo.specified=true");

        // Get all the suppliersList where validTo is null
        defaultSuppliersShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        suppliers.setUser(user);
        suppliersRepository.saveAndFlush(suppliers);
        String userId = user.getId();

        // Get all the suppliersList where user equals to userId
        defaultSuppliersShouldBeFound("userId.equals=" + userId);

        // Get all the suppliersList where user equals to userId + 1
        defaultSuppliersShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersBySupplierCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        SupplierCategories supplierCategory = SupplierCategoriesResourceIT.createEntity(em);
        em.persist(supplierCategory);
        em.flush();
        suppliers.setSupplierCategory(supplierCategory);
        suppliersRepository.saveAndFlush(suppliers);
        Long supplierCategoryId = supplierCategory.getId();

        // Get all the suppliersList where supplierCategory equals to supplierCategoryId
        defaultSuppliersShouldBeFound("supplierCategoryId.equals=" + supplierCategoryId);

        // Get all the suppliersList where supplierCategory equals to supplierCategoryId + 1
        defaultSuppliersShouldNotBeFound("supplierCategoryId.equals=" + (supplierCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        suppliers.setDeliveryMethod(deliveryMethod);
        suppliersRepository.saveAndFlush(suppliers);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the suppliersList where deliveryMethod equals to deliveryMethodId
        defaultSuppliersShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the suppliersList where deliveryMethod equals to deliveryMethodId + 1
        defaultSuppliersShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByDeliveryCityIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Cities deliveryCity = CitiesResourceIT.createEntity(em);
        em.persist(deliveryCity);
        em.flush();
        suppliers.setDeliveryCity(deliveryCity);
        suppliersRepository.saveAndFlush(suppliers);
        Long deliveryCityId = deliveryCity.getId();

        // Get all the suppliersList where deliveryCity equals to deliveryCityId
        defaultSuppliersShouldBeFound("deliveryCityId.equals=" + deliveryCityId);

        // Get all the suppliersList where deliveryCity equals to deliveryCityId + 1
        defaultSuppliersShouldNotBeFound("deliveryCityId.equals=" + (deliveryCityId + 1));
    }


    @Test
    @Transactional
    public void getAllSuppliersByPostalCityIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Cities postalCity = CitiesResourceIT.createEntity(em);
        em.persist(postalCity);
        em.flush();
        suppliers.setPostalCity(postalCity);
        suppliersRepository.saveAndFlush(suppliers);
        Long postalCityId = postalCity.getId();

        // Get all the suppliersList where postalCity equals to postalCityId
        defaultSuppliersShouldBeFound("postalCityId.equals=" + postalCityId);

        // Get all the suppliersList where postalCity equals to postalCityId + 1
        defaultSuppliersShouldNotBeFound("postalCityId.equals=" + (postalCityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSuppliersShouldBeFound(String filter) throws Exception {
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountBranch").value(hasItem(DEFAULT_BANK_ACCOUNT_BRANCH)))
            .andExpect(jsonPath("$.[*].bankAccountCode").value(hasItem(DEFAULT_BANK_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].bankInternationalCode").value(hasItem(DEFAULT_BANK_INTERNATIONAL_CODE)))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteURL").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].webServiceUrl").value(hasItem(DEFAULT_WEB_SERVICE_URL)))
            .andExpect(jsonPath("$.[*].creditRating").value(hasItem(DEFAULT_CREDIT_RATING)))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restSuppliersMockMvc.perform(get("/api/suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSuppliersShouldNotBeFound(String filter) throws Exception {
        restSuppliersMockMvc.perform(get("/api/suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSuppliersMockMvc.perform(get("/api/suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSuppliers() throws Exception {
        // Get the suppliers
        restSuppliersMockMvc.perform(get("/api/suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Update the suppliers
        Suppliers updatedSuppliers = suppliersRepository.findById(suppliers.getId()).get();
        // Disconnect from session so that the updates on updatedSuppliers are not directly saved in db
        em.detach(updatedSuppliers);
        updatedSuppliers
            .name(UPDATED_NAME)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountBranch(UPDATED_BANK_ACCOUNT_BRANCH)
            .bankAccountCode(UPDATED_BANK_ACCOUNT_CODE)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .bankInternationalCode(UPDATED_BANK_INTERNATIONAL_CODE)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .websiteURL(UPDATED_WEBSITE_URL)
            .webServiceUrl(UPDATED_WEB_SERVICE_URL)
            .creditRating(UPDATED_CREDIT_RATING)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(updatedSuppliers);

        restSuppliersMockMvc.perform(put("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isOk());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSuppliers.getSupplierReference()).isEqualTo(UPDATED_SUPPLIER_REFERENCE);
        assertThat(testSuppliers.getBankAccountName()).isEqualTo(UPDATED_BANK_ACCOUNT_NAME);
        assertThat(testSuppliers.getBankAccountBranch()).isEqualTo(UPDATED_BANK_ACCOUNT_BRANCH);
        assertThat(testSuppliers.getBankAccountCode()).isEqualTo(UPDATED_BANK_ACCOUNT_CODE);
        assertThat(testSuppliers.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testSuppliers.getBankInternationalCode()).isEqualTo(UPDATED_BANK_INTERNATIONAL_CODE);
        assertThat(testSuppliers.getPaymentDays()).isEqualTo(UPDATED_PAYMENT_DAYS);
        assertThat(testSuppliers.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testSuppliers.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSuppliers.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testSuppliers.getWebsiteURL()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testSuppliers.getWebServiceUrl()).isEqualTo(UPDATED_WEB_SERVICE_URL);
        assertThat(testSuppliers.getCreditRating()).isEqualTo(UPDATED_CREDIT_RATING);
        assertThat(testSuppliers.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSuppliers.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
        assertThat(testSuppliers.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSuppliers.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuppliersMockMvc.perform(put("/api/suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeDelete = suppliersRepository.findAll().size();

        // Delete the suppliers
        restSuppliersMockMvc.perform(delete("/api/suppliers/{id}", suppliers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
