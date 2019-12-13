package com.epmweb.server.web.rest;

import com.epmweb.server.service.SupplierCategoriesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.SupplierCategoriesDTO;
import com.epmweb.server.service.dto.SupplierCategoriesCriteria;
import com.epmweb.server.service.SupplierCategoriesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.SupplierCategories}.
 */
@RestController
@RequestMapping("/api")
public class SupplierCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(SupplierCategoriesResource.class);

    private static final String ENTITY_NAME = "supplierCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierCategoriesService supplierCategoriesService;

    private final SupplierCategoriesQueryService supplierCategoriesQueryService;

    public SupplierCategoriesResource(SupplierCategoriesService supplierCategoriesService, SupplierCategoriesQueryService supplierCategoriesQueryService) {
        this.supplierCategoriesService = supplierCategoriesService;
        this.supplierCategoriesQueryService = supplierCategoriesQueryService;
    }

    /**
     * {@code POST  /supplier-categories} : Create a new supplierCategories.
     *
     * @param supplierCategoriesDTO the supplierCategoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierCategoriesDTO, or with status {@code 400 (Bad Request)} if the supplierCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-categories")
    public ResponseEntity<SupplierCategoriesDTO> createSupplierCategories(@Valid @RequestBody SupplierCategoriesDTO supplierCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierCategories : {}", supplierCategoriesDTO);
        if (supplierCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierCategoriesDTO result = supplierCategoriesService.save(supplierCategoriesDTO);
        return ResponseEntity.created(new URI("/api/supplier-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-categories} : Updates an existing supplierCategories.
     *
     * @param supplierCategoriesDTO the supplierCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the supplierCategoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-categories")
    public ResponseEntity<SupplierCategoriesDTO> updateSupplierCategories(@Valid @RequestBody SupplierCategoriesDTO supplierCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierCategories : {}", supplierCategoriesDTO);
        if (supplierCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierCategoriesDTO result = supplierCategoriesService.save(supplierCategoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-categories} : get all the supplierCategories.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierCategories in body.
     */
    @GetMapping("/supplier-categories")
    public ResponseEntity<List<SupplierCategoriesDTO>> getAllSupplierCategories(SupplierCategoriesCriteria criteria) {
        log.debug("REST request to get SupplierCategories by criteria: {}", criteria);
        List<SupplierCategoriesDTO> entityList = supplierCategoriesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /supplier-categories/count} : count all the supplierCategories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/supplier-categories/count")
    public ResponseEntity<Long> countSupplierCategories(SupplierCategoriesCriteria criteria) {
        log.debug("REST request to count SupplierCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierCategoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-categories/:id} : get the "id" supplierCategories.
     *
     * @param id the id of the supplierCategoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierCategoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-categories/{id}")
    public ResponseEntity<SupplierCategoriesDTO> getSupplierCategories(@PathVariable Long id) {
        log.debug("REST request to get SupplierCategories : {}", id);
        Optional<SupplierCategoriesDTO> supplierCategoriesDTO = supplierCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierCategoriesDTO);
    }

    /**
     * {@code DELETE  /supplier-categories/:id} : delete the "id" supplierCategories.
     *
     * @param id the id of the supplierCategoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-categories/{id}")
    public ResponseEntity<Void> deleteSupplierCategories(@PathVariable Long id) {
        log.debug("REST request to delete SupplierCategories : {}", id);
        supplierCategoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
