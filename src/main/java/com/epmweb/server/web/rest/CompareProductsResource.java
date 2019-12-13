package com.epmweb.server.web.rest;

import com.epmweb.server.service.CompareProductsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CompareProductsDTO;
import com.epmweb.server.service.dto.CompareProductsCriteria;
import com.epmweb.server.service.CompareProductsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.CompareProducts}.
 */
@RestController
@RequestMapping("/api")
public class CompareProductsResource {

    private final Logger log = LoggerFactory.getLogger(CompareProductsResource.class);

    private static final String ENTITY_NAME = "compareProducts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompareProductsService compareProductsService;

    private final CompareProductsQueryService compareProductsQueryService;

    public CompareProductsResource(CompareProductsService compareProductsService, CompareProductsQueryService compareProductsQueryService) {
        this.compareProductsService = compareProductsService;
        this.compareProductsQueryService = compareProductsQueryService;
    }

    /**
     * {@code POST  /compare-products} : Create a new compareProducts.
     *
     * @param compareProductsDTO the compareProductsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compareProductsDTO, or with status {@code 400 (Bad Request)} if the compareProducts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compare-products")
    public ResponseEntity<CompareProductsDTO> createCompareProducts(@RequestBody CompareProductsDTO compareProductsDTO) throws URISyntaxException {
        log.debug("REST request to save CompareProducts : {}", compareProductsDTO);
        if (compareProductsDTO.getId() != null) {
            throw new BadRequestAlertException("A new compareProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompareProductsDTO result = compareProductsService.save(compareProductsDTO);
        return ResponseEntity.created(new URI("/api/compare-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compare-products} : Updates an existing compareProducts.
     *
     * @param compareProductsDTO the compareProductsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compareProductsDTO,
     * or with status {@code 400 (Bad Request)} if the compareProductsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compareProductsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compare-products")
    public ResponseEntity<CompareProductsDTO> updateCompareProducts(@RequestBody CompareProductsDTO compareProductsDTO) throws URISyntaxException {
        log.debug("REST request to update CompareProducts : {}", compareProductsDTO);
        if (compareProductsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompareProductsDTO result = compareProductsService.save(compareProductsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compareProductsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compare-products} : get all the compareProducts.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compareProducts in body.
     */
    @GetMapping("/compare-products")
    public ResponseEntity<List<CompareProductsDTO>> getAllCompareProducts(CompareProductsCriteria criteria) {
        log.debug("REST request to get CompareProducts by criteria: {}", criteria);
        List<CompareProductsDTO> entityList = compareProductsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /compare-products/count} : count all the compareProducts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/compare-products/count")
    public ResponseEntity<Long> countCompareProducts(CompareProductsCriteria criteria) {
        log.debug("REST request to count CompareProducts by criteria: {}", criteria);
        return ResponseEntity.ok().body(compareProductsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compare-products/:id} : get the "id" compareProducts.
     *
     * @param id the id of the compareProductsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compareProductsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compare-products/{id}")
    public ResponseEntity<CompareProductsDTO> getCompareProducts(@PathVariable Long id) {
        log.debug("REST request to get CompareProducts : {}", id);
        Optional<CompareProductsDTO> compareProductsDTO = compareProductsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compareProductsDTO);
    }

    /**
     * {@code DELETE  /compare-products/:id} : delete the "id" compareProducts.
     *
     * @param id the id of the compareProductsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compare-products/{id}")
    public ResponseEntity<Void> deleteCompareProducts(@PathVariable Long id) {
        log.debug("REST request to delete CompareProducts : {}", id);
        compareProductsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
