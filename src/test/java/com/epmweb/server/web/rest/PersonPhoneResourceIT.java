package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.PersonPhone;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.PhoneNumberType;
import com.epmweb.server.repository.PersonPhoneRepository;
import com.epmweb.server.service.PersonPhoneService;
import com.epmweb.server.service.dto.PersonPhoneDTO;
import com.epmweb.server.service.mapper.PersonPhoneMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.PersonPhoneCriteria;
import com.epmweb.server.service.PersonPhoneQueryService;

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
 * Integration tests for the {@link PersonPhoneResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class PersonPhoneResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    @Autowired
    private PersonPhoneRepository personPhoneRepository;

    @Autowired
    private PersonPhoneMapper personPhoneMapper;

    @Autowired
    private PersonPhoneService personPhoneService;

    @Autowired
    private PersonPhoneQueryService personPhoneQueryService;

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

    private MockMvc restPersonPhoneMockMvc;

    private PersonPhone personPhone;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonPhoneResource personPhoneResource = new PersonPhoneResource(personPhoneService, personPhoneQueryService);
        this.restPersonPhoneMockMvc = MockMvcBuilders.standaloneSetup(personPhoneResource)
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
    public static PersonPhone createEntity(EntityManager em) {
        PersonPhone personPhone = new PersonPhone()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .activeInd(DEFAULT_ACTIVE_IND);
        return personPhone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonPhone createUpdatedEntity(EntityManager em) {
        PersonPhone personPhone = new PersonPhone()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        return personPhone;
    }

    @BeforeEach
    public void initTest() {
        personPhone = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonPhone() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);
        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate + 1);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonPhone.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testPersonPhone.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void createPersonPhoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personPhoneRepository.findAll().size();

        // Create the PersonPhone with an existing ID
        personPhone.setId(1L);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = personPhoneRepository.findAll().size();
        // set the field null
        personPhone.setPhoneNumber(null);

        // Create the PersonPhone, which fails.
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        restPersonPhoneMockMvc.perform(post("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonPhones() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", personPhone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personPhone.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()));
    }


    @Test
    @Transactional
    public void getPersonPhonesByIdFiltering() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        Long id = personPhone.getId();

        defaultPersonPhoneShouldBeFound("id.equals=" + id);
        defaultPersonPhoneShouldNotBeFound("id.notEquals=" + id);

        defaultPersonPhoneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonPhoneShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonPhoneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonPhoneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber is not null
        defaultPersonPhoneShouldBeFound("phoneNumber.specified=true");

        // Get all the personPhoneList where phoneNumber is null
        defaultPersonPhoneShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultPersonPhoneShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the personPhoneList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultPersonPhoneShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultPersonPhoneShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the personPhoneList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultPersonPhoneShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the personPhoneList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the personPhoneList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPersonPhoneShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where defaultInd is not null
        defaultPersonPhoneShouldBeFound("defaultInd.specified=true");

        // Get all the personPhoneList where defaultInd is null
        defaultPersonPhoneShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultPersonPhoneShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the personPhoneList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPersonPhoneShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultPersonPhoneShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the personPhoneList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultPersonPhoneShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultPersonPhoneShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the personPhoneList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPersonPhoneShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        // Get all the personPhoneList where activeInd is not null
        defaultPersonPhoneShouldBeFound("activeInd.specified=true");

        // Get all the personPhoneList where activeInd is null
        defaultPersonPhoneShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonPhonesByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        personPhone.setPerson(person);
        personPhoneRepository.saveAndFlush(personPhone);
        Long personId = person.getId();

        // Get all the personPhoneList where person equals to personId
        defaultPersonPhoneShouldBeFound("personId.equals=" + personId);

        // Get all the personPhoneList where person equals to personId + 1
        defaultPersonPhoneShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonPhonesByPhoneNumberTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);
        PhoneNumberType phoneNumberType = PhoneNumberTypeResourceIT.createEntity(em);
        em.persist(phoneNumberType);
        em.flush();
        personPhone.setPhoneNumberType(phoneNumberType);
        personPhoneRepository.saveAndFlush(personPhone);
        Long phoneNumberTypeId = phoneNumberType.getId();

        // Get all the personPhoneList where phoneNumberType equals to phoneNumberTypeId
        defaultPersonPhoneShouldBeFound("phoneNumberTypeId.equals=" + phoneNumberTypeId);

        // Get all the personPhoneList where phoneNumberType equals to phoneNumberTypeId + 1
        defaultPersonPhoneShouldNotBeFound("phoneNumberTypeId.equals=" + (phoneNumberTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonPhoneShouldBeFound(String filter) throws Exception {
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personPhone.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())));

        // Check, that the count call also returns 1
        restPersonPhoneMockMvc.perform(get("/api/person-phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonPhoneShouldNotBeFound(String filter) throws Exception {
        restPersonPhoneMockMvc.perform(get("/api/person-phones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonPhoneMockMvc.perform(get("/api/person-phones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPersonPhone() throws Exception {
        // Get the personPhone
        restPersonPhoneMockMvc.perform(get("/api/person-phones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Update the personPhone
        PersonPhone updatedPersonPhone = personPhoneRepository.findById(personPhone.getId()).get();
        // Disconnect from session so that the updates on updatedPersonPhone are not directly saved in db
        em.detach(updatedPersonPhone);
        updatedPersonPhone
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .defaultInd(UPDATED_DEFAULT_IND)
            .activeInd(UPDATED_ACTIVE_IND);
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(updatedPersonPhone);

        restPersonPhoneMockMvc.perform(put("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isOk());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
        PersonPhone testPersonPhone = personPhoneList.get(personPhoneList.size() - 1);
        assertThat(testPersonPhone.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonPhone.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testPersonPhone.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonPhone() throws Exception {
        int databaseSizeBeforeUpdate = personPhoneRepository.findAll().size();

        // Create the PersonPhone
        PersonPhoneDTO personPhoneDTO = personPhoneMapper.toDto(personPhone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonPhoneMockMvc.perform(put("/api/person-phones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personPhoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonPhone in the database
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonPhone() throws Exception {
        // Initialize the database
        personPhoneRepository.saveAndFlush(personPhone);

        int databaseSizeBeforeDelete = personPhoneRepository.findAll().size();

        // Delete the personPhone
        restPersonPhoneMockMvc.perform(delete("/api/person-phones/{id}", personPhone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonPhone> personPhoneList = personPhoneRepository.findAll();
        assertThat(personPhoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
