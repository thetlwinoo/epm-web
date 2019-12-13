package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductDocumentService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductDocumentDTO;
import com.epmweb.server.service.dto.ProductDocumentCriteria;
import com.epmweb.server.service.ProductDocumentQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ProductDocument}.
 */
@RestController
@RequestMapping("/api")
public class ProductDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentResource.class);

    private static final String ENTITY_NAME = "productDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDocumentService productDocumentService;

    private final ProductDocumentQueryService productDocumentQueryService;

    public ProductDocumentResource(ProductDocumentService productDocumentService, ProductDocumentQueryService productDocumentQueryService) {
        this.productDocumentService = productDocumentService;
        this.productDocumentQueryService = productDocumentQueryService;
    }

    /**
     * {@code POST  /product-documents} : Create a new productDocument.
     *
     * @param productDocumentDTO the productDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDocumentDTO, or with status {@code 400 (Bad Request)} if the productDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-documents")
    public ResponseEntity<ProductDocumentDTO> createProductDocument(@RequestBody ProductDocumentDTO productDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ProductDocument : {}", productDocumentDTO);
        if (productDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDocumentDTO result = productDocumentService.save(productDocumentDTO);
        return ResponseEntity.created(new URI("/api/product-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-documents} : Updates an existing productDocument.
     *
     * @param productDocumentDTO the productDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the productDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-documents")
    public ResponseEntity<ProductDocumentDTO> updateProductDocument(@RequestBody ProductDocumentDTO productDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ProductDocument : {}", productDocumentDTO);
        if (productDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDocumentDTO result = productDocumentService.save(productDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-documents} : get all the productDocuments.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDocuments in body.
     */
    @GetMapping("/product-documents")
    public ResponseEntity<List<ProductDocumentDTO>> getAllProductDocuments(ProductDocumentCriteria criteria) {
        log.debug("REST request to get ProductDocuments by criteria: {}", criteria);
        List<ProductDocumentDTO> entityList = productDocumentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-documents/count} : count all the productDocuments.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-documents/count")
    public ResponseEntity<Long> countProductDocuments(ProductDocumentCriteria criteria) {
        log.debug("REST request to count ProductDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(productDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-documents/:id} : get the "id" productDocument.
     *
     * @param id the id of the productDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-documents/{id}")
    public ResponseEntity<ProductDocumentDTO> getProductDocument(@PathVariable Long id) {
        log.debug("REST request to get ProductDocument : {}", id);
        Optional<ProductDocumentDTO> productDocumentDTO = productDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDocumentDTO);
    }

    /**
     * {@code DELETE  /product-documents/:id} : delete the "id" productDocument.
     *
     * @param id the id of the productDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-documents/{id}")
    public ResponseEntity<Void> deleteProductDocument(@PathVariable Long id) {
        log.debug("REST request to delete ProductDocument : {}", id);
        productDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
