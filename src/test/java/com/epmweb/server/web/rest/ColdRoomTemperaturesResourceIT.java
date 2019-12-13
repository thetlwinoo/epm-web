package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ColdRoomTemperatures;
import com.epmweb.server.repository.ColdRoomTemperaturesRepository;
import com.epmweb.server.service.ColdRoomTemperaturesService;
import com.epmweb.server.service.dto.ColdRoomTemperaturesDTO;
import com.epmweb.server.service.mapper.ColdRoomTemperaturesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ColdRoomTemperaturesCriteria;
import com.epmweb.server.service.ColdRoomTemperaturesQueryService;

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
 * Integration tests for the {@link ColdRoomTemperaturesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ColdRoomTemperaturesResourceIT {

    private static final Integer DEFAULT_COLD_ROOM_SENSOR_NUMBER = 1;
    private static final Integer UPDATED_COLD_ROOM_SENSOR_NUMBER = 2;
    private static final Integer SMALLER_COLD_ROOM_SENSOR_NUMBER = 1 - 1;

    private static final Instant DEFAULT_RECORDED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORDED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_TEMPERATURE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TEMPERATURE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TEMPERATURE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ColdRoomTemperaturesRepository coldRoomTemperaturesRepository;

    @Autowired
    private ColdRoomTemperaturesMapper coldRoomTemperaturesMapper;

    @Autowired
    private ColdRoomTemperaturesService coldRoomTemperaturesService;

    @Autowired
    private ColdRoomTemperaturesQueryService coldRoomTemperaturesQueryService;

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

    private MockMvc restColdRoomTemperaturesMockMvc;

    private ColdRoomTemperatures coldRoomTemperatures;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColdRoomTemperaturesResource coldRoomTemperaturesResource = new ColdRoomTemperaturesResource(coldRoomTemperaturesService, coldRoomTemperaturesQueryService);
        this.restColdRoomTemperaturesMockMvc = MockMvcBuilders.standaloneSetup(coldRoomTemperaturesResource)
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
    public static ColdRoomTemperatures createEntity(EntityManager em) {
        ColdRoomTemperatures coldRoomTemperatures = new ColdRoomTemperatures()
            .coldRoomSensorNumber(DEFAULT_COLD_ROOM_SENSOR_NUMBER)
            .recordedWhen(DEFAULT_RECORDED_WHEN)
            .temperature(DEFAULT_TEMPERATURE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return coldRoomTemperatures;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColdRoomTemperatures createUpdatedEntity(EntityManager em) {
        ColdRoomTemperatures coldRoomTemperatures = new ColdRoomTemperatures()
            .coldRoomSensorNumber(UPDATED_COLD_ROOM_SENSOR_NUMBER)
            .recordedWhen(UPDATED_RECORDED_WHEN)
            .temperature(UPDATED_TEMPERATURE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return coldRoomTemperatures;
    }

    @BeforeEach
    public void initTest() {
        coldRoomTemperatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createColdRoomTemperatures() throws Exception {
        int databaseSizeBeforeCreate = coldRoomTemperaturesRepository.findAll().size();

        // Create the ColdRoomTemperatures
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);
        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isCreated());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeCreate + 1);
        ColdRoomTemperatures testColdRoomTemperatures = coldRoomTemperaturesList.get(coldRoomTemperaturesList.size() - 1);
        assertThat(testColdRoomTemperatures.getColdRoomSensorNumber()).isEqualTo(DEFAULT_COLD_ROOM_SENSOR_NUMBER);
        assertThat(testColdRoomTemperatures.getRecordedWhen()).isEqualTo(DEFAULT_RECORDED_WHEN);
        assertThat(testColdRoomTemperatures.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testColdRoomTemperatures.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testColdRoomTemperatures.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createColdRoomTemperaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coldRoomTemperaturesRepository.findAll().size();

        // Create the ColdRoomTemperatures with an existing ID
        coldRoomTemperatures.setId(1L);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkColdRoomSensorNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setColdRoomSensorNumber(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setRecordedWhen(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setTemperature(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setValidFrom(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = coldRoomTemperaturesRepository.findAll().size();
        // set the field null
        coldRoomTemperatures.setValidTo(null);

        // Create the ColdRoomTemperatures, which fails.
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(post("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coldRoomTemperatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].coldRoomSensorNumber").value(hasItem(DEFAULT_COLD_ROOM_SENSOR_NUMBER)))
            .andExpect(jsonPath("$.[*].recordedWhen").value(hasItem(DEFAULT_RECORDED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get the coldRoomTemperatures
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures/{id}", coldRoomTemperatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coldRoomTemperatures.getId().intValue()))
            .andExpect(jsonPath("$.coldRoomSensorNumber").value(DEFAULT_COLD_ROOM_SENSOR_NUMBER))
            .andExpect(jsonPath("$.recordedWhen").value(DEFAULT_RECORDED_WHEN.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getColdRoomTemperaturesByIdFiltering() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        Long id = coldRoomTemperatures.getId();

        defaultColdRoomTemperaturesShouldBeFound("id.equals=" + id);
        defaultColdRoomTemperaturesShouldNotBeFound("id.notEquals=" + id);

        defaultColdRoomTemperaturesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultColdRoomTemperaturesShouldNotBeFound("id.greaterThan=" + id);

        defaultColdRoomTemperaturesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultColdRoomTemperaturesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber equals to DEFAULT_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.equals=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber equals to UPDATED_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.equals=" + UPDATED_COLD_ROOM_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber not equals to DEFAULT_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.notEquals=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber not equals to UPDATED_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.notEquals=" + UPDATED_COLD_ROOM_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsInShouldWork() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber in DEFAULT_COLD_ROOM_SENSOR_NUMBER or UPDATED_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.in=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER + "," + UPDATED_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber equals to UPDATED_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.in=" + UPDATED_COLD_ROOM_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is not null
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.specified=true");

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is null
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is greater than or equal to DEFAULT_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.greaterThanOrEqual=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is greater than or equal to UPDATED_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.greaterThanOrEqual=" + UPDATED_COLD_ROOM_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is less than or equal to DEFAULT_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.lessThanOrEqual=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is less than or equal to SMALLER_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.lessThanOrEqual=" + SMALLER_COLD_ROOM_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is less than DEFAULT_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.lessThan=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is less than UPDATED_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.lessThan=" + UPDATED_COLD_ROOM_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByColdRoomSensorNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is greater than DEFAULT_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldNotBeFound("coldRoomSensorNumber.greaterThan=" + DEFAULT_COLD_ROOM_SENSOR_NUMBER);

        // Get all the coldRoomTemperaturesList where coldRoomSensorNumber is greater than SMALLER_COLD_ROOM_SENSOR_NUMBER
        defaultColdRoomTemperaturesShouldBeFound("coldRoomSensorNumber.greaterThan=" + SMALLER_COLD_ROOM_SENSOR_NUMBER);
    }


    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByRecordedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where recordedWhen equals to DEFAULT_RECORDED_WHEN
        defaultColdRoomTemperaturesShouldBeFound("recordedWhen.equals=" + DEFAULT_RECORDED_WHEN);

        // Get all the coldRoomTemperaturesList where recordedWhen equals to UPDATED_RECORDED_WHEN
        defaultColdRoomTemperaturesShouldNotBeFound("recordedWhen.equals=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByRecordedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where recordedWhen not equals to DEFAULT_RECORDED_WHEN
        defaultColdRoomTemperaturesShouldNotBeFound("recordedWhen.notEquals=" + DEFAULT_RECORDED_WHEN);

        // Get all the coldRoomTemperaturesList where recordedWhen not equals to UPDATED_RECORDED_WHEN
        defaultColdRoomTemperaturesShouldBeFound("recordedWhen.notEquals=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByRecordedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where recordedWhen in DEFAULT_RECORDED_WHEN or UPDATED_RECORDED_WHEN
        defaultColdRoomTemperaturesShouldBeFound("recordedWhen.in=" + DEFAULT_RECORDED_WHEN + "," + UPDATED_RECORDED_WHEN);

        // Get all the coldRoomTemperaturesList where recordedWhen equals to UPDATED_RECORDED_WHEN
        defaultColdRoomTemperaturesShouldNotBeFound("recordedWhen.in=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByRecordedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where recordedWhen is not null
        defaultColdRoomTemperaturesShouldBeFound("recordedWhen.specified=true");

        // Get all the coldRoomTemperaturesList where recordedWhen is null
        defaultColdRoomTemperaturesShouldNotBeFound("recordedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature equals to DEFAULT_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature equals to UPDATED_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature not equals to DEFAULT_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature not equals to UPDATED_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature equals to UPDATED_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature is not null
        defaultColdRoomTemperaturesShouldBeFound("temperature.specified=true");

        // Get all the coldRoomTemperaturesList where temperature is null
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature is greater than or equal to DEFAULT_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.greaterThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature is greater than or equal to UPDATED_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.greaterThanOrEqual=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature is less than or equal to DEFAULT_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.lessThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature is less than or equal to SMALLER_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.lessThanOrEqual=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature is less than DEFAULT_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.lessThan=" + DEFAULT_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature is less than UPDATED_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.lessThan=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where temperature is greater than DEFAULT_TEMPERATURE
        defaultColdRoomTemperaturesShouldNotBeFound("temperature.greaterThan=" + DEFAULT_TEMPERATURE);

        // Get all the coldRoomTemperaturesList where temperature is greater than SMALLER_TEMPERATURE
        defaultColdRoomTemperaturesShouldBeFound("temperature.greaterThan=" + SMALLER_TEMPERATURE);
    }


    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validFrom equals to DEFAULT_VALID_FROM
        defaultColdRoomTemperaturesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the coldRoomTemperaturesList where validFrom equals to UPDATED_VALID_FROM
        defaultColdRoomTemperaturesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultColdRoomTemperaturesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the coldRoomTemperaturesList where validFrom not equals to UPDATED_VALID_FROM
        defaultColdRoomTemperaturesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultColdRoomTemperaturesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the coldRoomTemperaturesList where validFrom equals to UPDATED_VALID_FROM
        defaultColdRoomTemperaturesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validFrom is not null
        defaultColdRoomTemperaturesShouldBeFound("validFrom.specified=true");

        // Get all the coldRoomTemperaturesList where validFrom is null
        defaultColdRoomTemperaturesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validTo equals to DEFAULT_VALID_TO
        defaultColdRoomTemperaturesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the coldRoomTemperaturesList where validTo equals to UPDATED_VALID_TO
        defaultColdRoomTemperaturesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validTo not equals to DEFAULT_VALID_TO
        defaultColdRoomTemperaturesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the coldRoomTemperaturesList where validTo not equals to UPDATED_VALID_TO
        defaultColdRoomTemperaturesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultColdRoomTemperaturesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the coldRoomTemperaturesList where validTo equals to UPDATED_VALID_TO
        defaultColdRoomTemperaturesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllColdRoomTemperaturesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        // Get all the coldRoomTemperaturesList where validTo is not null
        defaultColdRoomTemperaturesShouldBeFound("validTo.specified=true");

        // Get all the coldRoomTemperaturesList where validTo is null
        defaultColdRoomTemperaturesShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultColdRoomTemperaturesShouldBeFound(String filter) throws Exception {
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coldRoomTemperatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].coldRoomSensorNumber").value(hasItem(DEFAULT_COLD_ROOM_SENSOR_NUMBER)))
            .andExpect(jsonPath("$.[*].recordedWhen").value(hasItem(DEFAULT_RECORDED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultColdRoomTemperaturesShouldNotBeFound(String filter) throws Exception {
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingColdRoomTemperatures() throws Exception {
        // Get the coldRoomTemperatures
        restColdRoomTemperaturesMockMvc.perform(get("/api/cold-room-temperatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        int databaseSizeBeforeUpdate = coldRoomTemperaturesRepository.findAll().size();

        // Update the coldRoomTemperatures
        ColdRoomTemperatures updatedColdRoomTemperatures = coldRoomTemperaturesRepository.findById(coldRoomTemperatures.getId()).get();
        // Disconnect from session so that the updates on updatedColdRoomTemperatures are not directly saved in db
        em.detach(updatedColdRoomTemperatures);
        updatedColdRoomTemperatures
            .coldRoomSensorNumber(UPDATED_COLD_ROOM_SENSOR_NUMBER)
            .recordedWhen(UPDATED_RECORDED_WHEN)
            .temperature(UPDATED_TEMPERATURE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(updatedColdRoomTemperatures);

        restColdRoomTemperaturesMockMvc.perform(put("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isOk());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeUpdate);
        ColdRoomTemperatures testColdRoomTemperatures = coldRoomTemperaturesList.get(coldRoomTemperaturesList.size() - 1);
        assertThat(testColdRoomTemperatures.getColdRoomSensorNumber()).isEqualTo(UPDATED_COLD_ROOM_SENSOR_NUMBER);
        assertThat(testColdRoomTemperatures.getRecordedWhen()).isEqualTo(UPDATED_RECORDED_WHEN);
        assertThat(testColdRoomTemperatures.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testColdRoomTemperatures.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testColdRoomTemperatures.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingColdRoomTemperatures() throws Exception {
        int databaseSizeBeforeUpdate = coldRoomTemperaturesRepository.findAll().size();

        // Create the ColdRoomTemperatures
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColdRoomTemperaturesMockMvc.perform(put("/api/cold-room-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coldRoomTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ColdRoomTemperatures in the database
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColdRoomTemperatures() throws Exception {
        // Initialize the database
        coldRoomTemperaturesRepository.saveAndFlush(coldRoomTemperatures);

        int databaseSizeBeforeDelete = coldRoomTemperaturesRepository.findAll().size();

        // Delete the coldRoomTemperatures
        restColdRoomTemperaturesMockMvc.perform(delete("/api/cold-room-temperatures/{id}", coldRoomTemperatures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ColdRoomTemperatures> coldRoomTemperaturesList = coldRoomTemperaturesRepository.findAll();
        assertThat(coldRoomTemperaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
