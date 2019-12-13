package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.InvoiceLines;
import com.epmweb.server.domain.PackageTypes;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.Invoices;
import com.epmweb.server.repository.InvoiceLinesRepository;
import com.epmweb.server.service.InvoiceLinesService;
import com.epmweb.server.service.dto.InvoiceLinesDTO;
import com.epmweb.server.service.mapper.InvoiceLinesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.InvoiceLinesCriteria;
import com.epmweb.server.service.InvoiceLinesQueryService;

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
 * Integration tests for the {@link InvoiceLinesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class InvoiceLinesResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LINE_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LINE_PROFIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_LINE_PROFIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTENDED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTENDED_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InvoiceLinesRepository invoiceLinesRepository;

    @Autowired
    private InvoiceLinesMapper invoiceLinesMapper;

    @Autowired
    private InvoiceLinesService invoiceLinesService;

    @Autowired
    private InvoiceLinesQueryService invoiceLinesQueryService;

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

    private MockMvc restInvoiceLinesMockMvc;

    private InvoiceLines invoiceLines;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceLinesResource invoiceLinesResource = new InvoiceLinesResource(invoiceLinesService, invoiceLinesQueryService);
        this.restInvoiceLinesMockMvc = MockMvcBuilders.standaloneSetup(invoiceLinesResource)
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
    public static InvoiceLines createEntity(EntityManager em) {
        InvoiceLines invoiceLines = new InvoiceLines()
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .taxRate(DEFAULT_TAX_RATE)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .lineProfit(DEFAULT_LINE_PROFIT)
            .extendedPrice(DEFAULT_EXTENDED_PRICE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return invoiceLines;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLines createUpdatedEntity(EntityManager em) {
        InvoiceLines invoiceLines = new InvoiceLines()
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .taxRate(UPDATED_TAX_RATE)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .lineProfit(UPDATED_LINE_PROFIT)
            .extendedPrice(UPDATED_EXTENDED_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return invoiceLines;
    }

    @BeforeEach
    public void initTest() {
        invoiceLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceLines() throws Exception {
        int databaseSizeBeforeCreate = invoiceLinesRepository.findAll().size();

        // Create the InvoiceLines
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);
        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLines testInvoiceLines = invoiceLinesList.get(invoiceLinesList.size() - 1);
        assertThat(testInvoiceLines.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceLines.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceLines.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testInvoiceLines.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testInvoiceLines.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testInvoiceLines.getLineProfit()).isEqualTo(DEFAULT_LINE_PROFIT);
        assertThat(testInvoiceLines.getExtendedPrice()).isEqualTo(DEFAULT_EXTENDED_PRICE);
        assertThat(testInvoiceLines.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testInvoiceLines.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createInvoiceLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceLinesRepository.findAll().size();

        // Create the InvoiceLines with an existing ID
        invoiceLines.setId(1L);
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setDescription(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setQuantity(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setTaxRate(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setTaxAmount(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLineProfitIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setLineProfit(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtendedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLinesRepository.findAll().size();
        // set the field null
        invoiceLines.setExtendedPrice(null);

        // Create the InvoiceLines, which fails.
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        restInvoiceLinesMockMvc.perform(post("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lineProfit").value(hasItem(DEFAULT_LINE_PROFIT.intValue())))
            .andExpect(jsonPath("$.[*].extendedPrice").value(hasItem(DEFAULT_EXTENDED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get the invoiceLines
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines/{id}", invoiceLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLines.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.lineProfit").value(DEFAULT_LINE_PROFIT.intValue()))
            .andExpect(jsonPath("$.extendedPrice").value(DEFAULT_EXTENDED_PRICE.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getInvoiceLinesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        Long id = invoiceLines.getId();

        defaultInvoiceLinesShouldBeFound("id.equals=" + id);
        defaultInvoiceLinesShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceLinesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceLinesShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceLinesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceLinesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where description equals to DEFAULT_DESCRIPTION
        defaultInvoiceLinesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceLinesList where description equals to UPDATED_DESCRIPTION
        defaultInvoiceLinesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where description not equals to DEFAULT_DESCRIPTION
        defaultInvoiceLinesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceLinesList where description not equals to UPDATED_DESCRIPTION
        defaultInvoiceLinesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInvoiceLinesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the invoiceLinesList where description equals to UPDATED_DESCRIPTION
        defaultInvoiceLinesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where description is not null
        defaultInvoiceLinesShouldBeFound("description.specified=true");

        // Get all the invoiceLinesList where description is null
        defaultInvoiceLinesShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoiceLinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where description contains DEFAULT_DESCRIPTION
        defaultInvoiceLinesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceLinesList where description contains UPDATED_DESCRIPTION
        defaultInvoiceLinesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where description does not contain DEFAULT_DESCRIPTION
        defaultInvoiceLinesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the invoiceLinesList where description does not contain UPDATED_DESCRIPTION
        defaultInvoiceLinesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity equals to DEFAULT_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the invoiceLinesList where quantity equals to UPDATED_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity not equals to DEFAULT_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the invoiceLinesList where quantity not equals to UPDATED_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the invoiceLinesList where quantity equals to UPDATED_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity is not null
        defaultInvoiceLinesShouldBeFound("quantity.specified=true");

        // Get all the invoiceLinesList where quantity is null
        defaultInvoiceLinesShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the invoiceLinesList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the invoiceLinesList where quantity is less than or equal to SMALLER_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity is less than DEFAULT_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the invoiceLinesList where quantity is less than UPDATED_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where quantity is greater than DEFAULT_QUANTITY
        defaultInvoiceLinesShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the invoiceLinesList where quantity is greater than SMALLER_QUANTITY
        defaultInvoiceLinesShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice is not null
        defaultInvoiceLinesShouldBeFound("unitPrice.specified=true");

        // Get all the invoiceLinesList where unitPrice is null
        defaultInvoiceLinesShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultInvoiceLinesShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the invoiceLinesList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultInvoiceLinesShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate equals to DEFAULT_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.equals=" + DEFAULT_TAX_RATE);

        // Get all the invoiceLinesList where taxRate equals to UPDATED_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.equals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate not equals to DEFAULT_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.notEquals=" + DEFAULT_TAX_RATE);

        // Get all the invoiceLinesList where taxRate not equals to UPDATED_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.notEquals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate in DEFAULT_TAX_RATE or UPDATED_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.in=" + DEFAULT_TAX_RATE + "," + UPDATED_TAX_RATE);

        // Get all the invoiceLinesList where taxRate equals to UPDATED_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.in=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate is not null
        defaultInvoiceLinesShouldBeFound("taxRate.specified=true");

        // Get all the invoiceLinesList where taxRate is null
        defaultInvoiceLinesShouldNotBeFound("taxRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate is greater than or equal to DEFAULT_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.greaterThanOrEqual=" + DEFAULT_TAX_RATE);

        // Get all the invoiceLinesList where taxRate is greater than or equal to UPDATED_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.greaterThanOrEqual=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate is less than or equal to DEFAULT_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.lessThanOrEqual=" + DEFAULT_TAX_RATE);

        // Get all the invoiceLinesList where taxRate is less than or equal to SMALLER_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.lessThanOrEqual=" + SMALLER_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate is less than DEFAULT_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.lessThan=" + DEFAULT_TAX_RATE);

        // Get all the invoiceLinesList where taxRate is less than UPDATED_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.lessThan=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxRate is greater than DEFAULT_TAX_RATE
        defaultInvoiceLinesShouldNotBeFound("taxRate.greaterThan=" + DEFAULT_TAX_RATE);

        // Get all the invoiceLinesList where taxRate is greater than SMALLER_TAX_RATE
        defaultInvoiceLinesShouldBeFound("taxRate.greaterThan=" + SMALLER_TAX_RATE);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount is not null
        defaultInvoiceLinesShouldBeFound("taxAmount.specified=true");

        // Get all the invoiceLinesList where taxAmount is null
        defaultInvoiceLinesShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultInvoiceLinesShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the invoiceLinesList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultInvoiceLinesShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit equals to DEFAULT_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.equals=" + DEFAULT_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit equals to UPDATED_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.equals=" + UPDATED_LINE_PROFIT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit not equals to DEFAULT_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.notEquals=" + DEFAULT_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit not equals to UPDATED_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.notEquals=" + UPDATED_LINE_PROFIT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit in DEFAULT_LINE_PROFIT or UPDATED_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.in=" + DEFAULT_LINE_PROFIT + "," + UPDATED_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit equals to UPDATED_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.in=" + UPDATED_LINE_PROFIT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit is not null
        defaultInvoiceLinesShouldBeFound("lineProfit.specified=true");

        // Get all the invoiceLinesList where lineProfit is null
        defaultInvoiceLinesShouldNotBeFound("lineProfit.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit is greater than or equal to DEFAULT_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.greaterThanOrEqual=" + DEFAULT_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit is greater than or equal to UPDATED_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.greaterThanOrEqual=" + UPDATED_LINE_PROFIT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit is less than or equal to DEFAULT_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.lessThanOrEqual=" + DEFAULT_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit is less than or equal to SMALLER_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.lessThanOrEqual=" + SMALLER_LINE_PROFIT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit is less than DEFAULT_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.lessThan=" + DEFAULT_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit is less than UPDATED_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.lessThan=" + UPDATED_LINE_PROFIT);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLineProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lineProfit is greater than DEFAULT_LINE_PROFIT
        defaultInvoiceLinesShouldNotBeFound("lineProfit.greaterThan=" + DEFAULT_LINE_PROFIT);

        // Get all the invoiceLinesList where lineProfit is greater than SMALLER_LINE_PROFIT
        defaultInvoiceLinesShouldBeFound("lineProfit.greaterThan=" + SMALLER_LINE_PROFIT);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice equals to DEFAULT_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.equals=" + DEFAULT_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice equals to UPDATED_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.equals=" + UPDATED_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice not equals to DEFAULT_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.notEquals=" + DEFAULT_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice not equals to UPDATED_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.notEquals=" + UPDATED_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice in DEFAULT_EXTENDED_PRICE or UPDATED_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.in=" + DEFAULT_EXTENDED_PRICE + "," + UPDATED_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice equals to UPDATED_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.in=" + UPDATED_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice is not null
        defaultInvoiceLinesShouldBeFound("extendedPrice.specified=true");

        // Get all the invoiceLinesList where extendedPrice is null
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice is greater than or equal to DEFAULT_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.greaterThanOrEqual=" + DEFAULT_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice is greater than or equal to UPDATED_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.greaterThanOrEqual=" + UPDATED_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice is less than or equal to DEFAULT_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.lessThanOrEqual=" + DEFAULT_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice is less than or equal to SMALLER_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.lessThanOrEqual=" + SMALLER_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice is less than DEFAULT_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.lessThan=" + DEFAULT_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice is less than UPDATED_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.lessThan=" + UPDATED_EXTENDED_PRICE);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByExtendedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where extendedPrice is greater than DEFAULT_EXTENDED_PRICE
        defaultInvoiceLinesShouldNotBeFound("extendedPrice.greaterThan=" + DEFAULT_EXTENDED_PRICE);

        // Get all the invoiceLinesList where extendedPrice is greater than SMALLER_EXTENDED_PRICE
        defaultInvoiceLinesShouldBeFound("extendedPrice.greaterThan=" + SMALLER_EXTENDED_PRICE);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultInvoiceLinesShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoiceLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultInvoiceLinesShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultInvoiceLinesShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoiceLinesList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultInvoiceLinesShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultInvoiceLinesShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the invoiceLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultInvoiceLinesShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedBy is not null
        defaultInvoiceLinesShouldBeFound("lastEditedBy.specified=true");

        // Get all the invoiceLinesList where lastEditedBy is null
        defaultInvoiceLinesShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultInvoiceLinesShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoiceLinesList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultInvoiceLinesShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultInvoiceLinesShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the invoiceLinesList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultInvoiceLinesShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultInvoiceLinesShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the invoiceLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultInvoiceLinesShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultInvoiceLinesShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the invoiceLinesList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultInvoiceLinesShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultInvoiceLinesShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the invoiceLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultInvoiceLinesShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        // Get all the invoiceLinesList where lastEditedWhen is not null
        defaultInvoiceLinesShouldBeFound("lastEditedWhen.specified=true");

        // Get all the invoiceLinesList where lastEditedWhen is null
        defaultInvoiceLinesShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceLinesByPackageTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);
        PackageTypes packageType = PackageTypesResourceIT.createEntity(em);
        em.persist(packageType);
        em.flush();
        invoiceLines.setPackageType(packageType);
        invoiceLinesRepository.saveAndFlush(invoiceLines);
        Long packageTypeId = packageType.getId();

        // Get all the invoiceLinesList where packageType equals to packageTypeId
        defaultInvoiceLinesShouldBeFound("packageTypeId.equals=" + packageTypeId);

        // Get all the invoiceLinesList where packageType equals to packageTypeId + 1
        defaultInvoiceLinesShouldNotBeFound("packageTypeId.equals=" + (packageTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        invoiceLines.setStockItem(stockItem);
        invoiceLinesRepository.saveAndFlush(invoiceLines);
        Long stockItemId = stockItem.getId();

        // Get all the invoiceLinesList where stockItem equals to stockItemId
        defaultInvoiceLinesShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the invoiceLinesList where stockItem equals to stockItemId + 1
        defaultInvoiceLinesShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceLinesByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);
        Invoices invoice = InvoicesResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        invoiceLines.setInvoice(invoice);
        invoiceLinesRepository.saveAndFlush(invoiceLines);
        Long invoiceId = invoice.getId();

        // Get all the invoiceLinesList where invoice equals to invoiceId
        defaultInvoiceLinesShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the invoiceLinesList where invoice equals to invoiceId + 1
        defaultInvoiceLinesShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceLinesShouldBeFound(String filter) throws Exception {
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lineProfit").value(hasItem(DEFAULT_LINE_PROFIT.intValue())))
            .andExpect(jsonPath("$.[*].extendedPrice").value(hasItem(DEFAULT_EXTENDED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceLinesShouldNotBeFound(String filter) throws Exception {
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvoiceLines() throws Exception {
        // Get the invoiceLines
        restInvoiceLinesMockMvc.perform(get("/api/invoice-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        int databaseSizeBeforeUpdate = invoiceLinesRepository.findAll().size();

        // Update the invoiceLines
        InvoiceLines updatedInvoiceLines = invoiceLinesRepository.findById(invoiceLines.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceLines are not directly saved in db
        em.detach(updatedInvoiceLines);
        updatedInvoiceLines
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .taxRate(UPDATED_TAX_RATE)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .lineProfit(UPDATED_LINE_PROFIT)
            .extendedPrice(UPDATED_EXTENDED_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(updatedInvoiceLines);

        restInvoiceLinesMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isOk());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLines testInvoiceLines = invoiceLinesList.get(invoiceLinesList.size() - 1);
        assertThat(testInvoiceLines.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLines.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLines.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testInvoiceLines.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testInvoiceLines.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testInvoiceLines.getLineProfit()).isEqualTo(UPDATED_LINE_PROFIT);
        assertThat(testInvoiceLines.getExtendedPrice()).isEqualTo(UPDATED_EXTENDED_PRICE);
        assertThat(testInvoiceLines.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testInvoiceLines.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceLines() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLinesRepository.findAll().size();

        // Create the InvoiceLines
        InvoiceLinesDTO invoiceLinesDTO = invoiceLinesMapper.toDto(invoiceLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLinesMockMvc.perform(put("/api/invoice-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLines in the database
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceLines() throws Exception {
        // Initialize the database
        invoiceLinesRepository.saveAndFlush(invoiceLines);

        int databaseSizeBeforeDelete = invoiceLinesRepository.findAll().size();

        // Delete the invoiceLines
        restInvoiceLinesMockMvc.perform(delete("/api/invoice-lines/{id}", invoiceLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceLines> invoiceLinesList = invoiceLinesRepository.findAll();
        assertThat(invoiceLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
