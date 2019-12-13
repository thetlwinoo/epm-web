package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductAttribute;
import com.epmweb.server.domain.ProductAttributeSet;
import com.epmweb.server.domain.Suppliers;
import com.epmweb.server.repository.ProductAttributeRepository;
import com.epmweb.server.service.ProductAttributeService;
import com.epmweb.server.service.dto.ProductAttributeDTO;
import com.epmweb.server.service.mapper.ProductAttributeMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductAttributeCriteria;
import com.epmweb.server.service.ProductAttributeQueryService;

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
 * Integration tests for the {@link ProductAttributeResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductAttributeResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private ProductAttributeQueryService productAttributeQueryService;

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

    private MockMvc restProductAttributeMockMvc;

    private ProductAttribute productAttribute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAttributeResource productAttributeResource = new ProductAttributeResource(productAttributeService, productAttributeQueryService);
        this.restProductAttributeMockMvc = MockMvcBuilders.standaloneSetup(productAttributeResource)
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
    public static ProductAttribute createEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .value(DEFAULT_VALUE);
        return productAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttribute createUpdatedEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .value(UPDATED_VALUE);
        return productAttribute;
    }

    @BeforeEach
    public void initTest() {
        productAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttribute() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createProductAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute with an existing ID
        productAttribute.setId(1L);
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAttributeRepository.findAll().size();
        // set the field null
        productAttribute.setValue(null);

        // Create the ProductAttribute, which fails.
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAttributes() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", productAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAttribute.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getProductAttributesByIdFiltering() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        Long id = productAttribute.getId();

        defaultProductAttributeShouldBeFound("id.equals=" + id);
        defaultProductAttributeShouldNotBeFound("id.notEquals=" + id);

        defaultProductAttributeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductAttributeShouldNotBeFound("id.greaterThan=" + id);

        defaultProductAttributeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductAttributeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductAttributesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where value equals to DEFAULT_VALUE
        defaultProductAttributeShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the productAttributeList where value equals to UPDATED_VALUE
        defaultProductAttributeShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where value not equals to DEFAULT_VALUE
        defaultProductAttributeShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the productAttributeList where value not equals to UPDATED_VALUE
        defaultProductAttributeShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultProductAttributeShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the productAttributeList where value equals to UPDATED_VALUE
        defaultProductAttributeShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where value is not null
        defaultProductAttributeShouldBeFound("value.specified=true");

        // Get all the productAttributeList where value is null
        defaultProductAttributeShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductAttributesByValueContainsSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where value contains DEFAULT_VALUE
        defaultProductAttributeShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the productAttributeList where value contains UPDATED_VALUE
        defaultProductAttributeShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where value does not contain DEFAULT_VALUE
        defaultProductAttributeShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the productAttributeList where value does not contain UPDATED_VALUE
        defaultProductAttributeShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);
        ProductAttributeSet productAttributeSet = ProductAttributeSetResourceIT.createEntity(em);
        em.persist(productAttributeSet);
        em.flush();
        productAttribute.setProductAttributeSet(productAttributeSet);
        productAttributeRepository.saveAndFlush(productAttribute);
        Long productAttributeSetId = productAttributeSet.getId();

        // Get all the productAttributeList where productAttributeSet equals to productAttributeSetId
        defaultProductAttributeShouldBeFound("productAttributeSetId.equals=" + productAttributeSetId);

        // Get all the productAttributeList where productAttributeSet equals to productAttributeSetId + 1
        defaultProductAttributeShouldNotBeFound("productAttributeSetId.equals=" + (productAttributeSetId + 1));
    }


    @Test
    @Transactional
    public void getAllProductAttributesBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        productAttribute.setSupplier(supplier);
        productAttributeRepository.saveAndFlush(productAttribute);
        Long supplierId = supplier.getId();

        // Get all the productAttributeList where supplier equals to supplierId
        defaultProductAttributeShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productAttributeList where supplier equals to supplierId + 1
        defaultProductAttributeShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductAttributeShouldBeFound(String filter) throws Exception {
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restProductAttributeMockMvc.perform(get("/api/product-attributes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductAttributeShouldNotBeFound(String filter) throws Exception {
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductAttributeMockMvc.perform(get("/api/product-attributes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductAttribute() throws Exception {
        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Update the productAttribute
        ProductAttribute updatedProductAttribute = productAttributeRepository.findById(productAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttribute are not directly saved in db
        em.detach(updatedProductAttribute);
        updatedProductAttribute
            .value(UPDATED_VALUE);
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(updatedProductAttribute);

        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeDelete = productAttributeRepository.findAll().size();

        // Delete the productAttribute
        restProductAttributeMockMvc.perform(delete("/api/product-attributes/{id}", productAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
