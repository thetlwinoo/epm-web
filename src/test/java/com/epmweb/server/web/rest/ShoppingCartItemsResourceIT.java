package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ShoppingCartItems;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.repository.ShoppingCartItemsRepository;
import com.epmweb.server.service.ShoppingCartItemsService;
import com.epmweb.server.service.dto.ShoppingCartItemsDTO;
import com.epmweb.server.service.mapper.ShoppingCartItemsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ShoppingCartItemsCriteria;
import com.epmweb.server.service.ShoppingCartItemsQueryService;

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
 * Integration tests for the {@link ShoppingCartItemsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ShoppingCartItemsResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ShoppingCartItemsRepository shoppingCartItemsRepository;

    @Autowired
    private ShoppingCartItemsMapper shoppingCartItemsMapper;

    @Autowired
    private ShoppingCartItemsService shoppingCartItemsService;

    @Autowired
    private ShoppingCartItemsQueryService shoppingCartItemsQueryService;

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

    private MockMvc restShoppingCartItemsMockMvc;

    private ShoppingCartItems shoppingCartItems;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartItemsResource shoppingCartItemsResource = new ShoppingCartItemsResource(shoppingCartItemsService, shoppingCartItemsQueryService);
        this.restShoppingCartItemsMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartItemsResource)
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
    public static ShoppingCartItems createEntity(EntityManager em) {
        ShoppingCartItems shoppingCartItems = new ShoppingCartItems()
            .quantity(DEFAULT_QUANTITY)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return shoppingCartItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCartItems createUpdatedEntity(EntityManager em) {
        ShoppingCartItems shoppingCartItems = new ShoppingCartItems()
            .quantity(UPDATED_QUANTITY)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return shoppingCartItems;
    }

    @BeforeEach
    public void initTest() {
        shoppingCartItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCartItems() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemsRepository.findAll().size();

        // Create the ShoppingCartItems
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(shoppingCartItems);
        restShoppingCartItemsMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCartItems testShoppingCartItems = shoppingCartItemsList.get(shoppingCartItemsList.size() - 1);
        assertThat(testShoppingCartItems.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testShoppingCartItems.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testShoppingCartItems.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createShoppingCartItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemsRepository.findAll().size();

        // Create the ShoppingCartItems with an existing ID
        shoppingCartItems.setId(1L);
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(shoppingCartItems);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartItemsMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get the shoppingCartItems
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items/{id}", shoppingCartItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCartItems.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getShoppingCartItemsByIdFiltering() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        Long id = shoppingCartItems.getId();

        defaultShoppingCartItemsShouldBeFound("id.equals=" + id);
        defaultShoppingCartItemsShouldNotBeFound("id.notEquals=" + id);

        defaultShoppingCartItemsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShoppingCartItemsShouldNotBeFound("id.greaterThan=" + id);

        defaultShoppingCartItemsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShoppingCartItemsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity equals to DEFAULT_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the shoppingCartItemsList where quantity equals to UPDATED_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity not equals to DEFAULT_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the shoppingCartItemsList where quantity not equals to UPDATED_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the shoppingCartItemsList where quantity equals to UPDATED_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity is not null
        defaultShoppingCartItemsShouldBeFound("quantity.specified=true");

        // Get all the shoppingCartItemsList where quantity is null
        defaultShoppingCartItemsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the shoppingCartItemsList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the shoppingCartItemsList where quantity is less than or equal to SMALLER_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity is less than DEFAULT_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the shoppingCartItemsList where quantity is less than UPDATED_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where quantity is greater than DEFAULT_QUANTITY
        defaultShoppingCartItemsShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the shoppingCartItemsList where quantity is greater than SMALLER_QUANTITY
        defaultShoppingCartItemsShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultShoppingCartItemsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartItemsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartItemsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultShoppingCartItemsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartItemsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartItemsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultShoppingCartItemsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the shoppingCartItemsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartItemsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedBy is not null
        defaultShoppingCartItemsShouldBeFound("lastEditedBy.specified=true");

        // Get all the shoppingCartItemsList where lastEditedBy is null
        defaultShoppingCartItemsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultShoppingCartItemsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartItemsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultShoppingCartItemsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultShoppingCartItemsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartItemsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultShoppingCartItemsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultShoppingCartItemsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shoppingCartItemsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartItemsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultShoppingCartItemsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shoppingCartItemsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartItemsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartItemsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the shoppingCartItemsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartItemsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        // Get all the shoppingCartItemsList where lastEditedWhen is not null
        defaultShoppingCartItemsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the shoppingCartItemsList where lastEditedWhen is null
        defaultShoppingCartItemsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartItemsByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        shoppingCartItems.setStockItem(stockItem);
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);
        Long stockItemId = stockItem.getId();

        // Get all the shoppingCartItemsList where stockItem equals to stockItemId
        defaultShoppingCartItemsShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the shoppingCartItemsList where stockItem equals to stockItemId + 1
        defaultShoppingCartItemsShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartItemsByCartIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);
        ShoppingCarts cart = ShoppingCartsResourceIT.createEntity(em);
        em.persist(cart);
        em.flush();
        shoppingCartItems.setCart(cart);
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);
        Long cartId = cart.getId();

        // Get all the shoppingCartItemsList where cart equals to cartId
        defaultShoppingCartItemsShouldBeFound("cartId.equals=" + cartId);

        // Get all the shoppingCartItemsList where cart equals to cartId + 1
        defaultShoppingCartItemsShouldNotBeFound("cartId.equals=" + (cartId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShoppingCartItemsShouldBeFound(String filter) throws Exception {
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShoppingCartItemsShouldNotBeFound(String filter) throws Exception {
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingShoppingCartItems() throws Exception {
        // Get the shoppingCartItems
        restShoppingCartItemsMockMvc.perform(get("/api/shopping-cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        int databaseSizeBeforeUpdate = shoppingCartItemsRepository.findAll().size();

        // Update the shoppingCartItems
        ShoppingCartItems updatedShoppingCartItems = shoppingCartItemsRepository.findById(shoppingCartItems.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCartItems are not directly saved in db
        em.detach(updatedShoppingCartItems);
        updatedShoppingCartItems
            .quantity(UPDATED_QUANTITY)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(updatedShoppingCartItems);

        restShoppingCartItemsMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCartItems testShoppingCartItems = shoppingCartItemsList.get(shoppingCartItemsList.size() - 1);
        assertThat(testShoppingCartItems.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testShoppingCartItems.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testShoppingCartItems.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCartItems() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartItemsRepository.findAll().size();

        // Create the ShoppingCartItems
        ShoppingCartItemsDTO shoppingCartItemsDTO = shoppingCartItemsMapper.toDto(shoppingCartItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartItemsMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCartItems in the database
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemsRepository.saveAndFlush(shoppingCartItems);

        int databaseSizeBeforeDelete = shoppingCartItemsRepository.findAll().size();

        // Delete the shoppingCartItems
        restShoppingCartItemsMockMvc.perform(delete("/api/shopping-cart-items/{id}", shoppingCartItems.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShoppingCartItems> shoppingCartItemsList = shoppingCartItemsRepository.findAll();
        assertThat(shoppingCartItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
