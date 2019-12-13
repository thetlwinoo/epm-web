package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.PersonEmailAddress;
import com.epmweb.server.domain.People;
import com.epmweb.server.repository.PersonEmailAddressRepository;
import com.epmweb.server.service.PersonEmailAddressService;
import com.epmweb.server.service.dto.PersonEmailAddressDTO;
import com.epmweb.server.service.mapper.PersonEmailAddressMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.PersonEmailAddressCriteria;
import com.epmweb.server.service.PersonEmailAddressQueryService;

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
 * Integration tests for the {@link PersonEmailAddressResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class PersonEmailAddressResourceIT {

    private static final String DEFAULT_EMAIL_ADDRESS = "9*@J\\.b)";
    private static final String UPDATED_EMAIL_ADDRESS = "XS@S.X";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    @Autowired
    private PersonEmailAddressRepository personEmailAddressRepository;

    @Autowired
    private PersonEmailAddressMapper personEmailAddressMapper;

    @Autowired
    private PersonEmailAddressService personEmailAddressService;

    @Autowired
    private PersonEmailAddressQueryService personEmailAddressQueryService;

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

    private MockMvc restPersonEmailAddressMockMvc;

    private PersonEmailAddress personEmailAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonEmailAddressResource personEmailAddressResource = new PersonEmailAddressResource(personEmailAddressService, personEmailAddressQueryService);
        this.restPersonEmailAddressMockMvc = MockMvcBuilders.standaloneSetup(personEmailAddressResource)
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
    public static PersonEmailAddress createEntity(EntityManager em) {
        PersonEmailAddress personEmailAddress = new PersonEmailAddress()
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .activeInd(DEFAULT_ACTIVE_IND);
        return personEmailAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEmailAddress createUpdatedEntity(EntityManager em) {
        PersonEmailAddress personEmailAddress = new PersonEmailAddress()
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        return personEmailAddress;
    }

    @BeforeEach
    public void initTest() {
        personEmailAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonEmailAddress() throws Exception {
        int databaseSizeBeforeCreate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);
        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEmailAddress testPersonEmailAddress = personEmailAddressList.get(personEmailAddressList.size() - 1);
        assertThat(testPersonEmailAddress.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testPersonEmailAddress.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPersonEmailAddress.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void createPersonEmailAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress with an existing ID
        personEmailAddress.setId(1L);
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = personEmailAddressRepository.findAll().size();
        // set the field null
        personEmailAddress.setEmailAddress(null);

        // Create the PersonEmailAddress, which fails.
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        restPersonEmailAddressMockMvc.perform(post("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddresses() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEmailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get the personEmailAddress
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/{id}", personEmailAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personEmailAddress.getId().intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
    }


    @Test
    @Transactional
    public void getPersonEmailAddressesByIdFiltering() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        Long id = personEmailAddress.getId();

        defaultPersonEmailAddressShouldBeFound("id.equals=" + id);
        defaultPersonEmailAddressShouldNotBeFound("id.notEquals=" + id);

        defaultPersonEmailAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonEmailAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonEmailAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonEmailAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress is not null
        defaultPersonEmailAddressShouldBeFound("emailAddress.specified=true");

        // Get all the personEmailAddressList where emailAddress is null
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the personEmailAddressList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultPersonEmailAddressShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultPersonEmailAddressShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the personEmailAddressList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the personEmailAddressList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the personEmailAddressList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where defaultInd is not null
        defaultPersonEmailAddressShouldBeFound("defaultInd.specified=true");

        // Get all the personEmailAddressList where defaultInd is null
        defaultPersonEmailAddressShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultPersonEmailAddressShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the personEmailAddressList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPersonEmailAddressShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultPersonEmailAddressShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the personEmailAddressList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultPersonEmailAddressShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultPersonEmailAddressShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the personEmailAddressList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPersonEmailAddressShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        // Get all the personEmailAddressList where activeInd is not null
        defaultPersonEmailAddressShouldBeFound("activeInd.specified=true");

        // Get all the personEmailAddressList where activeInd is null
        defaultPersonEmailAddressShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonEmailAddressesByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        personEmailAddress.setPerson(person);
        personEmailAddressRepository.saveAndFlush(personEmailAddress);
        Long personId = person.getId();

        // Get all the personEmailAddressList where person equals to personId
        defaultPersonEmailAddressShouldBeFound("personId.equals=" + personId);

        // Get all the personEmailAddressList where person equals to personId + 1
        defaultPersonEmailAddressShouldNotBeFound("personId.equals=" + (personId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonEmailAddressShouldBeFound(String filter) throws Exception {
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEmailAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));

        // Check, that the count call also returns 1
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonEmailAddressShouldNotBeFound(String filter) throws Exception {
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPersonEmailAddress() throws Exception {
        // Get the personEmailAddress
        restPersonEmailAddressMockMvc.perform(get("/api/person-email-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        int databaseSizeBeforeUpdate = personEmailAddressRepository.findAll().size();

        // Update the personEmailAddress
        PersonEmailAddress updatedPersonEmailAddress = personEmailAddressRepository.findById(personEmailAddress.getId()).get();
        // Disconnect from session so that the updates on updatedPersonEmailAddress are not directly saved in db
        em.detach(updatedPersonEmailAddress);
        updatedPersonEmailAddress
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(updatedPersonEmailAddress);

        restPersonEmailAddressMockMvc.perform(put("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isOk());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeUpdate);
        PersonEmailAddress testPersonEmailAddress = personEmailAddressList.get(personEmailAddressList.size() - 1);
        assertThat(testPersonEmailAddress.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testPersonEmailAddress.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPersonEmailAddress.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonEmailAddress() throws Exception {
        int databaseSizeBeforeUpdate = personEmailAddressRepository.findAll().size();

        // Create the PersonEmailAddress
        PersonEmailAddressDTO personEmailAddressDTO = personEmailAddressMapper.toDto(personEmailAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonEmailAddressMockMvc.perform(put("/api/person-email-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personEmailAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonEmailAddress in the database
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonEmailAddress() throws Exception {
        // Initialize the database
        personEmailAddressRepository.saveAndFlush(personEmailAddress);

        int databaseSizeBeforeDelete = personEmailAddressRepository.findAll().size();

        // Delete the personEmailAddress
        restPersonEmailAddressMockMvc.perform(delete("/api/person-email-addresses/{id}", personEmailAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonEmailAddress> personEmailAddressList = personEmailAddressRepository.findAll();
        assertThat(personEmailAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
