package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.PaymentTransactions;
import com.epmweb.server.domain.Orders;
import com.epmweb.server.repository.PaymentTransactionsRepository;
import com.epmweb.server.service.PaymentTransactionsService;
import com.epmweb.server.service.dto.PaymentTransactionsDTO;
import com.epmweb.server.service.mapper.PaymentTransactionsMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.PaymentTransactionsCriteria;
import com.epmweb.server.service.PaymentTransactionsQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link PaymentTransactionsResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class PaymentTransactionsResourceIT {

    private static final String DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RETURNED_COMPLETED_PAYMENT_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentTransactionsRepository paymentTransactionsRepository;

    @Autowired
    private PaymentTransactionsMapper paymentTransactionsMapper;

    @Autowired
    private PaymentTransactionsService paymentTransactionsService;

    @Autowired
    private PaymentTransactionsQueryService paymentTransactionsQueryService;

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

    private MockMvc restPaymentTransactionsMockMvc;

    private PaymentTransactions paymentTransactions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentTransactionsResource paymentTransactionsResource = new PaymentTransactionsResource(paymentTransactionsService, paymentTransactionsQueryService);
        this.restPaymentTransactionsMockMvc = MockMvcBuilders.standaloneSetup(paymentTransactionsResource)
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
    public static PaymentTransactions createEntity(EntityManager em) {
        PaymentTransactions paymentTransactions = new PaymentTransactions()
            .returnedCompletedPaymentData(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return paymentTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTransactions createUpdatedEntity(EntityManager em) {
        PaymentTransactions paymentTransactions = new PaymentTransactions()
            .returnedCompletedPaymentData(UPDATED_RETURNED_COMPLETED_PAYMENT_DATA)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return paymentTransactions;
    }

    @BeforeEach
    public void initTest() {
        paymentTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentTransactions() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionsRepository.findAll().size();

        // Create the PaymentTransactions
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(paymentTransactions);
        restPaymentTransactionsMockMvc.perform(post("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTransactions testPaymentTransactions = paymentTransactionsList.get(paymentTransactionsList.size() - 1);
        assertThat(testPaymentTransactions.getReturnedCompletedPaymentData()).isEqualTo(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA);
        assertThat(testPaymentTransactions.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testPaymentTransactions.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createPaymentTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionsRepository.findAll().size();

        // Create the PaymentTransactions with an existing ID
        paymentTransactions.setId(1L);
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(paymentTransactions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTransactionsMockMvc.perform(post("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].returnedCompletedPaymentData").value(hasItem(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get the paymentTransactions
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions/{id}", paymentTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTransactions.getId().intValue()))
            .andExpect(jsonPath("$.returnedCompletedPaymentData").value(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getPaymentTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        Long id = paymentTransactions.getId();

        defaultPaymentTransactionsShouldBeFound("id.equals=" + id);
        defaultPaymentTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentTransactionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultPaymentTransactionsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the paymentTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultPaymentTransactionsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultPaymentTransactionsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the paymentTransactionsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultPaymentTransactionsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultPaymentTransactionsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the paymentTransactionsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultPaymentTransactionsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedBy is not null
        defaultPaymentTransactionsShouldBeFound("lastEditedBy.specified=true");

        // Get all the paymentTransactionsList where lastEditedBy is null
        defaultPaymentTransactionsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultPaymentTransactionsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the paymentTransactionsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultPaymentTransactionsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultPaymentTransactionsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the paymentTransactionsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultPaymentTransactionsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultPaymentTransactionsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the paymentTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultPaymentTransactionsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultPaymentTransactionsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the paymentTransactionsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultPaymentTransactionsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultPaymentTransactionsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the paymentTransactionsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultPaymentTransactionsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        // Get all the paymentTransactionsList where lastEditedWhen is not null
        defaultPaymentTransactionsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the paymentTransactionsList where lastEditedWhen is null
        defaultPaymentTransactionsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionsByPaymentOnOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);
        Orders paymentOnOrder = OrdersResourceIT.createEntity(em);
        em.persist(paymentOnOrder);
        em.flush();
        paymentTransactions.setPaymentOnOrder(paymentOnOrder);
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);
        Long paymentOnOrderId = paymentOnOrder.getId();

        // Get all the paymentTransactionsList where paymentOnOrder equals to paymentOnOrderId
        defaultPaymentTransactionsShouldBeFound("paymentOnOrderId.equals=" + paymentOnOrderId);

        // Get all the paymentTransactionsList where paymentOnOrder equals to paymentOnOrderId + 1
        defaultPaymentTransactionsShouldNotBeFound("paymentOnOrderId.equals=" + (paymentOnOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentTransactionsShouldBeFound(String filter) throws Exception {
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].returnedCompletedPaymentData").value(hasItem(DEFAULT_RETURNED_COMPLETED_PAYMENT_DATA.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentTransactionsShouldNotBeFound(String filter) throws Exception {
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaymentTransactions() throws Exception {
        // Get the paymentTransactions
        restPaymentTransactionsMockMvc.perform(get("/api/payment-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        int databaseSizeBeforeUpdate = paymentTransactionsRepository.findAll().size();

        // Update the paymentTransactions
        PaymentTransactions updatedPaymentTransactions = paymentTransactionsRepository.findById(paymentTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentTransactions are not directly saved in db
        em.detach(updatedPaymentTransactions);
        updatedPaymentTransactions
            .returnedCompletedPaymentData(UPDATED_RETURNED_COMPLETED_PAYMENT_DATA)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(updatedPaymentTransactions);

        restPaymentTransactionsMockMvc.perform(put("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransactions testPaymentTransactions = paymentTransactionsList.get(paymentTransactionsList.size() - 1);
        assertThat(testPaymentTransactions.getReturnedCompletedPaymentData()).isEqualTo(UPDATED_RETURNED_COMPLETED_PAYMENT_DATA);
        assertThat(testPaymentTransactions.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testPaymentTransactions.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentTransactions() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionsRepository.findAll().size();

        // Create the PaymentTransactions
        PaymentTransactionsDTO paymentTransactionsDTO = paymentTransactionsMapper.toDto(paymentTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTransactionsMockMvc.perform(put("/api/payment-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransactions in the database
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentTransactions() throws Exception {
        // Initialize the database
        paymentTransactionsRepository.saveAndFlush(paymentTransactions);

        int databaseSizeBeforeDelete = paymentTransactionsRepository.findAll().size();

        // Delete the paymentTransactions
        restPaymentTransactionsMockMvc.perform(delete("/api/payment-transactions/{id}", paymentTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentTransactions> paymentTransactionsList = paymentTransactionsRepository.findAll();
        assertThat(paymentTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
