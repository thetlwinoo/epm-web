package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Cities;
import com.epmweb.server.domain.StateProvinces;
import com.epmweb.server.repository.CitiesRepository;
import com.epmweb.server.service.CitiesService;
import com.epmweb.server.service.dto.CitiesDTO;
import com.epmweb.server.service.mapper.CitiesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.CitiesCriteria;
import com.epmweb.server.service.CitiesQueryService;

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
 * Integration tests for the {@link CitiesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class CitiesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Long DEFAULT_LATEST_RECORDED_POPULATION = 1L;
    private static final Long UPDATED_LATEST_RECORDED_POPULATION = 2L;
    private static final Long SMALLER_LATEST_RECORDED_POPULATION = 1L - 1L;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private CitiesMapper citiesMapper;

    @Autowired
    private CitiesService citiesService;

    @Autowired
    private CitiesQueryService citiesQueryService;

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

    private MockMvc restCitiesMockMvc;

    private Cities cities;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CitiesResource citiesResource = new CitiesResource(citiesService, citiesQueryService);
        this.restCitiesMockMvc = MockMvcBuilders.standaloneSetup(citiesResource)
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
    public static Cities createEntity(EntityManager em) {
        Cities cities = new Cities()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION)
            .latestRecordedPopulation(DEFAULT_LATEST_RECORDED_POPULATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return cities;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createUpdatedEntity(EntityManager em) {
        Cities cities = new Cities()
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return cities;
    }

    @BeforeEach
    public void initTest() {
        cities = createEntity(em);
    }

    @Test
    @Transactional
    public void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);
        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCities.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCities.getLatestRecordedPopulation()).isEqualTo(DEFAULT_LATEST_RECORDED_POPULATION);
        assertThat(testCities.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCities.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // Create the Cities with an existing ID
        cities.setId(1L);
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setName(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setValidFrom(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setValidTo(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList
        restCitiesMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get the cities
        restCitiesMockMvc.perform(get("/api/cities/{id}", cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cities.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.latestRecordedPopulation").value(DEFAULT_LATEST_RECORDED_POPULATION.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getCitiesByIdFiltering() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        Long id = cities.getId();

        defaultCitiesShouldBeFound("id.equals=" + id);
        defaultCitiesShouldNotBeFound("id.notEquals=" + id);

        defaultCitiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.greaterThan=" + id);

        defaultCitiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name equals to DEFAULT_NAME
        defaultCitiesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citiesList where name equals to UPDATED_NAME
        defaultCitiesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name not equals to DEFAULT_NAME
        defaultCitiesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citiesList where name not equals to UPDATED_NAME
        defaultCitiesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitiesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citiesList where name equals to UPDATED_NAME
        defaultCitiesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name is not null
        defaultCitiesShouldBeFound("name.specified=true");

        // Get all the citiesList where name is null
        defaultCitiesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCitiesByNameContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name contains DEFAULT_NAME
        defaultCitiesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citiesList where name contains UPDATED_NAME
        defaultCitiesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name does not contain DEFAULT_NAME
        defaultCitiesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citiesList where name does not contain UPDATED_NAME
        defaultCitiesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCitiesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where location equals to DEFAULT_LOCATION
        defaultCitiesShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the citiesList where location equals to UPDATED_LOCATION
        defaultCitiesShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where location not equals to DEFAULT_LOCATION
        defaultCitiesShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the citiesList where location not equals to UPDATED_LOCATION
        defaultCitiesShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultCitiesShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the citiesList where location equals to UPDATED_LOCATION
        defaultCitiesShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where location is not null
        defaultCitiesShouldBeFound("location.specified=true");

        // Get all the citiesList where location is null
        defaultCitiesShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllCitiesByLocationContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where location contains DEFAULT_LOCATION
        defaultCitiesShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the citiesList where location contains UPDATED_LOCATION
        defaultCitiesShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where location does not contain DEFAULT_LOCATION
        defaultCitiesShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the citiesList where location does not contain UPDATED_LOCATION
        defaultCitiesShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation equals to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.equals=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.equals=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation not equals to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.notEquals=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation not equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.notEquals=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation in DEFAULT_LATEST_RECORDED_POPULATION or UPDATED_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.in=" + DEFAULT_LATEST_RECORDED_POPULATION + "," + UPDATED_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.in=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation is not null
        defaultCitiesShouldBeFound("latestRecordedPopulation.specified=true");

        // Get all the citiesList where latestRecordedPopulation is null
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation is greater than or equal to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.greaterThanOrEqual=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation is greater than or equal to UPDATED_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.greaterThanOrEqual=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation is less than or equal to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.lessThanOrEqual=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation is less than or equal to SMALLER_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.lessThanOrEqual=" + SMALLER_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsLessThanSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation is less than DEFAULT_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.lessThan=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation is less than UPDATED_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.lessThan=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCitiesByLatestRecordedPopulationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latestRecordedPopulation is greater than DEFAULT_LATEST_RECORDED_POPULATION
        defaultCitiesShouldNotBeFound("latestRecordedPopulation.greaterThan=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the citiesList where latestRecordedPopulation is greater than SMALLER_LATEST_RECORDED_POPULATION
        defaultCitiesShouldBeFound("latestRecordedPopulation.greaterThan=" + SMALLER_LATEST_RECORDED_POPULATION);
    }


    @Test
    @Transactional
    public void getAllCitiesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validFrom equals to DEFAULT_VALID_FROM
        defaultCitiesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the citiesList where validFrom equals to UPDATED_VALID_FROM
        defaultCitiesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCitiesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCitiesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the citiesList where validFrom not equals to UPDATED_VALID_FROM
        defaultCitiesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCitiesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCitiesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the citiesList where validFrom equals to UPDATED_VALID_FROM
        defaultCitiesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCitiesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validFrom is not null
        defaultCitiesShouldBeFound("validFrom.specified=true");

        // Get all the citiesList where validFrom is null
        defaultCitiesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validTo equals to DEFAULT_VALID_TO
        defaultCitiesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the citiesList where validTo equals to UPDATED_VALID_TO
        defaultCitiesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCitiesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validTo not equals to DEFAULT_VALID_TO
        defaultCitiesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the citiesList where validTo not equals to UPDATED_VALID_TO
        defaultCitiesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCitiesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCitiesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the citiesList where validTo equals to UPDATED_VALID_TO
        defaultCitiesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCitiesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where validTo is not null
        defaultCitiesShouldBeFound("validTo.specified=true");

        // Get all the citiesList where validTo is null
        defaultCitiesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        StateProvinces stateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvince);
        em.flush();
        cities.setStateProvince(stateProvince);
        citiesRepository.saveAndFlush(cities);
        Long stateProvinceId = stateProvince.getId();

        // Get all the citiesList where stateProvince equals to stateProvinceId
        defaultCitiesShouldBeFound("stateProvinceId.equals=" + stateProvinceId);

        // Get all the citiesList where stateProvince equals to stateProvinceId + 1
        defaultCitiesShouldNotBeFound("stateProvinceId.equals=" + (stateProvinceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitiesShouldBeFound(String filter) throws Exception {
        restCitiesMockMvc.perform(get("/api/cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restCitiesMockMvc.perform(get("/api/cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitiesShouldNotBeFound(String filter) throws Exception {
        restCitiesMockMvc.perform(get("/api/cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitiesMockMvc.perform(get("/api/cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        Cities updatedCities = citiesRepository.findById(cities.getId()).get();
        // Disconnect from session so that the updates on updatedCities are not directly saved in db
        em.detach(updatedCities);
        updatedCities
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CitiesDTO citiesDTO = citiesMapper.toDto(updatedCities);

        restCitiesMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCities.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCities.getLatestRecordedPopulation()).isEqualTo(UPDATED_LATEST_RECORDED_POPULATION);
        assertThat(testCities.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCities.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Delete the cities
        restCitiesMockMvc.perform(delete("/api/cities/{id}", cities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
