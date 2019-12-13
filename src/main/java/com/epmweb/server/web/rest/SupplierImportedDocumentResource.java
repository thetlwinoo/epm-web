package com.epmweb.server.web.rest;

import com.epmweb.server.service.SupplierImportedDocumentService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;
import com.epmweb.server.service.dto.SupplierImportedDocumentCriteria;
import com.epmweb.server.service.SupplierImportedDocumentQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.SupplierImportedDocument}.
 */
@RestController
@RequestMapping("/api")
public class SupplierImportedDocumentResource {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentResource.class);

    private static final String ENTITY_NAME = "supplierImportedDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierImportedDocumentService supplierImportedDocumentService;

    private final SupplierImportedDocumentQueryService supplierImportedDocumentQueryService;

    public SupplierImportedDocumentResource(SupplierImportedDocumentService supplierImportedDocumentService, SupplierImportedDocumentQueryService supplierImportedDocumentQueryService) {
        this.supplierImportedDocumentService = supplierImportedDocumentService;
        this.supplierImportedDocumentQueryService = supplierImportedDocumentQueryService;
    }

    /**
     * {@code POST  /supplier-imported-documents} : Create a new supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the supplierImportedDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierImportedDocumentDTO, or with status {@code 400 (Bad Request)} if the supplierImportedDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-imported-documents")
    public ResponseEntity<SupplierImportedDocumentDTO> createSupplierImportedDocument(@RequestBody SupplierImportedDocumentDTO supplierImportedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierImportedDocument : {}", supplierImportedDocumentDTO);
        if (supplierImportedDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierImportedDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierImportedDocumentDTO result = supplierImportedDocumentService.save(supplierImportedDocumentDTO);
        return ResponseEntity.created(new URI("/api/supplier-imported-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-imported-documents} : Updates an existing supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the supplierImportedDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierImportedDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the supplierImportedDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierImportedDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-imported-documents")
    public ResponseEntity<SupplierImportedDocumentDTO> updateSupplierImportedDocument(@RequestBody SupplierImportedDocumentDTO supplierImportedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierImportedDocument : {}", supplierImportedDocumentDTO);
        if (supplierImportedDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierImportedDocumentDTO result = supplierImportedDocumentService.save(supplierImportedDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierImportedDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-imported-documents} : get all the supplierImportedDocuments.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierImportedDocuments in body.
     */
    @GetMapping("/supplier-imported-documents")
    public ResponseEntity<List<SupplierImportedDocumentDTO>> getAllSupplierImportedDocuments(SupplierImportedDocumentCriteria criteria) {
        log.debug("REST request to get SupplierImportedDocuments by criteria: {}", criteria);
        List<SupplierImportedDocumentDTO> entityList = supplierImportedDocumentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /supplier-imported-documents/count} : count all the supplierImportedDocuments.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/supplier-imported-documents/count")
    public ResponseEntity<Long> countSupplierImportedDocuments(SupplierImportedDocumentCriteria criteria) {
        log.debug("REST request to count SupplierImportedDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierImportedDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-imported-documents/:id} : get the "id" supplierImportedDocument.
     *
     * @param id the id of the supplierImportedDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierImportedDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-imported-documents/{id}")
    public ResponseEntity<SupplierImportedDocumentDTO> getSupplierImportedDocument(@PathVariable Long id) {
        log.debug("REST request to get SupplierImportedDocument : {}", id);
        Optional<SupplierImportedDocumentDTO> supplierImportedDocumentDTO = supplierImportedDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierImportedDocumentDTO);
    }

    /**
     * {@code DELETE  /supplier-imported-documents/:id} : delete the "id" supplierImportedDocument.
     *
     * @param id the id of the supplierImportedDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-imported-documents/{id}")
    public ResponseEntity<Void> deleteSupplierImportedDocument(@PathVariable Long id) {
        log.debug("REST request to delete SupplierImportedDocument : {}", id);
        supplierImportedDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
