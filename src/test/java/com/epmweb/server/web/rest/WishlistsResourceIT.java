package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.Wishlists;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.WishlistProducts;
import com.epmweb.server.repository.WishlistsRepository;
import com.epmweb.server.service.WishlistsService;
import com.epmweb.server.service.dto.WishlistsDTO;
import com.epmweb.server.service.mapper.WishlistsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.WishlistsCriteria;
import com.epmweb.server.service.WishlistsQueryService;

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
 * Integration tests for the {@link WishlistsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class WishlistsResourceIT {

    @Autowired
    private WishlistsRepository wishlistsRepository;

    @Autowired
    private WishlistsMapper wishlistsMapper;

    @Autowired
    private WishlistsService wishlistsService;

    @Autowired
    private WishlistsQueryService wishlistsQueryService;

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

    private MockMvc restWishlistsMockMvc;

    private Wishlists wishlists;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WishlistsResource wishlistsResource = new WishlistsResource(wishlistsService, wishlistsQueryService);
        this.restWishlistsMockMvc = MockMvcBuilders.standaloneSetup(wishlistsResource)
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
    public static Wishlists createEntity(EntityManager em) {
        Wishlists wishlists = new Wishlists();
        return wishlists;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wishlists createUpdatedEntity(EntityManager em) {
        Wishlists wishlists = new Wishlists();
        return wishlists;
    }

    @BeforeEach
    public void initTest() {
        wishlists = createEntity(em);
    }

    @Test
    @Transactional
    public void createWishlists() throws Exception {
        int databaseSizeBeforeCreate = wishlistsRepository.findAll().size();

        // Create the Wishlists
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(wishlists);
        restWishlistsMockMvc.perform(post("/api/wishlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isCreated());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeCreate + 1);
        Wishlists testWishlists = wishlistsList.get(wishlistsList.size() - 1);
    }

    @Test
    @Transactional
    public void createWishlistsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wishlistsRepository.findAll().size();

        // Create the Wishlists with an existing ID
        wishlists.setId(1L);
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(wishlists);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWishlistsMockMvc.perform(post("/api/wishlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        // Get all the wishlistsList
        restWishlistsMockMvc.perform(get("/api/wishlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlists.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        // Get the wishlists
        restWishlistsMockMvc.perform(get("/api/wishlists/{id}", wishlists.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wishlists.getId().intValue()));
    }


    @Test
    @Transactional
    public void getWishlistsByIdFiltering() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        Long id = wishlists.getId();

        defaultWishlistsShouldBeFound("id.equals=" + id);
        defaultWishlistsShouldNotBeFound("id.notEquals=" + id);

        defaultWishlistsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWishlistsShouldNotBeFound("id.greaterThan=" + id);

        defaultWishlistsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWishlistsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWishlistsByWishlistUserIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);
        People wishlistUser = PeopleResourceIT.createEntity(em);
        em.persist(wishlistUser);
        em.flush();
        wishlists.setWishlistUser(wishlistUser);
        wishlistsRepository.saveAndFlush(wishlists);
        Long wishlistUserId = wishlistUser.getId();

        // Get all the wishlistsList where wishlistUser equals to wishlistUserId
        defaultWishlistsShouldBeFound("wishlistUserId.equals=" + wishlistUserId);

        // Get all the wishlistsList where wishlistUser equals to wishlistUserId + 1
        defaultWishlistsShouldNotBeFound("wishlistUserId.equals=" + (wishlistUserId + 1));
    }


    @Test
    @Transactional
    public void getAllWishlistsByWishlistListIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);
        WishlistProducts wishlistList = WishlistProductsResourceIT.createEntity(em);
        em.persist(wishlistList);
        em.flush();
        wishlists.addWishlistList(wishlistList);
        wishlistsRepository.saveAndFlush(wishlists);
        Long wishlistListId = wishlistList.getId();

        // Get all the wishlistsList where wishlistList equals to wishlistListId
        defaultWishlistsShouldBeFound("wishlistListId.equals=" + wishlistListId);

        // Get all the wishlistsList where wishlistList equals to wishlistListId + 1
        defaultWishlistsShouldNotBeFound("wishlistListId.equals=" + (wishlistListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWishlistsShouldBeFound(String filter) throws Exception {
        restWishlistsMockMvc.perform(get("/api/wishlists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlists.getId().intValue())));

        // Check, that the count call also returns 1
        restWishlistsMockMvc.perform(get("/api/wishlists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWishlistsShouldNotBeFound(String filter) throws Exception {
        restWishlistsMockMvc.perform(get("/api/wishlists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWishlistsMockMvc.perform(get("/api/wishlists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWishlists() throws Exception {
        // Get the wishlists
        restWishlistsMockMvc.perform(get("/api/wishlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        int databaseSizeBeforeUpdate = wishlistsRepository.findAll().size();

        // Update the wishlists
        Wishlists updatedWishlists = wishlistsRepository.findById(wishlists.getId()).get();
        // Disconnect from session so that the updates on updatedWishlists are not directly saved in db
        em.detach(updatedWishlists);
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(updatedWishlists);

        restWishlistsMockMvc.perform(put("/api/wishlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isOk());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeUpdate);
        Wishlists testWishlists = wishlistsList.get(wishlistsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWishlists() throws Exception {
        int databaseSizeBeforeUpdate = wishlistsRepository.findAll().size();

        // Create the Wishlists
        WishlistsDTO wishlistsDTO = wishlistsMapper.toDto(wishlists);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWishlistsMockMvc.perform(put("/api/wishlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wishlists in the database
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWishlists() throws Exception {
        // Initialize the database
        wishlistsRepository.saveAndFlush(wishlists);

        int databaseSizeBeforeDelete = wishlistsRepository.findAll().size();

        // Delete the wishlists
        restWishlistsMockMvc.perform(delete("/api/wishlists/{id}", wishlists.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wishlists> wishlistsList = wishlistsRepository.findAll();
        assertThat(wishlistsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
