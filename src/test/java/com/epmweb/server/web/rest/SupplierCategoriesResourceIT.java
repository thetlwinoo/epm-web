package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.SupplierCategories;
import com.epmweb.server.repository.SupplierCategoriesRepository;
import com.epmweb.server.service.SupplierCategoriesService;
import com.epmweb.server.service.dto.SupplierCategoriesDTO;
import com.epmweb.server.service.mapper.SupplierCategoriesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.SupplierCategoriesCriteria;
import com.epmweb.server.service.SupplierCategoriesQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupplierCategoriesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class SupplierCategoriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplierCategoriesRepository supplierCategoriesRepository;

    @Autowired
    private SupplierCategoriesMapper supplierCategoriesMapper;

    @Autowired
    private SupplierCategoriesService supplierCategoriesService;

    @Autowired
    private SupplierCategoriesQueryService supplierCategoriesQueryService;

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

    private MockMvc restSupplierCategoriesMockMvc;

    private SupplierCategories supplierCategories;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierCategoriesResource supplierCategoriesResource = new SupplierCategoriesResource(supplierCategoriesService, supplierCategoriesQueryService);
        this.restSupplierCategoriesMockMvc = MockMvcBuilders.standaloneSetup(supplierCategoriesResource)
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
    public static SupplierCategories createEntity(EntityManager em) {
        SupplierCategories supplierCategories = new SupplierCategories()
            .name(DEFAULT_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return supplierCategories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierCategories createUpdatedEntity(EntityManager em) {
        SupplierCategories supplierCategories = new SupplierCategories()
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return supplierCategories;
    }

    @BeforeEach
    public void initTest() {
        supplierCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierCategories() throws Exception {
        int databaseSizeBeforeCreate = supplierCategoriesRepository.findAll().size();

        // Create the SupplierCategories
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);
        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierCategories testSupplierCategories = supplierCategoriesList.get(supplierCategoriesList.size() - 1);
        assertThat(testSupplierCategories.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplierCategories.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSupplierCategories.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createSupplierCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierCategoriesRepository.findAll().size();

        // Create the SupplierCategories with an existing ID
        supplierCategories.setId(1L);
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoriesRepository.findAll().size();
        // set the field null
        supplierCategories.setName(null);

        // Create the SupplierCategories, which fails.
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoriesRepository.findAll().size();
        // set the field null
        supplierCategories.setValidFrom(null);

        // Create the SupplierCategories, which fails.
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoriesRepository.findAll().size();
        // set the field null
        supplierCategories.setValidTo(null);

        // Create the SupplierCategories, which fails.
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get the supplierCategories
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/{id}", supplierCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierCategories.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getSupplierCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        Long id = supplierCategories.getId();

        defaultSupplierCategoriesShouldBeFound("id.equals=" + id);
        defaultSupplierCategoriesShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierCategoriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierCategoriesShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierCategoriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierCategoriesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupplierCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where name equals to DEFAULT_NAME
        defaultSupplierCategoriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplierCategoriesList where name equals to UPDATED_NAME
        defaultSupplierCategoriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where name not equals to DEFAULT_NAME
        defaultSupplierCategoriesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the supplierCategoriesList where name not equals to UPDATED_NAME
        defaultSupplierCategoriesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplierCategoriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplierCategoriesList where name equals to UPDATED_NAME
        defaultSupplierCategoriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where name is not null
        defaultSupplierCategoriesShouldBeFound("name.specified=true");

        // Get all the supplierCategoriesList where name is null
        defaultSupplierCategoriesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where name contains DEFAULT_NAME
        defaultSupplierCategoriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the supplierCategoriesList where name contains UPDATED_NAME
        defaultSupplierCategoriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where name does not contain DEFAULT_NAME
        defaultSupplierCategoriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the supplierCategoriesList where name does not contain UPDATED_NAME
        defaultSupplierCategoriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom equals to DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom not equals to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom is not null
        defaultSupplierCategoriesShouldBeFound("validFrom.specified=true");

        // Get all the supplierCategoriesList where validFrom is null
        defaultSupplierCategoriesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo equals to DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo not equals to DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo not equals to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the supplierCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo is not null
        defaultSupplierCategoriesShouldBeFound("validTo.specified=true");

        // Get all the supplierCategoriesList where validTo is null
        defaultSupplierCategoriesShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierCategoriesShouldBeFound(String filter) throws Exception {
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierCategoriesShouldNotBeFound(String filter) throws Exception {
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplierCategories() throws Exception {
        // Get the supplierCategories
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        int databaseSizeBeforeUpdate = supplierCategoriesRepository.findAll().size();

        // Update the supplierCategories
        SupplierCategories updatedSupplierCategories = supplierCategoriesRepository.findById(supplierCategories.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierCategories are not directly saved in db
        em.detach(updatedSupplierCategories);
        updatedSupplierCategories
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(updatedSupplierCategories);

        restSupplierCategoriesMockMvc.perform(put("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeUpdate);
        SupplierCategories testSupplierCategories = supplierCategoriesList.get(supplierCategoriesList.size() - 1);
        assertThat(testSupplierCategories.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplierCategories.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSupplierCategories.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierCategories() throws Exception {
        int databaseSizeBeforeUpdate = supplierCategoriesRepository.findAll().size();

        // Create the SupplierCategories
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierCategoriesMockMvc.perform(put("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        int databaseSizeBeforeDelete = supplierCategoriesRepository.findAll().size();

        // Delete the supplierCategories
        restSupplierCategoriesMockMvc.perform(delete("/api/supplier-categories/{id}", supplierCategories.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
