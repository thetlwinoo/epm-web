package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductCatalog;
import com.epmweb.server.domain.ProductCategory;
import com.epmweb.server.domain.Products;
import com.epmweb.server.repository.ProductCatalogRepository;
import com.epmweb.server.service.ProductCatalogService;
import com.epmweb.server.service.dto.ProductCatalogDTO;
import com.epmweb.server.service.mapper.ProductCatalogMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductCatalogCriteria;
import com.epmweb.server.service.ProductCatalogQueryService;

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
 * Integration tests for the {@link ProductCatalogResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductCatalogResourceIT {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private ProductCatalogMapper productCatalogMapper;

    @Autowired
    private ProductCatalogService productCatalogService;

    @Autowired
    private ProductCatalogQueryService productCatalogQueryService;

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

    private MockMvc restProductCatalogMockMvc;

    private ProductCatalog productCatalog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductCatalogResource productCatalogResource = new ProductCatalogResource(productCatalogService, productCatalogQueryService);
        this.restProductCatalogMockMvc = MockMvcBuilders.standaloneSetup(productCatalogResource)
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
    public static ProductCatalog createEntity(EntityManager em) {
        ProductCatalog productCatalog = new ProductCatalog();
        return productCatalog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCatalog createUpdatedEntity(EntityManager em) {
        ProductCatalog productCatalog = new ProductCatalog();
        return productCatalog;
    }

    @BeforeEach
    public void initTest() {
        productCatalog = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCatalog() throws Exception {
        int databaseSizeBeforeCreate = productCatalogRepository.findAll().size();

        // Create the ProductCatalog
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(productCatalog);
        restProductCatalogMockMvc.perform(post("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCatalog testProductCatalog = productCatalogList.get(productCatalogList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductCatalogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCatalogRepository.findAll().size();

        // Create the ProductCatalog with an existing ID
        productCatalog.setId(1L);
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(productCatalog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCatalogMockMvc.perform(post("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductCatalogs() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        // Get all the productCatalogList
        restProductCatalogMockMvc.perform(get("/api/product-catalogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCatalog.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductCatalog() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        // Get the productCatalog
        restProductCatalogMockMvc.perform(get("/api/product-catalogs/{id}", productCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productCatalog.getId().intValue()));
    }


    @Test
    @Transactional
    public void getProductCatalogsByIdFiltering() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        Long id = productCatalog.getId();

        defaultProductCatalogShouldBeFound("id.equals=" + id);
        defaultProductCatalogShouldNotBeFound("id.notEquals=" + id);

        defaultProductCatalogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCatalogShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCatalogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCatalogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCatalogsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        productCatalog.setProductCategory(productCategory);
        productCatalogRepository.saveAndFlush(productCatalog);
        Long productCategoryId = productCategory.getId();

        // Get all the productCatalogList where productCategory equals to productCategoryId
        defaultProductCatalogShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productCatalogList where productCategory equals to productCategoryId + 1
        defaultProductCatalogShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductCatalogsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productCatalog.setProduct(product);
        productCatalogRepository.saveAndFlush(productCatalog);
        Long productId = product.getId();

        // Get all the productCatalogList where product equals to productId
        defaultProductCatalogShouldBeFound("productId.equals=" + productId);

        // Get all the productCatalogList where product equals to productId + 1
        defaultProductCatalogShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCatalogShouldBeFound(String filter) throws Exception {
        restProductCatalogMockMvc.perform(get("/api/product-catalogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCatalog.getId().intValue())));

        // Check, that the count call also returns 1
        restProductCatalogMockMvc.perform(get("/api/product-catalogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCatalogShouldNotBeFound(String filter) throws Exception {
        restProductCatalogMockMvc.perform(get("/api/product-catalogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCatalogMockMvc.perform(get("/api/product-catalogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductCatalog() throws Exception {
        // Get the productCatalog
        restProductCatalogMockMvc.perform(get("/api/product-catalogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCatalog() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        int databaseSizeBeforeUpdate = productCatalogRepository.findAll().size();

        // Update the productCatalog
        ProductCatalog updatedProductCatalog = productCatalogRepository.findById(productCatalog.getId()).get();
        // Disconnect from session so that the updates on updatedProductCatalog are not directly saved in db
        em.detach(updatedProductCatalog);
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(updatedProductCatalog);

        restProductCatalogMockMvc.perform(put("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeUpdate);
        ProductCatalog testProductCatalog = productCatalogList.get(productCatalogList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCatalog() throws Exception {
        int databaseSizeBeforeUpdate = productCatalogRepository.findAll().size();

        // Create the ProductCatalog
        ProductCatalogDTO productCatalogDTO = productCatalogMapper.toDto(productCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCatalogMockMvc.perform(put("/api/product-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCatalog in the database
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCatalog() throws Exception {
        // Initialize the database
        productCatalogRepository.saveAndFlush(productCatalog);

        int databaseSizeBeforeDelete = productCatalogRepository.findAll().size();

        // Delete the productCatalog
        restProductCatalogMockMvc.perform(delete("/api/product-catalogs/{id}", productCatalog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll();
        assertThat(productCatalogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
