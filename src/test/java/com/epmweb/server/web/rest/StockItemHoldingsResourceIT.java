package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.StockItemHoldings;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.repository.StockItemHoldingsRepository;
import com.epmweb.server.service.StockItemHoldingsService;
import com.epmweb.server.service.dto.StockItemHoldingsDTO;
import com.epmweb.server.service.mapper.StockItemHoldingsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.StockItemHoldingsCriteria;
import com.epmweb.server.service.StockItemHoldingsQueryService;

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
 * Integration tests for the {@link StockItemHoldingsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StockItemHoldingsResourceIT {

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 1;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 2;
    private static final Integer SMALLER_QUANTITY_ON_HAND = 1 - 1;

    private static final String DEFAULT_BIN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_BIN_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAST_STOCKTAKE_QUANTITY = 1;
    private static final Integer UPDATED_LAST_STOCKTAKE_QUANTITY = 2;
    private static final Integer SMALLER_LAST_STOCKTAKE_QUANTITY = 1 - 1;

    private static final BigDecimal DEFAULT_LAST_COST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_COST_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LAST_COST_PRICE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_REORDER_LEVEL = 1;
    private static final Integer UPDATED_REORDER_LEVEL = 2;
    private static final Integer SMALLER_REORDER_LEVEL = 1 - 1;

    private static final Integer DEFAULT_TARGER_STOCK_LEVEL = 1;
    private static final Integer UPDATED_TARGER_STOCK_LEVEL = 2;
    private static final Integer SMALLER_TARGER_STOCK_LEVEL = 1 - 1;

    @Autowired
    private StockItemHoldingsRepository stockItemHoldingsRepository;

    @Autowired
    private StockItemHoldingsMapper stockItemHoldingsMapper;

    @Autowired
    private StockItemHoldingsService stockItemHoldingsService;

    @Autowired
    private StockItemHoldingsQueryService stockItemHoldingsQueryService;

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

    private MockMvc restStockItemHoldingsMockMvc;

    private StockItemHoldings stockItemHoldings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemHoldingsResource stockItemHoldingsResource = new StockItemHoldingsResource(stockItemHoldingsService, stockItemHoldingsQueryService);
        this.restStockItemHoldingsMockMvc = MockMvcBuilders.standaloneSetup(stockItemHoldingsResource)
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
    public static StockItemHoldings createEntity(EntityManager em) {
        StockItemHoldings stockItemHoldings = new StockItemHoldings()
            .quantityOnHand(DEFAULT_QUANTITY_ON_HAND)
            .binLocation(DEFAULT_BIN_LOCATION)
            .lastStocktakeQuantity(DEFAULT_LAST_STOCKTAKE_QUANTITY)
            .lastCostPrice(DEFAULT_LAST_COST_PRICE)
            .reorderLevel(DEFAULT_REORDER_LEVEL)
            .targerStockLevel(DEFAULT_TARGER_STOCK_LEVEL);
        return stockItemHoldings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemHoldings createUpdatedEntity(EntityManager em) {
        StockItemHoldings stockItemHoldings = new StockItemHoldings()
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .binLocation(UPDATED_BIN_LOCATION)
            .lastStocktakeQuantity(UPDATED_LAST_STOCKTAKE_QUANTITY)
            .lastCostPrice(UPDATED_LAST_COST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targerStockLevel(UPDATED_TARGER_STOCK_LEVEL);
        return stockItemHoldings;
    }

    @BeforeEach
    public void initTest() {
        stockItemHoldings = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemHoldings() throws Exception {
        int databaseSizeBeforeCreate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);
        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemHoldings testStockItemHoldings = stockItemHoldingsList.get(stockItemHoldingsList.size() - 1);
        assertThat(testStockItemHoldings.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testStockItemHoldings.getBinLocation()).isEqualTo(DEFAULT_BIN_LOCATION);
        assertThat(testStockItemHoldings.getLastStocktakeQuantity()).isEqualTo(DEFAULT_LAST_STOCKTAKE_QUANTITY);
        assertThat(testStockItemHoldings.getLastCostPrice()).isEqualTo(DEFAULT_LAST_COST_PRICE);
        assertThat(testStockItemHoldings.getReorderLevel()).isEqualTo(DEFAULT_REORDER_LEVEL);
        assertThat(testStockItemHoldings.getTargerStockLevel()).isEqualTo(DEFAULT_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void createStockItemHoldingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings with an existing ID
        stockItemHoldings.setId(1L);
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityOnHandIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setQuantityOnHand(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBinLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setBinLocation(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastStocktakeQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastStocktakeQuantity(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastCostPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setLastCostPrice(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReorderLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setReorderLevel(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargerStockLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemHoldingsRepository.findAll().size();
        // set the field null
        stockItemHoldings.setTargerStockLevel(null);

        // Create the StockItemHoldings, which fails.
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        restStockItemHoldingsMockMvc.perform(post("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemHoldings.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].binLocation").value(hasItem(DEFAULT_BIN_LOCATION)))
            .andExpect(jsonPath("$.[*].lastStocktakeQuantity").value(hasItem(DEFAULT_LAST_STOCKTAKE_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastCostPrice").value(hasItem(DEFAULT_LAST_COST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].targerStockLevel").value(hasItem(DEFAULT_TARGER_STOCK_LEVEL)));
    }
    
    @Test
    @Transactional
    public void getStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/{id}", stockItemHoldings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemHoldings.getId().intValue()))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.binLocation").value(DEFAULT_BIN_LOCATION))
            .andExpect(jsonPath("$.lastStocktakeQuantity").value(DEFAULT_LAST_STOCKTAKE_QUANTITY))
            .andExpect(jsonPath("$.lastCostPrice").value(DEFAULT_LAST_COST_PRICE.intValue()))
            .andExpect(jsonPath("$.reorderLevel").value(DEFAULT_REORDER_LEVEL))
            .andExpect(jsonPath("$.targerStockLevel").value(DEFAULT_TARGER_STOCK_LEVEL));
    }


    @Test
    @Transactional
    public void getStockItemHoldingsByIdFiltering() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        Long id = stockItemHoldings.getId();

        defaultStockItemHoldingsShouldBeFound("id.equals=" + id);
        defaultStockItemHoldingsShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemHoldingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemHoldingsShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemHoldingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemHoldingsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.equals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.equals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand not equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.notEquals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand not equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.notEquals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand in DEFAULT_QUANTITY_ON_HAND or UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.in=" + DEFAULT_QUANTITY_ON_HAND + "," + UPDATED_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.in=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is not null
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.specified=true");

        // Get all the stockItemHoldingsList where quantityOnHand is null
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.greaterThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than or equal to UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.greaterThanOrEqual=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is less than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.lessThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is less than or equal to SMALLER_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.lessThanOrEqual=" + SMALLER_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is less than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.lessThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is less than UPDATED_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.lessThan=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByQuantityOnHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldNotBeFound("quantityOnHand.greaterThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemHoldingsList where quantityOnHand is greater than SMALLER_QUANTITY_ON_HAND
        defaultStockItemHoldingsShouldBeFound("quantityOnHand.greaterThan=" + SMALLER_QUANTITY_ON_HAND);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation equals to DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.equals=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation equals to UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.equals=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation not equals to DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.notEquals=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation not equals to UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.notEquals=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation in DEFAULT_BIN_LOCATION or UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.in=" + DEFAULT_BIN_LOCATION + "," + UPDATED_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation equals to UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.in=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation is not null
        defaultStockItemHoldingsShouldBeFound("binLocation.specified=true");

        // Get all the stockItemHoldingsList where binLocation is null
        defaultStockItemHoldingsShouldNotBeFound("binLocation.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationContainsSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation contains DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.contains=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation contains UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.contains=" + UPDATED_BIN_LOCATION);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByBinLocationNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where binLocation does not contain DEFAULT_BIN_LOCATION
        defaultStockItemHoldingsShouldNotBeFound("binLocation.doesNotContain=" + DEFAULT_BIN_LOCATION);

        // Get all the stockItemHoldingsList where binLocation does not contain UPDATED_BIN_LOCATION
        defaultStockItemHoldingsShouldBeFound("binLocation.doesNotContain=" + UPDATED_BIN_LOCATION);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity equals to DEFAULT_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.equals=" + DEFAULT_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity equals to UPDATED_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.equals=" + UPDATED_LAST_STOCKTAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity not equals to DEFAULT_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.notEquals=" + DEFAULT_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity not equals to UPDATED_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.notEquals=" + UPDATED_LAST_STOCKTAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity in DEFAULT_LAST_STOCKTAKE_QUANTITY or UPDATED_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.in=" + DEFAULT_LAST_STOCKTAKE_QUANTITY + "," + UPDATED_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity equals to UPDATED_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.in=" + UPDATED_LAST_STOCKTAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is not null
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.specified=true");

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is null
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is greater than or equal to DEFAULT_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.greaterThanOrEqual=" + DEFAULT_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is greater than or equal to UPDATED_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.greaterThanOrEqual=" + UPDATED_LAST_STOCKTAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is less than or equal to DEFAULT_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.lessThanOrEqual=" + DEFAULT_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is less than or equal to SMALLER_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.lessThanOrEqual=" + SMALLER_LAST_STOCKTAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is less than DEFAULT_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.lessThan=" + DEFAULT_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is less than UPDATED_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.lessThan=" + UPDATED_LAST_STOCKTAKE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastStocktakeQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is greater than DEFAULT_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldNotBeFound("lastStocktakeQuantity.greaterThan=" + DEFAULT_LAST_STOCKTAKE_QUANTITY);

        // Get all the stockItemHoldingsList where lastStocktakeQuantity is greater than SMALLER_LAST_STOCKTAKE_QUANTITY
        defaultStockItemHoldingsShouldBeFound("lastStocktakeQuantity.greaterThan=" + SMALLER_LAST_STOCKTAKE_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice equals to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.equals=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice equals to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.equals=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice not equals to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.notEquals=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice not equals to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.notEquals=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice in DEFAULT_LAST_COST_PRICE or UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.in=" + DEFAULT_LAST_COST_PRICE + "," + UPDATED_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice equals to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.in=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is not null
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.specified=true");

        // Get all the stockItemHoldingsList where lastCostPrice is null
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than or equal to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.greaterThanOrEqual=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than or equal to UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.greaterThanOrEqual=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is less than or equal to DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.lessThanOrEqual=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is less than or equal to SMALLER_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.lessThanOrEqual=" + SMALLER_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is less than DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.lessThan=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is less than UPDATED_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.lessThan=" + UPDATED_LAST_COST_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByLastCostPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than DEFAULT_LAST_COST_PRICE
        defaultStockItemHoldingsShouldNotBeFound("lastCostPrice.greaterThan=" + DEFAULT_LAST_COST_PRICE);

        // Get all the stockItemHoldingsList where lastCostPrice is greater than SMALLER_LAST_COST_PRICE
        defaultStockItemHoldingsShouldBeFound("lastCostPrice.greaterThan=" + SMALLER_LAST_COST_PRICE);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel equals to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.equals=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel equals to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.equals=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel not equals to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.notEquals=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel not equals to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.notEquals=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel in DEFAULT_REORDER_LEVEL or UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.in=" + DEFAULT_REORDER_LEVEL + "," + UPDATED_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel equals to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.in=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is not null
        defaultStockItemHoldingsShouldBeFound("reorderLevel.specified=true");

        // Get all the stockItemHoldingsList where reorderLevel is null
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is greater than or equal to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.greaterThanOrEqual=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is greater than or equal to UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.greaterThanOrEqual=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is less than or equal to DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.lessThanOrEqual=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is less than or equal to SMALLER_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.lessThanOrEqual=" + SMALLER_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is less than DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.lessThan=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is less than UPDATED_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.lessThan=" + UPDATED_REORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByReorderLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where reorderLevel is greater than DEFAULT_REORDER_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("reorderLevel.greaterThan=" + DEFAULT_REORDER_LEVEL);

        // Get all the stockItemHoldingsList where reorderLevel is greater than SMALLER_REORDER_LEVEL
        defaultStockItemHoldingsShouldBeFound("reorderLevel.greaterThan=" + SMALLER_REORDER_LEVEL);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel equals to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.equals=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel equals to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.equals=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel not equals to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.notEquals=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel not equals to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.notEquals=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel in DEFAULT_TARGER_STOCK_LEVEL or UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.in=" + DEFAULT_TARGER_STOCK_LEVEL + "," + UPDATED_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel equals to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.in=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is not null
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.specified=true");

        // Get all the stockItemHoldingsList where targerStockLevel is null
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than or equal to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.greaterThanOrEqual=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than or equal to UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.greaterThanOrEqual=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is less than or equal to DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.lessThanOrEqual=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is less than or equal to SMALLER_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.lessThanOrEqual=" + SMALLER_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is less than DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.lessThan=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is less than UPDATED_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.lessThan=" + UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStockItemHoldingsByTargerStockLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than DEFAULT_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldNotBeFound("targerStockLevel.greaterThan=" + DEFAULT_TARGER_STOCK_LEVEL);

        // Get all the stockItemHoldingsList where targerStockLevel is greater than SMALLER_TARGER_STOCK_LEVEL
        defaultStockItemHoldingsShouldBeFound("targerStockLevel.greaterThan=" + SMALLER_TARGER_STOCK_LEVEL);
    }


    @Test
    @Transactional
    public void getAllStockItemHoldingsByStockItemHoldingOnStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);
        StockItems stockItemHoldingOnStockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItemHoldingOnStockItem);
        em.flush();
        stockItemHoldings.setStockItemHoldingOnStockItem(stockItemHoldingOnStockItem);
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);
        Long stockItemHoldingOnStockItemId = stockItemHoldingOnStockItem.getId();

        // Get all the stockItemHoldingsList where stockItemHoldingOnStockItem equals to stockItemHoldingOnStockItemId
        defaultStockItemHoldingsShouldBeFound("stockItemHoldingOnStockItemId.equals=" + stockItemHoldingOnStockItemId);

        // Get all the stockItemHoldingsList where stockItemHoldingOnStockItem equals to stockItemHoldingOnStockItemId + 1
        defaultStockItemHoldingsShouldNotBeFound("stockItemHoldingOnStockItemId.equals=" + (stockItemHoldingOnStockItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemHoldingsShouldBeFound(String filter) throws Exception {
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemHoldings.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].binLocation").value(hasItem(DEFAULT_BIN_LOCATION)))
            .andExpect(jsonPath("$.[*].lastStocktakeQuantity").value(hasItem(DEFAULT_LAST_STOCKTAKE_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastCostPrice").value(hasItem(DEFAULT_LAST_COST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].targerStockLevel").value(hasItem(DEFAULT_TARGER_STOCK_LEVEL)));

        // Check, that the count call also returns 1
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemHoldingsShouldNotBeFound(String filter) throws Exception {
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockItemHoldings() throws Exception {
        // Get the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(get("/api/stock-item-holdings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        int databaseSizeBeforeUpdate = stockItemHoldingsRepository.findAll().size();

        // Update the stockItemHoldings
        StockItemHoldings updatedStockItemHoldings = stockItemHoldingsRepository.findById(stockItemHoldings.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemHoldings are not directly saved in db
        em.detach(updatedStockItemHoldings);
        updatedStockItemHoldings
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .binLocation(UPDATED_BIN_LOCATION)
            .lastStocktakeQuantity(UPDATED_LAST_STOCKTAKE_QUANTITY)
            .lastCostPrice(UPDATED_LAST_COST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targerStockLevel(UPDATED_TARGER_STOCK_LEVEL);
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(updatedStockItemHoldings);

        restStockItemHoldingsMockMvc.perform(put("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeUpdate);
        StockItemHoldings testStockItemHoldings = stockItemHoldingsList.get(stockItemHoldingsList.size() - 1);
        assertThat(testStockItemHoldings.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testStockItemHoldings.getBinLocation()).isEqualTo(UPDATED_BIN_LOCATION);
        assertThat(testStockItemHoldings.getLastStocktakeQuantity()).isEqualTo(UPDATED_LAST_STOCKTAKE_QUANTITY);
        assertThat(testStockItemHoldings.getLastCostPrice()).isEqualTo(UPDATED_LAST_COST_PRICE);
        assertThat(testStockItemHoldings.getReorderLevel()).isEqualTo(UPDATED_REORDER_LEVEL);
        assertThat(testStockItemHoldings.getTargerStockLevel()).isEqualTo(UPDATED_TARGER_STOCK_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemHoldings() throws Exception {
        int databaseSizeBeforeUpdate = stockItemHoldingsRepository.findAll().size();

        // Create the StockItemHoldings
        StockItemHoldingsDTO stockItemHoldingsDTO = stockItemHoldingsMapper.toDto(stockItemHoldings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemHoldingsMockMvc.perform(put("/api/stock-item-holdings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemHoldingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemHoldings in the database
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemHoldings() throws Exception {
        // Initialize the database
        stockItemHoldingsRepository.saveAndFlush(stockItemHoldings);

        int databaseSizeBeforeDelete = stockItemHoldingsRepository.findAll().size();

        // Delete the stockItemHoldings
        restStockItemHoldingsMockMvc.perform(delete("/api/stock-item-holdings/{id}", stockItemHoldings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItemHoldings> stockItemHoldingsList = stockItemHoldingsRepository.findAll();
        assertThat(stockItemHoldingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
