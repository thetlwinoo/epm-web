package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductCategoryService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductCategoryDTO;
import com.epmweb.server.service.dto.ProductCategoryCriteria;
import com.epmweb.server.service.ProductCategoryQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductCategory}.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryResource.class);

    private static final String ENTITY_NAME = "productCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCategoryService productCategoryService;

    private final ProductCategoryQueryService productCategoryQueryService;

    public ProductCategoryResource(ProductCategoryService productCategoryService, ProductCategoryQueryService productCategoryQueryService) {
        this.productCategoryService = productCategoryService;
        this.productCategoryQueryService = productCategoryQueryService;
    }

    /**
     * {@code POST  /product-categories} : Create a new productCategory.
     *
     * @param productCategoryDTO the productCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCategoryDTO, or with status {@code 400 (Bad Request)} if the productCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-categories")
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@Valid @RequestBody ProductCategoryDTO productCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductCategory : {}", productCategoryDTO);
        if (productCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCategoryDTO result = productCategoryService.save(productCategoryDTO);
        return ResponseEntity.created(new URI("/api/product-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-categories} : Updates an existing productCategory.
     *
     * @param productCategoryDTO the productCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the productCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-categories")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(@Valid @RequestBody ProductCategoryDTO productCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductCategory : {}", productCategoryDTO);
        if (productCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCategoryDTO result = productCategoryService.save(productCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-categories} : get all the productCategories.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCategories in body.
     */
    @GetMapping("/product-categories")
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories(ProductCategoryCriteria criteria) {
        log.debug("REST request to get ProductCategories by criteria: {}", criteria);
        List<ProductCategoryDTO> entityList = productCategoryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-categories/count} : count all the productCategories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-categories/count")
    public ResponseEntity<Long> countProductCategories(ProductCategoryCriteria criteria) {
        log.debug("REST request to count ProductCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(productCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-categories/:id} : get the "id" productCategory.
     *
     * @param id the id of the productCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategory(@PathVariable Long id) {
        log.debug("REST request to get ProductCategory : {}", id);
        Optional<ProductCategoryDTO> productCategoryDTO = productCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCategoryDTO);
    }

    /**
     * {@code DELETE  /product-categories/:id} : delete the "id" productCategory.
     *
     * @param id the id of the productCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-categories/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategory : {}", id);
        productCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
