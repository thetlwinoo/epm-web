package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.ReviewLines;
import com.epmweb.server.domain.Photos;
import com.epmweb.server.domain.DangerousGoods;
import com.epmweb.server.domain.SpecialDeals;
import com.epmweb.server.domain.UnitMeasure;
import com.epmweb.server.domain.ProductAttribute;
import com.epmweb.server.domain.ProductOption;
import com.epmweb.server.domain.Materials;
import com.epmweb.server.domain.Currency;
import com.epmweb.server.domain.BarcodeTypes;
import com.epmweb.server.domain.StockItemHoldings;
import com.epmweb.server.domain.Products;
import com.epmweb.server.repository.StockItemsRepository;
import com.epmweb.server.service.StockItemsService;
import com.epmweb.server.service.dto.StockItemsDTO;
import com.epmweb.server.service.mapper.StockItemsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.StockItemsCriteria;
import com.epmweb.server.service.StockItemsQueryService;

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
 * Integration tests for the {@link StockItemsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StockItemsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_SKU = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_GENERATED_SKU = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RECOMMENDED_RETAIL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECOMMENDED_RETAIL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RECOMMENDED_RETAIL_PRICE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 1;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 2;
    private static final Integer SMALLER_QUANTITY_ON_HAND = 1 - 1;

    private static final Integer DEFAULT_ITEM_LENGTH = 1;
    private static final Integer UPDATED_ITEM_LENGTH = 2;
    private static final Integer SMALLER_ITEM_LENGTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_WIDTH = 1;
    private static final Integer UPDATED_ITEM_WIDTH = 2;
    private static final Integer SMALLER_ITEM_WIDTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_HEIGHT = 1;
    private static final Integer UPDATED_ITEM_HEIGHT = 2;
    private static final Integer SMALLER_ITEM_HEIGHT = 1 - 1;

    private static final BigDecimal DEFAULT_ITEM_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ITEM_WEIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ITEM_WEIGHT = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_ITEM_PACKAGE_LENGTH = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_LENGTH = 2;
    private static final Integer SMALLER_ITEM_PACKAGE_LENGTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_PACKAGE_WIDTH = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_WIDTH = 2;
    private static final Integer SMALLER_ITEM_PACKAGE_WIDTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_PACKAGE_HEIGHT = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_HEIGHT = 2;
    private static final Integer SMALLER_ITEM_PACKAGE_HEIGHT = 1 - 1;

    private static final BigDecimal DEFAULT_ITEM_PACKAGE_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ITEM_PACKAGE_WEIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ITEM_PACKAGE_WEIGHT = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_NO_OF_PIECES = 1;
    private static final Integer UPDATED_NO_OF_PIECES = 2;
    private static final Integer SMALLER_NO_OF_PIECES = 1 - 1;

    private static final Integer DEFAULT_NO_OF_ITEMS = 1;
    private static final Integer UPDATED_NO_OF_ITEMS = 2;
    private static final Integer SMALLER_NO_OF_ITEMS = 1 - 1;

    private static final String DEFAULT_MANUFACTURE = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURE = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETING_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_MARKETING_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SELL_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SELL_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;
    private static final Integer SMALLER_SELL_COUNT = 1 - 1;

    private static final String DEFAULT_CUSTOM_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StockItemsRepository stockItemsRepository;

    @Autowired
    private StockItemsMapper stockItemsMapper;

    @Autowired
    private StockItemsService stockItemsService;

    @Autowired
    private StockItemsQueryService stockItemsQueryService;

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

    private MockMvc restStockItemsMockMvc;

    private StockItems stockItems;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemsResource stockItemsResource = new StockItemsResource(stockItemsService, stockItemsQueryService);
        this.restStockItemsMockMvc = MockMvcBuilders.standaloneSetup(stockItemsResource)
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
    public static StockItems createEntity(EntityManager em) {
        StockItems stockItems = new StockItems()
            .name(DEFAULT_NAME)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorSKU(DEFAULT_VENDOR_SKU)
            .generatedSKU(DEFAULT_GENERATED_SKU)
            .barcode(DEFAULT_BARCODE)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .recommendedRetailPrice(DEFAULT_RECOMMENDED_RETAIL_PRICE)
            .quantityOnHand(DEFAULT_QUANTITY_ON_HAND)
            .itemLength(DEFAULT_ITEM_LENGTH)
            .itemWidth(DEFAULT_ITEM_WIDTH)
            .itemHeight(DEFAULT_ITEM_HEIGHT)
            .itemWeight(DEFAULT_ITEM_WEIGHT)
            .itemPackageLength(DEFAULT_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(DEFAULT_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(DEFAULT_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(DEFAULT_ITEM_PACKAGE_WEIGHT)
            .noOfPieces(DEFAULT_NO_OF_PIECES)
            .noOfItems(DEFAULT_NO_OF_ITEMS)
            .manufacture(DEFAULT_MANUFACTURE)
            .marketingComments(DEFAULT_MARKETING_COMMENTS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .sellStartDate(DEFAULT_SELL_START_DATE)
            .sellEndDate(DEFAULT_SELL_END_DATE)
            .sellCount(DEFAULT_SELL_COUNT)
            .customFields(DEFAULT_CUSTOM_FIELDS)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL)
            .activeInd(DEFAULT_ACTIVE_IND)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return stockItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItems createUpdatedEntity(EntityManager em) {
        StockItems stockItems = new StockItems()
            .name(UPDATED_NAME)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorSKU(UPDATED_VENDOR_SKU)
            .generatedSKU(UPDATED_GENERATED_SKU)
            .barcode(UPDATED_BARCODE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .recommendedRetailPrice(UPDATED_RECOMMENDED_RETAIL_PRICE)
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .itemLength(UPDATED_ITEM_LENGTH)
            .itemWidth(UPDATED_ITEM_WIDTH)
            .itemHeight(UPDATED_ITEM_HEIGHT)
            .itemWeight(UPDATED_ITEM_WEIGHT)
            .itemPackageLength(UPDATED_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(UPDATED_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(UPDATED_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(UPDATED_ITEM_PACKAGE_WEIGHT)
            .noOfPieces(UPDATED_NO_OF_PIECES)
            .noOfItems(UPDATED_NO_OF_ITEMS)
            .manufacture(UPDATED_MANUFACTURE)
            .marketingComments(UPDATED_MARKETING_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .sellCount(UPDATED_SELL_COUNT)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .activeInd(UPDATED_ACTIVE_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return stockItems;
    }

    @BeforeEach
    public void initTest() {
        stockItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItems() throws Exception {
        int databaseSizeBeforeCreate = stockItemsRepository.findAll().size();

        // Create the StockItems
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);
        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItems testStockItems = stockItemsList.get(stockItemsList.size() - 1);
        assertThat(testStockItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockItems.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testStockItems.getVendorSKU()).isEqualTo(DEFAULT_VENDOR_SKU);
        assertThat(testStockItems.getGeneratedSKU()).isEqualTo(DEFAULT_GENERATED_SKU);
        assertThat(testStockItems.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testStockItems.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testStockItems.getRecommendedRetailPrice()).isEqualTo(DEFAULT_RECOMMENDED_RETAIL_PRICE);
        assertThat(testStockItems.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testStockItems.getItemLength()).isEqualTo(DEFAULT_ITEM_LENGTH);
        assertThat(testStockItems.getItemWidth()).isEqualTo(DEFAULT_ITEM_WIDTH);
        assertThat(testStockItems.getItemHeight()).isEqualTo(DEFAULT_ITEM_HEIGHT);
        assertThat(testStockItems.getItemWeight()).isEqualTo(DEFAULT_ITEM_WEIGHT);
        assertThat(testStockItems.getItemPackageLength()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH);
        assertThat(testStockItems.getItemPackageWidth()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH);
        assertThat(testStockItems.getItemPackageHeight()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT);
        assertThat(testStockItems.getItemPackageWeight()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT);
        assertThat(testStockItems.getNoOfPieces()).isEqualTo(DEFAULT_NO_OF_PIECES);
        assertThat(testStockItems.getNoOfItems()).isEqualTo(DEFAULT_NO_OF_ITEMS);
        assertThat(testStockItems.getManufacture()).isEqualTo(DEFAULT_MANUFACTURE);
        assertThat(testStockItems.getMarketingComments()).isEqualTo(DEFAULT_MARKETING_COMMENTS);
        assertThat(testStockItems.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testStockItems.getSellStartDate()).isEqualTo(DEFAULT_SELL_START_DATE);
        assertThat(testStockItems.getSellEndDate()).isEqualTo(DEFAULT_SELL_END_DATE);
        assertThat(testStockItems.getSellCount()).isEqualTo(DEFAULT_SELL_COUNT);
        assertThat(testStockItems.getCustomFields()).isEqualTo(DEFAULT_CUSTOM_FIELDS);
        assertThat(testStockItems.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
        assertThat(testStockItems.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testStockItems.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testStockItems.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createStockItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemsRepository.findAll().size();

        // Create the StockItems with an existing ID
        stockItems.setId(1L);
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsRepository.findAll().size();
        // set the field null
        stockItems.setName(null);

        // Create the StockItems, which fails.
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsRepository.findAll().size();
        // set the field null
        stockItems.setUnitPrice(null);

        // Create the StockItems, which fails.
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityOnHandIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemsRepository.findAll().size();
        // set the field null
        stockItems.setQuantityOnHand(null);

        // Create the StockItems, which fails.
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        restStockItemsMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList
        restStockItemsMockMvc.perform(get("/api/stock-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorSKU").value(hasItem(DEFAULT_VENDOR_SKU)))
            .andExpect(jsonPath("$.[*].generatedSKU").value(hasItem(DEFAULT_GENERATED_SKU)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].recommendedRetailPrice").value(hasItem(DEFAULT_RECOMMENDED_RETAIL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].itemLength").value(hasItem(DEFAULT_ITEM_LENGTH)))
            .andExpect(jsonPath("$.[*].itemWidth").value(hasItem(DEFAULT_ITEM_WIDTH)))
            .andExpect(jsonPath("$.[*].itemHeight").value(hasItem(DEFAULT_ITEM_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemWeight").value(hasItem(DEFAULT_ITEM_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLength").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH)))
            .andExpect(jsonPath("$.[*].itemPackageWidth").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH)))
            .andExpect(jsonPath("$.[*].itemPackageHeight").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemPackageWeight").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].noOfPieces").value(hasItem(DEFAULT_NO_OF_PIECES)))
            .andExpect(jsonPath("$.[*].noOfItems").value(hasItem(DEFAULT_NO_OF_ITEMS)))
            .andExpect(jsonPath("$.[*].manufacture").value(hasItem(DEFAULT_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].marketingComments").value(hasItem(DEFAULT_MARKETING_COMMENTS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get the stockItems
        restStockItemsMockMvc.perform(get("/api/stock-items/{id}", stockItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItems.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorSKU").value(DEFAULT_VENDOR_SKU))
            .andExpect(jsonPath("$.generatedSKU").value(DEFAULT_GENERATED_SKU))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.recommendedRetailPrice").value(DEFAULT_RECOMMENDED_RETAIL_PRICE.intValue()))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.itemLength").value(DEFAULT_ITEM_LENGTH))
            .andExpect(jsonPath("$.itemWidth").value(DEFAULT_ITEM_WIDTH))
            .andExpect(jsonPath("$.itemHeight").value(DEFAULT_ITEM_HEIGHT))
            .andExpect(jsonPath("$.itemWeight").value(DEFAULT_ITEM_WEIGHT.intValue()))
            .andExpect(jsonPath("$.itemPackageLength").value(DEFAULT_ITEM_PACKAGE_LENGTH))
            .andExpect(jsonPath("$.itemPackageWidth").value(DEFAULT_ITEM_PACKAGE_WIDTH))
            .andExpect(jsonPath("$.itemPackageHeight").value(DEFAULT_ITEM_PACKAGE_HEIGHT))
            .andExpect(jsonPath("$.itemPackageWeight").value(DEFAULT_ITEM_PACKAGE_WEIGHT.intValue()))
            .andExpect(jsonPath("$.noOfPieces").value(DEFAULT_NO_OF_PIECES))
            .andExpect(jsonPath("$.noOfItems").value(DEFAULT_NO_OF_ITEMS))
            .andExpect(jsonPath("$.manufacture").value(DEFAULT_MANUFACTURE))
            .andExpect(jsonPath("$.marketingComments").value(DEFAULT_MARKETING_COMMENTS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.sellStartDate").value(DEFAULT_SELL_START_DATE.toString()))
            .andExpect(jsonPath("$.sellEndDate").value(DEFAULT_SELL_END_DATE.toString()))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.customFields").value(DEFAULT_CUSTOM_FIELDS))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getStockItemsByIdFiltering() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        Long id = stockItems.getId();

        defaultStockItemsShouldBeFound("id.equals=" + id);
        defaultStockItemsShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemsShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where name equals to DEFAULT_NAME
        defaultStockItemsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stockItemsList where name equals to UPDATED_NAME
        defaultStockItemsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where name not equals to DEFAULT_NAME
        defaultStockItemsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stockItemsList where name not equals to UPDATED_NAME
        defaultStockItemsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStockItemsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stockItemsList where name equals to UPDATED_NAME
        defaultStockItemsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where name is not null
        defaultStockItemsShouldBeFound("name.specified=true");

        // Get all the stockItemsList where name is null
        defaultStockItemsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where name contains DEFAULT_NAME
        defaultStockItemsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stockItemsList where name contains UPDATED_NAME
        defaultStockItemsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where name does not contain DEFAULT_NAME
        defaultStockItemsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stockItemsList where name does not contain UPDATED_NAME
        defaultStockItemsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemsByVendorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorCode equals to DEFAULT_VENDOR_CODE
        defaultStockItemsShouldBeFound("vendorCode.equals=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemsList where vendorCode equals to UPDATED_VENDOR_CODE
        defaultStockItemsShouldNotBeFound("vendorCode.equals=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorCode not equals to DEFAULT_VENDOR_CODE
        defaultStockItemsShouldNotBeFound("vendorCode.notEquals=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemsList where vendorCode not equals to UPDATED_VENDOR_CODE
        defaultStockItemsShouldBeFound("vendorCode.notEquals=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorCode in DEFAULT_VENDOR_CODE or UPDATED_VENDOR_CODE
        defaultStockItemsShouldBeFound("vendorCode.in=" + DEFAULT_VENDOR_CODE + "," + UPDATED_VENDOR_CODE);

        // Get all the stockItemsList where vendorCode equals to UPDATED_VENDOR_CODE
        defaultStockItemsShouldNotBeFound("vendorCode.in=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorCode is not null
        defaultStockItemsShouldBeFound("vendorCode.specified=true");

        // Get all the stockItemsList where vendorCode is null
        defaultStockItemsShouldNotBeFound("vendorCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByVendorCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorCode contains DEFAULT_VENDOR_CODE
        defaultStockItemsShouldBeFound("vendorCode.contains=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemsList where vendorCode contains UPDATED_VENDOR_CODE
        defaultStockItemsShouldNotBeFound("vendorCode.contains=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorCode does not contain DEFAULT_VENDOR_CODE
        defaultStockItemsShouldNotBeFound("vendorCode.doesNotContain=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemsList where vendorCode does not contain UPDATED_VENDOR_CODE
        defaultStockItemsShouldBeFound("vendorCode.doesNotContain=" + UPDATED_VENDOR_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemsByVendorSKUIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorSKU equals to DEFAULT_VENDOR_SKU
        defaultStockItemsShouldBeFound("vendorSKU.equals=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemsList where vendorSKU equals to UPDATED_VENDOR_SKU
        defaultStockItemsShouldNotBeFound("vendorSKU.equals=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorSKUIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorSKU not equals to DEFAULT_VENDOR_SKU
        defaultStockItemsShouldNotBeFound("vendorSKU.notEquals=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemsList where vendorSKU not equals to UPDATED_VENDOR_SKU
        defaultStockItemsShouldBeFound("vendorSKU.notEquals=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorSKUIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorSKU in DEFAULT_VENDOR_SKU or UPDATED_VENDOR_SKU
        defaultStockItemsShouldBeFound("vendorSKU.in=" + DEFAULT_VENDOR_SKU + "," + UPDATED_VENDOR_SKU);

        // Get all the stockItemsList where vendorSKU equals to UPDATED_VENDOR_SKU
        defaultStockItemsShouldNotBeFound("vendorSKU.in=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorSKUIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorSKU is not null
        defaultStockItemsShouldBeFound("vendorSKU.specified=true");

        // Get all the stockItemsList where vendorSKU is null
        defaultStockItemsShouldNotBeFound("vendorSKU.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByVendorSKUContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorSKU contains DEFAULT_VENDOR_SKU
        defaultStockItemsShouldBeFound("vendorSKU.contains=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemsList where vendorSKU contains UPDATED_VENDOR_SKU
        defaultStockItemsShouldNotBeFound("vendorSKU.contains=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByVendorSKUNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where vendorSKU does not contain DEFAULT_VENDOR_SKU
        defaultStockItemsShouldNotBeFound("vendorSKU.doesNotContain=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemsList where vendorSKU does not contain UPDATED_VENDOR_SKU
        defaultStockItemsShouldBeFound("vendorSKU.doesNotContain=" + UPDATED_VENDOR_SKU);
    }


    @Test
    @Transactional
    public void getAllStockItemsByGeneratedSKUIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where generatedSKU equals to DEFAULT_GENERATED_SKU
        defaultStockItemsShouldBeFound("generatedSKU.equals=" + DEFAULT_GENERATED_SKU);

        // Get all the stockItemsList where generatedSKU equals to UPDATED_GENERATED_SKU
        defaultStockItemsShouldNotBeFound("generatedSKU.equals=" + UPDATED_GENERATED_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByGeneratedSKUIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where generatedSKU not equals to DEFAULT_GENERATED_SKU
        defaultStockItemsShouldNotBeFound("generatedSKU.notEquals=" + DEFAULT_GENERATED_SKU);

        // Get all the stockItemsList where generatedSKU not equals to UPDATED_GENERATED_SKU
        defaultStockItemsShouldBeFound("generatedSKU.notEquals=" + UPDATED_GENERATED_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByGeneratedSKUIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where generatedSKU in DEFAULT_GENERATED_SKU or UPDATED_GENERATED_SKU
        defaultStockItemsShouldBeFound("generatedSKU.in=" + DEFAULT_GENERATED_SKU + "," + UPDATED_GENERATED_SKU);

        // Get all the stockItemsList where generatedSKU equals to UPDATED_GENERATED_SKU
        defaultStockItemsShouldNotBeFound("generatedSKU.in=" + UPDATED_GENERATED_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByGeneratedSKUIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where generatedSKU is not null
        defaultStockItemsShouldBeFound("generatedSKU.specified=true");

        // Get all the stockItemsList where generatedSKU is null
        defaultStockItemsShouldNotBeFound("generatedSKU.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByGeneratedSKUContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where generatedSKU contains DEFAULT_GENERATED_SKU
        defaultStockItemsShouldBeFound("generatedSKU.contains=" + DEFAULT_GENERATED_SKU);

        // Get all the stockItemsList where generatedSKU contains UPDATED_GENERATED_SKU
        defaultStockItemsShouldNotBeFound("generatedSKU.contains=" + UPDATED_GENERATED_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemsByGeneratedSKUNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where generatedSKU does not contain DEFAULT_GENERATED_SKU
        defaultStockItemsShouldNotBeFound("generatedSKU.doesNotContain=" + DEFAULT_GENERATED_SKU);

        // Get all the stockItemsList where generatedSKU does not contain UPDATED_GENERATED_SKU
        defaultStockItemsShouldBeFound("generatedSKU.doesNotContain=" + UPDATED_GENERATED_SKU);
    }


    @Test
    @Transactional
    public void getAllStockItemsByBarcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where barcode equals to DEFAULT_BARCODE
        defaultStockItemsShouldBeFound("barcode.equals=" + DEFAULT_BARCODE);

        // Get all the stockItemsList where barcode equals to UPDATED_BARCODE
        defaultStockItemsShouldNotBeFound("barcode.equals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByBarcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where barcode not equals to DEFAULT_BARCODE
        defaultStockItemsShouldNotBeFound("barcode.notEquals=" + DEFAULT_BARCODE);

        // Get all the stockItemsList where barcode not equals to UPDATED_BARCODE
        defaultStockItemsShouldBeFound("barcode.notEquals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByBarcodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where barcode in DEFAULT_BARCODE or UPDATED_BARCODE
        defaultStockItemsShouldBeFound("barcode.in=" + DEFAULT_BARCODE + "," + UPDATED_BARCODE);

        // Get all the stockItemsList where barcode equals to UPDATED_BARCODE
        defaultStockItemsShouldNotBeFound("barcode.in=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByBarcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where barcode is not null
        defaultStockItemsShouldBeFound("barcode.specified=true");

        // Get all the stockItemsList where barcode is null
        defaultStockItemsShouldNotBeFound("barcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByBarcodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where barcode contains DEFAULT_BARCODE
        defaultStockItemsShouldBeFound("barcode.contains=" + DEFAULT_BARCODE);

        // Get all the stockItemsList where barcode contains UPDATED_BARCODE
        defaultStockItemsShouldNotBeFound("barcode.contains=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByBarcodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where barcode does not contain DEFAULT_BARCODE
        defaultStockItemsShouldNotBeFound("barcode.doesNotContain=" + DEFAULT_BARCODE);

        // Get all the stockItemsList where barcode does not contain UPDATED_BARCODE
        defaultStockItemsShouldBeFound("barcode.doesNotContain=" + UPDATED_BARCODE);
    }


    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice is not null
        defaultStockItemsShouldBeFound("unitPrice.specified=true");

        // Get all the stockItemsList where unitPrice is null
        defaultStockItemsShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultStockItemsShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemsList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultStockItemsShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice equals to DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.equals=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice equals to UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.equals=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice not equals to DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.notEquals=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice not equals to UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.notEquals=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice in DEFAULT_RECOMMENDED_RETAIL_PRICE or UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.in=" + DEFAULT_RECOMMENDED_RETAIL_PRICE + "," + UPDATED_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice equals to UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.in=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice is not null
        defaultStockItemsShouldBeFound("recommendedRetailPrice.specified=true");

        // Get all the stockItemsList where recommendedRetailPrice is null
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice is greater than or equal to DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.greaterThanOrEqual=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice is greater than or equal to UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.greaterThanOrEqual=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice is less than or equal to DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.lessThanOrEqual=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice is less than or equal to SMALLER_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.lessThanOrEqual=" + SMALLER_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice is less than DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.lessThan=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice is less than UPDATED_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.lessThan=" + UPDATED_RECOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByRecommendedRetailPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where recommendedRetailPrice is greater than DEFAULT_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldNotBeFound("recommendedRetailPrice.greaterThan=" + DEFAULT_RECOMMENDED_RETAIL_PRICE);

        // Get all the stockItemsList where recommendedRetailPrice is greater than SMALLER_RECOMMENDED_RETAIL_PRICE
        defaultStockItemsShouldBeFound("recommendedRetailPrice.greaterThan=" + SMALLER_RECOMMENDED_RETAIL_PRICE);
    }


    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.equals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.equals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand not equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.notEquals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand not equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.notEquals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand in DEFAULT_QUANTITY_ON_HAND or UPDATED_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.in=" + DEFAULT_QUANTITY_ON_HAND + "," + UPDATED_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.in=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand is not null
        defaultStockItemsShouldBeFound("quantityOnHand.specified=true");

        // Get all the stockItemsList where quantityOnHand is null
        defaultStockItemsShouldNotBeFound("quantityOnHand.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand is greater than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.greaterThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand is greater than or equal to UPDATED_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.greaterThanOrEqual=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand is less than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.lessThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand is less than or equal to SMALLER_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.lessThanOrEqual=" + SMALLER_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand is less than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.lessThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand is less than UPDATED_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.lessThan=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByQuantityOnHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where quantityOnHand is greater than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemsShouldNotBeFound("quantityOnHand.greaterThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemsList where quantityOnHand is greater than SMALLER_QUANTITY_ON_HAND
        defaultStockItemsShouldBeFound("quantityOnHand.greaterThan=" + SMALLER_QUANTITY_ON_HAND);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength equals to DEFAULT_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.equals=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength equals to UPDATED_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.equals=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength not equals to DEFAULT_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.notEquals=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength not equals to UPDATED_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.notEquals=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength in DEFAULT_ITEM_LENGTH or UPDATED_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.in=" + DEFAULT_ITEM_LENGTH + "," + UPDATED_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength equals to UPDATED_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.in=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength is not null
        defaultStockItemsShouldBeFound("itemLength.specified=true");

        // Get all the stockItemsList where itemLength is null
        defaultStockItemsShouldNotBeFound("itemLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength is greater than or equal to DEFAULT_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.greaterThanOrEqual=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength is greater than or equal to UPDATED_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.greaterThanOrEqual=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength is less than or equal to DEFAULT_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.lessThanOrEqual=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength is less than or equal to SMALLER_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.lessThanOrEqual=" + SMALLER_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength is less than DEFAULT_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.lessThan=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength is less than UPDATED_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.lessThan=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemLength is greater than DEFAULT_ITEM_LENGTH
        defaultStockItemsShouldNotBeFound("itemLength.greaterThan=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemsList where itemLength is greater than SMALLER_ITEM_LENGTH
        defaultStockItemsShouldBeFound("itemLength.greaterThan=" + SMALLER_ITEM_LENGTH);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth equals to DEFAULT_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.equals=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth equals to UPDATED_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.equals=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth not equals to DEFAULT_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.notEquals=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth not equals to UPDATED_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.notEquals=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth in DEFAULT_ITEM_WIDTH or UPDATED_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.in=" + DEFAULT_ITEM_WIDTH + "," + UPDATED_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth equals to UPDATED_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.in=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth is not null
        defaultStockItemsShouldBeFound("itemWidth.specified=true");

        // Get all the stockItemsList where itemWidth is null
        defaultStockItemsShouldNotBeFound("itemWidth.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth is greater than or equal to DEFAULT_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.greaterThanOrEqual=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth is greater than or equal to UPDATED_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.greaterThanOrEqual=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth is less than or equal to DEFAULT_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.lessThanOrEqual=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth is less than or equal to SMALLER_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.lessThanOrEqual=" + SMALLER_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth is less than DEFAULT_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.lessThan=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth is less than UPDATED_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.lessThan=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWidth is greater than DEFAULT_ITEM_WIDTH
        defaultStockItemsShouldNotBeFound("itemWidth.greaterThan=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemsList where itemWidth is greater than SMALLER_ITEM_WIDTH
        defaultStockItemsShouldBeFound("itemWidth.greaterThan=" + SMALLER_ITEM_WIDTH);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight equals to DEFAULT_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.equals=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight equals to UPDATED_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.equals=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight not equals to DEFAULT_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.notEquals=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight not equals to UPDATED_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.notEquals=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight in DEFAULT_ITEM_HEIGHT or UPDATED_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.in=" + DEFAULT_ITEM_HEIGHT + "," + UPDATED_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight equals to UPDATED_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.in=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight is not null
        defaultStockItemsShouldBeFound("itemHeight.specified=true");

        // Get all the stockItemsList where itemHeight is null
        defaultStockItemsShouldNotBeFound("itemHeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight is greater than or equal to DEFAULT_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.greaterThanOrEqual=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight is greater than or equal to UPDATED_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.greaterThanOrEqual=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight is less than or equal to DEFAULT_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.lessThanOrEqual=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight is less than or equal to SMALLER_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.lessThanOrEqual=" + SMALLER_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight is less than DEFAULT_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.lessThan=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight is less than UPDATED_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.lessThan=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemHeight is greater than DEFAULT_ITEM_HEIGHT
        defaultStockItemsShouldNotBeFound("itemHeight.greaterThan=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemsList where itemHeight is greater than SMALLER_ITEM_HEIGHT
        defaultStockItemsShouldBeFound("itemHeight.greaterThan=" + SMALLER_ITEM_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight equals to DEFAULT_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.equals=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight equals to UPDATED_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.equals=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight not equals to DEFAULT_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.notEquals=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight not equals to UPDATED_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.notEquals=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight in DEFAULT_ITEM_WEIGHT or UPDATED_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.in=" + DEFAULT_ITEM_WEIGHT + "," + UPDATED_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight equals to UPDATED_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.in=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight is not null
        defaultStockItemsShouldBeFound("itemWeight.specified=true");

        // Get all the stockItemsList where itemWeight is null
        defaultStockItemsShouldNotBeFound("itemWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight is greater than or equal to DEFAULT_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.greaterThanOrEqual=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight is greater than or equal to UPDATED_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.greaterThanOrEqual=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight is less than or equal to DEFAULT_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.lessThanOrEqual=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight is less than or equal to SMALLER_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.lessThanOrEqual=" + SMALLER_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight is less than DEFAULT_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.lessThan=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight is less than UPDATED_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.lessThan=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemWeight is greater than DEFAULT_ITEM_WEIGHT
        defaultStockItemsShouldNotBeFound("itemWeight.greaterThan=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemsList where itemWeight is greater than SMALLER_ITEM_WEIGHT
        defaultStockItemsShouldBeFound("itemWeight.greaterThan=" + SMALLER_ITEM_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength equals to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.equals=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength equals to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.equals=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength not equals to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.notEquals=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength not equals to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.notEquals=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength in DEFAULT_ITEM_PACKAGE_LENGTH or UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.in=" + DEFAULT_ITEM_PACKAGE_LENGTH + "," + UPDATED_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength equals to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.in=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength is not null
        defaultStockItemsShouldBeFound("itemPackageLength.specified=true");

        // Get all the stockItemsList where itemPackageLength is null
        defaultStockItemsShouldNotBeFound("itemPackageLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength is greater than or equal to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength is greater than or equal to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength is less than or equal to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength is less than or equal to SMALLER_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength is less than DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.lessThan=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength is less than UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.lessThan=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageLength is greater than DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldNotBeFound("itemPackageLength.greaterThan=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemsList where itemPackageLength is greater than SMALLER_ITEM_PACKAGE_LENGTH
        defaultStockItemsShouldBeFound("itemPackageLength.greaterThan=" + SMALLER_ITEM_PACKAGE_LENGTH);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth equals to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.equals=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth equals to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.equals=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth not equals to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.notEquals=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth not equals to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.notEquals=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth in DEFAULT_ITEM_PACKAGE_WIDTH or UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.in=" + DEFAULT_ITEM_PACKAGE_WIDTH + "," + UPDATED_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth equals to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.in=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth is not null
        defaultStockItemsShouldBeFound("itemPackageWidth.specified=true");

        // Get all the stockItemsList where itemPackageWidth is null
        defaultStockItemsShouldNotBeFound("itemPackageWidth.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth is greater than or equal to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth is greater than or equal to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth is less than or equal to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth is less than or equal to SMALLER_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth is less than DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.lessThan=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth is less than UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.lessThan=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWidth is greater than DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldNotBeFound("itemPackageWidth.greaterThan=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemsList where itemPackageWidth is greater than SMALLER_ITEM_PACKAGE_WIDTH
        defaultStockItemsShouldBeFound("itemPackageWidth.greaterThan=" + SMALLER_ITEM_PACKAGE_WIDTH);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight equals to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.equals=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight equals to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.equals=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight not equals to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.notEquals=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight not equals to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.notEquals=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight in DEFAULT_ITEM_PACKAGE_HEIGHT or UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.in=" + DEFAULT_ITEM_PACKAGE_HEIGHT + "," + UPDATED_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight equals to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.in=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight is not null
        defaultStockItemsShouldBeFound("itemPackageHeight.specified=true");

        // Get all the stockItemsList where itemPackageHeight is null
        defaultStockItemsShouldNotBeFound("itemPackageHeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight is greater than or equal to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight is greater than or equal to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight is less than or equal to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight is less than or equal to SMALLER_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight is less than DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.lessThan=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight is less than UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.lessThan=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageHeight is greater than DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageHeight.greaterThan=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemsList where itemPackageHeight is greater than SMALLER_ITEM_PACKAGE_HEIGHT
        defaultStockItemsShouldBeFound("itemPackageHeight.greaterThan=" + SMALLER_ITEM_PACKAGE_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight equals to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.equals=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight equals to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.equals=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight not equals to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.notEquals=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight not equals to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.notEquals=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight in DEFAULT_ITEM_PACKAGE_WEIGHT or UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.in=" + DEFAULT_ITEM_PACKAGE_WEIGHT + "," + UPDATED_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight equals to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.in=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight is not null
        defaultStockItemsShouldBeFound("itemPackageWeight.specified=true");

        // Get all the stockItemsList where itemPackageWeight is null
        defaultStockItemsShouldNotBeFound("itemPackageWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight is greater than or equal to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight is greater than or equal to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight is less than or equal to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight is less than or equal to SMALLER_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight is less than DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.lessThan=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight is less than UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.lessThan=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where itemPackageWeight is greater than DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldNotBeFound("itemPackageWeight.greaterThan=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemsList where itemPackageWeight is greater than SMALLER_ITEM_PACKAGE_WEIGHT
        defaultStockItemsShouldBeFound("itemPackageWeight.greaterThan=" + SMALLER_ITEM_PACKAGE_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces equals to DEFAULT_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.equals=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces equals to UPDATED_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.equals=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces not equals to DEFAULT_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.notEquals=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces not equals to UPDATED_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.notEquals=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces in DEFAULT_NO_OF_PIECES or UPDATED_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.in=" + DEFAULT_NO_OF_PIECES + "," + UPDATED_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces equals to UPDATED_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.in=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces is not null
        defaultStockItemsShouldBeFound("noOfPieces.specified=true");

        // Get all the stockItemsList where noOfPieces is null
        defaultStockItemsShouldNotBeFound("noOfPieces.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces is greater than or equal to DEFAULT_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.greaterThanOrEqual=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces is greater than or equal to UPDATED_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.greaterThanOrEqual=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces is less than or equal to DEFAULT_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.lessThanOrEqual=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces is less than or equal to SMALLER_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.lessThanOrEqual=" + SMALLER_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces is less than DEFAULT_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.lessThan=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces is less than UPDATED_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.lessThan=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfPiecesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfPieces is greater than DEFAULT_NO_OF_PIECES
        defaultStockItemsShouldNotBeFound("noOfPieces.greaterThan=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemsList where noOfPieces is greater than SMALLER_NO_OF_PIECES
        defaultStockItemsShouldBeFound("noOfPieces.greaterThan=" + SMALLER_NO_OF_PIECES);
    }


    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems equals to DEFAULT_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.equals=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems equals to UPDATED_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.equals=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems not equals to DEFAULT_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.notEquals=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems not equals to UPDATED_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.notEquals=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems in DEFAULT_NO_OF_ITEMS or UPDATED_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.in=" + DEFAULT_NO_OF_ITEMS + "," + UPDATED_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems equals to UPDATED_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.in=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems is not null
        defaultStockItemsShouldBeFound("noOfItems.specified=true");

        // Get all the stockItemsList where noOfItems is null
        defaultStockItemsShouldNotBeFound("noOfItems.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems is greater than or equal to DEFAULT_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.greaterThanOrEqual=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems is greater than or equal to UPDATED_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.greaterThanOrEqual=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems is less than or equal to DEFAULT_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.lessThanOrEqual=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems is less than or equal to SMALLER_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.lessThanOrEqual=" + SMALLER_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems is less than DEFAULT_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.lessThan=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems is less than UPDATED_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.lessThan=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByNoOfItemsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where noOfItems is greater than DEFAULT_NO_OF_ITEMS
        defaultStockItemsShouldNotBeFound("noOfItems.greaterThan=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemsList where noOfItems is greater than SMALLER_NO_OF_ITEMS
        defaultStockItemsShouldBeFound("noOfItems.greaterThan=" + SMALLER_NO_OF_ITEMS);
    }


    @Test
    @Transactional
    public void getAllStockItemsByManufactureIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where manufacture equals to DEFAULT_MANUFACTURE
        defaultStockItemsShouldBeFound("manufacture.equals=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemsList where manufacture equals to UPDATED_MANUFACTURE
        defaultStockItemsShouldNotBeFound("manufacture.equals=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByManufactureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where manufacture not equals to DEFAULT_MANUFACTURE
        defaultStockItemsShouldNotBeFound("manufacture.notEquals=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemsList where manufacture not equals to UPDATED_MANUFACTURE
        defaultStockItemsShouldBeFound("manufacture.notEquals=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByManufactureIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where manufacture in DEFAULT_MANUFACTURE or UPDATED_MANUFACTURE
        defaultStockItemsShouldBeFound("manufacture.in=" + DEFAULT_MANUFACTURE + "," + UPDATED_MANUFACTURE);

        // Get all the stockItemsList where manufacture equals to UPDATED_MANUFACTURE
        defaultStockItemsShouldNotBeFound("manufacture.in=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByManufactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where manufacture is not null
        defaultStockItemsShouldBeFound("manufacture.specified=true");

        // Get all the stockItemsList where manufacture is null
        defaultStockItemsShouldNotBeFound("manufacture.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByManufactureContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where manufacture contains DEFAULT_MANUFACTURE
        defaultStockItemsShouldBeFound("manufacture.contains=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemsList where manufacture contains UPDATED_MANUFACTURE
        defaultStockItemsShouldNotBeFound("manufacture.contains=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemsByManufactureNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where manufacture does not contain DEFAULT_MANUFACTURE
        defaultStockItemsShouldNotBeFound("manufacture.doesNotContain=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemsList where manufacture does not contain UPDATED_MANUFACTURE
        defaultStockItemsShouldBeFound("manufacture.doesNotContain=" + UPDATED_MANUFACTURE);
    }


    @Test
    @Transactional
    public void getAllStockItemsByMarketingCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where marketingComments equals to DEFAULT_MARKETING_COMMENTS
        defaultStockItemsShouldBeFound("marketingComments.equals=" + DEFAULT_MARKETING_COMMENTS);

        // Get all the stockItemsList where marketingComments equals to UPDATED_MARKETING_COMMENTS
        defaultStockItemsShouldNotBeFound("marketingComments.equals=" + UPDATED_MARKETING_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByMarketingCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where marketingComments not equals to DEFAULT_MARKETING_COMMENTS
        defaultStockItemsShouldNotBeFound("marketingComments.notEquals=" + DEFAULT_MARKETING_COMMENTS);

        // Get all the stockItemsList where marketingComments not equals to UPDATED_MARKETING_COMMENTS
        defaultStockItemsShouldBeFound("marketingComments.notEquals=" + UPDATED_MARKETING_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByMarketingCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where marketingComments in DEFAULT_MARKETING_COMMENTS or UPDATED_MARKETING_COMMENTS
        defaultStockItemsShouldBeFound("marketingComments.in=" + DEFAULT_MARKETING_COMMENTS + "," + UPDATED_MARKETING_COMMENTS);

        // Get all the stockItemsList where marketingComments equals to UPDATED_MARKETING_COMMENTS
        defaultStockItemsShouldNotBeFound("marketingComments.in=" + UPDATED_MARKETING_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByMarketingCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where marketingComments is not null
        defaultStockItemsShouldBeFound("marketingComments.specified=true");

        // Get all the stockItemsList where marketingComments is null
        defaultStockItemsShouldNotBeFound("marketingComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByMarketingCommentsContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where marketingComments contains DEFAULT_MARKETING_COMMENTS
        defaultStockItemsShouldBeFound("marketingComments.contains=" + DEFAULT_MARKETING_COMMENTS);

        // Get all the stockItemsList where marketingComments contains UPDATED_MARKETING_COMMENTS
        defaultStockItemsShouldNotBeFound("marketingComments.contains=" + UPDATED_MARKETING_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByMarketingCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where marketingComments does not contain DEFAULT_MARKETING_COMMENTS
        defaultStockItemsShouldNotBeFound("marketingComments.doesNotContain=" + DEFAULT_MARKETING_COMMENTS);

        // Get all the stockItemsList where marketingComments does not contain UPDATED_MARKETING_COMMENTS
        defaultStockItemsShouldBeFound("marketingComments.doesNotContain=" + UPDATED_MARKETING_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllStockItemsByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultStockItemsShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the stockItemsList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultStockItemsShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByInternalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where internalComments not equals to DEFAULT_INTERNAL_COMMENTS
        defaultStockItemsShouldNotBeFound("internalComments.notEquals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the stockItemsList where internalComments not equals to UPDATED_INTERNAL_COMMENTS
        defaultStockItemsShouldBeFound("internalComments.notEquals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultStockItemsShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the stockItemsList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultStockItemsShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where internalComments is not null
        defaultStockItemsShouldBeFound("internalComments.specified=true");

        // Get all the stockItemsList where internalComments is null
        defaultStockItemsShouldNotBeFound("internalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByInternalCommentsContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where internalComments contains DEFAULT_INTERNAL_COMMENTS
        defaultStockItemsShouldBeFound("internalComments.contains=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the stockItemsList where internalComments contains UPDATED_INTERNAL_COMMENTS
        defaultStockItemsShouldNotBeFound("internalComments.contains=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByInternalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where internalComments does not contain DEFAULT_INTERNAL_COMMENTS
        defaultStockItemsShouldNotBeFound("internalComments.doesNotContain=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the stockItemsList where internalComments does not contain UPDATED_INTERNAL_COMMENTS
        defaultStockItemsShouldBeFound("internalComments.doesNotContain=" + UPDATED_INTERNAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllStockItemsBySellStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellStartDate equals to DEFAULT_SELL_START_DATE
        defaultStockItemsShouldBeFound("sellStartDate.equals=" + DEFAULT_SELL_START_DATE);

        // Get all the stockItemsList where sellStartDate equals to UPDATED_SELL_START_DATE
        defaultStockItemsShouldNotBeFound("sellStartDate.equals=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellStartDate not equals to DEFAULT_SELL_START_DATE
        defaultStockItemsShouldNotBeFound("sellStartDate.notEquals=" + DEFAULT_SELL_START_DATE);

        // Get all the stockItemsList where sellStartDate not equals to UPDATED_SELL_START_DATE
        defaultStockItemsShouldBeFound("sellStartDate.notEquals=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellStartDate in DEFAULT_SELL_START_DATE or UPDATED_SELL_START_DATE
        defaultStockItemsShouldBeFound("sellStartDate.in=" + DEFAULT_SELL_START_DATE + "," + UPDATED_SELL_START_DATE);

        // Get all the stockItemsList where sellStartDate equals to UPDATED_SELL_START_DATE
        defaultStockItemsShouldNotBeFound("sellStartDate.in=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellStartDate is not null
        defaultStockItemsShouldBeFound("sellStartDate.specified=true");

        // Get all the stockItemsList where sellStartDate is null
        defaultStockItemsShouldNotBeFound("sellStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellEndDate equals to DEFAULT_SELL_END_DATE
        defaultStockItemsShouldBeFound("sellEndDate.equals=" + DEFAULT_SELL_END_DATE);

        // Get all the stockItemsList where sellEndDate equals to UPDATED_SELL_END_DATE
        defaultStockItemsShouldNotBeFound("sellEndDate.equals=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellEndDate not equals to DEFAULT_SELL_END_DATE
        defaultStockItemsShouldNotBeFound("sellEndDate.notEquals=" + DEFAULT_SELL_END_DATE);

        // Get all the stockItemsList where sellEndDate not equals to UPDATED_SELL_END_DATE
        defaultStockItemsShouldBeFound("sellEndDate.notEquals=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellEndDate in DEFAULT_SELL_END_DATE or UPDATED_SELL_END_DATE
        defaultStockItemsShouldBeFound("sellEndDate.in=" + DEFAULT_SELL_END_DATE + "," + UPDATED_SELL_END_DATE);

        // Get all the stockItemsList where sellEndDate equals to UPDATED_SELL_END_DATE
        defaultStockItemsShouldNotBeFound("sellEndDate.in=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellEndDate is not null
        defaultStockItemsShouldBeFound("sellEndDate.specified=true");

        // Get all the stockItemsList where sellEndDate is null
        defaultStockItemsShouldNotBeFound("sellEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount equals to DEFAULT_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.equals=" + DEFAULT_SELL_COUNT);

        // Get all the stockItemsList where sellCount equals to UPDATED_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.equals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount not equals to DEFAULT_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.notEquals=" + DEFAULT_SELL_COUNT);

        // Get all the stockItemsList where sellCount not equals to UPDATED_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.notEquals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount in DEFAULT_SELL_COUNT or UPDATED_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.in=" + DEFAULT_SELL_COUNT + "," + UPDATED_SELL_COUNT);

        // Get all the stockItemsList where sellCount equals to UPDATED_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.in=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount is not null
        defaultStockItemsShouldBeFound("sellCount.specified=true");

        // Get all the stockItemsList where sellCount is null
        defaultStockItemsShouldNotBeFound("sellCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount is greater than or equal to DEFAULT_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.greaterThanOrEqual=" + DEFAULT_SELL_COUNT);

        // Get all the stockItemsList where sellCount is greater than or equal to UPDATED_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.greaterThanOrEqual=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount is less than or equal to DEFAULT_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.lessThanOrEqual=" + DEFAULT_SELL_COUNT);

        // Get all the stockItemsList where sellCount is less than or equal to SMALLER_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.lessThanOrEqual=" + SMALLER_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount is less than DEFAULT_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.lessThan=" + DEFAULT_SELL_COUNT);

        // Get all the stockItemsList where sellCount is less than UPDATED_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.lessThan=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllStockItemsBySellCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where sellCount is greater than DEFAULT_SELL_COUNT
        defaultStockItemsShouldNotBeFound("sellCount.greaterThan=" + DEFAULT_SELL_COUNT);

        // Get all the stockItemsList where sellCount is greater than SMALLER_SELL_COUNT
        defaultStockItemsShouldBeFound("sellCount.greaterThan=" + SMALLER_SELL_COUNT);
    }


    @Test
    @Transactional
    public void getAllStockItemsByCustomFieldsIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where customFields equals to DEFAULT_CUSTOM_FIELDS
        defaultStockItemsShouldBeFound("customFields.equals=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the stockItemsList where customFields equals to UPDATED_CUSTOM_FIELDS
        defaultStockItemsShouldNotBeFound("customFields.equals=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByCustomFieldsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where customFields not equals to DEFAULT_CUSTOM_FIELDS
        defaultStockItemsShouldNotBeFound("customFields.notEquals=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the stockItemsList where customFields not equals to UPDATED_CUSTOM_FIELDS
        defaultStockItemsShouldBeFound("customFields.notEquals=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByCustomFieldsIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where customFields in DEFAULT_CUSTOM_FIELDS or UPDATED_CUSTOM_FIELDS
        defaultStockItemsShouldBeFound("customFields.in=" + DEFAULT_CUSTOM_FIELDS + "," + UPDATED_CUSTOM_FIELDS);

        // Get all the stockItemsList where customFields equals to UPDATED_CUSTOM_FIELDS
        defaultStockItemsShouldNotBeFound("customFields.in=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByCustomFieldsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where customFields is not null
        defaultStockItemsShouldBeFound("customFields.specified=true");

        // Get all the stockItemsList where customFields is null
        defaultStockItemsShouldNotBeFound("customFields.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByCustomFieldsContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where customFields contains DEFAULT_CUSTOM_FIELDS
        defaultStockItemsShouldBeFound("customFields.contains=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the stockItemsList where customFields contains UPDATED_CUSTOM_FIELDS
        defaultStockItemsShouldNotBeFound("customFields.contains=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllStockItemsByCustomFieldsNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where customFields does not contain DEFAULT_CUSTOM_FIELDS
        defaultStockItemsShouldNotBeFound("customFields.doesNotContain=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the stockItemsList where customFields does not contain UPDATED_CUSTOM_FIELDS
        defaultStockItemsShouldBeFound("customFields.doesNotContain=" + UPDATED_CUSTOM_FIELDS);
    }


    @Test
    @Transactional
    public void getAllStockItemsByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultStockItemsShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the stockItemsList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultStockItemsShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemsByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultStockItemsShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the stockItemsList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultStockItemsShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemsByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultStockItemsShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the stockItemsList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultStockItemsShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemsByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where thumbnailUrl is not null
        defaultStockItemsShouldBeFound("thumbnailUrl.specified=true");

        // Get all the stockItemsList where thumbnailUrl is null
        defaultStockItemsShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultStockItemsShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the stockItemsList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultStockItemsShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemsByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultStockItemsShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the stockItemsList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultStockItemsShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllStockItemsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultStockItemsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the stockItemsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultStockItemsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultStockItemsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the stockItemsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultStockItemsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultStockItemsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the stockItemsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultStockItemsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllStockItemsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where activeInd is not null
        defaultStockItemsShouldBeFound("activeInd.specified=true");

        // Get all the stockItemsList where activeInd is null
        defaultStockItemsShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultStockItemsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultStockItemsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the stockItemsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedBy is not null
        defaultStockItemsShouldBeFound("lastEditedBy.specified=true");

        // Get all the stockItemsList where lastEditedBy is null
        defaultStockItemsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultStockItemsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultStockItemsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultStockItemsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultStockItemsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllStockItemsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultStockItemsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the stockItemsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        // Get all the stockItemsList where lastEditedWhen is not null
        defaultStockItemsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the stockItemsList where lastEditedWhen is null
        defaultStockItemsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemsByStockItemOnReviewLineIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        ReviewLines stockItemOnReviewLine = ReviewLinesResourceIT.createEntity(em);
        em.persist(stockItemOnReviewLine);
        em.flush();
        stockItems.setStockItemOnReviewLine(stockItemOnReviewLine);
        stockItemsRepository.saveAndFlush(stockItems);
        Long stockItemOnReviewLineId = stockItemOnReviewLine.getId();

        // Get all the stockItemsList where stockItemOnReviewLine equals to stockItemOnReviewLineId
        defaultStockItemsShouldBeFound("stockItemOnReviewLineId.equals=" + stockItemOnReviewLineId);

        // Get all the stockItemsList where stockItemOnReviewLine equals to stockItemOnReviewLineId + 1
        defaultStockItemsShouldNotBeFound("stockItemOnReviewLineId.equals=" + (stockItemOnReviewLineId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByPhotoListIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        Photos photoList = PhotosResourceIT.createEntity(em);
        em.persist(photoList);
        em.flush();
        stockItems.addPhotoList(photoList);
        stockItemsRepository.saveAndFlush(stockItems);
        Long photoListId = photoList.getId();

        // Get all the stockItemsList where photoList equals to photoListId
        defaultStockItemsShouldBeFound("photoListId.equals=" + photoListId);

        // Get all the stockItemsList where photoList equals to photoListId + 1
        defaultStockItemsShouldNotBeFound("photoListId.equals=" + (photoListId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByDangerousGoodListIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        DangerousGoods dangerousGoodList = DangerousGoodsResourceIT.createEntity(em);
        em.persist(dangerousGoodList);
        em.flush();
        stockItems.addDangerousGoodList(dangerousGoodList);
        stockItemsRepository.saveAndFlush(stockItems);
        Long dangerousGoodListId = dangerousGoodList.getId();

        // Get all the stockItemsList where dangerousGoodList equals to dangerousGoodListId
        defaultStockItemsShouldBeFound("dangerousGoodListId.equals=" + dangerousGoodListId);

        // Get all the stockItemsList where dangerousGoodList equals to dangerousGoodListId + 1
        defaultStockItemsShouldNotBeFound("dangerousGoodListId.equals=" + (dangerousGoodListId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsBySpecialDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        SpecialDeals specialDiscount = SpecialDealsResourceIT.createEntity(em);
        em.persist(specialDiscount);
        em.flush();
        stockItems.addSpecialDiscount(specialDiscount);
        stockItemsRepository.saveAndFlush(stockItems);
        Long specialDiscountId = specialDiscount.getId();

        // Get all the stockItemsList where specialDiscount equals to specialDiscountId
        defaultStockItemsShouldBeFound("specialDiscountId.equals=" + specialDiscountId);

        // Get all the stockItemsList where specialDiscount equals to specialDiscountId + 1
        defaultStockItemsShouldNotBeFound("specialDiscountId.equals=" + (specialDiscountId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemLengthUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure itemLengthUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(itemLengthUnit);
        em.flush();
        stockItems.setItemLengthUnit(itemLengthUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long itemLengthUnitId = itemLengthUnit.getId();

        // Get all the stockItemsList where itemLengthUnit equals to itemLengthUnitId
        defaultStockItemsShouldBeFound("itemLengthUnitId.equals=" + itemLengthUnitId);

        // Get all the stockItemsList where itemLengthUnit equals to itemLengthUnitId + 1
        defaultStockItemsShouldNotBeFound("itemLengthUnitId.equals=" + (itemLengthUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemWidthUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure itemWidthUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(itemWidthUnit);
        em.flush();
        stockItems.setItemWidthUnit(itemWidthUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long itemWidthUnitId = itemWidthUnit.getId();

        // Get all the stockItemsList where itemWidthUnit equals to itemWidthUnitId
        defaultStockItemsShouldBeFound("itemWidthUnitId.equals=" + itemWidthUnitId);

        // Get all the stockItemsList where itemWidthUnit equals to itemWidthUnitId + 1
        defaultStockItemsShouldNotBeFound("itemWidthUnitId.equals=" + (itemWidthUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemHeightUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure itemHeightUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(itemHeightUnit);
        em.flush();
        stockItems.setItemHeightUnit(itemHeightUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long itemHeightUnitId = itemHeightUnit.getId();

        // Get all the stockItemsList where itemHeightUnit equals to itemHeightUnitId
        defaultStockItemsShouldBeFound("itemHeightUnitId.equals=" + itemHeightUnitId);

        // Get all the stockItemsList where itemHeightUnit equals to itemHeightUnitId + 1
        defaultStockItemsShouldNotBeFound("itemHeightUnitId.equals=" + (itemHeightUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByPackageLengthUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure packageLengthUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(packageLengthUnit);
        em.flush();
        stockItems.setPackageLengthUnit(packageLengthUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long packageLengthUnitId = packageLengthUnit.getId();

        // Get all the stockItemsList where packageLengthUnit equals to packageLengthUnitId
        defaultStockItemsShouldBeFound("packageLengthUnitId.equals=" + packageLengthUnitId);

        // Get all the stockItemsList where packageLengthUnit equals to packageLengthUnitId + 1
        defaultStockItemsShouldNotBeFound("packageLengthUnitId.equals=" + (packageLengthUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByPackageWidthUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure packageWidthUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(packageWidthUnit);
        em.flush();
        stockItems.setPackageWidthUnit(packageWidthUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long packageWidthUnitId = packageWidthUnit.getId();

        // Get all the stockItemsList where packageWidthUnit equals to packageWidthUnitId
        defaultStockItemsShouldBeFound("packageWidthUnitId.equals=" + packageWidthUnitId);

        // Get all the stockItemsList where packageWidthUnit equals to packageWidthUnitId + 1
        defaultStockItemsShouldNotBeFound("packageWidthUnitId.equals=" + (packageWidthUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByPackageHeightUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure packageHeightUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(packageHeightUnit);
        em.flush();
        stockItems.setPackageHeightUnit(packageHeightUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long packageHeightUnitId = packageHeightUnit.getId();

        // Get all the stockItemsList where packageHeightUnit equals to packageHeightUnitId
        defaultStockItemsShouldBeFound("packageHeightUnitId.equals=" + packageHeightUnitId);

        // Get all the stockItemsList where packageHeightUnit equals to packageHeightUnitId + 1
        defaultStockItemsShouldNotBeFound("packageHeightUnitId.equals=" + (packageHeightUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByItemPackageWeightUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        UnitMeasure itemPackageWeightUnit = UnitMeasureResourceIT.createEntity(em);
        em.persist(itemPackageWeightUnit);
        em.flush();
        stockItems.setItemPackageWeightUnit(itemPackageWeightUnit);
        stockItemsRepository.saveAndFlush(stockItems);
        Long itemPackageWeightUnitId = itemPackageWeightUnit.getId();

        // Get all the stockItemsList where itemPackageWeightUnit equals to itemPackageWeightUnitId
        defaultStockItemsShouldBeFound("itemPackageWeightUnitId.equals=" + itemPackageWeightUnitId);

        // Get all the stockItemsList where itemPackageWeightUnit equals to itemPackageWeightUnitId + 1
        defaultStockItemsShouldNotBeFound("itemPackageWeightUnitId.equals=" + (itemPackageWeightUnitId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByProductAttributeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        ProductAttribute productAttribute = ProductAttributeResourceIT.createEntity(em);
        em.persist(productAttribute);
        em.flush();
        stockItems.setProductAttribute(productAttribute);
        stockItemsRepository.saveAndFlush(stockItems);
        Long productAttributeId = productAttribute.getId();

        // Get all the stockItemsList where productAttribute equals to productAttributeId
        defaultStockItemsShouldBeFound("productAttributeId.equals=" + productAttributeId);

        // Get all the stockItemsList where productAttribute equals to productAttributeId + 1
        defaultStockItemsShouldNotBeFound("productAttributeId.equals=" + (productAttributeId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByProductOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        ProductOption productOption = ProductOptionResourceIT.createEntity(em);
        em.persist(productOption);
        em.flush();
        stockItems.setProductOption(productOption);
        stockItemsRepository.saveAndFlush(stockItems);
        Long productOptionId = productOption.getId();

        // Get all the stockItemsList where productOption equals to productOptionId
        defaultStockItemsShouldBeFound("productOptionId.equals=" + productOptionId);

        // Get all the stockItemsList where productOption equals to productOptionId + 1
        defaultStockItemsShouldNotBeFound("productOptionId.equals=" + (productOptionId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        Materials material = MaterialsResourceIT.createEntity(em);
        em.persist(material);
        em.flush();
        stockItems.setMaterial(material);
        stockItemsRepository.saveAndFlush(stockItems);
        Long materialId = material.getId();

        // Get all the stockItemsList where material equals to materialId
        defaultStockItemsShouldBeFound("materialId.equals=" + materialId);

        // Get all the stockItemsList where material equals to materialId + 1
        defaultStockItemsShouldNotBeFound("materialId.equals=" + (materialId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        stockItems.setCurrency(currency);
        stockItemsRepository.saveAndFlush(stockItems);
        Long currencyId = currency.getId();

        // Get all the stockItemsList where currency equals to currencyId
        defaultStockItemsShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the stockItemsList where currency equals to currencyId + 1
        defaultStockItemsShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByBarcodeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        BarcodeTypes barcodeType = BarcodeTypesResourceIT.createEntity(em);
        em.persist(barcodeType);
        em.flush();
        stockItems.setBarcodeType(barcodeType);
        stockItemsRepository.saveAndFlush(stockItems);
        Long barcodeTypeId = barcodeType.getId();

        // Get all the stockItemsList where barcodeType equals to barcodeTypeId
        defaultStockItemsShouldBeFound("barcodeTypeId.equals=" + barcodeTypeId);

        // Get all the stockItemsList where barcodeType equals to barcodeTypeId + 1
        defaultStockItemsShouldNotBeFound("barcodeTypeId.equals=" + (barcodeTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByStockItemHoldingIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        StockItemHoldings stockItemHolding = StockItemHoldingsResourceIT.createEntity(em);
        em.persist(stockItemHolding);
        em.flush();
        stockItems.setStockItemHolding(stockItemHolding);
        stockItemHolding.setStockItemHoldingOnStockItem(stockItems);
        stockItemsRepository.saveAndFlush(stockItems);
        Long stockItemHoldingId = stockItemHolding.getId();

        // Get all the stockItemsList where stockItemHolding equals to stockItemHoldingId
        defaultStockItemsShouldBeFound("stockItemHoldingId.equals=" + stockItemHoldingId);

        // Get all the stockItemsList where stockItemHolding equals to stockItemHoldingId + 1
        defaultStockItemsShouldNotBeFound("stockItemHoldingId.equals=" + (stockItemHoldingId + 1));
    }


    @Test
    @Transactional
    public void getAllStockItemsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        stockItems.setProduct(product);
        stockItemsRepository.saveAndFlush(stockItems);
        Long productId = product.getId();

        // Get all the stockItemsList where product equals to productId
        defaultStockItemsShouldBeFound("productId.equals=" + productId);

        // Get all the stockItemsList where product equals to productId + 1
        defaultStockItemsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemsShouldBeFound(String filter) throws Exception {
        restStockItemsMockMvc.perform(get("/api/stock-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorSKU").value(hasItem(DEFAULT_VENDOR_SKU)))
            .andExpect(jsonPath("$.[*].generatedSKU").value(hasItem(DEFAULT_GENERATED_SKU)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].recommendedRetailPrice").value(hasItem(DEFAULT_RECOMMENDED_RETAIL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].itemLength").value(hasItem(DEFAULT_ITEM_LENGTH)))
            .andExpect(jsonPath("$.[*].itemWidth").value(hasItem(DEFAULT_ITEM_WIDTH)))
            .andExpect(jsonPath("$.[*].itemHeight").value(hasItem(DEFAULT_ITEM_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemWeight").value(hasItem(DEFAULT_ITEM_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLength").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH)))
            .andExpect(jsonPath("$.[*].itemPackageWidth").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH)))
            .andExpect(jsonPath("$.[*].itemPackageHeight").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemPackageWeight").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].noOfPieces").value(hasItem(DEFAULT_NO_OF_PIECES)))
            .andExpect(jsonPath("$.[*].noOfItems").value(hasItem(DEFAULT_NO_OF_ITEMS)))
            .andExpect(jsonPath("$.[*].manufacture").value(hasItem(DEFAULT_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].marketingComments").value(hasItem(DEFAULT_MARKETING_COMMENTS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restStockItemsMockMvc.perform(get("/api/stock-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemsShouldNotBeFound(String filter) throws Exception {
        restStockItemsMockMvc.perform(get("/api/stock-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemsMockMvc.perform(get("/api/stock-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockItems() throws Exception {
        // Get the stockItems
        restStockItemsMockMvc.perform(get("/api/stock-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        int databaseSizeBeforeUpdate = stockItemsRepository.findAll().size();

        // Update the stockItems
        StockItems updatedStockItems = stockItemsRepository.findById(stockItems.getId()).get();
        // Disconnect from session so that the updates on updatedStockItems are not directly saved in db
        em.detach(updatedStockItems);
        updatedStockItems
            .name(UPDATED_NAME)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorSKU(UPDATED_VENDOR_SKU)
            .generatedSKU(UPDATED_GENERATED_SKU)
            .barcode(UPDATED_BARCODE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .recommendedRetailPrice(UPDATED_RECOMMENDED_RETAIL_PRICE)
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .itemLength(UPDATED_ITEM_LENGTH)
            .itemWidth(UPDATED_ITEM_WIDTH)
            .itemHeight(UPDATED_ITEM_HEIGHT)
            .itemWeight(UPDATED_ITEM_WEIGHT)
            .itemPackageLength(UPDATED_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(UPDATED_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(UPDATED_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(UPDATED_ITEM_PACKAGE_WEIGHT)
            .noOfPieces(UPDATED_NO_OF_PIECES)
            .noOfItems(UPDATED_NO_OF_ITEMS)
            .manufacture(UPDATED_MANUFACTURE)
            .marketingComments(UPDATED_MARKETING_COMMENTS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .sellCount(UPDATED_SELL_COUNT)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL)
            .activeInd(UPDATED_ACTIVE_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(updatedStockItems);

        restStockItemsMockMvc.perform(put("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeUpdate);
        StockItems testStockItems = stockItemsList.get(stockItemsList.size() - 1);
        assertThat(testStockItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockItems.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testStockItems.getVendorSKU()).isEqualTo(UPDATED_VENDOR_SKU);
        assertThat(testStockItems.getGeneratedSKU()).isEqualTo(UPDATED_GENERATED_SKU);
        assertThat(testStockItems.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testStockItems.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testStockItems.getRecommendedRetailPrice()).isEqualTo(UPDATED_RECOMMENDED_RETAIL_PRICE);
        assertThat(testStockItems.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testStockItems.getItemLength()).isEqualTo(UPDATED_ITEM_LENGTH);
        assertThat(testStockItems.getItemWidth()).isEqualTo(UPDATED_ITEM_WIDTH);
        assertThat(testStockItems.getItemHeight()).isEqualTo(UPDATED_ITEM_HEIGHT);
        assertThat(testStockItems.getItemWeight()).isEqualTo(UPDATED_ITEM_WEIGHT);
        assertThat(testStockItems.getItemPackageLength()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH);
        assertThat(testStockItems.getItemPackageWidth()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH);
        assertThat(testStockItems.getItemPackageHeight()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT);
        assertThat(testStockItems.getItemPackageWeight()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT);
        assertThat(testStockItems.getNoOfPieces()).isEqualTo(UPDATED_NO_OF_PIECES);
        assertThat(testStockItems.getNoOfItems()).isEqualTo(UPDATED_NO_OF_ITEMS);
        assertThat(testStockItems.getManufacture()).isEqualTo(UPDATED_MANUFACTURE);
        assertThat(testStockItems.getMarketingComments()).isEqualTo(UPDATED_MARKETING_COMMENTS);
        assertThat(testStockItems.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testStockItems.getSellStartDate()).isEqualTo(UPDATED_SELL_START_DATE);
        assertThat(testStockItems.getSellEndDate()).isEqualTo(UPDATED_SELL_END_DATE);
        assertThat(testStockItems.getSellCount()).isEqualTo(UPDATED_SELL_COUNT);
        assertThat(testStockItems.getCustomFields()).isEqualTo(UPDATED_CUSTOM_FIELDS);
        assertThat(testStockItems.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
        assertThat(testStockItems.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testStockItems.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testStockItems.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItems() throws Exception {
        int databaseSizeBeforeUpdate = stockItemsRepository.findAll().size();

        // Create the StockItems
        StockItemsDTO stockItemsDTO = stockItemsMapper.toDto(stockItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemsMockMvc.perform(put("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItems in the database
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItems() throws Exception {
        // Initialize the database
        stockItemsRepository.saveAndFlush(stockItems);

        int databaseSizeBeforeDelete = stockItemsRepository.findAll().size();

        // Delete the stockItems
        restStockItemsMockMvc.perform(delete("/api/stock-items/{id}", stockItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItems> stockItemsList = stockItemsRepository.findAll();
        assertThat(stockItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
