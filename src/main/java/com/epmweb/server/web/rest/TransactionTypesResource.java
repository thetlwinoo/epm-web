package com.epmweb.server.web.rest;

import com.epmweb.server.service.TransactionTypesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.TransactionTypesDTO;
import com.epmweb.server.service.dto.TransactionTypesCriteria;
import com.epmweb.server.service.TransactionTypesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.TransactionTypes}.
 */
@RestController
@RequestMapping("/api")
public class TransactionTypesResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesResource.class);

    private static final String ENTITY_NAME = "transactionTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionTypesService transactionTypesService;

    private final TransactionTypesQueryService transactionTypesQueryService;

    public TransactionTypesResource(TransactionTypesService transactionTypesService, TransactionTypesQueryService transactionTypesQueryService) {
        this.transactionTypesService = transactionTypesService;
        this.transactionTypesQueryService = transactionTypesQueryService;
    }

    /**
     * {@code POST  /transaction-types} : Create a new transactionTypes.
     *
     * @param transactionTypesDTO the transactionTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionTypesDTO, or with status {@code 400 (Bad Request)} if the transactionTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-types")
    public ResponseEntity<TransactionTypesDTO> createTransactionTypes(@Valid @RequestBody TransactionTypesDTO transactionTypesDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionTypes : {}", transactionTypesDTO);
        if (transactionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionTypesDTO result = transactionTypesService.save(transactionTypesDTO);
        return ResponseEntity.created(new URI("/api/transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-types} : Updates an existing transactionTypes.
     *
     * @param transactionTypesDTO the transactionTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionTypesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-types")
    public ResponseEntity<TransactionTypesDTO> updateTransactionTypes(@Valid @RequestBody TransactionTypesDTO transactionTypesDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionTypes : {}", transactionTypesDTO);
        if (transactionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionTypesDTO result = transactionTypesService.save(transactionTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-types} : get all the transactionTypes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionTypes in body.
     */
    @GetMapping("/transaction-types")
    public ResponseEntity<List<TransactionTypesDTO>> getAllTransactionTypes(TransactionTypesCriteria criteria) {
        log.debug("REST request to get TransactionTypes by criteria: {}", criteria);
        List<TransactionTypesDTO> entityList = transactionTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /transaction-types/count} : count all the transactionTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/transaction-types/count")
    public ResponseEntity<Long> countTransactionTypes(TransactionTypesCriteria criteria) {
        log.debug("REST request to count TransactionTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-types/:id} : get the "id" transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-types/{id}")
    public ResponseEntity<TransactionTypesDTO> getTransactionTypes(@PathVariable Long id) {
        log.debug("REST request to get TransactionTypes : {}", id);
        Optional<TransactionTypesDTO> transactionTypesDTO = transactionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionTypesDTO);
    }

    /**
     * {@code DELETE  /transaction-types/:id} : delete the "id" transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-types/{id}")
    public ResponseEntity<Void> deleteTransactionTypes(@PathVariable Long id) {
        log.debug("REST request to delete TransactionTypes : {}", id);
        transactionTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
