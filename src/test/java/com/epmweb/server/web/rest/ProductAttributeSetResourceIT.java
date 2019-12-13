package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductAttributeSet;
import com.epmweb.server.domain.ProductOptionSet;
import com.epmweb.server.repository.ProductAttributeSetRepository;
import com.epmweb.server.service.ProductAttributeSetService;
import com.epmweb.server.service.dto.ProductAttributeSetDTO;
import com.epmweb.server.service.mapper.ProductAttributeSetMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductAttributeSetCriteria;
import com.epmweb.server.service.ProductAttributeSetQueryService;

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
 * Integration tests for the {@link ProductAttributeSetResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductAttributeSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductAttributeSetRepository productAttributeSetRepository;

    @Autowired
    private ProductAttributeSetMapper productAttributeSetMapper;

    @Autowired
    private ProductAttributeSetService productAttributeSetService;

    @Autowired
    private ProductAttributeSetQueryService productAttributeSetQueryService;

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

    private MockMvc restProductAttributeSetMockMvc;

    private ProductAttributeSet productAttributeSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAttributeSetResource productAttributeSetResource = new ProductAttributeSetResource(productAttributeSetService, productAttributeSetQueryService);
        this.restProductAttributeSetMockMvc = MockMvcBuilders.standaloneSetup(productAttributeSetResource)
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
    public static ProductAttributeSet createEntity(EntityManager em) {
        ProductAttributeSet productAttributeSet = new ProductAttributeSet()
            .name(DEFAULT_NAME);
        return productAttributeSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttributeSet createUpdatedEntity(EntityManager em) {
        ProductAttributeSet productAttributeSet = new ProductAttributeSet()
            .name(UPDATED_NAME);
        return productAttributeSet;
    }

    @BeforeEach
    public void initTest() {
        productAttributeSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttributeSet() throws Exception {
        int databaseSizeBeforeCreate = productAttributeSetRepository.findAll().size();

        // Create the ProductAttributeSet
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);
        restProductAttributeSetMockMvc.perform(post("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttributeSet testProductAttributeSet = productAttributeSetList.get(productAttributeSetList.size() - 1);
        assertThat(testProductAttributeSet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductAttributeSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeSetRepository.findAll().size();

        // Create the ProductAttributeSet with an existing ID
        productAttributeSet.setId(1L);
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeSetMockMvc.perform(post("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAttributeSetRepository.findAll().size();
        // set the field null
        productAttributeSet.setName(null);

        // Create the ProductAttributeSet, which fails.
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);

        restProductAttributeSetMockMvc.perform(post("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isBadRequest());

        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSets() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttributeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductAttributeSet() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get the productAttributeSet
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/{id}", productAttributeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAttributeSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductAttributeSetsByIdFiltering() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        Long id = productAttributeSet.getId();

        defaultProductAttributeSetShouldBeFound("id.equals=" + id);
        defaultProductAttributeSetShouldNotBeFound("id.notEquals=" + id);

        defaultProductAttributeSetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductAttributeSetShouldNotBeFound("id.greaterThan=" + id);

        defaultProductAttributeSetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductAttributeSetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductAttributeSetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where name equals to DEFAULT_NAME
        defaultProductAttributeSetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productAttributeSetList where name equals to UPDATED_NAME
        defaultProductAttributeSetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where name not equals to DEFAULT_NAME
        defaultProductAttributeSetShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productAttributeSetList where name not equals to UPDATED_NAME
        defaultProductAttributeSetShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductAttributeSetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productAttributeSetList where name equals to UPDATED_NAME
        defaultProductAttributeSetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where name is not null
        defaultProductAttributeSetShouldBeFound("name.specified=true");

        // Get all the productAttributeSetList where name is null
        defaultProductAttributeSetShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductAttributeSetsByNameContainsSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where name contains DEFAULT_NAME
        defaultProductAttributeSetShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productAttributeSetList where name contains UPDATED_NAME
        defaultProductAttributeSetShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where name does not contain DEFAULT_NAME
        defaultProductAttributeSetShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productAttributeSetList where name does not contain UPDATED_NAME
        defaultProductAttributeSetShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductOptionSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);
        ProductOptionSet productOptionSet = ProductOptionSetResourceIT.createEntity(em);
        em.persist(productOptionSet);
        em.flush();
        productAttributeSet.setProductOptionSet(productOptionSet);
        productAttributeSetRepository.saveAndFlush(productAttributeSet);
        Long productOptionSetId = productOptionSet.getId();

        // Get all the productAttributeSetList where productOptionSet equals to productOptionSetId
        defaultProductAttributeSetShouldBeFound("productOptionSetId.equals=" + productOptionSetId);

        // Get all the productAttributeSetList where productOptionSet equals to productOptionSetId + 1
        defaultProductAttributeSetShouldNotBeFound("productOptionSetId.equals=" + (productOptionSetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductAttributeSetShouldBeFound(String filter) throws Exception {
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttributeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductAttributeSetShouldNotBeFound(String filter) throws Exception {
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductAttributeSet() throws Exception {
        // Get the productAttributeSet
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttributeSet() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        int databaseSizeBeforeUpdate = productAttributeSetRepository.findAll().size();

        // Update the productAttributeSet
        ProductAttributeSet updatedProductAttributeSet = productAttributeSetRepository.findById(productAttributeSet.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttributeSet are not directly saved in db
        em.detach(updatedProductAttributeSet);
        updatedProductAttributeSet
            .name(UPDATED_NAME);
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(updatedProductAttributeSet);

        restProductAttributeSetMockMvc.perform(put("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeUpdate);
        ProductAttributeSet testProductAttributeSet = productAttributeSetList.get(productAttributeSetList.size() - 1);
        assertThat(testProductAttributeSet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttributeSet() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeSetRepository.findAll().size();

        // Create the ProductAttributeSet
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeSetMockMvc.perform(put("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttributeSet() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        int databaseSizeBeforeDelete = productAttributeSetRepository.findAll().size();

        // Delete the productAttributeSet
        restProductAttributeSetMockMvc.perform(delete("/api/product-attribute-sets/{id}", productAttributeSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
