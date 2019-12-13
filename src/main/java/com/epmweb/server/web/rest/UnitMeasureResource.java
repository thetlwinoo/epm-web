package com.epmweb.server.web.rest;

import com.epmweb.server.service.UnitMeasureService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.UnitMeasureDTO;
import com.epmweb.server.service.dto.UnitMeasureCriteria;
import com.epmweb.server.service.UnitMeasureQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.UnitMeasure}.
 */
@RestController
@RequestMapping("/api")
public class UnitMeasureResource {

    private final Logger log = LoggerFactory.getLogger(UnitMeasureResource.class);

    private static final String ENTITY_NAME = "unitMeasure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitMeasureService unitMeasureService;

    private final UnitMeasureQueryService unitMeasureQueryService;

    public UnitMeasureResource(UnitMeasureService unitMeasureService, UnitMeasureQueryService unitMeasureQueryService) {
        this.unitMeasureService = unitMeasureService;
        this.unitMeasureQueryService = unitMeasureQueryService;
    }

    /**
     * {@code POST  /unit-measures} : Create a new unitMeasure.
     *
     * @param unitMeasureDTO the unitMeasureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitMeasureDTO, or with status {@code 400 (Bad Request)} if the unitMeasure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unit-measures")
    public ResponseEntity<UnitMeasureDTO> createUnitMeasure(@Valid @RequestBody UnitMeasureDTO unitMeasureDTO) throws URISyntaxException {
        log.debug("REST request to save UnitMeasure : {}", unitMeasureDTO);
        if (unitMeasureDTO.getId() != null) {
            throw new BadRequestAlertException("A new unitMeasure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitMeasureDTO result = unitMeasureService.save(unitMeasureDTO);
        return ResponseEntity.created(new URI("/api/unit-measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unit-measures} : Updates an existing unitMeasure.
     *
     * @param unitMeasureDTO the unitMeasureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitMeasureDTO,
     * or with status {@code 400 (Bad Request)} if the unitMeasureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitMeasureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unit-measures")
    public ResponseEntity<UnitMeasureDTO> updateUnitMeasure(@Valid @RequestBody UnitMeasureDTO unitMeasureDTO) throws URISyntaxException {
        log.debug("REST request to update UnitMeasure : {}", unitMeasureDTO);
        if (unitMeasureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitMeasureDTO result = unitMeasureService.save(unitMeasureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitMeasureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unit-measures} : get all the unitMeasures.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unitMeasures in body.
     */
    @GetMapping("/unit-measures")
    public ResponseEntity<List<UnitMeasureDTO>> getAllUnitMeasures(UnitMeasureCriteria criteria) {
        log.debug("REST request to get UnitMeasures by criteria: {}", criteria);
        List<UnitMeasureDTO> entityList = unitMeasureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /unit-measures/count} : count all the unitMeasures.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/unit-measures/count")
    public ResponseEntity<Long> countUnitMeasures(UnitMeasureCriteria criteria) {
        log.debug("REST request to count UnitMeasures by criteria: {}", criteria);
        return ResponseEntity.ok().body(unitMeasureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /unit-measures/:id} : get the "id" unitMeasure.
     *
     * @param id the id of the unitMeasureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitMeasureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unit-measures/{id}")
    public ResponseEntity<UnitMeasureDTO> getUnitMeasure(@PathVariable Long id) {
        log.debug("REST request to get UnitMeasure : {}", id);
        Optional<UnitMeasureDTO> unitMeasureDTO = unitMeasureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitMeasureDTO);
    }

    /**
     * {@code DELETE  /unit-measures/:id} : delete the "id" unitMeasure.
     *
     * @param id the id of the unitMeasureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unit-measures/{id}")
    public ResponseEntity<Void> deleteUnitMeasure(@PathVariable Long id) {
        log.debug("REST request to delete UnitMeasure : {}", id);
        unitMeasureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
