package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.AddressTypes;
import com.epmweb.server.repository.AddressTypesRepository;
import com.epmweb.server.service.AddressTypesService;
import com.epmweb.server.service.dto.AddressTypesDTO;
import com.epmweb.server.service.mapper.AddressTypesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.AddressTypesCriteria;
import com.epmweb.server.service.AddressTypesQueryService;

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
 * Integration tests for the {@link AddressTypesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class AddressTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFER = "AAAAAAAAAA";
    private static final String UPDATED_REFER = "BBBBBBBBBB";

    @Autowired
    private AddressTypesRepository addressTypesRepository;

    @Autowired
    private AddressTypesMapper addressTypesMapper;

    @Autowired
    private AddressTypesService addressTypesService;

    @Autowired
    private AddressTypesQueryService addressTypesQueryService;

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

    private MockMvc restAddressTypesMockMvc;

    private AddressTypes addressTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressTypesResource addressTypesResource = new AddressTypesResource(addressTypesService, addressTypesQueryService);
        this.restAddressTypesMockMvc = MockMvcBuilders.standaloneSetup(addressTypesResource)
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
    public static AddressTypes createEntity(EntityManager em) {
        AddressTypes addressTypes = new AddressTypes()
            .name(DEFAULT_NAME)
            .refer(DEFAULT_REFER);
        return addressTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressTypes createUpdatedEntity(EntityManager em) {
        AddressTypes addressTypes = new AddressTypes()
            .name(UPDATED_NAME)
            .refer(UPDATED_REFER);
        return addressTypes;
    }

    @BeforeEach
    public void initTest() {
        addressTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressTypes() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate + 1);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddressTypes.getRefer()).isEqualTo(DEFAULT_REFER);
    }

    @Test
    @Transactional
    public void createAddressTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes with an existing ID
        addressTypes.setId(1L);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressTypesRepository.findAll().size();
        // set the field null
        addressTypes.setName(null);

        // Create the AddressTypes, which fails.
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].refer").value(hasItem(DEFAULT_REFER)));
    }
    
    @Test
    @Transactional
    public void getAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", addressTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.refer").value(DEFAULT_REFER));
    }


    @Test
    @Transactional
    public void getAddressTypesByIdFiltering() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        Long id = addressTypes.getId();

        defaultAddressTypesShouldBeFound("id.equals=" + id);
        defaultAddressTypesShouldNotBeFound("id.notEquals=" + id);

        defaultAddressTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAddressTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where name equals to DEFAULT_NAME
        defaultAddressTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the addressTypesList where name equals to UPDATED_NAME
        defaultAddressTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where name not equals to DEFAULT_NAME
        defaultAddressTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the addressTypesList where name not equals to UPDATED_NAME
        defaultAddressTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAddressTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the addressTypesList where name equals to UPDATED_NAME
        defaultAddressTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where name is not null
        defaultAddressTypesShouldBeFound("name.specified=true");

        // Get all the addressTypesList where name is null
        defaultAddressTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where name contains DEFAULT_NAME
        defaultAddressTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the addressTypesList where name contains UPDATED_NAME
        defaultAddressTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where name does not contain DEFAULT_NAME
        defaultAddressTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the addressTypesList where name does not contain UPDATED_NAME
        defaultAddressTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAddressTypesByReferIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer equals to DEFAULT_REFER
        defaultAddressTypesShouldBeFound("refer.equals=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer equals to UPDATED_REFER
        defaultAddressTypesShouldNotBeFound("refer.equals=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer not equals to DEFAULT_REFER
        defaultAddressTypesShouldNotBeFound("refer.notEquals=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer not equals to UPDATED_REFER
        defaultAddressTypesShouldBeFound("refer.notEquals=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer in DEFAULT_REFER or UPDATED_REFER
        defaultAddressTypesShouldBeFound("refer.in=" + DEFAULT_REFER + "," + UPDATED_REFER);

        // Get all the addressTypesList where refer equals to UPDATED_REFER
        defaultAddressTypesShouldNotBeFound("refer.in=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer is not null
        defaultAddressTypesShouldBeFound("refer.specified=true");

        // Get all the addressTypesList where refer is null
        defaultAddressTypesShouldNotBeFound("refer.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressTypesByReferContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer contains DEFAULT_REFER
        defaultAddressTypesShouldBeFound("refer.contains=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer contains UPDATED_REFER
        defaultAddressTypesShouldNotBeFound("refer.contains=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferNotContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer does not contain DEFAULT_REFER
        defaultAddressTypesShouldNotBeFound("refer.doesNotContain=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer does not contain UPDATED_REFER
        defaultAddressTypesShouldBeFound("refer.doesNotContain=" + UPDATED_REFER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressTypesShouldBeFound(String filter) throws Exception {
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].refer").value(hasItem(DEFAULT_REFER)));

        // Check, that the count call also returns 1
        restAddressTypesMockMvc.perform(get("/api/address-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressTypesShouldNotBeFound(String filter) throws Exception {
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressTypesMockMvc.perform(get("/api/address-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAddressTypes() throws Exception {
        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Update the addressTypes
        AddressTypes updatedAddressTypes = addressTypesRepository.findById(addressTypes.getId()).get();
        // Disconnect from session so that the updates on updatedAddressTypes are not directly saved in db
        em.detach(updatedAddressTypes);
        updatedAddressTypes
            .name(UPDATED_NAME)
            .refer(UPDATED_REFER);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(updatedAddressTypes);

        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isOk());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddressTypes.getRefer()).isEqualTo(UPDATED_REFER);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressTypes() throws Exception {
        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        int databaseSizeBeforeDelete = addressTypesRepository.findAll().size();

        // Delete the addressTypes
        restAddressTypesMockMvc.perform(delete("/api/address-types/{id}", addressTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
