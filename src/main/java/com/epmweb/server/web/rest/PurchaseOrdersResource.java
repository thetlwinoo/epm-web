package com.epmweb.server.web.rest;

import com.epmweb.server.service.PurchaseOrdersService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PurchaseOrdersDTO;
import com.epmweb.server.service.dto.PurchaseOrdersCriteria;
import com.epmweb.server.service.PurchaseOrdersQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.PurchaseOrders}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrdersResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrdersResource.class);

    private static final String ENTITY_NAME = "purchaseOrders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseOrdersService purchaseOrdersService;

    private final PurchaseOrdersQueryService purchaseOrdersQueryService;

    public PurchaseOrdersResource(PurchaseOrdersService purchaseOrdersService, PurchaseOrdersQueryService purchaseOrdersQueryService) {
        this.purchaseOrdersService = purchaseOrdersService;
        this.purchaseOrdersQueryService = purchaseOrdersQueryService;
    }

    /**
     * {@code POST  /purchase-orders} : Create a new purchaseOrders.
     *
     * @param purchaseOrdersDTO the purchaseOrdersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseOrdersDTO, or with status {@code 400 (Bad Request)} if the purchaseOrders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrdersDTO> createPurchaseOrders(@Valid @RequestBody PurchaseOrdersDTO purchaseOrdersDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrders : {}", purchaseOrdersDTO);
        if (purchaseOrdersDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrdersDTO result = purchaseOrdersService.save(purchaseOrdersDTO);
        return ResponseEntity.created(new URI("/api/purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-orders} : Updates an existing purchaseOrders.
     *
     * @param purchaseOrdersDTO the purchaseOrdersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseOrdersDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseOrdersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseOrdersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrdersDTO> updatePurchaseOrders(@Valid @RequestBody PurchaseOrdersDTO purchaseOrdersDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrders : {}", purchaseOrdersDTO);
        if (purchaseOrdersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrdersDTO result = purchaseOrdersService.save(purchaseOrdersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseOrdersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-orders} : get all the purchaseOrders.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseOrders in body.
     */
    @GetMapping("/purchase-orders")
    public ResponseEntity<List<PurchaseOrdersDTO>> getAllPurchaseOrders(PurchaseOrdersCriteria criteria) {
        log.debug("REST request to get PurchaseOrders by criteria: {}", criteria);
        List<PurchaseOrdersDTO> entityList = purchaseOrdersQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /purchase-orders/count} : count all the purchaseOrders.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/purchase-orders/count")
    public ResponseEntity<Long> countPurchaseOrders(PurchaseOrdersCriteria criteria) {
        log.debug("REST request to count PurchaseOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(purchaseOrdersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrders.
     *
     * @param id the id of the purchaseOrdersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrdersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-orders/{id}")
    public ResponseEntity<PurchaseOrdersDTO> getPurchaseOrders(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrders : {}", id);
        Optional<PurchaseOrdersDTO> purchaseOrdersDTO = purchaseOrdersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrdersDTO);
    }

    /**
     * {@code DELETE  /purchase-orders/:id} : delete the "id" purchaseOrders.
     *
     * @param id the id of the purchaseOrdersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-orders/{id}")
    public ResponseEntity<Void> deletePurchaseOrders(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrders : {}", id);
        purchaseOrdersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
