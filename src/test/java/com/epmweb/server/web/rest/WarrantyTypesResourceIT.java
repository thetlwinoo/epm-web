package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.WarrantyTypes;
import com.epmweb.server.repository.WarrantyTypesRepository;
import com.epmweb.server.service.WarrantyTypesService;
import com.epmweb.server.service.dto.WarrantyTypesDTO;
import com.epmweb.server.service.mapper.WarrantyTypesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.WarrantyTypesCriteria;
import com.epmweb.server.service.WarrantyTypesQueryService;

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
 * Integration tests for the {@link WarrantyTypesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class WarrantyTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private WarrantyTypesRepository warrantyTypesRepository;

    @Autowired
    private WarrantyTypesMapper warrantyTypesMapper;

    @Autowired
    private WarrantyTypesService warrantyTypesService;

    @Autowired
    private WarrantyTypesQueryService warrantyTypesQueryService;

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

    private MockMvc restWarrantyTypesMockMvc;

    private WarrantyTypes warrantyTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarrantyTypesResource warrantyTypesResource = new WarrantyTypesResource(warrantyTypesService, warrantyTypesQueryService);
        this.restWarrantyTypesMockMvc = MockMvcBuilders.standaloneSetup(warrantyTypesResource)
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
    public static WarrantyTypes createEntity(EntityManager em) {
        WarrantyTypes warrantyTypes = new WarrantyTypes()
            .name(DEFAULT_NAME);
        return warrantyTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyTypes createUpdatedEntity(EntityManager em) {
        WarrantyTypes warrantyTypes = new WarrantyTypes()
            .name(UPDATED_NAME);
        return warrantyTypes;
    }

    @BeforeEach
    public void initTest() {
        warrantyTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarrantyTypes() throws Exception {
        int databaseSizeBeforeCreate = warrantyTypesRepository.findAll().size();

        // Create the WarrantyTypes
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);
        restWarrantyTypesMockMvc.perform(post("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeCreate + 1);
        WarrantyTypes testWarrantyTypes = warrantyTypesList.get(warrantyTypesList.size() - 1);
        assertThat(testWarrantyTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWarrantyTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warrantyTypesRepository.findAll().size();

        // Create the WarrantyTypes with an existing ID
        warrantyTypes.setId(1L);
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyTypesMockMvc.perform(post("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyTypesRepository.findAll().size();
        // set the field null
        warrantyTypes.setName(null);

        // Create the WarrantyTypes, which fails.
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);

        restWarrantyTypesMockMvc.perform(post("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isBadRequest());

        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get the warrantyTypes
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/{id}", warrantyTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warrantyTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getWarrantyTypesByIdFiltering() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        Long id = warrantyTypes.getId();

        defaultWarrantyTypesShouldBeFound("id.equals=" + id);
        defaultWarrantyTypesShouldNotBeFound("id.notEquals=" + id);

        defaultWarrantyTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWarrantyTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultWarrantyTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWarrantyTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWarrantyTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where name equals to DEFAULT_NAME
        defaultWarrantyTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the warrantyTypesList where name equals to UPDATED_NAME
        defaultWarrantyTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where name not equals to DEFAULT_NAME
        defaultWarrantyTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the warrantyTypesList where name not equals to UPDATED_NAME
        defaultWarrantyTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWarrantyTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the warrantyTypesList where name equals to UPDATED_NAME
        defaultWarrantyTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where name is not null
        defaultWarrantyTypesShouldBeFound("name.specified=true");

        // Get all the warrantyTypesList where name is null
        defaultWarrantyTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllWarrantyTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where name contains DEFAULT_NAME
        defaultWarrantyTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the warrantyTypesList where name contains UPDATED_NAME
        defaultWarrantyTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where name does not contain DEFAULT_NAME
        defaultWarrantyTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the warrantyTypesList where name does not contain UPDATED_NAME
        defaultWarrantyTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWarrantyTypesShouldBeFound(String filter) throws Exception {
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWarrantyTypesShouldNotBeFound(String filter) throws Exception {
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWarrantyTypes() throws Exception {
        // Get the warrantyTypes
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        int databaseSizeBeforeUpdate = warrantyTypesRepository.findAll().size();

        // Update the warrantyTypes
        WarrantyTypes updatedWarrantyTypes = warrantyTypesRepository.findById(warrantyTypes.getId()).get();
        // Disconnect from session so that the updates on updatedWarrantyTypes are not directly saved in db
        em.detach(updatedWarrantyTypes);
        updatedWarrantyTypes
            .name(UPDATED_NAME);
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(updatedWarrantyTypes);

        restWarrantyTypesMockMvc.perform(put("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isOk());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeUpdate);
        WarrantyTypes testWarrantyTypes = warrantyTypesList.get(warrantyTypesList.size() - 1);
        assertThat(testWarrantyTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWarrantyTypes() throws Exception {
        int databaseSizeBeforeUpdate = warrantyTypesRepository.findAll().size();

        // Create the WarrantyTypes
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyTypesMockMvc.perform(put("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        int databaseSizeBeforeDelete = warrantyTypesRepository.findAll().size();

        // Delete the warrantyTypes
        restWarrantyTypesMockMvc.perform(delete("/api/warranty-types/{id}", warrantyTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
