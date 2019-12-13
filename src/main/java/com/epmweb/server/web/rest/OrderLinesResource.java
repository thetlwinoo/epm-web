package com.epmweb.server.web.rest;

import com.epmweb.server.service.OrderLinesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.OrderLinesDTO;
import com.epmweb.server.service.dto.OrderLinesCriteria;
import com.epmweb.server.service.OrderLinesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.OrderLines}.
 */
@RestController
@RequestMapping("/api")
public class OrderLinesResource {

    private final Logger log = LoggerFactory.getLogger(OrderLinesResource.class);

    private static final String ENTITY_NAME = "orderLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderLinesService orderLinesService;

    private final OrderLinesQueryService orderLinesQueryService;

    public OrderLinesResource(OrderLinesService orderLinesService, OrderLinesQueryService orderLinesQueryService) {
        this.orderLinesService = orderLinesService;
        this.orderLinesQueryService = orderLinesQueryService;
    }

    /**
     * {@code POST  /order-lines} : Create a new orderLines.
     *
     * @param orderLinesDTO the orderLinesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderLinesDTO, or with status {@code 400 (Bad Request)} if the orderLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-lines")
    public ResponseEntity<OrderLinesDTO> createOrderLines(@Valid @RequestBody OrderLinesDTO orderLinesDTO) throws URISyntaxException {
        log.debug("REST request to save OrderLines : {}", orderLinesDTO);
        if (orderLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderLinesDTO result = orderLinesService.save(orderLinesDTO);
        return ResponseEntity.created(new URI("/api/order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-lines} : Updates an existing orderLines.
     *
     * @param orderLinesDTO the orderLinesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderLinesDTO,
     * or with status {@code 400 (Bad Request)} if the orderLinesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderLinesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-lines")
    public ResponseEntity<OrderLinesDTO> updateOrderLines(@Valid @RequestBody OrderLinesDTO orderLinesDTO) throws URISyntaxException {
        log.debug("REST request to update OrderLines : {}", orderLinesDTO);
        if (orderLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderLinesDTO result = orderLinesService.save(orderLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-lines} : get all the orderLines.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderLines in body.
     */
    @GetMapping("/order-lines")
    public ResponseEntity<List<OrderLinesDTO>> getAllOrderLines(OrderLinesCriteria criteria) {
        log.debug("REST request to get OrderLines by criteria: {}", criteria);
        List<OrderLinesDTO> entityList = orderLinesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /order-lines/count} : count all the orderLines.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/order-lines/count")
    public ResponseEntity<Long> countOrderLines(OrderLinesCriteria criteria) {
        log.debug("REST request to count OrderLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-lines/:id} : get the "id" orderLines.
     *
     * @param id the id of the orderLinesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderLinesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-lines/{id}")
    public ResponseEntity<OrderLinesDTO> getOrderLines(@PathVariable Long id) {
        log.debug("REST request to get OrderLines : {}", id);
        Optional<OrderLinesDTO> orderLinesDTO = orderLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderLinesDTO);
    }

    /**
     * {@code DELETE  /order-lines/:id} : delete the "id" orderLines.
     *
     * @param id the id of the orderLinesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-lines/{id}")
    public ResponseEntity<Void> deleteOrderLines(@PathVariable Long id) {
        log.debug("REST request to delete OrderLines : {}", id);
        orderLinesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
