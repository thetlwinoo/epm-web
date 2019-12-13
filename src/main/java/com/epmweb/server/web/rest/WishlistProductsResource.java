package com.epmweb.server.web.rest;

import com.epmweb.server.service.WishlistProductsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.WishlistProductsDTO;
import com.epmweb.server.service.dto.WishlistProductsCriteria;
import com.epmweb.server.service.WishlistProductsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.WishlistProducts}.
 */
@RestController
@RequestMapping("/api")
public class WishlistProductsResource {

    private final Logger log = LoggerFactory.getLogger(WishlistProductsResource.class);

    private static final String ENTITY_NAME = "wishlistProducts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WishlistProductsService wishlistProductsService;

    private final WishlistProductsQueryService wishlistProductsQueryService;

    public WishlistProductsResource(WishlistProductsService wishlistProductsService, WishlistProductsQueryService wishlistProductsQueryService) {
        this.wishlistProductsService = wishlistProductsService;
        this.wishlistProductsQueryService = wishlistProductsQueryService;
    }

    /**
     * {@code POST  /wishlist-products} : Create a new wishlistProducts.
     *
     * @param wishlistProductsDTO the wishlistProductsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wishlistProductsDTO, or with status {@code 400 (Bad Request)} if the wishlistProducts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wishlist-products")
    public ResponseEntity<WishlistProductsDTO> createWishlistProducts(@RequestBody WishlistProductsDTO wishlistProductsDTO) throws URISyntaxException {
        log.debug("REST request to save WishlistProducts : {}", wishlistProductsDTO);
        if (wishlistProductsDTO.getId() != null) {
            throw new BadRequestAlertException("A new wishlistProducts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WishlistProductsDTO result = wishlistProductsService.save(wishlistProductsDTO);
        return ResponseEntity.created(new URI("/api/wishlist-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wishlist-products} : Updates an existing wishlistProducts.
     *
     * @param wishlistProductsDTO the wishlistProductsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wishlistProductsDTO,
     * or with status {@code 400 (Bad Request)} if the wishlistProductsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wishlistProductsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wishlist-products")
    public ResponseEntity<WishlistProductsDTO> updateWishlistProducts(@RequestBody WishlistProductsDTO wishlistProductsDTO) throws URISyntaxException {
        log.debug("REST request to update WishlistProducts : {}", wishlistProductsDTO);
        if (wishlistProductsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WishlistProductsDTO result = wishlistProductsService.save(wishlistProductsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wishlistProductsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wishlist-products} : get all the wishlistProducts.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wishlistProducts in body.
     */
    @GetMapping("/wishlist-products")
    public ResponseEntity<List<WishlistProductsDTO>> getAllWishlistProducts(WishlistProductsCriteria criteria) {
        log.debug("REST request to get WishlistProducts by criteria: {}", criteria);
        List<WishlistProductsDTO> entityList = wishlistProductsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /wishlist-products/count} : count all the wishlistProducts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/wishlist-products/count")
    public ResponseEntity<Long> countWishlistProducts(WishlistProductsCriteria criteria) {
        log.debug("REST request to count WishlistProducts by criteria: {}", criteria);
        return ResponseEntity.ok().body(wishlistProductsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wishlist-products/:id} : get the "id" wishlistProducts.
     *
     * @param id the id of the wishlistProductsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wishlistProductsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wishlist-products/{id}")
    public ResponseEntity<WishlistProductsDTO> getWishlistProducts(@PathVariable Long id) {
        log.debug("REST request to get WishlistProducts : {}", id);
        Optional<WishlistProductsDTO> wishlistProductsDTO = wishlistProductsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wishlistProductsDTO);
    }

    /**
     * {@code DELETE  /wishlist-products/:id} : delete the "id" wishlistProducts.
     *
     * @param id the id of the wishlistProductsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wishlist-products/{id}")
    public ResponseEntity<Void> deleteWishlistProducts(@PathVariable Long id) {
        log.debug("REST request to delete WishlistProducts : {}", id);
        wishlistProductsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
