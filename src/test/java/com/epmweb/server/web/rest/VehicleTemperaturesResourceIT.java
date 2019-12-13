package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.VehicleTemperatures;
import com.epmweb.server.repository.VehicleTemperaturesRepository;
import com.epmweb.server.service.VehicleTemperaturesService;
import com.epmweb.server.service.dto.VehicleTemperaturesDTO;
import com.epmweb.server.service.mapper.VehicleTemperaturesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.VehicleTemperaturesCriteria;
import com.epmweb.server.service.VehicleTemperaturesQueryService;

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
 * Integration tests for the {@link VehicleTemperaturesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class VehicleTemperaturesResourceIT {

    private static final Integer DEFAULT_VEHICLE_REGISTRATION = 1;
    private static final Integer UPDATED_VEHICLE_REGISTRATION = 2;
    private static final Integer SMALLER_VEHICLE_REGISTRATION = 1 - 1;

    private static final String DEFAULT_CHILLER_SENSOR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHILLER_SENSOR_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECORDED_WHEN = 1;
    private static final Integer UPDATED_RECORDED_WHEN = 2;
    private static final Integer SMALLER_RECORDED_WHEN = 1 - 1;

    private static final BigDecimal DEFAULT_TEMPERATURE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TEMPERATURE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TEMPERATURE = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_IS_COMPRESSED = false;
    private static final Boolean UPDATED_IS_COMPRESSED = true;

    private static final String DEFAULT_FULL_SENSOR_DATA = "AAAAAAAAAA";
    private static final String UPDATED_FULL_SENSOR_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_COMPRESSED_SENSOR_DATA = "AAAAAAAAAA";
    private static final String UPDATED_COMPRESSED_SENSOR_DATA = "BBBBBBBBBB";

    @Autowired
    private VehicleTemperaturesRepository vehicleTemperaturesRepository;

    @Autowired
    private VehicleTemperaturesMapper vehicleTemperaturesMapper;

    @Autowired
    private VehicleTemperaturesService vehicleTemperaturesService;

    @Autowired
    private VehicleTemperaturesQueryService vehicleTemperaturesQueryService;

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

    private MockMvc restVehicleTemperaturesMockMvc;

    private VehicleTemperatures vehicleTemperatures;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleTemperaturesResource vehicleTemperaturesResource = new VehicleTemperaturesResource(vehicleTemperaturesService, vehicleTemperaturesQueryService);
        this.restVehicleTemperaturesMockMvc = MockMvcBuilders.standaloneSetup(vehicleTemperaturesResource)
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
    public static VehicleTemperatures createEntity(EntityManager em) {
        VehicleTemperatures vehicleTemperatures = new VehicleTemperatures()
            .vehicleRegistration(DEFAULT_VEHICLE_REGISTRATION)
            .chillerSensorNumber(DEFAULT_CHILLER_SENSOR_NUMBER)
            .recordedWhen(DEFAULT_RECORDED_WHEN)
            .temperature(DEFAULT_TEMPERATURE)
            .isCompressed(DEFAULT_IS_COMPRESSED)
            .fullSensorData(DEFAULT_FULL_SENSOR_DATA)
            .compressedSensorData(DEFAULT_COMPRESSED_SENSOR_DATA);
        return vehicleTemperatures;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleTemperatures createUpdatedEntity(EntityManager em) {
        VehicleTemperatures vehicleTemperatures = new VehicleTemperatures()
            .vehicleRegistration(UPDATED_VEHICLE_REGISTRATION)
            .chillerSensorNumber(UPDATED_CHILLER_SENSOR_NUMBER)
            .recordedWhen(UPDATED_RECORDED_WHEN)
            .temperature(UPDATED_TEMPERATURE)
            .isCompressed(UPDATED_IS_COMPRESSED)
            .fullSensorData(UPDATED_FULL_SENSOR_DATA)
            .compressedSensorData(UPDATED_COMPRESSED_SENSOR_DATA);
        return vehicleTemperatures;
    }

    @BeforeEach
    public void initTest() {
        vehicleTemperatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleTemperatures() throws Exception {
        int databaseSizeBeforeCreate = vehicleTemperaturesRepository.findAll().size();

        // Create the VehicleTemperatures
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);
        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleTemperatures testVehicleTemperatures = vehicleTemperaturesList.get(vehicleTemperaturesList.size() - 1);
        assertThat(testVehicleTemperatures.getVehicleRegistration()).isEqualTo(DEFAULT_VEHICLE_REGISTRATION);
        assertThat(testVehicleTemperatures.getChillerSensorNumber()).isEqualTo(DEFAULT_CHILLER_SENSOR_NUMBER);
        assertThat(testVehicleTemperatures.getRecordedWhen()).isEqualTo(DEFAULT_RECORDED_WHEN);
        assertThat(testVehicleTemperatures.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testVehicleTemperatures.isIsCompressed()).isEqualTo(DEFAULT_IS_COMPRESSED);
        assertThat(testVehicleTemperatures.getFullSensorData()).isEqualTo(DEFAULT_FULL_SENSOR_DATA);
        assertThat(testVehicleTemperatures.getCompressedSensorData()).isEqualTo(DEFAULT_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void createVehicleTemperaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleTemperaturesRepository.findAll().size();

        // Create the VehicleTemperatures with an existing ID
        vehicleTemperatures.setId(1L);
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVehicleRegistrationIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setVehicleRegistration(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChillerSensorNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setChillerSensorNumber(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecordedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setRecordedWhen(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemperatureIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setTemperature(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCompressedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleTemperaturesRepository.findAll().size();
        // set the field null
        vehicleTemperatures.setIsCompressed(null);

        // Create the VehicleTemperatures, which fails.
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(post("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleTemperatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleRegistration").value(hasItem(DEFAULT_VEHICLE_REGISTRATION)))
            .andExpect(jsonPath("$.[*].chillerSensorNumber").value(hasItem(DEFAULT_CHILLER_SENSOR_NUMBER)))
            .andExpect(jsonPath("$.[*].recordedWhen").value(hasItem(DEFAULT_RECORDED_WHEN)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())))
            .andExpect(jsonPath("$.[*].isCompressed").value(hasItem(DEFAULT_IS_COMPRESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].fullSensorData").value(hasItem(DEFAULT_FULL_SENSOR_DATA)))
            .andExpect(jsonPath("$.[*].compressedSensorData").value(hasItem(DEFAULT_COMPRESSED_SENSOR_DATA)));
    }
    
    @Test
    @Transactional
    public void getVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get the vehicleTemperatures
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures/{id}", vehicleTemperatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleTemperatures.getId().intValue()))
            .andExpect(jsonPath("$.vehicleRegistration").value(DEFAULT_VEHICLE_REGISTRATION))
            .andExpect(jsonPath("$.chillerSensorNumber").value(DEFAULT_CHILLER_SENSOR_NUMBER))
            .andExpect(jsonPath("$.recordedWhen").value(DEFAULT_RECORDED_WHEN))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.intValue()))
            .andExpect(jsonPath("$.isCompressed").value(DEFAULT_IS_COMPRESSED.booleanValue()))
            .andExpect(jsonPath("$.fullSensorData").value(DEFAULT_FULL_SENSOR_DATA))
            .andExpect(jsonPath("$.compressedSensorData").value(DEFAULT_COMPRESSED_SENSOR_DATA));
    }


    @Test
    @Transactional
    public void getVehicleTemperaturesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        Long id = vehicleTemperatures.getId();

        defaultVehicleTemperaturesShouldBeFound("id.equals=" + id);
        defaultVehicleTemperaturesShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleTemperaturesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleTemperaturesShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleTemperaturesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleTemperaturesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration equals to DEFAULT_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.equals=" + DEFAULT_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration equals to UPDATED_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.equals=" + UPDATED_VEHICLE_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration not equals to DEFAULT_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.notEquals=" + DEFAULT_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration not equals to UPDATED_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.notEquals=" + UPDATED_VEHICLE_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration in DEFAULT_VEHICLE_REGISTRATION or UPDATED_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.in=" + DEFAULT_VEHICLE_REGISTRATION + "," + UPDATED_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration equals to UPDATED_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.in=" + UPDATED_VEHICLE_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration is not null
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.specified=true");

        // Get all the vehicleTemperaturesList where vehicleRegistration is null
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration is greater than or equal to DEFAULT_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.greaterThanOrEqual=" + DEFAULT_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration is greater than or equal to UPDATED_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.greaterThanOrEqual=" + UPDATED_VEHICLE_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration is less than or equal to DEFAULT_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.lessThanOrEqual=" + DEFAULT_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration is less than or equal to SMALLER_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.lessThanOrEqual=" + SMALLER_VEHICLE_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration is less than DEFAULT_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.lessThan=" + DEFAULT_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration is less than UPDATED_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.lessThan=" + UPDATED_VEHICLE_REGISTRATION);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByVehicleRegistrationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where vehicleRegistration is greater than DEFAULT_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldNotBeFound("vehicleRegistration.greaterThan=" + DEFAULT_VEHICLE_REGISTRATION);

        // Get all the vehicleTemperaturesList where vehicleRegistration is greater than SMALLER_VEHICLE_REGISTRATION
        defaultVehicleTemperaturesShouldBeFound("vehicleRegistration.greaterThan=" + SMALLER_VEHICLE_REGISTRATION);
    }


    @Test
    @Transactional
    public void getAllVehicleTemperaturesByChillerSensorNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where chillerSensorNumber equals to DEFAULT_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldBeFound("chillerSensorNumber.equals=" + DEFAULT_CHILLER_SENSOR_NUMBER);

        // Get all the vehicleTemperaturesList where chillerSensorNumber equals to UPDATED_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldNotBeFound("chillerSensorNumber.equals=" + UPDATED_CHILLER_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByChillerSensorNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where chillerSensorNumber not equals to DEFAULT_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldNotBeFound("chillerSensorNumber.notEquals=" + DEFAULT_CHILLER_SENSOR_NUMBER);

        // Get all the vehicleTemperaturesList where chillerSensorNumber not equals to UPDATED_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldBeFound("chillerSensorNumber.notEquals=" + UPDATED_CHILLER_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByChillerSensorNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where chillerSensorNumber in DEFAULT_CHILLER_SENSOR_NUMBER or UPDATED_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldBeFound("chillerSensorNumber.in=" + DEFAULT_CHILLER_SENSOR_NUMBER + "," + UPDATED_CHILLER_SENSOR_NUMBER);

        // Get all the vehicleTemperaturesList where chillerSensorNumber equals to UPDATED_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldNotBeFound("chillerSensorNumber.in=" + UPDATED_CHILLER_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByChillerSensorNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where chillerSensorNumber is not null
        defaultVehicleTemperaturesShouldBeFound("chillerSensorNumber.specified=true");

        // Get all the vehicleTemperaturesList where chillerSensorNumber is null
        defaultVehicleTemperaturesShouldNotBeFound("chillerSensorNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehicleTemperaturesByChillerSensorNumberContainsSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where chillerSensorNumber contains DEFAULT_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldBeFound("chillerSensorNumber.contains=" + DEFAULT_CHILLER_SENSOR_NUMBER);

        // Get all the vehicleTemperaturesList where chillerSensorNumber contains UPDATED_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldNotBeFound("chillerSensorNumber.contains=" + UPDATED_CHILLER_SENSOR_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByChillerSensorNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where chillerSensorNumber does not contain DEFAULT_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldNotBeFound("chillerSensorNumber.doesNotContain=" + DEFAULT_CHILLER_SENSOR_NUMBER);

        // Get all the vehicleTemperaturesList where chillerSensorNumber does not contain UPDATED_CHILLER_SENSOR_NUMBER
        defaultVehicleTemperaturesShouldBeFound("chillerSensorNumber.doesNotContain=" + UPDATED_CHILLER_SENSOR_NUMBER);
    }


    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen equals to DEFAULT_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.equals=" + DEFAULT_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen equals to UPDATED_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.equals=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen not equals to DEFAULT_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.notEquals=" + DEFAULT_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen not equals to UPDATED_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.notEquals=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen in DEFAULT_RECORDED_WHEN or UPDATED_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.in=" + DEFAULT_RECORDED_WHEN + "," + UPDATED_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen equals to UPDATED_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.in=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen is not null
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.specified=true");

        // Get all the vehicleTemperaturesList where recordedWhen is null
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen is greater than or equal to DEFAULT_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.greaterThanOrEqual=" + DEFAULT_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen is greater than or equal to UPDATED_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.greaterThanOrEqual=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen is less than or equal to DEFAULT_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.lessThanOrEqual=" + DEFAULT_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen is less than or equal to SMALLER_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.lessThanOrEqual=" + SMALLER_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen is less than DEFAULT_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.lessThan=" + DEFAULT_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen is less than UPDATED_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.lessThan=" + UPDATED_RECORDED_WHEN);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByRecordedWhenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where recordedWhen is greater than DEFAULT_RECORDED_WHEN
        defaultVehicleTemperaturesShouldNotBeFound("recordedWhen.greaterThan=" + DEFAULT_RECORDED_WHEN);

        // Get all the vehicleTemperaturesList where recordedWhen is greater than SMALLER_RECORDED_WHEN
        defaultVehicleTemperaturesShouldBeFound("recordedWhen.greaterThan=" + SMALLER_RECORDED_WHEN);
    }


    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature equals to DEFAULT_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature equals to UPDATED_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature not equals to DEFAULT_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature not equals to UPDATED_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature equals to UPDATED_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature is not null
        defaultVehicleTemperaturesShouldBeFound("temperature.specified=true");

        // Get all the vehicleTemperaturesList where temperature is null
        defaultVehicleTemperaturesShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature is greater than or equal to DEFAULT_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.greaterThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature is greater than or equal to UPDATED_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.greaterThanOrEqual=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature is less than or equal to DEFAULT_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.lessThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature is less than or equal to SMALLER_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.lessThanOrEqual=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature is less than DEFAULT_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.lessThan=" + DEFAULT_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature is less than UPDATED_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.lessThan=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where temperature is greater than DEFAULT_TEMPERATURE
        defaultVehicleTemperaturesShouldNotBeFound("temperature.greaterThan=" + DEFAULT_TEMPERATURE);

        // Get all the vehicleTemperaturesList where temperature is greater than SMALLER_TEMPERATURE
        defaultVehicleTemperaturesShouldBeFound("temperature.greaterThan=" + SMALLER_TEMPERATURE);
    }


    @Test
    @Transactional
    public void getAllVehicleTemperaturesByIsCompressedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where isCompressed equals to DEFAULT_IS_COMPRESSED
        defaultVehicleTemperaturesShouldBeFound("isCompressed.equals=" + DEFAULT_IS_COMPRESSED);

        // Get all the vehicleTemperaturesList where isCompressed equals to UPDATED_IS_COMPRESSED
        defaultVehicleTemperaturesShouldNotBeFound("isCompressed.equals=" + UPDATED_IS_COMPRESSED);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByIsCompressedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where isCompressed not equals to DEFAULT_IS_COMPRESSED
        defaultVehicleTemperaturesShouldNotBeFound("isCompressed.notEquals=" + DEFAULT_IS_COMPRESSED);

        // Get all the vehicleTemperaturesList where isCompressed not equals to UPDATED_IS_COMPRESSED
        defaultVehicleTemperaturesShouldBeFound("isCompressed.notEquals=" + UPDATED_IS_COMPRESSED);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByIsCompressedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where isCompressed in DEFAULT_IS_COMPRESSED or UPDATED_IS_COMPRESSED
        defaultVehicleTemperaturesShouldBeFound("isCompressed.in=" + DEFAULT_IS_COMPRESSED + "," + UPDATED_IS_COMPRESSED);

        // Get all the vehicleTemperaturesList where isCompressed equals to UPDATED_IS_COMPRESSED
        defaultVehicleTemperaturesShouldNotBeFound("isCompressed.in=" + UPDATED_IS_COMPRESSED);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByIsCompressedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where isCompressed is not null
        defaultVehicleTemperaturesShouldBeFound("isCompressed.specified=true");

        // Get all the vehicleTemperaturesList where isCompressed is null
        defaultVehicleTemperaturesShouldNotBeFound("isCompressed.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByFullSensorDataIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where fullSensorData equals to DEFAULT_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("fullSensorData.equals=" + DEFAULT_FULL_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where fullSensorData equals to UPDATED_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("fullSensorData.equals=" + UPDATED_FULL_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByFullSensorDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where fullSensorData not equals to DEFAULT_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("fullSensorData.notEquals=" + DEFAULT_FULL_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where fullSensorData not equals to UPDATED_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("fullSensorData.notEquals=" + UPDATED_FULL_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByFullSensorDataIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where fullSensorData in DEFAULT_FULL_SENSOR_DATA or UPDATED_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("fullSensorData.in=" + DEFAULT_FULL_SENSOR_DATA + "," + UPDATED_FULL_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where fullSensorData equals to UPDATED_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("fullSensorData.in=" + UPDATED_FULL_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByFullSensorDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where fullSensorData is not null
        defaultVehicleTemperaturesShouldBeFound("fullSensorData.specified=true");

        // Get all the vehicleTemperaturesList where fullSensorData is null
        defaultVehicleTemperaturesShouldNotBeFound("fullSensorData.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehicleTemperaturesByFullSensorDataContainsSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where fullSensorData contains DEFAULT_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("fullSensorData.contains=" + DEFAULT_FULL_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where fullSensorData contains UPDATED_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("fullSensorData.contains=" + UPDATED_FULL_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByFullSensorDataNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where fullSensorData does not contain DEFAULT_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("fullSensorData.doesNotContain=" + DEFAULT_FULL_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where fullSensorData does not contain UPDATED_FULL_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("fullSensorData.doesNotContain=" + UPDATED_FULL_SENSOR_DATA);
    }


    @Test
    @Transactional
    public void getAllVehicleTemperaturesByCompressedSensorDataIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where compressedSensorData equals to DEFAULT_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("compressedSensorData.equals=" + DEFAULT_COMPRESSED_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where compressedSensorData equals to UPDATED_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("compressedSensorData.equals=" + UPDATED_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByCompressedSensorDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where compressedSensorData not equals to DEFAULT_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("compressedSensorData.notEquals=" + DEFAULT_COMPRESSED_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where compressedSensorData not equals to UPDATED_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("compressedSensorData.notEquals=" + UPDATED_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByCompressedSensorDataIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where compressedSensorData in DEFAULT_COMPRESSED_SENSOR_DATA or UPDATED_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("compressedSensorData.in=" + DEFAULT_COMPRESSED_SENSOR_DATA + "," + UPDATED_COMPRESSED_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where compressedSensorData equals to UPDATED_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("compressedSensorData.in=" + UPDATED_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByCompressedSensorDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where compressedSensorData is not null
        defaultVehicleTemperaturesShouldBeFound("compressedSensorData.specified=true");

        // Get all the vehicleTemperaturesList where compressedSensorData is null
        defaultVehicleTemperaturesShouldNotBeFound("compressedSensorData.specified=false");
    }
                @Test
    @Transactional
    public void getAllVehicleTemperaturesByCompressedSensorDataContainsSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where compressedSensorData contains DEFAULT_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("compressedSensorData.contains=" + DEFAULT_COMPRESSED_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where compressedSensorData contains UPDATED_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("compressedSensorData.contains=" + UPDATED_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void getAllVehicleTemperaturesByCompressedSensorDataNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        // Get all the vehicleTemperaturesList where compressedSensorData does not contain DEFAULT_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldNotBeFound("compressedSensorData.doesNotContain=" + DEFAULT_COMPRESSED_SENSOR_DATA);

        // Get all the vehicleTemperaturesList where compressedSensorData does not contain UPDATED_COMPRESSED_SENSOR_DATA
        defaultVehicleTemperaturesShouldBeFound("compressedSensorData.doesNotContain=" + UPDATED_COMPRESSED_SENSOR_DATA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleTemperaturesShouldBeFound(String filter) throws Exception {
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleTemperatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleRegistration").value(hasItem(DEFAULT_VEHICLE_REGISTRATION)))
            .andExpect(jsonPath("$.[*].chillerSensorNumber").value(hasItem(DEFAULT_CHILLER_SENSOR_NUMBER)))
            .andExpect(jsonPath("$.[*].recordedWhen").value(hasItem(DEFAULT_RECORDED_WHEN)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.intValue())))
            .andExpect(jsonPath("$.[*].isCompressed").value(hasItem(DEFAULT_IS_COMPRESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].fullSensorData").value(hasItem(DEFAULT_FULL_SENSOR_DATA)))
            .andExpect(jsonPath("$.[*].compressedSensorData").value(hasItem(DEFAULT_COMPRESSED_SENSOR_DATA)));

        // Check, that the count call also returns 1
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleTemperaturesShouldNotBeFound(String filter) throws Exception {
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVehicleTemperatures() throws Exception {
        // Get the vehicleTemperatures
        restVehicleTemperaturesMockMvc.perform(get("/api/vehicle-temperatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        int databaseSizeBeforeUpdate = vehicleTemperaturesRepository.findAll().size();

        // Update the vehicleTemperatures
        VehicleTemperatures updatedVehicleTemperatures = vehicleTemperaturesRepository.findById(vehicleTemperatures.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleTemperatures are not directly saved in db
        em.detach(updatedVehicleTemperatures);
        updatedVehicleTemperatures
            .vehicleRegistration(UPDATED_VEHICLE_REGISTRATION)
            .chillerSensorNumber(UPDATED_CHILLER_SENSOR_NUMBER)
            .recordedWhen(UPDATED_RECORDED_WHEN)
            .temperature(UPDATED_TEMPERATURE)
            .isCompressed(UPDATED_IS_COMPRESSED)
            .fullSensorData(UPDATED_FULL_SENSOR_DATA)
            .compressedSensorData(UPDATED_COMPRESSED_SENSOR_DATA);
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(updatedVehicleTemperatures);

        restVehicleTemperaturesMockMvc.perform(put("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeUpdate);
        VehicleTemperatures testVehicleTemperatures = vehicleTemperaturesList.get(vehicleTemperaturesList.size() - 1);
        assertThat(testVehicleTemperatures.getVehicleRegistration()).isEqualTo(UPDATED_VEHICLE_REGISTRATION);
        assertThat(testVehicleTemperatures.getChillerSensorNumber()).isEqualTo(UPDATED_CHILLER_SENSOR_NUMBER);
        assertThat(testVehicleTemperatures.getRecordedWhen()).isEqualTo(UPDATED_RECORDED_WHEN);
        assertThat(testVehicleTemperatures.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testVehicleTemperatures.isIsCompressed()).isEqualTo(UPDATED_IS_COMPRESSED);
        assertThat(testVehicleTemperatures.getFullSensorData()).isEqualTo(UPDATED_FULL_SENSOR_DATA);
        assertThat(testVehicleTemperatures.getCompressedSensorData()).isEqualTo(UPDATED_COMPRESSED_SENSOR_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleTemperatures() throws Exception {
        int databaseSizeBeforeUpdate = vehicleTemperaturesRepository.findAll().size();

        // Create the VehicleTemperatures
        VehicleTemperaturesDTO vehicleTemperaturesDTO = vehicleTemperaturesMapper.toDto(vehicleTemperatures);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleTemperaturesMockMvc.perform(put("/api/vehicle-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTemperaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleTemperatures in the database
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicleTemperatures() throws Exception {
        // Initialize the database
        vehicleTemperaturesRepository.saveAndFlush(vehicleTemperatures);

        int databaseSizeBeforeDelete = vehicleTemperaturesRepository.findAll().size();

        // Delete the vehicleTemperatures
        restVehicleTemperaturesMockMvc.perform(delete("/api/vehicle-temperatures/{id}", vehicleTemperatures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleTemperatures> vehicleTemperaturesList = vehicleTemperaturesRepository.findAll();
        assertThat(vehicleTemperaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
