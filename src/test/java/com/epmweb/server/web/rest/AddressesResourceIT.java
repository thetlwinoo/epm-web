package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Addresses;
import com.epmweb.server.domain.StateProvinces;
import com.epmweb.server.domain.AddressTypes;
import com.epmweb.server.domain.People;
import com.epmweb.server.repository.AddressesRepository;
import com.epmweb.server.service.AddressesService;
import com.epmweb.server.service.dto.AddressesDTO;
import com.epmweb.server.service.mapper.AddressesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.AddressesCriteria;
import com.epmweb.server.service.AddressesQueryService;

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
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AddressesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class AddressesResourceIT {

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL_ADDRESS = "3y@z_.Z";
    private static final String UPDATED_CONTACT_EMAIL_ADDRESS = "i@*.=";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private AddressesMapper addressesMapper;

    @Autowired
    private AddressesService addressesService;

    @Autowired
    private AddressesQueryService addressesQueryService;

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

    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressesResource addressesResource = new AddressesResource(addressesService, addressesQueryService);
        this.restAddressesMockMvc = MockMvcBuilders.standaloneSetup(addressesResource)
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
    public static Addresses createEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .contactEmailAddress(DEFAULT_CONTACT_EMAIL_ADDRESS)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .activeInd(DEFAULT_ACTIVE_IND);
        return addresses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createUpdatedEntity(EntityManager em) {
        Addresses addresses = new Addresses()
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        return addresses;
    }

    @BeforeEach
    public void initTest() {
        addresses = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);
        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testAddresses.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testAddresses.getContactEmailAddress()).isEqualTo(DEFAULT_CONTACT_EMAIL_ADDRESS);
        assertThat(testAddresses.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testAddresses.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testAddresses.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddresses.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAddresses.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testAddresses.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void createAddressesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // Create the Addresses with an existing ID
        addresses.setId(1L);
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContactPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setContactPerson(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setContactNumber(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressLine1IsRequired() throws Exception {
        int databaseSizeBeforeTest = addressesRepository.findAll().size();
        // set the field null
        addresses.setAddressLine1(null);

        // Create the Addresses, which fails.
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        restAddressesMockMvc.perform(post("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].contactEmailAddress").value(hasItem(DEFAULT_CONTACT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addresses/{id}", addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addresses.getId().intValue()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER))
            .andExpect(jsonPath("$.contactEmailAddress").value(DEFAULT_CONTACT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
    }


    @Test
    @Transactional
    public void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        Long id = addresses.getId();

        defaultAddressesShouldBeFound("id.equals=" + id);
        defaultAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson equals to DEFAULT_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.equals=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.equals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson not equals to DEFAULT_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.notEquals=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson not equals to UPDATED_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.notEquals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson in DEFAULT_CONTACT_PERSON or UPDATED_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.in=" + DEFAULT_CONTACT_PERSON + "," + UPDATED_CONTACT_PERSON);

        // Get all the addressesList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.in=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson is not null
        defaultAddressesShouldBeFound("contactPerson.specified=true");

        // Get all the addressesList where contactPerson is null
        defaultAddressesShouldNotBeFound("contactPerson.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByContactPersonContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson contains DEFAULT_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.contains=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson contains UPDATED_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.contains=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactPersonNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactPerson does not contain DEFAULT_CONTACT_PERSON
        defaultAddressesShouldNotBeFound("contactPerson.doesNotContain=" + DEFAULT_CONTACT_PERSON);

        // Get all the addressesList where contactPerson does not contain UPDATED_CONTACT_PERSON
        defaultAddressesShouldBeFound("contactPerson.doesNotContain=" + UPDATED_CONTACT_PERSON);
    }


    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber not equals to DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.notEquals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber not equals to UPDATED_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.notEquals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber is not null
        defaultAddressesShouldBeFound("contactNumber.specified=true");

        // Get all the addressesList where contactNumber is null
        defaultAddressesShouldNotBeFound("contactNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByContactNumberContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber contains DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.contains=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber contains UPDATED_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.contains=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactNumberNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactNumber does not contain DEFAULT_CONTACT_NUMBER
        defaultAddressesShouldNotBeFound("contactNumber.doesNotContain=" + DEFAULT_CONTACT_NUMBER);

        // Get all the addressesList where contactNumber does not contain UPDATED_CONTACT_NUMBER
        defaultAddressesShouldBeFound("contactNumber.doesNotContain=" + UPDATED_CONTACT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress equals to DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.equals=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.equals=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress not equals to DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.notEquals=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress not equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.notEquals=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress in DEFAULT_CONTACT_EMAIL_ADDRESS or UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.in=" + DEFAULT_CONTACT_EMAIL_ADDRESS + "," + UPDATED_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress equals to UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.in=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress is not null
        defaultAddressesShouldBeFound("contactEmailAddress.specified=true");

        // Get all the addressesList where contactEmailAddress is null
        defaultAddressesShouldNotBeFound("contactEmailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress contains DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.contains=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress contains UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.contains=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllAddressesByContactEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where contactEmailAddress does not contain DEFAULT_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldNotBeFound("contactEmailAddress.doesNotContain=" + DEFAULT_CONTACT_EMAIL_ADDRESS);

        // Get all the addressesList where contactEmailAddress does not contain UPDATED_CONTACT_EMAIL_ADDRESS
        defaultAddressesShouldBeFound("contactEmailAddress.doesNotContain=" + UPDATED_CONTACT_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 equals to DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.equals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.equals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 not equals to DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.notEquals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 not equals to UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.notEquals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 in DEFAULT_ADDRESS_LINE_1 or UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.in=" + DEFAULT_ADDRESS_LINE_1 + "," + UPDATED_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.in=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 is not null
        defaultAddressesShouldBeFound("addressLine1.specified=true");

        // Get all the addressesList where addressLine1 is null
        defaultAddressesShouldNotBeFound("addressLine1.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByAddressLine1ContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 contains DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.contains=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 contains UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.contains=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine1NotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine1 does not contain DEFAULT_ADDRESS_LINE_1
        defaultAddressesShouldNotBeFound("addressLine1.doesNotContain=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressesList where addressLine1 does not contain UPDATED_ADDRESS_LINE_1
        defaultAddressesShouldBeFound("addressLine1.doesNotContain=" + UPDATED_ADDRESS_LINE_1);
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 equals to DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.equals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.equals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 not equals to DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.notEquals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 not equals to UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.notEquals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 in DEFAULT_ADDRESS_LINE_2 or UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.in=" + DEFAULT_ADDRESS_LINE_2 + "," + UPDATED_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.in=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 is not null
        defaultAddressesShouldBeFound("addressLine2.specified=true");

        // Get all the addressesList where addressLine2 is null
        defaultAddressesShouldNotBeFound("addressLine2.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByAddressLine2ContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 contains DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.contains=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 contains UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.contains=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    public void getAllAddressesByAddressLine2NotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where addressLine2 does not contain DEFAULT_ADDRESS_LINE_2
        defaultAddressesShouldNotBeFound("addressLine2.doesNotContain=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressesList where addressLine2 does not contain UPDATED_ADDRESS_LINE_2
        defaultAddressesShouldBeFound("addressLine2.doesNotContain=" + UPDATED_ADDRESS_LINE_2);
    }


    @Test
    @Transactional
    public void getAllAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city equals to DEFAULT_CITY
        defaultAddressesShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the addressesList where city equals to UPDATED_CITY
        defaultAddressesShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city not equals to DEFAULT_CITY
        defaultAddressesShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the addressesList where city not equals to UPDATED_CITY
        defaultAddressesShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city in DEFAULT_CITY or UPDATED_CITY
        defaultAddressesShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the addressesList where city equals to UPDATED_CITY
        defaultAddressesShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city is not null
        defaultAddressesShouldBeFound("city.specified=true");

        // Get all the addressesList where city is null
        defaultAddressesShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByCityContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city contains DEFAULT_CITY
        defaultAddressesShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the addressesList where city contains UPDATED_CITY
        defaultAddressesShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where city does not contain DEFAULT_CITY
        defaultAddressesShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the addressesList where city does not contain UPDATED_CITY
        defaultAddressesShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the addressesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode is not null
        defaultAddressesShouldBeFound("postalCode.specified=true");

        // Get all the addressesList where postalCode is null
        defaultAddressesShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode contains DEFAULT_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode contains UPDATED_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultAddressesShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the addressesList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultAddressesShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressesByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultAddressesShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the addressesList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultAddressesShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllAddressesByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultAddressesShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the addressesList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultAddressesShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllAddressesByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultAddressesShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the addressesList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultAddressesShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllAddressesByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where defaultInd is not null
        defaultAddressesShouldBeFound("defaultInd.specified=true");

        // Get all the addressesList where defaultInd is null
        defaultAddressesShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultAddressesShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the addressesList where activeInd equals to UPDATED_ACTIVE_IND
        defaultAddressesShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllAddressesByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultAddressesShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the addressesList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultAddressesShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllAddressesByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultAddressesShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the addressesList where activeInd equals to UPDATED_ACTIVE_IND
        defaultAddressesShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllAddressesByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where activeInd is not null
        defaultAddressesShouldBeFound("activeInd.specified=true");

        // Get all the addressesList where activeInd is null
        defaultAddressesShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        StateProvinces stateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvince);
        em.flush();
        addresses.setStateProvince(stateProvince);
        addressesRepository.saveAndFlush(addresses);
        Long stateProvinceId = stateProvince.getId();

        // Get all the addressesList where stateProvince equals to stateProvinceId
        defaultAddressesShouldBeFound("stateProvinceId.equals=" + stateProvinceId);

        // Get all the addressesList where stateProvince equals to stateProvinceId + 1
        defaultAddressesShouldNotBeFound("stateProvinceId.equals=" + (stateProvinceId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        AddressTypes addressType = AddressTypesResourceIT.createEntity(em);
        em.persist(addressType);
        em.flush();
        addresses.setAddressType(addressType);
        addressesRepository.saveAndFlush(addresses);
        Long addressTypeId = addressType.getId();

        // Get all the addressesList where addressType equals to addressTypeId
        defaultAddressesShouldBeFound("addressTypeId.equals=" + addressTypeId);

        // Get all the addressesList where addressType equals to addressTypeId + 1
        defaultAddressesShouldNotBeFound("addressTypeId.equals=" + (addressTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllAddressesByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        addresses.setPerson(person);
        addressesRepository.saveAndFlush(addresses);
        Long personId = person.getId();

        // Get all the addressesList where person equals to personId
        defaultAddressesShouldBeFound("personId.equals=" + personId);

        // Get all the addressesList where person equals to personId + 1
        defaultAddressesShouldNotBeFound("personId.equals=" + (personId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressesShouldBeFound(String filter) throws Exception {
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].contactEmailAddress").value(hasItem(DEFAULT_CONTACT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));

        // Check, that the count call also returns 1
        restAddressesMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressesShouldNotBeFound(String filter) throws Exception {
        restAddressesMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressesMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses
        Addresses updatedAddresses = addressesRepository.findById(addresses.getId()).get();
        // Disconnect from session so that the updates on updatedAddresses are not directly saved in db
        em.detach(updatedAddresses);
        updatedAddresses
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .contactEmailAddress(UPDATED_CONTACT_EMAIL_ADDRESS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        AddressesDTO addressesDTO = addressesMapper.toDto(updatedAddresses);

        restAddressesMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testAddresses.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testAddresses.getContactEmailAddress()).isEqualTo(UPDATED_CONTACT_EMAIL_ADDRESS);
        assertThat(testAddresses.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testAddresses.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testAddresses.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddresses.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAddresses.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testAddresses.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void updateNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc.perform(put("/api/addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Delete the addresses
        restAddressesMockMvc.perform(delete("/api/addresses/{id}", addresses.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
