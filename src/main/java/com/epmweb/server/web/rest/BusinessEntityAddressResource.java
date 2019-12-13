package com.epmweb.server.web.rest;

import com.epmweb.server.service.BusinessEntityAddressService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.BusinessEntityAddressDTO;
import com.epmweb.server.service.dto.BusinessEntityAddressCriteria;
import com.epmweb.server.service.BusinessEntityAddressQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.BusinessEntityAddress}.
 */
@RestController
@RequestMapping("/api")
public class BusinessEntityAddressResource {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityAddressResource.class);

    private static final String ENTITY_NAME = "businessEntityAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessEntityAddressService businessEntityAddressService;

    private final BusinessEntityAddressQueryService businessEntityAddressQueryService;

    public BusinessEntityAddressResource(BusinessEntityAddressService businessEntityAddressService, BusinessEntityAddressQueryService businessEntityAddressQueryService) {
        this.businessEntityAddressService = businessEntityAddressService;
        this.businessEntityAddressQueryService = businessEntityAddressQueryService;
    }

    /**
     * {@code POST  /business-entity-addresses} : Create a new businessEntityAddress.
     *
     * @param businessEntityAddressDTO the businessEntityAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessEntityAddressDTO, or with status {@code 400 (Bad Request)} if the businessEntityAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-entity-addresses")
    public ResponseEntity<BusinessEntityAddressDTO> createBusinessEntityAddress(@RequestBody BusinessEntityAddressDTO businessEntityAddressDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessEntityAddress : {}", businessEntityAddressDTO);
        if (businessEntityAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessEntityAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessEntityAddressDTO result = businessEntityAddressService.save(businessEntityAddressDTO);
        return ResponseEntity.created(new URI("/api/business-entity-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-entity-addresses} : Updates an existing businessEntityAddress.
     *
     * @param businessEntityAddressDTO the businessEntityAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessEntityAddressDTO,
     * or with status {@code 400 (Bad Request)} if the businessEntityAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessEntityAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-entity-addresses")
    public ResponseEntity<BusinessEntityAddressDTO> updateBusinessEntityAddress(@RequestBody BusinessEntityAddressDTO businessEntityAddressDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessEntityAddress : {}", businessEntityAddressDTO);
        if (businessEntityAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessEntityAddressDTO result = businessEntityAddressService.save(businessEntityAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessEntityAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-entity-addresses} : get all the businessEntityAddresses.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessEntityAddresses in body.
     */
    @GetMapping("/business-entity-addresses")
    public ResponseEntity<List<BusinessEntityAddressDTO>> getAllBusinessEntityAddresses(BusinessEntityAddressCriteria criteria) {
        log.debug("REST request to get BusinessEntityAddresses by criteria: {}", criteria);
        List<BusinessEntityAddressDTO> entityList = businessEntityAddressQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /business-entity-addresses/count} : count all the businessEntityAddresses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/business-entity-addresses/count")
    public ResponseEntity<Long> countBusinessEntityAddresses(BusinessEntityAddressCriteria criteria) {
        log.debug("REST request to count BusinessEntityAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessEntityAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /business-entity-addresses/:id} : get the "id" businessEntityAddress.
     *
     * @param id the id of the businessEntityAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessEntityAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-entity-addresses/{id}")
    public ResponseEntity<BusinessEntityAddressDTO> getBusinessEntityAddress(@PathVariable Long id) {
        log.debug("REST request to get BusinessEntityAddress : {}", id);
        Optional<BusinessEntityAddressDTO> businessEntityAddressDTO = businessEntityAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessEntityAddressDTO);
    }

    /**
     * {@code DELETE  /business-entity-addresses/:id} : delete the "id" businessEntityAddress.
     *
     * @param id the id of the businessEntityAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-entity-addresses/{id}")
    public ResponseEntity<Void> deleteBusinessEntityAddress(@PathVariable Long id) {
        log.debug("REST request to delete BusinessEntityAddress : {}", id);
        businessEntityAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
