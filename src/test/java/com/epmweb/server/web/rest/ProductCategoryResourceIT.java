package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductCategory;
import com.epmweb.server.domain.Photos;
import com.epmweb.server.domain.ProductCategory;
import com.epmweb.server.repository.ProductCategoryRepository;
import com.epmweb.server.service.ProductCategoryService;
import com.epmweb.server.service.dto.ProductCategoryDTO;
import com.epmweb.server.service.mapper.ProductCategoryMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductCategoryCriteria;
import com.epmweb.server.service.ProductCategoryQueryService;

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
 * Integration tests for the {@link ProductCategoryResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_URL = "BBBBBBBBBB";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;

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

    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductCategoryResource productCategoryResource = new ProductCategoryResource(productCategoryService, productCategoryQueryService);
        this.restProductCategoryMockMvc = MockMvcBuilders.standaloneSetup(productCategoryResource)
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
    public static ProductCategory createEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(DEFAULT_NAME)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .thumbnailUrl(DEFAULT_THUMBNAIL_URL);
        return productCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createUpdatedEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(UPDATED_NAME)
            .shortLabel(UPDATED_SHORT_LABEL)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        return productCategory;
    }

    @BeforeEach
    public void initTest() {
        productCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);
        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testProductCategory.getThumbnailUrl()).isEqualTo(DEFAULT_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void createProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory with an existing ID
        productCategory.setId(1L);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productCategoryRepository.findAll().size();
        // set the field null
        productCategory.setName(null);

        // Create the ProductCategory, which fails.
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));
    }
    
    @Test
    @Transactional
    public void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL))
            .andExpect(jsonPath("$.thumbnailUrl").value(DEFAULT_THUMBNAIL_URL));
    }


    @Test
    @Transactional
    public void getProductCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        Long id = productCategory.getId();

        defaultProductCategoryShouldBeFound("id.equals=" + id);
        defaultProductCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name equals to DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name not equals to DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name not equals to UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name is not null
        defaultProductCategoryShouldBeFound("name.specified=true");

        // Get all the productCategoryList where name is null
        defaultProductCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name contains DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productCategoryList where name contains UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name does not contain DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productCategoryList where name does not contain UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel equals to DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.equals=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.equals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel not equals to DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.notEquals=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel not equals to UPDATED_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.notEquals=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel in DEFAULT_SHORT_LABEL or UPDATED_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.in=" + DEFAULT_SHORT_LABEL + "," + UPDATED_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel equals to UPDATED_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.in=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel is not null
        defaultProductCategoryShouldBeFound("shortLabel.specified=true");

        // Get all the productCategoryList where shortLabel is null
        defaultProductCategoryShouldNotBeFound("shortLabel.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel contains DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.contains=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel contains UPDATED_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.contains=" + UPDATED_SHORT_LABEL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByShortLabelNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where shortLabel does not contain DEFAULT_SHORT_LABEL
        defaultProductCategoryShouldNotBeFound("shortLabel.doesNotContain=" + DEFAULT_SHORT_LABEL);

        // Get all the productCategoryList where shortLabel does not contain UPDATED_SHORT_LABEL
        defaultProductCategoryShouldBeFound("shortLabel.doesNotContain=" + UPDATED_SHORT_LABEL);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByThumbnailUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where thumbnailUrl equals to DEFAULT_THUMBNAIL_URL
        defaultProductCategoryShouldBeFound("thumbnailUrl.equals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productCategoryList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductCategoryShouldNotBeFound("thumbnailUrl.equals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByThumbnailUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where thumbnailUrl not equals to DEFAULT_THUMBNAIL_URL
        defaultProductCategoryShouldNotBeFound("thumbnailUrl.notEquals=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productCategoryList where thumbnailUrl not equals to UPDATED_THUMBNAIL_URL
        defaultProductCategoryShouldBeFound("thumbnailUrl.notEquals=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByThumbnailUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where thumbnailUrl in DEFAULT_THUMBNAIL_URL or UPDATED_THUMBNAIL_URL
        defaultProductCategoryShouldBeFound("thumbnailUrl.in=" + DEFAULT_THUMBNAIL_URL + "," + UPDATED_THUMBNAIL_URL);

        // Get all the productCategoryList where thumbnailUrl equals to UPDATED_THUMBNAIL_URL
        defaultProductCategoryShouldNotBeFound("thumbnailUrl.in=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByThumbnailUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where thumbnailUrl is not null
        defaultProductCategoryShouldBeFound("thumbnailUrl.specified=true");

        // Get all the productCategoryList where thumbnailUrl is null
        defaultProductCategoryShouldNotBeFound("thumbnailUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByThumbnailUrlContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where thumbnailUrl contains DEFAULT_THUMBNAIL_URL
        defaultProductCategoryShouldBeFound("thumbnailUrl.contains=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productCategoryList where thumbnailUrl contains UPDATED_THUMBNAIL_URL
        defaultProductCategoryShouldNotBeFound("thumbnailUrl.contains=" + UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByThumbnailUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where thumbnailUrl does not contain DEFAULT_THUMBNAIL_URL
        defaultProductCategoryShouldNotBeFound("thumbnailUrl.doesNotContain=" + DEFAULT_THUMBNAIL_URL);

        // Get all the productCategoryList where thumbnailUrl does not contain UPDATED_THUMBNAIL_URL
        defaultProductCategoryShouldBeFound("thumbnailUrl.doesNotContain=" + UPDATED_THUMBNAIL_URL);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByPhotoListIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        Photos photoList = PhotosResourceIT.createEntity(em);
        em.persist(photoList);
        em.flush();
        productCategory.addPhotoList(photoList);
        productCategoryRepository.saveAndFlush(productCategory);
        Long photoListId = photoList.getId();

        // Get all the productCategoryList where photoList equals to photoListId
        defaultProductCategoryShouldBeFound("photoListId.equals=" + photoListId);

        // Get all the productCategoryList where photoList equals to photoListId + 1
        defaultProductCategoryShouldNotBeFound("photoListId.equals=" + (photoListId + 1));
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        ProductCategory parent = ProductCategoryResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        productCategory.setParent(parent);
        productCategoryRepository.saveAndFlush(productCategory);
        Long parentId = parent.getId();

        // Get all the productCategoryList where parent equals to parentId
        defaultProductCategoryShouldBeFound("parentId.equals=" + parentId);

        // Get all the productCategoryList where parent equals to parentId + 1
        defaultProductCategoryShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryShouldBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL)))
            .andExpect(jsonPath("$.[*].thumbnailUrl").value(hasItem(DEFAULT_THUMBNAIL_URL)));

        // Check, that the count call also returns 1
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryShouldNotBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        ProductCategory updatedProductCategory = productCategoryRepository.findById(productCategory.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategory are not directly saved in db
        em.detach(updatedProductCategory);
        updatedProductCategory
            .name(UPDATED_NAME)
            .shortLabel(UPDATED_SHORT_LABEL)
            .thumbnailUrl(UPDATED_THUMBNAIL_URL);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(updatedProductCategory);

        restProductCategoryMockMvc.perform(put("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testProductCategory.getThumbnailUrl()).isEqualTo(UPDATED_THUMBNAIL_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc.perform(put("/api/product-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Delete the productCategory
        restProductCategoryMockMvc.perform(delete("/api/product-categories/{id}", productCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
