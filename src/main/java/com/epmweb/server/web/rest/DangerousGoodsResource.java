package com.epmweb.server.web.rest;

import com.epmweb.server.service.DangerousGoodsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.DangerousGoodsDTO;
import com.epmweb.server.service.dto.DangerousGoodsCriteria;
import com.epmweb.server.service.DangerousGoodsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.DangerousGoods}.
 */
@RestController
@RequestMapping("/api")
public class DangerousGoodsResource {

    private final Logger log = LoggerFactory.getLogger(DangerousGoodsResource.class);

    private static final String ENTITY_NAME = "dangerousGoods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DangerousGoodsService dangerousGoodsService;

    private final DangerousGoodsQueryService dangerousGoodsQueryService;

    public DangerousGoodsResource(DangerousGoodsService dangerousGoodsService, DangerousGoodsQueryService dangerousGoodsQueryService) {
        this.dangerousGoodsService = dangerousGoodsService;
        this.dangerousGoodsQueryService = dangerousGoodsQueryService;
    }

    /**
     * {@code POST  /dangerous-goods} : Create a new dangerousGoods.
     *
     * @param dangerousGoodsDTO the dangerousGoodsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dangerousGoodsDTO, or with status {@code 400 (Bad Request)} if the dangerousGoods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dangerous-goods")
    public ResponseEntity<DangerousGoodsDTO> createDangerousGoods(@Valid @RequestBody DangerousGoodsDTO dangerousGoodsDTO) throws URISyntaxException {
        log.debug("REST request to save DangerousGoods : {}", dangerousGoodsDTO);
        if (dangerousGoodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dangerousGoods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DangerousGoodsDTO result = dangerousGoodsService.save(dangerousGoodsDTO);
        return ResponseEntity.created(new URI("/api/dangerous-goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dangerous-goods} : Updates an existing dangerousGoods.
     *
     * @param dangerousGoodsDTO the dangerousGoodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dangerousGoodsDTO,
     * or with status {@code 400 (Bad Request)} if the dangerousGoodsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dangerousGoodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dangerous-goods")
    public ResponseEntity<DangerousGoodsDTO> updateDangerousGoods(@Valid @RequestBody DangerousGoodsDTO dangerousGoodsDTO) throws URISyntaxException {
        log.debug("REST request to update DangerousGoods : {}", dangerousGoodsDTO);
        if (dangerousGoodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DangerousGoodsDTO result = dangerousGoodsService.save(dangerousGoodsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dangerousGoodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dangerous-goods} : get all the dangerousGoods.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dangerousGoods in body.
     */
    @GetMapping("/dangerous-goods")
    public ResponseEntity<List<DangerousGoodsDTO>> getAllDangerousGoods(DangerousGoodsCriteria criteria) {
        log.debug("REST request to get DangerousGoods by criteria: {}", criteria);
        List<DangerousGoodsDTO> entityList = dangerousGoodsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /dangerous-goods/count} : count all the dangerousGoods.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/dangerous-goods/count")
    public ResponseEntity<Long> countDangerousGoods(DangerousGoodsCriteria criteria) {
        log.debug("REST request to count DangerousGoods by criteria: {}", criteria);
        return ResponseEntity.ok().body(dangerousGoodsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dangerous-goods/:id} : get the "id" dangerousGoods.
     *
     * @param id the id of the dangerousGoodsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dangerousGoodsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dangerous-goods/{id}")
    public ResponseEntity<DangerousGoodsDTO> getDangerousGoods(@PathVariable Long id) {
        log.debug("REST request to get DangerousGoods : {}", id);
        Optional<DangerousGoodsDTO> dangerousGoodsDTO = dangerousGoodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dangerousGoodsDTO);
    }

    /**
     * {@code DELETE  /dangerous-goods/:id} : delete the "id" dangerousGoods.
     *
     * @param id the id of the dangerousGoodsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dangerous-goods/{id}")
    public ResponseEntity<Void> deleteDangerousGoods(@PathVariable Long id) {
        log.debug("REST request to delete DangerousGoods : {}", id);
        dangerousGoodsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
