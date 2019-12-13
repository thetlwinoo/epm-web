package com.epmweb.server.web.rest;

import com.epmweb.server.service.CustomersService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CustomersDTO;
import com.epmweb.server.service.dto.CustomersCriteria;
import com.epmweb.server.service.CustomersQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Customers}.
 */
@RestController
@RequestMapping("/api")
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersService customersService;

    private final CustomersQueryService customersQueryService;

    public CustomersResource(CustomersService customersService, CustomersQueryService customersQueryService) {
        this.customersService = customersService;
        this.customersQueryService = customersQueryService;
    }

    /**
     * {@code POST  /customers} : Create a new customers.
     *
     * @param customersDTO the customersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customersDTO, or with status {@code 400 (Bad Request)} if the customers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomersDTO> createCustomers(@Valid @RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customersDTO);
        if (customersDTO.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers} : Updates an existing customers.
     *
     * @param customersDTO the customersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersDTO,
     * or with status {@code 400 (Bad Request)} if the customersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers")
    public ResponseEntity<CustomersDTO> updateCustomers(@Valid @RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to update Customers : {}", customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomersDTO>> getAllCustomers(CustomersCriteria criteria) {
        log.debug("REST request to get Customers by criteria: {}", criteria);
        List<CustomersDTO> entityList = customersQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /customers/count} : count all the customers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/customers/count")
    public ResponseEntity<Long> countCustomers(CustomersCriteria criteria) {
        log.debug("REST request to count Customers by criteria: {}", criteria);
        return ResponseEntity.ok().body(customersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customers.
     *
     * @param id the id of the customersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomersDTO> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<CustomersDTO> customersDTO = customersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersDTO);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customers.
     *
     * @param id the id of the customersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
