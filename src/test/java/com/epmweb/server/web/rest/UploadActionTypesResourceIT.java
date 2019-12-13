package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.UploadActionTypes;
import com.epmweb.server.repository.UploadActionTypesRepository;
import com.epmweb.server.service.UploadActionTypesService;
import com.epmweb.server.service.dto.UploadActionTypesDTO;
import com.epmweb.server.service.mapper.UploadActionTypesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.UploadActionTypesCriteria;
import com.epmweb.server.service.UploadActionTypesQueryService;

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
 * Integration tests for the {@link UploadActionTypesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class UploadActionTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private UploadActionTypesRepository uploadActionTypesRepository;

    @Autowired
    private UploadActionTypesMapper uploadActionTypesMapper;

    @Autowired
    private UploadActionTypesService uploadActionTypesService;

    @Autowired
    private UploadActionTypesQueryService uploadActionTypesQueryService;

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

    private MockMvc restUploadActionTypesMockMvc;

    private UploadActionTypes uploadActionTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadActionTypesResource uploadActionTypesResource = new UploadActionTypesResource(uploadActionTypesService, uploadActionTypesQueryService);
        this.restUploadActionTypesMockMvc = MockMvcBuilders.standaloneSetup(uploadActionTypesResource)
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
    public static UploadActionTypes createEntity(EntityManager em) {
        UploadActionTypes uploadActionTypes = new UploadActionTypes()
            .name(DEFAULT_NAME);
        return uploadActionTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadActionTypes createUpdatedEntity(EntityManager em) {
        UploadActionTypes uploadActionTypes = new UploadActionTypes()
            .name(UPDATED_NAME);
        return uploadActionTypes;
    }

    @BeforeEach
    public void initTest() {
        uploadActionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadActionTypes() throws Exception {
        int databaseSizeBeforeCreate = uploadActionTypesRepository.findAll().size();

        // Create the UploadActionTypes
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(uploadActionTypes);
        restUploadActionTypesMockMvc.perform(post("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        UploadActionTypes testUploadActionTypes = uploadActionTypesList.get(uploadActionTypesList.size() - 1);
        assertThat(testUploadActionTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUploadActionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadActionTypesRepository.findAll().size();

        // Create the UploadActionTypes with an existing ID
        uploadActionTypes.setId(1L);
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(uploadActionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadActionTypesMockMvc.perform(post("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadActionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get the uploadActionTypes
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types/{id}", uploadActionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadActionTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getUploadActionTypesByIdFiltering() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        Long id = uploadActionTypes.getId();

        defaultUploadActionTypesShouldBeFound("id.equals=" + id);
        defaultUploadActionTypesShouldNotBeFound("id.notEquals=" + id);

        defaultUploadActionTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUploadActionTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultUploadActionTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUploadActionTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUploadActionTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList where name equals to DEFAULT_NAME
        defaultUploadActionTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uploadActionTypesList where name equals to UPDATED_NAME
        defaultUploadActionTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadActionTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList where name not equals to DEFAULT_NAME
        defaultUploadActionTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the uploadActionTypesList where name not equals to UPDATED_NAME
        defaultUploadActionTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadActionTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUploadActionTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uploadActionTypesList where name equals to UPDATED_NAME
        defaultUploadActionTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadActionTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList where name is not null
        defaultUploadActionTypesShouldBeFound("name.specified=true");

        // Get all the uploadActionTypesList where name is null
        defaultUploadActionTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadActionTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList where name contains DEFAULT_NAME
        defaultUploadActionTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the uploadActionTypesList where name contains UPDATED_NAME
        defaultUploadActionTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadActionTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        // Get all the uploadActionTypesList where name does not contain DEFAULT_NAME
        defaultUploadActionTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the uploadActionTypesList where name does not contain UPDATED_NAME
        defaultUploadActionTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUploadActionTypesShouldBeFound(String filter) throws Exception {
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadActionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUploadActionTypesShouldNotBeFound(String filter) throws Exception {
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUploadActionTypes() throws Exception {
        // Get the uploadActionTypes
        restUploadActionTypesMockMvc.perform(get("/api/upload-action-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        int databaseSizeBeforeUpdate = uploadActionTypesRepository.findAll().size();

        // Update the uploadActionTypes
        UploadActionTypes updatedUploadActionTypes = uploadActionTypesRepository.findById(uploadActionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedUploadActionTypes are not directly saved in db
        em.detach(updatedUploadActionTypes);
        updatedUploadActionTypes
            .name(UPDATED_NAME);
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(updatedUploadActionTypes);

        restUploadActionTypesMockMvc.perform(put("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeUpdate);
        UploadActionTypes testUploadActionTypes = uploadActionTypesList.get(uploadActionTypesList.size() - 1);
        assertThat(testUploadActionTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadActionTypes() throws Exception {
        int databaseSizeBeforeUpdate = uploadActionTypesRepository.findAll().size();

        // Create the UploadActionTypes
        UploadActionTypesDTO uploadActionTypesDTO = uploadActionTypesMapper.toDto(uploadActionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadActionTypesMockMvc.perform(put("/api/upload-action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadActionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadActionTypes in the database
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadActionTypes() throws Exception {
        // Initialize the database
        uploadActionTypesRepository.saveAndFlush(uploadActionTypes);

        int databaseSizeBeforeDelete = uploadActionTypesRepository.findAll().size();

        // Delete the uploadActionTypes
        restUploadActionTypesMockMvc.perform(delete("/api/upload-action-types/{id}", uploadActionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UploadActionTypes> uploadActionTypesList = uploadActionTypesRepository.findAll();
        assertThat(uploadActionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
