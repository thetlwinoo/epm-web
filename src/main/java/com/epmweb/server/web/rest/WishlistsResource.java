package com.epmweb.server.web.rest;

import com.epmweb.server.service.WishlistsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.WishlistsDTO;
import com.epmweb.server.service.dto.WishlistsCriteria;
import com.epmweb.server.service.WishlistsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Wishlists}.
 */
@RestController
@RequestMapping("/api")
public class WishlistsResource {

    private final Logger log = LoggerFactory.getLogger(WishlistsResource.class);

    private static final String ENTITY_NAME = "wishlists";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WishlistsService wishlistsService;

    private final WishlistsQueryService wishlistsQueryService;

    public WishlistsResource(WishlistsService wishlistsService, WishlistsQueryService wishlistsQueryService) {
        this.wishlistsService = wishlistsService;
        this.wishlistsQueryService = wishlistsQueryService;
    }

    /**
     * {@code POST  /wishlists} : Create a new wishlists.
     *
     * @param wishlistsDTO the wishlistsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wishlistsDTO, or with status {@code 400 (Bad Request)} if the wishlists has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wishlists")
    public ResponseEntity<WishlistsDTO> createWishlists(@RequestBody WishlistsDTO wishlistsDTO) throws URISyntaxException {
        log.debug("REST request to save Wishlists : {}", wishlistsDTO);
        if (wishlistsDTO.getId() != null) {
            throw new BadRequestAlertException("A new wishlists cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WishlistsDTO result = wishlistsService.save(wishlistsDTO);
        return ResponseEntity.created(new URI("/api/wishlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wishlists} : Updates an existing wishlists.
     *
     * @param wishlistsDTO the wishlistsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wishlistsDTO,
     * or with status {@code 400 (Bad Request)} if the wishlistsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wishlistsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wishlists")
    public ResponseEntity<WishlistsDTO> updateWishlists(@RequestBody WishlistsDTO wishlistsDTO) throws URISyntaxException {
        log.debug("REST request to update Wishlists : {}", wishlistsDTO);
        if (wishlistsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WishlistsDTO result = wishlistsService.save(wishlistsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wishlistsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wishlists} : get all the wishlists.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wishlists in body.
     */
    @GetMapping("/wishlists")
    public ResponseEntity<List<WishlistsDTO>> getAllWishlists(WishlistsCriteria criteria) {
        log.debug("REST request to get Wishlists by criteria: {}", criteria);
        List<WishlistsDTO> entityList = wishlistsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /wishlists/count} : count all the wishlists.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/wishlists/count")
    public ResponseEntity<Long> countWishlists(WishlistsCriteria criteria) {
        log.debug("REST request to count Wishlists by criteria: {}", criteria);
        return ResponseEntity.ok().body(wishlistsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wishlists/:id} : get the "id" wishlists.
     *
     * @param id the id of the wishlistsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wishlistsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wishlists/{id}")
    public ResponseEntity<WishlistsDTO> getWishlists(@PathVariable Long id) {
        log.debug("REST request to get Wishlists : {}", id);
        Optional<WishlistsDTO> wishlistsDTO = wishlistsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wishlistsDTO);
    }

    /**
     * {@code DELETE  /wishlists/:id} : delete the "id" wishlists.
     *
     * @param id the id of the wishlistsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<Void> deleteWishlists(@PathVariable Long id) {
        log.debug("REST request to delete Wishlists : {}", id);
        wishlistsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
