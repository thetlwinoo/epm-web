package com.epmweb.server.web.rest;

import com.epmweb.server.service.StockItemHoldingsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.StockItemHoldingsDTO;
import com.epmweb.server.service.dto.StockItemHoldingsCriteria;
import com.epmweb.server.service.StockItemHoldingsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.StockItemHoldings}.
 */
@RestController
@RequestMapping("/api")
public class StockItemHoldingsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemHoldingsResource.class);

    private static final String ENTITY_NAME = "stockItemHoldings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemHoldingsService stockItemHoldingsService;

    private final StockItemHoldingsQueryService stockItemHoldingsQueryService;

    public StockItemHoldingsResource(StockItemHoldingsService stockItemHoldingsService, StockItemHoldingsQueryService stockItemHoldingsQueryService) {
        this.stockItemHoldingsService = stockItemHoldingsService;
        this.stockItemHoldingsQueryService = stockItemHoldingsQueryService;
    }

    /**
     * {@code POST  /stock-item-holdings} : Create a new stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the stockItemHoldingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItemHoldingsDTO, or with status {@code 400 (Bad Request)} if the stockItemHoldings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-item-holdings")
    public ResponseEntity<StockItemHoldingsDTO> createStockItemHoldings(@Valid @RequestBody StockItemHoldingsDTO stockItemHoldingsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemHoldings : {}", stockItemHoldingsDTO);
        if (stockItemHoldingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemHoldings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemHoldingsDTO result = stockItemHoldingsService.save(stockItemHoldingsDTO);
        return ResponseEntity.created(new URI("/api/stock-item-holdings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-item-holdings} : Updates an existing stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the stockItemHoldingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItemHoldingsDTO,
     * or with status {@code 400 (Bad Request)} if the stockItemHoldingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItemHoldingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-item-holdings")
    public ResponseEntity<StockItemHoldingsDTO> updateStockItemHoldings(@Valid @RequestBody StockItemHoldingsDTO stockItemHoldingsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemHoldings : {}", stockItemHoldingsDTO);
        if (stockItemHoldingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemHoldingsDTO result = stockItemHoldingsService.save(stockItemHoldingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItemHoldingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-item-holdings} : get all the stockItemHoldings.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItemHoldings in body.
     */
    @GetMapping("/stock-item-holdings")
    public ResponseEntity<List<StockItemHoldingsDTO>> getAllStockItemHoldings(StockItemHoldingsCriteria criteria) {
        log.debug("REST request to get StockItemHoldings by criteria: {}", criteria);
        List<StockItemHoldingsDTO> entityList = stockItemHoldingsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /stock-item-holdings/count} : count all the stockItemHoldings.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/stock-item-holdings/count")
    public ResponseEntity<Long> countStockItemHoldings(StockItemHoldingsCriteria criteria) {
        log.debug("REST request to count StockItemHoldings by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemHoldingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-item-holdings/:id} : get the "id" stockItemHoldings.
     *
     * @param id the id of the stockItemHoldingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItemHoldingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-item-holdings/{id}")
    public ResponseEntity<StockItemHoldingsDTO> getStockItemHoldings(@PathVariable Long id) {
        log.debug("REST request to get StockItemHoldings : {}", id);
        Optional<StockItemHoldingsDTO> stockItemHoldingsDTO = stockItemHoldingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemHoldingsDTO);
    }

    /**
     * {@code DELETE  /stock-item-holdings/:id} : delete the "id" stockItemHoldings.
     *
     * @param id the id of the stockItemHoldingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-item-holdings/{id}")
    public ResponseEntity<Void> deleteStockItemHoldings(@PathVariable Long id) {
        log.debug("REST request to delete StockItemHoldings : {}", id);
        stockItemHoldingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
