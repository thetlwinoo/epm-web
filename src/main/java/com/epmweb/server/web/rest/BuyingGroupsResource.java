package com.epmweb.server.web.rest;

import com.epmweb.server.service.BuyingGroupsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.BuyingGroupsDTO;
import com.epmweb.server.service.dto.BuyingGroupsCriteria;
import com.epmweb.server.service.BuyingGroupsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.BuyingGroups}.
 */
@RestController
@RequestMapping("/api")
public class BuyingGroupsResource {

    private final Logger log = LoggerFactory.getLogger(BuyingGroupsResource.class);

    private static final String ENTITY_NAME = "buyingGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyingGroupsService buyingGroupsService;

    private final BuyingGroupsQueryService buyingGroupsQueryService;

    public BuyingGroupsResource(BuyingGroupsService buyingGroupsService, BuyingGroupsQueryService buyingGroupsQueryService) {
        this.buyingGroupsService = buyingGroupsService;
        this.buyingGroupsQueryService = buyingGroupsQueryService;
    }

    /**
     * {@code POST  /buying-groups} : Create a new buyingGroups.
     *
     * @param buyingGroupsDTO the buyingGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyingGroupsDTO, or with status {@code 400 (Bad Request)} if the buyingGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buying-groups")
    public ResponseEntity<BuyingGroupsDTO> createBuyingGroups(@Valid @RequestBody BuyingGroupsDTO buyingGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save BuyingGroups : {}", buyingGroupsDTO);
        if (buyingGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyingGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyingGroupsDTO result = buyingGroupsService.save(buyingGroupsDTO);
        return ResponseEntity.created(new URI("/api/buying-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buying-groups} : Updates an existing buyingGroups.
     *
     * @param buyingGroupsDTO the buyingGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyingGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the buyingGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyingGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buying-groups")
    public ResponseEntity<BuyingGroupsDTO> updateBuyingGroups(@Valid @RequestBody BuyingGroupsDTO buyingGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update BuyingGroups : {}", buyingGroupsDTO);
        if (buyingGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuyingGroupsDTO result = buyingGroupsService.save(buyingGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyingGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /buying-groups} : get all the buyingGroups.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyingGroups in body.
     */
    @GetMapping("/buying-groups")
    public ResponseEntity<List<BuyingGroupsDTO>> getAllBuyingGroups(BuyingGroupsCriteria criteria) {
        log.debug("REST request to get BuyingGroups by criteria: {}", criteria);
        List<BuyingGroupsDTO> entityList = buyingGroupsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /buying-groups/count} : count all the buyingGroups.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/buying-groups/count")
    public ResponseEntity<Long> countBuyingGroups(BuyingGroupsCriteria criteria) {
        log.debug("REST request to count BuyingGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(buyingGroupsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /buying-groups/:id} : get the "id" buyingGroups.
     *
     * @param id the id of the buyingGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyingGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buying-groups/{id}")
    public ResponseEntity<BuyingGroupsDTO> getBuyingGroups(@PathVariable Long id) {
        log.debug("REST request to get BuyingGroups : {}", id);
        Optional<BuyingGroupsDTO> buyingGroupsDTO = buyingGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyingGroupsDTO);
    }

    /**
     * {@code DELETE  /buying-groups/:id} : delete the "id" buyingGroups.
     *
     * @param id the id of the buyingGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buying-groups/{id}")
    public ResponseEntity<Void> deleteBuyingGroups(@PathVariable Long id) {
        log.debug("REST request to delete BuyingGroups : {}", id);
        buyingGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
