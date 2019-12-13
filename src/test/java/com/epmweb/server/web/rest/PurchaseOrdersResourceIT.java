package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.PurchaseOrders;
import com.epmweb.server.domain.PurchaseOrderLines;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.Suppliers;
import com.epmweb.server.domain.DeliveryMethods;
import com.epmweb.server.repository.PurchaseOrdersRepository;
import com.epmweb.server.service.PurchaseOrdersService;
import com.epmweb.server.service.dto.PurchaseOrdersDTO;
import com.epmweb.server.service.mapper.PurchaseOrdersMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.PurchaseOrdersCriteria;
import com.epmweb.server.service.PurchaseOrdersQueryService;

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
 * Integration tests for the {@link PurchaseOrdersResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class PurchaseOrdersResourceIT {

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPECTED_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SUPPLIER_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_ORDER_FINALIZED = 1;
    private static final Integer UPDATED_IS_ORDER_FINALIZED = 2;
    private static final Integer SMALLER_IS_ORDER_FINALIZED = 1 - 1;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private PurchaseOrdersMapper purchaseOrdersMapper;

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;

    @Autowired
    private PurchaseOrdersQueryService purchaseOrdersQueryService;

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

    private MockMvc restPurchaseOrdersMockMvc;

    private PurchaseOrders purchaseOrders;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrdersResource purchaseOrdersResource = new PurchaseOrdersResource(purchaseOrdersService, purchaseOrdersQueryService);
        this.restPurchaseOrdersMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrdersResource)
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
    public static PurchaseOrders createEntity(EntityManager em) {
        PurchaseOrders purchaseOrders = new PurchaseOrders()
            .orderDate(DEFAULT_ORDER_DATE)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .supplierReference(DEFAULT_SUPPLIER_REFERENCE)
            .isOrderFinalized(DEFAULT_IS_ORDER_FINALIZED)
            .comments(DEFAULT_COMMENTS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return purchaseOrders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrders createUpdatedEntity(EntityManager em) {
        PurchaseOrders purchaseOrders = new PurchaseOrders()
            .orderDate(UPDATED_ORDER_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .isOrderFinalized(UPDATED_IS_ORDER_FINALIZED)
            .comments(UPDATED_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return purchaseOrders;
    }

    @BeforeEach
    public void initTest() {
        purchaseOrders = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrders() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);
        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrders testPurchaseOrders = purchaseOrdersList.get(purchaseOrdersList.size() - 1);
        assertThat(testPurchaseOrders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testPurchaseOrders.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrders.getSupplierReference()).isEqualTo(DEFAULT_SUPPLIER_REFERENCE);
        assertThat(testPurchaseOrders.getIsOrderFinalized()).isEqualTo(DEFAULT_IS_ORDER_FINALIZED);
        assertThat(testPurchaseOrders.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPurchaseOrders.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testPurchaseOrders.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testPurchaseOrders.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createPurchaseOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders with an existing ID
        purchaseOrders.setId(1L);
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrdersRepository.findAll().size();
        // set the field null
        purchaseOrders.setOrderDate(null);

        // Create the PurchaseOrders, which fails.
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsOrderFinalizedIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrdersRepository.findAll().size();
        // set the field null
        purchaseOrders.setIsOrderFinalized(null);

        // Create the PurchaseOrders, which fails.
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].isOrderFinalized").value(hasItem(DEFAULT_IS_ORDER_FINALIZED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrders.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.supplierReference").value(DEFAULT_SUPPLIER_REFERENCE))
            .andExpect(jsonPath("$.isOrderFinalized").value(DEFAULT_IS_ORDER_FINALIZED))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getPurchaseOrdersByIdFiltering() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        Long id = purchaseOrders.getId();

        defaultPurchaseOrdersShouldBeFound("id.equals=" + id);
        defaultPurchaseOrdersShouldNotBeFound("id.notEquals=" + id);

        defaultPurchaseOrdersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPurchaseOrdersShouldNotBeFound("id.greaterThan=" + id);

        defaultPurchaseOrdersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPurchaseOrdersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where orderDate equals to DEFAULT_ORDER_DATE
        defaultPurchaseOrdersShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the purchaseOrdersList where orderDate equals to UPDATED_ORDER_DATE
        defaultPurchaseOrdersShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where orderDate not equals to DEFAULT_ORDER_DATE
        defaultPurchaseOrdersShouldNotBeFound("orderDate.notEquals=" + DEFAULT_ORDER_DATE);

        // Get all the purchaseOrdersList where orderDate not equals to UPDATED_ORDER_DATE
        defaultPurchaseOrdersShouldBeFound("orderDate.notEquals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultPurchaseOrdersShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the purchaseOrdersList where orderDate equals to UPDATED_ORDER_DATE
        defaultPurchaseOrdersShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where orderDate is not null
        defaultPurchaseOrdersShouldBeFound("orderDate.specified=true");

        // Get all the purchaseOrdersList where orderDate is null
        defaultPurchaseOrdersShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByExpectedDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where expectedDeliveryDate equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrdersShouldBeFound("expectedDeliveryDate.equals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the purchaseOrdersList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrdersShouldNotBeFound("expectedDeliveryDate.equals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByExpectedDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where expectedDeliveryDate not equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrdersShouldNotBeFound("expectedDeliveryDate.notEquals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the purchaseOrdersList where expectedDeliveryDate not equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrdersShouldBeFound("expectedDeliveryDate.notEquals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByExpectedDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where expectedDeliveryDate in DEFAULT_EXPECTED_DELIVERY_DATE or UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrdersShouldBeFound("expectedDeliveryDate.in=" + DEFAULT_EXPECTED_DELIVERY_DATE + "," + UPDATED_EXPECTED_DELIVERY_DATE);

        // Get all the purchaseOrdersList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrdersShouldNotBeFound("expectedDeliveryDate.in=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByExpectedDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where expectedDeliveryDate is not null
        defaultPurchaseOrdersShouldBeFound("expectedDeliveryDate.specified=true");

        // Get all the purchaseOrdersList where expectedDeliveryDate is null
        defaultPurchaseOrdersShouldNotBeFound("expectedDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where supplierReference equals to DEFAULT_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldBeFound("supplierReference.equals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the purchaseOrdersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldNotBeFound("supplierReference.equals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where supplierReference not equals to DEFAULT_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldNotBeFound("supplierReference.notEquals=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the purchaseOrdersList where supplierReference not equals to UPDATED_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldBeFound("supplierReference.notEquals=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where supplierReference in DEFAULT_SUPPLIER_REFERENCE or UPDATED_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldBeFound("supplierReference.in=" + DEFAULT_SUPPLIER_REFERENCE + "," + UPDATED_SUPPLIER_REFERENCE);

        // Get all the purchaseOrdersList where supplierReference equals to UPDATED_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldNotBeFound("supplierReference.in=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where supplierReference is not null
        defaultPurchaseOrdersShouldBeFound("supplierReference.specified=true");

        // Get all the purchaseOrdersList where supplierReference is null
        defaultPurchaseOrdersShouldNotBeFound("supplierReference.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierReferenceContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where supplierReference contains DEFAULT_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldBeFound("supplierReference.contains=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the purchaseOrdersList where supplierReference contains UPDATED_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldNotBeFound("supplierReference.contains=" + UPDATED_SUPPLIER_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where supplierReference does not contain DEFAULT_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldNotBeFound("supplierReference.doesNotContain=" + DEFAULT_SUPPLIER_REFERENCE);

        // Get all the purchaseOrdersList where supplierReference does not contain UPDATED_SUPPLIER_REFERENCE
        defaultPurchaseOrdersShouldBeFound("supplierReference.doesNotContain=" + UPDATED_SUPPLIER_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized equals to DEFAULT_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.equals=" + DEFAULT_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized equals to UPDATED_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.equals=" + UPDATED_IS_ORDER_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized not equals to DEFAULT_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.notEquals=" + DEFAULT_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized not equals to UPDATED_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.notEquals=" + UPDATED_IS_ORDER_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized in DEFAULT_IS_ORDER_FINALIZED or UPDATED_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.in=" + DEFAULT_IS_ORDER_FINALIZED + "," + UPDATED_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized equals to UPDATED_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.in=" + UPDATED_IS_ORDER_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized is not null
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.specified=true");

        // Get all the purchaseOrdersList where isOrderFinalized is null
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized is greater than or equal to DEFAULT_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.greaterThanOrEqual=" + DEFAULT_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized is greater than or equal to UPDATED_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.greaterThanOrEqual=" + UPDATED_IS_ORDER_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized is less than or equal to DEFAULT_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.lessThanOrEqual=" + DEFAULT_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized is less than or equal to SMALLER_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.lessThanOrEqual=" + SMALLER_IS_ORDER_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized is less than DEFAULT_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.lessThan=" + DEFAULT_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized is less than UPDATED_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.lessThan=" + UPDATED_IS_ORDER_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIsOrderFinalizedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where isOrderFinalized is greater than DEFAULT_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldNotBeFound("isOrderFinalized.greaterThan=" + DEFAULT_IS_ORDER_FINALIZED);

        // Get all the purchaseOrdersList where isOrderFinalized is greater than SMALLER_IS_ORDER_FINALIZED
        defaultPurchaseOrdersShouldBeFound("isOrderFinalized.greaterThan=" + SMALLER_IS_ORDER_FINALIZED);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where comments equals to DEFAULT_COMMENTS
        defaultPurchaseOrdersShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the purchaseOrdersList where comments equals to UPDATED_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where comments not equals to DEFAULT_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the purchaseOrdersList where comments not equals to UPDATED_COMMENTS
        defaultPurchaseOrdersShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultPurchaseOrdersShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the purchaseOrdersList where comments equals to UPDATED_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where comments is not null
        defaultPurchaseOrdersShouldBeFound("comments.specified=true");

        // Get all the purchaseOrdersList where comments is null
        defaultPurchaseOrdersShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where comments contains DEFAULT_COMMENTS
        defaultPurchaseOrdersShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the purchaseOrdersList where comments contains UPDATED_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where comments does not contain DEFAULT_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the purchaseOrdersList where comments does not contain UPDATED_COMMENTS
        defaultPurchaseOrdersShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the purchaseOrdersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByInternalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where internalComments not equals to DEFAULT_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("internalComments.notEquals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the purchaseOrdersList where internalComments not equals to UPDATED_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldBeFound("internalComments.notEquals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the purchaseOrdersList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where internalComments is not null
        defaultPurchaseOrdersShouldBeFound("internalComments.specified=true");

        // Get all the purchaseOrdersList where internalComments is null
        defaultPurchaseOrdersShouldNotBeFound("internalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrdersByInternalCommentsContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where internalComments contains DEFAULT_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldBeFound("internalComments.contains=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the purchaseOrdersList where internalComments contains UPDATED_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("internalComments.contains=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByInternalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where internalComments does not contain DEFAULT_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldNotBeFound("internalComments.doesNotContain=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the purchaseOrdersList where internalComments does not contain UPDATED_INTERNAL_COMMENTS
        defaultPurchaseOrdersShouldBeFound("internalComments.doesNotContain=" + UPDATED_INTERNAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrdersShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrdersList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultPurchaseOrdersShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrdersShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrdersList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultPurchaseOrdersShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultPurchaseOrdersShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the purchaseOrdersList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultPurchaseOrdersShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedBy is not null
        defaultPurchaseOrdersShouldBeFound("lastEditedBy.specified=true");

        // Get all the purchaseOrdersList where lastEditedBy is null
        defaultPurchaseOrdersShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrdersShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrdersList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultPurchaseOrdersShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrdersShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrdersList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultPurchaseOrdersShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrdersShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrdersList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrdersShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrdersShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrdersList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrdersShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrdersShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the purchaseOrdersList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrdersShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList where lastEditedWhen is not null
        defaultPurchaseOrdersShouldBeFound("lastEditedWhen.specified=true");

        // Get all the purchaseOrdersList where lastEditedWhen is null
        defaultPurchaseOrdersShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByPurchaseOrderLineListIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        PurchaseOrderLines purchaseOrderLineList = PurchaseOrderLinesResourceIT.createEntity(em);
        em.persist(purchaseOrderLineList);
        em.flush();
        purchaseOrders.addPurchaseOrderLineList(purchaseOrderLineList);
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        Long purchaseOrderLineListId = purchaseOrderLineList.getId();

        // Get all the purchaseOrdersList where purchaseOrderLineList equals to purchaseOrderLineListId
        defaultPurchaseOrdersShouldBeFound("purchaseOrderLineListId.equals=" + purchaseOrderLineListId);

        // Get all the purchaseOrdersList where purchaseOrderLineList equals to purchaseOrderLineListId + 1
        defaultPurchaseOrdersShouldNotBeFound("purchaseOrderLineListId.equals=" + (purchaseOrderLineListId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        People contactPerson = PeopleResourceIT.createEntity(em);
        em.persist(contactPerson);
        em.flush();
        purchaseOrders.setContactPerson(contactPerson);
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        Long contactPersonId = contactPerson.getId();

        // Get all the purchaseOrdersList where contactPerson equals to contactPersonId
        defaultPurchaseOrdersShouldBeFound("contactPersonId.equals=" + contactPersonId);

        // Get all the purchaseOrdersList where contactPerson equals to contactPersonId + 1
        defaultPurchaseOrdersShouldNotBeFound("contactPersonId.equals=" + (contactPersonId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        purchaseOrders.setSupplier(supplier);
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        Long supplierId = supplier.getId();

        // Get all the purchaseOrdersList where supplier equals to supplierId
        defaultPurchaseOrdersShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the purchaseOrdersList where supplier equals to supplierId + 1
        defaultPurchaseOrdersShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        purchaseOrders.setDeliveryMethod(deliveryMethod);
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the purchaseOrdersList where deliveryMethod equals to deliveryMethodId
        defaultPurchaseOrdersShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the purchaseOrdersList where deliveryMethod equals to deliveryMethodId + 1
        defaultPurchaseOrdersShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPurchaseOrdersShouldBeFound(String filter) throws Exception {
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].supplierReference").value(hasItem(DEFAULT_SUPPLIER_REFERENCE)))
            .andExpect(jsonPath("$.[*].isOrderFinalized").value(hasItem(DEFAULT_IS_ORDER_FINALIZED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPurchaseOrdersShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPurchaseOrders() throws Exception {
        // Get the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        int databaseSizeBeforeUpdate = purchaseOrdersRepository.findAll().size();

        // Update the purchaseOrders
        PurchaseOrders updatedPurchaseOrders = purchaseOrdersRepository.findById(purchaseOrders.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrders are not directly saved in db
        em.detach(updatedPurchaseOrders);
        updatedPurchaseOrders
            .orderDate(UPDATED_ORDER_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .supplierReference(UPDATED_SUPPLIER_REFERENCE)
            .isOrderFinalized(UPDATED_IS_ORDER_FINALIZED)
            .comments(UPDATED_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(updatedPurchaseOrders);

        restPurchaseOrdersMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrders testPurchaseOrders = purchaseOrdersList.get(purchaseOrdersList.size() - 1);
        assertThat(testPurchaseOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testPurchaseOrders.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrders.getSupplierReference()).isEqualTo(UPDATED_SUPPLIER_REFERENCE);
        assertThat(testPurchaseOrders.getIsOrderFinalized()).isEqualTo(UPDATED_IS_ORDER_FINALIZED);
        assertThat(testPurchaseOrders.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPurchaseOrders.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testPurchaseOrders.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testPurchaseOrders.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrders() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders
        PurchaseOrdersDTO purchaseOrdersDTO = purchaseOrdersMapper.toDto(purchaseOrders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrdersMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrdersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        int databaseSizeBeforeDelete = purchaseOrdersRepository.findAll().size();

        // Delete the purchaseOrders
        restPurchaseOrdersMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
