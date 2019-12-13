package com.epmweb.server.web.rest;

import com.epmweb.server.service.PurchaseOrderLinesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PurchaseOrderLinesDTO;
import com.epmweb.server.service.dto.PurchaseOrderLinesCriteria;
import com.epmweb.server.service.PurchaseOrderLinesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.PurchaseOrderLines}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderLinesResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderLinesResource.class);

    private static final String ENTITY_NAME = "purchaseOrderLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseOrderLinesService purchaseOrderLinesService;

    private final PurchaseOrderLinesQueryService purchaseOrderLinesQueryService;

    public PurchaseOrderLinesResource(PurchaseOrderLinesService purchaseOrderLinesService, PurchaseOrderLinesQueryService purchaseOrderLinesQueryService) {
        this.purchaseOrderLinesService = purchaseOrderLinesService;
        this.purchaseOrderLinesQueryService = purchaseOrderLinesQueryService;
    }

    /**
     * {@code POST  /purchase-order-lines} : Create a new purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the purchaseOrderLinesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseOrderLinesDTO, or with status {@code 400 (Bad Request)} if the purchaseOrderLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-order-lines")
    public ResponseEntity<PurchaseOrderLinesDTO> createPurchaseOrderLines(@Valid @RequestBody PurchaseOrderLinesDTO purchaseOrderLinesDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderLines : {}", purchaseOrderLinesDTO);
        if (purchaseOrderLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrderLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrderLinesDTO result = purchaseOrderLinesService.save(purchaseOrderLinesDTO);
        return ResponseEntity.created(new URI("/api/purchase-order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-order-lines} : Updates an existing purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the purchaseOrderLinesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseOrderLinesDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseOrderLinesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseOrderLinesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-order-lines")
    public ResponseEntity<PurchaseOrderLinesDTO> updatePurchaseOrderLines(@Valid @RequestBody PurchaseOrderLinesDTO purchaseOrderLinesDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderLines : {}", purchaseOrderLinesDTO);
        if (purchaseOrderLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrderLinesDTO result = purchaseOrderLinesService.save(purchaseOrderLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseOrderLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-order-lines} : get all the purchaseOrderLines.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseOrderLines in body.
     */
    @GetMapping("/purchase-order-lines")
    public ResponseEntity<List<PurchaseOrderLinesDTO>> getAllPurchaseOrderLines(PurchaseOrderLinesCriteria criteria) {
        log.debug("REST request to get PurchaseOrderLines by criteria: {}", criteria);
        List<PurchaseOrderLinesDTO> entityList = purchaseOrderLinesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /purchase-order-lines/count} : count all the purchaseOrderLines.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/purchase-order-lines/count")
    public ResponseEntity<Long> countPurchaseOrderLines(PurchaseOrderLinesCriteria criteria) {
        log.debug("REST request to count PurchaseOrderLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(purchaseOrderLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /purchase-order-lines/:id} : get the "id" purchaseOrderLines.
     *
     * @param id the id of the purchaseOrderLinesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrderLinesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-order-lines/{id}")
    public ResponseEntity<PurchaseOrderLinesDTO> getPurchaseOrderLines(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderLines : {}", id);
        Optional<PurchaseOrderLinesDTO> purchaseOrderLinesDTO = purchaseOrderLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrderLinesDTO);
    }

    /**
     * {@code DELETE  /purchase-order-lines/:id} : delete the "id" purchaseOrderLines.
     *
     * @param id the id of the purchaseOrderLinesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-order-lines/{id}")
    public ResponseEntity<Void> deletePurchaseOrderLines(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderLines : {}", id);
        purchaseOrderLinesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
