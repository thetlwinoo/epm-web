package com.epmweb.server.web.rest;

import com.epmweb.server.service.ShoppingCartsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ShoppingCartsDTO;
import com.epmweb.server.service.dto.ShoppingCartsCriteria;
import com.epmweb.server.service.ShoppingCartsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.ShoppingCarts}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartsResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsResource.class);

    private static final String ENTITY_NAME = "shoppingCarts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingCartsService shoppingCartsService;

    private final ShoppingCartsQueryService shoppingCartsQueryService;

    public ShoppingCartsResource(ShoppingCartsService shoppingCartsService, ShoppingCartsQueryService shoppingCartsQueryService) {
        this.shoppingCartsService = shoppingCartsService;
        this.shoppingCartsQueryService = shoppingCartsQueryService;
    }

    /**
     * {@code POST  /shopping-carts} : Create a new shoppingCarts.
     *
     * @param shoppingCartsDTO the shoppingCartsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingCartsDTO, or with status {@code 400 (Bad Request)} if the shoppingCarts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shopping-carts")
    public ResponseEntity<ShoppingCartsDTO> createShoppingCarts(@RequestBody ShoppingCartsDTO shoppingCartsDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingCarts : {}", shoppingCartsDTO);
        if (shoppingCartsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCarts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCartsDTO result = shoppingCartsService.save(shoppingCartsDTO);
        return ResponseEntity.created(new URI("/api/shopping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shopping-carts} : Updates an existing shoppingCarts.
     *
     * @param shoppingCartsDTO the shoppingCartsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingCartsDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingCartsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingCartsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shopping-carts")
    public ResponseEntity<ShoppingCartsDTO> updateShoppingCarts(@RequestBody ShoppingCartsDTO shoppingCartsDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingCarts : {}", shoppingCartsDTO);
        if (shoppingCartsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShoppingCartsDTO result = shoppingCartsService.save(shoppingCartsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingCartsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shopping-carts} : get all the shoppingCarts.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppingCarts in body.
     */
    @GetMapping("/shopping-carts")
    public ResponseEntity<List<ShoppingCartsDTO>> getAllShoppingCarts(ShoppingCartsCriteria criteria) {
        log.debug("REST request to get ShoppingCarts by criteria: {}", criteria);
        List<ShoppingCartsDTO> entityList = shoppingCartsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /shopping-carts/count} : count all the shoppingCarts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/shopping-carts/count")
    public ResponseEntity<Long> countShoppingCarts(ShoppingCartsCriteria criteria) {
        log.debug("REST request to count ShoppingCarts by criteria: {}", criteria);
        return ResponseEntity.ok().body(shoppingCartsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shopping-carts/:id} : get the "id" shoppingCarts.
     *
     * @param id the id of the shoppingCartsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingCartsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shopping-carts/{id}")
    public ResponseEntity<ShoppingCartsDTO> getShoppingCarts(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCarts : {}", id);
        Optional<ShoppingCartsDTO> shoppingCartsDTO = shoppingCartsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingCartsDTO);
    }

    /**
     * {@code DELETE  /shopping-carts/:id} : delete the "id" shoppingCarts.
     *
     * @param id the id of the shoppingCartsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shopping-carts/{id}")
    public ResponseEntity<Void> deleteShoppingCarts(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCarts : {}", id);
        shoppingCartsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
