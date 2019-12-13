package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.BusinessEntityContact;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.ContactType;
import com.epmweb.server.repository.BusinessEntityContactRepository;
import com.epmweb.server.service.BusinessEntityContactService;
import com.epmweb.server.service.dto.BusinessEntityContactDTO;
import com.epmweb.server.service.mapper.BusinessEntityContactMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.BusinessEntityContactCriteria;
import com.epmweb.server.service.BusinessEntityContactQueryService;

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
 * Integration tests for the {@link BusinessEntityContactResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class BusinessEntityContactResourceIT {

    @Autowired
    private BusinessEntityContactRepository businessEntityContactRepository;

    @Autowired
    private BusinessEntityContactMapper businessEntityContactMapper;

    @Autowired
    private BusinessEntityContactService businessEntityContactService;

    @Autowired
    private BusinessEntityContactQueryService businessEntityContactQueryService;

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

    private MockMvc restBusinessEntityContactMockMvc;

    private BusinessEntityContact businessEntityContact;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessEntityContactResource businessEntityContactResource = new BusinessEntityContactResource(businessEntityContactService, businessEntityContactQueryService);
        this.restBusinessEntityContactMockMvc = MockMvcBuilders.standaloneSetup(businessEntityContactResource)
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
    public static BusinessEntityContact createEntity(EntityManager em) {
        BusinessEntityContact businessEntityContact = new BusinessEntityContact();
        return businessEntityContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessEntityContact createUpdatedEntity(EntityManager em) {
        BusinessEntityContact businessEntityContact = new BusinessEntityContact();
        return businessEntityContact;
    }

    @BeforeEach
    public void initTest() {
        businessEntityContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessEntityContact() throws Exception {
        int databaseSizeBeforeCreate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);
        restBusinessEntityContactMockMvc.perform(post("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessEntityContact testBusinessEntityContact = businessEntityContactList.get(businessEntityContactList.size() - 1);
    }

    @Test
    @Transactional
    public void createBusinessEntityContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact with an existing ID
        businessEntityContact.setId(1L);
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessEntityContactMockMvc.perform(post("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBusinessEntityContacts() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        // Get all the businessEntityContactList
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityContact.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        // Get the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/{id}", businessEntityContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessEntityContact.getId().intValue()));
    }


    @Test
    @Transactional
    public void getBusinessEntityContactsByIdFiltering() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        Long id = businessEntityContact.getId();

        defaultBusinessEntityContactShouldBeFound("id.equals=" + id);
        defaultBusinessEntityContactShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessEntityContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessEntityContactShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessEntityContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessEntityContactShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBusinessEntityContactsByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        businessEntityContact.setPerson(person);
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        Long personId = person.getId();

        // Get all the businessEntityContactList where person equals to personId
        defaultBusinessEntityContactShouldBeFound("personId.equals=" + personId);

        // Get all the businessEntityContactList where person equals to personId + 1
        defaultBusinessEntityContactShouldNotBeFound("personId.equals=" + (personId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessEntityContactsByContactTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        ContactType contactType = ContactTypeResourceIT.createEntity(em);
        em.persist(contactType);
        em.flush();
        businessEntityContact.setContactType(contactType);
        businessEntityContactRepository.saveAndFlush(businessEntityContact);
        Long contactTypeId = contactType.getId();

        // Get all the businessEntityContactList where contactType equals to contactTypeId
        defaultBusinessEntityContactShouldBeFound("contactTypeId.equals=" + contactTypeId);

        // Get all the businessEntityContactList where contactType equals to contactTypeId + 1
        defaultBusinessEntityContactShouldNotBeFound("contactTypeId.equals=" + (contactTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessEntityContactShouldBeFound(String filter) throws Exception {
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessEntityContact.getId().intValue())));

        // Check, that the count call also returns 1
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessEntityContactShouldNotBeFound(String filter) throws Exception {
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBusinessEntityContact() throws Exception {
        // Get the businessEntityContact
        restBusinessEntityContactMockMvc.perform(get("/api/business-entity-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        int databaseSizeBeforeUpdate = businessEntityContactRepository.findAll().size();

        // Update the businessEntityContact
        BusinessEntityContact updatedBusinessEntityContact = businessEntityContactRepository.findById(businessEntityContact.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessEntityContact are not directly saved in db
        em.detach(updatedBusinessEntityContact);
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(updatedBusinessEntityContact);

        restBusinessEntityContactMockMvc.perform(put("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessEntityContact testBusinessEntityContact = businessEntityContactList.get(businessEntityContactList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessEntityContact() throws Exception {
        int databaseSizeBeforeUpdate = businessEntityContactRepository.findAll().size();

        // Create the BusinessEntityContact
        BusinessEntityContactDTO businessEntityContactDTO = businessEntityContactMapper.toDto(businessEntityContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessEntityContactMockMvc.perform(put("/api/business-entity-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessEntityContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessEntityContact in the database
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessEntityContact() throws Exception {
        // Initialize the database
        businessEntityContactRepository.saveAndFlush(businessEntityContact);

        int databaseSizeBeforeDelete = businessEntityContactRepository.findAll().size();

        // Delete the businessEntityContact
        restBusinessEntityContactMockMvc.perform(delete("/api/business-entity-contacts/{id}", businessEntityContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessEntityContact> businessEntityContactList = businessEntityContactRepository.findAll();
        assertThat(businessEntityContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
