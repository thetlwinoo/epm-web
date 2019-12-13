package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.CurrencyRate;
import com.epmweb.server.repository.CurrencyRateRepository;
import com.epmweb.server.service.CurrencyRateService;
import com.epmweb.server.service.dto.CurrencyRateDTO;
import com.epmweb.server.service.mapper.CurrencyRateMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.CurrencyRateCriteria;
import com.epmweb.server.service.CurrencyRateQueryService;

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
 * Integration tests for the {@link CurrencyRateResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class CurrencyRateResourceIT {

    private static final Instant DEFAULT_CURRENCY_RATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CURRENCY_RATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FROMCODE = "AAAAAAAAAA";
    private static final String UPDATED_FROMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOCODE = "AAAAAAAAAA";
    private static final String UPDATED_TOCODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AVERAGE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVERAGE_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AVERAGE_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_END_OF_DAY_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_END_OF_DAY_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_END_OF_DAY_RATE = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private CurrencyRateMapper currencyRateMapper;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private CurrencyRateQueryService currencyRateQueryService;

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

    private MockMvc restCurrencyRateMockMvc;

    private CurrencyRate currencyRate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyRateResource currencyRateResource = new CurrencyRateResource(currencyRateService, currencyRateQueryService);
        this.restCurrencyRateMockMvc = MockMvcBuilders.standaloneSetup(currencyRateResource)
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
    public static CurrencyRate createEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currencyRateDate(DEFAULT_CURRENCY_RATE_DATE)
            .fromcode(DEFAULT_FROMCODE)
            .tocode(DEFAULT_TOCODE)
            .averageRate(DEFAULT_AVERAGE_RATE)
            .endOfDayRate(DEFAULT_END_OF_DAY_RATE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return currencyRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyRate createUpdatedEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currencyRateDate(UPDATED_CURRENCY_RATE_DATE)
            .fromcode(UPDATED_FROMCODE)
            .tocode(UPDATED_TOCODE)
            .averageRate(UPDATED_AVERAGE_RATE)
            .endOfDayRate(UPDATED_END_OF_DAY_RATE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return currencyRate;
    }

    @BeforeEach
    public void initTest() {
        currencyRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrencyRate() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrencyRateDate()).isEqualTo(DEFAULT_CURRENCY_RATE_DATE);
        assertThat(testCurrencyRate.getFromcode()).isEqualTo(DEFAULT_FROMCODE);
        assertThat(testCurrencyRate.getTocode()).isEqualTo(DEFAULT_TOCODE);
        assertThat(testCurrencyRate.getAverageRate()).isEqualTo(DEFAULT_AVERAGE_RATE);
        assertThat(testCurrencyRate.getEndOfDayRate()).isEqualTo(DEFAULT_END_OF_DAY_RATE);
        assertThat(testCurrencyRate.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCurrencyRate.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCurrencyRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate with an existing ID
        currencyRate.setId(1L);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrencyRateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setCurrencyRateDate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencyRates() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyRateDate").value(hasItem(DEFAULT_CURRENCY_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromcode").value(hasItem(DEFAULT_FROMCODE)))
            .andExpect(jsonPath("$.[*].tocode").value(hasItem(DEFAULT_TOCODE)))
            .andExpect(jsonPath("$.[*].averageRate").value(hasItem(DEFAULT_AVERAGE_RATE.intValue())))
            .andExpect(jsonPath("$.[*].endOfDayRate").value(hasItem(DEFAULT_END_OF_DAY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", currencyRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currencyRate.getId().intValue()))
            .andExpect(jsonPath("$.currencyRateDate").value(DEFAULT_CURRENCY_RATE_DATE.toString()))
            .andExpect(jsonPath("$.fromcode").value(DEFAULT_FROMCODE))
            .andExpect(jsonPath("$.tocode").value(DEFAULT_TOCODE))
            .andExpect(jsonPath("$.averageRate").value(DEFAULT_AVERAGE_RATE.intValue()))
            .andExpect(jsonPath("$.endOfDayRate").value(DEFAULT_END_OF_DAY_RATE.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCurrencyRatesByIdFiltering() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        Long id = currencyRate.getId();

        defaultCurrencyRateShouldBeFound("id.equals=" + id);
        defaultCurrencyRateShouldNotBeFound("id.notEquals=" + id);

        defaultCurrencyRateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurrencyRateShouldNotBeFound("id.greaterThan=" + id);

        defaultCurrencyRateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurrencyRateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate equals to DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.equals=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate equals to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.equals=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate not equals to DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.notEquals=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate not equals to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.notEquals=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate in DEFAULT_CURRENCY_RATE_DATE or UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.in=" + DEFAULT_CURRENCY_RATE_DATE + "," + UPDATED_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate equals to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.in=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate is not null
        defaultCurrencyRateShouldBeFound("currencyRateDate.specified=true");

        // Get all the currencyRateList where currencyRateDate is null
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromcode equals to DEFAULT_FROMCODE
        defaultCurrencyRateShouldBeFound("fromcode.equals=" + DEFAULT_FROMCODE);

        // Get all the currencyRateList where fromcode equals to UPDATED_FROMCODE
        defaultCurrencyRateShouldNotBeFound("fromcode.equals=" + UPDATED_FROMCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromcode not equals to DEFAULT_FROMCODE
        defaultCurrencyRateShouldNotBeFound("fromcode.notEquals=" + DEFAULT_FROMCODE);

        // Get all the currencyRateList where fromcode not equals to UPDATED_FROMCODE
        defaultCurrencyRateShouldBeFound("fromcode.notEquals=" + UPDATED_FROMCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromcodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromcode in DEFAULT_FROMCODE or UPDATED_FROMCODE
        defaultCurrencyRateShouldBeFound("fromcode.in=" + DEFAULT_FROMCODE + "," + UPDATED_FROMCODE);

        // Get all the currencyRateList where fromcode equals to UPDATED_FROMCODE
        defaultCurrencyRateShouldNotBeFound("fromcode.in=" + UPDATED_FROMCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromcode is not null
        defaultCurrencyRateShouldBeFound("fromcode.specified=true");

        // Get all the currencyRateList where fromcode is null
        defaultCurrencyRateShouldNotBeFound("fromcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrencyRatesByFromcodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromcode contains DEFAULT_FROMCODE
        defaultCurrencyRateShouldBeFound("fromcode.contains=" + DEFAULT_FROMCODE);

        // Get all the currencyRateList where fromcode contains UPDATED_FROMCODE
        defaultCurrencyRateShouldNotBeFound("fromcode.contains=" + UPDATED_FROMCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromcodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromcode does not contain DEFAULT_FROMCODE
        defaultCurrencyRateShouldNotBeFound("fromcode.doesNotContain=" + DEFAULT_FROMCODE);

        // Get all the currencyRateList where fromcode does not contain UPDATED_FROMCODE
        defaultCurrencyRateShouldBeFound("fromcode.doesNotContain=" + UPDATED_FROMCODE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByTocodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where tocode equals to DEFAULT_TOCODE
        defaultCurrencyRateShouldBeFound("tocode.equals=" + DEFAULT_TOCODE);

        // Get all the currencyRateList where tocode equals to UPDATED_TOCODE
        defaultCurrencyRateShouldNotBeFound("tocode.equals=" + UPDATED_TOCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByTocodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where tocode not equals to DEFAULT_TOCODE
        defaultCurrencyRateShouldNotBeFound("tocode.notEquals=" + DEFAULT_TOCODE);

        // Get all the currencyRateList where tocode not equals to UPDATED_TOCODE
        defaultCurrencyRateShouldBeFound("tocode.notEquals=" + UPDATED_TOCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByTocodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where tocode in DEFAULT_TOCODE or UPDATED_TOCODE
        defaultCurrencyRateShouldBeFound("tocode.in=" + DEFAULT_TOCODE + "," + UPDATED_TOCODE);

        // Get all the currencyRateList where tocode equals to UPDATED_TOCODE
        defaultCurrencyRateShouldNotBeFound("tocode.in=" + UPDATED_TOCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByTocodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where tocode is not null
        defaultCurrencyRateShouldBeFound("tocode.specified=true");

        // Get all the currencyRateList where tocode is null
        defaultCurrencyRateShouldNotBeFound("tocode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrencyRatesByTocodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where tocode contains DEFAULT_TOCODE
        defaultCurrencyRateShouldBeFound("tocode.contains=" + DEFAULT_TOCODE);

        // Get all the currencyRateList where tocode contains UPDATED_TOCODE
        defaultCurrencyRateShouldNotBeFound("tocode.contains=" + UPDATED_TOCODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByTocodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where tocode does not contain DEFAULT_TOCODE
        defaultCurrencyRateShouldNotBeFound("tocode.doesNotContain=" + DEFAULT_TOCODE);

        // Get all the currencyRateList where tocode does not contain UPDATED_TOCODE
        defaultCurrencyRateShouldBeFound("tocode.doesNotContain=" + UPDATED_TOCODE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate equals to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.equals=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate equals to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.equals=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate not equals to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.notEquals=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate not equals to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.notEquals=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate in DEFAULT_AVERAGE_RATE or UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.in=" + DEFAULT_AVERAGE_RATE + "," + UPDATED_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate equals to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.in=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is not null
        defaultCurrencyRateShouldBeFound("averageRate.specified=true");

        // Get all the currencyRateList where averageRate is null
        defaultCurrencyRateShouldNotBeFound("averageRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is greater than or equal to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.greaterThanOrEqual=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is greater than or equal to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.greaterThanOrEqual=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is less than or equal to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.lessThanOrEqual=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is less than or equal to SMALLER_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.lessThanOrEqual=" + SMALLER_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is less than DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.lessThan=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is less than UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.lessThan=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is greater than DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.greaterThan=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is greater than SMALLER_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.greaterThan=" + SMALLER_AVERAGE_RATE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate equals to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.equals=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate equals to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.equals=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate not equals to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.notEquals=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate not equals to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.notEquals=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate in DEFAULT_END_OF_DAY_RATE or UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.in=" + DEFAULT_END_OF_DAY_RATE + "," + UPDATED_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate equals to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.in=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is not null
        defaultCurrencyRateShouldBeFound("endOfDayRate.specified=true");

        // Get all the currencyRateList where endOfDayRate is null
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is greater than or equal to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.greaterThanOrEqual=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is greater than or equal to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.greaterThanOrEqual=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is less than or equal to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.lessThanOrEqual=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is less than or equal to SMALLER_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.lessThanOrEqual=" + SMALLER_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is less than DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.lessThan=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is less than UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.lessThan=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is greater than DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.greaterThan=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is greater than SMALLER_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.greaterThan=" + SMALLER_END_OF_DAY_RATE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy is not null
        defaultCurrencyRateShouldBeFound("lastEditedBy.specified=true");

        // Get all the currencyRateList where lastEditedBy is null
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen is not null
        defaultCurrencyRateShouldBeFound("lastEditedWhen.specified=true");

        // Get all the currencyRateList where lastEditedWhen is null
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyRateShouldBeFound(String filter) throws Exception {
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyRateDate").value(hasItem(DEFAULT_CURRENCY_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromcode").value(hasItem(DEFAULT_FROMCODE)))
            .andExpect(jsonPath("$.[*].tocode").value(hasItem(DEFAULT_TOCODE)))
            .andExpect(jsonPath("$.[*].averageRate").value(hasItem(DEFAULT_AVERAGE_RATE.intValue())))
            .andExpect(jsonPath("$.[*].endOfDayRate").value(hasItem(DEFAULT_END_OF_DAY_RATE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyRateShouldNotBeFound(String filter) throws Exception {
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurrencyRate() throws Exception {
        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Update the currencyRate
        CurrencyRate updatedCurrencyRate = currencyRateRepository.findById(currencyRate.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyRate are not directly saved in db
        em.detach(updatedCurrencyRate);
        updatedCurrencyRate
            .currencyRateDate(UPDATED_CURRENCY_RATE_DATE)
            .fromcode(UPDATED_FROMCODE)
            .tocode(UPDATED_TOCODE)
            .averageRate(UPDATED_AVERAGE_RATE)
            .endOfDayRate(UPDATED_END_OF_DAY_RATE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(updatedCurrencyRate);

        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isOk());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrencyRateDate()).isEqualTo(UPDATED_CURRENCY_RATE_DATE);
        assertThat(testCurrencyRate.getFromcode()).isEqualTo(UPDATED_FROMCODE);
        assertThat(testCurrencyRate.getTocode()).isEqualTo(UPDATED_TOCODE);
        assertThat(testCurrencyRate.getAverageRate()).isEqualTo(UPDATED_AVERAGE_RATE);
        assertThat(testCurrencyRate.getEndOfDayRate()).isEqualTo(UPDATED_END_OF_DAY_RATE);
        assertThat(testCurrencyRate.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCurrencyRate.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrencyRate() throws Exception {
        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeDelete = currencyRateRepository.findAll().size();

        // Delete the currencyRate
        restCurrencyRateMockMvc.perform(delete("/api/currency-rates/{id}", currencyRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
