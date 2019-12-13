package com.epmweb.server.web.rest;

import com.epmweb.server.service.StateProvincesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.StateProvincesDTO;
import com.epmweb.server.service.dto.StateProvincesCriteria;
import com.epmweb.server.service.StateProvincesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.StateProvinces}.
 */
@RestController
@RequestMapping("/api")
public class StateProvincesResource {

    private final Logger log = LoggerFactory.getLogger(StateProvincesResource.class);

    private static final String ENTITY_NAME = "stateProvinces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateProvincesService stateProvincesService;

    private final StateProvincesQueryService stateProvincesQueryService;

    public StateProvincesResource(StateProvincesService stateProvincesService, StateProvincesQueryService stateProvincesQueryService) {
        this.stateProvincesService = stateProvincesService;
        this.stateProvincesQueryService = stateProvincesQueryService;
    }

    /**
     * {@code POST  /state-provinces} : Create a new stateProvinces.
     *
     * @param stateProvincesDTO the stateProvincesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateProvincesDTO, or with status {@code 400 (Bad Request)} if the stateProvinces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-provinces")
    public ResponseEntity<StateProvincesDTO> createStateProvinces(@Valid @RequestBody StateProvincesDTO stateProvincesDTO) throws URISyntaxException {
        log.debug("REST request to save StateProvinces : {}", stateProvincesDTO);
        if (stateProvincesDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateProvinces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateProvincesDTO result = stateProvincesService.save(stateProvincesDTO);
        return ResponseEntity.created(new URI("/api/state-provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-provinces} : Updates an existing stateProvinces.
     *
     * @param stateProvincesDTO the stateProvincesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateProvincesDTO,
     * or with status {@code 400 (Bad Request)} if the stateProvincesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateProvincesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-provinces")
    public ResponseEntity<StateProvincesDTO> updateStateProvinces(@Valid @RequestBody StateProvincesDTO stateProvincesDTO) throws URISyntaxException {
        log.debug("REST request to update StateProvinces : {}", stateProvincesDTO);
        if (stateProvincesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateProvincesDTO result = stateProvincesService.save(stateProvincesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateProvincesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-provinces} : get all the stateProvinces.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateProvinces in body.
     */
    @GetMapping("/state-provinces")
    public ResponseEntity<List<StateProvincesDTO>> getAllStateProvinces(StateProvincesCriteria criteria) {
        log.debug("REST request to get StateProvinces by criteria: {}", criteria);
        List<StateProvincesDTO> entityList = stateProvincesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /state-provinces/count} : count all the stateProvinces.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/state-provinces/count")
    public ResponseEntity<Long> countStateProvinces(StateProvincesCriteria criteria) {
        log.debug("REST request to count StateProvinces by criteria: {}", criteria);
        return ResponseEntity.ok().body(stateProvincesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /state-provinces/:id} : get the "id" stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateProvincesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-provinces/{id}")
    public ResponseEntity<StateProvincesDTO> getStateProvinces(@PathVariable Long id) {
        log.debug("REST request to get StateProvinces : {}", id);
        Optional<StateProvincesDTO> stateProvincesDTO = stateProvincesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateProvincesDTO);
    }

    /**
     * {@code DELETE  /state-provinces/:id} : delete the "id" stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-provinces/{id}")
    public ResponseEntity<Void> deleteStateProvinces(@PathVariable Long id) {
        log.debug("REST request to delete StateProvinces : {}", id);
        stateProvincesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
