package com.epmweb.server.web.rest;

import com.epmweb.server.service.PaymentTransactionsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PaymentTransactionsDTO;
import com.epmweb.server.service.dto.PaymentTransactionsCriteria;
import com.epmweb.server.service.PaymentTransactionsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epmweb.server.domain.PaymentTransactions}.
 */
@RestController
@RequestMapping("/api")
public class PaymentTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionsResource.class);

    private static final String ENTITY_NAME = "paymentTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentTransactionsService paymentTransactionsService;

    private final PaymentTransactionsQueryService paymentTransactionsQueryService;

    public PaymentTransactionsResource(PaymentTransactionsService paymentTransactionsService, PaymentTransactionsQueryService paymentTransactionsQueryService) {
        this.paymentTransactionsService = paymentTransactionsService;
        this.paymentTransactionsQueryService = paymentTransactionsQueryService;
    }

    /**
     * {@code POST  /payment-transactions} : Create a new paymentTransactions.
     *
     * @param paymentTransactionsDTO the paymentTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentTransactionsDTO, or with status {@code 400 (Bad Request)} if the paymentTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-transactions")
    public ResponseEntity<PaymentTransactionsDTO> createPaymentTransactions(@Valid @RequestBody PaymentTransactionsDTO paymentTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentTransactions : {}", paymentTransactionsDTO);
        if (paymentTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTransactionsDTO result = paymentTransactionsService.save(paymentTransactionsDTO);
        return ResponseEntity.created(new URI("/api/payment-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-transactions} : Updates an existing paymentTransactions.
     *
     * @param paymentTransactionsDTO the paymentTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-transactions")
    public ResponseEntity<PaymentTransactionsDTO> updatePaymentTransactions(@Valid @RequestBody PaymentTransactionsDTO paymentTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentTransactions : {}", paymentTransactionsDTO);
        if (paymentTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentTransactionsDTO result = paymentTransactionsService.save(paymentTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-transactions} : get all the paymentTransactions.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentTransactions in body.
     */
    @GetMapping("/payment-transactions")
    public ResponseEntity<List<PaymentTransactionsDTO>> getAllPaymentTransactions(PaymentTransactionsCriteria criteria) {
        log.debug("REST request to get PaymentTransactions by criteria: {}", criteria);
        List<PaymentTransactionsDTO> entityList = paymentTransactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /payment-transactions/count} : count all the paymentTransactions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/payment-transactions/count")
    public ResponseEntity<Long> countPaymentTransactions(PaymentTransactionsCriteria criteria) {
        log.debug("REST request to count PaymentTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-transactions/:id} : get the "id" paymentTransactions.
     *
     * @param id the id of the paymentTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-transactions/{id}")
    public ResponseEntity<PaymentTransactionsDTO> getPaymentTransactions(@PathVariable Long id) {
        log.debug("REST request to get PaymentTransactions : {}", id);
        Optional<PaymentTransactionsDTO> paymentTransactionsDTO = paymentTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentTransactionsDTO);
    }

    /**
     * {@code DELETE  /payment-transactions/:id} : delete the "id" paymentTransactions.
     *
     * @param id the id of the paymentTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-transactions/{id}")
    public ResponseEntity<Void> deletePaymentTransactions(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTransactions : {}", id);
        paymentTransactionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
