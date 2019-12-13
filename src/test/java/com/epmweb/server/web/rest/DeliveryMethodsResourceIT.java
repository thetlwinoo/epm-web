package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.DeliveryMethods;
import com.epmweb.server.repository.DeliveryMethodsRepository;
import com.epmweb.server.service.DeliveryMethodsService;
import com.epmweb.server.service.dto.DeliveryMethodsDTO;
import com.epmweb.server.service.mapper.DeliveryMethodsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.DeliveryMethodsCriteria;
import com.epmweb.server.service.DeliveryMethodsQueryService;

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
 * Integration tests for the {@link DeliveryMethodsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class DeliveryMethodsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeliveryMethodsRepository deliveryMethodsRepository;

    @Autowired
    private DeliveryMethodsMapper deliveryMethodsMapper;

    @Autowired
    private DeliveryMethodsService deliveryMethodsService;

    @Autowired
    private DeliveryMethodsQueryService deliveryMethodsQueryService;

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

    private MockMvc restDeliveryMethodsMockMvc;

    private DeliveryMethods deliveryMethods;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryMethodsResource deliveryMethodsResource = new DeliveryMethodsResource(deliveryMethodsService, deliveryMethodsQueryService);
        this.restDeliveryMethodsMockMvc = MockMvcBuilders.standaloneSetup(deliveryMethodsResource)
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
    public static DeliveryMethods createEntity(EntityManager em) {
        DeliveryMethods deliveryMethods = new DeliveryMethods()
            .name(DEFAULT_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return deliveryMethods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryMethods createUpdatedEntity(EntityManager em) {
        DeliveryMethods deliveryMethods = new DeliveryMethods()
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return deliveryMethods;
    }

    @BeforeEach
    public void initTest() {
        deliveryMethods = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryMethods() throws Exception {
        int databaseSizeBeforeCreate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);
        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryMethods testDeliveryMethods = deliveryMethodsList.get(deliveryMethodsList.size() - 1);
        assertThat(testDeliveryMethods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliveryMethods.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testDeliveryMethods.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createDeliveryMethodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods with an existing ID
        deliveryMethods.setId(1L);
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setName(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setValidFrom(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setValidTo(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get the deliveryMethods
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/{id}", deliveryMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryMethods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getDeliveryMethodsByIdFiltering() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        Long id = deliveryMethods.getId();

        defaultDeliveryMethodsShouldBeFound("id.equals=" + id);
        defaultDeliveryMethodsShouldNotBeFound("id.notEquals=" + id);

        defaultDeliveryMethodsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeliveryMethodsShouldNotBeFound("id.greaterThan=" + id);

        defaultDeliveryMethodsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeliveryMethodsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name equals to DEFAULT_NAME
        defaultDeliveryMethodsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name equals to UPDATED_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name not equals to DEFAULT_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name not equals to UPDATED_NAME
        defaultDeliveryMethodsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeliveryMethodsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deliveryMethodsList where name equals to UPDATED_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name is not null
        defaultDeliveryMethodsShouldBeFound("name.specified=true");

        // Get all the deliveryMethodsList where name is null
        defaultDeliveryMethodsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeliveryMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name contains DEFAULT_NAME
        defaultDeliveryMethodsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name contains UPDATED_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name does not contain DEFAULT_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name does not contain UPDATED_NAME
        defaultDeliveryMethodsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom equals to DEFAULT_VALID_FROM
        defaultDeliveryMethodsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the deliveryMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultDeliveryMethodsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the deliveryMethodsList where validFrom not equals to UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the deliveryMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom is not null
        defaultDeliveryMethodsShouldBeFound("validFrom.specified=true");

        // Get all the deliveryMethodsList where validFrom is null
        defaultDeliveryMethodsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo equals to DEFAULT_VALID_TO
        defaultDeliveryMethodsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the deliveryMethodsList where validTo equals to UPDATED_VALID_TO
        defaultDeliveryMethodsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo not equals to DEFAULT_VALID_TO
        defaultDeliveryMethodsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the deliveryMethodsList where validTo not equals to UPDATED_VALID_TO
        defaultDeliveryMethodsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultDeliveryMethodsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the deliveryMethodsList where validTo equals to UPDATED_VALID_TO
        defaultDeliveryMethodsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo is not null
        defaultDeliveryMethodsShouldBeFound("validTo.specified=true");

        // Get all the deliveryMethodsList where validTo is null
        defaultDeliveryMethodsShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeliveryMethodsShouldBeFound(String filter) throws Exception {
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeliveryMethodsShouldNotBeFound(String filter) throws Exception {
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDeliveryMethods() throws Exception {
        // Get the deliveryMethods
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        int databaseSizeBeforeUpdate = deliveryMethodsRepository.findAll().size();

        // Update the deliveryMethods
        DeliveryMethods updatedDeliveryMethods = deliveryMethodsRepository.findById(deliveryMethods.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryMethods are not directly saved in db
        em.detach(updatedDeliveryMethods);
        updatedDeliveryMethods
            .name(UPDATED_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(updatedDeliveryMethods);

        restDeliveryMethodsMockMvc.perform(put("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryMethods testDeliveryMethods = deliveryMethodsList.get(deliveryMethodsList.size() - 1);
        assertThat(testDeliveryMethods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliveryMethods.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testDeliveryMethods.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryMethods() throws Exception {
        int databaseSizeBeforeUpdate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMethodsMockMvc.perform(put("/api/delivery-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        int databaseSizeBeforeDelete = deliveryMethodsRepository.findAll().size();

        // Delete the deliveryMethods
        restDeliveryMethodsMockMvc.perform(delete("/api/delivery-methods/{id}", deliveryMethods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
