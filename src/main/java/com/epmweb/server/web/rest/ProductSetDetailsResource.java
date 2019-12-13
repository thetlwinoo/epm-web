package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductSetDetailsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductSetDetailsDTO;
import com.epmweb.server.service.dto.ProductSetDetailsCriteria;
import com.epmweb.server.service.ProductSetDetailsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductSetDetails}.
 */
@RestController
@RequestMapping("/api")
public class ProductSetDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailsResource.class);

    private static final String ENTITY_NAME = "productSetDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSetDetailsService productSetDetailsService;

    private final ProductSetDetailsQueryService productSetDetailsQueryService;

    public ProductSetDetailsResource(ProductSetDetailsService productSetDetailsService, ProductSetDetailsQueryService productSetDetailsQueryService) {
        this.productSetDetailsService = productSetDetailsService;
        this.productSetDetailsQueryService = productSetDetailsQueryService;
    }

    /**
     * {@code POST  /product-set-details} : Create a new productSetDetails.
     *
     * @param productSetDetailsDTO the productSetDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSetDetailsDTO, or with status {@code 400 (Bad Request)} if the productSetDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-set-details")
    public ResponseEntity<ProductSetDetailsDTO> createProductSetDetails(@Valid @RequestBody ProductSetDetailsDTO productSetDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSetDetails : {}", productSetDetailsDTO);
        if (productSetDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSetDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSetDetailsDTO result = productSetDetailsService.save(productSetDetailsDTO);
        return ResponseEntity.created(new URI("/api/product-set-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-set-details} : Updates an existing productSetDetails.
     *
     * @param productSetDetailsDTO the productSetDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSetDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the productSetDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSetDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-set-details")
    public ResponseEntity<ProductSetDetailsDTO> updateProductSetDetails(@Valid @RequestBody ProductSetDetailsDTO productSetDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSetDetails : {}", productSetDetailsDTO);
        if (productSetDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSetDetailsDTO result = productSetDetailsService.save(productSetDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSetDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-set-details} : get all the productSetDetails.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSetDetails in body.
     */
    @GetMapping("/product-set-details")
    public ResponseEntity<List<ProductSetDetailsDTO>> getAllProductSetDetails(ProductSetDetailsCriteria criteria) {
        log.debug("REST request to get ProductSetDetails by criteria: {}", criteria);
        List<ProductSetDetailsDTO> entityList = productSetDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-set-details/count} : count all the productSetDetails.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-set-details/count")
    public ResponseEntity<Long> countProductSetDetails(ProductSetDetailsCriteria criteria) {
        log.debug("REST request to count ProductSetDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(productSetDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-set-details/:id} : get the "id" productSetDetails.
     *
     * @param id the id of the productSetDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSetDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-set-details/{id}")
    public ResponseEntity<ProductSetDetailsDTO> getProductSetDetails(@PathVariable Long id) {
        log.debug("REST request to get ProductSetDetails : {}", id);
        Optional<ProductSetDetailsDTO> productSetDetailsDTO = productSetDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSetDetailsDTO);
    }

    /**
     * {@code DELETE  /product-set-details/:id} : delete the "id" productSetDetails.
     *
     * @param id the id of the productSetDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-set-details/{id}")
    public ResponseEntity<Void> deleteProductSetDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProductSetDetails : {}", id);
        productSetDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
