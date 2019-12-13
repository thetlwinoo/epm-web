package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductBrand;
import com.epmweb.server.repository.ProductBrandRepository;
import com.epmweb.server.service.ProductBrandService;
import com.epmweb.server.service.dto.ProductBrandDTO;
import com.epmweb.server.service.mapper.ProductBrandMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductBrandCriteria;
import com.epmweb.server.service.ProductBrandQueryService;

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
 * Integration tests for the {@link ProductBrandResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductBrandResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    @Autowired
    private ProductBrandRepository productBrandRepository;

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private ProductBrandQueryService productBrandQueryService;

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

    private MockMvc restProductBrandMockMvc;

    private ProductBrand productBrand;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductBrandResource productBrandResource = new ProductBrandResource(productBrandService, productBrandQueryService);
        this.restProductBrandMockMvc = MockMvcBuilders.standaloneSetup(productBrandResource)
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
    public static ProductBrand createEntity(EntityManager em) {
        ProductBrand productBrand = new ProductBrand()
            .name(DEFAULT_NAME)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL);
        return productBrand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductBrand createUpdatedEntity(EntityManager em) {
        ProductBrand productBrand = new ProductBrand()
            .name(UPDATED_NAME)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        return productBrand;
    }

    @BeforeEach
    public void initTest() {
        productBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductBrand() throws Exception {
        int databaseSizeBeforeCreate = productBrandRepository.findAll().size();

        // Create the ProductBrand
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);
        restProductBrandMockMvc.perform(post("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeCreate + 1);
        ProductBrand testProductBrand = productBrandList.get(productBrandList.size() - 1);
        assertThat(testProductBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductBrand.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void createProductBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productBrandRepository.findAll().size();

        // Create the ProductBrand with an existing ID
        productBrand.setId(1L);
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductBrandMockMvc.perform(post("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productBrandRepository.findAll().size();
        // set the field null
        productBrand.setName(null);

        // Create the ProductBrand, which fails.
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        restProductBrandMockMvc.perform(post("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductBrands() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));
    }
    
    @Test
    @Transactional
    public void getProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get the productBrand
        restProductBrandMockMvc.perform(get("/api/product-brands/{id}", productBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productBrand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL));
    }


    @Test
    @Transactional
    public void getProductBrandsByIdFiltering() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        Long id = productBrand.getId();

        defaultProductBrandShouldBeFound("id.equals=" + id);
        defaultProductBrandShouldNotBeFound("id.notEquals=" + id);

        defaultProductBrandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductBrandShouldNotBeFound("id.greaterThan=" + id);

        defaultProductBrandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductBrandShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name equals to DEFAULT_NAME
        defaultProductBrandShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productBrandList where name equals to UPDATED_NAME
        defaultProductBrandShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name not equals to DEFAULT_NAME
        defaultProductBrandShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productBrandList where name not equals to UPDATED_NAME
        defaultProductBrandShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductBrandShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productBrandList where name equals to UPDATED_NAME
        defaultProductBrandShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name is not null
        defaultProductBrandShouldBeFound("name.specified=true");

        // Get all the productBrandList where name is null
        defaultProductBrandShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByNameContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name contains DEFAULT_NAME
        defaultProductBrandShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productBrandList where name contains UPDATED_NAME
        defaultProductBrandShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where name does not contain DEFAULT_NAME
        defaultProductBrandShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productBrandList where name does not contain UPDATED_NAME
        defaultProductBrandShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl is not null
        defaultProductBrandShouldBeFound("thumbnailUrl.specified=true");

        // Get all the productBrandList where thumbnailUrl is null
        defaultProductBrandShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductBrandsByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        // Get all the productBrandList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultProductBrandShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productBrandList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultProductBrandShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductBrandShouldBeFound(String filter) throws Exception {
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));

        // Check, that the count call also returns 1
        restProductBrandMockMvc.perform(get("/api/product-brands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductBrandShouldNotBeFound(String filter) throws Exception {
        restProductBrandMockMvc.perform(get("/api/product-brands?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductBrandMockMvc.perform(get("/api/product-brands/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductBrand() throws Exception {
        // Get the productBrand
        restProductBrandMockMvc.perform(get("/api/product-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        int databaseSizeBeforeUpdate = productBrandRepository.findAll().size();

        // Update the productBrand
        ProductBrand updatedProductBrand = productBrandRepository.findById(productBrand.getId()).get();
        // Disconnect from session so that the updates on updatedProductBrand are not directly saved in db
        em.detach(updatedProductBrand);
        updatedProductBrand
            .name(UPDATED_NAME)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(updatedProductBrand);

        restProductBrandMockMvc.perform(put("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isOk());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeUpdate);
        ProductBrand testProductBrand = productBrandList.get(productBrandList.size() - 1);
        assertThat(testProductBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductBrand.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingProductBrand() throws Exception {
        int databaseSizeBeforeUpdate = productBrandRepository.findAll().size();

        // Create the ProductBrand
        ProductBrandDTO productBrandDTO = productBrandMapper.toDto(productBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductBrandMockMvc.perform(put("/api/product-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductBrand in the database
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductBrand() throws Exception {
        // Initialize the database
        productBrandRepository.saveAndFlush(productBrand);

        int databaseSizeBeforeDelete = productBrandRepository.findAll().size();

        // Delete the productBrand
        restProductBrandMockMvc.perform(delete("/api/product-brands/{id}", productBrand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductBrand> productBrandList = productBrandRepository.findAll();
        assertThat(productBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
