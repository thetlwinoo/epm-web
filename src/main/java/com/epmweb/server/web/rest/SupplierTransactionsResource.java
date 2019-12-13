package com.epmweb.server.web.rest;

import com.epmweb.server.service.SupplierTransactionsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.SupplierTransactionsDTO;
import com.epmweb.server.service.dto.SupplierTransactionsCriteria;
import com.epmweb.server.service.SupplierTransactionsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.SupplierTransactions}.
 */
@RestController
@RequestMapping("/api")
public class SupplierTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionsResource.class);

    private static final String ENTITY_NAME = "supplierTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierTransactionsService supplierTransactionsService;

    private final SupplierTransactionsQueryService supplierTransactionsQueryService;

    public SupplierTransactionsResource(SupplierTransactionsService supplierTransactionsService, SupplierTransactionsQueryService supplierTransactionsQueryService) {
        this.supplierTransactionsService = supplierTransactionsService;
        this.supplierTransactionsQueryService = supplierTransactionsQueryService;
    }

    /**
     * {@code POST  /supplier-transactions} : Create a new supplierTransactions.
     *
     * @param supplierTransactionsDTO the supplierTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierTransactionsDTO, or with status {@code 400 (Bad Request)} if the supplierTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-transactions")
    public ResponseEntity<SupplierTransactionsDTO> createSupplierTransactions(@Valid @RequestBody SupplierTransactionsDTO supplierTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierTransactions : {}", supplierTransactionsDTO);
        if (supplierTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierTransactionsDTO result = supplierTransactionsService.save(supplierTransactionsDTO);
        return ResponseEntity.created(new URI("/api/supplier-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-transactions} : Updates an existing supplierTransactions.
     *
     * @param supplierTransactionsDTO the supplierTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the supplierTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-transactions")
    public ResponseEntity<SupplierTransactionsDTO> updateSupplierTransactions(@Valid @RequestBody SupplierTransactionsDTO supplierTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierTransactions : {}", supplierTransactionsDTO);
        if (supplierTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierTransactionsDTO result = supplierTransactionsService.save(supplierTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-transactions} : get all the supplierTransactions.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierTransactions in body.
     */
    @GetMapping("/supplier-transactions")
    public ResponseEntity<List<SupplierTransactionsDTO>> getAllSupplierTransactions(SupplierTransactionsCriteria criteria) {
        log.debug("REST request to get SupplierTransactions by criteria: {}", criteria);
        List<SupplierTransactionsDTO> entityList = supplierTransactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /supplier-transactions/count} : count all the supplierTransactions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/supplier-transactions/count")
    public ResponseEntity<Long> countSupplierTransactions(SupplierTransactionsCriteria criteria) {
        log.debug("REST request to count SupplierTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-transactions/:id} : get the "id" supplierTransactions.
     *
     * @param id the id of the supplierTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-transactions/{id}")
    public ResponseEntity<SupplierTransactionsDTO> getSupplierTransactions(@PathVariable Long id) {
        log.debug("REST request to get SupplierTransactions : {}", id);
        Optional<SupplierTransactionsDTO> supplierTransactionsDTO = supplierTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierTransactionsDTO);
    }

    /**
     * {@code DELETE  /supplier-transactions/:id} : delete the "id" supplierTransactions.
     *
     * @param id the id of the supplierTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-transactions/{id}")
    public ResponseEntity<Void> deleteSupplierTransactions(@PathVariable Long id) {
        log.debug("REST request to delete SupplierTransactions : {}", id);
        supplierTransactionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
