package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductCatalogService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductCatalogDTO;
import com.epmweb.server.service.dto.ProductCatalogCriteria;
import com.epmweb.server.service.ProductCatalogQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductCatalog}.
 */
@RestController
@RequestMapping("/api")
public class ProductCatalogResource {

    private final Logger log = LoggerFactory.getLogger(ProductCatalogResource.class);

    private static final String ENTITY_NAME = "productCatalog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductCatalogService productCatalogService;

    private final ProductCatalogQueryService productCatalogQueryService;

    public ProductCatalogResource(ProductCatalogService productCatalogService, ProductCatalogQueryService productCatalogQueryService) {
        this.productCatalogService = productCatalogService;
        this.productCatalogQueryService = productCatalogQueryService;
    }

    /**
     * {@code POST  /product-catalogs} : Create a new productCatalog.
     *
     * @param productCatalogDTO the productCatalogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productCatalogDTO, or with status {@code 400 (Bad Request)} if the productCatalog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-catalogs")
    public ResponseEntity<ProductCatalogDTO> createProductCatalog(@RequestBody ProductCatalogDTO productCatalogDTO) throws URISyntaxException {
        log.debug("REST request to save ProductCatalog : {}", productCatalogDTO);
        if (productCatalogDTO.getId() != null) {
            throw new BadRequestAlertException("A new productCatalog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductCatalogDTO result = productCatalogService.save(productCatalogDTO);
        return ResponseEntity.created(new URI("/api/product-catalogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-catalogs} : Updates an existing productCatalog.
     *
     * @param productCatalogDTO the productCatalogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productCatalogDTO,
     * or with status {@code 400 (Bad Request)} if the productCatalogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productCatalogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-catalogs")
    public ResponseEntity<ProductCatalogDTO> updateProductCatalog(@RequestBody ProductCatalogDTO productCatalogDTO) throws URISyntaxException {
        log.debug("REST request to update ProductCatalog : {}", productCatalogDTO);
        if (productCatalogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductCatalogDTO result = productCatalogService.save(productCatalogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productCatalogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-catalogs} : get all the productCatalogs.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productCatalogs in body.
     */
    @GetMapping("/product-catalogs")
    public ResponseEntity<List<ProductCatalogDTO>> getAllProductCatalogs(ProductCatalogCriteria criteria) {
        log.debug("REST request to get ProductCatalogs by criteria: {}", criteria);
        List<ProductCatalogDTO> entityList = productCatalogQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-catalogs/count} : count all the productCatalogs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-catalogs/count")
    public ResponseEntity<Long> countProductCatalogs(ProductCatalogCriteria criteria) {
        log.debug("REST request to count ProductCatalogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(productCatalogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-catalogs/:id} : get the "id" productCatalog.
     *
     * @param id the id of the productCatalogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productCatalogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-catalogs/{id}")
    public ResponseEntity<ProductCatalogDTO> getProductCatalog(@PathVariable Long id) {
        log.debug("REST request to get ProductCatalog : {}", id);
        Optional<ProductCatalogDTO> productCatalogDTO = productCatalogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productCatalogDTO);
    }

    /**
     * {@code DELETE  /product-catalogs/:id} : delete the "id" productCatalog.
     *
     * @param id the id of the productCatalogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-catalogs/{id}")
    public ResponseEntity<Void> deleteProductCatalog(@PathVariable Long id) {
        log.debug("REST request to delete ProductCatalog : {}", id);
        productCatalogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
