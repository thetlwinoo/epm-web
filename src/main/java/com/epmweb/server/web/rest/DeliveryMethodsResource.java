package com.epmweb.server.web.rest;

import com.epmweb.server.service.DeliveryMethodsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.DeliveryMethodsDTO;
import com.epmweb.server.service.dto.DeliveryMethodsCriteria;
import com.epmweb.server.service.DeliveryMethodsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.DeliveryMethods}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryMethodsResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryMethodsResource.class);

    private static final String ENTITY_NAME = "deliveryMethods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryMethodsService deliveryMethodsService;

    private final DeliveryMethodsQueryService deliveryMethodsQueryService;

    public DeliveryMethodsResource(DeliveryMethodsService deliveryMethodsService, DeliveryMethodsQueryService deliveryMethodsQueryService) {
        this.deliveryMethodsService = deliveryMethodsService;
        this.deliveryMethodsQueryService = deliveryMethodsQueryService;
    }

    /**
     * {@code POST  /delivery-methods} : Create a new deliveryMethods.
     *
     * @param deliveryMethodsDTO the deliveryMethodsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryMethodsDTO, or with status {@code 400 (Bad Request)} if the deliveryMethods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-methods")
    public ResponseEntity<DeliveryMethodsDTO> createDeliveryMethods(@Valid @RequestBody DeliveryMethodsDTO deliveryMethodsDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryMethods : {}", deliveryMethodsDTO);
        if (deliveryMethodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryMethods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryMethodsDTO result = deliveryMethodsService.save(deliveryMethodsDTO);
        return ResponseEntity.created(new URI("/api/delivery-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-methods} : Updates an existing deliveryMethods.
     *
     * @param deliveryMethodsDTO the deliveryMethodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryMethodsDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryMethodsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryMethodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-methods")
    public ResponseEntity<DeliveryMethodsDTO> updateDeliveryMethods(@Valid @RequestBody DeliveryMethodsDTO deliveryMethodsDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryMethods : {}", deliveryMethodsDTO);
        if (deliveryMethodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryMethodsDTO result = deliveryMethodsService.save(deliveryMethodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryMethodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-methods} : get all the deliveryMethods.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryMethods in body.
     */
    @GetMapping("/delivery-methods")
    public ResponseEntity<List<DeliveryMethodsDTO>> getAllDeliveryMethods(DeliveryMethodsCriteria criteria) {
        log.debug("REST request to get DeliveryMethods by criteria: {}", criteria);
        List<DeliveryMethodsDTO> entityList = deliveryMethodsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /delivery-methods/count} : count all the deliveryMethods.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/delivery-methods/count")
    public ResponseEntity<Long> countDeliveryMethods(DeliveryMethodsCriteria criteria) {
        log.debug("REST request to count DeliveryMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(deliveryMethodsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /delivery-methods/:id} : get the "id" deliveryMethods.
     *
     * @param id the id of the deliveryMethodsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryMethodsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-methods/{id}")
    public ResponseEntity<DeliveryMethodsDTO> getDeliveryMethods(@PathVariable Long id) {
        log.debug("REST request to get DeliveryMethods : {}", id);
        Optional<DeliveryMethodsDTO> deliveryMethodsDTO = deliveryMethodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryMethodsDTO);
    }

    /**
     * {@code DELETE  /delivery-methods/:id} : delete the "id" deliveryMethods.
     *
     * @param id the id of the deliveryMethodsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-methods/{id}")
    public ResponseEntity<Void> deleteDeliveryMethods(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryMethods : {}", id);
        deliveryMethodsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
