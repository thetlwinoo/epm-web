package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Materials;
import com.epmweb.server.repository.MaterialsRepository;
import com.epmweb.server.service.MaterialsService;
import com.epmweb.server.service.dto.MaterialsDTO;
import com.epmweb.server.service.mapper.MaterialsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.MaterialsCriteria;
import com.epmweb.server.service.MaterialsQueryService;

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
 * Integration tests for the {@link MaterialsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class MaterialsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MaterialsRepository materialsRepository;

    @Autowired
    private MaterialsMapper materialsMapper;

    @Autowired
    private MaterialsService materialsService;

    @Autowired
    private MaterialsQueryService materialsQueryService;

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

    private MockMvc restMaterialsMockMvc;

    private Materials materials;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialsResource materialsResource = new MaterialsResource(materialsService, materialsQueryService);
        this.restMaterialsMockMvc = MockMvcBuilders.standaloneSetup(materialsResource)
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
    public static Materials createEntity(EntityManager em) {
        Materials materials = new Materials()
            .name(DEFAULT_NAME);
        return materials;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materials createUpdatedEntity(EntityManager em) {
        Materials materials = new Materials()
            .name(UPDATED_NAME);
        return materials;
    }

    @BeforeEach
    public void initTest() {
        materials = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterials() throws Exception {
        int databaseSizeBeforeCreate = materialsRepository.findAll().size();

        // Create the Materials
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);
        restMaterialsMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isCreated());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeCreate + 1);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMaterialsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialsRepository.findAll().size();

        // Create the Materials with an existing ID
        materials.setId(1L);
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialsMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialsRepository.findAll().size();
        // set the field null
        materials.setName(null);

        // Create the Materials, which fails.
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);

        restMaterialsMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isBadRequest());

        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList
        restMaterialsMockMvc.perform(get("/api/materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materials.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get the materials
        restMaterialsMockMvc.perform(get("/api/materials/{id}", materials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materials.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getMaterialsByIdFiltering() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        Long id = materials.getId();

        defaultMaterialsShouldBeFound("id.equals=" + id);
        defaultMaterialsShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialsShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMaterialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where name equals to DEFAULT_NAME
        defaultMaterialsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the materialsList where name equals to UPDATED_NAME
        defaultMaterialsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where name not equals to DEFAULT_NAME
        defaultMaterialsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the materialsList where name not equals to UPDATED_NAME
        defaultMaterialsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMaterialsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the materialsList where name equals to UPDATED_NAME
        defaultMaterialsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where name is not null
        defaultMaterialsShouldBeFound("name.specified=true");

        // Get all the materialsList where name is null
        defaultMaterialsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMaterialsByNameContainsSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where name contains DEFAULT_NAME
        defaultMaterialsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the materialsList where name contains UPDATED_NAME
        defaultMaterialsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where name does not contain DEFAULT_NAME
        defaultMaterialsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the materialsList where name does not contain UPDATED_NAME
        defaultMaterialsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialsShouldBeFound(String filter) throws Exception {
        restMaterialsMockMvc.perform(get("/api/materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materials.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMaterialsMockMvc.perform(get("/api/materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialsShouldNotBeFound(String filter) throws Exception {
        restMaterialsMockMvc.perform(get("/api/materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialsMockMvc.perform(get("/api/materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMaterials() throws Exception {
        // Get the materials
        restMaterialsMockMvc.perform(get("/api/materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Update the materials
        Materials updatedMaterials = materialsRepository.findById(materials.getId()).get();
        // Disconnect from session so that the updates on updatedMaterials are not directly saved in db
        em.detach(updatedMaterials);
        updatedMaterials
            .name(UPDATED_NAME);
        MaterialsDTO materialsDTO = materialsMapper.toDto(updatedMaterials);

        restMaterialsMockMvc.perform(put("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isOk());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Create the Materials
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialsMockMvc.perform(put("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeDelete = materialsRepository.findAll().size();

        // Delete the materials
        restMaterialsMockMvc.perform(delete("/api/materials/{id}", materials.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
