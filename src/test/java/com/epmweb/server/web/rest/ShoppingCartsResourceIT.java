package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.domain.People;
import com.epmweb.server.domain.ShoppingCartItems;
import com.epmweb.server.domain.Customers;
import com.epmweb.server.domain.SpecialDeals;
import com.epmweb.server.repository.ShoppingCartsRepository;
import com.epmweb.server.service.ShoppingCartsService;
import com.epmweb.server.service.dto.ShoppingCartsDTO;
import com.epmweb.server.service.mapper.ShoppingCartsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.ShoppingCartsCriteria;
import com.epmweb.server.service.ShoppingCartsQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ShoppingCartsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ShoppingCartsResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_CARGO_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CARGO_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_CARGO_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ShoppingCartsRepository shoppingCartsRepository;

    @Autowired
    private ShoppingCartsMapper shoppingCartsMapper;

    @Autowired
    private ShoppingCartsService shoppingCartsService;

    @Autowired
    private ShoppingCartsQueryService shoppingCartsQueryService;

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

    private MockMvc restShoppingCartsMockMvc;

    private ShoppingCarts shoppingCarts;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartsResource shoppingCartsResource = new ShoppingCartsResource(shoppingCartsService, shoppingCartsQueryService);
        this.restShoppingCartsMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartsResource)
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
    public static ShoppingCarts createEntity(EntityManager em) {
        ShoppingCarts shoppingCarts = new ShoppingCarts()
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .totalCargoPrice(DEFAULT_TOTAL_CARGO_PRICE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return shoppingCarts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCarts createUpdatedEntity(EntityManager em) {
        ShoppingCarts shoppingCarts = new ShoppingCarts()
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalCargoPrice(UPDATED_TOTAL_CARGO_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return shoppingCarts;
    }

    @BeforeEach
    public void initTest() {
        shoppingCarts = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCarts() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);
        restShoppingCartsMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCarts testShoppingCarts = shoppingCartsList.get(shoppingCartsList.size() - 1);
        assertThat(testShoppingCarts.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testShoppingCarts.getTotalCargoPrice()).isEqualTo(DEFAULT_TOTAL_CARGO_PRICE);
        assertThat(testShoppingCarts.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testShoppingCarts.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createShoppingCartsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts with an existing ID
        shoppingCarts.setId(1L);
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartsMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCarts.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalCargoPrice").value(hasItem(DEFAULT_TOTAL_CARGO_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get the shoppingCarts
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/{id}", shoppingCarts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCarts.getId().intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.totalCargoPrice").value(DEFAULT_TOTAL_CARGO_PRICE.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getShoppingCartsByIdFiltering() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        Long id = shoppingCarts.getId();

        defaultShoppingCartsShouldBeFound("id.equals=" + id);
        defaultShoppingCartsShouldNotBeFound("id.notEquals=" + id);

        defaultShoppingCartsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShoppingCartsShouldNotBeFound("id.greaterThan=" + id);

        defaultShoppingCartsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShoppingCartsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is not null
        defaultShoppingCartsShouldBeFound("totalPrice.specified=true");

        // Get all the shoppingCartsList where totalPrice is null
        defaultShoppingCartsShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultShoppingCartsShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the shoppingCartsList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultShoppingCartsShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice equals to DEFAULT_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.equals=" + DEFAULT_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice equals to UPDATED_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.equals=" + UPDATED_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice not equals to DEFAULT_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.notEquals=" + DEFAULT_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice not equals to UPDATED_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.notEquals=" + UPDATED_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice in DEFAULT_TOTAL_CARGO_PRICE or UPDATED_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.in=" + DEFAULT_TOTAL_CARGO_PRICE + "," + UPDATED_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice equals to UPDATED_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.in=" + UPDATED_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice is not null
        defaultShoppingCartsShouldBeFound("totalCargoPrice.specified=true");

        // Get all the shoppingCartsList where totalCargoPrice is null
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice is greater than or equal to DEFAULT_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice is greater than or equal to UPDATED_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.greaterThanOrEqual=" + UPDATED_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice is less than or equal to DEFAULT_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.lessThanOrEqual=" + DEFAULT_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice is less than or equal to SMALLER_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.lessThanOrEqual=" + SMALLER_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice is less than DEFAULT_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.lessThan=" + DEFAULT_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice is less than UPDATED_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.lessThan=" + UPDATED_TOTAL_CARGO_PRICE);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByTotalCargoPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where totalCargoPrice is greater than DEFAULT_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldNotBeFound("totalCargoPrice.greaterThan=" + DEFAULT_TOTAL_CARGO_PRICE);

        // Get all the shoppingCartsList where totalCargoPrice is greater than SMALLER_TOTAL_CARGO_PRICE
        defaultShoppingCartsShouldBeFound("totalCargoPrice.greaterThan=" + SMALLER_TOTAL_CARGO_PRICE);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy is not null
        defaultShoppingCartsShouldBeFound("lastEditedBy.specified=true");

        // Get all the shoppingCartsList where lastEditedBy is null
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultShoppingCartsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the shoppingCartsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultShoppingCartsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultShoppingCartsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shoppingCartsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the shoppingCartsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the shoppingCartsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        // Get all the shoppingCartsList where lastEditedWhen is not null
        defaultShoppingCartsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the shoppingCartsList where lastEditedWhen is null
        defaultShoppingCartsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllShoppingCartsByCartUserIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        People cartUser = PeopleResourceIT.createEntity(em);
        em.persist(cartUser);
        em.flush();
        shoppingCarts.setCartUser(cartUser);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long cartUserId = cartUser.getId();

        // Get all the shoppingCartsList where cartUser equals to cartUserId
        defaultShoppingCartsShouldBeFound("cartUserId.equals=" + cartUserId);

        // Get all the shoppingCartsList where cartUser equals to cartUserId + 1
        defaultShoppingCartsShouldNotBeFound("cartUserId.equals=" + (cartUserId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByCartItemListIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        ShoppingCartItems cartItemList = ShoppingCartItemsResourceIT.createEntity(em);
        em.persist(cartItemList);
        em.flush();
        shoppingCarts.addCartItemList(cartItemList);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long cartItemListId = cartItemList.getId();

        // Get all the shoppingCartsList where cartItemList equals to cartItemListId
        defaultShoppingCartsShouldBeFound("cartItemListId.equals=" + cartItemListId);

        // Get all the shoppingCartsList where cartItemList equals to cartItemListId + 1
        defaultShoppingCartsShouldNotBeFound("cartItemListId.equals=" + (cartItemListId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        shoppingCarts.setCustomer(customer);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long customerId = customer.getId();

        // Get all the shoppingCartsList where customer equals to customerId
        defaultShoppingCartsShouldBeFound("customerId.equals=" + customerId);

        // Get all the shoppingCartsList where customer equals to customerId + 1
        defaultShoppingCartsShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllShoppingCartsBySpecialDealsIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        SpecialDeals specialDeals = SpecialDealsResourceIT.createEntity(em);
        em.persist(specialDeals);
        em.flush();
        shoppingCarts.setSpecialDeals(specialDeals);
        shoppingCartsRepository.saveAndFlush(shoppingCarts);
        Long specialDealsId = specialDeals.getId();

        // Get all the shoppingCartsList where specialDeals equals to specialDealsId
        defaultShoppingCartsShouldBeFound("specialDealsId.equals=" + specialDealsId);

        // Get all the shoppingCartsList where specialDeals equals to specialDealsId + 1
        defaultShoppingCartsShouldNotBeFound("specialDealsId.equals=" + (specialDealsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShoppingCartsShouldBeFound(String filter) throws Exception {
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCarts.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalCargoPrice").value(hasItem(DEFAULT_TOTAL_CARGO_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShoppingCartsShouldNotBeFound(String filter) throws Exception {
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingShoppingCarts() throws Exception {
        // Get the shoppingCarts
        restShoppingCartsMockMvc.perform(get("/api/shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        int databaseSizeBeforeUpdate = shoppingCartsRepository.findAll().size();

        // Update the shoppingCarts
        ShoppingCarts updatedShoppingCarts = shoppingCartsRepository.findById(shoppingCarts.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCarts are not directly saved in db
        em.detach(updatedShoppingCarts);
        updatedShoppingCarts
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalCargoPrice(UPDATED_TOTAL_CARGO_PRICE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(updatedShoppingCarts);

        restShoppingCartsMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCarts testShoppingCarts = shoppingCartsList.get(shoppingCartsList.size() - 1);
        assertThat(testShoppingCarts.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testShoppingCarts.getTotalCargoPrice()).isEqualTo(UPDATED_TOTAL_CARGO_PRICE);
        assertThat(testShoppingCarts.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testShoppingCarts.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCarts() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartsRepository.findAll().size();

        // Create the ShoppingCarts
        ShoppingCartsDTO shoppingCartsDTO = shoppingCartsMapper.toDto(shoppingCarts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartsMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCarts in the database
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartsRepository.saveAndFlush(shoppingCarts);

        int databaseSizeBeforeDelete = shoppingCartsRepository.findAll().size();

        // Delete the shoppingCarts
        restShoppingCartsMockMvc.perform(delete("/api/shopping-carts/{id}", shoppingCarts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShoppingCarts> shoppingCartsList = shoppingCartsRepository.findAll();
        assertThat(shoppingCartsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
