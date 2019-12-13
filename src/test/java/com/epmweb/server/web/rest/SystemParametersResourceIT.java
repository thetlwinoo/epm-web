package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.SystemParameters;
import com.epmweb.server.domain.Cities;
import com.epmweb.server.repository.SystemParametersRepository;
import com.epmweb.server.service.SystemParametersService;
import com.epmweb.server.service.dto.SystemParametersDTO;
import com.epmweb.server.service.mapper.SystemParametersMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.SystemParametersCriteria;
import com.epmweb.server.service.SystemParametersQueryService;

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
 * Integration tests for the {@link SystemParametersResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class SystemParametersResourceIT {

    private static final String DEFAULT_APPLICATION_SETTINGS = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_SETTINGS = "BBBBBBBBBB";

    @Autowired
    private SystemParametersRepository systemParametersRepository;

    @Autowired
    private SystemParametersMapper systemParametersMapper;

    @Autowired
    private SystemParametersService systemParametersService;

    @Autowired
    private SystemParametersQueryService systemParametersQueryService;

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

    private MockMvc restSystemParametersMockMvc;

    private SystemParameters systemParameters;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemParametersResource systemParametersResource = new SystemParametersResource(systemParametersService, systemParametersQueryService);
        this.restSystemParametersMockMvc = MockMvcBuilders.standaloneSetup(systemParametersResource)
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
    public static SystemParameters createEntity(EntityManager em) {
        SystemParameters systemParameters = new SystemParameters()
            .applicationSettings(DEFAULT_APPLICATION_SETTINGS);
        return systemParameters;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemParameters createUpdatedEntity(EntityManager em) {
        SystemParameters systemParameters = new SystemParameters()
            .applicationSettings(UPDATED_APPLICATION_SETTINGS);
        return systemParameters;
    }

    @BeforeEach
    public void initTest() {
        systemParameters = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemParameters() throws Exception {
        int databaseSizeBeforeCreate = systemParametersRepository.findAll().size();

        // Create the SystemParameters
        SystemParametersDTO systemParametersDTO = systemParametersMapper.toDto(systemParameters);
        restSystemParametersMockMvc.perform(post("/api/system-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemParametersDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemParameters in the database
        List<SystemParameters> systemParametersList = systemParametersRepository.findAll();
        assertThat(systemParametersList).hasSize(databaseSizeBeforeCreate + 1);
        SystemParameters testSystemParameters = systemParametersList.get(systemParametersList.size() - 1);
        assertThat(testSystemParameters.getApplicationSettings()).isEqualTo(DEFAULT_APPLICATION_SETTINGS);
    }

    @Test
    @Transactional
    public void createSystemParametersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemParametersRepository.findAll().size();

        // Create the SystemParameters with an existing ID
        systemParameters.setId(1L);
        SystemParametersDTO systemParametersDTO = systemParametersMapper.toDto(systemParameters);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemParametersMockMvc.perform(post("/api/system-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemParametersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemParameters in the database
        List<SystemParameters> systemParametersList = systemParametersRepository.findAll();
        assertThat(systemParametersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkApplicationSettingsIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemParametersRepository.findAll().size();
        // set the field null
        systemParameters.setApplicationSettings(null);

        // Create the SystemParameters, which fails.
        SystemParametersDTO systemParametersDTO = systemParametersMapper.toDto(systemParameters);

        restSystemParametersMockMvc.perform(post("/api/system-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemParametersDTO)))
            .andExpect(status().isBadRequest());

        List<SystemParameters> systemParametersList = systemParametersRepository.findAll();
        assertThat(systemParametersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemParameters() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList
        restSystemParametersMockMvc.perform(get("/api/system-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemParameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSettings").value(hasItem(DEFAULT_APPLICATION_SETTINGS)));
    }
    
    @Test
    @Transactional
    public void getSystemParameters() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get the systemParameters
        restSystemParametersMockMvc.perform(get("/api/system-parameters/{id}", systemParameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemParameters.getId().intValue()))
            .andExpect(jsonPath("$.applicationSettings").value(DEFAULT_APPLICATION_SETTINGS));
    }


    @Test
    @Transactional
    public void getSystemParametersByIdFiltering() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        Long id = systemParameters.getId();

        defaultSystemParametersShouldBeFound("id.equals=" + id);
        defaultSystemParametersShouldNotBeFound("id.notEquals=" + id);

        defaultSystemParametersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemParametersShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemParametersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemParametersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSystemParametersByApplicationSettingsIsEqualToSomething() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList where applicationSettings equals to DEFAULT_APPLICATION_SETTINGS
        defaultSystemParametersShouldBeFound("applicationSettings.equals=" + DEFAULT_APPLICATION_SETTINGS);

        // Get all the systemParametersList where applicationSettings equals to UPDATED_APPLICATION_SETTINGS
        defaultSystemParametersShouldNotBeFound("applicationSettings.equals=" + UPDATED_APPLICATION_SETTINGS);
    }

    @Test
    @Transactional
    public void getAllSystemParametersByApplicationSettingsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList where applicationSettings not equals to DEFAULT_APPLICATION_SETTINGS
        defaultSystemParametersShouldNotBeFound("applicationSettings.notEquals=" + DEFAULT_APPLICATION_SETTINGS);

        // Get all the systemParametersList where applicationSettings not equals to UPDATED_APPLICATION_SETTINGS
        defaultSystemParametersShouldBeFound("applicationSettings.notEquals=" + UPDATED_APPLICATION_SETTINGS);
    }

    @Test
    @Transactional
    public void getAllSystemParametersByApplicationSettingsIsInShouldWork() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList where applicationSettings in DEFAULT_APPLICATION_SETTINGS or UPDATED_APPLICATION_SETTINGS
        defaultSystemParametersShouldBeFound("applicationSettings.in=" + DEFAULT_APPLICATION_SETTINGS + "," + UPDATED_APPLICATION_SETTINGS);

        // Get all the systemParametersList where applicationSettings equals to UPDATED_APPLICATION_SETTINGS
        defaultSystemParametersShouldNotBeFound("applicationSettings.in=" + UPDATED_APPLICATION_SETTINGS);
    }

    @Test
    @Transactional
    public void getAllSystemParametersByApplicationSettingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList where applicationSettings is not null
        defaultSystemParametersShouldBeFound("applicationSettings.specified=true");

        // Get all the systemParametersList where applicationSettings is null
        defaultSystemParametersShouldNotBeFound("applicationSettings.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemParametersByApplicationSettingsContainsSomething() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList where applicationSettings contains DEFAULT_APPLICATION_SETTINGS
        defaultSystemParametersShouldBeFound("applicationSettings.contains=" + DEFAULT_APPLICATION_SETTINGS);

        // Get all the systemParametersList where applicationSettings contains UPDATED_APPLICATION_SETTINGS
        defaultSystemParametersShouldNotBeFound("applicationSettings.contains=" + UPDATED_APPLICATION_SETTINGS);
    }

    @Test
    @Transactional
    public void getAllSystemParametersByApplicationSettingsNotContainsSomething() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        // Get all the systemParametersList where applicationSettings does not contain DEFAULT_APPLICATION_SETTINGS
        defaultSystemParametersShouldNotBeFound("applicationSettings.doesNotContain=" + DEFAULT_APPLICATION_SETTINGS);

        // Get all the systemParametersList where applicationSettings does not contain UPDATED_APPLICATION_SETTINGS
        defaultSystemParametersShouldBeFound("applicationSettings.doesNotContain=" + UPDATED_APPLICATION_SETTINGS);
    }


    @Test
    @Transactional
    public void getAllSystemParametersByDeliveryCityIsEqualToSomething() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);
        Cities deliveryCity = CitiesResourceIT.createEntity(em);
        em.persist(deliveryCity);
        em.flush();
        systemParameters.setDeliveryCity(deliveryCity);
        systemParametersRepository.saveAndFlush(systemParameters);
        Long deliveryCityId = deliveryCity.getId();

        // Get all the systemParametersList where deliveryCity equals to deliveryCityId
        defaultSystemParametersShouldBeFound("deliveryCityId.equals=" + deliveryCityId);

        // Get all the systemParametersList where deliveryCity equals to deliveryCityId + 1
        defaultSystemParametersShouldNotBeFound("deliveryCityId.equals=" + (deliveryCityId + 1));
    }


    @Test
    @Transactional
    public void getAllSystemParametersByPostalCityIsEqualToSomething() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);
        Cities postalCity = CitiesResourceIT.createEntity(em);
        em.persist(postalCity);
        em.flush();
        systemParameters.setPostalCity(postalCity);
        systemParametersRepository.saveAndFlush(systemParameters);
        Long postalCityId = postalCity.getId();

        // Get all the systemParametersList where postalCity equals to postalCityId
        defaultSystemParametersShouldBeFound("postalCityId.equals=" + postalCityId);

        // Get all the systemParametersList where postalCity equals to postalCityId + 1
        defaultSystemParametersShouldNotBeFound("postalCityId.equals=" + (postalCityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemParametersShouldBeFound(String filter) throws Exception {
        restSystemParametersMockMvc.perform(get("/api/system-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemParameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationSettings").value(hasItem(DEFAULT_APPLICATION_SETTINGS)));

        // Check, that the count call also returns 1
        restSystemParametersMockMvc.perform(get("/api/system-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemParametersShouldNotBeFound(String filter) throws Exception {
        restSystemParametersMockMvc.perform(get("/api/system-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemParametersMockMvc.perform(get("/api/system-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSystemParameters() throws Exception {
        // Get the systemParameters
        restSystemParametersMockMvc.perform(get("/api/system-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemParameters() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        int databaseSizeBeforeUpdate = systemParametersRepository.findAll().size();

        // Update the systemParameters
        SystemParameters updatedSystemParameters = systemParametersRepository.findById(systemParameters.getId()).get();
        // Disconnect from session so that the updates on updatedSystemParameters are not directly saved in db
        em.detach(updatedSystemParameters);
        updatedSystemParameters
            .applicationSettings(UPDATED_APPLICATION_SETTINGS);
        SystemParametersDTO systemParametersDTO = systemParametersMapper.toDto(updatedSystemParameters);

        restSystemParametersMockMvc.perform(put("/api/system-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemParametersDTO)))
            .andExpect(status().isOk());

        // Validate the SystemParameters in the database
        List<SystemParameters> systemParametersList = systemParametersRepository.findAll();
        assertThat(systemParametersList).hasSize(databaseSizeBeforeUpdate);
        SystemParameters testSystemParameters = systemParametersList.get(systemParametersList.size() - 1);
        assertThat(testSystemParameters.getApplicationSettings()).isEqualTo(UPDATED_APPLICATION_SETTINGS);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemParameters() throws Exception {
        int databaseSizeBeforeUpdate = systemParametersRepository.findAll().size();

        // Create the SystemParameters
        SystemParametersDTO systemParametersDTO = systemParametersMapper.toDto(systemParameters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemParametersMockMvc.perform(put("/api/system-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemParametersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemParameters in the database
        List<SystemParameters> systemParametersList = systemParametersRepository.findAll();
        assertThat(systemParametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemParameters() throws Exception {
        // Initialize the database
        systemParametersRepository.saveAndFlush(systemParameters);

        int databaseSizeBeforeDelete = systemParametersRepository.findAll().size();

        // Delete the systemParameters
        restSystemParametersMockMvc.perform(delete("/api/system-parameters/{id}", systemParameters.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemParameters> systemParametersList = systemParametersRepository.findAll();
        assertThat(systemParametersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
