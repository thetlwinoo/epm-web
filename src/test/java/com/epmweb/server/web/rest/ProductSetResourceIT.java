package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ProductSet;
import com.epmweb.server.repository.ProductSetRepository;
import com.epmweb.server.service.ProductSetService;
import com.epmweb.server.service.dto.ProductSetDTO;
import com.epmweb.server.service.mapper.ProductSetMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ProductSetCriteria;
import com.epmweb.server.service.ProductSetQueryService;

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
 * Integration tests for the {@link ProductSetResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_PERSON = 1;
    private static final Integer UPDATED_NO_OF_PERSON = 2;
    private static final Integer SMALLER_NO_OF_PERSON = 1 - 1;

    private static final Boolean DEFAULT_IS_EXCLUSIVE = false;
    private static final Boolean UPDATED_IS_EXCLUSIVE = true;

    @Autowired
    private ProductSetRepository productSetRepository;

    @Autowired
    private ProductSetMapper productSetMapper;

    @Autowired
    private ProductSetService productSetService;

    @Autowired
    private ProductSetQueryService productSetQueryService;

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

    private MockMvc restProductSetMockMvc;

    private ProductSet productSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductSetResource productSetResource = new ProductSetResource(productSetService, productSetQueryService);
        this.restProductSetMockMvc = MockMvcBuilders.standaloneSetup(productSetResource)
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
    public static ProductSet createEntity(EntityManager em) {
        ProductSet productSet = new ProductSet()
            .name(DEFAULT_NAME)
            .noOfPerson(DEFAULT_NO_OF_PERSON)
            .isExclusive(DEFAULT_IS_EXCLUSIVE);
        return productSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSet createUpdatedEntity(EntityManager em) {
        ProductSet productSet = new ProductSet()
            .name(UPDATED_NAME)
            .noOfPerson(UPDATED_NO_OF_PERSON)
            .isExclusive(UPDATED_IS_EXCLUSIVE);
        return productSet;
    }

    @BeforeEach
    public void initTest() {
        productSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSet() throws Exception {
        int databaseSizeBeforeCreate = productSetRepository.findAll().size();

        // Create the ProductSet
        ProductSetDTO productSetDTO = productSetMapper.toDto(productSet);
        restProductSetMockMvc.perform(post("/api/product-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSet in the database
        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSet testProductSet = productSetList.get(productSetList.size() - 1);
        assertThat(testProductSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductSet.getNoOfPerson()).isEqualTo(DEFAULT_NO_OF_PERSON);
        assertThat(testProductSet.isIsExclusive()).isEqualTo(DEFAULT_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void createProductSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSetRepository.findAll().size();

        // Create the ProductSet with an existing ID
        productSet.setId(1L);
        ProductSetDTO productSetDTO = productSetMapper.toDto(productSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSetMockMvc.perform(post("/api/product-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSet in the database
        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetRepository.findAll().size();
        // set the field null
        productSet.setName(null);

        // Create the ProductSet, which fails.
        ProductSetDTO productSetDTO = productSetMapper.toDto(productSet);

        restProductSetMockMvc.perform(post("/api/product-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoOfPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetRepository.findAll().size();
        // set the field null
        productSet.setNoOfPerson(null);

        // Create the ProductSet, which fails.
        ProductSetDTO productSetDTO = productSetMapper.toDto(productSet);

        restProductSetMockMvc.perform(post("/api/product-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSets() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList
        restProductSetMockMvc.perform(get("/api/product-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].noOfPerson").value(hasItem(DEFAULT_NO_OF_PERSON)))
            .andExpect(jsonPath("$.[*].isExclusive").value(hasItem(DEFAULT_IS_EXCLUSIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductSet() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get the productSet
        restProductSetMockMvc.perform(get("/api/product-sets/{id}", productSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.noOfPerson").value(DEFAULT_NO_OF_PERSON))
            .andExpect(jsonPath("$.isExclusive").value(DEFAULT_IS_EXCLUSIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getProductSetsByIdFiltering() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        Long id = productSet.getId();

        defaultProductSetShouldBeFound("id.equals=" + id);
        defaultProductSetShouldNotBeFound("id.notEquals=" + id);

        defaultProductSetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductSetShouldNotBeFound("id.greaterThan=" + id);

        defaultProductSetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductSetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductSetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where name equals to DEFAULT_NAME
        defaultProductSetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productSetList where name equals to UPDATED_NAME
        defaultProductSetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where name not equals to DEFAULT_NAME
        defaultProductSetShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productSetList where name not equals to UPDATED_NAME
        defaultProductSetShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductSetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productSetList where name equals to UPDATED_NAME
        defaultProductSetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where name is not null
        defaultProductSetShouldBeFound("name.specified=true");

        // Get all the productSetList where name is null
        defaultProductSetShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductSetsByNameContainsSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where name contains DEFAULT_NAME
        defaultProductSetShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productSetList where name contains UPDATED_NAME
        defaultProductSetShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where name does not contain DEFAULT_NAME
        defaultProductSetShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productSetList where name does not contain UPDATED_NAME
        defaultProductSetShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson equals to DEFAULT_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.equals=" + DEFAULT_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson equals to UPDATED_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.equals=" + UPDATED_NO_OF_PERSON);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson not equals to DEFAULT_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.notEquals=" + DEFAULT_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson not equals to UPDATED_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.notEquals=" + UPDATED_NO_OF_PERSON);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsInShouldWork() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson in DEFAULT_NO_OF_PERSON or UPDATED_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.in=" + DEFAULT_NO_OF_PERSON + "," + UPDATED_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson equals to UPDATED_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.in=" + UPDATED_NO_OF_PERSON);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson is not null
        defaultProductSetShouldBeFound("noOfPerson.specified=true");

        // Get all the productSetList where noOfPerson is null
        defaultProductSetShouldNotBeFound("noOfPerson.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson is greater than or equal to DEFAULT_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.greaterThanOrEqual=" + DEFAULT_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson is greater than or equal to UPDATED_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.greaterThanOrEqual=" + UPDATED_NO_OF_PERSON);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson is less than or equal to DEFAULT_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.lessThanOrEqual=" + DEFAULT_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson is less than or equal to SMALLER_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.lessThanOrEqual=" + SMALLER_NO_OF_PERSON);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson is less than DEFAULT_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.lessThan=" + DEFAULT_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson is less than UPDATED_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.lessThan=" + UPDATED_NO_OF_PERSON);
    }

    @Test
    @Transactional
    public void getAllProductSetsByNoOfPersonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where noOfPerson is greater than DEFAULT_NO_OF_PERSON
        defaultProductSetShouldNotBeFound("noOfPerson.greaterThan=" + DEFAULT_NO_OF_PERSON);

        // Get all the productSetList where noOfPerson is greater than SMALLER_NO_OF_PERSON
        defaultProductSetShouldBeFound("noOfPerson.greaterThan=" + SMALLER_NO_OF_PERSON);
    }


    @Test
    @Transactional
    public void getAllProductSetsByIsExclusiveIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where isExclusive equals to DEFAULT_IS_EXCLUSIVE
        defaultProductSetShouldBeFound("isExclusive.equals=" + DEFAULT_IS_EXCLUSIVE);

        // Get all the productSetList where isExclusive equals to UPDATED_IS_EXCLUSIVE
        defaultProductSetShouldNotBeFound("isExclusive.equals=" + UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void getAllProductSetsByIsExclusiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where isExclusive not equals to DEFAULT_IS_EXCLUSIVE
        defaultProductSetShouldNotBeFound("isExclusive.notEquals=" + DEFAULT_IS_EXCLUSIVE);

        // Get all the productSetList where isExclusive not equals to UPDATED_IS_EXCLUSIVE
        defaultProductSetShouldBeFound("isExclusive.notEquals=" + UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void getAllProductSetsByIsExclusiveIsInShouldWork() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where isExclusive in DEFAULT_IS_EXCLUSIVE or UPDATED_IS_EXCLUSIVE
        defaultProductSetShouldBeFound("isExclusive.in=" + DEFAULT_IS_EXCLUSIVE + "," + UPDATED_IS_EXCLUSIVE);

        // Get all the productSetList where isExclusive equals to UPDATED_IS_EXCLUSIVE
        defaultProductSetShouldNotBeFound("isExclusive.in=" + UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void getAllProductSetsByIsExclusiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        // Get all the productSetList where isExclusive is not null
        defaultProductSetShouldBeFound("isExclusive.specified=true");

        // Get all the productSetList where isExclusive is null
        defaultProductSetShouldNotBeFound("isExclusive.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductSetShouldBeFound(String filter) throws Exception {
        restProductSetMockMvc.perform(get("/api/product-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].noOfPerson").value(hasItem(DEFAULT_NO_OF_PERSON)))
            .andExpect(jsonPath("$.[*].isExclusive").value(hasItem(DEFAULT_IS_EXCLUSIVE.booleanValue())));

        // Check, that the count call also returns 1
        restProductSetMockMvc.perform(get("/api/product-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductSetShouldNotBeFound(String filter) throws Exception {
        restProductSetMockMvc.perform(get("/api/product-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductSetMockMvc.perform(get("/api/product-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductSet() throws Exception {
        // Get the productSet
        restProductSetMockMvc.perform(get("/api/product-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSet() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        int databaseSizeBeforeUpdate = productSetRepository.findAll().size();

        // Update the productSet
        ProductSet updatedProductSet = productSetRepository.findById(productSet.getId()).get();
        // Disconnect from session so that the updates on updatedProductSet are not directly saved in db
        em.detach(updatedProductSet);
        updatedProductSet
            .name(UPDATED_NAME)
            .noOfPerson(UPDATED_NO_OF_PERSON)
            .isExclusive(UPDATED_IS_EXCLUSIVE);
        ProductSetDTO productSetDTO = productSetMapper.toDto(updatedProductSet);

        restProductSetMockMvc.perform(put("/api/product-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSet in the database
        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeUpdate);
        ProductSet testProductSet = productSetList.get(productSetList.size() - 1);
        assertThat(testProductSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductSet.getNoOfPerson()).isEqualTo(UPDATED_NO_OF_PERSON);
        assertThat(testProductSet.isIsExclusive()).isEqualTo(UPDATED_IS_EXCLUSIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSet() throws Exception {
        int databaseSizeBeforeUpdate = productSetRepository.findAll().size();

        // Create the ProductSet
        ProductSetDTO productSetDTO = productSetMapper.toDto(productSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSetMockMvc.perform(put("/api/product-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSet in the database
        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSet() throws Exception {
        // Initialize the database
        productSetRepository.saveAndFlush(productSet);

        int databaseSizeBeforeDelete = productSetRepository.findAll().size();

        // Delete the productSet
        restProductSetMockMvc.perform(delete("/api/product-sets/{id}", productSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSet> productSetList = productSetRepository.findAll();
        assertThat(productSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
