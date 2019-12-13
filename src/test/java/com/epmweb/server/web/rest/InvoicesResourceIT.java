package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Invoices;
import com.epmweb.server.domain.InvoiceLines;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.Customers;
import com.epmweb.server.domain.DeliveryMethods;
import com.epmweb.server.domain.Orders;
import com.epmweb.server.repository.InvoicesRepository;
import com.epmweb.server.service.InvoicesService;
import com.epmweb.server.service.dto.InvoicesDTO;
import com.epmweb.server.service.mapper.InvoicesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.InvoicesCriteria;
import com.epmweb.server.service.InvoicesQueryService;

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

import com.epmweb.server.domain.enumeration.PaymentMethod;
import com.epmweb.server.domain.enumeration.InvoiceStatus;
/**
 * Integration tests for the {@link InvoicesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class InvoicesResourceIT {

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CREDIT_NOTE = false;
    private static final Boolean UPDATED_IS_CREDIT_NOTE = true;

    private static final String DEFAULT_CREDIT_NOTE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_NOTE_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_DRY_ITEMS = 1;
    private static final Integer UPDATED_TOTAL_DRY_ITEMS = 2;
    private static final Integer SMALLER_TOTAL_DRY_ITEMS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_CHILLER_ITEMS = 1;
    private static final Integer UPDATED_TOTAL_CHILLER_ITEMS = 2;
    private static final Integer SMALLER_TOTAL_CHILLER_ITEMS = 1 - 1;

    private static final String DEFAULT_DELIVERY_RUN = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_RUN = "BBBBBBBBBB";

    private static final String DEFAULT_RUN_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_RUN_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_RETURNED_DELIVERY_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RETURNED_DELIVERY_DATA = "BBBBBBBBBB";

    private static final Instant DEFAULT_CONFIRMED_DELIVERY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRMED_DELIVERY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONFIRMED_RECEIVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMED_RECEIVED_BY = "BBBBBBBBBB";

    private static final PaymentMethod DEFAULT_PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    private static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.CASH_ON_DELIVERY;

    private static final InvoiceStatus DEFAULT_STATUS = InvoiceStatus.PAID;
    private static final InvoiceStatus UPDATED_STATUS = InvoiceStatus.ISSUED;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private InvoicesMapper invoicesMapper;

    @Autowired
    private InvoicesService invoicesService;

    @Autowired
    private InvoicesQueryService invoicesQueryService;

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

    private MockMvc restInvoicesMockMvc;

    private Invoices invoices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoicesResource invoicesResource = new InvoicesResource(invoicesService, invoicesQueryService);
        this.restInvoicesMockMvc = MockMvcBuilders.standaloneSetup(invoicesResource)
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
    public static Invoices createEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .customerPurchaseOrderNumber(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .isCreditNote(DEFAULT_IS_CREDIT_NOTE)
            .creditNoteReason(DEFAULT_CREDIT_NOTE_REASON)
            .comments(DEFAULT_COMMENTS)
            .deliveryInstructions(DEFAULT_DELIVERY_INSTRUCTIONS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .totalDryItems(DEFAULT_TOTAL_DRY_ITEMS)
            .totalChillerItems(DEFAULT_TOTAL_CHILLER_ITEMS)
            .deliveryRun(DEFAULT_DELIVERY_RUN)
            .runPosition(DEFAULT_RUN_POSITION)
            .returnedDeliveryData(DEFAULT_RETURNED_DELIVERY_DATA)
            .confirmedDeliveryTime(DEFAULT_CONFIRMED_DELIVERY_TIME)
            .confirmedReceivedBy(DEFAULT_CONFIRMED_RECEIVED_BY)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .status(DEFAULT_STATUS)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return invoices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoices createUpdatedEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invoiceDate(UPDATED_INVOICE_DATE)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .isCreditNote(UPDATED_IS_CREDIT_NOTE)
            .creditNoteReason(UPDATED_CREDIT_NOTE_REASON)
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .totalDryItems(UPDATED_TOTAL_DRY_ITEMS)
            .totalChillerItems(UPDATED_TOTAL_CHILLER_ITEMS)
            .deliveryRun(UPDATED_DELIVERY_RUN)
            .runPosition(UPDATED_RUN_POSITION)
            .returnedDeliveryData(UPDATED_RETURNED_DELIVERY_DATA)
            .confirmedDeliveryTime(UPDATED_CONFIRMED_DELIVERY_TIME)
            .confirmedReceivedBy(UPDATED_CONFIRMED_RECEIVED_BY)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .status(UPDATED_STATUS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return invoices;
    }

    @BeforeEach
    public void initTest() {
        invoices = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoices() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate + 1);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoices.getCustomerPurchaseOrderNumber()).isEqualTo(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);
        assertThat(testInvoices.isIsCreditNote()).isEqualTo(DEFAULT_IS_CREDIT_NOTE);
        assertThat(testInvoices.getCreditNoteReason()).isEqualTo(DEFAULT_CREDIT_NOTE_REASON);
        assertThat(testInvoices.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInvoices.getDeliveryInstructions()).isEqualTo(DEFAULT_DELIVERY_INSTRUCTIONS);
        assertThat(testInvoices.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testInvoices.getTotalDryItems()).isEqualTo(DEFAULT_TOTAL_DRY_ITEMS);
        assertThat(testInvoices.getTotalChillerItems()).isEqualTo(DEFAULT_TOTAL_CHILLER_ITEMS);
        assertThat(testInvoices.getDeliveryRun()).isEqualTo(DEFAULT_DELIVERY_RUN);
        assertThat(testInvoices.getRunPosition()).isEqualTo(DEFAULT_RUN_POSITION);
        assertThat(testInvoices.getReturnedDeliveryData()).isEqualTo(DEFAULT_RETURNED_DELIVERY_DATA);
        assertThat(testInvoices.getConfirmedDeliveryTime()).isEqualTo(DEFAULT_CONFIRMED_DELIVERY_TIME);
        assertThat(testInvoices.getConfirmedReceivedBy()).isEqualTo(DEFAULT_CONFIRMED_RECEIVED_BY);
        assertThat(testInvoices.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testInvoices.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoices.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testInvoices.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createInvoicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices with an existing ID
        invoices.setId(1L);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInvoiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setInvoiceDate(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCreditNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setIsCreditNote(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDryItemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setTotalDryItems(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalChillerItemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setTotalChillerItems(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setPaymentMethod(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setStatus(null);

        // Create the Invoices, which fails.
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].isCreditNote").value(hasItem(DEFAULT_IS_CREDIT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].creditNoteReason").value(hasItem(DEFAULT_CREDIT_NOTE_REASON)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].totalDryItems").value(hasItem(DEFAULT_TOTAL_DRY_ITEMS)))
            .andExpect(jsonPath("$.[*].totalChillerItems").value(hasItem(DEFAULT_TOTAL_CHILLER_ITEMS)))
            .andExpect(jsonPath("$.[*].deliveryRun").value(hasItem(DEFAULT_DELIVERY_RUN)))
            .andExpect(jsonPath("$.[*].runPosition").value(hasItem(DEFAULT_RUN_POSITION)))
            .andExpect(jsonPath("$.[*].returnedDeliveryData").value(hasItem(DEFAULT_RETURNED_DELIVERY_DATA)))
            .andExpect(jsonPath("$.[*].confirmedDeliveryTime").value(hasItem(DEFAULT_CONFIRMED_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].confirmedReceivedBy").value(hasItem(DEFAULT_CONFIRMED_RECEIVED_BY)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoices.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.customerPurchaseOrderNumber").value(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER))
            .andExpect(jsonPath("$.isCreditNote").value(DEFAULT_IS_CREDIT_NOTE.booleanValue()))
            .andExpect(jsonPath("$.creditNoteReason").value(DEFAULT_CREDIT_NOTE_REASON))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.deliveryInstructions").value(DEFAULT_DELIVERY_INSTRUCTIONS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.totalDryItems").value(DEFAULT_TOTAL_DRY_ITEMS))
            .andExpect(jsonPath("$.totalChillerItems").value(DEFAULT_TOTAL_CHILLER_ITEMS))
            .andExpect(jsonPath("$.deliveryRun").value(DEFAULT_DELIVERY_RUN))
            .andExpect(jsonPath("$.runPosition").value(DEFAULT_RUN_POSITION))
            .andExpect(jsonPath("$.returnedDeliveryData").value(DEFAULT_RETURNED_DELIVERY_DATA))
            .andExpect(jsonPath("$.confirmedDeliveryTime").value(DEFAULT_CONFIRMED_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.confirmedReceivedBy").value(DEFAULT_CONFIRMED_RECEIVED_BY))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        Long id = invoices.getId();

        defaultInvoicesShouldBeFound("id.equals=" + id);
        defaultInvoicesShouldNotBeFound("id.notEquals=" + id);

        defaultInvoicesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoicesShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoicesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoicesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate not equals to DEFAULT_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.notEquals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate not equals to UPDATED_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.notEquals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate is not null
        defaultInvoicesShouldBeFound("invoiceDate.specified=true");

        // Get all the invoicesList where invoiceDate is null
        defaultInvoicesShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerPurchaseOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where customerPurchaseOrderNumber equals to DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldBeFound("customerPurchaseOrderNumber.equals=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the invoicesList where customerPurchaseOrderNumber equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldNotBeFound("customerPurchaseOrderNumber.equals=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerPurchaseOrderNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where customerPurchaseOrderNumber not equals to DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldNotBeFound("customerPurchaseOrderNumber.notEquals=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the invoicesList where customerPurchaseOrderNumber not equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldBeFound("customerPurchaseOrderNumber.notEquals=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerPurchaseOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where customerPurchaseOrderNumber in DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER or UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldBeFound("customerPurchaseOrderNumber.in=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER + "," + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the invoicesList where customerPurchaseOrderNumber equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldNotBeFound("customerPurchaseOrderNumber.in=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerPurchaseOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where customerPurchaseOrderNumber is not null
        defaultInvoicesShouldBeFound("customerPurchaseOrderNumber.specified=true");

        // Get all the invoicesList where customerPurchaseOrderNumber is null
        defaultInvoicesShouldNotBeFound("customerPurchaseOrderNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByCustomerPurchaseOrderNumberContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where customerPurchaseOrderNumber contains DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldBeFound("customerPurchaseOrderNumber.contains=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the invoicesList where customerPurchaseOrderNumber contains UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldNotBeFound("customerPurchaseOrderNumber.contains=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCustomerPurchaseOrderNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where customerPurchaseOrderNumber does not contain DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldNotBeFound("customerPurchaseOrderNumber.doesNotContain=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the invoicesList where customerPurchaseOrderNumber does not contain UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultInvoicesShouldBeFound("customerPurchaseOrderNumber.doesNotContain=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByIsCreditNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where isCreditNote equals to DEFAULT_IS_CREDIT_NOTE
        defaultInvoicesShouldBeFound("isCreditNote.equals=" + DEFAULT_IS_CREDIT_NOTE);

        // Get all the invoicesList where isCreditNote equals to UPDATED_IS_CREDIT_NOTE
        defaultInvoicesShouldNotBeFound("isCreditNote.equals=" + UPDATED_IS_CREDIT_NOTE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByIsCreditNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where isCreditNote not equals to DEFAULT_IS_CREDIT_NOTE
        defaultInvoicesShouldNotBeFound("isCreditNote.notEquals=" + DEFAULT_IS_CREDIT_NOTE);

        // Get all the invoicesList where isCreditNote not equals to UPDATED_IS_CREDIT_NOTE
        defaultInvoicesShouldBeFound("isCreditNote.notEquals=" + UPDATED_IS_CREDIT_NOTE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByIsCreditNoteIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where isCreditNote in DEFAULT_IS_CREDIT_NOTE or UPDATED_IS_CREDIT_NOTE
        defaultInvoicesShouldBeFound("isCreditNote.in=" + DEFAULT_IS_CREDIT_NOTE + "," + UPDATED_IS_CREDIT_NOTE);

        // Get all the invoicesList where isCreditNote equals to UPDATED_IS_CREDIT_NOTE
        defaultInvoicesShouldNotBeFound("isCreditNote.in=" + UPDATED_IS_CREDIT_NOTE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByIsCreditNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where isCreditNote is not null
        defaultInvoicesShouldBeFound("isCreditNote.specified=true");

        // Get all the invoicesList where isCreditNote is null
        defaultInvoicesShouldNotBeFound("isCreditNote.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreditNoteReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where creditNoteReason equals to DEFAULT_CREDIT_NOTE_REASON
        defaultInvoicesShouldBeFound("creditNoteReason.equals=" + DEFAULT_CREDIT_NOTE_REASON);

        // Get all the invoicesList where creditNoteReason equals to UPDATED_CREDIT_NOTE_REASON
        defaultInvoicesShouldNotBeFound("creditNoteReason.equals=" + UPDATED_CREDIT_NOTE_REASON);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreditNoteReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where creditNoteReason not equals to DEFAULT_CREDIT_NOTE_REASON
        defaultInvoicesShouldNotBeFound("creditNoteReason.notEquals=" + DEFAULT_CREDIT_NOTE_REASON);

        // Get all the invoicesList where creditNoteReason not equals to UPDATED_CREDIT_NOTE_REASON
        defaultInvoicesShouldBeFound("creditNoteReason.notEquals=" + UPDATED_CREDIT_NOTE_REASON);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreditNoteReasonIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where creditNoteReason in DEFAULT_CREDIT_NOTE_REASON or UPDATED_CREDIT_NOTE_REASON
        defaultInvoicesShouldBeFound("creditNoteReason.in=" + DEFAULT_CREDIT_NOTE_REASON + "," + UPDATED_CREDIT_NOTE_REASON);

        // Get all the invoicesList where creditNoteReason equals to UPDATED_CREDIT_NOTE_REASON
        defaultInvoicesShouldNotBeFound("creditNoteReason.in=" + UPDATED_CREDIT_NOTE_REASON);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreditNoteReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where creditNoteReason is not null
        defaultInvoicesShouldBeFound("creditNoteReason.specified=true");

        // Get all the invoicesList where creditNoteReason is null
        defaultInvoicesShouldNotBeFound("creditNoteReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByCreditNoteReasonContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where creditNoteReason contains DEFAULT_CREDIT_NOTE_REASON
        defaultInvoicesShouldBeFound("creditNoteReason.contains=" + DEFAULT_CREDIT_NOTE_REASON);

        // Get all the invoicesList where creditNoteReason contains UPDATED_CREDIT_NOTE_REASON
        defaultInvoicesShouldNotBeFound("creditNoteReason.contains=" + UPDATED_CREDIT_NOTE_REASON);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCreditNoteReasonNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where creditNoteReason does not contain DEFAULT_CREDIT_NOTE_REASON
        defaultInvoicesShouldNotBeFound("creditNoteReason.doesNotContain=" + DEFAULT_CREDIT_NOTE_REASON);

        // Get all the invoicesList where creditNoteReason does not contain UPDATED_CREDIT_NOTE_REASON
        defaultInvoicesShouldBeFound("creditNoteReason.doesNotContain=" + UPDATED_CREDIT_NOTE_REASON);
    }


    @Test
    @Transactional
    public void getAllInvoicesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where comments equals to DEFAULT_COMMENTS
        defaultInvoicesShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the invoicesList where comments equals to UPDATED_COMMENTS
        defaultInvoicesShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where comments not equals to DEFAULT_COMMENTS
        defaultInvoicesShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the invoicesList where comments not equals to UPDATED_COMMENTS
        defaultInvoicesShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultInvoicesShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the invoicesList where comments equals to UPDATED_COMMENTS
        defaultInvoicesShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where comments is not null
        defaultInvoicesShouldBeFound("comments.specified=true");

        // Get all the invoicesList where comments is null
        defaultInvoicesShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByCommentsContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where comments contains DEFAULT_COMMENTS
        defaultInvoicesShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the invoicesList where comments contains UPDATED_COMMENTS
        defaultInvoicesShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where comments does not contain DEFAULT_COMMENTS
        defaultInvoicesShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the invoicesList where comments does not contain UPDATED_COMMENTS
        defaultInvoicesShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDeliveryInstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryInstructions equals to DEFAULT_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldBeFound("deliveryInstructions.equals=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the invoicesList where deliveryInstructions equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldNotBeFound("deliveryInstructions.equals=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryInstructionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryInstructions not equals to DEFAULT_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldNotBeFound("deliveryInstructions.notEquals=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the invoicesList where deliveryInstructions not equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldBeFound("deliveryInstructions.notEquals=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryInstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryInstructions in DEFAULT_DELIVERY_INSTRUCTIONS or UPDATED_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldBeFound("deliveryInstructions.in=" + DEFAULT_DELIVERY_INSTRUCTIONS + "," + UPDATED_DELIVERY_INSTRUCTIONS);

        // Get all the invoicesList where deliveryInstructions equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldNotBeFound("deliveryInstructions.in=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryInstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryInstructions is not null
        defaultInvoicesShouldBeFound("deliveryInstructions.specified=true");

        // Get all the invoicesList where deliveryInstructions is null
        defaultInvoicesShouldNotBeFound("deliveryInstructions.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByDeliveryInstructionsContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryInstructions contains DEFAULT_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldBeFound("deliveryInstructions.contains=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the invoicesList where deliveryInstructions contains UPDATED_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldNotBeFound("deliveryInstructions.contains=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryInstructionsNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryInstructions does not contain DEFAULT_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldNotBeFound("deliveryInstructions.doesNotContain=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the invoicesList where deliveryInstructions does not contain UPDATED_DELIVERY_INSTRUCTIONS
        defaultInvoicesShouldBeFound("deliveryInstructions.doesNotContain=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultInvoicesShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the invoicesList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultInvoicesShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInternalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where internalComments not equals to DEFAULT_INTERNAL_COMMENTS
        defaultInvoicesShouldNotBeFound("internalComments.notEquals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the invoicesList where internalComments not equals to UPDATED_INTERNAL_COMMENTS
        defaultInvoicesShouldBeFound("internalComments.notEquals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultInvoicesShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the invoicesList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultInvoicesShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where internalComments is not null
        defaultInvoicesShouldBeFound("internalComments.specified=true");

        // Get all the invoicesList where internalComments is null
        defaultInvoicesShouldNotBeFound("internalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByInternalCommentsContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where internalComments contains DEFAULT_INTERNAL_COMMENTS
        defaultInvoicesShouldBeFound("internalComments.contains=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the invoicesList where internalComments contains UPDATED_INTERNAL_COMMENTS
        defaultInvoicesShouldNotBeFound("internalComments.contains=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInternalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where internalComments does not contain DEFAULT_INTERNAL_COMMENTS
        defaultInvoicesShouldNotBeFound("internalComments.doesNotContain=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the invoicesList where internalComments does not contain UPDATED_INTERNAL_COMMENTS
        defaultInvoicesShouldBeFound("internalComments.doesNotContain=" + UPDATED_INTERNAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems equals to DEFAULT_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.equals=" + DEFAULT_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems equals to UPDATED_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.equals=" + UPDATED_TOTAL_DRY_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems not equals to DEFAULT_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.notEquals=" + DEFAULT_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems not equals to UPDATED_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.notEquals=" + UPDATED_TOTAL_DRY_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems in DEFAULT_TOTAL_DRY_ITEMS or UPDATED_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.in=" + DEFAULT_TOTAL_DRY_ITEMS + "," + UPDATED_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems equals to UPDATED_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.in=" + UPDATED_TOTAL_DRY_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems is not null
        defaultInvoicesShouldBeFound("totalDryItems.specified=true");

        // Get all the invoicesList where totalDryItems is null
        defaultInvoicesShouldNotBeFound("totalDryItems.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems is greater than or equal to DEFAULT_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.greaterThanOrEqual=" + DEFAULT_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems is greater than or equal to UPDATED_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.greaterThanOrEqual=" + UPDATED_TOTAL_DRY_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems is less than or equal to DEFAULT_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.lessThanOrEqual=" + DEFAULT_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems is less than or equal to SMALLER_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.lessThanOrEqual=" + SMALLER_TOTAL_DRY_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems is less than DEFAULT_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.lessThan=" + DEFAULT_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems is less than UPDATED_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.lessThan=" + UPDATED_TOTAL_DRY_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalDryItemsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalDryItems is greater than DEFAULT_TOTAL_DRY_ITEMS
        defaultInvoicesShouldNotBeFound("totalDryItems.greaterThan=" + DEFAULT_TOTAL_DRY_ITEMS);

        // Get all the invoicesList where totalDryItems is greater than SMALLER_TOTAL_DRY_ITEMS
        defaultInvoicesShouldBeFound("totalDryItems.greaterThan=" + SMALLER_TOTAL_DRY_ITEMS);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems equals to DEFAULT_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.equals=" + DEFAULT_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems equals to UPDATED_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.equals=" + UPDATED_TOTAL_CHILLER_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems not equals to DEFAULT_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.notEquals=" + DEFAULT_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems not equals to UPDATED_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.notEquals=" + UPDATED_TOTAL_CHILLER_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems in DEFAULT_TOTAL_CHILLER_ITEMS or UPDATED_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.in=" + DEFAULT_TOTAL_CHILLER_ITEMS + "," + UPDATED_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems equals to UPDATED_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.in=" + UPDATED_TOTAL_CHILLER_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems is not null
        defaultInvoicesShouldBeFound("totalChillerItems.specified=true");

        // Get all the invoicesList where totalChillerItems is null
        defaultInvoicesShouldNotBeFound("totalChillerItems.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems is greater than or equal to DEFAULT_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.greaterThanOrEqual=" + DEFAULT_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems is greater than or equal to UPDATED_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.greaterThanOrEqual=" + UPDATED_TOTAL_CHILLER_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems is less than or equal to DEFAULT_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.lessThanOrEqual=" + DEFAULT_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems is less than or equal to SMALLER_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.lessThanOrEqual=" + SMALLER_TOTAL_CHILLER_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems is less than DEFAULT_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.lessThan=" + DEFAULT_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems is less than UPDATED_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.lessThan=" + UPDATED_TOTAL_CHILLER_ITEMS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalChillerItemsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalChillerItems is greater than DEFAULT_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldNotBeFound("totalChillerItems.greaterThan=" + DEFAULT_TOTAL_CHILLER_ITEMS);

        // Get all the invoicesList where totalChillerItems is greater than SMALLER_TOTAL_CHILLER_ITEMS
        defaultInvoicesShouldBeFound("totalChillerItems.greaterThan=" + SMALLER_TOTAL_CHILLER_ITEMS);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDeliveryRunIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryRun equals to DEFAULT_DELIVERY_RUN
        defaultInvoicesShouldBeFound("deliveryRun.equals=" + DEFAULT_DELIVERY_RUN);

        // Get all the invoicesList where deliveryRun equals to UPDATED_DELIVERY_RUN
        defaultInvoicesShouldNotBeFound("deliveryRun.equals=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryRun not equals to DEFAULT_DELIVERY_RUN
        defaultInvoicesShouldNotBeFound("deliveryRun.notEquals=" + DEFAULT_DELIVERY_RUN);

        // Get all the invoicesList where deliveryRun not equals to UPDATED_DELIVERY_RUN
        defaultInvoicesShouldBeFound("deliveryRun.notEquals=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryRunIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryRun in DEFAULT_DELIVERY_RUN or UPDATED_DELIVERY_RUN
        defaultInvoicesShouldBeFound("deliveryRun.in=" + DEFAULT_DELIVERY_RUN + "," + UPDATED_DELIVERY_RUN);

        // Get all the invoicesList where deliveryRun equals to UPDATED_DELIVERY_RUN
        defaultInvoicesShouldNotBeFound("deliveryRun.in=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryRun is not null
        defaultInvoicesShouldBeFound("deliveryRun.specified=true");

        // Get all the invoicesList where deliveryRun is null
        defaultInvoicesShouldNotBeFound("deliveryRun.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByDeliveryRunContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryRun contains DEFAULT_DELIVERY_RUN
        defaultInvoicesShouldBeFound("deliveryRun.contains=" + DEFAULT_DELIVERY_RUN);

        // Get all the invoicesList where deliveryRun contains UPDATED_DELIVERY_RUN
        defaultInvoicesShouldNotBeFound("deliveryRun.contains=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDeliveryRunNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where deliveryRun does not contain DEFAULT_DELIVERY_RUN
        defaultInvoicesShouldNotBeFound("deliveryRun.doesNotContain=" + DEFAULT_DELIVERY_RUN);

        // Get all the invoicesList where deliveryRun does not contain UPDATED_DELIVERY_RUN
        defaultInvoicesShouldBeFound("deliveryRun.doesNotContain=" + UPDATED_DELIVERY_RUN);
    }


    @Test
    @Transactional
    public void getAllInvoicesByRunPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where runPosition equals to DEFAULT_RUN_POSITION
        defaultInvoicesShouldBeFound("runPosition.equals=" + DEFAULT_RUN_POSITION);

        // Get all the invoicesList where runPosition equals to UPDATED_RUN_POSITION
        defaultInvoicesShouldNotBeFound("runPosition.equals=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByRunPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where runPosition not equals to DEFAULT_RUN_POSITION
        defaultInvoicesShouldNotBeFound("runPosition.notEquals=" + DEFAULT_RUN_POSITION);

        // Get all the invoicesList where runPosition not equals to UPDATED_RUN_POSITION
        defaultInvoicesShouldBeFound("runPosition.notEquals=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByRunPositionIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where runPosition in DEFAULT_RUN_POSITION or UPDATED_RUN_POSITION
        defaultInvoicesShouldBeFound("runPosition.in=" + DEFAULT_RUN_POSITION + "," + UPDATED_RUN_POSITION);

        // Get all the invoicesList where runPosition equals to UPDATED_RUN_POSITION
        defaultInvoicesShouldNotBeFound("runPosition.in=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByRunPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where runPosition is not null
        defaultInvoicesShouldBeFound("runPosition.specified=true");

        // Get all the invoicesList where runPosition is null
        defaultInvoicesShouldNotBeFound("runPosition.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByRunPositionContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where runPosition contains DEFAULT_RUN_POSITION
        defaultInvoicesShouldBeFound("runPosition.contains=" + DEFAULT_RUN_POSITION);

        // Get all the invoicesList where runPosition contains UPDATED_RUN_POSITION
        defaultInvoicesShouldNotBeFound("runPosition.contains=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllInvoicesByRunPositionNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where runPosition does not contain DEFAULT_RUN_POSITION
        defaultInvoicesShouldNotBeFound("runPosition.doesNotContain=" + DEFAULT_RUN_POSITION);

        // Get all the invoicesList where runPosition does not contain UPDATED_RUN_POSITION
        defaultInvoicesShouldBeFound("runPosition.doesNotContain=" + UPDATED_RUN_POSITION);
    }


    @Test
    @Transactional
    public void getAllInvoicesByReturnedDeliveryDataIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where returnedDeliveryData equals to DEFAULT_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldBeFound("returnedDeliveryData.equals=" + DEFAULT_RETURNED_DELIVERY_DATA);

        // Get all the invoicesList where returnedDeliveryData equals to UPDATED_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldNotBeFound("returnedDeliveryData.equals=" + UPDATED_RETURNED_DELIVERY_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReturnedDeliveryDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where returnedDeliveryData not equals to DEFAULT_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldNotBeFound("returnedDeliveryData.notEquals=" + DEFAULT_RETURNED_DELIVERY_DATA);

        // Get all the invoicesList where returnedDeliveryData not equals to UPDATED_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldBeFound("returnedDeliveryData.notEquals=" + UPDATED_RETURNED_DELIVERY_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReturnedDeliveryDataIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where returnedDeliveryData in DEFAULT_RETURNED_DELIVERY_DATA or UPDATED_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldBeFound("returnedDeliveryData.in=" + DEFAULT_RETURNED_DELIVERY_DATA + "," + UPDATED_RETURNED_DELIVERY_DATA);

        // Get all the invoicesList where returnedDeliveryData equals to UPDATED_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldNotBeFound("returnedDeliveryData.in=" + UPDATED_RETURNED_DELIVERY_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReturnedDeliveryDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where returnedDeliveryData is not null
        defaultInvoicesShouldBeFound("returnedDeliveryData.specified=true");

        // Get all the invoicesList where returnedDeliveryData is null
        defaultInvoicesShouldNotBeFound("returnedDeliveryData.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByReturnedDeliveryDataContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where returnedDeliveryData contains DEFAULT_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldBeFound("returnedDeliveryData.contains=" + DEFAULT_RETURNED_DELIVERY_DATA);

        // Get all the invoicesList where returnedDeliveryData contains UPDATED_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldNotBeFound("returnedDeliveryData.contains=" + UPDATED_RETURNED_DELIVERY_DATA);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReturnedDeliveryDataNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where returnedDeliveryData does not contain DEFAULT_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldNotBeFound("returnedDeliveryData.doesNotContain=" + DEFAULT_RETURNED_DELIVERY_DATA);

        // Get all the invoicesList where returnedDeliveryData does not contain UPDATED_RETURNED_DELIVERY_DATA
        defaultInvoicesShouldBeFound("returnedDeliveryData.doesNotContain=" + UPDATED_RETURNED_DELIVERY_DATA);
    }


    @Test
    @Transactional
    public void getAllInvoicesByConfirmedDeliveryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedDeliveryTime equals to DEFAULT_CONFIRMED_DELIVERY_TIME
        defaultInvoicesShouldBeFound("confirmedDeliveryTime.equals=" + DEFAULT_CONFIRMED_DELIVERY_TIME);

        // Get all the invoicesList where confirmedDeliveryTime equals to UPDATED_CONFIRMED_DELIVERY_TIME
        defaultInvoicesShouldNotBeFound("confirmedDeliveryTime.equals=" + UPDATED_CONFIRMED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedDeliveryTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedDeliveryTime not equals to DEFAULT_CONFIRMED_DELIVERY_TIME
        defaultInvoicesShouldNotBeFound("confirmedDeliveryTime.notEquals=" + DEFAULT_CONFIRMED_DELIVERY_TIME);

        // Get all the invoicesList where confirmedDeliveryTime not equals to UPDATED_CONFIRMED_DELIVERY_TIME
        defaultInvoicesShouldBeFound("confirmedDeliveryTime.notEquals=" + UPDATED_CONFIRMED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedDeliveryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedDeliveryTime in DEFAULT_CONFIRMED_DELIVERY_TIME or UPDATED_CONFIRMED_DELIVERY_TIME
        defaultInvoicesShouldBeFound("confirmedDeliveryTime.in=" + DEFAULT_CONFIRMED_DELIVERY_TIME + "," + UPDATED_CONFIRMED_DELIVERY_TIME);

        // Get all the invoicesList where confirmedDeliveryTime equals to UPDATED_CONFIRMED_DELIVERY_TIME
        defaultInvoicesShouldNotBeFound("confirmedDeliveryTime.in=" + UPDATED_CONFIRMED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedDeliveryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedDeliveryTime is not null
        defaultInvoicesShouldBeFound("confirmedDeliveryTime.specified=true");

        // Get all the invoicesList where confirmedDeliveryTime is null
        defaultInvoicesShouldNotBeFound("confirmedDeliveryTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedReceivedByIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedReceivedBy equals to DEFAULT_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldBeFound("confirmedReceivedBy.equals=" + DEFAULT_CONFIRMED_RECEIVED_BY);

        // Get all the invoicesList where confirmedReceivedBy equals to UPDATED_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldNotBeFound("confirmedReceivedBy.equals=" + UPDATED_CONFIRMED_RECEIVED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedReceivedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedReceivedBy not equals to DEFAULT_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldNotBeFound("confirmedReceivedBy.notEquals=" + DEFAULT_CONFIRMED_RECEIVED_BY);

        // Get all the invoicesList where confirmedReceivedBy not equals to UPDATED_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldBeFound("confirmedReceivedBy.notEquals=" + UPDATED_CONFIRMED_RECEIVED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedReceivedByIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedReceivedBy in DEFAULT_CONFIRMED_RECEIVED_BY or UPDATED_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldBeFound("confirmedReceivedBy.in=" + DEFAULT_CONFIRMED_RECEIVED_BY + "," + UPDATED_CONFIRMED_RECEIVED_BY);

        // Get all the invoicesList where confirmedReceivedBy equals to UPDATED_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldNotBeFound("confirmedReceivedBy.in=" + UPDATED_CONFIRMED_RECEIVED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedReceivedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedReceivedBy is not null
        defaultInvoicesShouldBeFound("confirmedReceivedBy.specified=true");

        // Get all the invoicesList where confirmedReceivedBy is null
        defaultInvoicesShouldNotBeFound("confirmedReceivedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByConfirmedReceivedByContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedReceivedBy contains DEFAULT_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldBeFound("confirmedReceivedBy.contains=" + DEFAULT_CONFIRMED_RECEIVED_BY);

        // Get all the invoicesList where confirmedReceivedBy contains UPDATED_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldNotBeFound("confirmedReceivedBy.contains=" + UPDATED_CONFIRMED_RECEIVED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByConfirmedReceivedByNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where confirmedReceivedBy does not contain DEFAULT_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldNotBeFound("confirmedReceivedBy.doesNotContain=" + DEFAULT_CONFIRMED_RECEIVED_BY);

        // Get all the invoicesList where confirmedReceivedBy does not contain UPDATED_CONFIRMED_RECEIVED_BY
        defaultInvoicesShouldBeFound("confirmedReceivedBy.doesNotContain=" + UPDATED_CONFIRMED_RECEIVED_BY);
    }


    @Test
    @Transactional
    public void getAllInvoicesByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where paymentMethod equals to DEFAULT_PAYMENT_METHOD
        defaultInvoicesShouldBeFound("paymentMethod.equals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the invoicesList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultInvoicesShouldNotBeFound("paymentMethod.equals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where paymentMethod not equals to DEFAULT_PAYMENT_METHOD
        defaultInvoicesShouldNotBeFound("paymentMethod.notEquals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the invoicesList where paymentMethod not equals to UPDATED_PAYMENT_METHOD
        defaultInvoicesShouldBeFound("paymentMethod.notEquals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentMethodIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where paymentMethod in DEFAULT_PAYMENT_METHOD or UPDATED_PAYMENT_METHOD
        defaultInvoicesShouldBeFound("paymentMethod.in=" + DEFAULT_PAYMENT_METHOD + "," + UPDATED_PAYMENT_METHOD);

        // Get all the invoicesList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultInvoicesShouldNotBeFound("paymentMethod.in=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaymentMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where paymentMethod is not null
        defaultInvoicesShouldBeFound("paymentMethod.specified=true");

        // Get all the invoicesList where paymentMethod is null
        defaultInvoicesShouldNotBeFound("paymentMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where status equals to DEFAULT_STATUS
        defaultInvoicesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the invoicesList where status equals to UPDATED_STATUS
        defaultInvoicesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where status not equals to DEFAULT_STATUS
        defaultInvoicesShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the invoicesList where status not equals to UPDATED_STATUS
        defaultInvoicesShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultInvoicesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the invoicesList where status equals to UPDATED_STATUS
        defaultInvoicesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInvoicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where status is not null
        defaultInvoicesShouldBeFound("status.specified=true");

        // Get all the invoicesList where status is null
        defaultInvoicesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultInvoicesShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoicesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultInvoicesShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultInvoicesShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoicesList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultInvoicesShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultInvoicesShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the invoicesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultInvoicesShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedBy is not null
        defaultInvoicesShouldBeFound("lastEditedBy.specified=true");

        // Get all the invoicesList where lastEditedBy is null
        defaultInvoicesShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultInvoicesShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoicesList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultInvoicesShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultInvoicesShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoicesList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultInvoicesShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllInvoicesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultInvoicesShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the invoicesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultInvoicesShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultInvoicesShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the invoicesList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultInvoicesShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultInvoicesShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the invoicesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultInvoicesShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllInvoicesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastEditedWhen is not null
        defaultInvoicesShouldBeFound("lastEditedWhen.specified=true");

        // Get all the invoicesList where lastEditedWhen is null
        defaultInvoicesShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceLineListIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        InvoiceLines invoiceLineList = InvoiceLinesResourceIT.createEntity(em);
        em.persist(invoiceLineList);
        em.flush();
        invoices.addInvoiceLineList(invoiceLineList);
        invoicesRepository.saveAndFlush(invoices);
        Long invoiceLineListId = invoiceLineList.getId();

        // Get all the invoicesList where invoiceLineList equals to invoiceLineListId
        defaultInvoicesShouldBeFound("invoiceLineListId.equals=" + invoiceLineListId);

        // Get all the invoicesList where invoiceLineList equals to invoiceLineListId + 1
        defaultInvoicesShouldNotBeFound("invoiceLineListId.equals=" + (invoiceLineListId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        People contactPerson = PeopleResourceIT.createEntity(em);
        em.persist(contactPerson);
        em.flush();
        invoices.setContactPerson(contactPerson);
        invoicesRepository.saveAndFlush(invoices);
        Long contactPersonId = contactPerson.getId();

        // Get all the invoicesList where contactPerson equals to contactPersonId
        defaultInvoicesShouldBeFound("contactPersonId.equals=" + contactPersonId);

        // Get all the invoicesList where contactPerson equals to contactPersonId + 1
        defaultInvoicesShouldNotBeFound("contactPersonId.equals=" + (contactPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesBySalespersonPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        People salespersonPerson = PeopleResourceIT.createEntity(em);
        em.persist(salespersonPerson);
        em.flush();
        invoices.setSalespersonPerson(salespersonPerson);
        invoicesRepository.saveAndFlush(invoices);
        Long salespersonPersonId = salespersonPerson.getId();

        // Get all the invoicesList where salespersonPerson equals to salespersonPersonId
        defaultInvoicesShouldBeFound("salespersonPersonId.equals=" + salespersonPersonId);

        // Get all the invoicesList where salespersonPerson equals to salespersonPersonId + 1
        defaultInvoicesShouldNotBeFound("salespersonPersonId.equals=" + (salespersonPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByPackedByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        People packedByPerson = PeopleResourceIT.createEntity(em);
        em.persist(packedByPerson);
        em.flush();
        invoices.setPackedByPerson(packedByPerson);
        invoicesRepository.saveAndFlush(invoices);
        Long packedByPersonId = packedByPerson.getId();

        // Get all the invoicesList where packedByPerson equals to packedByPersonId
        defaultInvoicesShouldBeFound("packedByPersonId.equals=" + packedByPersonId);

        // Get all the invoicesList where packedByPerson equals to packedByPersonId + 1
        defaultInvoicesShouldNotBeFound("packedByPersonId.equals=" + (packedByPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByAccountsPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        People accountsPerson = PeopleResourceIT.createEntity(em);
        em.persist(accountsPerson);
        em.flush();
        invoices.setAccountsPerson(accountsPerson);
        invoicesRepository.saveAndFlush(invoices);
        Long accountsPersonId = accountsPerson.getId();

        // Get all the invoicesList where accountsPerson equals to accountsPersonId
        defaultInvoicesShouldBeFound("accountsPersonId.equals=" + accountsPersonId);

        // Get all the invoicesList where accountsPerson equals to accountsPersonId + 1
        defaultInvoicesShouldNotBeFound("accountsPersonId.equals=" + (accountsPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        invoices.setCustomer(customer);
        invoicesRepository.saveAndFlush(invoices);
        Long customerId = customer.getId();

        // Get all the invoicesList where customer equals to customerId
        defaultInvoicesShouldBeFound("customerId.equals=" + customerId);

        // Get all the invoicesList where customer equals to customerId + 1
        defaultInvoicesShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByBillToCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        Customers billToCustomer = CustomersResourceIT.createEntity(em);
        em.persist(billToCustomer);
        em.flush();
        invoices.setBillToCustomer(billToCustomer);
        invoicesRepository.saveAndFlush(invoices);
        Long billToCustomerId = billToCustomer.getId();

        // Get all the invoicesList where billToCustomer equals to billToCustomerId
        defaultInvoicesShouldBeFound("billToCustomerId.equals=" + billToCustomerId);

        // Get all the invoicesList where billToCustomer equals to billToCustomerId + 1
        defaultInvoicesShouldNotBeFound("billToCustomerId.equals=" + (billToCustomerId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        invoices.setDeliveryMethod(deliveryMethod);
        invoicesRepository.saveAndFlush(invoices);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the invoicesList where deliveryMethod equals to deliveryMethodId
        defaultInvoicesShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the invoicesList where deliveryMethod equals to deliveryMethodId + 1
        defaultInvoicesShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);
        Orders order = OrdersResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        invoices.setOrder(order);
        invoicesRepository.saveAndFlush(invoices);
        Long orderId = order.getId();

        // Get all the invoicesList where order equals to orderId
        defaultInvoicesShouldBeFound("orderId.equals=" + orderId);

        // Get all the invoicesList where order equals to orderId + 1
        defaultInvoicesShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoicesShouldBeFound(String filter) throws Exception {
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].isCreditNote").value(hasItem(DEFAULT_IS_CREDIT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].creditNoteReason").value(hasItem(DEFAULT_CREDIT_NOTE_REASON)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].totalDryItems").value(hasItem(DEFAULT_TOTAL_DRY_ITEMS)))
            .andExpect(jsonPath("$.[*].totalChillerItems").value(hasItem(DEFAULT_TOTAL_CHILLER_ITEMS)))
            .andExpect(jsonPath("$.[*].deliveryRun").value(hasItem(DEFAULT_DELIVERY_RUN)))
            .andExpect(jsonPath("$.[*].runPosition").value(hasItem(DEFAULT_RUN_POSITION)))
            .andExpect(jsonPath("$.[*].returnedDeliveryData").value(hasItem(DEFAULT_RETURNED_DELIVERY_DATA)))
            .andExpect(jsonPath("$.[*].confirmedDeliveryTime").value(hasItem(DEFAULT_CONFIRMED_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].confirmedReceivedBy").value(hasItem(DEFAULT_CONFIRMED_RECEIVED_BY)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restInvoicesMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoicesShouldNotBeFound(String filter) throws Exception {
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoicesMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvoices() throws Exception {
        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices
        Invoices updatedInvoices = invoicesRepository.findById(invoices.getId()).get();
        // Disconnect from session so that the updates on updatedInvoices are not directly saved in db
        em.detach(updatedInvoices);
        updatedInvoices
            .invoiceDate(UPDATED_INVOICE_DATE)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .isCreditNote(UPDATED_IS_CREDIT_NOTE)
            .creditNoteReason(UPDATED_CREDIT_NOTE_REASON)
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .totalDryItems(UPDATED_TOTAL_DRY_ITEMS)
            .totalChillerItems(UPDATED_TOTAL_CHILLER_ITEMS)
            .deliveryRun(UPDATED_DELIVERY_RUN)
            .runPosition(UPDATED_RUN_POSITION)
            .returnedDeliveryData(UPDATED_RETURNED_DELIVERY_DATA)
            .confirmedDeliveryTime(UPDATED_CONFIRMED_DELIVERY_TIME)
            .confirmedReceivedBy(UPDATED_CONFIRMED_RECEIVED_BY)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .status(UPDATED_STATUS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(updatedInvoices);

        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoices.getCustomerPurchaseOrderNumber()).isEqualTo(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
        assertThat(testInvoices.isIsCreditNote()).isEqualTo(UPDATED_IS_CREDIT_NOTE);
        assertThat(testInvoices.getCreditNoteReason()).isEqualTo(UPDATED_CREDIT_NOTE_REASON);
        assertThat(testInvoices.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInvoices.getDeliveryInstructions()).isEqualTo(UPDATED_DELIVERY_INSTRUCTIONS);
        assertThat(testInvoices.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testInvoices.getTotalDryItems()).isEqualTo(UPDATED_TOTAL_DRY_ITEMS);
        assertThat(testInvoices.getTotalChillerItems()).isEqualTo(UPDATED_TOTAL_CHILLER_ITEMS);
        assertThat(testInvoices.getDeliveryRun()).isEqualTo(UPDATED_DELIVERY_RUN);
        assertThat(testInvoices.getRunPosition()).isEqualTo(UPDATED_RUN_POSITION);
        assertThat(testInvoices.getReturnedDeliveryData()).isEqualTo(UPDATED_RETURNED_DELIVERY_DATA);
        assertThat(testInvoices.getConfirmedDeliveryTime()).isEqualTo(UPDATED_CONFIRMED_DELIVERY_TIME);
        assertThat(testInvoices.getConfirmedReceivedBy()).isEqualTo(UPDATED_CONFIRMED_RECEIVED_BY);
        assertThat(testInvoices.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testInvoices.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoices.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testInvoices.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeDelete = invoicesRepository.findAll().size();

        // Delete the invoices
        restInvoicesMockMvc.perform(delete("/api/invoices/{id}", invoices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
