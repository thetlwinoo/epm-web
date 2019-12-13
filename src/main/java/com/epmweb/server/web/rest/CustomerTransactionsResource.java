package com.epmweb.server.web.rest;

import com.epmweb.server.service.CustomerTransactionsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CustomerTransactionsDTO;
import com.epmweb.server.service.dto.CustomerTransactionsCriteria;
import com.epmweb.server.service.CustomerTransactionsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.CustomerTransactions}.
 */
@RestController
@RequestMapping("/api")
public class CustomerTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTransactionsResource.class);

    private static final String ENTITY_NAME = "customerTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerTransactionsService customerTransactionsService;

    private final CustomerTransactionsQueryService customerTransactionsQueryService;

    public CustomerTransactionsResource(CustomerTransactionsService customerTransactionsService, CustomerTransactionsQueryService customerTransactionsQueryService) {
        this.customerTransactionsService = customerTransactionsService;
        this.customerTransactionsQueryService = customerTransactionsQueryService;
    }

    /**
     * {@code POST  /customer-transactions} : Create a new customerTransactions.
     *
     * @param customerTransactionsDTO the customerTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerTransactionsDTO, or with status {@code 400 (Bad Request)} if the customerTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-transactions")
    public ResponseEntity<CustomerTransactionsDTO> createCustomerTransactions(@Valid @RequestBody CustomerTransactionsDTO customerTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerTransactions : {}", customerTransactionsDTO);
        if (customerTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerTransactionsDTO result = customerTransactionsService.save(customerTransactionsDTO);
        return ResponseEntity.created(new URI("/api/customer-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-transactions} : Updates an existing customerTransactions.
     *
     * @param customerTransactionsDTO the customerTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the customerTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-transactions")
    public ResponseEntity<CustomerTransactionsDTO> updateCustomerTransactions(@Valid @RequestBody CustomerTransactionsDTO customerTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerTransactions : {}", customerTransactionsDTO);
        if (customerTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerTransactionsDTO result = customerTransactionsService.save(customerTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-transactions} : get all the customerTransactions.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerTransactions in body.
     */
    @GetMapping("/customer-transactions")
    public ResponseEntity<List<CustomerTransactionsDTO>> getAllCustomerTransactions(CustomerTransactionsCriteria criteria) {
        log.debug("REST request to get CustomerTransactions by criteria: {}", criteria);
        List<CustomerTransactionsDTO> entityList = customerTransactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /customer-transactions/count} : count all the customerTransactions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/customer-transactions/count")
    public ResponseEntity<Long> countCustomerTransactions(CustomerTransactionsCriteria criteria) {
        log.debug("REST request to count CustomerTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-transactions/:id} : get the "id" customerTransactions.
     *
     * @param id the id of the customerTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-transactions/{id}")
    public ResponseEntity<CustomerTransactionsDTO> getCustomerTransactions(@PathVariable Long id) {
        log.debug("REST request to get CustomerTransactions : {}", id);
        Optional<CustomerTransactionsDTO> customerTransactionsDTO = customerTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerTransactionsDTO);
    }

    /**
     * {@code DELETE  /customer-transactions/:id} : delete the "id" customerTransactions.
     *
     * @param id the id of the customerTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-transactions/{id}")
    public ResponseEntity<Void> deleteCustomerTransactions(@PathVariable Long id) {
        log.debug("REST request to delete CustomerTransactions : {}", id);
        customerTransactionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
