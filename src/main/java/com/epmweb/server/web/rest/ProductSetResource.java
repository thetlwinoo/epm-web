package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductSetService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductSetDTO;
import com.epmweb.server.service.dto.ProductSetCriteria;
import com.epmweb.server.service.ProductSetQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductSet}.
 */
@RestController
@RequestMapping("/api")
public class ProductSetResource {

    private final Logger log = LoggerFactory.getLogger(ProductSetResource.class);

    private static final String ENTITY_NAME = "productSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductSetService productSetService;

    private final ProductSetQueryService productSetQueryService;

    public ProductSetResource(ProductSetService productSetService, ProductSetQueryService productSetQueryService) {
        this.productSetService = productSetService;
        this.productSetQueryService = productSetQueryService;
    }

    /**
     * {@code POST  /product-sets} : Create a new productSet.
     *
     * @param productSetDTO the productSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productSetDTO, or with status {@code 400 (Bad Request)} if the productSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-sets")
    public ResponseEntity<ProductSetDTO> createProductSet(@Valid @RequestBody ProductSetDTO productSetDTO) throws URISyntaxException {
        log.debug("REST request to save ProductSet : {}", productSetDTO);
        if (productSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new productSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductSetDTO result = productSetService.save(productSetDTO);
        return ResponseEntity.created(new URI("/api/product-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-sets} : Updates an existing productSet.
     *
     * @param productSetDTO the productSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productSetDTO,
     * or with status {@code 400 (Bad Request)} if the productSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-sets")
    public ResponseEntity<ProductSetDTO> updateProductSet(@Valid @RequestBody ProductSetDTO productSetDTO) throws URISyntaxException {
        log.debug("REST request to update ProductSet : {}", productSetDTO);
        if (productSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductSetDTO result = productSetService.save(productSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-sets} : get all the productSets.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productSets in body.
     */
    @GetMapping("/product-sets")
    public ResponseEntity<List<ProductSetDTO>> getAllProductSets(ProductSetCriteria criteria) {
        log.debug("REST request to get ProductSets by criteria: {}", criteria);
        List<ProductSetDTO> entityList = productSetQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-sets/count} : count all the productSets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-sets/count")
    public ResponseEntity<Long> countProductSets(ProductSetCriteria criteria) {
        log.debug("REST request to count ProductSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(productSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-sets/:id} : get the "id" productSet.
     *
     * @param id the id of the productSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-sets/{id}")
    public ResponseEntity<ProductSetDTO> getProductSet(@PathVariable Long id) {
        log.debug("REST request to get ProductSet : {}", id);
        Optional<ProductSetDTO> productSetDTO = productSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productSetDTO);
    }

    /**
     * {@code DELETE  /product-sets/:id} : delete the "id" productSet.
     *
     * @param id the id of the productSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-sets/{id}")
    public ResponseEntity<Void> deleteProductSet(@PathVariable Long id) {
        log.debug("REST request to delete ProductSet : {}", id);
        productSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
