package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ProductsDTO;
import com.epmweb.server.service.dto.ProductsCriteria;
import com.epmweb.server.service.ProductsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epmweb.server.domain.Products}.
 */
@RestController
@RequestMapping("/api")
public class ProductsResource {

    private final Logger log = LoggerFactory.getLogger(ProductsResource.class);

    private static final String ENTITY_NAME = "products";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductsService productsService;

    private final ProductsQueryService productsQueryService;

    public ProductsResource(ProductsService productsService, ProductsQueryService productsQueryService) {
        this.productsService = productsService;
        this.productsQueryService = productsQueryService;
    }

    /**
     * {@code POST  /products} : Create a new products.
     *
     * @param productsDTO the productsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productsDTO, or with status {@code 400 (Bad Request)} if the products has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products")
    public ResponseEntity<ProductsDTO> createProducts(@Valid @RequestBody ProductsDTO productsDTO) throws URISyntaxException {
        log.debug("REST request to save Products : {}", productsDTO);
        if (productsDTO.getId() != null) {
            throw new BadRequestAlertException("A new products cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductsDTO result = productsService.save(productsDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /products} : Updates an existing products.
     *
     * @param productsDTO the productsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsDTO,
     * or with status {@code 400 (Bad Request)} if the productsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products")
    public ResponseEntity<ProductsDTO> updateProducts(@Valid @RequestBody ProductsDTO productsDTO) throws URISyntaxException {
        log.debug("REST request to update Products : {}", productsDTO);
        if (productsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductsDTO result = productsService.save(productsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /products} : get all the products.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductsDTO>> getAllProducts(ProductsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);
        Page<ProductsDTO> page = productsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /products/count} : count all the products.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/products/count")
    public ResponseEntity<Long> countProducts(ProductsCriteria criteria) {
        log.debug("REST request to count Products by criteria: {}", criteria);
        return ResponseEntity.ok().body(productsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /products/:id} : get the "id" products.
     *
     * @param id the id of the productsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductsDTO> getProducts(@PathVariable Long id) {
        log.debug("REST request to get Products : {}", id);
        Optional<ProductsDTO> productsDTO = productsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsDTO);
    }

    /**
     * {@code DELETE  /products/:id} : delete the "id" products.
     *
     * @param id the id of the productsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id) {
        log.debug("REST request to delete Products : {}", id);
        productsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
