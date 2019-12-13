package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.CustomerTransactions;
import com.epmweb.server.domain.Customers;
import com.epmweb.server.domain.PaymentTransactions;
import com.epmweb.server.domain.TransactionTypes;
import com.epmweb.server.domain.Invoices;
import com.epmweb.server.repository.CustomerTransactionsRepository;
import com.epmweb.server.service.CustomerTransactionsService;
import com.epmweb.server.service.dto.CustomerTransactionsDTO;
import com.epmweb.server.service.mapper.CustomerTransactionsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.CustomerTransactionsCriteria;
import com.epmweb.server.service.CustomerTransactionsQueryService;

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
 * Integration tests for the {@link CustomerTransactionsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class CustomerTransactionsResourceIT {

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT_EXCLUDING_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_EXCLUDING_TAX = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_EXCLUDING_TAX = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TRANSACTION_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OUTSTANDING_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OUTSTANDING_BALANCE = new BigDecimal(2);
    private static final BigDecimal SMALLER_OUTSTANDING_BALANCE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_FINALIZATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINALIZATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_FINALIZED = false;
    private static final Boolean UPDATED_IS_FINALIZED = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerTransactionsRepository customerTransactionsRepository;

    @Autowired
    private CustomerTransactionsMapper customerTransactionsMapper;

    @Autowired
    private CustomerTransactionsService customerTransactionsService;

    @Autowired
    private CustomerTransactionsQueryService customerTransactionsQueryService;

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

    private MockMvc restCustomerTransactionsMockMvc;

    private CustomerTransactions customerTransactions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerTransactionsResource customerTransactionsResource = new CustomerTransactionsResource(customerTransactionsService, customerTransactionsQueryService);
        this.restCustomerTransactionsMockMvc = MockMvcBuilders.standaloneSetup(customerTransactionsResource)
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
    public static CustomerTransactions createEntity(EntityManager em) {
        CustomerTransactions customerTransactions = new CustomerTransactions()
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .amountExcludingTax(DEFAULT_AMOUNT_EXCLUDING_TAX)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .outstandingBalance(DEFAULT_OUTSTANDING_BALANCE)
            .finalizationDate(DEFAULT_FINALIZATION_DATE)
            .isFinalized(DEFAULT_IS_FINALIZED)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return customerTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerTransactions createUpdatedEntity(EntityManager em) {
        CustomerTransactions customerTransactions = new CustomerTransactions()
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .finalizationDate(UPDATED_FINALIZATION_DATE)
            .isFinalized(UPDATED_IS_FINALIZED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return customerTransactions;
    }

    @BeforeEach
    public void initTest() {
        customerTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerTransactions() throws Exception {
        int databaseSizeBeforeCreate = customerTransactionsRepository.findAll().size();

        // Create the CustomerTransactions
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);
        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerTransactions testCustomerTransactions = customerTransactionsList.get(customerTransactionsList.size() - 1);
        assertThat(testCustomerTransactions.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testCustomerTransactions.getAmountExcludingTax()).isEqualTo(DEFAULT_AMOUNT_EXCLUDING_TAX);
        assertThat(testCustomerTransactions.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testCustomerTransactions.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testCustomerTransactions.getOutstandingBalance()).isEqualTo(DEFAULT_OUTSTANDING_BALANCE);
        assertThat(testCustomerTransactions.getFinalizationDate()).isEqualTo(DEFAULT_FINALIZATION_DATE);
        assertThat(testCustomerTransactions.isIsFinalized()).isEqualTo(DEFAULT_IS_FINALIZED);
        assertThat(testCustomerTransactions.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCustomerTransactions.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerTransactionsRepository.findAll().size();

        // Create the CustomerTransactions with an existing ID
        customerTransactions.setId(1L);
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setTransactionDate(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountExcludingTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setAmountExcludingTax(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setTaxAmount(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setTransactionAmount(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutstandingBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTransactionsRepository.findAll().size();
        // set the field null
        customerTransactions.setOutstandingBalance(null);

        // Create the CustomerTransactions, which fails.
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        restCustomerTransactionsMockMvc.perform(post("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountExcludingTax").value(hasItem(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].outstandingBalance").value(hasItem(DEFAULT_OUTSTANDING_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].finalizationDate").value(hasItem(DEFAULT_FINALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isFinalized").value(hasItem(DEFAULT_IS_FINALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get the customerTransactions
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions/{id}", customerTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerTransactions.getId().intValue()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.amountExcludingTax").value(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.outstandingBalance").value(DEFAULT_OUTSTANDING_BALANCE.intValue()))
            .andExpect(jsonPath("$.finalizationDate").value(DEFAULT_FINALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.isFinalized").value(DEFAULT_IS_FINALIZED.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        Long id = customerTransactions.getId();

        defaultCustomerTransactionsShouldBeFound("id.equals=" + id);
        defaultCustomerTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultCustomerTransactionsShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the customerTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultCustomerTransactionsShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionDate not equals to DEFAULT_TRANSACTION_DATE
        defaultCustomerTransactionsShouldNotBeFound("transactionDate.notEquals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the customerTransactionsList where transactionDate not equals to UPDATED_TRANSACTION_DATE
        defaultCustomerTransactionsShouldBeFound("transactionDate.notEquals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultCustomerTransactionsShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the customerTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultCustomerTransactionsShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionDate is not null
        defaultCustomerTransactionsShouldBeFound("transactionDate.specified=true");

        // Get all the customerTransactionsList where transactionDate is null
        defaultCustomerTransactionsShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax equals to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.equals=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.equals=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax not equals to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.notEquals=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax not equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.notEquals=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax in DEFAULT_AMOUNT_EXCLUDING_TAX or UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.in=" + DEFAULT_AMOUNT_EXCLUDING_TAX + "," + UPDATED_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.in=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax is not null
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.specified=true");

        // Get all the customerTransactionsList where amountExcludingTax is null
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax is greater than or equal to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.greaterThanOrEqual=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax is greater than or equal to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.greaterThanOrEqual=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax is less than or equal to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.lessThanOrEqual=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax is less than or equal to SMALLER_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.lessThanOrEqual=" + SMALLER_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax is less than DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.lessThan=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax is less than UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.lessThan=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByAmountExcludingTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where amountExcludingTax is greater than DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldNotBeFound("amountExcludingTax.greaterThan=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerTransactionsList where amountExcludingTax is greater than SMALLER_AMOUNT_EXCLUDING_TAX
        defaultCustomerTransactionsShouldBeFound("amountExcludingTax.greaterThan=" + SMALLER_AMOUNT_EXCLUDING_TAX);
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount is not null
        defaultCustomerTransactionsShouldBeFound("taxAmount.specified=true");

        // Get all the customerTransactionsList where taxAmount is null
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerTransactionsList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultCustomerTransactionsShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount not equals to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.notEquals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount not equals to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.notEquals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount is not null
        defaultCustomerTransactionsShouldBeFound("transactionAmount.specified=true");

        // Get all the customerTransactionsList where transactionAmount is null
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount is greater than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.greaterThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount is greater than or equal to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.greaterThanOrEqual=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount is less than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.lessThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount is less than or equal to SMALLER_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.lessThanOrEqual=" + SMALLER_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount is less than DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.lessThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount is less than UPDATED_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.lessThan=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where transactionAmount is greater than DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldNotBeFound("transactionAmount.greaterThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerTransactionsList where transactionAmount is greater than SMALLER_TRANSACTION_AMOUNT
        defaultCustomerTransactionsShouldBeFound("transactionAmount.greaterThan=" + SMALLER_TRANSACTION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance equals to DEFAULT_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.equals=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance equals to UPDATED_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.equals=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance not equals to DEFAULT_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.notEquals=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance not equals to UPDATED_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.notEquals=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance in DEFAULT_OUTSTANDING_BALANCE or UPDATED_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.in=" + DEFAULT_OUTSTANDING_BALANCE + "," + UPDATED_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance equals to UPDATED_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.in=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance is not null
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.specified=true");

        // Get all the customerTransactionsList where outstandingBalance is null
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance is greater than or equal to DEFAULT_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.greaterThanOrEqual=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance is greater than or equal to UPDATED_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.greaterThanOrEqual=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance is less than or equal to DEFAULT_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.lessThanOrEqual=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance is less than or equal to SMALLER_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.lessThanOrEqual=" + SMALLER_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance is less than DEFAULT_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.lessThan=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance is less than UPDATED_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.lessThan=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByOutstandingBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where outstandingBalance is greater than DEFAULT_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldNotBeFound("outstandingBalance.greaterThan=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the customerTransactionsList where outstandingBalance is greater than SMALLER_OUTSTANDING_BALANCE
        defaultCustomerTransactionsShouldBeFound("outstandingBalance.greaterThan=" + SMALLER_OUTSTANDING_BALANCE);
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByFinalizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where finalizationDate equals to DEFAULT_FINALIZATION_DATE
        defaultCustomerTransactionsShouldBeFound("finalizationDate.equals=" + DEFAULT_FINALIZATION_DATE);

        // Get all the customerTransactionsList where finalizationDate equals to UPDATED_FINALIZATION_DATE
        defaultCustomerTransactionsShouldNotBeFound("finalizationDate.equals=" + UPDATED_FINALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByFinalizationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where finalizationDate not equals to DEFAULT_FINALIZATION_DATE
        defaultCustomerTransactionsShouldNotBeFound("finalizationDate.notEquals=" + DEFAULT_FINALIZATION_DATE);

        // Get all the customerTransactionsList where finalizationDate not equals to UPDATED_FINALIZATION_DATE
        defaultCustomerTransactionsShouldBeFound("finalizationDate.notEquals=" + UPDATED_FINALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByFinalizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where finalizationDate in DEFAULT_FINALIZATION_DATE or UPDATED_FINALIZATION_DATE
        defaultCustomerTransactionsShouldBeFound("finalizationDate.in=" + DEFAULT_FINALIZATION_DATE + "," + UPDATED_FINALIZATION_DATE);

        // Get all the customerTransactionsList where finalizationDate equals to UPDATED_FINALIZATION_DATE
        defaultCustomerTransactionsShouldNotBeFound("finalizationDate.in=" + UPDATED_FINALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByFinalizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where finalizationDate is not null
        defaultCustomerTransactionsShouldBeFound("finalizationDate.specified=true");

        // Get all the customerTransactionsList where finalizationDate is null
        defaultCustomerTransactionsShouldNotBeFound("finalizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByIsFinalizedIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where isFinalized equals to DEFAULT_IS_FINALIZED
        defaultCustomerTransactionsShouldBeFound("isFinalized.equals=" + DEFAULT_IS_FINALIZED);

        // Get all the customerTransactionsList where isFinalized equals to UPDATED_IS_FINALIZED
        defaultCustomerTransactionsShouldNotBeFound("isFinalized.equals=" + UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByIsFinalizedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where isFinalized not equals to DEFAULT_IS_FINALIZED
        defaultCustomerTransactionsShouldNotBeFound("isFinalized.notEquals=" + DEFAULT_IS_FINALIZED);

        // Get all the customerTransactionsList where isFinalized not equals to UPDATED_IS_FINALIZED
        defaultCustomerTransactionsShouldBeFound("isFinalized.notEquals=" + UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByIsFinalizedIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where isFinalized in DEFAULT_IS_FINALIZED or UPDATED_IS_FINALIZED
        defaultCustomerTransactionsShouldBeFound("isFinalized.in=" + DEFAULT_IS_FINALIZED + "," + UPDATED_IS_FINALIZED);

        // Get all the customerTransactionsList where isFinalized equals to UPDATED_IS_FINALIZED
        defaultCustomerTransactionsShouldNotBeFound("isFinalized.in=" + UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByIsFinalizedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where isFinalized is not null
        defaultCustomerTransactionsShouldBeFound("isFinalized.specified=true");

        // Get all the customerTransactionsList where isFinalized is null
        defaultCustomerTransactionsShouldNotBeFound("isFinalized.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerTransactionsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerTransactionsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerTransactionsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerTransactionsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCustomerTransactionsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCustomerTransactionsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the customerTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerTransactionsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedBy is not null
        defaultCustomerTransactionsShouldBeFound("lastEditedBy.specified=true");

        // Get all the customerTransactionsList where lastEditedBy is null
        defaultCustomerTransactionsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCustomerTransactionsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerTransactionsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCustomerTransactionsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCustomerTransactionsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerTransactionsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCustomerTransactionsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerTransactionsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerTransactionsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerTransactionsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerTransactionsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerTransactionsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCustomerTransactionsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the customerTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerTransactionsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        // Get all the customerTransactionsList where lastEditedWhen is not null
        defaultCustomerTransactionsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the customerTransactionsList where lastEditedWhen is null
        defaultCustomerTransactionsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerTransactionsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        customerTransactions.setCustomer(customer);
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        Long customerId = customer.getId();

        // Get all the customerTransactionsList where customer equals to customerId
        defaultCustomerTransactionsShouldBeFound("customerId.equals=" + customerId);

        // Get all the customerTransactionsList where customer equals to customerId + 1
        defaultCustomerTransactionsShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByPaymentTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        PaymentTransactions paymentTransaction = PaymentTransactionsResourceIT.createEntity(em);
        em.persist(paymentTransaction);
        em.flush();
        customerTransactions.setPaymentTransaction(paymentTransaction);
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        Long paymentTransactionId = paymentTransaction.getId();

        // Get all the customerTransactionsList where paymentTransaction equals to paymentTransactionId
        defaultCustomerTransactionsShouldBeFound("paymentTransactionId.equals=" + paymentTransactionId);

        // Get all the customerTransactionsList where paymentTransaction equals to paymentTransactionId + 1
        defaultCustomerTransactionsShouldNotBeFound("paymentTransactionId.equals=" + (paymentTransactionId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        TransactionTypes transactionType = TransactionTypesResourceIT.createEntity(em);
        em.persist(transactionType);
        em.flush();
        customerTransactions.setTransactionType(transactionType);
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        Long transactionTypeId = transactionType.getId();

        // Get all the customerTransactionsList where transactionType equals to transactionTypeId
        defaultCustomerTransactionsShouldBeFound("transactionTypeId.equals=" + transactionTypeId);

        // Get all the customerTransactionsList where transactionType equals to transactionTypeId + 1
        defaultCustomerTransactionsShouldNotBeFound("transactionTypeId.equals=" + (transactionTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerTransactionsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        Invoices invoice = InvoicesResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        customerTransactions.setInvoice(invoice);
        customerTransactionsRepository.saveAndFlush(customerTransactions);
        Long invoiceId = invoice.getId();

        // Get all the customerTransactionsList where invoice equals to invoiceId
        defaultCustomerTransactionsShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the customerTransactionsList where invoice equals to invoiceId + 1
        defaultCustomerTransactionsShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerTransactionsShouldBeFound(String filter) throws Exception {
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountExcludingTax").value(hasItem(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].outstandingBalance").value(hasItem(DEFAULT_OUTSTANDING_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].finalizationDate").value(hasItem(DEFAULT_FINALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isFinalized").value(hasItem(DEFAULT_IS_FINALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerTransactionsShouldNotBeFound(String filter) throws Exception {
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCustomerTransactions() throws Exception {
        // Get the customerTransactions
        restCustomerTransactionsMockMvc.perform(get("/api/customer-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        int databaseSizeBeforeUpdate = customerTransactionsRepository.findAll().size();

        // Update the customerTransactions
        CustomerTransactions updatedCustomerTransactions = customerTransactionsRepository.findById(customerTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerTransactions are not directly saved in db
        em.detach(updatedCustomerTransactions);
        updatedCustomerTransactions
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .finalizationDate(UPDATED_FINALIZATION_DATE)
            .isFinalized(UPDATED_IS_FINALIZED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(updatedCustomerTransactions);

        restCustomerTransactionsMockMvc.perform(put("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeUpdate);
        CustomerTransactions testCustomerTransactions = customerTransactionsList.get(customerTransactionsList.size() - 1);
        assertThat(testCustomerTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testCustomerTransactions.getAmountExcludingTax()).isEqualTo(UPDATED_AMOUNT_EXCLUDING_TAX);
        assertThat(testCustomerTransactions.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testCustomerTransactions.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testCustomerTransactions.getOutstandingBalance()).isEqualTo(UPDATED_OUTSTANDING_BALANCE);
        assertThat(testCustomerTransactions.getFinalizationDate()).isEqualTo(UPDATED_FINALIZATION_DATE);
        assertThat(testCustomerTransactions.isIsFinalized()).isEqualTo(UPDATED_IS_FINALIZED);
        assertThat(testCustomerTransactions.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCustomerTransactions.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerTransactions() throws Exception {
        int databaseSizeBeforeUpdate = customerTransactionsRepository.findAll().size();

        // Create the CustomerTransactions
        CustomerTransactionsDTO customerTransactionsDTO = customerTransactionsMapper.toDto(customerTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerTransactionsMockMvc.perform(put("/api/customer-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerTransactions in the database
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerTransactions() throws Exception {
        // Initialize the database
        customerTransactionsRepository.saveAndFlush(customerTransactions);

        int databaseSizeBeforeDelete = customerTransactionsRepository.findAll().size();

        // Delete the customerTransactions
        restCustomerTransactionsMockMvc.perform(delete("/api/customer-transactions/{id}", customerTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerTransactions> customerTransactionsList = customerTransactionsRepository.findAll();
        assertThat(customerTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
