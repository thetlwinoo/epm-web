package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductAttributeSetService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductAttributeSetDTO;
import com.epmweb.server.service.dto.ProductAttributeSetCriteria;
import com.epmweb.server.service.ProductAttributeSetQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductAttributeSet}.
 */
@RestController
@RequestMapping("/api")
public class ProductAttributeSetResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeSetResource.class);

    private static final String ENTITY_NAME = "productAttributeSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductAttributeSetService productAttributeSetService;

    private final ProductAttributeSetQueryService productAttributeSetQueryService;

    public ProductAttributeSetResource(ProductAttributeSetService productAttributeSetService, ProductAttributeSetQueryService productAttributeSetQueryService) {
        this.productAttributeSetService = productAttributeSetService;
        this.productAttributeSetQueryService = productAttributeSetQueryService;
    }

    /**
     * {@code POST  /product-attribute-sets} : Create a new productAttributeSet.
     *
     * @param productAttributeSetDTO the productAttributeSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productAttributeSetDTO, or with status {@code 400 (Bad Request)} if the productAttributeSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-attribute-sets")
    public ResponseEntity<ProductAttributeSetDTO> createProductAttributeSet(@Valid @RequestBody ProductAttributeSetDTO productAttributeSetDTO) throws URISyntaxException {
        log.debug("REST request to save ProductAttributeSet : {}", productAttributeSetDTO);
        if (productAttributeSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new productAttributeSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductAttributeSetDTO result = productAttributeSetService.save(productAttributeSetDTO);
        return ResponseEntity.created(new URI("/api/product-attribute-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-attribute-sets} : Updates an existing productAttributeSet.
     *
     * @param productAttributeSetDTO the productAttributeSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productAttributeSetDTO,
     * or with status {@code 400 (Bad Request)} if the productAttributeSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productAttributeSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-attribute-sets")
    public ResponseEntity<ProductAttributeSetDTO> updateProductAttributeSet(@Valid @RequestBody ProductAttributeSetDTO productAttributeSetDTO) throws URISyntaxException {
        log.debug("REST request to update ProductAttributeSet : {}", productAttributeSetDTO);
        if (productAttributeSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductAttributeSetDTO result = productAttributeSetService.save(productAttributeSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productAttributeSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-attribute-sets} : get all the productAttributeSets.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productAttributeSets in body.
     */
    @GetMapping("/product-attribute-sets")
    public ResponseEntity<List<ProductAttributeSetDTO>> getAllProductAttributeSets(ProductAttributeSetCriteria criteria) {
        log.debug("REST request to get ProductAttributeSets by criteria: {}", criteria);
        List<ProductAttributeSetDTO> entityList = productAttributeSetQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-attribute-sets/count} : count all the productAttributeSets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-attribute-sets/count")
    public ResponseEntity<Long> countProductAttributeSets(ProductAttributeSetCriteria criteria) {
        log.debug("REST request to count ProductAttributeSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(productAttributeSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-attribute-sets/:id} : get the "id" productAttributeSet.
     *
     * @param id the id of the productAttributeSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productAttributeSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-attribute-sets/{id}")
    public ResponseEntity<ProductAttributeSetDTO> getProductAttributeSet(@PathVariable Long id) {
        log.debug("REST request to get ProductAttributeSet : {}", id);
        Optional<ProductAttributeSetDTO> productAttributeSetDTO = productAttributeSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productAttributeSetDTO);
    }

    /**
     * {@code DELETE  /product-attribute-sets/:id} : delete the "id" productAttributeSet.
     *
     * @param id the id of the productAttributeSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-attribute-sets/{id}")
    public ResponseEntity<Void> deleteProductAttributeSet(@PathVariable Long id) {
        log.debug("REST request to delete ProductAttributeSet : {}", id);
        productAttributeSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
