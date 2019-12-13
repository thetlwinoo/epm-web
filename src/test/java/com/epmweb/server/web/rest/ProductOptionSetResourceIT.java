package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductOptionSet;
import com.epmweb.server.repository.ProductOptionSetRepository;
import com.epmweb.server.service.ProductOptionSetService;
import com.epmweb.server.service.dto.ProductOptionSetDTO;
import com.epmweb.server.service.mapper.ProductOptionSetMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductOptionSetCriteria;
import com.epmweb.server.service.ProductOptionSetQueryService;

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
 * Integration tests for the {@link ProductOptionSetResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductOptionSetResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductOptionSetRepository productOptionSetRepository;

    @Autowired
    private ProductOptionSetMapper productOptionSetMapper;

    @Autowired
    private ProductOptionSetService productOptionSetService;

    @Autowired
    private ProductOptionSetQueryService productOptionSetQueryService;

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

    private MockMvc restProductOptionSetMockMvc;

    private ProductOptionSet productOptionSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductOptionSetResource productOptionSetResource = new ProductOptionSetResource(productOptionSetService, productOptionSetQueryService);
        this.restProductOptionSetMockMvc = MockMvcBuilders.standaloneSetup(productOptionSetResource)
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
    public static ProductOptionSet createEntity(EntityManager em) {
        ProductOptionSet productOptionSet = new ProductOptionSet()
            .value(DEFAULT_VALUE);
        return productOptionSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionSet createUpdatedEntity(EntityManager em) {
        ProductOptionSet productOptionSet = new ProductOptionSet()
            .value(UPDATED_VALUE);
        return productOptionSet;
    }

    @BeforeEach
    public void initTest() {
        productOptionSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOptionSet() throws Exception {
        int databaseSizeBeforeCreate = productOptionSetRepository.findAll().size();

        // Create the ProductOptionSet
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);
        restProductOptionSetMockMvc.perform(post("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionSet testProductOptionSet = productOptionSetList.get(productOptionSetList.size() - 1);
        assertThat(testProductOptionSet.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createProductOptionSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionSetRepository.findAll().size();

        // Create the ProductOptionSet with an existing ID
        productOptionSet.setId(1L);
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionSetMockMvc.perform(post("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionSetRepository.findAll().size();
        // set the field null
        productOptionSet.setValue(null);

        // Create the ProductOptionSet, which fails.
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);

        restProductOptionSetMockMvc.perform(post("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isBadRequest());

        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptionSets() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getProductOptionSet() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get the productOptionSet
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/{id}", productOptionSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionSet.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getProductOptionSetsByIdFiltering() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        Long id = productOptionSet.getId();

        defaultProductOptionSetShouldBeFound("id.equals=" + id);
        defaultProductOptionSetShouldNotBeFound("id.notEquals=" + id);

        defaultProductOptionSetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOptionSetShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOptionSetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOptionSetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductOptionSetsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where value equals to DEFAULT_VALUE
        defaultProductOptionSetShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the productOptionSetList where value equals to UPDATED_VALUE
        defaultProductOptionSetShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where value not equals to DEFAULT_VALUE
        defaultProductOptionSetShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the productOptionSetList where value not equals to UPDATED_VALUE
        defaultProductOptionSetShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultProductOptionSetShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the productOptionSetList where value equals to UPDATED_VALUE
        defaultProductOptionSetShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where value is not null
        defaultProductOptionSetShouldBeFound("value.specified=true");

        // Get all the productOptionSetList where value is null
        defaultProductOptionSetShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductOptionSetsByValueContainsSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where value contains DEFAULT_VALUE
        defaultProductOptionSetShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the productOptionSetList where value contains UPDATED_VALUE
        defaultProductOptionSetShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where value does not contain DEFAULT_VALUE
        defaultProductOptionSetShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the productOptionSetList where value does not contain UPDATED_VALUE
        defaultProductOptionSetShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionSetShouldBeFound(String filter) throws Exception {
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionSetShouldNotBeFound(String filter) throws Exception {
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductOptionSet() throws Exception {
        // Get the productOptionSet
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOptionSet() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        int databaseSizeBeforeUpdate = productOptionSetRepository.findAll().size();

        // Update the productOptionSet
        ProductOptionSet updatedProductOptionSet = productOptionSetRepository.findById(productOptionSet.getId()).get();
        // Disconnect from session so that the updates on updatedProductOptionSet are not directly saved in db
        em.detach(updatedProductOptionSet);
        updatedProductOptionSet
            .value(UPDATED_VALUE);
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(updatedProductOptionSet);

        restProductOptionSetMockMvc.perform(put("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isOk());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionSet testProductOptionSet = productOptionSetList.get(productOptionSetList.size() - 1);
        assertThat(testProductOptionSet.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOptionSet() throws Exception {
        int databaseSizeBeforeUpdate = productOptionSetRepository.findAll().size();

        // Create the ProductOptionSet
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionSetMockMvc.perform(put("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOptionSet() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        int databaseSizeBeforeDelete = productOptionSetRepository.findAll().size();

        // Delete the productOptionSet
        restProductOptionSetMockMvc.perform(delete("/api/product-option-sets/{id}", productOptionSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
