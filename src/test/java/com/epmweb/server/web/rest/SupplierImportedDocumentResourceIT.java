package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.SupplierImportedDocument;
import com.epmweb.server.domain.UploadTransactions;
import com.epmweb.server.repository.SupplierImportedDocumentRepository;
import com.epmweb.server.service.SupplierImportedDocumentService;
import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;
import com.epmweb.server.service.mapper.SupplierImportedDocumentMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.SupplierImportedDocumentCriteria;
import com.epmweb.server.service.SupplierImportedDocumentQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link SupplierImportedDocumentResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class SupplierImportedDocumentResourceIT {

    private static final byte[] DEFAULT_IMPORTED_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMPORTED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMPORTED_FAILED_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMPORTED_FAILED_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplierImportedDocumentRepository supplierImportedDocumentRepository;

    @Autowired
    private SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    @Autowired
    private SupplierImportedDocumentService supplierImportedDocumentService;

    @Autowired
    private SupplierImportedDocumentQueryService supplierImportedDocumentQueryService;

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

    private MockMvc restSupplierImportedDocumentMockMvc;

    private SupplierImportedDocument supplierImportedDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierImportedDocumentResource supplierImportedDocumentResource = new SupplierImportedDocumentResource(supplierImportedDocumentService, supplierImportedDocumentQueryService);
        this.restSupplierImportedDocumentMockMvc = MockMvcBuilders.standaloneSetup(supplierImportedDocumentResource)
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
    public static SupplierImportedDocument createEntity(EntityManager em) {
        SupplierImportedDocument supplierImportedDocument = new SupplierImportedDocument()
            .importedTemplate(DEFAULT_IMPORTED_TEMPLATE)
            .importedTemplateContentType(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(DEFAULT_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return supplierImportedDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierImportedDocument createUpdatedEntity(EntityManager em) {
        SupplierImportedDocument supplierImportedDocument = new SupplierImportedDocument()
            .importedTemplate(UPDATED_IMPORTED_TEMPLATE)
            .importedTemplateContentType(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(UPDATED_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return supplierImportedDocument;
    }

    @BeforeEach
    public void initTest() {
        supplierImportedDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierImportedDocument() throws Exception {
        int databaseSizeBeforeCreate = supplierImportedDocumentRepository.findAll().size();

        // Create the SupplierImportedDocument
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(supplierImportedDocument);
        restSupplierImportedDocumentMockMvc.perform(post("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierImportedDocument testSupplierImportedDocument = supplierImportedDocumentList.get(supplierImportedDocumentList.size() - 1);
        assertThat(testSupplierImportedDocument.getImportedTemplate()).isEqualTo(DEFAULT_IMPORTED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedTemplateContentType()).isEqualTo(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplate()).isEqualTo(DEFAULT_IMPORTED_FAILED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplateContentType()).isEqualTo(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
        assertThat(testSupplierImportedDocument.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testSupplierImportedDocument.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createSupplierImportedDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierImportedDocumentRepository.findAll().size();

        // Create the SupplierImportedDocument with an existing ID
        supplierImportedDocument.setId(1L);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(supplierImportedDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierImportedDocumentMockMvc.perform(post("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSupplierImportedDocuments() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierImportedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].importedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].importedFailedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedFailedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getSupplierImportedDocument() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get the supplierImportedDocument
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents/{id}", supplierImportedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierImportedDocument.getId().intValue()))
            .andExpect(jsonPath("$.importedTemplateContentType").value(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.importedTemplate").value(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE)))
            .andExpect(jsonPath("$.importedFailedTemplateContentType").value(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.importedFailedTemplate").value(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE)))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getSupplierImportedDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        Long id = supplierImportedDocument.getId();

        defaultSupplierImportedDocumentShouldBeFound("id.equals=" + id);
        defaultSupplierImportedDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierImportedDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierImportedDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierImportedDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierImportedDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierImportedDocumentList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierImportedDocumentList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the supplierImportedDocumentList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedBy is not null
        defaultSupplierImportedDocumentShouldBeFound("lastEditedBy.specified=true");

        // Get all the supplierImportedDocumentList where lastEditedBy is null
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierImportedDocumentList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the supplierImportedDocumentList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultSupplierImportedDocumentShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultSupplierImportedDocumentShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the supplierImportedDocumentList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the supplierImportedDocumentList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierImportedDocumentShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultSupplierImportedDocumentShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the supplierImportedDocumentList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        // Get all the supplierImportedDocumentList where lastEditedWhen is not null
        defaultSupplierImportedDocumentShouldBeFound("lastEditedWhen.specified=true");

        // Get all the supplierImportedDocumentList where lastEditedWhen is null
        defaultSupplierImportedDocumentShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierImportedDocumentsByUploadTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);
        UploadTransactions uploadTransaction = UploadTransactionsResourceIT.createEntity(em);
        em.persist(uploadTransaction);
        em.flush();
        supplierImportedDocument.setUploadTransaction(uploadTransaction);
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);
        Long uploadTransactionId = uploadTransaction.getId();

        // Get all the supplierImportedDocumentList where uploadTransaction equals to uploadTransactionId
        defaultSupplierImportedDocumentShouldBeFound("uploadTransactionId.equals=" + uploadTransactionId);

        // Get all the supplierImportedDocumentList where uploadTransaction equals to uploadTransactionId + 1
        defaultSupplierImportedDocumentShouldNotBeFound("uploadTransactionId.equals=" + (uploadTransactionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierImportedDocumentShouldBeFound(String filter) throws Exception {
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierImportedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].importedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].importedFailedTemplateContentType").value(hasItem(DEFAULT_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].importedFailedTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMPORTED_FAILED_TEMPLATE))))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierImportedDocumentShouldNotBeFound(String filter) throws Exception {
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplierImportedDocument() throws Exception {
        // Get the supplierImportedDocument
        restSupplierImportedDocumentMockMvc.perform(get("/api/supplier-imported-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierImportedDocument() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        int databaseSizeBeforeUpdate = supplierImportedDocumentRepository.findAll().size();

        // Update the supplierImportedDocument
        SupplierImportedDocument updatedSupplierImportedDocument = supplierImportedDocumentRepository.findById(supplierImportedDocument.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierImportedDocument are not directly saved in db
        em.detach(updatedSupplierImportedDocument);
        updatedSupplierImportedDocument
            .importedTemplate(UPDATED_IMPORTED_TEMPLATE)
            .importedTemplateContentType(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE)
            .importedFailedTemplate(UPDATED_IMPORTED_FAILED_TEMPLATE)
            .importedFailedTemplateContentType(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(updatedSupplierImportedDocument);

        restSupplierImportedDocumentMockMvc.perform(put("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeUpdate);
        SupplierImportedDocument testSupplierImportedDocument = supplierImportedDocumentList.get(supplierImportedDocumentList.size() - 1);
        assertThat(testSupplierImportedDocument.getImportedTemplate()).isEqualTo(UPDATED_IMPORTED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedTemplateContentType()).isEqualTo(UPDATED_IMPORTED_TEMPLATE_CONTENT_TYPE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplate()).isEqualTo(UPDATED_IMPORTED_FAILED_TEMPLATE);
        assertThat(testSupplierImportedDocument.getImportedFailedTemplateContentType()).isEqualTo(UPDATED_IMPORTED_FAILED_TEMPLATE_CONTENT_TYPE);
        assertThat(testSupplierImportedDocument.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testSupplierImportedDocument.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierImportedDocument() throws Exception {
        int databaseSizeBeforeUpdate = supplierImportedDocumentRepository.findAll().size();

        // Create the SupplierImportedDocument
        SupplierImportedDocumentDTO supplierImportedDocumentDTO = supplierImportedDocumentMapper.toDto(supplierImportedDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierImportedDocumentMockMvc.perform(put("/api/supplier-imported-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierImportedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierImportedDocument in the database
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierImportedDocument() throws Exception {
        // Initialize the database
        supplierImportedDocumentRepository.saveAndFlush(supplierImportedDocument);

        int databaseSizeBeforeDelete = supplierImportedDocumentRepository.findAll().size();

        // Delete the supplierImportedDocument
        restSupplierImportedDocumentMockMvc.perform(delete("/api/supplier-imported-documents/{id}", supplierImportedDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierImportedDocument> supplierImportedDocumentList = supplierImportedDocumentRepository.findAll();
        assertThat(supplierImportedDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
