package com.epmweb.server.web.rest;

import com.epmweb.server.service.BusinessEntityContactService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.BusinessEntityContactDTO;
import com.epmweb.server.service.dto.BusinessEntityContactCriteria;
import com.epmweb.server.service.BusinessEntityContactQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.BusinessEntityContact}.
 */
@RestController
@RequestMapping("/api")
public class BusinessEntityContactResource {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityContactResource.class);

    private static final String ENTITY_NAME = "businessEntityContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessEntityContactService businessEntityContactService;

    private final BusinessEntityContactQueryService businessEntityContactQueryService;

    public BusinessEntityContactResource(BusinessEntityContactService businessEntityContactService, BusinessEntityContactQueryService businessEntityContactQueryService) {
        this.businessEntityContactService = businessEntityContactService;
        this.businessEntityContactQueryService = businessEntityContactQueryService;
    }

    /**
     * {@code POST  /business-entity-contacts} : Create a new businessEntityContact.
     *
     * @param businessEntityContactDTO the businessEntityContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessEntityContactDTO, or with status {@code 400 (Bad Request)} if the businessEntityContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-entity-contacts")
    public ResponseEntity<BusinessEntityContactDTO> createBusinessEntityContact(@RequestBody BusinessEntityContactDTO businessEntityContactDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessEntityContact : {}", businessEntityContactDTO);
        if (businessEntityContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessEntityContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessEntityContactDTO result = businessEntityContactService.save(businessEntityContactDTO);
        return ResponseEntity.created(new URI("/api/business-entity-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-entity-contacts} : Updates an existing businessEntityContact.
     *
     * @param businessEntityContactDTO the businessEntityContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessEntityContactDTO,
     * or with status {@code 400 (Bad Request)} if the businessEntityContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessEntityContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-entity-contacts")
    public ResponseEntity<BusinessEntityContactDTO> updateBusinessEntityContact(@RequestBody BusinessEntityContactDTO businessEntityContactDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessEntityContact : {}", businessEntityContactDTO);
        if (businessEntityContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessEntityContactDTO result = businessEntityContactService.save(businessEntityContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessEntityContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-entity-contacts} : get all the businessEntityContacts.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessEntityContacts in body.
     */
    @GetMapping("/business-entity-contacts")
    public ResponseEntity<List<BusinessEntityContactDTO>> getAllBusinessEntityContacts(BusinessEntityContactCriteria criteria) {
        log.debug("REST request to get BusinessEntityContacts by criteria: {}", criteria);
        List<BusinessEntityContactDTO> entityList = businessEntityContactQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /business-entity-contacts/count} : count all the businessEntityContacts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/business-entity-contacts/count")
    public ResponseEntity<Long> countBusinessEntityContacts(BusinessEntityContactCriteria criteria) {
        log.debug("REST request to count BusinessEntityContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessEntityContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /business-entity-contacts/:id} : get the "id" businessEntityContact.
     *
     * @param id the id of the businessEntityContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessEntityContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-entity-contacts/{id}")
    public ResponseEntity<BusinessEntityContactDTO> getBusinessEntityContact(@PathVariable Long id) {
        log.debug("REST request to get BusinessEntityContact : {}", id);
        Optional<BusinessEntityContactDTO> businessEntityContactDTO = businessEntityContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessEntityContactDTO);
    }

    /**
     * {@code DELETE  /business-entity-contacts/:id} : delete the "id" businessEntityContact.
     *
     * @param id the id of the businessEntityContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-entity-contacts/{id}")
    public ResponseEntity<Void> deleteBusinessEntityContact(@PathVariable Long id) {
        log.debug("REST request to delete BusinessEntityContact : {}", id);
        businessEntityContactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
