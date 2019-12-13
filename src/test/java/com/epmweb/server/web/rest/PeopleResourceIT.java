package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.User;
import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.domain.Wishlists;
import com.epmweb.server.domain.Compares;
import com.epmweb.server.repository.PeopleRepository;
import com.epmweb.server.service.PeopleService;
import com.epmweb.server.service.dto.PeopleDTO;
import com.epmweb.server.service.mapper.PeopleMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.PeopleCriteria;
import com.epmweb.server.service.PeopleQueryService;

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

import com.epmweb.server.domain.enumeration.Gender;
/**
 * Integration tests for the {@link PeopleResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class PeopleResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERRED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Boolean DEFAULT_IS_PERMITTED_TO_LOGON = false;
    private static final Boolean UPDATED_IS_PERMITTED_TO_LOGON = true;

    private static final String DEFAULT_LOGON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOGON_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EXTERNAL_LOGON_PROVIDER = false;
    private static final Boolean UPDATED_IS_EXTERNAL_LOGON_PROVIDER = true;

    private static final Boolean DEFAULT_IS_SYSTEM_USER = false;
    private static final Boolean UPDATED_IS_SYSTEM_USER = true;

    private static final Boolean DEFAULT_IS_EMPLOYEE = false;
    private static final Boolean UPDATED_IS_EMPLOYEE = true;

    private static final Boolean DEFAULT_IS_SALES_PERSON = false;
    private static final Boolean UPDATED_IS_SALES_PERSON = true;

    private static final Boolean DEFAULT_IS_GUEST_USER = false;
    private static final Boolean UPDATED_IS_GUEST_USER = true;

    private static final Integer DEFAULT_EMAIL_PROMOTION = 1;
    private static final Integer UPDATED_EMAIL_PROMOTION = 2;
    private static final Integer SMALLER_EMAIL_PROMOTION = 1 - 1;

    private static final String DEFAULT_USER_PREFERENCES = "AAAAAAAAAA";
    private static final String UPDATED_USER_PREFERENCES = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "F@+i.Q";
    private static final String UPDATED_EMAIL_ADDRESS = "9i@4.(";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_LANGUAGES = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private PeopleQueryService peopleQueryService;

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

    private MockMvc restPeopleMockMvc;

    private People people;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeopleResource peopleResource = new PeopleResource(peopleService, peopleQueryService);
        this.restPeopleMockMvc = MockMvcBuilders.standaloneSetup(peopleResource)
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
    public static People createEntity(EntityManager em) {
        People people = new People()
            .fullName(DEFAULT_FULL_NAME)
            .preferredName(DEFAULT_PREFERRED_NAME)
            .searchName(DEFAULT_SEARCH_NAME)
            .gender(DEFAULT_GENDER)
            .isPermittedToLogon(DEFAULT_IS_PERMITTED_TO_LOGON)
            .logonName(DEFAULT_LOGON_NAME)
            .isExternalLogonProvider(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER)
            .isSystemUser(DEFAULT_IS_SYSTEM_USER)
            .isEmployee(DEFAULT_IS_EMPLOYEE)
            .isSalesPerson(DEFAULT_IS_SALES_PERSON)
            .isGuestUser(DEFAULT_IS_GUEST_USER)
            .emailPromotion(DEFAULT_EMAIL_PROMOTION)
            .userPreferences(DEFAULT_USER_PREFERENCES)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .photo(DEFAULT_PHOTO)
            .customFields(DEFAULT_CUSTOM_FIELDS)
            .otherLanguages(DEFAULT_OTHER_LANGUAGES)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        people.setUser(user);
        return people;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createUpdatedEntity(EntityManager em) {
        People people = new People()
            .fullName(UPDATED_FULL_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .searchName(UPDATED_SEARCH_NAME)
            .gender(UPDATED_GENDER)
            .isPermittedToLogon(UPDATED_IS_PERMITTED_TO_LOGON)
            .logonName(UPDATED_LOGON_NAME)
            .isExternalLogonProvider(UPDATED_IS_EXTERNAL_LOGON_PROVIDER)
            .isSystemUser(UPDATED_IS_SYSTEM_USER)
            .isEmployee(UPDATED_IS_EMPLOYEE)
            .isSalesPerson(UPDATED_IS_SALES_PERSON)
            .isGuestUser(UPDATED_IS_GUEST_USER)
            .emailPromotion(UPDATED_EMAIL_PROMOTION)
            .userPreferences(UPDATED_USER_PREFERENCES)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .photo(UPDATED_PHOTO)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .otherLanguages(UPDATED_OTHER_LANGUAGES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        people.setUser(user);
        return people;
    }

    @BeforeEach
    public void initTest() {
        people = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeople() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isCreated());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate + 1);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testPeople.getPreferredName()).isEqualTo(DEFAULT_PREFERRED_NAME);
        assertThat(testPeople.getSearchName()).isEqualTo(DEFAULT_SEARCH_NAME);
        assertThat(testPeople.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPeople.isIsPermittedToLogon()).isEqualTo(DEFAULT_IS_PERMITTED_TO_LOGON);
        assertThat(testPeople.getLogonName()).isEqualTo(DEFAULT_LOGON_NAME);
        assertThat(testPeople.isIsExternalLogonProvider()).isEqualTo(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER);
        assertThat(testPeople.isIsSystemUser()).isEqualTo(DEFAULT_IS_SYSTEM_USER);
        assertThat(testPeople.isIsEmployee()).isEqualTo(DEFAULT_IS_EMPLOYEE);
        assertThat(testPeople.isIsSalesPerson()).isEqualTo(DEFAULT_IS_SALES_PERSON);
        assertThat(testPeople.isIsGuestUser()).isEqualTo(DEFAULT_IS_GUEST_USER);
        assertThat(testPeople.getEmailPromotion()).isEqualTo(DEFAULT_EMAIL_PROMOTION);
        assertThat(testPeople.getUserPreferences()).isEqualTo(DEFAULT_USER_PREFERENCES);
        assertThat(testPeople.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPeople.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testPeople.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPeople.getCustomFields()).isEqualTo(DEFAULT_CUSTOM_FIELDS);
        assertThat(testPeople.getOtherLanguages()).isEqualTo(DEFAULT_OTHER_LANGUAGES);
        assertThat(testPeople.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPeople.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPeopleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People with an existing ID
        people.setId(1L);
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setFullName(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreferredNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setPreferredName(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSearchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setSearchName(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setGender(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPermittedToLogonIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsPermittedToLogon(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExternalLogonProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsExternalLogonProvider(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSystemUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsSystemUser(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEmployeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsEmployee(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSalesPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsSalesPerson(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsGuestUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setIsGuestUser(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPromotionIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setEmailPromotion(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setValidFrom(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setValidTo(null);

        // Create the People, which fails.
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME)))
            .andExpect(jsonPath("$.[*].searchName").value(hasItem(DEFAULT_SEARCH_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].isPermittedToLogon").value(hasItem(DEFAULT_IS_PERMITTED_TO_LOGON.booleanValue())))
            .andExpect(jsonPath("$.[*].logonName").value(hasItem(DEFAULT_LOGON_NAME)))
            .andExpect(jsonPath("$.[*].isExternalLogonProvider").value(hasItem(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER.booleanValue())))
            .andExpect(jsonPath("$.[*].isSystemUser").value(hasItem(DEFAULT_IS_SYSTEM_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].isEmployee").value(hasItem(DEFAULT_IS_EMPLOYEE.booleanValue())))
            .andExpect(jsonPath("$.[*].isSalesPerson").value(hasItem(DEFAULT_IS_SALES_PERSON.booleanValue())))
            .andExpect(jsonPath("$.[*].isGuestUser").value(hasItem(DEFAULT_IS_GUEST_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].emailPromotion").value(hasItem(DEFAULT_EMAIL_PROMOTION)))
            .andExpect(jsonPath("$.[*].userPreferences").value(hasItem(DEFAULT_USER_PREFERENCES)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS)))
            .andExpect(jsonPath("$.[*].otherLanguages").value(hasItem(DEFAULT_OTHER_LANGUAGES)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(people.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.preferredName").value(DEFAULT_PREFERRED_NAME))
            .andExpect(jsonPath("$.searchName").value(DEFAULT_SEARCH_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.isPermittedToLogon").value(DEFAULT_IS_PERMITTED_TO_LOGON.booleanValue()))
            .andExpect(jsonPath("$.logonName").value(DEFAULT_LOGON_NAME))
            .andExpect(jsonPath("$.isExternalLogonProvider").value(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER.booleanValue()))
            .andExpect(jsonPath("$.isSystemUser").value(DEFAULT_IS_SYSTEM_USER.booleanValue()))
            .andExpect(jsonPath("$.isEmployee").value(DEFAULT_IS_EMPLOYEE.booleanValue()))
            .andExpect(jsonPath("$.isSalesPerson").value(DEFAULT_IS_SALES_PERSON.booleanValue()))
            .andExpect(jsonPath("$.isGuestUser").value(DEFAULT_IS_GUEST_USER.booleanValue()))
            .andExpect(jsonPath("$.emailPromotion").value(DEFAULT_EMAIL_PROMOTION))
            .andExpect(jsonPath("$.userPreferences").value(DEFAULT_USER_PREFERENCES))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.customFields").value(DEFAULT_CUSTOM_FIELDS))
            .andExpect(jsonPath("$.otherLanguages").value(DEFAULT_OTHER_LANGUAGES))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getPeopleByIdFiltering() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        Long id = people.getId();

        defaultPeopleShouldBeFound("id.equals=" + id);
        defaultPeopleShouldNotBeFound("id.notEquals=" + id);

        defaultPeopleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPeopleShouldNotBeFound("id.greaterThan=" + id);

        defaultPeopleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPeopleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPeopleByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where fullName equals to DEFAULT_FULL_NAME
        defaultPeopleShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the peopleList where fullName equals to UPDATED_FULL_NAME
        defaultPeopleShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where fullName not equals to DEFAULT_FULL_NAME
        defaultPeopleShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the peopleList where fullName not equals to UPDATED_FULL_NAME
        defaultPeopleShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultPeopleShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the peopleList where fullName equals to UPDATED_FULL_NAME
        defaultPeopleShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where fullName is not null
        defaultPeopleShouldBeFound("fullName.specified=true");

        // Get all the peopleList where fullName is null
        defaultPeopleShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByFullNameContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where fullName contains DEFAULT_FULL_NAME
        defaultPeopleShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the peopleList where fullName contains UPDATED_FULL_NAME
        defaultPeopleShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where fullName does not contain DEFAULT_FULL_NAME
        defaultPeopleShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the peopleList where fullName does not contain UPDATED_FULL_NAME
        defaultPeopleShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllPeopleByPreferredNameIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where preferredName equals to DEFAULT_PREFERRED_NAME
        defaultPeopleShouldBeFound("preferredName.equals=" + DEFAULT_PREFERRED_NAME);

        // Get all the peopleList where preferredName equals to UPDATED_PREFERRED_NAME
        defaultPeopleShouldNotBeFound("preferredName.equals=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByPreferredNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where preferredName not equals to DEFAULT_PREFERRED_NAME
        defaultPeopleShouldNotBeFound("preferredName.notEquals=" + DEFAULT_PREFERRED_NAME);

        // Get all the peopleList where preferredName not equals to UPDATED_PREFERRED_NAME
        defaultPeopleShouldBeFound("preferredName.notEquals=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByPreferredNameIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where preferredName in DEFAULT_PREFERRED_NAME or UPDATED_PREFERRED_NAME
        defaultPeopleShouldBeFound("preferredName.in=" + DEFAULT_PREFERRED_NAME + "," + UPDATED_PREFERRED_NAME);

        // Get all the peopleList where preferredName equals to UPDATED_PREFERRED_NAME
        defaultPeopleShouldNotBeFound("preferredName.in=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByPreferredNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where preferredName is not null
        defaultPeopleShouldBeFound("preferredName.specified=true");

        // Get all the peopleList where preferredName is null
        defaultPeopleShouldNotBeFound("preferredName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByPreferredNameContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where preferredName contains DEFAULT_PREFERRED_NAME
        defaultPeopleShouldBeFound("preferredName.contains=" + DEFAULT_PREFERRED_NAME);

        // Get all the peopleList where preferredName contains UPDATED_PREFERRED_NAME
        defaultPeopleShouldNotBeFound("preferredName.contains=" + UPDATED_PREFERRED_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByPreferredNameNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where preferredName does not contain DEFAULT_PREFERRED_NAME
        defaultPeopleShouldNotBeFound("preferredName.doesNotContain=" + DEFAULT_PREFERRED_NAME);

        // Get all the peopleList where preferredName does not contain UPDATED_PREFERRED_NAME
        defaultPeopleShouldBeFound("preferredName.doesNotContain=" + UPDATED_PREFERRED_NAME);
    }


    @Test
    @Transactional
    public void getAllPeopleBySearchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where searchName equals to DEFAULT_SEARCH_NAME
        defaultPeopleShouldBeFound("searchName.equals=" + DEFAULT_SEARCH_NAME);

        // Get all the peopleList where searchName equals to UPDATED_SEARCH_NAME
        defaultPeopleShouldNotBeFound("searchName.equals=" + UPDATED_SEARCH_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleBySearchNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where searchName not equals to DEFAULT_SEARCH_NAME
        defaultPeopleShouldNotBeFound("searchName.notEquals=" + DEFAULT_SEARCH_NAME);

        // Get all the peopleList where searchName not equals to UPDATED_SEARCH_NAME
        defaultPeopleShouldBeFound("searchName.notEquals=" + UPDATED_SEARCH_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleBySearchNameIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where searchName in DEFAULT_SEARCH_NAME or UPDATED_SEARCH_NAME
        defaultPeopleShouldBeFound("searchName.in=" + DEFAULT_SEARCH_NAME + "," + UPDATED_SEARCH_NAME);

        // Get all the peopleList where searchName equals to UPDATED_SEARCH_NAME
        defaultPeopleShouldNotBeFound("searchName.in=" + UPDATED_SEARCH_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleBySearchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where searchName is not null
        defaultPeopleShouldBeFound("searchName.specified=true");

        // Get all the peopleList where searchName is null
        defaultPeopleShouldNotBeFound("searchName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleBySearchNameContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where searchName contains DEFAULT_SEARCH_NAME
        defaultPeopleShouldBeFound("searchName.contains=" + DEFAULT_SEARCH_NAME);

        // Get all the peopleList where searchName contains UPDATED_SEARCH_NAME
        defaultPeopleShouldNotBeFound("searchName.contains=" + UPDATED_SEARCH_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleBySearchNameNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where searchName does not contain DEFAULT_SEARCH_NAME
        defaultPeopleShouldNotBeFound("searchName.doesNotContain=" + DEFAULT_SEARCH_NAME);

        // Get all the peopleList where searchName does not contain UPDATED_SEARCH_NAME
        defaultPeopleShouldBeFound("searchName.doesNotContain=" + UPDATED_SEARCH_NAME);
    }


    @Test
    @Transactional
    public void getAllPeopleByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where gender equals to DEFAULT_GENDER
        defaultPeopleShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the peopleList where gender equals to UPDATED_GENDER
        defaultPeopleShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPeopleByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where gender not equals to DEFAULT_GENDER
        defaultPeopleShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the peopleList where gender not equals to UPDATED_GENDER
        defaultPeopleShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPeopleByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPeopleShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the peopleList where gender equals to UPDATED_GENDER
        defaultPeopleShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPeopleByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where gender is not null
        defaultPeopleShouldBeFound("gender.specified=true");

        // Get all the peopleList where gender is null
        defaultPeopleShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByIsPermittedToLogonIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isPermittedToLogon equals to DEFAULT_IS_PERMITTED_TO_LOGON
        defaultPeopleShouldBeFound("isPermittedToLogon.equals=" + DEFAULT_IS_PERMITTED_TO_LOGON);

        // Get all the peopleList where isPermittedToLogon equals to UPDATED_IS_PERMITTED_TO_LOGON
        defaultPeopleShouldNotBeFound("isPermittedToLogon.equals=" + UPDATED_IS_PERMITTED_TO_LOGON);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsPermittedToLogonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isPermittedToLogon not equals to DEFAULT_IS_PERMITTED_TO_LOGON
        defaultPeopleShouldNotBeFound("isPermittedToLogon.notEquals=" + DEFAULT_IS_PERMITTED_TO_LOGON);

        // Get all the peopleList where isPermittedToLogon not equals to UPDATED_IS_PERMITTED_TO_LOGON
        defaultPeopleShouldBeFound("isPermittedToLogon.notEquals=" + UPDATED_IS_PERMITTED_TO_LOGON);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsPermittedToLogonIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isPermittedToLogon in DEFAULT_IS_PERMITTED_TO_LOGON or UPDATED_IS_PERMITTED_TO_LOGON
        defaultPeopleShouldBeFound("isPermittedToLogon.in=" + DEFAULT_IS_PERMITTED_TO_LOGON + "," + UPDATED_IS_PERMITTED_TO_LOGON);

        // Get all the peopleList where isPermittedToLogon equals to UPDATED_IS_PERMITTED_TO_LOGON
        defaultPeopleShouldNotBeFound("isPermittedToLogon.in=" + UPDATED_IS_PERMITTED_TO_LOGON);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsPermittedToLogonIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isPermittedToLogon is not null
        defaultPeopleShouldBeFound("isPermittedToLogon.specified=true");

        // Get all the peopleList where isPermittedToLogon is null
        defaultPeopleShouldNotBeFound("isPermittedToLogon.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByLogonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where logonName equals to DEFAULT_LOGON_NAME
        defaultPeopleShouldBeFound("logonName.equals=" + DEFAULT_LOGON_NAME);

        // Get all the peopleList where logonName equals to UPDATED_LOGON_NAME
        defaultPeopleShouldNotBeFound("logonName.equals=" + UPDATED_LOGON_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByLogonNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where logonName not equals to DEFAULT_LOGON_NAME
        defaultPeopleShouldNotBeFound("logonName.notEquals=" + DEFAULT_LOGON_NAME);

        // Get all the peopleList where logonName not equals to UPDATED_LOGON_NAME
        defaultPeopleShouldBeFound("logonName.notEquals=" + UPDATED_LOGON_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByLogonNameIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where logonName in DEFAULT_LOGON_NAME or UPDATED_LOGON_NAME
        defaultPeopleShouldBeFound("logonName.in=" + DEFAULT_LOGON_NAME + "," + UPDATED_LOGON_NAME);

        // Get all the peopleList where logonName equals to UPDATED_LOGON_NAME
        defaultPeopleShouldNotBeFound("logonName.in=" + UPDATED_LOGON_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByLogonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where logonName is not null
        defaultPeopleShouldBeFound("logonName.specified=true");

        // Get all the peopleList where logonName is null
        defaultPeopleShouldNotBeFound("logonName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByLogonNameContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where logonName contains DEFAULT_LOGON_NAME
        defaultPeopleShouldBeFound("logonName.contains=" + DEFAULT_LOGON_NAME);

        // Get all the peopleList where logonName contains UPDATED_LOGON_NAME
        defaultPeopleShouldNotBeFound("logonName.contains=" + UPDATED_LOGON_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByLogonNameNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where logonName does not contain DEFAULT_LOGON_NAME
        defaultPeopleShouldNotBeFound("logonName.doesNotContain=" + DEFAULT_LOGON_NAME);

        // Get all the peopleList where logonName does not contain UPDATED_LOGON_NAME
        defaultPeopleShouldBeFound("logonName.doesNotContain=" + UPDATED_LOGON_NAME);
    }


    @Test
    @Transactional
    public void getAllPeopleByIsExternalLogonProviderIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isExternalLogonProvider equals to DEFAULT_IS_EXTERNAL_LOGON_PROVIDER
        defaultPeopleShouldBeFound("isExternalLogonProvider.equals=" + DEFAULT_IS_EXTERNAL_LOGON_PROVIDER);

        // Get all the peopleList where isExternalLogonProvider equals to UPDATED_IS_EXTERNAL_LOGON_PROVIDER
        defaultPeopleShouldNotBeFound("isExternalLogonProvider.equals=" + UPDATED_IS_EXTERNAL_LOGON_PROVIDER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsExternalLogonProviderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isExternalLogonProvider not equals to DEFAULT_IS_EXTERNAL_LOGON_PROVIDER
        defaultPeopleShouldNotBeFound("isExternalLogonProvider.notEquals=" + DEFAULT_IS_EXTERNAL_LOGON_PROVIDER);

        // Get all the peopleList where isExternalLogonProvider not equals to UPDATED_IS_EXTERNAL_LOGON_PROVIDER
        defaultPeopleShouldBeFound("isExternalLogonProvider.notEquals=" + UPDATED_IS_EXTERNAL_LOGON_PROVIDER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsExternalLogonProviderIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isExternalLogonProvider in DEFAULT_IS_EXTERNAL_LOGON_PROVIDER or UPDATED_IS_EXTERNAL_LOGON_PROVIDER
        defaultPeopleShouldBeFound("isExternalLogonProvider.in=" + DEFAULT_IS_EXTERNAL_LOGON_PROVIDER + "," + UPDATED_IS_EXTERNAL_LOGON_PROVIDER);

        // Get all the peopleList where isExternalLogonProvider equals to UPDATED_IS_EXTERNAL_LOGON_PROVIDER
        defaultPeopleShouldNotBeFound("isExternalLogonProvider.in=" + UPDATED_IS_EXTERNAL_LOGON_PROVIDER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsExternalLogonProviderIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isExternalLogonProvider is not null
        defaultPeopleShouldBeFound("isExternalLogonProvider.specified=true");

        // Get all the peopleList where isExternalLogonProvider is null
        defaultPeopleShouldNotBeFound("isExternalLogonProvider.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSystemUserIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSystemUser equals to DEFAULT_IS_SYSTEM_USER
        defaultPeopleShouldBeFound("isSystemUser.equals=" + DEFAULT_IS_SYSTEM_USER);

        // Get all the peopleList where isSystemUser equals to UPDATED_IS_SYSTEM_USER
        defaultPeopleShouldNotBeFound("isSystemUser.equals=" + UPDATED_IS_SYSTEM_USER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSystemUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSystemUser not equals to DEFAULT_IS_SYSTEM_USER
        defaultPeopleShouldNotBeFound("isSystemUser.notEquals=" + DEFAULT_IS_SYSTEM_USER);

        // Get all the peopleList where isSystemUser not equals to UPDATED_IS_SYSTEM_USER
        defaultPeopleShouldBeFound("isSystemUser.notEquals=" + UPDATED_IS_SYSTEM_USER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSystemUserIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSystemUser in DEFAULT_IS_SYSTEM_USER or UPDATED_IS_SYSTEM_USER
        defaultPeopleShouldBeFound("isSystemUser.in=" + DEFAULT_IS_SYSTEM_USER + "," + UPDATED_IS_SYSTEM_USER);

        // Get all the peopleList where isSystemUser equals to UPDATED_IS_SYSTEM_USER
        defaultPeopleShouldNotBeFound("isSystemUser.in=" + UPDATED_IS_SYSTEM_USER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSystemUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSystemUser is not null
        defaultPeopleShouldBeFound("isSystemUser.specified=true");

        // Get all the peopleList where isSystemUser is null
        defaultPeopleShouldNotBeFound("isSystemUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByIsEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isEmployee equals to DEFAULT_IS_EMPLOYEE
        defaultPeopleShouldBeFound("isEmployee.equals=" + DEFAULT_IS_EMPLOYEE);

        // Get all the peopleList where isEmployee equals to UPDATED_IS_EMPLOYEE
        defaultPeopleShouldNotBeFound("isEmployee.equals=" + UPDATED_IS_EMPLOYEE);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsEmployeeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isEmployee not equals to DEFAULT_IS_EMPLOYEE
        defaultPeopleShouldNotBeFound("isEmployee.notEquals=" + DEFAULT_IS_EMPLOYEE);

        // Get all the peopleList where isEmployee not equals to UPDATED_IS_EMPLOYEE
        defaultPeopleShouldBeFound("isEmployee.notEquals=" + UPDATED_IS_EMPLOYEE);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsEmployeeIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isEmployee in DEFAULT_IS_EMPLOYEE or UPDATED_IS_EMPLOYEE
        defaultPeopleShouldBeFound("isEmployee.in=" + DEFAULT_IS_EMPLOYEE + "," + UPDATED_IS_EMPLOYEE);

        // Get all the peopleList where isEmployee equals to UPDATED_IS_EMPLOYEE
        defaultPeopleShouldNotBeFound("isEmployee.in=" + UPDATED_IS_EMPLOYEE);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsEmployeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isEmployee is not null
        defaultPeopleShouldBeFound("isEmployee.specified=true");

        // Get all the peopleList where isEmployee is null
        defaultPeopleShouldNotBeFound("isEmployee.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSalesPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSalesPerson equals to DEFAULT_IS_SALES_PERSON
        defaultPeopleShouldBeFound("isSalesPerson.equals=" + DEFAULT_IS_SALES_PERSON);

        // Get all the peopleList where isSalesPerson equals to UPDATED_IS_SALES_PERSON
        defaultPeopleShouldNotBeFound("isSalesPerson.equals=" + UPDATED_IS_SALES_PERSON);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSalesPersonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSalesPerson not equals to DEFAULT_IS_SALES_PERSON
        defaultPeopleShouldNotBeFound("isSalesPerson.notEquals=" + DEFAULT_IS_SALES_PERSON);

        // Get all the peopleList where isSalesPerson not equals to UPDATED_IS_SALES_PERSON
        defaultPeopleShouldBeFound("isSalesPerson.notEquals=" + UPDATED_IS_SALES_PERSON);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSalesPersonIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSalesPerson in DEFAULT_IS_SALES_PERSON or UPDATED_IS_SALES_PERSON
        defaultPeopleShouldBeFound("isSalesPerson.in=" + DEFAULT_IS_SALES_PERSON + "," + UPDATED_IS_SALES_PERSON);

        // Get all the peopleList where isSalesPerson equals to UPDATED_IS_SALES_PERSON
        defaultPeopleShouldNotBeFound("isSalesPerson.in=" + UPDATED_IS_SALES_PERSON);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsSalesPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isSalesPerson is not null
        defaultPeopleShouldBeFound("isSalesPerson.specified=true");

        // Get all the peopleList where isSalesPerson is null
        defaultPeopleShouldNotBeFound("isSalesPerson.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByIsGuestUserIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isGuestUser equals to DEFAULT_IS_GUEST_USER
        defaultPeopleShouldBeFound("isGuestUser.equals=" + DEFAULT_IS_GUEST_USER);

        // Get all the peopleList where isGuestUser equals to UPDATED_IS_GUEST_USER
        defaultPeopleShouldNotBeFound("isGuestUser.equals=" + UPDATED_IS_GUEST_USER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsGuestUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isGuestUser not equals to DEFAULT_IS_GUEST_USER
        defaultPeopleShouldNotBeFound("isGuestUser.notEquals=" + DEFAULT_IS_GUEST_USER);

        // Get all the peopleList where isGuestUser not equals to UPDATED_IS_GUEST_USER
        defaultPeopleShouldBeFound("isGuestUser.notEquals=" + UPDATED_IS_GUEST_USER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsGuestUserIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isGuestUser in DEFAULT_IS_GUEST_USER or UPDATED_IS_GUEST_USER
        defaultPeopleShouldBeFound("isGuestUser.in=" + DEFAULT_IS_GUEST_USER + "," + UPDATED_IS_GUEST_USER);

        // Get all the peopleList where isGuestUser equals to UPDATED_IS_GUEST_USER
        defaultPeopleShouldNotBeFound("isGuestUser.in=" + UPDATED_IS_GUEST_USER);
    }

    @Test
    @Transactional
    public void getAllPeopleByIsGuestUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where isGuestUser is not null
        defaultPeopleShouldBeFound("isGuestUser.specified=true");

        // Get all the peopleList where isGuestUser is null
        defaultPeopleShouldNotBeFound("isGuestUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion equals to DEFAULT_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.equals=" + DEFAULT_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion equals to UPDATED_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.equals=" + UPDATED_EMAIL_PROMOTION);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion not equals to DEFAULT_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.notEquals=" + DEFAULT_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion not equals to UPDATED_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.notEquals=" + UPDATED_EMAIL_PROMOTION);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion in DEFAULT_EMAIL_PROMOTION or UPDATED_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.in=" + DEFAULT_EMAIL_PROMOTION + "," + UPDATED_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion equals to UPDATED_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.in=" + UPDATED_EMAIL_PROMOTION);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion is not null
        defaultPeopleShouldBeFound("emailPromotion.specified=true");

        // Get all the peopleList where emailPromotion is null
        defaultPeopleShouldNotBeFound("emailPromotion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion is greater than or equal to DEFAULT_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.greaterThanOrEqual=" + DEFAULT_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion is greater than or equal to UPDATED_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.greaterThanOrEqual=" + UPDATED_EMAIL_PROMOTION);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion is less than or equal to DEFAULT_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.lessThanOrEqual=" + DEFAULT_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion is less than or equal to SMALLER_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.lessThanOrEqual=" + SMALLER_EMAIL_PROMOTION);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsLessThanSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion is less than DEFAULT_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.lessThan=" + DEFAULT_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion is less than UPDATED_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.lessThan=" + UPDATED_EMAIL_PROMOTION);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailPromotionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailPromotion is greater than DEFAULT_EMAIL_PROMOTION
        defaultPeopleShouldNotBeFound("emailPromotion.greaterThan=" + DEFAULT_EMAIL_PROMOTION);

        // Get all the peopleList where emailPromotion is greater than SMALLER_EMAIL_PROMOTION
        defaultPeopleShouldBeFound("emailPromotion.greaterThan=" + SMALLER_EMAIL_PROMOTION);
    }


    @Test
    @Transactional
    public void getAllPeopleByUserPreferencesIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where userPreferences equals to DEFAULT_USER_PREFERENCES
        defaultPeopleShouldBeFound("userPreferences.equals=" + DEFAULT_USER_PREFERENCES);

        // Get all the peopleList where userPreferences equals to UPDATED_USER_PREFERENCES
        defaultPeopleShouldNotBeFound("userPreferences.equals=" + UPDATED_USER_PREFERENCES);
    }

    @Test
    @Transactional
    public void getAllPeopleByUserPreferencesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where userPreferences not equals to DEFAULT_USER_PREFERENCES
        defaultPeopleShouldNotBeFound("userPreferences.notEquals=" + DEFAULT_USER_PREFERENCES);

        // Get all the peopleList where userPreferences not equals to UPDATED_USER_PREFERENCES
        defaultPeopleShouldBeFound("userPreferences.notEquals=" + UPDATED_USER_PREFERENCES);
    }

    @Test
    @Transactional
    public void getAllPeopleByUserPreferencesIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where userPreferences in DEFAULT_USER_PREFERENCES or UPDATED_USER_PREFERENCES
        defaultPeopleShouldBeFound("userPreferences.in=" + DEFAULT_USER_PREFERENCES + "," + UPDATED_USER_PREFERENCES);

        // Get all the peopleList where userPreferences equals to UPDATED_USER_PREFERENCES
        defaultPeopleShouldNotBeFound("userPreferences.in=" + UPDATED_USER_PREFERENCES);
    }

    @Test
    @Transactional
    public void getAllPeopleByUserPreferencesIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where userPreferences is not null
        defaultPeopleShouldBeFound("userPreferences.specified=true");

        // Get all the peopleList where userPreferences is null
        defaultPeopleShouldNotBeFound("userPreferences.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByUserPreferencesContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where userPreferences contains DEFAULT_USER_PREFERENCES
        defaultPeopleShouldBeFound("userPreferences.contains=" + DEFAULT_USER_PREFERENCES);

        // Get all the peopleList where userPreferences contains UPDATED_USER_PREFERENCES
        defaultPeopleShouldNotBeFound("userPreferences.contains=" + UPDATED_USER_PREFERENCES);
    }

    @Test
    @Transactional
    public void getAllPeopleByUserPreferencesNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where userPreferences does not contain DEFAULT_USER_PREFERENCES
        defaultPeopleShouldNotBeFound("userPreferences.doesNotContain=" + DEFAULT_USER_PREFERENCES);

        // Get all the peopleList where userPreferences does not contain UPDATED_USER_PREFERENCES
        defaultPeopleShouldBeFound("userPreferences.doesNotContain=" + UPDATED_USER_PREFERENCES);
    }


    @Test
    @Transactional
    public void getAllPeopleByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultPeopleShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the peopleList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPeopleShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultPeopleShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the peopleList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultPeopleShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultPeopleShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the peopleList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPeopleShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where phoneNumber is not null
        defaultPeopleShouldBeFound("phoneNumber.specified=true");

        // Get all the peopleList where phoneNumber is null
        defaultPeopleShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultPeopleShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the peopleList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultPeopleShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultPeopleShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the peopleList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultPeopleShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPeopleByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultPeopleShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the peopleList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultPeopleShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultPeopleShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the peopleList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultPeopleShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultPeopleShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the peopleList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultPeopleShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailAddress is not null
        defaultPeopleShouldBeFound("emailAddress.specified=true");

        // Get all the peopleList where emailAddress is null
        defaultPeopleShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultPeopleShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the peopleList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultPeopleShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPeopleByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultPeopleShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the peopleList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultPeopleShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllPeopleByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where photo equals to DEFAULT_PHOTO
        defaultPeopleShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the peopleList where photo equals to UPDATED_PHOTO
        defaultPeopleShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where photo not equals to DEFAULT_PHOTO
        defaultPeopleShouldNotBeFound("photo.notEquals=" + DEFAULT_PHOTO);

        // Get all the peopleList where photo not equals to UPDATED_PHOTO
        defaultPeopleShouldBeFound("photo.notEquals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultPeopleShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the peopleList where photo equals to UPDATED_PHOTO
        defaultPeopleShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where photo is not null
        defaultPeopleShouldBeFound("photo.specified=true");

        // Get all the peopleList where photo is null
        defaultPeopleShouldNotBeFound("photo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByPhotoContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where photo contains DEFAULT_PHOTO
        defaultPeopleShouldBeFound("photo.contains=" + DEFAULT_PHOTO);

        // Get all the peopleList where photo contains UPDATED_PHOTO
        defaultPeopleShouldNotBeFound("photo.contains=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    public void getAllPeopleByPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where photo does not contain DEFAULT_PHOTO
        defaultPeopleShouldNotBeFound("photo.doesNotContain=" + DEFAULT_PHOTO);

        // Get all the peopleList where photo does not contain UPDATED_PHOTO
        defaultPeopleShouldBeFound("photo.doesNotContain=" + UPDATED_PHOTO);
    }


    @Test
    @Transactional
    public void getAllPeopleByCustomFieldsIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where customFields equals to DEFAULT_CUSTOM_FIELDS
        defaultPeopleShouldBeFound("customFields.equals=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the peopleList where customFields equals to UPDATED_CUSTOM_FIELDS
        defaultPeopleShouldNotBeFound("customFields.equals=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllPeopleByCustomFieldsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where customFields not equals to DEFAULT_CUSTOM_FIELDS
        defaultPeopleShouldNotBeFound("customFields.notEquals=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the peopleList where customFields not equals to UPDATED_CUSTOM_FIELDS
        defaultPeopleShouldBeFound("customFields.notEquals=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllPeopleByCustomFieldsIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where customFields in DEFAULT_CUSTOM_FIELDS or UPDATED_CUSTOM_FIELDS
        defaultPeopleShouldBeFound("customFields.in=" + DEFAULT_CUSTOM_FIELDS + "," + UPDATED_CUSTOM_FIELDS);

        // Get all the peopleList where customFields equals to UPDATED_CUSTOM_FIELDS
        defaultPeopleShouldNotBeFound("customFields.in=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllPeopleByCustomFieldsIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where customFields is not null
        defaultPeopleShouldBeFound("customFields.specified=true");

        // Get all the peopleList where customFields is null
        defaultPeopleShouldNotBeFound("customFields.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByCustomFieldsContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where customFields contains DEFAULT_CUSTOM_FIELDS
        defaultPeopleShouldBeFound("customFields.contains=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the peopleList where customFields contains UPDATED_CUSTOM_FIELDS
        defaultPeopleShouldNotBeFound("customFields.contains=" + UPDATED_CUSTOM_FIELDS);
    }

    @Test
    @Transactional
    public void getAllPeopleByCustomFieldsNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where customFields does not contain DEFAULT_CUSTOM_FIELDS
        defaultPeopleShouldNotBeFound("customFields.doesNotContain=" + DEFAULT_CUSTOM_FIELDS);

        // Get all the peopleList where customFields does not contain UPDATED_CUSTOM_FIELDS
        defaultPeopleShouldBeFound("customFields.doesNotContain=" + UPDATED_CUSTOM_FIELDS);
    }


    @Test
    @Transactional
    public void getAllPeopleByOtherLanguagesIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where otherLanguages equals to DEFAULT_OTHER_LANGUAGES
        defaultPeopleShouldBeFound("otherLanguages.equals=" + DEFAULT_OTHER_LANGUAGES);

        // Get all the peopleList where otherLanguages equals to UPDATED_OTHER_LANGUAGES
        defaultPeopleShouldNotBeFound("otherLanguages.equals=" + UPDATED_OTHER_LANGUAGES);
    }

    @Test
    @Transactional
    public void getAllPeopleByOtherLanguagesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where otherLanguages not equals to DEFAULT_OTHER_LANGUAGES
        defaultPeopleShouldNotBeFound("otherLanguages.notEquals=" + DEFAULT_OTHER_LANGUAGES);

        // Get all the peopleList where otherLanguages not equals to UPDATED_OTHER_LANGUAGES
        defaultPeopleShouldBeFound("otherLanguages.notEquals=" + UPDATED_OTHER_LANGUAGES);
    }

    @Test
    @Transactional
    public void getAllPeopleByOtherLanguagesIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where otherLanguages in DEFAULT_OTHER_LANGUAGES or UPDATED_OTHER_LANGUAGES
        defaultPeopleShouldBeFound("otherLanguages.in=" + DEFAULT_OTHER_LANGUAGES + "," + UPDATED_OTHER_LANGUAGES);

        // Get all the peopleList where otherLanguages equals to UPDATED_OTHER_LANGUAGES
        defaultPeopleShouldNotBeFound("otherLanguages.in=" + UPDATED_OTHER_LANGUAGES);
    }

    @Test
    @Transactional
    public void getAllPeopleByOtherLanguagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where otherLanguages is not null
        defaultPeopleShouldBeFound("otherLanguages.specified=true");

        // Get all the peopleList where otherLanguages is null
        defaultPeopleShouldNotBeFound("otherLanguages.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeopleByOtherLanguagesContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where otherLanguages contains DEFAULT_OTHER_LANGUAGES
        defaultPeopleShouldBeFound("otherLanguages.contains=" + DEFAULT_OTHER_LANGUAGES);

        // Get all the peopleList where otherLanguages contains UPDATED_OTHER_LANGUAGES
        defaultPeopleShouldNotBeFound("otherLanguages.contains=" + UPDATED_OTHER_LANGUAGES);
    }

    @Test
    @Transactional
    public void getAllPeopleByOtherLanguagesNotContainsSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where otherLanguages does not contain DEFAULT_OTHER_LANGUAGES
        defaultPeopleShouldNotBeFound("otherLanguages.doesNotContain=" + DEFAULT_OTHER_LANGUAGES);

        // Get all the peopleList where otherLanguages does not contain UPDATED_OTHER_LANGUAGES
        defaultPeopleShouldBeFound("otherLanguages.doesNotContain=" + UPDATED_OTHER_LANGUAGES);
    }


    @Test
    @Transactional
    public void getAllPeopleByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validFrom equals to DEFAULT_VALID_FROM
        defaultPeopleShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the peopleList where validFrom equals to UPDATED_VALID_FROM
        defaultPeopleShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPeopleByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPeopleShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the peopleList where validFrom not equals to UPDATED_VALID_FROM
        defaultPeopleShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPeopleByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPeopleShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the peopleList where validFrom equals to UPDATED_VALID_FROM
        defaultPeopleShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPeopleByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validFrom is not null
        defaultPeopleShouldBeFound("validFrom.specified=true");

        // Get all the peopleList where validFrom is null
        defaultPeopleShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validTo equals to DEFAULT_VALID_TO
        defaultPeopleShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the peopleList where validTo equals to UPDATED_VALID_TO
        defaultPeopleShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPeopleByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validTo not equals to DEFAULT_VALID_TO
        defaultPeopleShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the peopleList where validTo not equals to UPDATED_VALID_TO
        defaultPeopleShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPeopleByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPeopleShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the peopleList where validTo equals to UPDATED_VALID_TO
        defaultPeopleShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPeopleByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList where validTo is not null
        defaultPeopleShouldBeFound("validTo.specified=true");

        // Get all the peopleList where validTo is null
        defaultPeopleShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = people.getUser();
        peopleRepository.saveAndFlush(people);
        String userId = user.getId();

        // Get all the peopleList where user equals to userId
        defaultPeopleShouldBeFound("userId.equals=" + userId);

        // Get all the peopleList where user equals to userId + 1
        defaultPeopleShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPeopleByCartIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);
        ShoppingCarts cart = ShoppingCartsResourceIT.createEntity(em);
        em.persist(cart);
        em.flush();
        people.setCart(cart);
        cart.setCartUser(people);
        peopleRepository.saveAndFlush(people);
        Long cartId = cart.getId();

        // Get all the peopleList where cart equals to cartId
        defaultPeopleShouldBeFound("cartId.equals=" + cartId);

        // Get all the peopleList where cart equals to cartId + 1
        defaultPeopleShouldNotBeFound("cartId.equals=" + (cartId + 1));
    }


    @Test
    @Transactional
    public void getAllPeopleByWishlistIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);
        Wishlists wishlist = WishlistsResourceIT.createEntity(em);
        em.persist(wishlist);
        em.flush();
        people.setWishlist(wishlist);
        wishlist.setWishlistUser(people);
        peopleRepository.saveAndFlush(people);
        Long wishlistId = wishlist.getId();

        // Get all the peopleList where wishlist equals to wishlistId
        defaultPeopleShouldBeFound("wishlistId.equals=" + wishlistId);

        // Get all the peopleList where wishlist equals to wishlistId + 1
        defaultPeopleShouldNotBeFound("wishlistId.equals=" + (wishlistId + 1));
    }


    @Test
    @Transactional
    public void getAllPeopleByCompareIsEqualToSomething() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);
        Compares compare = ComparesResourceIT.createEntity(em);
        em.persist(compare);
        em.flush();
        people.setCompare(compare);
        compare.setCompareUser(people);
        peopleRepository.saveAndFlush(people);
        Long compareId = compare.getId();

        // Get all the peopleList where compare equals to compareId
        defaultPeopleShouldBeFound("compareId.equals=" + compareId);

        // Get all the peopleList where compare equals to compareId + 1
        defaultPeopleShouldNotBeFound("compareId.equals=" + (compareId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeopleShouldBeFound(String filter) throws Exception {
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].preferredName").value(hasItem(DEFAULT_PREFERRED_NAME)))
            .andExpect(jsonPath("$.[*].searchName").value(hasItem(DEFAULT_SEARCH_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].isPermittedToLogon").value(hasItem(DEFAULT_IS_PERMITTED_TO_LOGON.booleanValue())))
            .andExpect(jsonPath("$.[*].logonName").value(hasItem(DEFAULT_LOGON_NAME)))
            .andExpect(jsonPath("$.[*].isExternalLogonProvider").value(hasItem(DEFAULT_IS_EXTERNAL_LOGON_PROVIDER.booleanValue())))
            .andExpect(jsonPath("$.[*].isSystemUser").value(hasItem(DEFAULT_IS_SYSTEM_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].isEmployee").value(hasItem(DEFAULT_IS_EMPLOYEE.booleanValue())))
            .andExpect(jsonPath("$.[*].isSalesPerson").value(hasItem(DEFAULT_IS_SALES_PERSON.booleanValue())))
            .andExpect(jsonPath("$.[*].isGuestUser").value(hasItem(DEFAULT_IS_GUEST_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].emailPromotion").value(hasItem(DEFAULT_EMAIL_PROMOTION)))
            .andExpect(jsonPath("$.[*].userPreferences").value(hasItem(DEFAULT_USER_PREFERENCES)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].customFields").value(hasItem(DEFAULT_CUSTOM_FIELDS)))
            .andExpect(jsonPath("$.[*].otherLanguages").value(hasItem(DEFAULT_OTHER_LANGUAGES)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPeopleMockMvc.perform(get("/api/people/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeopleShouldNotBeFound(String filter) throws Exception {
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeopleMockMvc.perform(get("/api/people/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Update the people
        People updatedPeople = peopleRepository.findById(people.getId()).get();
        // Disconnect from session so that the updates on updatedPeople are not directly saved in db
        em.detach(updatedPeople);
        updatedPeople
            .fullName(UPDATED_FULL_NAME)
            .preferredName(UPDATED_PREFERRED_NAME)
            .searchName(UPDATED_SEARCH_NAME)
            .gender(UPDATED_GENDER)
            .isPermittedToLogon(UPDATED_IS_PERMITTED_TO_LOGON)
            .logonName(UPDATED_LOGON_NAME)
            .isExternalLogonProvider(UPDATED_IS_EXTERNAL_LOGON_PROVIDER)
            .isSystemUser(UPDATED_IS_SYSTEM_USER)
            .isEmployee(UPDATED_IS_EMPLOYEE)
            .isSalesPerson(UPDATED_IS_SALES_PERSON)
            .isGuestUser(UPDATED_IS_GUEST_USER)
            .emailPromotion(UPDATED_EMAIL_PROMOTION)
            .userPreferences(UPDATED_USER_PREFERENCES)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .photo(UPDATED_PHOTO)
            .customFields(UPDATED_CUSTOM_FIELDS)
            .otherLanguages(UPDATED_OTHER_LANGUAGES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PeopleDTO peopleDTO = peopleMapper.toDto(updatedPeople);

        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isOk());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testPeople.getPreferredName()).isEqualTo(UPDATED_PREFERRED_NAME);
        assertThat(testPeople.getSearchName()).isEqualTo(UPDATED_SEARCH_NAME);
        assertThat(testPeople.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPeople.isIsPermittedToLogon()).isEqualTo(UPDATED_IS_PERMITTED_TO_LOGON);
        assertThat(testPeople.getLogonName()).isEqualTo(UPDATED_LOGON_NAME);
        assertThat(testPeople.isIsExternalLogonProvider()).isEqualTo(UPDATED_IS_EXTERNAL_LOGON_PROVIDER);
        assertThat(testPeople.isIsSystemUser()).isEqualTo(UPDATED_IS_SYSTEM_USER);
        assertThat(testPeople.isIsEmployee()).isEqualTo(UPDATED_IS_EMPLOYEE);
        assertThat(testPeople.isIsSalesPerson()).isEqualTo(UPDATED_IS_SALES_PERSON);
        assertThat(testPeople.isIsGuestUser()).isEqualTo(UPDATED_IS_GUEST_USER);
        assertThat(testPeople.getEmailPromotion()).isEqualTo(UPDATED_EMAIL_PROMOTION);
        assertThat(testPeople.getUserPreferences()).isEqualTo(UPDATED_USER_PREFERENCES);
        assertThat(testPeople.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPeople.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testPeople.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPeople.getCustomFields()).isEqualTo(UPDATED_CUSTOM_FIELDS);
        assertThat(testPeople.getOtherLanguages()).isEqualTo(UPDATED_OTHER_LANGUAGES);
        assertThat(testPeople.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPeople.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPeople() throws Exception {
        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeDelete = peopleRepository.findAll().size();

        // Delete the people
        restPeopleMockMvc.perform(delete("/api/people/{id}", people.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
