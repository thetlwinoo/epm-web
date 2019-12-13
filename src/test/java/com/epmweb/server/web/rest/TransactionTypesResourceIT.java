package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.TransactionTypes;
import com.epmweb.server.repository.TransactionTypesRepository;
import com.epmweb.server.service.TransactionTypesService;
import com.epmweb.server.service.dto.TransactionTypesDTO;
import com.epmweb.server.service.mapper.TransactionTypesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.TransactionTypesCriteria;
import com.epmweb.server.service.TransactionTypesQueryService;

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
 * Integration tests for the {@link TransactionTypesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class TransactionTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    @Autowired
    private TransactionTypesMapper transactionTypesMapper;

    @Autowired
    private TransactionTypesService transactionTypesService;

    @Autowired
    private TransactionTypesQueryService transactionTypesQueryService;

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

    private MockMvc restTransactionTypesMockMvc;

    private TransactionTypes transactionTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionTypesResource transactionTypesResource = new TransactionTypesResource(transactionTypesService, transactionTypesQueryService);
        this.restTransactionTypesMockMvc = MockMvcBuilders.standaloneSetup(transactionTypesResource)
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
    public static TransactionTypes createEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .name(DEFAULT_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return transactionTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionTypes createUpdatedEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return transactionTypes;
    }

    @BeforeEach
    public void initTest() {
        transactionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionTypes() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);
        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTransactionTypes.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTransactionTypes.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createTransactionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes with an existing ID
        transactionTypes.setId(1L);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setName(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setValidFrom(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setValidTo(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/{id}", transactionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getTransactionTypesByIdFiltering() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        Long id = transactionTypes.getId();

        defaultTransactionTypesShouldBeFound("id.equals=" + id);
        defaultTransactionTypesShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionTypesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where name equals to DEFAULT_NAME
        defaultTransactionTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the transactionTypesList where name equals to UPDATED_NAME
        defaultTransactionTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where name not equals to DEFAULT_NAME
        defaultTransactionTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the transactionTypesList where name not equals to UPDATED_NAME
        defaultTransactionTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTransactionTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the transactionTypesList where name equals to UPDATED_NAME
        defaultTransactionTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where name is not null
        defaultTransactionTypesShouldBeFound("name.specified=true");

        // Get all the transactionTypesList where name is null
        defaultTransactionTypesShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where name contains DEFAULT_NAME
        defaultTransactionTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the transactionTypesList where name contains UPDATED_NAME
        defaultTransactionTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where name does not contain DEFAULT_NAME
        defaultTransactionTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the transactionTypesList where name does not contain UPDATED_NAME
        defaultTransactionTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom equals to DEFAULT_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom not equals to UPDATED_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the transactionTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom is not null
        defaultTransactionTypesShouldBeFound("validFrom.specified=true");

        // Get all the transactionTypesList where validFrom is null
        defaultTransactionTypesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo equals to DEFAULT_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo equals to UPDATED_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo not equals to DEFAULT_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo not equals to UPDATED_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the transactionTypesList where validTo equals to UPDATED_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo is not null
        defaultTransactionTypesShouldBeFound("validTo.specified=true");

        // Get all the transactionTypesList where validTo is null
        defaultTransactionTypesShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionTypesShouldBeFound(String filter) throws Exception {
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionTypesShouldNotBeFound(String filter) throws Exception {
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionTypes() throws Exception {
        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Update the transactionTypes
        TransactionTypes updatedTransactionTypes = transactionTypesRepository.findById(transactionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionTypes are not directly saved in db
        em.detach(updatedTransactionTypes);
        updatedTransactionTypes
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(updatedTransactionTypes);

        restTransactionTypesMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransactionTypes.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTransactionTypes.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeDelete = transactionTypesRepository.findAll().size();

        // Delete the transactionTypes
        restTransactionTypesMockMvc.perform(delete("/api/transaction-types/{id}", transactionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
