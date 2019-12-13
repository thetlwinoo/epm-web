package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Countries;
import com.epmweb.server.repository.CountriesRepository;
import com.epmweb.server.service.CountriesService;
import com.epmweb.server.service.dto.CountriesDTO;
import com.epmweb.server.service.mapper.CountriesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.CountriesCriteria;
import com.epmweb.server.service.CountriesQueryService;

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
 * Integration tests for the {@link CountriesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class CountriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISO_APLHA_3_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ISO_APLHA_3_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISO_NUMERIC_CODE = 1;
    private static final Integer UPDATED_ISO_NUMERIC_CODE = 2;
    private static final Integer SMALLER_ISO_NUMERIC_CODE = 1 - 1;

    private static final String DEFAULT_COUNTRY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_LATEST_RECORDED_POPULATION = 1L;
    private static final Long UPDATED_LATEST_RECORDED_POPULATION = 2L;
    private static final Long SMALLER_LATEST_RECORDED_POPULATION = 1L - 1L;

    private static final String DEFAULT_CONTINENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTINENT = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBREGION = "AAAAAAAAAA";
    private static final String UPDATED_SUBREGION = "BBBBBBBBBB";

    private static final String DEFAULT_BORDER = "AAAAAAAAAA";
    private static final String UPDATED_BORDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CountriesMapper countriesMapper;

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CountriesQueryService countriesQueryService;

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

    private MockMvc restCountriesMockMvc;

    private Countries countries;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountriesResource countriesResource = new CountriesResource(countriesService, countriesQueryService);
        this.restCountriesMockMvc = MockMvcBuilders.standaloneSetup(countriesResource)
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
    public static Countries createEntity(EntityManager em) {
        Countries countries = new Countries()
            .name(DEFAULT_NAME)
            .formalName(DEFAULT_FORMAL_NAME)
            .isoAplha3Code(DEFAULT_ISO_APLHA_3_CODE)
            .isoNumericCode(DEFAULT_ISO_NUMERIC_CODE)
            .countryType(DEFAULT_COUNTRY_TYPE)
            .latestRecordedPopulation(DEFAULT_LATEST_RECORDED_POPULATION)
            .continent(DEFAULT_CONTINENT)
            .region(DEFAULT_REGION)
            .subregion(DEFAULT_SUBREGION)
            .border(DEFAULT_BORDER)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return countries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createUpdatedEntity(EntityManager em) {
        Countries countries = new Countries()
            .name(UPDATED_NAME)
            .formalName(UPDATED_FORMAL_NAME)
            .isoAplha3Code(UPDATED_ISO_APLHA_3_CODE)
            .isoNumericCode(UPDATED_ISO_NUMERIC_CODE)
            .countryType(UPDATED_COUNTRY_TYPE)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .continent(UPDATED_CONTINENT)
            .region(UPDATED_REGION)
            .subregion(UPDATED_SUBREGION)
            .border(UPDATED_BORDER)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return countries;
    }

    @BeforeEach
    public void initTest() {
        countries = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountries() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);
        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate + 1);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountries.getFormalName()).isEqualTo(DEFAULT_FORMAL_NAME);
        assertThat(testCountries.getIsoAplha3Code()).isEqualTo(DEFAULT_ISO_APLHA_3_CODE);
        assertThat(testCountries.getIsoNumericCode()).isEqualTo(DEFAULT_ISO_NUMERIC_CODE);
        assertThat(testCountries.getCountryType()).isEqualTo(DEFAULT_COUNTRY_TYPE);
        assertThat(testCountries.getLatestRecordedPopulation()).isEqualTo(DEFAULT_LATEST_RECORDED_POPULATION);
        assertThat(testCountries.getContinent()).isEqualTo(DEFAULT_CONTINENT);
        assertThat(testCountries.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCountries.getSubregion()).isEqualTo(DEFAULT_SUBREGION);
        assertThat(testCountries.getBorder()).isEqualTo(DEFAULT_BORDER);
        assertThat(testCountries.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCountries.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCountriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // Create the Countries with an existing ID
        countries.setId(1L);
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setName(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setFormalName(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContinentIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setContinent(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setRegion(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubregionIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setSubregion(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setValidFrom(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setValidTo(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList
        restCountriesMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].formalName").value(hasItem(DEFAULT_FORMAL_NAME)))
            .andExpect(jsonPath("$.[*].isoAplha3Code").value(hasItem(DEFAULT_ISO_APLHA_3_CODE)))
            .andExpect(jsonPath("$.[*].isoNumericCode").value(hasItem(DEFAULT_ISO_NUMERIC_CODE)))
            .andExpect(jsonPath("$.[*].countryType").value(hasItem(DEFAULT_COUNTRY_TYPE)))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].subregion").value(hasItem(DEFAULT_SUBREGION)))
            .andExpect(jsonPath("$.[*].border").value(hasItem(DEFAULT_BORDER)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get the countries
        restCountriesMockMvc.perform(get("/api/countries/{id}", countries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countries.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.formalName").value(DEFAULT_FORMAL_NAME))
            .andExpect(jsonPath("$.isoAplha3Code").value(DEFAULT_ISO_APLHA_3_CODE))
            .andExpect(jsonPath("$.isoNumericCode").value(DEFAULT_ISO_NUMERIC_CODE))
            .andExpect(jsonPath("$.countryType").value(DEFAULT_COUNTRY_TYPE))
            .andExpect(jsonPath("$.latestRecordedPopulation").value(DEFAULT_LATEST_RECORDED_POPULATION.intValue()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.subregion").value(DEFAULT_SUBREGION))
            .andExpect(jsonPath("$.border").value(DEFAULT_BORDER))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        Long id = countries.getId();

        defaultCountriesShouldBeFound("id.equals=" + id);
        defaultCountriesShouldNotBeFound("id.notEquals=" + id);

        defaultCountriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountriesShouldNotBeFound("id.greaterThan=" + id);

        defaultCountriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountriesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name equals to DEFAULT_NAME
        defaultCountriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countriesList where name equals to UPDATED_NAME
        defaultCountriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name not equals to DEFAULT_NAME
        defaultCountriesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countriesList where name not equals to UPDATED_NAME
        defaultCountriesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countriesList where name equals to UPDATED_NAME
        defaultCountriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name is not null
        defaultCountriesShouldBeFound("name.specified=true");

        // Get all the countriesList where name is null
        defaultCountriesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByNameContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name contains DEFAULT_NAME
        defaultCountriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countriesList where name contains UPDATED_NAME
        defaultCountriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name does not contain DEFAULT_NAME
        defaultCountriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countriesList where name does not contain UPDATED_NAME
        defaultCountriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCountriesByFormalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where formalName equals to DEFAULT_FORMAL_NAME
        defaultCountriesShouldBeFound("formalName.equals=" + DEFAULT_FORMAL_NAME);

        // Get all the countriesList where formalName equals to UPDATED_FORMAL_NAME
        defaultCountriesShouldNotBeFound("formalName.equals=" + UPDATED_FORMAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByFormalNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where formalName not equals to DEFAULT_FORMAL_NAME
        defaultCountriesShouldNotBeFound("formalName.notEquals=" + DEFAULT_FORMAL_NAME);

        // Get all the countriesList where formalName not equals to UPDATED_FORMAL_NAME
        defaultCountriesShouldBeFound("formalName.notEquals=" + UPDATED_FORMAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByFormalNameIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where formalName in DEFAULT_FORMAL_NAME or UPDATED_FORMAL_NAME
        defaultCountriesShouldBeFound("formalName.in=" + DEFAULT_FORMAL_NAME + "," + UPDATED_FORMAL_NAME);

        // Get all the countriesList where formalName equals to UPDATED_FORMAL_NAME
        defaultCountriesShouldNotBeFound("formalName.in=" + UPDATED_FORMAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByFormalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where formalName is not null
        defaultCountriesShouldBeFound("formalName.specified=true");

        // Get all the countriesList where formalName is null
        defaultCountriesShouldNotBeFound("formalName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByFormalNameContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where formalName contains DEFAULT_FORMAL_NAME
        defaultCountriesShouldBeFound("formalName.contains=" + DEFAULT_FORMAL_NAME);

        // Get all the countriesList where formalName contains UPDATED_FORMAL_NAME
        defaultCountriesShouldNotBeFound("formalName.contains=" + UPDATED_FORMAL_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByFormalNameNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where formalName does not contain DEFAULT_FORMAL_NAME
        defaultCountriesShouldNotBeFound("formalName.doesNotContain=" + DEFAULT_FORMAL_NAME);

        // Get all the countriesList where formalName does not contain UPDATED_FORMAL_NAME
        defaultCountriesShouldBeFound("formalName.doesNotContain=" + UPDATED_FORMAL_NAME);
    }


    @Test
    @Transactional
    public void getAllCountriesByIsoAplha3CodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoAplha3Code equals to DEFAULT_ISO_APLHA_3_CODE
        defaultCountriesShouldBeFound("isoAplha3Code.equals=" + DEFAULT_ISO_APLHA_3_CODE);

        // Get all the countriesList where isoAplha3Code equals to UPDATED_ISO_APLHA_3_CODE
        defaultCountriesShouldNotBeFound("isoAplha3Code.equals=" + UPDATED_ISO_APLHA_3_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoAplha3CodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoAplha3Code not equals to DEFAULT_ISO_APLHA_3_CODE
        defaultCountriesShouldNotBeFound("isoAplha3Code.notEquals=" + DEFAULT_ISO_APLHA_3_CODE);

        // Get all the countriesList where isoAplha3Code not equals to UPDATED_ISO_APLHA_3_CODE
        defaultCountriesShouldBeFound("isoAplha3Code.notEquals=" + UPDATED_ISO_APLHA_3_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoAplha3CodeIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoAplha3Code in DEFAULT_ISO_APLHA_3_CODE or UPDATED_ISO_APLHA_3_CODE
        defaultCountriesShouldBeFound("isoAplha3Code.in=" + DEFAULT_ISO_APLHA_3_CODE + "," + UPDATED_ISO_APLHA_3_CODE);

        // Get all the countriesList where isoAplha3Code equals to UPDATED_ISO_APLHA_3_CODE
        defaultCountriesShouldNotBeFound("isoAplha3Code.in=" + UPDATED_ISO_APLHA_3_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoAplha3CodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoAplha3Code is not null
        defaultCountriesShouldBeFound("isoAplha3Code.specified=true");

        // Get all the countriesList where isoAplha3Code is null
        defaultCountriesShouldNotBeFound("isoAplha3Code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByIsoAplha3CodeContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoAplha3Code contains DEFAULT_ISO_APLHA_3_CODE
        defaultCountriesShouldBeFound("isoAplha3Code.contains=" + DEFAULT_ISO_APLHA_3_CODE);

        // Get all the countriesList where isoAplha3Code contains UPDATED_ISO_APLHA_3_CODE
        defaultCountriesShouldNotBeFound("isoAplha3Code.contains=" + UPDATED_ISO_APLHA_3_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoAplha3CodeNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoAplha3Code does not contain DEFAULT_ISO_APLHA_3_CODE
        defaultCountriesShouldNotBeFound("isoAplha3Code.doesNotContain=" + DEFAULT_ISO_APLHA_3_CODE);

        // Get all the countriesList where isoAplha3Code does not contain UPDATED_ISO_APLHA_3_CODE
        defaultCountriesShouldBeFound("isoAplha3Code.doesNotContain=" + UPDATED_ISO_APLHA_3_CODE);
    }


    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode equals to DEFAULT_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.equals=" + DEFAULT_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode equals to UPDATED_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.equals=" + UPDATED_ISO_NUMERIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode not equals to DEFAULT_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.notEquals=" + DEFAULT_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode not equals to UPDATED_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.notEquals=" + UPDATED_ISO_NUMERIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode in DEFAULT_ISO_NUMERIC_CODE or UPDATED_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.in=" + DEFAULT_ISO_NUMERIC_CODE + "," + UPDATED_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode equals to UPDATED_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.in=" + UPDATED_ISO_NUMERIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode is not null
        defaultCountriesShouldBeFound("isoNumericCode.specified=true");

        // Get all the countriesList where isoNumericCode is null
        defaultCountriesShouldNotBeFound("isoNumericCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode is greater than or equal to DEFAULT_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.greaterThanOrEqual=" + DEFAULT_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode is greater than or equal to UPDATED_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.greaterThanOrEqual=" + UPDATED_ISO_NUMERIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode is less than or equal to DEFAULT_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.lessThanOrEqual=" + DEFAULT_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode is less than or equal to SMALLER_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.lessThanOrEqual=" + SMALLER_ISO_NUMERIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode is less than DEFAULT_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.lessThan=" + DEFAULT_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode is less than UPDATED_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.lessThan=" + UPDATED_ISO_NUMERIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByIsoNumericCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where isoNumericCode is greater than DEFAULT_ISO_NUMERIC_CODE
        defaultCountriesShouldNotBeFound("isoNumericCode.greaterThan=" + DEFAULT_ISO_NUMERIC_CODE);

        // Get all the countriesList where isoNumericCode is greater than SMALLER_ISO_NUMERIC_CODE
        defaultCountriesShouldBeFound("isoNumericCode.greaterThan=" + SMALLER_ISO_NUMERIC_CODE);
    }


    @Test
    @Transactional
    public void getAllCountriesByCountryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryType equals to DEFAULT_COUNTRY_TYPE
        defaultCountriesShouldBeFound("countryType.equals=" + DEFAULT_COUNTRY_TYPE);

        // Get all the countriesList where countryType equals to UPDATED_COUNTRY_TYPE
        defaultCountriesShouldNotBeFound("countryType.equals=" + UPDATED_COUNTRY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryType not equals to DEFAULT_COUNTRY_TYPE
        defaultCountriesShouldNotBeFound("countryType.notEquals=" + DEFAULT_COUNTRY_TYPE);

        // Get all the countriesList where countryType not equals to UPDATED_COUNTRY_TYPE
        defaultCountriesShouldBeFound("countryType.notEquals=" + UPDATED_COUNTRY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTypeIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryType in DEFAULT_COUNTRY_TYPE or UPDATED_COUNTRY_TYPE
        defaultCountriesShouldBeFound("countryType.in=" + DEFAULT_COUNTRY_TYPE + "," + UPDATED_COUNTRY_TYPE);

        // Get all the countriesList where countryType equals to UPDATED_COUNTRY_TYPE
        defaultCountriesShouldNotBeFound("countryType.in=" + UPDATED_COUNTRY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryType is not null
        defaultCountriesShouldBeFound("countryType.specified=true");

        // Get all the countriesList where countryType is null
        defaultCountriesShouldNotBeFound("countryType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByCountryTypeContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryType contains DEFAULT_COUNTRY_TYPE
        defaultCountriesShouldBeFound("countryType.contains=" + DEFAULT_COUNTRY_TYPE);

        // Get all the countriesList where countryType contains UPDATED_COUNTRY_TYPE
        defaultCountriesShouldNotBeFound("countryType.contains=" + UPDATED_COUNTRY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryTypeNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryType does not contain DEFAULT_COUNTRY_TYPE
        defaultCountriesShouldNotBeFound("countryType.doesNotContain=" + DEFAULT_COUNTRY_TYPE);

        // Get all the countriesList where countryType does not contain UPDATED_COUNTRY_TYPE
        defaultCountriesShouldBeFound("countryType.doesNotContain=" + UPDATED_COUNTRY_TYPE);
    }


    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation equals to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.equals=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.equals=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation not equals to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.notEquals=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation not equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.notEquals=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation in DEFAULT_LATEST_RECORDED_POPULATION or UPDATED_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.in=" + DEFAULT_LATEST_RECORDED_POPULATION + "," + UPDATED_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.in=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation is not null
        defaultCountriesShouldBeFound("latestRecordedPopulation.specified=true");

        // Get all the countriesList where latestRecordedPopulation is null
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation is greater than or equal to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.greaterThanOrEqual=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation is greater than or equal to UPDATED_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.greaterThanOrEqual=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation is less than or equal to DEFAULT_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.lessThanOrEqual=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation is less than or equal to SMALLER_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.lessThanOrEqual=" + SMALLER_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsLessThanSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation is less than DEFAULT_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.lessThan=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation is less than UPDATED_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.lessThan=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllCountriesByLatestRecordedPopulationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where latestRecordedPopulation is greater than DEFAULT_LATEST_RECORDED_POPULATION
        defaultCountriesShouldNotBeFound("latestRecordedPopulation.greaterThan=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the countriesList where latestRecordedPopulation is greater than SMALLER_LATEST_RECORDED_POPULATION
        defaultCountriesShouldBeFound("latestRecordedPopulation.greaterThan=" + SMALLER_LATEST_RECORDED_POPULATION);
    }


    @Test
    @Transactional
    public void getAllCountriesByContinentIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where continent equals to DEFAULT_CONTINENT
        defaultCountriesShouldBeFound("continent.equals=" + DEFAULT_CONTINENT);

        // Get all the countriesList where continent equals to UPDATED_CONTINENT
        defaultCountriesShouldNotBeFound("continent.equals=" + UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where continent not equals to DEFAULT_CONTINENT
        defaultCountriesShouldNotBeFound("continent.notEquals=" + DEFAULT_CONTINENT);

        // Get all the countriesList where continent not equals to UPDATED_CONTINENT
        defaultCountriesShouldBeFound("continent.notEquals=" + UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where continent in DEFAULT_CONTINENT or UPDATED_CONTINENT
        defaultCountriesShouldBeFound("continent.in=" + DEFAULT_CONTINENT + "," + UPDATED_CONTINENT);

        // Get all the countriesList where continent equals to UPDATED_CONTINENT
        defaultCountriesShouldNotBeFound("continent.in=" + UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where continent is not null
        defaultCountriesShouldBeFound("continent.specified=true");

        // Get all the countriesList where continent is null
        defaultCountriesShouldNotBeFound("continent.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByContinentContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where continent contains DEFAULT_CONTINENT
        defaultCountriesShouldBeFound("continent.contains=" + DEFAULT_CONTINENT);

        // Get all the countriesList where continent contains UPDATED_CONTINENT
        defaultCountriesShouldNotBeFound("continent.contains=" + UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void getAllCountriesByContinentNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where continent does not contain DEFAULT_CONTINENT
        defaultCountriesShouldNotBeFound("continent.doesNotContain=" + DEFAULT_CONTINENT);

        // Get all the countriesList where continent does not contain UPDATED_CONTINENT
        defaultCountriesShouldBeFound("continent.doesNotContain=" + UPDATED_CONTINENT);
    }


    @Test
    @Transactional
    public void getAllCountriesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where region equals to DEFAULT_REGION
        defaultCountriesShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the countriesList where region equals to UPDATED_REGION
        defaultCountriesShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where region not equals to DEFAULT_REGION
        defaultCountriesShouldNotBeFound("region.notEquals=" + DEFAULT_REGION);

        // Get all the countriesList where region not equals to UPDATED_REGION
        defaultCountriesShouldBeFound("region.notEquals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where region in DEFAULT_REGION or UPDATED_REGION
        defaultCountriesShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the countriesList where region equals to UPDATED_REGION
        defaultCountriesShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where region is not null
        defaultCountriesShouldBeFound("region.specified=true");

        // Get all the countriesList where region is null
        defaultCountriesShouldNotBeFound("region.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByRegionContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where region contains DEFAULT_REGION
        defaultCountriesShouldBeFound("region.contains=" + DEFAULT_REGION);

        // Get all the countriesList where region contains UPDATED_REGION
        defaultCountriesShouldNotBeFound("region.contains=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    public void getAllCountriesByRegionNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where region does not contain DEFAULT_REGION
        defaultCountriesShouldNotBeFound("region.doesNotContain=" + DEFAULT_REGION);

        // Get all the countriesList where region does not contain UPDATED_REGION
        defaultCountriesShouldBeFound("region.doesNotContain=" + UPDATED_REGION);
    }


    @Test
    @Transactional
    public void getAllCountriesBySubregionIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where subregion equals to DEFAULT_SUBREGION
        defaultCountriesShouldBeFound("subregion.equals=" + DEFAULT_SUBREGION);

        // Get all the countriesList where subregion equals to UPDATED_SUBREGION
        defaultCountriesShouldNotBeFound("subregion.equals=" + UPDATED_SUBREGION);
    }

    @Test
    @Transactional
    public void getAllCountriesBySubregionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where subregion not equals to DEFAULT_SUBREGION
        defaultCountriesShouldNotBeFound("subregion.notEquals=" + DEFAULT_SUBREGION);

        // Get all the countriesList where subregion not equals to UPDATED_SUBREGION
        defaultCountriesShouldBeFound("subregion.notEquals=" + UPDATED_SUBREGION);
    }

    @Test
    @Transactional
    public void getAllCountriesBySubregionIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where subregion in DEFAULT_SUBREGION or UPDATED_SUBREGION
        defaultCountriesShouldBeFound("subregion.in=" + DEFAULT_SUBREGION + "," + UPDATED_SUBREGION);

        // Get all the countriesList where subregion equals to UPDATED_SUBREGION
        defaultCountriesShouldNotBeFound("subregion.in=" + UPDATED_SUBREGION);
    }

    @Test
    @Transactional
    public void getAllCountriesBySubregionIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where subregion is not null
        defaultCountriesShouldBeFound("subregion.specified=true");

        // Get all the countriesList where subregion is null
        defaultCountriesShouldNotBeFound("subregion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesBySubregionContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where subregion contains DEFAULT_SUBREGION
        defaultCountriesShouldBeFound("subregion.contains=" + DEFAULT_SUBREGION);

        // Get all the countriesList where subregion contains UPDATED_SUBREGION
        defaultCountriesShouldNotBeFound("subregion.contains=" + UPDATED_SUBREGION);
    }

    @Test
    @Transactional
    public void getAllCountriesBySubregionNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where subregion does not contain DEFAULT_SUBREGION
        defaultCountriesShouldNotBeFound("subregion.doesNotContain=" + DEFAULT_SUBREGION);

        // Get all the countriesList where subregion does not contain UPDATED_SUBREGION
        defaultCountriesShouldBeFound("subregion.doesNotContain=" + UPDATED_SUBREGION);
    }


    @Test
    @Transactional
    public void getAllCountriesByBorderIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where border equals to DEFAULT_BORDER
        defaultCountriesShouldBeFound("border.equals=" + DEFAULT_BORDER);

        // Get all the countriesList where border equals to UPDATED_BORDER
        defaultCountriesShouldNotBeFound("border.equals=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllCountriesByBorderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where border not equals to DEFAULT_BORDER
        defaultCountriesShouldNotBeFound("border.notEquals=" + DEFAULT_BORDER);

        // Get all the countriesList where border not equals to UPDATED_BORDER
        defaultCountriesShouldBeFound("border.notEquals=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllCountriesByBorderIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where border in DEFAULT_BORDER or UPDATED_BORDER
        defaultCountriesShouldBeFound("border.in=" + DEFAULT_BORDER + "," + UPDATED_BORDER);

        // Get all the countriesList where border equals to UPDATED_BORDER
        defaultCountriesShouldNotBeFound("border.in=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllCountriesByBorderIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where border is not null
        defaultCountriesShouldBeFound("border.specified=true");

        // Get all the countriesList where border is null
        defaultCountriesShouldNotBeFound("border.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountriesByBorderContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where border contains DEFAULT_BORDER
        defaultCountriesShouldBeFound("border.contains=" + DEFAULT_BORDER);

        // Get all the countriesList where border contains UPDATED_BORDER
        defaultCountriesShouldNotBeFound("border.contains=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllCountriesByBorderNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where border does not contain DEFAULT_BORDER
        defaultCountriesShouldNotBeFound("border.doesNotContain=" + DEFAULT_BORDER);

        // Get all the countriesList where border does not contain UPDATED_BORDER
        defaultCountriesShouldBeFound("border.doesNotContain=" + UPDATED_BORDER);
    }


    @Test
    @Transactional
    public void getAllCountriesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validFrom equals to DEFAULT_VALID_FROM
        defaultCountriesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the countriesList where validFrom equals to UPDATED_VALID_FROM
        defaultCountriesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCountriesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCountriesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the countriesList where validFrom not equals to UPDATED_VALID_FROM
        defaultCountriesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCountriesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCountriesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the countriesList where validFrom equals to UPDATED_VALID_FROM
        defaultCountriesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCountriesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validFrom is not null
        defaultCountriesShouldBeFound("validFrom.specified=true");

        // Get all the countriesList where validFrom is null
        defaultCountriesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validTo equals to DEFAULT_VALID_TO
        defaultCountriesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the countriesList where validTo equals to UPDATED_VALID_TO
        defaultCountriesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCountriesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validTo not equals to DEFAULT_VALID_TO
        defaultCountriesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the countriesList where validTo not equals to UPDATED_VALID_TO
        defaultCountriesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCountriesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCountriesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the countriesList where validTo equals to UPDATED_VALID_TO
        defaultCountriesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCountriesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where validTo is not null
        defaultCountriesShouldBeFound("validTo.specified=true");

        // Get all the countriesList where validTo is null
        defaultCountriesShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountriesShouldBeFound(String filter) throws Exception {
        restCountriesMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].formalName").value(hasItem(DEFAULT_FORMAL_NAME)))
            .andExpect(jsonPath("$.[*].isoAplha3Code").value(hasItem(DEFAULT_ISO_APLHA_3_CODE)))
            .andExpect(jsonPath("$.[*].isoNumericCode").value(hasItem(DEFAULT_ISO_NUMERIC_CODE)))
            .andExpect(jsonPath("$.[*].countryType").value(hasItem(DEFAULT_COUNTRY_TYPE)))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].subregion").value(hasItem(DEFAULT_SUBREGION)))
            .andExpect(jsonPath("$.[*].border").value(hasItem(DEFAULT_BORDER)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restCountriesMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountriesShouldNotBeFound(String filter) throws Exception {
        restCountriesMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountriesMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCountries() throws Exception {
        // Get the countries
        restCountriesMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries
        Countries updatedCountries = countriesRepository.findById(countries.getId()).get();
        // Disconnect from session so that the updates on updatedCountries are not directly saved in db
        em.detach(updatedCountries);
        updatedCountries
            .name(UPDATED_NAME)
            .formalName(UPDATED_FORMAL_NAME)
            .isoAplha3Code(UPDATED_ISO_APLHA_3_CODE)
            .isoNumericCode(UPDATED_ISO_NUMERIC_CODE)
            .countryType(UPDATED_COUNTRY_TYPE)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .continent(UPDATED_CONTINENT)
            .region(UPDATED_REGION)
            .subregion(UPDATED_SUBREGION)
            .border(UPDATED_BORDER)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CountriesDTO countriesDTO = countriesMapper.toDto(updatedCountries);

        restCountriesMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountries.getFormalName()).isEqualTo(UPDATED_FORMAL_NAME);
        assertThat(testCountries.getIsoAplha3Code()).isEqualTo(UPDATED_ISO_APLHA_3_CODE);
        assertThat(testCountries.getIsoNumericCode()).isEqualTo(UPDATED_ISO_NUMERIC_CODE);
        assertThat(testCountries.getCountryType()).isEqualTo(UPDATED_COUNTRY_TYPE);
        assertThat(testCountries.getLatestRecordedPopulation()).isEqualTo(UPDATED_LATEST_RECORDED_POPULATION);
        assertThat(testCountries.getContinent()).isEqualTo(UPDATED_CONTINENT);
        assertThat(testCountries.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCountries.getSubregion()).isEqualTo(UPDATED_SUBREGION);
        assertThat(testCountries.getBorder()).isEqualTo(UPDATED_BORDER);
        assertThat(testCountries.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCountries.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeDelete = countriesRepository.findAll().size();

        // Delete the countries
        restCountriesMockMvc.perform(delete("/api/countries/{id}", countries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
