package com.epmweb.server.web.rest;

import com.epmweb.server.service.StockItemTransactionsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.StockItemTransactionsDTO;
import com.epmweb.server.service.dto.StockItemTransactionsCriteria;
import com.epmweb.server.service.StockItemTransactionsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.StockItemTransactions}.
 */
@RestController
@RequestMapping("/api")
public class StockItemTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemTransactionsResource.class);

    private static final String ENTITY_NAME = "stockItemTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemTransactionsService stockItemTransactionsService;

    private final StockItemTransactionsQueryService stockItemTransactionsQueryService;

    public StockItemTransactionsResource(StockItemTransactionsService stockItemTransactionsService, StockItemTransactionsQueryService stockItemTransactionsQueryService) {
        this.stockItemTransactionsService = stockItemTransactionsService;
        this.stockItemTransactionsQueryService = stockItemTransactionsQueryService;
    }

    /**
     * {@code POST  /stock-item-transactions} : Create a new stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the stockItemTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItemTransactionsDTO, or with status {@code 400 (Bad Request)} if the stockItemTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-item-transactions")
    public ResponseEntity<StockItemTransactionsDTO> createStockItemTransactions(@Valid @RequestBody StockItemTransactionsDTO stockItemTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItemTransactions : {}", stockItemTransactionsDTO);
        if (stockItemTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItemTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemTransactionsDTO result = stockItemTransactionsService.save(stockItemTransactionsDTO);
        return ResponseEntity.created(new URI("/api/stock-item-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-item-transactions} : Updates an existing stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the stockItemTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItemTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the stockItemTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItemTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-item-transactions")
    public ResponseEntity<StockItemTransactionsDTO> updateStockItemTransactions(@Valid @RequestBody StockItemTransactionsDTO stockItemTransactionsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItemTransactions : {}", stockItemTransactionsDTO);
        if (stockItemTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemTransactionsDTO result = stockItemTransactionsService.save(stockItemTransactionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItemTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-item-transactions} : get all the stockItemTransactions.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItemTransactions in body.
     */
    @GetMapping("/stock-item-transactions")
    public ResponseEntity<List<StockItemTransactionsDTO>> getAllStockItemTransactions(StockItemTransactionsCriteria criteria) {
        log.debug("REST request to get StockItemTransactions by criteria: {}", criteria);
        List<StockItemTransactionsDTO> entityList = stockItemTransactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /stock-item-transactions/count} : count all the stockItemTransactions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/stock-item-transactions/count")
    public ResponseEntity<Long> countStockItemTransactions(StockItemTransactionsCriteria criteria) {
        log.debug("REST request to count StockItemTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-item-transactions/:id} : get the "id" stockItemTransactions.
     *
     * @param id the id of the stockItemTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItemTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-item-transactions/{id}")
    public ResponseEntity<StockItemTransactionsDTO> getStockItemTransactions(@PathVariable Long id) {
        log.debug("REST request to get StockItemTransactions : {}", id);
        Optional<StockItemTransactionsDTO> stockItemTransactionsDTO = stockItemTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemTransactionsDTO);
    }

    /**
     * {@code DELETE  /stock-item-transactions/:id} : delete the "id" stockItemTransactions.
     *
     * @param id the id of the stockItemTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-item-transactions/{id}")
    public ResponseEntity<Void> deleteStockItemTransactions(@PathVariable Long id) {
        log.debug("REST request to delete StockItemTransactions : {}", id);
        stockItemTransactionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
