package com.epmweb.server.web.rest;

import com.epmweb.server.service.ComparesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ComparesDTO;
import com.epmweb.server.service.dto.ComparesCriteria;
import com.epmweb.server.service.ComparesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Compares}.
 */
@RestController
@RequestMapping("/api")
public class ComparesResource {

    private final Logger log = LoggerFactory.getLogger(ComparesResource.class);

    private static final String ENTITY_NAME = "compares";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComparesService comparesService;

    private final ComparesQueryService comparesQueryService;

    public ComparesResource(ComparesService comparesService, ComparesQueryService comparesQueryService) {
        this.comparesService = comparesService;
        this.comparesQueryService = comparesQueryService;
    }

    /**
     * {@code POST  /compares} : Create a new compares.
     *
     * @param comparesDTO the comparesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comparesDTO, or with status {@code 400 (Bad Request)} if the compares has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compares")
    public ResponseEntity<ComparesDTO> createCompares(@RequestBody ComparesDTO comparesDTO) throws URISyntaxException {
        log.debug("REST request to save Compares : {}", comparesDTO);
        if (comparesDTO.getId() != null) {
            throw new BadRequestAlertException("A new compares cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComparesDTO result = comparesService.save(comparesDTO);
        return ResponseEntity.created(new URI("/api/compares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compares} : Updates an existing compares.
     *
     * @param comparesDTO the comparesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comparesDTO,
     * or with status {@code 400 (Bad Request)} if the comparesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comparesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compares")
    public ResponseEntity<ComparesDTO> updateCompares(@RequestBody ComparesDTO comparesDTO) throws URISyntaxException {
        log.debug("REST request to update Compares : {}", comparesDTO);
        if (comparesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComparesDTO result = comparesService.save(comparesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comparesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compares} : get all the compares.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compares in body.
     */
    @GetMapping("/compares")
    public ResponseEntity<List<ComparesDTO>> getAllCompares(ComparesCriteria criteria) {
        log.debug("REST request to get Compares by criteria: {}", criteria);
        List<ComparesDTO> entityList = comparesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /compares/count} : count all the compares.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/compares/count")
    public ResponseEntity<Long> countCompares(ComparesCriteria criteria) {
        log.debug("REST request to count Compares by criteria: {}", criteria);
        return ResponseEntity.ok().body(comparesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compares/:id} : get the "id" compares.
     *
     * @param id the id of the comparesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comparesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compares/{id}")
    public ResponseEntity<ComparesDTO> getCompares(@PathVariable Long id) {
        log.debug("REST request to get Compares : {}", id);
        Optional<ComparesDTO> comparesDTO = comparesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comparesDTO);
    }

    /**
     * {@code DELETE  /compares/:id} : delete the "id" compares.
     *
     * @param id the id of the comparesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compares/{id}")
    public ResponseEntity<Void> deleteCompares(@PathVariable Long id) {
        log.debug("REST request to delete Compares : {}", id);
        comparesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
