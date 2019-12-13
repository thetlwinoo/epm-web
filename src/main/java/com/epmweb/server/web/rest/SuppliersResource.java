package com.epmweb.server.web.rest;

import com.epmweb.server.service.SuppliersService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.SuppliersDTO;
import com.epmweb.server.service.dto.SuppliersCriteria;
import com.epmweb.server.service.SuppliersQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Suppliers}.
 */
@RestController
@RequestMapping("/api")
public class SuppliersResource {

    private final Logger log = LoggerFactory.getLogger(SuppliersResource.class);

    private static final String ENTITY_NAME = "suppliers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuppliersService suppliersService;

    private final SuppliersQueryService suppliersQueryService;

    public SuppliersResource(SuppliersService suppliersService, SuppliersQueryService suppliersQueryService) {
        this.suppliersService = suppliersService;
        this.suppliersQueryService = suppliersQueryService;
    }

    /**
     * {@code POST  /suppliers} : Create a new suppliers.
     *
     * @param suppliersDTO the suppliersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suppliersDTO, or with status {@code 400 (Bad Request)} if the suppliers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suppliers")
    public ResponseEntity<SuppliersDTO> createSuppliers(@Valid @RequestBody SuppliersDTO suppliersDTO) throws URISyntaxException {
        log.debug("REST request to save Suppliers : {}", suppliersDTO);
        if (suppliersDTO.getId() != null) {
            throw new BadRequestAlertException("A new suppliers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuppliersDTO result = suppliersService.save(suppliersDTO);
        return ResponseEntity.created(new URI("/api/suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suppliers} : Updates an existing suppliers.
     *
     * @param suppliersDTO the suppliersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suppliersDTO,
     * or with status {@code 400 (Bad Request)} if the suppliersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suppliersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suppliers")
    public ResponseEntity<SuppliersDTO> updateSuppliers(@Valid @RequestBody SuppliersDTO suppliersDTO) throws URISyntaxException {
        log.debug("REST request to update Suppliers : {}", suppliersDTO);
        if (suppliersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuppliersDTO result = suppliersService.save(suppliersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suppliersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /suppliers} : get all the suppliers.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suppliers in body.
     */
    @GetMapping("/suppliers")
    public ResponseEntity<List<SuppliersDTO>> getAllSuppliers(SuppliersCriteria criteria) {
        log.debug("REST request to get Suppliers by criteria: {}", criteria);
        List<SuppliersDTO> entityList = suppliersQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /suppliers/count} : count all the suppliers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/suppliers/count")
    public ResponseEntity<Long> countSuppliers(SuppliersCriteria criteria) {
        log.debug("REST request to count Suppliers by criteria: {}", criteria);
        return ResponseEntity.ok().body(suppliersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /suppliers/:id} : get the "id" suppliers.
     *
     * @param id the id of the suppliersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suppliersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SuppliersDTO> getSuppliers(@PathVariable Long id) {
        log.debug("REST request to get Suppliers : {}", id);
        Optional<SuppliersDTO> suppliersDTO = suppliersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suppliersDTO);
    }

    /**
     * {@code DELETE  /suppliers/:id} : delete the "id" suppliers.
     *
     * @param id the id of the suppliersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSuppliers(@PathVariable Long id) {
        log.debug("REST request to delete Suppliers : {}", id);
        suppliersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
