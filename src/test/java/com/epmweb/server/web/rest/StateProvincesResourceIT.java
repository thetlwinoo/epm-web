package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.StateProvinces;
import com.epmweb.server.domain.Countries;
import com.epmweb.server.repository.StateProvincesRepository;
import com.epmweb.server.service.StateProvincesService;
import com.epmweb.server.service.dto.StateProvincesDTO;
import com.epmweb.server.service.mapper.StateProvincesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.StateProvincesCriteria;
import com.epmweb.server.service.StateProvincesQueryService;

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
 * Integration tests for the {@link StateProvincesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StateProvincesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_TERRITORY = "AAAAAAAAAA";
    private static final String UPDATED_SALES_TERRITORY = "BBBBBBBBBB";

    private static final String DEFAULT_BORDER = "AAAAAAAAAA";
    private static final String UPDATED_BORDER = "BBBBBBBBBB";

    private static final Long DEFAULT_LATEST_RECORDED_POPULATION = 1L;
    private static final Long UPDATED_LATEST_RECORDED_POPULATION = 2L;
    private static final Long SMALLER_LATEST_RECORDED_POPULATION = 1L - 1L;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StateProvincesRepository stateProvincesRepository;

    @Autowired
    private StateProvincesMapper stateProvincesMapper;

    @Autowired
    private StateProvincesService stateProvincesService;

    @Autowired
    private StateProvincesQueryService stateProvincesQueryService;

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

    private MockMvc restStateProvincesMockMvc;

    private StateProvinces stateProvinces;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StateProvincesResource stateProvincesResource = new StateProvincesResource(stateProvincesService, stateProvincesQueryService);
        this.restStateProvincesMockMvc = MockMvcBuilders.standaloneSetup(stateProvincesResource)
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
    public static StateProvinces createEntity(EntityManager em) {
        StateProvinces stateProvinces = new StateProvinces()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .salesTerritory(DEFAULT_SALES_TERRITORY)
            .border(DEFAULT_BORDER)
            .latestRecordedPopulation(DEFAULT_LATEST_RECORDED_POPULATION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return stateProvinces;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvinces createUpdatedEntity(EntityManager em) {
        StateProvinces stateProvinces = new StateProvinces()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .salesTerritory(UPDATED_SALES_TERRITORY)
            .border(UPDATED_BORDER)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return stateProvinces;
    }

    @BeforeEach
    public void initTest() {
        stateProvinces = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateProvinces() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesRepository.findAll().size();

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);
        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isCreated());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeCreate + 1);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStateProvinces.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStateProvinces.getSalesTerritory()).isEqualTo(DEFAULT_SALES_TERRITORY);
        assertThat(testStateProvinces.getBorder()).isEqualTo(DEFAULT_BORDER);
        assertThat(testStateProvinces.getLatestRecordedPopulation()).isEqualTo(DEFAULT_LATEST_RECORDED_POPULATION);
        assertThat(testStateProvinces.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testStateProvinces.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createStateProvincesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesRepository.findAll().size();

        // Create the StateProvinces with an existing ID
        stateProvinces.setId(1L);
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setCode(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setName(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalesTerritoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setSalesTerritory(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setValidFrom(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setValidTo(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList
        restStateProvincesMockMvc.perform(get("/api/state-provinces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvinces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].salesTerritory").value(hasItem(DEFAULT_SALES_TERRITORY)))
            .andExpect(jsonPath("$.[*].border").value(hasItem(DEFAULT_BORDER)))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get the stateProvinces
        restStateProvincesMockMvc.perform(get("/api/state-provinces/{id}", stateProvinces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateProvinces.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.salesTerritory").value(DEFAULT_SALES_TERRITORY))
            .andExpect(jsonPath("$.border").value(DEFAULT_BORDER))
            .andExpect(jsonPath("$.latestRecordedPopulation").value(DEFAULT_LATEST_RECORDED_POPULATION.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getStateProvincesByIdFiltering() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        Long id = stateProvinces.getId();

        defaultStateProvincesShouldBeFound("id.equals=" + id);
        defaultStateProvincesShouldNotBeFound("id.notEquals=" + id);

        defaultStateProvincesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStateProvincesShouldNotBeFound("id.greaterThan=" + id);

        defaultStateProvincesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStateProvincesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStateProvincesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where code equals to DEFAULT_CODE
        defaultStateProvincesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the stateProvincesList where code equals to UPDATED_CODE
        defaultStateProvincesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where code not equals to DEFAULT_CODE
        defaultStateProvincesShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the stateProvincesList where code not equals to UPDATED_CODE
        defaultStateProvincesShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultStateProvincesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the stateProvincesList where code equals to UPDATED_CODE
        defaultStateProvincesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where code is not null
        defaultStateProvincesShouldBeFound("code.specified=true");

        // Get all the stateProvincesList where code is null
        defaultStateProvincesShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllStateProvincesByCodeContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where code contains DEFAULT_CODE
        defaultStateProvincesShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the stateProvincesList where code contains UPDATED_CODE
        defaultStateProvincesShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where code does not contain DEFAULT_CODE
        defaultStateProvincesShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the stateProvincesList where code does not contain UPDATED_CODE
        defaultStateProvincesShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllStateProvincesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where name equals to DEFAULT_NAME
        defaultStateProvincesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stateProvincesList where name equals to UPDATED_NAME
        defaultStateProvincesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where name not equals to DEFAULT_NAME
        defaultStateProvincesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stateProvincesList where name not equals to UPDATED_NAME
        defaultStateProvincesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStateProvincesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stateProvincesList where name equals to UPDATED_NAME
        defaultStateProvincesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where name is not null
        defaultStateProvincesShouldBeFound("name.specified=true");

        // Get all the stateProvincesList where name is null
        defaultStateProvincesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStateProvincesByNameContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where name contains DEFAULT_NAME
        defaultStateProvincesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stateProvincesList where name contains UPDATED_NAME
        defaultStateProvincesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where name does not contain DEFAULT_NAME
        defaultStateProvincesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stateProvincesList where name does not contain UPDATED_NAME
        defaultStateProvincesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStateProvincesBySalesTerritoryIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where salesTerritory equals to DEFAULT_SALES_TERRITORY
        defaultStateProvincesShouldBeFound("salesTerritory.equals=" + DEFAULT_SALES_TERRITORY);

        // Get all the stateProvincesList where salesTerritory equals to UPDATED_SALES_TERRITORY
        defaultStateProvincesShouldNotBeFound("salesTerritory.equals=" + UPDATED_SALES_TERRITORY);
    }

    @Test
    @Transactional
    public void getAllStateProvincesBySalesTerritoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where salesTerritory not equals to DEFAULT_SALES_TERRITORY
        defaultStateProvincesShouldNotBeFound("salesTerritory.notEquals=" + DEFAULT_SALES_TERRITORY);

        // Get all the stateProvincesList where salesTerritory not equals to UPDATED_SALES_TERRITORY
        defaultStateProvincesShouldBeFound("salesTerritory.notEquals=" + UPDATED_SALES_TERRITORY);
    }

    @Test
    @Transactional
    public void getAllStateProvincesBySalesTerritoryIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where salesTerritory in DEFAULT_SALES_TERRITORY or UPDATED_SALES_TERRITORY
        defaultStateProvincesShouldBeFound("salesTerritory.in=" + DEFAULT_SALES_TERRITORY + "," + UPDATED_SALES_TERRITORY);

        // Get all the stateProvincesList where salesTerritory equals to UPDATED_SALES_TERRITORY
        defaultStateProvincesShouldNotBeFound("salesTerritory.in=" + UPDATED_SALES_TERRITORY);
    }

    @Test
    @Transactional
    public void getAllStateProvincesBySalesTerritoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where salesTerritory is not null
        defaultStateProvincesShouldBeFound("salesTerritory.specified=true");

        // Get all the stateProvincesList where salesTerritory is null
        defaultStateProvincesShouldNotBeFound("salesTerritory.specified=false");
    }
                @Test
    @Transactional
    public void getAllStateProvincesBySalesTerritoryContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where salesTerritory contains DEFAULT_SALES_TERRITORY
        defaultStateProvincesShouldBeFound("salesTerritory.contains=" + DEFAULT_SALES_TERRITORY);

        // Get all the stateProvincesList where salesTerritory contains UPDATED_SALES_TERRITORY
        defaultStateProvincesShouldNotBeFound("salesTerritory.contains=" + UPDATED_SALES_TERRITORY);
    }

    @Test
    @Transactional
    public void getAllStateProvincesBySalesTerritoryNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where salesTerritory does not contain DEFAULT_SALES_TERRITORY
        defaultStateProvincesShouldNotBeFound("salesTerritory.doesNotContain=" + DEFAULT_SALES_TERRITORY);

        // Get all the stateProvincesList where salesTerritory does not contain UPDATED_SALES_TERRITORY
        defaultStateProvincesShouldBeFound("salesTerritory.doesNotContain=" + UPDATED_SALES_TERRITORY);
    }


    @Test
    @Transactional
    public void getAllStateProvincesByBorderIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where border equals to DEFAULT_BORDER
        defaultStateProvincesShouldBeFound("border.equals=" + DEFAULT_BORDER);

        // Get all the stateProvincesList where border equals to UPDATED_BORDER
        defaultStateProvincesShouldNotBeFound("border.equals=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByBorderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where border not equals to DEFAULT_BORDER
        defaultStateProvincesShouldNotBeFound("border.notEquals=" + DEFAULT_BORDER);

        // Get all the stateProvincesList where border not equals to UPDATED_BORDER
        defaultStateProvincesShouldBeFound("border.notEquals=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByBorderIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where border in DEFAULT_BORDER or UPDATED_BORDER
        defaultStateProvincesShouldBeFound("border.in=" + DEFAULT_BORDER + "," + UPDATED_BORDER);

        // Get all the stateProvincesList where border equals to UPDATED_BORDER
        defaultStateProvincesShouldNotBeFound("border.in=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByBorderIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where border is not null
        defaultStateProvincesShouldBeFound("border.specified=true");

        // Get all the stateProvincesList where border is null
        defaultStateProvincesShouldNotBeFound("border.specified=false");
    }
                @Test
    @Transactional
    public void getAllStateProvincesByBorderContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where border contains DEFAULT_BORDER
        defaultStateProvincesShouldBeFound("border.contains=" + DEFAULT_BORDER);

        // Get all the stateProvincesList where border contains UPDATED_BORDER
        defaultStateProvincesShouldNotBeFound("border.contains=" + UPDATED_BORDER);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByBorderNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where border does not contain DEFAULT_BORDER
        defaultStateProvincesShouldNotBeFound("border.doesNotContain=" + DEFAULT_BORDER);

        // Get all the stateProvincesList where border does not contain UPDATED_BORDER
        defaultStateProvincesShouldBeFound("border.doesNotContain=" + UPDATED_BORDER);
    }


    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation equals to DEFAULT_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.equals=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.equals=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation not equals to DEFAULT_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.notEquals=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation not equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.notEquals=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation in DEFAULT_LATEST_RECORDED_POPULATION or UPDATED_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.in=" + DEFAULT_LATEST_RECORDED_POPULATION + "," + UPDATED_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation equals to UPDATED_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.in=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation is not null
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.specified=true");

        // Get all the stateProvincesList where latestRecordedPopulation is null
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.specified=false");
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation is greater than or equal to DEFAULT_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.greaterThanOrEqual=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation is greater than or equal to UPDATED_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.greaterThanOrEqual=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation is less than or equal to DEFAULT_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.lessThanOrEqual=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation is less than or equal to SMALLER_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.lessThanOrEqual=" + SMALLER_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsLessThanSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation is less than DEFAULT_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.lessThan=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation is less than UPDATED_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.lessThan=" + UPDATED_LATEST_RECORDED_POPULATION);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByLatestRecordedPopulationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where latestRecordedPopulation is greater than DEFAULT_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldNotBeFound("latestRecordedPopulation.greaterThan=" + DEFAULT_LATEST_RECORDED_POPULATION);

        // Get all the stateProvincesList where latestRecordedPopulation is greater than SMALLER_LATEST_RECORDED_POPULATION
        defaultStateProvincesShouldBeFound("latestRecordedPopulation.greaterThan=" + SMALLER_LATEST_RECORDED_POPULATION);
    }


    @Test
    @Transactional
    public void getAllStateProvincesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validFrom equals to DEFAULT_VALID_FROM
        defaultStateProvincesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the stateProvincesList where validFrom equals to UPDATED_VALID_FROM
        defaultStateProvincesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultStateProvincesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the stateProvincesList where validFrom not equals to UPDATED_VALID_FROM
        defaultStateProvincesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultStateProvincesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the stateProvincesList where validFrom equals to UPDATED_VALID_FROM
        defaultStateProvincesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validFrom is not null
        defaultStateProvincesShouldBeFound("validFrom.specified=true");

        // Get all the stateProvincesList where validFrom is null
        defaultStateProvincesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validTo equals to DEFAULT_VALID_TO
        defaultStateProvincesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the stateProvincesList where validTo equals to UPDATED_VALID_TO
        defaultStateProvincesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validTo not equals to DEFAULT_VALID_TO
        defaultStateProvincesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the stateProvincesList where validTo not equals to UPDATED_VALID_TO
        defaultStateProvincesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultStateProvincesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the stateProvincesList where validTo equals to UPDATED_VALID_TO
        defaultStateProvincesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllStateProvincesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where validTo is not null
        defaultStateProvincesShouldBeFound("validTo.specified=true");

        // Get all the stateProvincesList where validTo is null
        defaultStateProvincesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllStateProvincesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Countries country = CountriesResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        stateProvinces.setCountry(country);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long countryId = country.getId();

        // Get all the stateProvincesList where country equals to countryId
        defaultStateProvincesShouldBeFound("countryId.equals=" + countryId);

        // Get all the stateProvincesList where country equals to countryId + 1
        defaultStateProvincesShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStateProvincesShouldBeFound(String filter) throws Exception {
        restStateProvincesMockMvc.perform(get("/api/state-provinces?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvinces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].salesTerritory").value(hasItem(DEFAULT_SALES_TERRITORY)))
            .andExpect(jsonPath("$.[*].border").value(hasItem(DEFAULT_BORDER)))
            .andExpect(jsonPath("$.[*].latestRecordedPopulation").value(hasItem(DEFAULT_LATEST_RECORDED_POPULATION.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restStateProvincesMockMvc.perform(get("/api/state-provinces/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStateProvincesShouldNotBeFound(String filter) throws Exception {
        restStateProvincesMockMvc.perform(get("/api/state-provinces?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStateProvincesMockMvc.perform(get("/api/state-provinces/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStateProvinces() throws Exception {
        // Get the stateProvinces
        restStateProvincesMockMvc.perform(get("/api/state-provinces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Update the stateProvinces
        StateProvinces updatedStateProvinces = stateProvincesRepository.findById(stateProvinces.getId()).get();
        // Disconnect from session so that the updates on updatedStateProvinces are not directly saved in db
        em.detach(updatedStateProvinces);
        updatedStateProvinces
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .salesTerritory(UPDATED_SALES_TERRITORY)
            .border(UPDATED_BORDER)
            .latestRecordedPopulation(UPDATED_LATEST_RECORDED_POPULATION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(updatedStateProvinces);

        restStateProvincesMockMvc.perform(put("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isOk());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStateProvinces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStateProvinces.getSalesTerritory()).isEqualTo(UPDATED_SALES_TERRITORY);
        assertThat(testStateProvinces.getBorder()).isEqualTo(UPDATED_BORDER);
        assertThat(testStateProvinces.getLatestRecordedPopulation()).isEqualTo(UPDATED_LATEST_RECORDED_POPULATION);
        assertThat(testStateProvinces.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testStateProvinces.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc.perform(put("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeDelete = stateProvincesRepository.findAll().size();

        // Delete the stateProvinces
        restStateProvincesMockMvc.perform(delete("/api/state-provinces/{id}", stateProvinces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
