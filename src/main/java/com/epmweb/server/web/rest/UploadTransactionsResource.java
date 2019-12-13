package com.epmweb.server.web.rest;

import com.epmweb.server.service.UploadTransactionsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.UploadTransactionsDTO;
import com.epmweb.server.service.dto.UploadTransactionsCriteria;
import com.epmweb.server.service.UploadTransactionsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epmweb.server.domain.UploadTransactions}.
 */
@RestController
@RequestMapping("/api")
public class UploadTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsResource.class);

    private static final String ENTITY_NAME = "uploadTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UploadTransactionsService uploadTransactionsService;

    private final UploadTransactionsQueryService uploadTransactionsQueryService;

    public UploadTransactionsResource(UploadTransactionsService uploadTransactionsService, UploadTransactionsQueryService uploadTransactionsQueryService) {
        this.uploadTransactionsService = uploadTransactionsService;
        this.uploadTransactionsQueryService = uploadTransactionsQueryService;
    }

    /**
     * {@code POST  /upload-transactions} : Create a new uploadTransactions.
     *
     * @param uploadTransactionsDTO the uploadTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uploadTransactionsDTO, or with status {@code 400 (Bad Request)} if the uploadTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upload-transactions")
    public ResponseEntity<UploadTransactionsDTO> createUploadTransactions(@RequestBody UploadTransactionsDTO uploadTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save UploadTransactions : {}", uploadTransactionsDTO);
        if (uploadTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploadTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadTransactionsDTO result = uploadTransactionsService.save(uploadTransactionsDTO);
        return ResponseEntity.created(new URI("/api/upload-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upload-transactions} : Updates an existing uploadTransactions.
     *
     * @param uploadTransactionsDTO the uploadTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the uploadTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upload-transactions")
    public ResponseEntity<UploadTransactionsDTO> updateUploadTransactions(@RequestBody UploadTransactionsDTO uploadTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update UploadTransactions : {}", uploadTransactionsDTO);
        if (uploadTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadTransactionsDTO result = uploadTransactionsService.save(uploadTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uploadTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /upload-transactions} : get all the uploadTransactions.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uploadTransactions in body.
     */
    @GetMapping("/upload-transactions")
    public ResponseEntity<List<UploadTransactionsDTO>> getAllUploadTransactions(UploadTransactionsCriteria criteria) {
        log.debug("REST request to get UploadTransactions by criteria: {}", criteria);
        List<UploadTransactionsDTO> entityList = uploadTransactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /upload-transactions/count} : count all the uploadTransactions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/upload-transactions/count")
    public ResponseEntity<Long> countUploadTransactions(UploadTransactionsCriteria criteria) {
        log.debug("REST request to count UploadTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(uploadTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /upload-transactions/:id} : get the "id" uploadTransactions.
     *
     * @param id the id of the uploadTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uploadTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/upload-transactions/{id}")
    public ResponseEntity<UploadTransactionsDTO> getUploadTransactions(@PathVariable Long id) {
        log.debug("REST request to get UploadTransactions : {}", id);
        Optional<UploadTransactionsDTO> uploadTransactionsDTO = uploadTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadTransactionsDTO);
    }

    /**
     * {@code DELETE  /upload-transactions/:id} : delete the "id" uploadTransactions.
     *
     * @param id the id of the uploadTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/upload-transactions/{id}")
    public ResponseEntity<Void> deleteUploadTransactions(@PathVariable Long id) {
        log.debug("REST request to delete UploadTransactions : {}", id);
        uploadTransactionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
