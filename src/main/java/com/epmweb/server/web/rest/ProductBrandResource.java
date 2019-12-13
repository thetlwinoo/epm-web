package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductBrandService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductBrandDTO;
import com.epmweb.server.service.dto.ProductBrandCriteria;
import com.epmweb.server.service.ProductBrandQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductBrand}.
 */
@RestController
@RequestMapping("/api")
public class ProductBrandResource {

    private final Logger log = LoggerFactory.getLogger(ProductBrandResource.class);

    private static final String ENTITY_NAME = "productBrand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductBrandService productBrandService;

    private final ProductBrandQueryService productBrandQueryService;

    public ProductBrandResource(ProductBrandService productBrandService, ProductBrandQueryService productBrandQueryService) {
        this.productBrandService = productBrandService;
        this.productBrandQueryService = productBrandQueryService;
    }

    /**
     * {@code POST  /product-brands} : Create a new productBrand.
     *
     * @param productBrandDTO the productBrandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productBrandDTO, or with status {@code 400 (Bad Request)} if the productBrand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-brands")
    public ResponseEntity<ProductBrandDTO> createProductBrand(@Valid @RequestBody ProductBrandDTO productBrandDTO) throws URISyntaxException {
        log.debug("REST request to save ProductBrand : {}", productBrandDTO);
        if (productBrandDTO.getId() != null) {
            throw new BadRequestAlertException("A new productBrand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductBrandDTO result = productBrandService.save(productBrandDTO);
        return ResponseEntity.created(new URI("/api/product-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-brands} : Updates an existing productBrand.
     *
     * @param productBrandDTO the productBrandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productBrandDTO,
     * or with status {@code 400 (Bad Request)} if the productBrandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productBrandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-brands")
    public ResponseEntity<ProductBrandDTO> updateProductBrand(@Valid @RequestBody ProductBrandDTO productBrandDTO) throws URISyntaxException {
        log.debug("REST request to update ProductBrand : {}", productBrandDTO);
        if (productBrandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductBrandDTO result = productBrandService.save(productBrandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-brands} : get all the productBrands.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productBrands in body.
     */
    @GetMapping("/product-brands")
    public ResponseEntity<List<ProductBrandDTO>> getAllProductBrands(ProductBrandCriteria criteria) {
        log.debug("REST request to get ProductBrands by criteria: {}", criteria);
        List<ProductBrandDTO> entityList = productBrandQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-brands/count} : count all the productBrands.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-brands/count")
    public ResponseEntity<Long> countProductBrands(ProductBrandCriteria criteria) {
        log.debug("REST request to count ProductBrands by criteria: {}", criteria);
        return ResponseEntity.ok().body(productBrandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-brands/:id} : get the "id" productBrand.
     *
     * @param id the id of the productBrandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productBrandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-brands/{id}")
    public ResponseEntity<ProductBrandDTO> getProductBrand(@PathVariable Long id) {
        log.debug("REST request to get ProductBrand : {}", id);
        Optional<ProductBrandDTO> productBrandDTO = productBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productBrandDTO);
    }

    /**
     * {@code DELETE  /product-brands/:id} : delete the "id" productBrand.
     *
     * @param id the id of the productBrandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-brands/{id}")
    public ResponseEntity<Void> deleteProductBrand(@PathVariable Long id) {
        log.debug("REST request to delete ProductBrand : {}", id);
        productBrandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
