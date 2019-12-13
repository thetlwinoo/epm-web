package com.epmweb.server.web.rest;

import com.epmweb.server.service.CustomerCategoriesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CustomerCategoriesDTO;
import com.epmweb.server.service.dto.CustomerCategoriesCriteria;
import com.epmweb.server.service.CustomerCategoriesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.CustomerCategories}.
 */
@RestController
@RequestMapping("/api")
public class CustomerCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoriesResource.class);

    private static final String ENTITY_NAME = "customerCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerCategoriesService customerCategoriesService;

    private final CustomerCategoriesQueryService customerCategoriesQueryService;

    public CustomerCategoriesResource(CustomerCategoriesService customerCategoriesService, CustomerCategoriesQueryService customerCategoriesQueryService) {
        this.customerCategoriesService = customerCategoriesService;
        this.customerCategoriesQueryService = customerCategoriesQueryService;
    }

    /**
     * {@code POST  /customer-categories} : Create a new customerCategories.
     *
     * @param customerCategoriesDTO the customerCategoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerCategoriesDTO, or with status {@code 400 (Bad Request)} if the customerCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-categories")
    public ResponseEntity<CustomerCategoriesDTO> createCustomerCategories(@Valid @RequestBody CustomerCategoriesDTO customerCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerCategories : {}", customerCategoriesDTO);
        if (customerCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerCategoriesDTO result = customerCategoriesService.save(customerCategoriesDTO);
        return ResponseEntity.created(new URI("/api/customer-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-categories} : Updates an existing customerCategories.
     *
     * @param customerCategoriesDTO the customerCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the customerCategoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-categories")
    public ResponseEntity<CustomerCategoriesDTO> updateCustomerCategories(@Valid @RequestBody CustomerCategoriesDTO customerCategoriesDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerCategories : {}", customerCategoriesDTO);
        if (customerCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerCategoriesDTO result = customerCategoriesService.save(customerCategoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-categories} : get all the customerCategories.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerCategories in body.
     */
    @GetMapping("/customer-categories")
    public ResponseEntity<List<CustomerCategoriesDTO>> getAllCustomerCategories(CustomerCategoriesCriteria criteria) {
        log.debug("REST request to get CustomerCategories by criteria: {}", criteria);
        List<CustomerCategoriesDTO> entityList = customerCategoriesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /customer-categories/count} : count all the customerCategories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/customer-categories/count")
    public ResponseEntity<Long> countCustomerCategories(CustomerCategoriesCriteria criteria) {
        log.debug("REST request to count CustomerCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerCategoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-categories/:id} : get the "id" customerCategories.
     *
     * @param id the id of the customerCategoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerCategoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-categories/{id}")
    public ResponseEntity<CustomerCategoriesDTO> getCustomerCategories(@PathVariable Long id) {
        log.debug("REST request to get CustomerCategories : {}", id);
        Optional<CustomerCategoriesDTO> customerCategoriesDTO = customerCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerCategoriesDTO);
    }

    /**
     * {@code DELETE  /customer-categories/:id} : delete the "id" customerCategories.
     *
     * @param id the id of the customerCategoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-categories/{id}")
    public ResponseEntity<Void> deleteCustomerCategories(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCategories : {}", id);
        customerCategoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
