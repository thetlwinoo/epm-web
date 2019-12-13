package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Culture;
import com.epmweb.server.repository.CultureRepository;
import com.epmweb.server.service.CultureService;
import com.epmweb.server.service.dto.CultureDTO;
import com.epmweb.server.service.mapper.CultureMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.CultureCriteria;
import com.epmweb.server.service.CultureQueryService;

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
 * Integration tests for the {@link CultureResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class CultureResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private CultureMapper cultureMapper;

    @Autowired
    private CultureService cultureService;

    @Autowired
    private CultureQueryService cultureQueryService;

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

    private MockMvc restCultureMockMvc;

    private Culture culture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CultureResource cultureResource = new CultureResource(cultureService, cultureQueryService);
        this.restCultureMockMvc = MockMvcBuilders.standaloneSetup(cultureResource)
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
    public static Culture createEntity(EntityManager em) {
        Culture culture = new Culture()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return culture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Culture createUpdatedEntity(EntityManager em) {
        Culture culture = new Culture()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        return culture;
    }

    @BeforeEach
    public void initTest() {
        culture = createEntity(em);
    }

    @Test
    @Transactional
    public void createCulture() throws Exception {
        int databaseSizeBeforeCreate = cultureRepository.findAll().size();

        // Create the Culture
        CultureDTO cultureDTO = cultureMapper.toDto(culture);
        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isCreated());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeCreate + 1);
        Culture testCulture = cultureList.get(cultureList.size() - 1);
        assertThat(testCulture.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCulture.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cultureRepository.findAll().size();

        // Create the Culture with an existing ID
        culture.setId(1L);
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cultureRepository.findAll().size();
        // set the field null
        culture.setCode(null);

        // Create the Culture, which fails.
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cultureRepository.findAll().size();
        // set the field null
        culture.setName(null);

        // Create the Culture, which fails.
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCultures() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(culture.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get the culture
        restCultureMockMvc.perform(get("/api/cultures/{id}", culture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(culture.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCulturesByIdFiltering() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        Long id = culture.getId();

        defaultCultureShouldBeFound("id.equals=" + id);
        defaultCultureShouldNotBeFound("id.notEquals=" + id);

        defaultCultureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCultureShouldNotBeFound("id.greaterThan=" + id);

        defaultCultureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCultureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCulturesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where code equals to DEFAULT_CODE
        defaultCultureShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cultureList where code equals to UPDATED_CODE
        defaultCultureShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where code not equals to DEFAULT_CODE
        defaultCultureShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cultureList where code not equals to UPDATED_CODE
        defaultCultureShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCultureShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cultureList where code equals to UPDATED_CODE
        defaultCultureShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where code is not null
        defaultCultureShouldBeFound("code.specified=true");

        // Get all the cultureList where code is null
        defaultCultureShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCulturesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where code contains DEFAULT_CODE
        defaultCultureShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cultureList where code contains UPDATED_CODE
        defaultCultureShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where code does not contain DEFAULT_CODE
        defaultCultureShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cultureList where code does not contain UPDATED_CODE
        defaultCultureShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCulturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where name equals to DEFAULT_NAME
        defaultCultureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cultureList where name equals to UPDATED_NAME
        defaultCultureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where name not equals to DEFAULT_NAME
        defaultCultureShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cultureList where name not equals to UPDATED_NAME
        defaultCultureShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCultureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cultureList where name equals to UPDATED_NAME
        defaultCultureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where name is not null
        defaultCultureShouldBeFound("name.specified=true");

        // Get all the cultureList where name is null
        defaultCultureShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCulturesByNameContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where name contains DEFAULT_NAME
        defaultCultureShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cultureList where name contains UPDATED_NAME
        defaultCultureShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where name does not contain DEFAULT_NAME
        defaultCultureShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cultureList where name does not contain UPDATED_NAME
        defaultCultureShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCultureShouldBeFound(String filter) throws Exception {
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(culture.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCultureMockMvc.perform(get("/api/cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCultureShouldNotBeFound(String filter) throws Exception {
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCultureMockMvc.perform(get("/api/cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCulture() throws Exception {
        // Get the culture
        restCultureMockMvc.perform(get("/api/cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        int databaseSizeBeforeUpdate = cultureRepository.findAll().size();

        // Update the culture
        Culture updatedCulture = cultureRepository.findById(culture.getId()).get();
        // Disconnect from session so that the updates on updatedCulture are not directly saved in db
        em.detach(updatedCulture);
        updatedCulture
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        CultureDTO cultureDTO = cultureMapper.toDto(updatedCulture);

        restCultureMockMvc.perform(put("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isOk());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeUpdate);
        Culture testCulture = cultureList.get(cultureList.size() - 1);
        assertThat(testCulture.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCulture.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCulture() throws Exception {
        int databaseSizeBeforeUpdate = cultureRepository.findAll().size();

        // Create the Culture
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultureMockMvc.perform(put("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        int databaseSizeBeforeDelete = cultureRepository.findAll().size();

        // Delete the culture
        restCultureMockMvc.perform(delete("/api/cultures/{id}", culture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
