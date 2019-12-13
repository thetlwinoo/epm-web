package com.epmweb.server.web.rest;

import com.epmweb.server.service.ShipMethodService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ShipMethodDTO;
import com.epmweb.server.service.dto.ShipMethodCriteria;
import com.epmweb.server.service.ShipMethodQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ShipMethod}.
 */
@RestController
@RequestMapping("/api")
public class ShipMethodResource {

    private final Logger log = LoggerFactory.getLogger(ShipMethodResource.class);

    private static final String ENTITY_NAME = "shipMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipMethodService shipMethodService;

    private final ShipMethodQueryService shipMethodQueryService;

    public ShipMethodResource(ShipMethodService shipMethodService, ShipMethodQueryService shipMethodQueryService) {
        this.shipMethodService = shipMethodService;
        this.shipMethodQueryService = shipMethodQueryService;
    }

    /**
     * {@code POST  /ship-methods} : Create a new shipMethod.
     *
     * @param shipMethodDTO the shipMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipMethodDTO, or with status {@code 400 (Bad Request)} if the shipMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ship-methods")
    public ResponseEntity<ShipMethodDTO> createShipMethod(@RequestBody ShipMethodDTO shipMethodDTO) throws URISyntaxException {
        log.debug("REST request to save ShipMethod : {}", shipMethodDTO);
        if (shipMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipMethodDTO result = shipMethodService.save(shipMethodDTO);
        return ResponseEntity.created(new URI("/api/ship-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ship-methods} : Updates an existing shipMethod.
     *
     * @param shipMethodDTO the shipMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipMethodDTO,
     * or with status {@code 400 (Bad Request)} if the shipMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ship-methods")
    public ResponseEntity<ShipMethodDTO> updateShipMethod(@RequestBody ShipMethodDTO shipMethodDTO) throws URISyntaxException {
        log.debug("REST request to update ShipMethod : {}", shipMethodDTO);
        if (shipMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipMethodDTO result = shipMethodService.save(shipMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ship-methods} : get all the shipMethods.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipMethods in body.
     */
    @GetMapping("/ship-methods")
    public ResponseEntity<List<ShipMethodDTO>> getAllShipMethods(ShipMethodCriteria criteria) {
        log.debug("REST request to get ShipMethods by criteria: {}", criteria);
        List<ShipMethodDTO> entityList = shipMethodQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /ship-methods/count} : count all the shipMethods.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/ship-methods/count")
    public ResponseEntity<Long> countShipMethods(ShipMethodCriteria criteria) {
        log.debug("REST request to count ShipMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(shipMethodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ship-methods/:id} : get the "id" shipMethod.
     *
     * @param id the id of the shipMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ship-methods/{id}")
    public ResponseEntity<ShipMethodDTO> getShipMethod(@PathVariable Long id) {
        log.debug("REST request to get ShipMethod : {}", id);
        Optional<ShipMethodDTO> shipMethodDTO = shipMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipMethodDTO);
    }

    /**
     * {@code DELETE  /ship-methods/:id} : delete the "id" shipMethod.
     *
     * @param id the id of the shipMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ship-methods/{id}")
    public ResponseEntity<Void> deleteShipMethod(@PathVariable Long id) {
        log.debug("REST request to delete ShipMethod : {}", id);
        shipMethodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
