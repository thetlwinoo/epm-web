package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.SupplierTransactions;
import com.epmweb.server.domain.Suppliers;
import com.epmweb.server.domain.TransactionTypes;
import com.epmweb.server.domain.PurchaseOrders;
import com.epmweb.server.repository.SupplierTransactionsRepository;
import com.epmweb.server.service.SupplierTransactionsService;
import com.epmweb.server.service.dto.SupplierTransactionsDTO;
import com.epmweb.server.service.mapper.SupplierTransactionsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.SupplierTransactionsCriteria;
import com.epmweb.server.service.SupplierTransactionsQueryService;

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
 * Integration tests for the {@link SupplierTransactionsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class SupplierTransactionsResourceIT {

    private static final String DEFAULT_SUPPLIER_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_INVOICE_NUMBER = "BBBBBBBBBB";

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
    private SupplierTransactionsRepository supplierTransactionsRepository;

    @Autowired
    private SupplierTransactionsMapper supplierTransactionsMapper;

    @Autowired
    private SupplierTransactionsService supplierTransactionsService;

    @Autowired
    private SupplierTransactionsQueryService supplierTransactionsQueryService;

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

    private MockMvc restSupplierTransactionsMockMvc;

    private SupplierTransactions supplierTransactions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierTransactionsResource supplierTransactionsResource = new SupplierTransactionsResource(supplierTransactionsService, supplierTransactionsQueryService);
        this.restSupplierTransactionsMockMvc = MockMvcBuilders.standaloneSetup(supplierTransactionsResource)
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
    public static SupplierTransactions createEntity(EntityManager em) {
        SupplierTransactions supplierTransactions = new SupplierTransactions()
            .supplierInvoiceNumber(DEFAULT_SUPPLIER_INVOICE_NUMBER)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .amountExcludingTax(DEFAULT_AMOUNT_EXCLUDING_TAX)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .outstandingBalance(DEFAULT_OUTSTANDING_BALANCE)
            .finalizationDate(DEFAULT_FINALIZATION_DATE)
            .isFinalized(DEFAULT_IS_FINALIZED)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return supplierTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierTransactions createUpdatedEntity(EntityManager em) {
        SupplierTransactions supplierTransactions = new SupplierTransactions()
            .supplierInvoiceNumber(UPDATED_SUPPLIER_INVOICE_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .finalizationDate(UPDATED_FINALIZATION_DATE)
            .isFinalized(UPDATED_IS_FINALIZED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return supplierTransactions;
    }

    @BeforeEach
    public void initTest() {
        supplierTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierTransactions() throws Exception {
        int databaseSizeBeforeCreate = supplierTransactionsRepository.findAll().size();

        // Create the SupplierTransactions
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);
        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierTransactions testSupplierTransactions = supplierTransactionsList.get(supplierTransactionsList.size() - 1);
        assertThat(testSupplierTransactions.getSupplierInvoiceNumber()).isEqualTo(DEFAULT_SUPPLIER_INVOICE_NUMBER);
        assertThat(testSupplierTransactions.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testSupplierTransactions.getAmountExcludingTax()).isEqualTo(DEFAULT_AMOUNT_EXCLUDING_TAX);
        assertThat(testSupplierTransactions.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testSupplierTransactions.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testSupplierTransactions.getOutstandingBalance()).isEqualTo(DEFAULT_OUTSTANDING_BALANCE);
        assertThat(testSupplierTransactions.getFinalizationDate()).isEqualTo(DEFAULT_FINALIZATION_DATE);
        assertThat(testSupplierTransactions.isIsFinalized()).isEqualTo(DEFAULT_IS_FINALIZED);
        assertThat(testSupplierTransactions.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testSupplierTransactions.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createSupplierTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierTransactionsRepository.findAll().size();

        // Create the SupplierTransactions with an existing ID
        supplierTransactions.setId(1L);
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setTransactionDate(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountExcludingTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setAmountExcludingTax(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setTaxAmount(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setTransactionAmount(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutstandingBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierTransactionsRepository.findAll().size();
        // set the field null
        supplierTransactions.setOutstandingBalance(null);

        // Create the SupplierTransactions, which fails.
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        restSupplierTransactionsMockMvc.perform(post("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierInvoiceNumber").value(hasItem(DEFAULT_SUPPLIER_INVOICE_NUMBER)))
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
    public void getSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get the supplierTransactions
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions/{id}", supplierTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierTransactions.getId().intValue()))
            .andExpect(jsonPath("$.supplierInvoiceNumber").value(DEFAULT_SUPPLIER_INVOICE_NUMBER))
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
    public void getSupplierTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        Long id = supplierTransactions.getId();

        defaultSupplierTransactionsShouldBeFound("id.equals=" + id);
        defaultSupplierTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where supplierInvoiceNumber equals to DEFAULT_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldBeFound("supplierInvoiceNumber.equals=" + DEFAULT_SUPPLIER_INVOICE_NUMBER);

        // Get all the supplierTransactionsList where supplierInvoiceNumber equals to UPDATED_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldNotBeFound("supplierInvoiceNumber.equals=" + UPDATED_SUPPLIER_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierInvoiceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where supplierInvoiceNumber not equals to DEFAULT_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldNotBeFound("supplierInvoiceNumber.notEquals=" + DEFAULT_SUPPLIER_INVOICE_NUMBER);

        // Get all the supplierTransactionsList where supplierInvoiceNumber not equals to UPDATED_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldBeFound("supplierInvoiceNumber.notEquals=" + UPDATED_SUPPLIER_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where supplierInvoiceNumber in DEFAULT_SUPPLIER_INVOICE_NUMBER or UPDATED_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldBeFound("supplierInvoiceNumber.in=" + DEFAULT_SUPPLIER_INVOICE_NUMBER + "," + UPDATED_SUPPLIER_INVOICE_NUMBER);

        // Get all the supplierTransactionsList where supplierInvoiceNumber equals to UPDATED_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldNotBeFound("supplierInvoiceNumber.in=" + UPDATED_SUPPLIER_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where supplierInvoiceNumber is not null
        defaultSupplierTransactionsShouldBeFound("supplierInvoiceNumber.specified=true");

        // Get all the supplierTransactionsList where supplierInvoiceNumber is null
        defaultSupplierTransactionsShouldNotBeFound("supplierInvoiceNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierInvoiceNumberContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where supplierInvoiceNumber contains DEFAULT_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldBeFound("supplierInvoiceNumber.contains=" + DEFAULT_SUPPLIER_INVOICE_NUMBER);

        // Get all the supplierTransactionsList where supplierInvoiceNumber contains UPDATED_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldNotBeFound("supplierInvoiceNumber.contains=" + UPDATED_SUPPLIER_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierInvoiceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where supplierInvoiceNumber does not contain DEFAULT_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldNotBeFound("supplierInvoiceNumber.doesNotContain=" + DEFAULT_SUPPLIER_INVOICE_NUMBER);

        // Get all the supplierTransactionsList where supplierInvoiceNumber does not contain UPDATED_SUPPLIER_INVOICE_NUMBER
        defaultSupplierTransactionsShouldBeFound("supplierInvoiceNumber.doesNotContain=" + UPDATED_SUPPLIER_INVOICE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultSupplierTransactionsShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the supplierTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultSupplierTransactionsShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionDate not equals to DEFAULT_TRANSACTION_DATE
        defaultSupplierTransactionsShouldNotBeFound("transactionDate.notEquals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the supplierTransactionsList where transactionDate not equals to UPDATED_TRANSACTION_DATE
        defaultSupplierTransactionsShouldBeFound("transactionDate.notEquals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultSupplierTransactionsShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the supplierTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultSupplierTransactionsShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionDate is not null
        defaultSupplierTransactionsShouldBeFound("transactionDate.specified=true");

        // Get all the supplierTransactionsList where transactionDate is null
        defaultSupplierTransactionsShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax equals to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.equals=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.equals=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax not equals to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.notEquals=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax not equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.notEquals=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax in DEFAULT_AMOUNT_EXCLUDING_TAX or UPDATED_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.in=" + DEFAULT_AMOUNT_EXCLUDING_TAX + "," + UPDATED_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.in=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax is not null
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.specified=true");

        // Get all the supplierTransactionsList where amountExcludingTax is null
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax is greater than or equal to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.greaterThanOrEqual=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax is greater than or equal to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.greaterThanOrEqual=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax is less than or equal to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.lessThanOrEqual=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax is less than or equal to SMALLER_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.lessThanOrEqual=" + SMALLER_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax is less than DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.lessThan=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax is less than UPDATED_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.lessThan=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByAmountExcludingTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where amountExcludingTax is greater than DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldNotBeFound("amountExcludingTax.greaterThan=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the supplierTransactionsList where amountExcludingTax is greater than SMALLER_AMOUNT_EXCLUDING_TAX
        defaultSupplierTransactionsShouldBeFound("amountExcludingTax.greaterThan=" + SMALLER_AMOUNT_EXCLUDING_TAX);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount is not null
        defaultSupplierTransactionsShouldBeFound("taxAmount.specified=true");

        // Get all the supplierTransactionsList where taxAmount is null
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the supplierTransactionsList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultSupplierTransactionsShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount not equals to DEFAULT_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.notEquals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount not equals to UPDATED_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.notEquals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount is not null
        defaultSupplierTransactionsShouldBeFound("transactionAmount.specified=true");

        // Get all the supplierTransactionsList where transactionAmount is null
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount is greater than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.greaterThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount is greater than or equal to UPDATED_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.greaterThanOrEqual=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount is less than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.lessThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount is less than or equal to SMALLER_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.lessThanOrEqual=" + SMALLER_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount is less than DEFAULT_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.lessThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount is less than UPDATED_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.lessThan=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where transactionAmount is greater than DEFAULT_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldNotBeFound("transactionAmount.greaterThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the supplierTransactionsList where transactionAmount is greater than SMALLER_TRANSACTION_AMOUNT
        defaultSupplierTransactionsShouldBeFound("transactionAmount.greaterThan=" + SMALLER_TRANSACTION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance equals to DEFAULT_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.equals=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance equals to UPDATED_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.equals=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance not equals to DEFAULT_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.notEquals=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance not equals to UPDATED_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.notEquals=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance in DEFAULT_OUTSTANDING_BALANCE or UPDATED_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.in=" + DEFAULT_OUTSTANDING_BALANCE + "," + UPDATED_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance equals to UPDATED_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.in=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance is not null
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.specified=true");

        // Get all the supplierTransactionsList where outstandingBalance is null
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance is greater than or equal to DEFAULT_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.greaterThanOrEqual=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance is greater than or equal to UPDATED_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.greaterThanOrEqual=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance is less than or equal to DEFAULT_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.lessThanOrEqual=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance is less than or equal to SMALLER_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.lessThanOrEqual=" + SMALLER_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance is less than DEFAULT_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.lessThan=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance is less than UPDATED_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.lessThan=" + UPDATED_OUTSTANDING_BALANCE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByOutstandingBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where outstandingBalance is greater than DEFAULT_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldNotBeFound("outstandingBalance.greaterThan=" + DEFAULT_OUTSTANDING_BALANCE);

        // Get all the supplierTransactionsList where outstandingBalance is greater than SMALLER_OUTSTANDING_BALANCE
        defaultSupplierTransactionsShouldBeFound("outstandingBalance.greaterThan=" + SMALLER_OUTSTANDING_BALANCE);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByFinalizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where finalizationDate equals to DEFAULT_FINALIZATION_DATE
        defaultSupplierTransactionsShouldBeFound("finalizationDate.equals=" + DEFAULT_FINALIZATION_DATE);

        // Get all the supplierTransactionsList where finalizationDate equals to UPDATED_FINALIZATION_DATE
        defaultSupplierTransactionsShouldNotBeFound("finalizationDate.equals=" + UPDATED_FINALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByFinalizationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where finalizationDate not equals to DEFAULT_FINALIZATION_DATE
        defaultSupplierTransactionsShouldNotBeFound("finalizationDate.notEquals=" + DEFAULT_FINALIZATION_DATE);

        // Get all the supplierTransactionsList where finalizationDate not equals to UPDATED_FINALIZATION_DATE
        defaultSupplierTransactionsShouldBeFound("finalizationDate.notEquals=" + UPDATED_FINALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByFinalizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where finalizationDate in DEFAULT_FINALIZATION_DATE or UPDATED_FINALIZATION_DATE
        defaultSupplierTransactionsShouldBeFound("finalizationDate.in=" + DEFAULT_FINALIZATION_DATE + "," + UPDATED_FINALIZATION_DATE);

        // Get all the supplierTransactionsList where finalizationDate equals to UPDATED_FINALIZATION_DATE
        defaultSupplierTransactionsShouldNotBeFound("finalizationDate.in=" + UPDATED_FINALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByFinalizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where finalizationDate is not null
        defaultSupplierTransactionsShouldBeFound("finalizationDate.specified=true");

        // Get all the supplierTransactionsList where finalizationDate is null
        defaultSupplierTransactionsShouldNotBeFound("finalizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByIsFinalizedIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where isFinalized equals to DEFAULT_IS_FINALIZED
        defaultSupplierTransactionsShouldBeFound("isFinalized.equals=" + DEFAULT_IS_FINALIZED);

        // Get all the supplierTransactionsList where isFinalized equals to UPDATED_IS_FINALIZED
        defaultSupplierTransactionsShouldNotBeFound("isFinalized.equals=" + UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByIsFinalizedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where isFinalized not equals to DEFAULT_IS_FINALIZED
        defaultSupplierTransactionsShouldNotBeFound("isFinalized.notEquals=" + DEFAULT_IS_FINALIZED);

        // Get all the supplierTransactionsList where isFinalized not equals to UPDATED_IS_FINALIZED
        defaultSupplierTransactionsShouldBeFound("isFinalized.notEquals=" + UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByIsFinalizedIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where isFinalized in DEFAULT_IS_FINALIZED or UPDATED_IS_FINALIZED
        defaultSupplierTransactionsShouldBeFound("isFinalized.in=" + DEFAULT_IS_FINALIZED + "," + UPDATED_IS_FINALIZED);

        // Get all the supplierTransactionsList where isFinalized equals to UPDATED_IS_FINALIZED
        defaultSupplierTransactionsShouldNotBeFound("isFinalized.in=" + UPDATED_IS_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByIsFinalizedIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where isFinalized is not null
        defaultSupplierTransactionsShouldBeFound("isFinalized.specified=true");

        // Get all the supplierTransactionsList where isFinalized is null
        defaultSupplierTransactionsShouldNotBeFound("isFinalized.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the supplierTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedBy is not null
        defaultSupplierTransactionsShouldBeFound("lastEditedBy.specified=true");

        // Get all the supplierTransactionsList where lastEditedBy is null
        defaultSupplierTransactionsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultSupplierTransactionsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierTransactionsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultSupplierTransactionsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultSupplierTransactionsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the supplierTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultSupplierTransactionsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the supplierTransactionsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the supplierTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierTransactionsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        // Get all the supplierTransactionsList where lastEditedWhen is not null
        defaultSupplierTransactionsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the supplierTransactionsList where lastEditedWhen is null
        defaultSupplierTransactionsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierTransactionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        supplierTransactions.setSupplier(supplier);
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);
        Long supplierId = supplier.getId();

        // Get all the supplierTransactionsList where supplier equals to supplierId
        defaultSupplierTransactionsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the supplierTransactionsList where supplier equals to supplierId + 1
        defaultSupplierTransactionsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);
        TransactionTypes transactionType = TransactionTypesResourceIT.createEntity(em);
        em.persist(transactionType);
        em.flush();
        supplierTransactions.setTransactionType(transactionType);
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);
        Long transactionTypeId = transactionType.getId();

        // Get all the supplierTransactionsList where transactionType equals to transactionTypeId
        defaultSupplierTransactionsShouldBeFound("transactionTypeId.equals=" + transactionTypeId);

        // Get all the supplierTransactionsList where transactionType equals to transactionTypeId + 1
        defaultSupplierTransactionsShouldNotBeFound("transactionTypeId.equals=" + (transactionTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierTransactionsByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);
        PurchaseOrders purchaseOrder = PurchaseOrdersResourceIT.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        supplierTransactions.setPurchaseOrder(purchaseOrder);
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the supplierTransactionsList where purchaseOrder equals to purchaseOrderId
        defaultSupplierTransactionsShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the supplierTransactionsList where purchaseOrder equals to purchaseOrderId + 1
        defaultSupplierTransactionsShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierTransactionsShouldBeFound(String filter) throws Exception {
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierInvoiceNumber").value(hasItem(DEFAULT_SUPPLIER_INVOICE_NUMBER)))
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
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierTransactionsShouldNotBeFound(String filter) throws Exception {
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplierTransactions() throws Exception {
        // Get the supplierTransactions
        restSupplierTransactionsMockMvc.perform(get("/api/supplier-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        int databaseSizeBeforeUpdate = supplierTransactionsRepository.findAll().size();

        // Update the supplierTransactions
        SupplierTransactions updatedSupplierTransactions = supplierTransactionsRepository.findById(supplierTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierTransactions are not directly saved in db
        em.detach(updatedSupplierTransactions);
        updatedSupplierTransactions
            .supplierInvoiceNumber(UPDATED_SUPPLIER_INVOICE_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .finalizationDate(UPDATED_FINALIZATION_DATE)
            .isFinalized(UPDATED_IS_FINALIZED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(updatedSupplierTransactions);

        restSupplierTransactionsMockMvc.perform(put("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeUpdate);
        SupplierTransactions testSupplierTransactions = supplierTransactionsList.get(supplierTransactionsList.size() - 1);
        assertThat(testSupplierTransactions.getSupplierInvoiceNumber()).isEqualTo(UPDATED_SUPPLIER_INVOICE_NUMBER);
        assertThat(testSupplierTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testSupplierTransactions.getAmountExcludingTax()).isEqualTo(UPDATED_AMOUNT_EXCLUDING_TAX);
        assertThat(testSupplierTransactions.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testSupplierTransactions.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testSupplierTransactions.getOutstandingBalance()).isEqualTo(UPDATED_OUTSTANDING_BALANCE);
        assertThat(testSupplierTransactions.getFinalizationDate()).isEqualTo(UPDATED_FINALIZATION_DATE);
        assertThat(testSupplierTransactions.isIsFinalized()).isEqualTo(UPDATED_IS_FINALIZED);
        assertThat(testSupplierTransactions.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testSupplierTransactions.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierTransactions() throws Exception {
        int databaseSizeBeforeUpdate = supplierTransactionsRepository.findAll().size();

        // Create the SupplierTransactions
        SupplierTransactionsDTO supplierTransactionsDTO = supplierTransactionsMapper.toDto(supplierTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierTransactionsMockMvc.perform(put("/api/supplier-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierTransactions in the database
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierTransactions() throws Exception {
        // Initialize the database
        supplierTransactionsRepository.saveAndFlush(supplierTransactions);

        int databaseSizeBeforeDelete = supplierTransactionsRepository.findAll().size();

        // Delete the supplierTransactions
        restSupplierTransactionsMockMvc.perform(delete("/api/supplier-transactions/{id}", supplierTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierTransactions> supplierTransactionsList = supplierTransactionsRepository.findAll();
        assertThat(supplierTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
