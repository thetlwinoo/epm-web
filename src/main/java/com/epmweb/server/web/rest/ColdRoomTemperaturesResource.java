package com.epmweb.server.web.rest;

import com.epmweb.server.service.ColdRoomTemperaturesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ColdRoomTemperaturesDTO;
import com.epmweb.server.service.dto.ColdRoomTemperaturesCriteria;
import com.epmweb.server.service.ColdRoomTemperaturesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ColdRoomTemperatures}.
 */
@RestController
@RequestMapping("/api")
public class ColdRoomTemperaturesResource {

    private final Logger log = LoggerFactory.getLogger(ColdRoomTemperaturesResource.class);

    private static final String ENTITY_NAME = "coldRoomTemperatures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColdRoomTemperaturesService coldRoomTemperaturesService;

    private final ColdRoomTemperaturesQueryService coldRoomTemperaturesQueryService;

    public ColdRoomTemperaturesResource(ColdRoomTemperaturesService coldRoomTemperaturesService, ColdRoomTemperaturesQueryService coldRoomTemperaturesQueryService) {
        this.coldRoomTemperaturesService = coldRoomTemperaturesService;
        this.coldRoomTemperaturesQueryService = coldRoomTemperaturesQueryService;
    }

    /**
     * {@code POST  /cold-room-temperatures} : Create a new coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the coldRoomTemperaturesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coldRoomTemperaturesDTO, or with status {@code 400 (Bad Request)} if the coldRoomTemperatures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cold-room-temperatures")
    public ResponseEntity<ColdRoomTemperaturesDTO> createColdRoomTemperatures(@Valid @RequestBody ColdRoomTemperaturesDTO coldRoomTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to save ColdRoomTemperatures : {}", coldRoomTemperaturesDTO);
        if (coldRoomTemperaturesDTO.getId() != null) {
            throw new BadRequestAlertException("A new coldRoomTemperatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColdRoomTemperaturesDTO result = coldRoomTemperaturesService.save(coldRoomTemperaturesDTO);
        return ResponseEntity.created(new URI("/api/cold-room-temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cold-room-temperatures} : Updates an existing coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the coldRoomTemperaturesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coldRoomTemperaturesDTO,
     * or with status {@code 400 (Bad Request)} if the coldRoomTemperaturesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coldRoomTemperaturesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cold-room-temperatures")
    public ResponseEntity<ColdRoomTemperaturesDTO> updateColdRoomTemperatures(@Valid @RequestBody ColdRoomTemperaturesDTO coldRoomTemperaturesDTO) throws URISyntaxException {
        log.debug("REST request to update ColdRoomTemperatures : {}", coldRoomTemperaturesDTO);
        if (coldRoomTemperaturesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColdRoomTemperaturesDTO result = coldRoomTemperaturesService.save(coldRoomTemperaturesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coldRoomTemperaturesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cold-room-temperatures} : get all the coldRoomTemperatures.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coldRoomTemperatures in body.
     */
    @GetMapping("/cold-room-temperatures")
    public ResponseEntity<List<ColdRoomTemperaturesDTO>> getAllColdRoomTemperatures(ColdRoomTemperaturesCriteria criteria) {
        log.debug("REST request to get ColdRoomTemperatures by criteria: {}", criteria);
        List<ColdRoomTemperaturesDTO> entityList = coldRoomTemperaturesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /cold-room-temperatures/count} : count all the coldRoomTemperatures.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cold-room-temperatures/count")
    public ResponseEntity<Long> countColdRoomTemperatures(ColdRoomTemperaturesCriteria criteria) {
        log.debug("REST request to count ColdRoomTemperatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(coldRoomTemperaturesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cold-room-temperatures/:id} : get the "id" coldRoomTemperatures.
     *
     * @param id the id of the coldRoomTemperaturesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coldRoomTemperaturesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cold-room-temperatures/{id}")
    public ResponseEntity<ColdRoomTemperaturesDTO> getColdRoomTemperatures(@PathVariable Long id) {
        log.debug("REST request to get ColdRoomTemperatures : {}", id);
        Optional<ColdRoomTemperaturesDTO> coldRoomTemperaturesDTO = coldRoomTemperaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coldRoomTemperaturesDTO);
    }

    /**
     * {@code DELETE  /cold-room-temperatures/:id} : delete the "id" coldRoomTemperatures.
     *
     * @param id the id of the coldRoomTemperaturesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cold-room-temperatures/{id}")
    public ResponseEntity<Void> deleteColdRoomTemperatures(@PathVariable Long id) {
        log.debug("REST request to delete ColdRoomTemperatures : {}", id);
        coldRoomTemperaturesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
