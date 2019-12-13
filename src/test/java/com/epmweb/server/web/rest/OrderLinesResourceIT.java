package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.OrderLines;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.PackageTypes;
import com.epmweb.server.domain.Orders;
import com.epmweb.server.repository.OrderLinesRepository;
import com.epmweb.server.service.OrderLinesService;
import com.epmweb.server.service.dto.OrderLinesDTO;
import com.epmweb.server.service.mapper.OrderLinesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.OrderLinesCriteria;
import com.epmweb.server.service.OrderLinesQueryService;

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

import com.epmweb.server.domain.enumeration.OrderLineStatus;
/**
 * Integration tests for the {@link OrderLinesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class OrderLinesResourceIT {

    private static final String DEFAULT_CARRIER_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_UNIT_PRICE_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LINE_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LINE_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_LINE_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_RATE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PICKED_QUANTITY = 1;
    private static final Integer UPDATED_PICKED_QUANTITY = 2;
    private static final Integer SMALLER_PICKED_QUANTITY = 1 - 1;

    private static final Instant DEFAULT_PICKING_COMPLETED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKING_COMPLETED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OrderLineStatus DEFAULT_STATUS = OrderLineStatus.AVAILABLE;
    private static final OrderLineStatus UPDATED_STATUS = OrderLineStatus.OUT_OF_STOCK;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrderLinesRepository orderLinesRepository;

    @Autowired
    private OrderLinesMapper orderLinesMapper;

    @Autowired
    private OrderLinesService orderLinesService;

    @Autowired
    private OrderLinesQueryService orderLinesQueryService;

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

    private MockMvc restOrderLinesMockMvc;

    private OrderLines orderLines;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderLinesResource orderLinesResource = new OrderLinesResource(orderLinesService, orderLinesQueryService);
        this.restOrderLinesMockMvc = MockMvcBuilders.standaloneSetup(orderLinesResource)
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
    public static OrderLines createEntity(EntityManager em) {
        OrderLines orderLines = new OrderLines()
            .carrierTrackingNumber(DEFAULT_CARRIER_TRACKING_NUMBER)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .unitPriceDiscount(DEFAULT_UNIT_PRICE_DISCOUNT)
            .lineTotal(DEFAULT_LINE_TOTAL)
            .taxRate(DEFAULT_TAX_RATE)
            .pickedQuantity(DEFAULT_PICKED_QUANTITY)
            .pickingCompletedWhen(DEFAULT_PICKING_COMPLETED_WHEN)
            .status(DEFAULT_STATUS)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return orderLines;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderLines createUpdatedEntity(EntityManager em) {
        OrderLines orderLines = new OrderLines()
            .carrierTrackingNumber(UPDATED_CARRIER_TRACKING_NUMBER)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .unitPriceDiscount(UPDATED_UNIT_PRICE_DISCOUNT)
            .lineTotal(UPDATED_LINE_TOTAL)
            .taxRate(UPDATED_TAX_RATE)
            .pickedQuantity(UPDATED_PICKED_QUANTITY)
            .pickingCompletedWhen(UPDATED_PICKING_COMPLETED_WHEN)
            .status(UPDATED_STATUS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return orderLines;
    }

    @BeforeEach
    public void initTest() {
        orderLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderLines() throws Exception {
        int databaseSizeBeforeCreate = orderLinesRepository.findAll().size();

        // Create the OrderLines
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);
        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeCreate + 1);
        OrderLines testOrderLines = orderLinesList.get(orderLinesList.size() - 1);
        assertThat(testOrderLines.getCarrierTrackingNumber()).isEqualTo(DEFAULT_CARRIER_TRACKING_NUMBER);
        assertThat(testOrderLines.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderLines.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testOrderLines.getUnitPriceDiscount()).isEqualTo(DEFAULT_UNIT_PRICE_DISCOUNT);
        assertThat(testOrderLines.getLineTotal()).isEqualTo(DEFAULT_LINE_TOTAL);
        assertThat(testOrderLines.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testOrderLines.getPickedQuantity()).isEqualTo(DEFAULT_PICKED_QUANTITY);
        assertThat(testOrderLines.getPickingCompletedWhen()).isEqualTo(DEFAULT_PICKING_COMPLETED_WHEN);
        assertThat(testOrderLines.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderLines.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testOrderLines.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createOrderLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderLinesRepository.findAll().size();

        // Create the OrderLines with an existing ID
        orderLines.setId(1L);
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLinesRepository.findAll().size();
        // set the field null
        orderLines.setQuantity(null);

        // Create the OrderLines, which fails.
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderLinesRepository.findAll().size();
        // set the field null
        orderLines.setStatus(null);

        // Create the OrderLines, which fails.
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        restOrderLinesMockMvc.perform(post("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList
        restOrderLinesMockMvc.perform(get("/api/order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].carrierTrackingNumber").value(hasItem(DEFAULT_CARRIER_TRACKING_NUMBER)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].unitPriceDiscount").value(hasItem(DEFAULT_UNIT_PRICE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lineTotal").value(hasItem(DEFAULT_LINE_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())))
            .andExpect(jsonPath("$.[*].pickedQuantity").value(hasItem(DEFAULT_PICKED_QUANTITY)))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get the orderLines
        restOrderLinesMockMvc.perform(get("/api/order-lines/{id}", orderLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderLines.getId().intValue()))
            .andExpect(jsonPath("$.carrierTrackingNumber").value(DEFAULT_CARRIER_TRACKING_NUMBER))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.unitPriceDiscount").value(DEFAULT_UNIT_PRICE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.lineTotal").value(DEFAULT_LINE_TOTAL.intValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.intValue()))
            .andExpect(jsonPath("$.pickedQuantity").value(DEFAULT_PICKED_QUANTITY))
            .andExpect(jsonPath("$.pickingCompletedWhen").value(DEFAULT_PICKING_COMPLETED_WHEN.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getOrderLinesByIdFiltering() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        Long id = orderLines.getId();

        defaultOrderLinesShouldBeFound("id.equals=" + id);
        defaultOrderLinesShouldNotBeFound("id.notEquals=" + id);

        defaultOrderLinesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderLinesShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderLinesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderLinesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByCarrierTrackingNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where carrierTrackingNumber equals to DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldBeFound("carrierTrackingNumber.equals=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderLinesList where carrierTrackingNumber equals to UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldNotBeFound("carrierTrackingNumber.equals=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByCarrierTrackingNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where carrierTrackingNumber not equals to DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldNotBeFound("carrierTrackingNumber.notEquals=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderLinesList where carrierTrackingNumber not equals to UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldBeFound("carrierTrackingNumber.notEquals=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByCarrierTrackingNumberIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where carrierTrackingNumber in DEFAULT_CARRIER_TRACKING_NUMBER or UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldBeFound("carrierTrackingNumber.in=" + DEFAULT_CARRIER_TRACKING_NUMBER + "," + UPDATED_CARRIER_TRACKING_NUMBER);

        // Get all the orderLinesList where carrierTrackingNumber equals to UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldNotBeFound("carrierTrackingNumber.in=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByCarrierTrackingNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where carrierTrackingNumber is not null
        defaultOrderLinesShouldBeFound("carrierTrackingNumber.specified=true");

        // Get all the orderLinesList where carrierTrackingNumber is null
        defaultOrderLinesShouldNotBeFound("carrierTrackingNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderLinesByCarrierTrackingNumberContainsSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where carrierTrackingNumber contains DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldBeFound("carrierTrackingNumber.contains=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderLinesList where carrierTrackingNumber contains UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldNotBeFound("carrierTrackingNumber.contains=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByCarrierTrackingNumberNotContainsSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where carrierTrackingNumber does not contain DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldNotBeFound("carrierTrackingNumber.doesNotContain=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderLinesList where carrierTrackingNumber does not contain UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderLinesShouldBeFound("carrierTrackingNumber.doesNotContain=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity equals to DEFAULT_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the orderLinesList where quantity equals to UPDATED_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity not equals to DEFAULT_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the orderLinesList where quantity not equals to UPDATED_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the orderLinesList where quantity equals to UPDATED_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity is not null
        defaultOrderLinesShouldBeFound("quantity.specified=true");

        // Get all the orderLinesList where quantity is null
        defaultOrderLinesShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the orderLinesList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the orderLinesList where quantity is less than or equal to SMALLER_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity is less than DEFAULT_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the orderLinesList where quantity is less than UPDATED_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where quantity is greater than DEFAULT_QUANTITY
        defaultOrderLinesShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the orderLinesList where quantity is greater than SMALLER_QUANTITY
        defaultOrderLinesShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice is not null
        defaultOrderLinesShouldBeFound("unitPrice.specified=true");

        // Get all the orderLinesList where unitPrice is null
        defaultOrderLinesShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultOrderLinesShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the orderLinesList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultOrderLinesShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount equals to DEFAULT_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.equals=" + DEFAULT_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount equals to UPDATED_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.equals=" + UPDATED_UNIT_PRICE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount not equals to DEFAULT_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.notEquals=" + DEFAULT_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount not equals to UPDATED_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.notEquals=" + UPDATED_UNIT_PRICE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount in DEFAULT_UNIT_PRICE_DISCOUNT or UPDATED_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.in=" + DEFAULT_UNIT_PRICE_DISCOUNT + "," + UPDATED_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount equals to UPDATED_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.in=" + UPDATED_UNIT_PRICE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount is not null
        defaultOrderLinesShouldBeFound("unitPriceDiscount.specified=true");

        // Get all the orderLinesList where unitPriceDiscount is null
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount is greater than or equal to DEFAULT_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount is greater than or equal to UPDATED_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.greaterThanOrEqual=" + UPDATED_UNIT_PRICE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount is less than or equal to DEFAULT_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.lessThanOrEqual=" + DEFAULT_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount is less than or equal to SMALLER_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.lessThanOrEqual=" + SMALLER_UNIT_PRICE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount is less than DEFAULT_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.lessThan=" + DEFAULT_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount is less than UPDATED_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.lessThan=" + UPDATED_UNIT_PRICE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByUnitPriceDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where unitPriceDiscount is greater than DEFAULT_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldNotBeFound("unitPriceDiscount.greaterThan=" + DEFAULT_UNIT_PRICE_DISCOUNT);

        // Get all the orderLinesList where unitPriceDiscount is greater than SMALLER_UNIT_PRICE_DISCOUNT
        defaultOrderLinesShouldBeFound("unitPriceDiscount.greaterThan=" + SMALLER_UNIT_PRICE_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal equals to DEFAULT_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.equals=" + DEFAULT_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal equals to UPDATED_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.equals=" + UPDATED_LINE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal not equals to DEFAULT_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.notEquals=" + DEFAULT_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal not equals to UPDATED_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.notEquals=" + UPDATED_LINE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal in DEFAULT_LINE_TOTAL or UPDATED_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.in=" + DEFAULT_LINE_TOTAL + "," + UPDATED_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal equals to UPDATED_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.in=" + UPDATED_LINE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal is not null
        defaultOrderLinesShouldBeFound("lineTotal.specified=true");

        // Get all the orderLinesList where lineTotal is null
        defaultOrderLinesShouldNotBeFound("lineTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal is greater than or equal to DEFAULT_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.greaterThanOrEqual=" + DEFAULT_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal is greater than or equal to UPDATED_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.greaterThanOrEqual=" + UPDATED_LINE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal is less than or equal to DEFAULT_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.lessThanOrEqual=" + DEFAULT_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal is less than or equal to SMALLER_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.lessThanOrEqual=" + SMALLER_LINE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal is less than DEFAULT_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.lessThan=" + DEFAULT_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal is less than UPDATED_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.lessThan=" + UPDATED_LINE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLineTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lineTotal is greater than DEFAULT_LINE_TOTAL
        defaultOrderLinesShouldNotBeFound("lineTotal.greaterThan=" + DEFAULT_LINE_TOTAL);

        // Get all the orderLinesList where lineTotal is greater than SMALLER_LINE_TOTAL
        defaultOrderLinesShouldBeFound("lineTotal.greaterThan=" + SMALLER_LINE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate equals to DEFAULT_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.equals=" + DEFAULT_TAX_RATE);

        // Get all the orderLinesList where taxRate equals to UPDATED_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.equals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate not equals to DEFAULT_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.notEquals=" + DEFAULT_TAX_RATE);

        // Get all the orderLinesList where taxRate not equals to UPDATED_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.notEquals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate in DEFAULT_TAX_RATE or UPDATED_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.in=" + DEFAULT_TAX_RATE + "," + UPDATED_TAX_RATE);

        // Get all the orderLinesList where taxRate equals to UPDATED_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.in=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate is not null
        defaultOrderLinesShouldBeFound("taxRate.specified=true");

        // Get all the orderLinesList where taxRate is null
        defaultOrderLinesShouldNotBeFound("taxRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate is greater than or equal to DEFAULT_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.greaterThanOrEqual=" + DEFAULT_TAX_RATE);

        // Get all the orderLinesList where taxRate is greater than or equal to UPDATED_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.greaterThanOrEqual=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate is less than or equal to DEFAULT_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.lessThanOrEqual=" + DEFAULT_TAX_RATE);

        // Get all the orderLinesList where taxRate is less than or equal to SMALLER_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.lessThanOrEqual=" + SMALLER_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate is less than DEFAULT_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.lessThan=" + DEFAULT_TAX_RATE);

        // Get all the orderLinesList where taxRate is less than UPDATED_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.lessThan=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByTaxRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where taxRate is greater than DEFAULT_TAX_RATE
        defaultOrderLinesShouldNotBeFound("taxRate.greaterThan=" + DEFAULT_TAX_RATE);

        // Get all the orderLinesList where taxRate is greater than SMALLER_TAX_RATE
        defaultOrderLinesShouldBeFound("taxRate.greaterThan=" + SMALLER_TAX_RATE);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity equals to DEFAULT_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.equals=" + DEFAULT_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity equals to UPDATED_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.equals=" + UPDATED_PICKED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity not equals to DEFAULT_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.notEquals=" + DEFAULT_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity not equals to UPDATED_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.notEquals=" + UPDATED_PICKED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity in DEFAULT_PICKED_QUANTITY or UPDATED_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.in=" + DEFAULT_PICKED_QUANTITY + "," + UPDATED_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity equals to UPDATED_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.in=" + UPDATED_PICKED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity is not null
        defaultOrderLinesShouldBeFound("pickedQuantity.specified=true");

        // Get all the orderLinesList where pickedQuantity is null
        defaultOrderLinesShouldNotBeFound("pickedQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity is greater than or equal to DEFAULT_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.greaterThanOrEqual=" + DEFAULT_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity is greater than or equal to UPDATED_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.greaterThanOrEqual=" + UPDATED_PICKED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity is less than or equal to DEFAULT_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.lessThanOrEqual=" + DEFAULT_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity is less than or equal to SMALLER_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.lessThanOrEqual=" + SMALLER_PICKED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity is less than DEFAULT_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.lessThan=" + DEFAULT_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity is less than UPDATED_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.lessThan=" + UPDATED_PICKED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickedQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickedQuantity is greater than DEFAULT_PICKED_QUANTITY
        defaultOrderLinesShouldNotBeFound("pickedQuantity.greaterThan=" + DEFAULT_PICKED_QUANTITY);

        // Get all the orderLinesList where pickedQuantity is greater than SMALLER_PICKED_QUANTITY
        defaultOrderLinesShouldBeFound("pickedQuantity.greaterThan=" + SMALLER_PICKED_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByPickingCompletedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickingCompletedWhen equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrderLinesShouldBeFound("pickingCompletedWhen.equals=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the orderLinesList where pickingCompletedWhen equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderLinesShouldNotBeFound("pickingCompletedWhen.equals=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickingCompletedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickingCompletedWhen not equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrderLinesShouldNotBeFound("pickingCompletedWhen.notEquals=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the orderLinesList where pickingCompletedWhen not equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderLinesShouldBeFound("pickingCompletedWhen.notEquals=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickingCompletedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickingCompletedWhen in DEFAULT_PICKING_COMPLETED_WHEN or UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderLinesShouldBeFound("pickingCompletedWhen.in=" + DEFAULT_PICKING_COMPLETED_WHEN + "," + UPDATED_PICKING_COMPLETED_WHEN);

        // Get all the orderLinesList where pickingCompletedWhen equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderLinesShouldNotBeFound("pickingCompletedWhen.in=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByPickingCompletedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where pickingCompletedWhen is not null
        defaultOrderLinesShouldBeFound("pickingCompletedWhen.specified=true");

        // Get all the orderLinesList where pickingCompletedWhen is null
        defaultOrderLinesShouldNotBeFound("pickingCompletedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where status equals to DEFAULT_STATUS
        defaultOrderLinesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the orderLinesList where status equals to UPDATED_STATUS
        defaultOrderLinesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where status not equals to DEFAULT_STATUS
        defaultOrderLinesShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the orderLinesList where status not equals to UPDATED_STATUS
        defaultOrderLinesShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrderLinesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the orderLinesList where status equals to UPDATED_STATUS
        defaultOrderLinesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where status is not null
        defaultOrderLinesShouldBeFound("status.specified=true");

        // Get all the orderLinesList where status is null
        defaultOrderLinesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultOrderLinesShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultOrderLinesShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultOrderLinesShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderLinesList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultOrderLinesShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultOrderLinesShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the orderLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultOrderLinesShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedBy is not null
        defaultOrderLinesShouldBeFound("lastEditedBy.specified=true");

        // Get all the orderLinesList where lastEditedBy is null
        defaultOrderLinesShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderLinesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultOrderLinesShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderLinesList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultOrderLinesShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultOrderLinesShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderLinesList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultOrderLinesShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultOrderLinesShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the orderLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultOrderLinesShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultOrderLinesShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the orderLinesList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultOrderLinesShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultOrderLinesShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the orderLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultOrderLinesShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderLinesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        // Get all the orderLinesList where lastEditedWhen is not null
        defaultOrderLinesShouldBeFound("lastEditedWhen.specified=true");

        // Get all the orderLinesList where lastEditedWhen is null
        defaultOrderLinesShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderLinesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        orderLines.setStockItem(stockItem);
        orderLinesRepository.saveAndFlush(orderLines);
        Long stockItemId = stockItem.getId();

        // Get all the orderLinesList where stockItem equals to stockItemId
        defaultOrderLinesShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the orderLinesList where stockItem equals to stockItemId + 1
        defaultOrderLinesShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderLinesByPackageTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);
        PackageTypes packageType = PackageTypesResourceIT.createEntity(em);
        em.persist(packageType);
        em.flush();
        orderLines.setPackageType(packageType);
        orderLinesRepository.saveAndFlush(orderLines);
        Long packageTypeId = packageType.getId();

        // Get all the orderLinesList where packageType equals to packageTypeId
        defaultOrderLinesShouldBeFound("packageTypeId.equals=" + packageTypeId);

        // Get all the orderLinesList where packageType equals to packageTypeId + 1
        defaultOrderLinesShouldNotBeFound("packageTypeId.equals=" + (packageTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderLinesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);
        Orders order = OrdersResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        orderLines.setOrder(order);
        orderLinesRepository.saveAndFlush(orderLines);
        Long orderId = order.getId();

        // Get all the orderLinesList where order equals to orderId
        defaultOrderLinesShouldBeFound("orderId.equals=" + orderId);

        // Get all the orderLinesList where order equals to orderId + 1
        defaultOrderLinesShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderLinesShouldBeFound(String filter) throws Exception {
        restOrderLinesMockMvc.perform(get("/api/order-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].carrierTrackingNumber").value(hasItem(DEFAULT_CARRIER_TRACKING_NUMBER)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].unitPriceDiscount").value(hasItem(DEFAULT_UNIT_PRICE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lineTotal").value(hasItem(DEFAULT_LINE_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())))
            .andExpect(jsonPath("$.[*].pickedQuantity").value(hasItem(DEFAULT_PICKED_QUANTITY)))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restOrderLinesMockMvc.perform(get("/api/order-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderLinesShouldNotBeFound(String filter) throws Exception {
        restOrderLinesMockMvc.perform(get("/api/order-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderLinesMockMvc.perform(get("/api/order-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrderLines() throws Exception {
        // Get the orderLines
        restOrderLinesMockMvc.perform(get("/api/order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        int databaseSizeBeforeUpdate = orderLinesRepository.findAll().size();

        // Update the orderLines
        OrderLines updatedOrderLines = orderLinesRepository.findById(orderLines.getId()).get();
        // Disconnect from session so that the updates on updatedOrderLines are not directly saved in db
        em.detach(updatedOrderLines);
        updatedOrderLines
            .carrierTrackingNumber(UPDATED_CARRIER_TRACKING_NUMBER)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .unitPriceDiscount(UPDATED_UNIT_PRICE_DISCOUNT)
            .lineTotal(UPDATED_LINE_TOTAL)
            .taxRate(UPDATED_TAX_RATE)
            .pickedQuantity(UPDATED_PICKED_QUANTITY)
            .pickingCompletedWhen(UPDATED_PICKING_COMPLETED_WHEN)
            .status(UPDATED_STATUS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(updatedOrderLines);

        restOrderLinesMockMvc.perform(put("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isOk());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeUpdate);
        OrderLines testOrderLines = orderLinesList.get(orderLinesList.size() - 1);
        assertThat(testOrderLines.getCarrierTrackingNumber()).isEqualTo(UPDATED_CARRIER_TRACKING_NUMBER);
        assertThat(testOrderLines.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderLines.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderLines.getUnitPriceDiscount()).isEqualTo(UPDATED_UNIT_PRICE_DISCOUNT);
        assertThat(testOrderLines.getLineTotal()).isEqualTo(UPDATED_LINE_TOTAL);
        assertThat(testOrderLines.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testOrderLines.getPickedQuantity()).isEqualTo(UPDATED_PICKED_QUANTITY);
        assertThat(testOrderLines.getPickingCompletedWhen()).isEqualTo(UPDATED_PICKING_COMPLETED_WHEN);
        assertThat(testOrderLines.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderLines.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testOrderLines.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderLines() throws Exception {
        int databaseSizeBeforeUpdate = orderLinesRepository.findAll().size();

        // Create the OrderLines
        OrderLinesDTO orderLinesDTO = orderLinesMapper.toDto(orderLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderLinesMockMvc.perform(put("/api/order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderLines in the database
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderLines() throws Exception {
        // Initialize the database
        orderLinesRepository.saveAndFlush(orderLines);

        int databaseSizeBeforeDelete = orderLinesRepository.findAll().size();

        // Delete the orderLines
        restOrderLinesMockMvc.perform(delete("/api/order-lines/{id}", orderLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderLines> orderLinesList = orderLinesRepository.findAll();
        assertThat(orderLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
