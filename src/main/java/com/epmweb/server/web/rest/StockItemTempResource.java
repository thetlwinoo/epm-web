package com.epmweb.server.web.rest;

import com.epmweb.server.service.StockItemTempService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.StockItemTempDTO;
import com.epmweb.server.service.dto.StockItemTempCriteria;
import com.epmweb.server.service.StockItemTempQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.StockItemTemp}.
 */
@RestController
@RequestMapping("/api")
public class StockItemTempResource {

    private final Logger log = LoggerFactory.getLogger(StockItemTempResource.class);

    private static final String ENTITY_NAME = "stockItemTemp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemTempService stockItemTempService;

    private final StockItemTempQueryService stockItemTempQueryService;

    public StockItemTempResource(StockItemTempService stockItemTempService, StockItemTempQueryService stockItemTempQueryService) {
        this.stockItemTempService = stockItemTempService;
        this.stockItemTempQueryService = stockItemTempQueryService;
    }

    /**
     * {@code POST  /stock-item-temps} : Create a new stockItemTemp.
     *
     * @param stockItemTempDTO the stockItemTempDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItemTempDTO, or with status {@code 400 (Bad Request)} if the stockItemTemp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-item-temps")
    public ResponseEntity<StockItemTempDTO> createStockItemTemp(@Valid @RequestBody StockItemTempDTO stockItemTempDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemTemp : {}", stockItemTempDTO);
        if (stockItemTempDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemTemp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemTempDTO result = stockItemTempService.save(stockItemTempDTO);
        return ResponseEntity.created(new URI("/api/stock-item-temps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-item-temps} : Updates an existing stockItemTemp.
     *
     * @param stockItemTempDTO the stockItemTempDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItemTempDTO,
     * or with status {@code 400 (Bad Request)} if the stockItemTempDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItemTempDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-item-temps")
    public ResponseEntity<StockItemTempDTO> updateStockItemTemp(@Valid @RequestBody StockItemTempDTO stockItemTempDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemTemp : {}", stockItemTempDTO);
        if (stockItemTempDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemTempDTO result = stockItemTempService.save(stockItemTempDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItemTempDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-item-temps} : get all the stockItemTemps.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItemTemps in body.
     */
    @GetMapping("/stock-item-temps")
    public ResponseEntity<List<StockItemTempDTO>> getAllStockItemTemps(StockItemTempCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockItemTemps by criteria: {}", criteria);
        Page<StockItemTempDTO> page = stockItemTempQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /stock-item-temps/count} : count all the stockItemTemps.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/stock-item-temps/count")
    public ResponseEntity<Long> countStockItemTemps(StockItemTempCriteria criteria) {
        log.debug("REST request to count StockItemTemps by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemTempQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-item-temps/:id} : get the "id" stockItemTemp.
     *
     * @param id the id of the stockItemTempDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItemTempDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-item-temps/{id}")
    public ResponseEntity<StockItemTempDTO> getStockItemTemp(@PathVariable Long id) {
        log.debug("REST request to get StockItemTemp : {}", id);
        Optional<StockItemTempDTO> stockItemTempDTO = stockItemTempService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemTempDTO);
    }

    /**
     * {@code DELETE  /stock-item-temps/:id} : delete the "id" stockItemTemp.
     *
     * @param id the id of the stockItemTempDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-item-temps/{id}")
    public ResponseEntity<Void> deleteStockItemTemp(@PathVariable Long id) {
        log.debug("REST request to delete StockItemTemp : {}", id);
        stockItemTempService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
