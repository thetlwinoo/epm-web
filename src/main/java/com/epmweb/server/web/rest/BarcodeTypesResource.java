package com.epmweb.server.web.rest;

import com.epmweb.server.service.BarcodeTypesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.BarcodeTypesDTO;
import com.epmweb.server.service.dto.BarcodeTypesCriteria;
import com.epmweb.server.service.BarcodeTypesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.BarcodeTypes}.
 */
@RestController
@RequestMapping("/api")
public class BarcodeTypesResource {

    private final Logger log = LoggerFactory.getLogger(BarcodeTypesResource.class);

    private static final String ENTITY_NAME = "barcodeTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BarcodeTypesService barcodeTypesService;

    private final BarcodeTypesQueryService barcodeTypesQueryService;

    public BarcodeTypesResource(BarcodeTypesService barcodeTypesService, BarcodeTypesQueryService barcodeTypesQueryService) {
        this.barcodeTypesService = barcodeTypesService;
        this.barcodeTypesQueryService = barcodeTypesQueryService;
    }

    /**
     * {@code POST  /barcode-types} : Create a new barcodeTypes.
     *
     * @param barcodeTypesDTO the barcodeTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barcodeTypesDTO, or with status {@code 400 (Bad Request)} if the barcodeTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/barcode-types")
    public ResponseEntity<BarcodeTypesDTO> createBarcodeTypes(@Valid @RequestBody BarcodeTypesDTO barcodeTypesDTO) throws URISyntaxException {
        log.debug("REST request to save BarcodeTypes : {}", barcodeTypesDTO);
        if (barcodeTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new barcodeTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BarcodeTypesDTO result = barcodeTypesService.save(barcodeTypesDTO);
        return ResponseEntity.created(new URI("/api/barcode-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /barcode-types} : Updates an existing barcodeTypes.
     *
     * @param barcodeTypesDTO the barcodeTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barcodeTypesDTO,
     * or with status {@code 400 (Bad Request)} if the barcodeTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the barcodeTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/barcode-types")
    public ResponseEntity<BarcodeTypesDTO> updateBarcodeTypes(@Valid @RequestBody BarcodeTypesDTO barcodeTypesDTO) throws URISyntaxException {
        log.debug("REST request to update BarcodeTypes : {}", barcodeTypesDTO);
        if (barcodeTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BarcodeTypesDTO result = barcodeTypesService.save(barcodeTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, barcodeTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /barcode-types} : get all the barcodeTypes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of barcodeTypes in body.
     */
    @GetMapping("/barcode-types")
    public ResponseEntity<List<BarcodeTypesDTO>> getAllBarcodeTypes(BarcodeTypesCriteria criteria) {
        log.debug("REST request to get BarcodeTypes by criteria: {}", criteria);
        List<BarcodeTypesDTO> entityList = barcodeTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /barcode-types/count} : count all the barcodeTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/barcode-types/count")
    public ResponseEntity<Long> countBarcodeTypes(BarcodeTypesCriteria criteria) {
        log.debug("REST request to count BarcodeTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(barcodeTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /barcode-types/:id} : get the "id" barcodeTypes.
     *
     * @param id the id of the barcodeTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the barcodeTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/barcode-types/{id}")
    public ResponseEntity<BarcodeTypesDTO> getBarcodeTypes(@PathVariable Long id) {
        log.debug("REST request to get BarcodeTypes : {}", id);
        Optional<BarcodeTypesDTO> barcodeTypesDTO = barcodeTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barcodeTypesDTO);
    }

    /**
     * {@code DELETE  /barcode-types/:id} : delete the "id" barcodeTypes.
     *
     * @param id the id of the barcodeTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/barcode-types/{id}")
    public ResponseEntity<Void> deleteBarcodeTypes(@PathVariable Long id) {
        log.debug("REST request to delete BarcodeTypes : {}", id);
        barcodeTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
