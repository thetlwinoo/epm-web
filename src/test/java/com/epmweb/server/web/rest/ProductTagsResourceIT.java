package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductTags;
import com.epmweb.server.domain.Products;
import com.epmweb.server.repository.ProductTagsRepository;
import com.epmweb.server.service.ProductTagsService;
import com.epmweb.server.service.dto.ProductTagsDTO;
import com.epmweb.server.service.mapper.ProductTagsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductTagsCriteria;
import com.epmweb.server.service.ProductTagsQueryService;

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
 * Integration tests for the {@link ProductTagsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductTagsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductTagsRepository productTagsRepository;

    @Autowired
    private ProductTagsMapper productTagsMapper;

    @Autowired
    private ProductTagsService productTagsService;

    @Autowired
    private ProductTagsQueryService productTagsQueryService;

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

    private MockMvc restProductTagsMockMvc;

    private ProductTags productTags;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductTagsResource productTagsResource = new ProductTagsResource(productTagsService, productTagsQueryService);
        this.restProductTagsMockMvc = MockMvcBuilders.standaloneSetup(productTagsResource)
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
    public static ProductTags createEntity(EntityManager em) {
        ProductTags productTags = new ProductTags()
            .name(DEFAULT_NAME);
        return productTags;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTags createUpdatedEntity(EntityManager em) {
        ProductTags productTags = new ProductTags()
            .name(UPDATED_NAME);
        return productTags;
    }

    @BeforeEach
    public void initTest() {
        productTags = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductTags() throws Exception {
        int databaseSizeBeforeCreate = productTagsRepository.findAll().size();

        // Create the ProductTags
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);
        restProductTagsMockMvc.perform(post("/api/product-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTags testProductTags = productTagsList.get(productTagsList.size() - 1);
        assertThat(testProductTags.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productTagsRepository.findAll().size();

        // Create the ProductTags with an existing ID
        productTags.setId(1L);
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTagsMockMvc.perform(post("/api/product-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTagsRepository.findAll().size();
        // set the field null
        productTags.setName(null);

        // Create the ProductTags, which fails.
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);

        restProductTagsMockMvc.perform(post("/api/product-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList
        restProductTagsMockMvc.perform(get("/api/product-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get the productTags
        restProductTagsMockMvc.perform(get("/api/product-tags/{id}", productTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productTags.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getProductTagsByIdFiltering() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        Long id = productTags.getId();

        defaultProductTagsShouldBeFound("id.equals=" + id);
        defaultProductTagsShouldNotBeFound("id.notEquals=" + id);

        defaultProductTagsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductTagsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductTagsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductTagsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name equals to DEFAULT_NAME
        defaultProductTagsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productTagsList where name equals to UPDATED_NAME
        defaultProductTagsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name not equals to DEFAULT_NAME
        defaultProductTagsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productTagsList where name not equals to UPDATED_NAME
        defaultProductTagsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductTagsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productTagsList where name equals to UPDATED_NAME
        defaultProductTagsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name is not null
        defaultProductTagsShouldBeFound("name.specified=true");

        // Get all the productTagsList where name is null
        defaultProductTagsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductTagsByNameContainsSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name contains DEFAULT_NAME
        defaultProductTagsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productTagsList where name contains UPDATED_NAME
        defaultProductTagsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductTagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        // Get all the productTagsList where name does not contain DEFAULT_NAME
        defaultProductTagsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productTagsList where name does not contain UPDATED_NAME
        defaultProductTagsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductTagsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productTags.setProduct(product);
        productTagsRepository.saveAndFlush(productTags);
        Long productId = product.getId();

        // Get all the productTagsList where product equals to productId
        defaultProductTagsShouldBeFound("productId.equals=" + productId);

        // Get all the productTagsList where product equals to productId + 1
        defaultProductTagsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductTagsShouldBeFound(String filter) throws Exception {
        restProductTagsMockMvc.perform(get("/api/product-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restProductTagsMockMvc.perform(get("/api/product-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductTagsShouldNotBeFound(String filter) throws Exception {
        restProductTagsMockMvc.perform(get("/api/product-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductTagsMockMvc.perform(get("/api/product-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductTags() throws Exception {
        // Get the productTags
        restProductTagsMockMvc.perform(get("/api/product-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        int databaseSizeBeforeUpdate = productTagsRepository.findAll().size();

        // Update the productTags
        ProductTags updatedProductTags = productTagsRepository.findById(productTags.getId()).get();
        // Disconnect from session so that the updates on updatedProductTags are not directly saved in db
        em.detach(updatedProductTags);
        updatedProductTags
            .name(UPDATED_NAME);
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(updatedProductTags);

        restProductTagsMockMvc.perform(put("/api/product-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeUpdate);
        ProductTags testProductTags = productTagsList.get(productTagsList.size() - 1);
        assertThat(testProductTags.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductTags() throws Exception {
        int databaseSizeBeforeUpdate = productTagsRepository.findAll().size();

        // Create the ProductTags
        ProductTagsDTO productTagsDTO = productTagsMapper.toDto(productTags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTagsMockMvc.perform(put("/api/product-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductTags in the database
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductTags() throws Exception {
        // Initialize the database
        productTagsRepository.saveAndFlush(productTags);

        int databaseSizeBeforeDelete = productTagsRepository.findAll().size();

        // Delete the productTags
        restProductTagsMockMvc.perform(delete("/api/product-tags/{id}", productTags.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTags> productTagsList = productTagsRepository.findAll();
        assertThat(productTagsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
