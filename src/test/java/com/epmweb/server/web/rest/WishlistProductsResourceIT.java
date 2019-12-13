package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.WishlistProducts;
import com.epmweb.server.domain.Products;
import com.epmweb.server.domain.Wishlists;
import com.epmweb.server.repository.WishlistProductsRepository;
import com.epmweb.server.service.WishlistProductsService;
import com.epmweb.server.service.dto.WishlistProductsDTO;
import com.epmweb.server.service.mapper.WishlistProductsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.WishlistProductsCriteria;
import com.epmweb.server.service.WishlistProductsQueryService;

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
 * Integration tests for the {@link WishlistProductsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class WishlistProductsResourceIT {

    @Autowired
    private WishlistProductsRepository wishlistProductsRepository;

    @Autowired
    private WishlistProductsMapper wishlistProductsMapper;

    @Autowired
    private WishlistProductsService wishlistProductsService;

    @Autowired
    private WishlistProductsQueryService wishlistProductsQueryService;

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

    private MockMvc restWishlistProductsMockMvc;

    private WishlistProducts wishlistProducts;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WishlistProductsResource wishlistProductsResource = new WishlistProductsResource(wishlistProductsService, wishlistProductsQueryService);
        this.restWishlistProductsMockMvc = MockMvcBuilders.standaloneSetup(wishlistProductsResource)
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
    public static WishlistProducts createEntity(EntityManager em) {
        WishlistProducts wishlistProducts = new WishlistProducts();
        return wishlistProducts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WishlistProducts createUpdatedEntity(EntityManager em) {
        WishlistProducts wishlistProducts = new WishlistProducts();
        return wishlistProducts;
    }

    @BeforeEach
    public void initTest() {
        wishlistProducts = createEntity(em);
    }

    @Test
    @Transactional
    public void createWishlistProducts() throws Exception {
        int databaseSizeBeforeCreate = wishlistProductsRepository.findAll().size();

        // Create the WishlistProducts
        WishlistProductsDTO wishlistProductsDTO = wishlistProductsMapper.toDto(wishlistProducts);
        restWishlistProductsMockMvc.perform(post("/api/wishlist-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistProductsDTO)))
            .andExpect(status().isCreated());

        // Validate the WishlistProducts in the database
        List<WishlistProducts> wishlistProductsList = wishlistProductsRepository.findAll();
        assertThat(wishlistProductsList).hasSize(databaseSizeBeforeCreate + 1);
        WishlistProducts testWishlistProducts = wishlistProductsList.get(wishlistProductsList.size() - 1);
    }

    @Test
    @Transactional
    public void createWishlistProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wishlistProductsRepository.findAll().size();

        // Create the WishlistProducts with an existing ID
        wishlistProducts.setId(1L);
        WishlistProductsDTO wishlistProductsDTO = wishlistProductsMapper.toDto(wishlistProducts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWishlistProductsMockMvc.perform(post("/api/wishlist-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistProductsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WishlistProducts in the database
        List<WishlistProducts> wishlistProductsList = wishlistProductsRepository.findAll();
        assertThat(wishlistProductsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWishlistProducts() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);

        // Get all the wishlistProductsList
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlistProducts.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getWishlistProducts() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);

        // Get the wishlistProducts
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products/{id}", wishlistProducts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wishlistProducts.getId().intValue()));
    }


    @Test
    @Transactional
    public void getWishlistProductsByIdFiltering() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);

        Long id = wishlistProducts.getId();

        defaultWishlistProductsShouldBeFound("id.equals=" + id);
        defaultWishlistProductsShouldNotBeFound("id.notEquals=" + id);

        defaultWishlistProductsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWishlistProductsShouldNotBeFound("id.greaterThan=" + id);

        defaultWishlistProductsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWishlistProductsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWishlistProductsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        wishlistProducts.setProduct(product);
        wishlistProductsRepository.saveAndFlush(wishlistProducts);
        Long productId = product.getId();

        // Get all the wishlistProductsList where product equals to productId
        defaultWishlistProductsShouldBeFound("productId.equals=" + productId);

        // Get all the wishlistProductsList where product equals to productId + 1
        defaultWishlistProductsShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllWishlistProductsByWishlistIsEqualToSomething() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);
        Wishlists wishlist = WishlistsResourceIT.createEntity(em);
        em.persist(wishlist);
        em.flush();
        wishlistProducts.setWishlist(wishlist);
        wishlistProductsRepository.saveAndFlush(wishlistProducts);
        Long wishlistId = wishlist.getId();

        // Get all the wishlistProductsList where wishlist equals to wishlistId
        defaultWishlistProductsShouldBeFound("wishlistId.equals=" + wishlistId);

        // Get all the wishlistProductsList where wishlist equals to wishlistId + 1
        defaultWishlistProductsShouldNotBeFound("wishlistId.equals=" + (wishlistId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWishlistProductsShouldBeFound(String filter) throws Exception {
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishlistProducts.getId().intValue())));

        // Check, that the count call also returns 1
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWishlistProductsShouldNotBeFound(String filter) throws Exception {
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWishlistProducts() throws Exception {
        // Get the wishlistProducts
        restWishlistProductsMockMvc.perform(get("/api/wishlist-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWishlistProducts() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);

        int databaseSizeBeforeUpdate = wishlistProductsRepository.findAll().size();

        // Update the wishlistProducts
        WishlistProducts updatedWishlistProducts = wishlistProductsRepository.findById(wishlistProducts.getId()).get();
        // Disconnect from session so that the updates on updatedWishlistProducts are not directly saved in db
        em.detach(updatedWishlistProducts);
        WishlistProductsDTO wishlistProductsDTO = wishlistProductsMapper.toDto(updatedWishlistProducts);

        restWishlistProductsMockMvc.perform(put("/api/wishlist-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistProductsDTO)))
            .andExpect(status().isOk());

        // Validate the WishlistProducts in the database
        List<WishlistProducts> wishlistProductsList = wishlistProductsRepository.findAll();
        assertThat(wishlistProductsList).hasSize(databaseSizeBeforeUpdate);
        WishlistProducts testWishlistProducts = wishlistProductsList.get(wishlistProductsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWishlistProducts() throws Exception {
        int databaseSizeBeforeUpdate = wishlistProductsRepository.findAll().size();

        // Create the WishlistProducts
        WishlistProductsDTO wishlistProductsDTO = wishlistProductsMapper.toDto(wishlistProducts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWishlistProductsMockMvc.perform(put("/api/wishlist-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wishlistProductsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WishlistProducts in the database
        List<WishlistProducts> wishlistProductsList = wishlistProductsRepository.findAll();
        assertThat(wishlistProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWishlistProducts() throws Exception {
        // Initialize the database
        wishlistProductsRepository.saveAndFlush(wishlistProducts);

        int databaseSizeBeforeDelete = wishlistProductsRepository.findAll().size();

        // Delete the wishlistProducts
        restWishlistProductsMockMvc.perform(delete("/api/wishlist-products/{id}", wishlistProducts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WishlistProducts> wishlistProductsList = wishlistProductsRepository.findAll();
        assertThat(wishlistProductsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
