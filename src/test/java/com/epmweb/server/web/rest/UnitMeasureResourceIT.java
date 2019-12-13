package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.UnitMeasure;
import com.epmweb.server.repository.UnitMeasureRepository;
import com.epmweb.server.service.UnitMeasureService;
import com.epmweb.server.service.dto.UnitMeasureDTO;
import com.epmweb.server.service.mapper.UnitMeasureMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.UnitMeasureCriteria;
import com.epmweb.server.service.UnitMeasureQueryService;

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
 * Integration tests for the {@link UnitMeasureResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class UnitMeasureResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    @Autowired
    private UnitMeasureMapper unitMeasureMapper;

    @Autowired
    private UnitMeasureService unitMeasureService;

    @Autowired
    private UnitMeasureQueryService unitMeasureQueryService;

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

    private MockMvc restUnitMeasureMockMvc;

    private UnitMeasure unitMeasure;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitMeasureResource unitMeasureResource = new UnitMeasureResource(unitMeasureService, unitMeasureQueryService);
        this.restUnitMeasureMockMvc = MockMvcBuilders.standaloneSetup(unitMeasureResource)
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
    public static UnitMeasure createEntity(EntityManager em) {
        UnitMeasure unitMeasure = new UnitMeasure()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return unitMeasure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitMeasure createUpdatedEntity(EntityManager em) {
        UnitMeasure unitMeasure = new UnitMeasure()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return unitMeasure;
    }

    @BeforeEach
    public void initTest() {
        unitMeasure = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitMeasure() throws Exception {
        int databaseSizeBeforeCreate = unitMeasureRepository.findAll().size();

        // Create the UnitMeasure
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);
        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isCreated());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        UnitMeasure testUnitMeasure = unitMeasureList.get(unitMeasureList.size() - 1);
        assertThat(testUnitMeasure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUnitMeasure.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUnitMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitMeasureRepository.findAll().size();

        // Create the UnitMeasure with an existing ID
        unitMeasure.setId(1L);
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitMeasureRepository.findAll().size();
        // set the field null
        unitMeasure.setCode(null);

        // Create the UnitMeasure, which fails.
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitMeasureRepository.findAll().size();
        // set the field null
        unitMeasure.setName(null);

        // Create the UnitMeasure, which fails.
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnitMeasures() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList
        restUnitMeasureMockMvc.perform(get("/api/unit-measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getUnitMeasure() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get the unitMeasure
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/{id}", unitMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unitMeasure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getUnitMeasuresByIdFiltering() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        Long id = unitMeasure.getId();

        defaultUnitMeasureShouldBeFound("id.equals=" + id);
        defaultUnitMeasureShouldNotBeFound("id.notEquals=" + id);

        defaultUnitMeasureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUnitMeasureShouldNotBeFound("id.greaterThan=" + id);

        defaultUnitMeasureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUnitMeasureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUnitMeasuresByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where code equals to DEFAULT_CODE
        defaultUnitMeasureShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the unitMeasureList where code equals to UPDATED_CODE
        defaultUnitMeasureShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where code not equals to DEFAULT_CODE
        defaultUnitMeasureShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the unitMeasureList where code not equals to UPDATED_CODE
        defaultUnitMeasureShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where code in DEFAULT_CODE or UPDATED_CODE
        defaultUnitMeasureShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the unitMeasureList where code equals to UPDATED_CODE
        defaultUnitMeasureShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where code is not null
        defaultUnitMeasureShouldBeFound("code.specified=true");

        // Get all the unitMeasureList where code is null
        defaultUnitMeasureShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllUnitMeasuresByCodeContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where code contains DEFAULT_CODE
        defaultUnitMeasureShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the unitMeasureList where code contains UPDATED_CODE
        defaultUnitMeasureShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where code does not contain DEFAULT_CODE
        defaultUnitMeasureShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the unitMeasureList where code does not contain UPDATED_CODE
        defaultUnitMeasureShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllUnitMeasuresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where name equals to DEFAULT_NAME
        defaultUnitMeasureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the unitMeasureList where name equals to UPDATED_NAME
        defaultUnitMeasureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where name not equals to DEFAULT_NAME
        defaultUnitMeasureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the unitMeasureList where name not equals to UPDATED_NAME
        defaultUnitMeasureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUnitMeasureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the unitMeasureList where name equals to UPDATED_NAME
        defaultUnitMeasureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where name is not null
        defaultUnitMeasureShouldBeFound("name.specified=true");

        // Get all the unitMeasureList where name is null
        defaultUnitMeasureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUnitMeasuresByNameContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where name contains DEFAULT_NAME
        defaultUnitMeasureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the unitMeasureList where name contains UPDATED_NAME
        defaultUnitMeasureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where name does not contain DEFAULT_NAME
        defaultUnitMeasureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the unitMeasureList where name does not contain UPDATED_NAME
        defaultUnitMeasureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnitMeasureShouldBeFound(String filter) throws Exception {
        restUnitMeasureMockMvc.perform(get("/api/unit-measures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnitMeasureShouldNotBeFound(String filter) throws Exception {
        restUnitMeasureMockMvc.perform(get("/api/unit-measures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUnitMeasure() throws Exception {
        // Get the unitMeasure
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitMeasure() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        int databaseSizeBeforeUpdate = unitMeasureRepository.findAll().size();

        // Update the unitMeasure
        UnitMeasure updatedUnitMeasure = unitMeasureRepository.findById(unitMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedUnitMeasure are not directly saved in db
        em.detach(updatedUnitMeasure);
        updatedUnitMeasure
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(updatedUnitMeasure);

        restUnitMeasureMockMvc.perform(put("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isOk());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeUpdate);
        UnitMeasure testUnitMeasure = unitMeasureList.get(unitMeasureList.size() - 1);
        assertThat(testUnitMeasure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUnitMeasure.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitMeasure() throws Exception {
        int databaseSizeBeforeUpdate = unitMeasureRepository.findAll().size();

        // Create the UnitMeasure
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMeasureMockMvc.perform(put("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitMeasure() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        int databaseSizeBeforeDelete = unitMeasureRepository.findAll().size();

        // Delete the unitMeasure
        restUnitMeasureMockMvc.perform(delete("/api/unit-measures/{id}", unitMeasure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
