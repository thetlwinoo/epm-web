package com.epmweb.server.web.rest;

import com.epmweb.server.service.VehicleTemperaturesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.VehicleTemperaturesDTO;
import com.epmweb.server.service.dto.VehicleTemperaturesCriteria;
import com.epmweb.server.service.VehicleTemperaturesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.VehicleTemperatures}.
 */
@RestController
@RequestMapping("/api")
public class VehicleTemperaturesResource {

    private final Logger log = LoggerFactory.getLogger(VehicleTemperaturesResource.class);

    private static final String ENTITY_NAME = "vehicleTemperatures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleTemperaturesService vehicleTemperaturesService;

    private final VehicleTemperaturesQueryService vehicleTemperaturesQueryService;

    public VehicleTemperaturesResource(VehicleTemperaturesService vehicleTemperaturesService, VehicleTemperaturesQueryService vehicleTemperaturesQueryService) {
        this.vehicleTemperaturesService = vehicleTemperaturesService;
        this.vehicleTemperaturesQueryService = vehicleTemperaturesQueryService;
    }

    /**
     * {@code POST  /vehicle-temperatures} : Create a new vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the vehicleTemperaturesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleTemperaturesDTO, or with status {@code 400 (Bad Request)} if the vehicleTemperatures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-temperatures")
    public ResponseEntity<VehicleTemperaturesDTO> createVehicleTemperatures(@Valid @RequestBody VehicleTemperaturesDTO vehicleTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleTemperatures : {}", vehicleTemperaturesDTO);
        if (vehicleTemperaturesDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleTemperatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleTemperaturesDTO result = vehicleTemperaturesService.save(vehicleTemperaturesDTO);
        return ResponseEntity.created(new URI("/api/vehicle-temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-temperatures} : Updates an existing vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the vehicleTemperaturesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleTemperaturesDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleTemperaturesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleTemperaturesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-temperatures")
    public ResponseEntity<VehicleTemperaturesDTO> updateVehicleTemperatures(@Valid @RequestBody VehicleTemperaturesDTO vehicleTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleTemperatures : {}", vehicleTemperaturesDTO);
        if (vehicleTemperaturesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleTemperaturesDTO result = vehicleTemperaturesService.save(vehicleTemperaturesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleTemperaturesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vehicle-temperatures} : get all the vehicleTemperatures.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleTemperatures in body.
     */
    @GetMapping("/vehicle-temperatures")
    public ResponseEntity<List<VehicleTemperaturesDTO>> getAllVehicleTemperatures(VehicleTemperaturesCriteria criteria) {
        log.debug("REST request to get VehicleTemperatures by criteria: {}", criteria);
        List<VehicleTemperaturesDTO> entityList = vehicleTemperaturesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /vehicle-temperatures/count} : count all the vehicleTemperatures.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/vehicle-temperatures/count")
    public ResponseEntity<Long> countVehicleTemperatures(VehicleTemperaturesCriteria criteria) {
        log.debug("REST request to count VehicleTemperatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleTemperaturesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-temperatures/:id} : get the "id" vehicleTemperatures.
     *
     * @param id the id of the vehicleTemperaturesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleTemperaturesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-temperatures/{id}")
    public ResponseEntity<VehicleTemperaturesDTO> getVehicleTemperatures(@PathVariable Long id) {
        log.debug("REST request to get VehicleTemperatures : {}", id);
        Optional<VehicleTemperaturesDTO> vehicleTemperaturesDTO = vehicleTemperaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleTemperaturesDTO);
    }

    /**
     * {@code DELETE  /vehicle-temperatures/:id} : delete the "id" vehicleTemperatures.
     *
     * @param id the id of the vehicleTemperaturesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-temperatures/{id}")
    public ResponseEntity<Void> deleteVehicleTemperatures(@PathVariable Long id) {
        log.debug("REST request to delete VehicleTemperatures : {}", id);
        vehicleTemperaturesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
