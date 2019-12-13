package com.epmweb.server.web.rest;

import com.epmweb.server.service.SystemParametersService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.SystemParametersDTO;
import com.epmweb.server.service.dto.SystemParametersCriteria;
import com.epmweb.server.service.SystemParametersQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.SystemParameters}.
 */
@RestController
@RequestMapping("/api")
public class SystemParametersResource {

    private final Logger log = LoggerFactory.getLogger(SystemParametersResource.class);

    private static final String ENTITY_NAME = "systemParameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemParametersService systemParametersService;

    private final SystemParametersQueryService systemParametersQueryService;

    public SystemParametersResource(SystemParametersService systemParametersService, SystemParametersQueryService systemParametersQueryService) {
        this.systemParametersService = systemParametersService;
        this.systemParametersQueryService = systemParametersQueryService;
    }

    /**
     * {@code POST  /system-parameters} : Create a new systemParameters.
     *
     * @param systemParametersDTO the systemParametersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemParametersDTO, or with status {@code 400 (Bad Request)} if the systemParameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-parameters")
    public ResponseEntity<SystemParametersDTO> createSystemParameters(@Valid @RequestBody SystemParametersDTO systemParametersDTO) throws URISyntaxException {
        log.debug("REST request to save SystemParameters : {}", systemParametersDTO);
        if (systemParametersDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemParameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemParametersDTO result = systemParametersService.save(systemParametersDTO);
        return ResponseEntity.created(new URI("/api/system-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-parameters} : Updates an existing systemParameters.
     *
     * @param systemParametersDTO the systemParametersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemParametersDTO,
     * or with status {@code 400 (Bad Request)} if the systemParametersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemParametersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-parameters")
    public ResponseEntity<SystemParametersDTO> updateSystemParameters(@Valid @RequestBody SystemParametersDTO systemParametersDTO) throws URISyntaxException {
        log.debug("REST request to update SystemParameters : {}", systemParametersDTO);
        if (systemParametersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemParametersDTO result = systemParametersService.save(systemParametersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemParametersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-parameters} : get all the systemParameters.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemParameters in body.
     */
    @GetMapping("/system-parameters")
    public ResponseEntity<List<SystemParametersDTO>> getAllSystemParameters(SystemParametersCriteria criteria) {
        log.debug("REST request to get SystemParameters by criteria: {}", criteria);
        List<SystemParametersDTO> entityList = systemParametersQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /system-parameters/count} : count all the systemParameters.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/system-parameters/count")
    public ResponseEntity<Long> countSystemParameters(SystemParametersCriteria criteria) {
        log.debug("REST request to count SystemParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemParametersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-parameters/:id} : get the "id" systemParameters.
     *
     * @param id the id of the systemParametersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemParametersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-parameters/{id}")
    public ResponseEntity<SystemParametersDTO> getSystemParameters(@PathVariable Long id) {
        log.debug("REST request to get SystemParameters : {}", id);
        Optional<SystemParametersDTO> systemParametersDTO = systemParametersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemParametersDTO);
    }

    /**
     * {@code DELETE  /system-parameters/:id} : delete the "id" systemParameters.
     *
     * @param id the id of the systemParametersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-parameters/{id}")
    public ResponseEntity<Void> deleteSystemParameters(@PathVariable Long id) {
        log.debug("REST request to delete SystemParameters : {}", id);
        systemParametersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
