package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductOptionSetService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductOptionSetDTO;
import com.epmweb.server.service.dto.ProductOptionSetCriteria;
import com.epmweb.server.service.ProductOptionSetQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductOptionSet}.
 */
@RestController
@RequestMapping("/api")
public class ProductOptionSetResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionSetResource.class);

    private static final String ENTITY_NAME = "productOptionSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductOptionSetService productOptionSetService;

    private final ProductOptionSetQueryService productOptionSetQueryService;

    public ProductOptionSetResource(ProductOptionSetService productOptionSetService, ProductOptionSetQueryService productOptionSetQueryService) {
        this.productOptionSetService = productOptionSetService;
        this.productOptionSetQueryService = productOptionSetQueryService;
    }

    /**
     * {@code POST  /product-option-sets} : Create a new productOptionSet.
     *
     * @param productOptionSetDTO the productOptionSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productOptionSetDTO, or with status {@code 400 (Bad Request)} if the productOptionSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-option-sets")
    public ResponseEntity<ProductOptionSetDTO> createProductOptionSet(@Valid @RequestBody ProductOptionSetDTO productOptionSetDTO) throws URISyntaxException {
        log.debug("REST request to save ProductOptionSet : {}", productOptionSetDTO);
        if (productOptionSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new productOptionSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOptionSetDTO result = productOptionSetService.save(productOptionSetDTO);
        return ResponseEntity.created(new URI("/api/product-option-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-option-sets} : Updates an existing productOptionSet.
     *
     * @param productOptionSetDTO the productOptionSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOptionSetDTO,
     * or with status {@code 400 (Bad Request)} if the productOptionSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productOptionSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-option-sets")
    public ResponseEntity<ProductOptionSetDTO> updateProductOptionSet(@Valid @RequestBody ProductOptionSetDTO productOptionSetDTO) throws URISyntaxException {
        log.debug("REST request to update ProductOptionSet : {}", productOptionSetDTO);
        if (productOptionSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductOptionSetDTO result = productOptionSetService.save(productOptionSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productOptionSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-option-sets} : get all the productOptionSets.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productOptionSets in body.
     */
    @GetMapping("/product-option-sets")
    public ResponseEntity<List<ProductOptionSetDTO>> getAllProductOptionSets(ProductOptionSetCriteria criteria) {
        log.debug("REST request to get ProductOptionSets by criteria: {}", criteria);
        List<ProductOptionSetDTO> entityList = productOptionSetQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-option-sets/count} : count all the productOptionSets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-option-sets/count")
    public ResponseEntity<Long> countProductOptionSets(ProductOptionSetCriteria criteria) {
        log.debug("REST request to count ProductOptionSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOptionSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-option-sets/:id} : get the "id" productOptionSet.
     *
     * @param id the id of the productOptionSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productOptionSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-option-sets/{id}")
    public ResponseEntity<ProductOptionSetDTO> getProductOptionSet(@PathVariable Long id) {
        log.debug("REST request to get ProductOptionSet : {}", id);
        Optional<ProductOptionSetDTO> productOptionSetDTO = productOptionSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOptionSetDTO);
    }

    /**
     * {@code DELETE  /product-option-sets/:id} : delete the "id" productOptionSet.
     *
     * @param id the id of the productOptionSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-option-sets/{id}")
    public ResponseEntity<Void> deleteProductOptionSet(@PathVariable Long id) {
        log.debug("REST request to delete ProductOptionSet : {}", id);
        productOptionSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
