package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.StockItemTransactions;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.Customers;
import com.epmweb.server.domain.Invoices;
import com.epmweb.server.domain.Suppliers;
import com.epmweb.server.domain.TransactionTypes;
import com.epmweb.server.domain.PurchaseOrders;
import com.epmweb.server.repository.StockItemTransactionsRepository;
import com.epmweb.server.service.StockItemTransactionsService;
import com.epmweb.server.service.dto.StockItemTransactionsDTO;
import com.epmweb.server.service.mapper.StockItemTransactionsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.StockItemTransactionsCriteria;
import com.epmweb.server.service.StockItemTransactionsQueryService;

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
 * Integration tests for the {@link StockItemTransactionsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StockItemTransactionsResourceIT {

    private static final Instant DEFAULT_TRANSACTION_OCCURED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_OCCURED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StockItemTransactionsRepository stockItemTransactionsRepository;

    @Autowired
    private StockItemTransactionsMapper stockItemTransactionsMapper;

    @Autowired
    private StockItemTransactionsService stockItemTransactionsService;

    @Autowired
    private StockItemTransactionsQueryService stockItemTransactionsQueryService;

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

    private MockMvc restStockItemTransactionsMockMvc;

    private StockItemTransactions stockItemTransactions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemTransactionsResource stockItemTransactionsResource = new StockItemTransactionsResource(stockItemTransactionsService, stockItemTransactionsQueryService);
        this.restStockItemTransactionsMockMvc = MockMvcBuilders.standaloneSetup(stockItemTransactionsResource)
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
    public static StockItemTransactions createEntity(EntityManager em) {
        StockItemTransactions stockItemTransactions = new StockItemTransactions()
            .transactionOccuredWhen(DEFAULT_TRANSACTION_OCCURED_WHEN)
            .quantity(DEFAULT_QUANTITY)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return stockItemTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemTransactions createUpdatedEntity(EntityManager em) {
        StockItemTransactions stockItemTransactions = new StockItemTransactions()
            .transactionOccuredWhen(UPDATED_TRANSACTION_OCCURED_WHEN)
            .quantity(UPDATED_QUANTITY)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return stockItemTransactions;
    }

    @BeforeEach
    public void initTest() {
        stockItemTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemTransactions() throws Exception {
        int databaseSizeBeforeCreate = stockItemTransactionsRepository.findAll().size();

        // Create the StockItemTransactions
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);
        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemTransactions testStockItemTransactions = stockItemTransactionsList.get(stockItemTransactionsList.size() - 1);
        assertThat(testStockItemTransactions.getTransactionOccuredWhen()).isEqualTo(DEFAULT_TRANSACTION_OCCURED_WHEN);
        assertThat(testStockItemTransactions.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStockItemTransactions.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testStockItemTransactions.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createStockItemTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemTransactionsRepository.findAll().size();

        // Create the StockItemTransactions with an existing ID
        stockItemTransactions.setId(1L);
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionOccuredWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTransactionsRepository.findAll().size();
        // set the field null
        stockItemTransactions.setTransactionOccuredWhen(null);

        // Create the StockItemTransactions, which fails.
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        restStockItemTransactionsMockMvc.perform(post("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionOccuredWhen").value(hasItem(DEFAULT_TRANSACTION_OCCURED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get the stockItemTransactions
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions/{id}", stockItemTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemTransactions.getId().intValue()))
            .andExpect(jsonPath("$.transactionOccuredWhen").value(DEFAULT_TRANSACTION_OCCURED_WHEN.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getStockItemTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        Long id = stockItemTransactions.getId();

        defaultStockItemTransactionsShouldBeFound("id.equals=" + id);
        defaultStockItemTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByTransactionOccuredWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where transactionOccuredWhen equals to DEFAULT_TRANSACTION_OCCURED_WHEN
        defaultStockItemTransactionsShouldBeFound("transactionOccuredWhen.equals=" + DEFAULT_TRANSACTION_OCCURED_WHEN);

        // Get all the stockItemTransactionsList where transactionOccuredWhen equals to UPDATED_TRANSACTION_OCCURED_WHEN
        defaultStockItemTransactionsShouldNotBeFound("transactionOccuredWhen.equals=" + UPDATED_TRANSACTION_OCCURED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByTransactionOccuredWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where transactionOccuredWhen not equals to DEFAULT_TRANSACTION_OCCURED_WHEN
        defaultStockItemTransactionsShouldNotBeFound("transactionOccuredWhen.notEquals=" + DEFAULT_TRANSACTION_OCCURED_WHEN);

        // Get all the stockItemTransactionsList where transactionOccuredWhen not equals to UPDATED_TRANSACTION_OCCURED_WHEN
        defaultStockItemTransactionsShouldBeFound("transactionOccuredWhen.notEquals=" + UPDATED_TRANSACTION_OCCURED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByTransactionOccuredWhenIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where transactionOccuredWhen in DEFAULT_TRANSACTION_OCCURED_WHEN or UPDATED_TRANSACTION_OCCURED_WHEN
        defaultStockItemTransactionsShouldBeFound("transactionOccuredWhen.in=" + DEFAULT_TRANSACTION_OCCURED_WHEN + "," + UPDATED_TRANSACTION_OCCURED_WHEN);

        // Get all the stockItemTransactionsList where transactionOccuredWhen equals to UPDATED_TRANSACTION_OCCURED_WHEN
        defaultStockItemTransactionsShouldNotBeFound("transactionOccuredWhen.in=" + UPDATED_TRANSACTION_OCCURED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByTransactionOccuredWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where transactionOccuredWhen is not null
        defaultStockItemTransactionsShouldBeFound("transactionOccuredWhen.specified=true");

        // Get all the stockItemTransactionsList where transactionOccuredWhen is null
        defaultStockItemTransactionsShouldNotBeFound("transactionOccuredWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity equals to DEFAULT_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the stockItemTransactionsList where quantity equals to UPDATED_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity not equals to DEFAULT_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the stockItemTransactionsList where quantity not equals to UPDATED_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the stockItemTransactionsList where quantity equals to UPDATED_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity is not null
        defaultStockItemTransactionsShouldBeFound("quantity.specified=true");

        // Get all the stockItemTransactionsList where quantity is null
        defaultStockItemTransactionsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the stockItemTransactionsList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the stockItemTransactionsList where quantity is less than or equal to SMALLER_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity is less than DEFAULT_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the stockItemTransactionsList where quantity is less than UPDATED_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where quantity is greater than DEFAULT_QUANTITY
        defaultStockItemTransactionsShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the stockItemTransactionsList where quantity is greater than SMALLER_QUANTITY
        defaultStockItemTransactionsShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemTransactionsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemTransactionsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemTransactionsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTransactionsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultStockItemTransactionsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultStockItemTransactionsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the stockItemTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemTransactionsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedBy is not null
        defaultStockItemTransactionsShouldBeFound("lastEditedBy.specified=true");

        // Get all the stockItemTransactionsList where lastEditedBy is null
        defaultStockItemTransactionsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultStockItemTransactionsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTransactionsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultStockItemTransactionsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultStockItemTransactionsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTransactionsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultStockItemTransactionsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemTransactionsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemTransactionsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemTransactionsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemTransactionsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemTransactionsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultStockItemTransactionsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the stockItemTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemTransactionsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        // Get all the stockItemTransactionsList where lastEditedWhen is not null
        defaultStockItemTransactionsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the stockItemTransactionsList where lastEditedWhen is null
        defaultStockItemTransactionsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTransactionsByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        stockItemTransactions.setStockItem(stockItem);
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Long stockItemId = stockItem.getId();

        // Get all the stockItemTransactionsList where stockItem equals to stockItemId
        defaultStockItemTransactionsShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the stockItemTransactionsList where stockItem equals to stockItemId + 1
        defaultStockItemTransactionsShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        stockItemTransactions.setCustomer(customer);
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Long customerId = customer.getId();

        // Get all the stockItemTransactionsList where customer equals to customerId
        defaultStockItemTransactionsShouldBeFound("customerId.equals=" + customerId);

        // Get all the stockItemTransactionsList where customer equals to customerId + 1
        defaultStockItemTransactionsShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Invoices invoice = InvoicesResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        stockItemTransactions.setInvoice(invoice);
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Long invoiceId = invoice.getId();

        // Get all the stockItemTransactionsList where invoice equals to invoiceId
        defaultStockItemTransactionsShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the stockItemTransactionsList where invoice equals to invoiceId + 1
        defaultStockItemTransactionsShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        stockItemTransactions.setSupplier(supplier);
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Long supplierId = supplier.getId();

        // Get all the stockItemTransactionsList where supplier equals to supplierId
        defaultStockItemTransactionsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the stockItemTransactionsList where supplier equals to supplierId + 1
        defaultStockItemTransactionsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        TransactionTypes transactionType = TransactionTypesResourceIT.createEntity(em);
        em.persist(transactionType);
        em.flush();
        stockItemTransactions.setTransactionType(transactionType);
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Long transactionTypeId = transactionType.getId();

        // Get all the stockItemTransactionsList where transactionType equals to transactionTypeId
        defaultStockItemTransactionsShouldBeFound("transactionTypeId.equals=" + transactionTypeId);

        // Get all the stockItemTransactionsList where transactionType equals to transactionTypeId + 1
        defaultStockItemTransactionsShouldNotBeFound("transactionTypeId.equals=" + (transactionTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemTransactionsByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        PurchaseOrders purchaseOrder = PurchaseOrdersResourceIT.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        stockItemTransactions.setPurchaseOrder(purchaseOrder);
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the stockItemTransactionsList where purchaseOrder equals to purchaseOrderId
        defaultStockItemTransactionsShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the stockItemTransactionsList where purchaseOrder equals to purchaseOrderId + 1
        defaultStockItemTransactionsShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemTransactionsShouldBeFound(String filter) throws Exception {
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionOccuredWhen").value(hasItem(DEFAULT_TRANSACTION_OCCURED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemTransactionsShouldNotBeFound(String filter) throws Exception {
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockItemTransactions() throws Exception {
        // Get the stockItemTransactions
        restStockItemTransactionsMockMvc.perform(get("/api/stock-item-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        int databaseSizeBeforeUpdate = stockItemTransactionsRepository.findAll().size();

        // Update the stockItemTransactions
        StockItemTransactions updatedStockItemTransactions = stockItemTransactionsRepository.findById(stockItemTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemTransactions are not directly saved in db
        em.detach(updatedStockItemTransactions);
        updatedStockItemTransactions
            .transactionOccuredWhen(UPDATED_TRANSACTION_OCCURED_WHEN)
            .quantity(UPDATED_QUANTITY)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(updatedStockItemTransactions);

        restStockItemTransactionsMockMvc.perform(put("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeUpdate);
        StockItemTransactions testStockItemTransactions = stockItemTransactionsList.get(stockItemTransactionsList.size() - 1);
        assertThat(testStockItemTransactions.getTransactionOccuredWhen()).isEqualTo(UPDATED_TRANSACTION_OCCURED_WHEN);
        assertThat(testStockItemTransactions.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStockItemTransactions.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testStockItemTransactions.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemTransactions() throws Exception {
        int databaseSizeBeforeUpdate = stockItemTransactionsRepository.findAll().size();

        // Create the StockItemTransactions
        StockItemTransactionsDTO stockItemTransactionsDTO = stockItemTransactionsMapper.toDto(stockItemTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemTransactionsMockMvc.perform(put("/api/stock-item-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTransactions in the database
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemTransactions() throws Exception {
        // Initialize the database
        stockItemTransactionsRepository.saveAndFlush(stockItemTransactions);

        int databaseSizeBeforeDelete = stockItemTransactionsRepository.findAll().size();

        // Delete the stockItemTransactions
        restStockItemTransactionsMockMvc.perform(delete("/api/stock-item-transactions/{id}", stockItemTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItemTransactions> stockItemTransactionsList = stockItemTransactionsRepository.findAll();
        assertThat(stockItemTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
