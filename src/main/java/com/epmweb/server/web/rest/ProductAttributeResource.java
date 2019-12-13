package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductAttributeService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductAttributeDTO;
import com.epmweb.server.service.dto.ProductAttributeCriteria;
import com.epmweb.server.service.ProductAttributeQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductAttribute}.
 */
@RestController
@RequestMapping("/api")
public class ProductAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeResource.class);

    private static final String ENTITY_NAME = "productAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAttributeService productAttributeService;

    private final ProductAttributeQueryService productAttributeQueryService;

    public ProductAttributeResource(ProductAttributeService productAttributeService, ProductAttributeQueryService productAttributeQueryService) {
        this.productAttributeService = productAttributeService;
        this.productAttributeQueryService = productAttributeQueryService;
    }

    /**
     * {@code POST  /product-attributes} : Create a new productAttribute.
     *
     * @param productAttributeDTO the productAttributeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAttributeDTO, or with status {@code 400 (Bad Request)} if the productAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-attributes")
    public ResponseEntity<ProductAttributeDTO> createProductAttribute(@Valid @RequestBody ProductAttributeDTO productAttributeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAttribute : {}", productAttributeDTO);
        if (productAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttributeDTO result = productAttributeService.save(productAttributeDTO);
        return ResponseEntity.created(new URI("/api/product-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-attributes} : Updates an existing productAttribute.
     *
     * @param productAttributeDTO the productAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the productAttributeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-attributes")
    public ResponseEntity<ProductAttributeDTO> updateProductAttribute(@Valid @RequestBody ProductAttributeDTO productAttributeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductAttribute : {}", productAttributeDTO);
        if (productAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttributeDTO result = productAttributeService.save(productAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAttributeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-attributes} : get all the productAttributes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAttributes in body.
     */
    @GetMapping("/product-attributes")
    public ResponseEntity<List<ProductAttributeDTO>> getAllProductAttributes(ProductAttributeCriteria criteria) {
        log.debug("REST request to get ProductAttributes by criteria: {}", criteria);
        List<ProductAttributeDTO> entityList = productAttributeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-attributes/count} : count all the productAttributes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-attributes/count")
    public ResponseEntity<Long> countProductAttributes(ProductAttributeCriteria criteria) {
        log.debug("REST request to count ProductAttributes by criteria: {}", criteria);
        return ResponseEntity.ok().body(productAttributeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-attributes/:id} : get the "id" productAttribute.
     *
     * @param id the id of the productAttributeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAttributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-attributes/{id}")
    public ResponseEntity<ProductAttributeDTO> getProductAttribute(@PathVariable Long id) {
        log.debug("REST request to get ProductAttribute : {}", id);
        Optional<ProductAttributeDTO> productAttributeDTO = productAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAttributeDTO);
    }

    /**
     * {@code DELETE  /product-attributes/:id} : delete the "id" productAttribute.
     *
     * @param id the id of the productAttributeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-attributes/{id}")
    public ResponseEntity<Void> deleteProductAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttribute : {}", id);
        productAttributeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
