package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.BarcodeTypes;
import com.epmweb.server.repository.BarcodeTypesRepository;
import com.epmweb.server.service.BarcodeTypesService;
import com.epmweb.server.service.dto.BarcodeTypesDTO;
import com.epmweb.server.service.mapper.BarcodeTypesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.BarcodeTypesCriteria;
import com.epmweb.server.service.BarcodeTypesQueryService;

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
 * Integration tests for the {@link BarcodeTypesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class BarcodeTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BarcodeTypesRepository barcodeTypesRepository;

    @Autowired
    private BarcodeTypesMapper barcodeTypesMapper;

    @Autowired
    private BarcodeTypesService barcodeTypesService;

    @Autowired
    private BarcodeTypesQueryService barcodeTypesQueryService;

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

    private MockMvc restBarcodeTypesMockMvc;

    private BarcodeTypes barcodeTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BarcodeTypesResource barcodeTypesResource = new BarcodeTypesResource(barcodeTypesService, barcodeTypesQueryService);
        this.restBarcodeTypesMockMvc = MockMvcBuilders.standaloneSetup(barcodeTypesResource)
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
    public static BarcodeTypes createEntity(EntityManager em) {
        BarcodeTypes barcodeTypes = new BarcodeTypes()
            .name(DEFAULT_NAME);
        return barcodeTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BarcodeTypes createUpdatedEntity(EntityManager em) {
        BarcodeTypes barcodeTypes = new BarcodeTypes()
            .name(UPDATED_NAME);
        return barcodeTypes;
    }

    @BeforeEach
    public void initTest() {
        barcodeTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createBarcodeTypes() throws Exception {
        int databaseSizeBeforeCreate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);
        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeCreate + 1);
        BarcodeTypes testBarcodeTypes = barcodeTypesList.get(barcodeTypesList.size() - 1);
        assertThat(testBarcodeTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBarcodeTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes with an existing ID
        barcodeTypes.setId(1L);
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = barcodeTypesRepository.findAll().size();
        // set the field null
        barcodeTypes.setName(null);

        // Create the BarcodeTypes, which fails.
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/{id}", barcodeTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(barcodeTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getBarcodeTypesByIdFiltering() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        Long id = barcodeTypes.getId();

        defaultBarcodeTypesShouldBeFound("id.equals=" + id);
        defaultBarcodeTypesShouldNotBeFound("id.notEquals=" + id);

        defaultBarcodeTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBarcodeTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultBarcodeTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBarcodeTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name equals to DEFAULT_NAME
        defaultBarcodeTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name equals to UPDATED_NAME
        defaultBarcodeTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name not equals to DEFAULT_NAME
        defaultBarcodeTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name not equals to UPDATED_NAME
        defaultBarcodeTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBarcodeTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the barcodeTypesList where name equals to UPDATED_NAME
        defaultBarcodeTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name is not null
        defaultBarcodeTypesShouldBeFound("name.specified=true");

        // Get all the barcodeTypesList where name is null
        defaultBarcodeTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBarcodeTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name contains DEFAULT_NAME
        defaultBarcodeTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name contains UPDATED_NAME
        defaultBarcodeTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where name does not contain DEFAULT_NAME
        defaultBarcodeTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the barcodeTypesList where name does not contain UPDATED_NAME
        defaultBarcodeTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBarcodeTypesShouldBeFound(String filter) throws Exception {
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBarcodeTypesShouldNotBeFound(String filter) throws Exception {
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBarcodeTypes() throws Exception {
        // Get the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        int databaseSizeBeforeUpdate = barcodeTypesRepository.findAll().size();

        // Update the barcodeTypes
        BarcodeTypes updatedBarcodeTypes = barcodeTypesRepository.findById(barcodeTypes.getId()).get();
        // Disconnect from session so that the updates on updatedBarcodeTypes are not directly saved in db
        em.detach(updatedBarcodeTypes);
        updatedBarcodeTypes
            .name(UPDATED_NAME);
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(updatedBarcodeTypes);

        restBarcodeTypesMockMvc.perform(put("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isOk());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeUpdate);
        BarcodeTypes testBarcodeTypes = barcodeTypesList.get(barcodeTypesList.size() - 1);
        assertThat(testBarcodeTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBarcodeTypes() throws Exception {
        int databaseSizeBeforeUpdate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeTypesMockMvc.perform(put("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        int databaseSizeBeforeDelete = barcodeTypesRepository.findAll().size();

        // Delete the barcodeTypes
        restBarcodeTypesMockMvc.perform(delete("/api/barcode-types/{id}", barcodeTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
