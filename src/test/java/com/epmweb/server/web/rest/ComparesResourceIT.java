package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Compares;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.CompareProducts;
import com.epmweb.server.repository.ComparesRepository;
import com.epmweb.server.service.ComparesService;
import com.epmweb.server.service.dto.ComparesDTO;
import com.epmweb.server.service.mapper.ComparesMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ComparesCriteria;
import com.epmweb.server.service.ComparesQueryService;

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
 * Integration tests for the {@link ComparesResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ComparesResourceIT {

    @Autowired
    private ComparesRepository comparesRepository;

    @Autowired
    private ComparesMapper comparesMapper;

    @Autowired
    private ComparesService comparesService;

    @Autowired
    private ComparesQueryService comparesQueryService;

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

    private MockMvc restComparesMockMvc;

    private Compares compares;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComparesResource comparesResource = new ComparesResource(comparesService, comparesQueryService);
        this.restComparesMockMvc = MockMvcBuilders.standaloneSetup(comparesResource)
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
    public static Compares createEntity(EntityManager em) {
        Compares compares = new Compares();
        return compares;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compares createUpdatedEntity(EntityManager em) {
        Compares compares = new Compares();
        return compares;
    }

    @BeforeEach
    public void initTest() {
        compares = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompares() throws Exception {
        int databaseSizeBeforeCreate = comparesRepository.findAll().size();

        // Create the Compares
        ComparesDTO comparesDTO = comparesMapper.toDto(compares);
        restComparesMockMvc.perform(post("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isCreated());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeCreate + 1);
        Compares testCompares = comparesList.get(comparesList.size() - 1);
    }

    @Test
    @Transactional
    public void createComparesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comparesRepository.findAll().size();

        // Create the Compares with an existing ID
        compares.setId(1L);
        ComparesDTO comparesDTO = comparesMapper.toDto(compares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComparesMockMvc.perform(post("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        // Get all the comparesList
        restComparesMockMvc.perform(get("/api/compares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compares.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        // Get the compares
        restComparesMockMvc.perform(get("/api/compares/{id}", compares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compares.getId().intValue()));
    }


    @Test
    @Transactional
    public void getComparesByIdFiltering() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        Long id = compares.getId();

        defaultComparesShouldBeFound("id.equals=" + id);
        defaultComparesShouldNotBeFound("id.notEquals=" + id);

        defaultComparesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComparesShouldNotBeFound("id.greaterThan=" + id);

        defaultComparesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComparesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComparesByCompareUserIsEqualToSomething() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);
        People compareUser = PeopleResourceIT.createEntity(em);
        em.persist(compareUser);
        em.flush();
        compares.setCompareUser(compareUser);
        comparesRepository.saveAndFlush(compares);
        Long compareUserId = compareUser.getId();

        // Get all the comparesList where compareUser equals to compareUserId
        defaultComparesShouldBeFound("compareUserId.equals=" + compareUserId);

        // Get all the comparesList where compareUser equals to compareUserId + 1
        defaultComparesShouldNotBeFound("compareUserId.equals=" + (compareUserId + 1));
    }


    @Test
    @Transactional
    public void getAllComparesByCompareListIsEqualToSomething() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);
        CompareProducts compareList = CompareProductsResourceIT.createEntity(em);
        em.persist(compareList);
        em.flush();
        compares.addCompareList(compareList);
        comparesRepository.saveAndFlush(compares);
        Long compareListId = compareList.getId();

        // Get all the comparesList where compareList equals to compareListId
        defaultComparesShouldBeFound("compareListId.equals=" + compareListId);

        // Get all the comparesList where compareList equals to compareListId + 1
        defaultComparesShouldNotBeFound("compareListId.equals=" + (compareListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComparesShouldBeFound(String filter) throws Exception {
        restComparesMockMvc.perform(get("/api/compares?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compares.getId().intValue())));

        // Check, that the count call also returns 1
        restComparesMockMvc.perform(get("/api/compares/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComparesShouldNotBeFound(String filter) throws Exception {
        restComparesMockMvc.perform(get("/api/compares?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComparesMockMvc.perform(get("/api/compares/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompares() throws Exception {
        // Get the compares
        restComparesMockMvc.perform(get("/api/compares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        int databaseSizeBeforeUpdate = comparesRepository.findAll().size();

        // Update the compares
        Compares updatedCompares = comparesRepository.findById(compares.getId()).get();
        // Disconnect from session so that the updates on updatedCompares are not directly saved in db
        em.detach(updatedCompares);
        ComparesDTO comparesDTO = comparesMapper.toDto(updatedCompares);

        restComparesMockMvc.perform(put("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isOk());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeUpdate);
        Compares testCompares = comparesList.get(comparesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCompares() throws Exception {
        int databaseSizeBeforeUpdate = comparesRepository.findAll().size();

        // Create the Compares
        ComparesDTO comparesDTO = comparesMapper.toDto(compares);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComparesMockMvc.perform(put("/api/compares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comparesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compares in the database
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompares() throws Exception {
        // Initialize the database
        comparesRepository.saveAndFlush(compares);

        int databaseSizeBeforeDelete = comparesRepository.findAll().size();

        // Delete the compares
        restComparesMockMvc.perform(delete("/api/compares/{id}", compares.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compares> comparesList = comparesRepository.findAll();
        assertThat(comparesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
