package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.UploadTransactions;
import com.epmweb.server.domain.SupplierImportedDocument;
import com.epmweb.server.domain.StockItemTemp;
import com.epmweb.server.domain.Suppliers;
import com.epmweb.server.domain.UploadActionTypes;
import com.epmweb.server.repository.UploadTransactionsRepository;
import com.epmweb.server.service.UploadTransactionsService;
import com.epmweb.server.service.dto.UploadTransactionsDTO;
import com.epmweb.server.service.mapper.UploadTransactionsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.UploadTransactionsCriteria;
import com.epmweb.server.service.UploadTransactionsQueryService;

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
 * Integration tests for the {@link UploadTransactionsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class UploadTransactionsResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_URL = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String DEFAULT_GENERATED_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GENERATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UploadTransactionsRepository uploadTransactionsRepository;

    @Autowired
    private UploadTransactionsMapper uploadTransactionsMapper;

    @Autowired
    private UploadTransactionsService uploadTransactionsService;

    @Autowired
    private UploadTransactionsQueryService uploadTransactionsQueryService;

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

    private MockMvc restUploadTransactionsMockMvc;

    private UploadTransactions uploadTransactions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadTransactionsResource uploadTransactionsResource = new UploadTransactionsResource(uploadTransactionsService, uploadTransactionsQueryService);
        this.restUploadTransactionsMockMvc = MockMvcBuilders.standaloneSetup(uploadTransactionsResource)
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
    public static UploadTransactions createEntity(EntityManager em) {
        UploadTransactions uploadTransactions = new UploadTransactions()
            .fileName(DEFAULT_FILE_NAME)
            .templateUrl(DEFAULT_TEMPLATE_URL)
            .status(DEFAULT_STATUS)
            .generatedCode(DEFAULT_GENERATED_CODE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return uploadTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadTransactions createUpdatedEntity(EntityManager em) {
        UploadTransactions uploadTransactions = new UploadTransactions()
            .fileName(UPDATED_FILE_NAME)
            .templateUrl(UPDATED_TEMPLATE_URL)
            .status(UPDATED_STATUS)
            .generatedCode(UPDATED_GENERATED_CODE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return uploadTransactions;
    }

    @BeforeEach
    public void initTest() {
        uploadTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadTransactions() throws Exception {
        int databaseSizeBeforeCreate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);
        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        UploadTransactions testUploadTransactions = uploadTransactionsList.get(uploadTransactionsList.size() - 1);
        assertThat(testUploadTransactions.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testUploadTransactions.getTemplateUrl()).isEqualTo(DEFAULT_TEMPLATE_URL);
        assertThat(testUploadTransactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUploadTransactions.getGeneratedCode()).isEqualTo(DEFAULT_GENERATED_CODE);
        assertThat(testUploadTransactions.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testUploadTransactions.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createUploadTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions with an existing ID
        uploadTransactions.setId(1L);
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadTransactionsMockMvc.perform(post("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].templateUrl").value(hasItem(DEFAULT_TEMPLATE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].generatedCode").value(hasItem(DEFAULT_GENERATED_CODE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get the uploadTransactions
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/{id}", uploadTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadTransactions.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.templateUrl").value(DEFAULT_TEMPLATE_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.generatedCode").value(DEFAULT_GENERATED_CODE))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getUploadTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        Long id = uploadTransactions.getId();

        defaultUploadTransactionsShouldBeFound("id.equals=" + id);
        defaultUploadTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultUploadTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUploadTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultUploadTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUploadTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName equals to DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName equals to UPDATED_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName not equals to DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName not equals to UPDATED_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the uploadTransactionsList where fileName equals to UPDATED_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName is not null
        defaultUploadTransactionsShouldBeFound("fileName.specified=true");

        // Get all the uploadTransactionsList where fileName is null
        defaultUploadTransactionsShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName contains DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName contains UPDATED_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where fileName does not contain DEFAULT_FILE_NAME
        defaultUploadTransactionsShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the uploadTransactionsList where fileName does not contain UPDATED_FILE_NAME
        defaultUploadTransactionsShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByTemplateUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where templateUrl equals to DEFAULT_TEMPLATE_URL
        defaultUploadTransactionsShouldBeFound("templateUrl.equals=" + DEFAULT_TEMPLATE_URL);

        // Get all the uploadTransactionsList where templateUrl equals to UPDATED_TEMPLATE_URL
        defaultUploadTransactionsShouldNotBeFound("templateUrl.equals=" + UPDATED_TEMPLATE_URL);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByTemplateUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where templateUrl not equals to DEFAULT_TEMPLATE_URL
        defaultUploadTransactionsShouldNotBeFound("templateUrl.notEquals=" + DEFAULT_TEMPLATE_URL);

        // Get all the uploadTransactionsList where templateUrl not equals to UPDATED_TEMPLATE_URL
        defaultUploadTransactionsShouldBeFound("templateUrl.notEquals=" + UPDATED_TEMPLATE_URL);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByTemplateUrlIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where templateUrl in DEFAULT_TEMPLATE_URL or UPDATED_TEMPLATE_URL
        defaultUploadTransactionsShouldBeFound("templateUrl.in=" + DEFAULT_TEMPLATE_URL + "," + UPDATED_TEMPLATE_URL);

        // Get all the uploadTransactionsList where templateUrl equals to UPDATED_TEMPLATE_URL
        defaultUploadTransactionsShouldNotBeFound("templateUrl.in=" + UPDATED_TEMPLATE_URL);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByTemplateUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where templateUrl is not null
        defaultUploadTransactionsShouldBeFound("templateUrl.specified=true");

        // Get all the uploadTransactionsList where templateUrl is null
        defaultUploadTransactionsShouldNotBeFound("templateUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByTemplateUrlContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where templateUrl contains DEFAULT_TEMPLATE_URL
        defaultUploadTransactionsShouldBeFound("templateUrl.contains=" + DEFAULT_TEMPLATE_URL);

        // Get all the uploadTransactionsList where templateUrl contains UPDATED_TEMPLATE_URL
        defaultUploadTransactionsShouldNotBeFound("templateUrl.contains=" + UPDATED_TEMPLATE_URL);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByTemplateUrlNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where templateUrl does not contain DEFAULT_TEMPLATE_URL
        defaultUploadTransactionsShouldNotBeFound("templateUrl.doesNotContain=" + DEFAULT_TEMPLATE_URL);

        // Get all the uploadTransactionsList where templateUrl does not contain UPDATED_TEMPLATE_URL
        defaultUploadTransactionsShouldBeFound("templateUrl.doesNotContain=" + UPDATED_TEMPLATE_URL);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status equals to DEFAULT_STATUS
        defaultUploadTransactionsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status equals to UPDATED_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status not equals to DEFAULT_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status not equals to UPDATED_STATUS
        defaultUploadTransactionsShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUploadTransactionsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the uploadTransactionsList where status equals to UPDATED_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is not null
        defaultUploadTransactionsShouldBeFound("status.specified=true");

        // Get all the uploadTransactionsList where status is null
        defaultUploadTransactionsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is greater than or equal to DEFAULT_STATUS
        defaultUploadTransactionsShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is greater than or equal to UPDATED_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is less than or equal to DEFAULT_STATUS
        defaultUploadTransactionsShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is less than or equal to SMALLER_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is less than DEFAULT_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is less than UPDATED_STATUS
        defaultUploadTransactionsShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where status is greater than DEFAULT_STATUS
        defaultUploadTransactionsShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the uploadTransactionsList where status is greater than SMALLER_STATUS
        defaultUploadTransactionsShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode equals to DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.equals=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode equals to UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.equals=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode not equals to DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.notEquals=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode not equals to UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.notEquals=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode in DEFAULT_GENERATED_CODE or UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.in=" + DEFAULT_GENERATED_CODE + "," + UPDATED_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode equals to UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.in=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode is not null
        defaultUploadTransactionsShouldBeFound("generatedCode.specified=true");

        // Get all the uploadTransactionsList where generatedCode is null
        defaultUploadTransactionsShouldNotBeFound("generatedCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode contains DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.contains=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode contains UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.contains=" + UPDATED_GENERATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByGeneratedCodeNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where generatedCode does not contain DEFAULT_GENERATED_CODE
        defaultUploadTransactionsShouldNotBeFound("generatedCode.doesNotContain=" + DEFAULT_GENERATED_CODE);

        // Get all the uploadTransactionsList where generatedCode does not contain UPDATED_GENERATED_CODE
        defaultUploadTransactionsShouldBeFound("generatedCode.doesNotContain=" + UPDATED_GENERATED_CODE);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy is not null
        defaultUploadTransactionsShouldBeFound("lastEditedBy.specified=true");

        // Get all the uploadTransactionsList where lastEditedBy is null
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultUploadTransactionsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the uploadTransactionsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultUploadTransactionsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the uploadTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the uploadTransactionsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the uploadTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        // Get all the uploadTransactionsList where lastEditedWhen is not null
        defaultUploadTransactionsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the uploadTransactionsList where lastEditedWhen is null
        defaultUploadTransactionsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadTransactionsByImportDocumentListIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        SupplierImportedDocument importDocumentList = SupplierImportedDocumentResourceIT.createEntity(em);
        em.persist(importDocumentList);
        em.flush();
        uploadTransactions.addImportDocumentList(importDocumentList);
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Long importDocumentListId = importDocumentList.getId();

        // Get all the uploadTransactionsList where importDocumentList equals to importDocumentListId
        defaultUploadTransactionsShouldBeFound("importDocumentListId.equals=" + importDocumentListId);

        // Get all the uploadTransactionsList where importDocumentList equals to importDocumentListId + 1
        defaultUploadTransactionsShouldNotBeFound("importDocumentListId.equals=" + (importDocumentListId + 1));
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByStockItemTempListIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        StockItemTemp stockItemTempList = StockItemTempResourceIT.createEntity(em);
        em.persist(stockItemTempList);
        em.flush();
        uploadTransactions.addStockItemTempList(stockItemTempList);
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Long stockItemTempListId = stockItemTempList.getId();

        // Get all the uploadTransactionsList where stockItemTempList equals to stockItemTempListId
        defaultUploadTransactionsShouldBeFound("stockItemTempListId.equals=" + stockItemTempListId);

        // Get all the uploadTransactionsList where stockItemTempList equals to stockItemTempListId + 1
        defaultUploadTransactionsShouldNotBeFound("stockItemTempListId.equals=" + (stockItemTempListId + 1));
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        uploadTransactions.setSupplier(supplier);
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Long supplierId = supplier.getId();

        // Get all the uploadTransactionsList where supplier equals to supplierId
        defaultUploadTransactionsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the uploadTransactionsList where supplier equals to supplierId + 1
        defaultUploadTransactionsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllUploadTransactionsByActionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        UploadActionTypes actionType = UploadActionTypesResourceIT.createEntity(em);
        em.persist(actionType);
        em.flush();
        uploadTransactions.setActionType(actionType);
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);
        Long actionTypeId = actionType.getId();

        // Get all the uploadTransactionsList where actionType equals to actionTypeId
        defaultUploadTransactionsShouldBeFound("actionTypeId.equals=" + actionTypeId);

        // Get all the uploadTransactionsList where actionType equals to actionTypeId + 1
        defaultUploadTransactionsShouldNotBeFound("actionTypeId.equals=" + (actionTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUploadTransactionsShouldBeFound(String filter) throws Exception {
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].templateUrl").value(hasItem(DEFAULT_TEMPLATE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].generatedCode").value(hasItem(DEFAULT_GENERATED_CODE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUploadTransactionsShouldNotBeFound(String filter) throws Exception {
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUploadTransactions() throws Exception {
        // Get the uploadTransactions
        restUploadTransactionsMockMvc.perform(get("/api/upload-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        int databaseSizeBeforeUpdate = uploadTransactionsRepository.findAll().size();

        // Update the uploadTransactions
        UploadTransactions updatedUploadTransactions = uploadTransactionsRepository.findById(uploadTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedUploadTransactions are not directly saved in db
        em.detach(updatedUploadTransactions);
        updatedUploadTransactions
            .fileName(UPDATED_FILE_NAME)
            .templateUrl(UPDATED_TEMPLATE_URL)
            .status(UPDATED_STATUS)
            .generatedCode(UPDATED_GENERATED_CODE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(updatedUploadTransactions);

        restUploadTransactionsMockMvc.perform(put("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeUpdate);
        UploadTransactions testUploadTransactions = uploadTransactionsList.get(uploadTransactionsList.size() - 1);
        assertThat(testUploadTransactions.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testUploadTransactions.getTemplateUrl()).isEqualTo(UPDATED_TEMPLATE_URL);
        assertThat(testUploadTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUploadTransactions.getGeneratedCode()).isEqualTo(UPDATED_GENERATED_CODE);
        assertThat(testUploadTransactions.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testUploadTransactions.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadTransactions() throws Exception {
        int databaseSizeBeforeUpdate = uploadTransactionsRepository.findAll().size();

        // Create the UploadTransactions
        UploadTransactionsDTO uploadTransactionsDTO = uploadTransactionsMapper.toDto(uploadTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadTransactionsMockMvc.perform(put("/api/upload-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uploadTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadTransactions in the database
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadTransactions() throws Exception {
        // Initialize the database
        uploadTransactionsRepository.saveAndFlush(uploadTransactions);

        int databaseSizeBeforeDelete = uploadTransactionsRepository.findAll().size();

        // Delete the uploadTransactions
        restUploadTransactionsMockMvc.perform(delete("/api/upload-transactions/{id}", uploadTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UploadTransactions> uploadTransactionsList = uploadTransactionsRepository.findAll();
        assertThat(uploadTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
