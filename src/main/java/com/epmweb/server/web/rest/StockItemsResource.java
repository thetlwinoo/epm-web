package com.epmweb.server.web.rest;

import com.epmweb.server.service.StockItemsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.StockItemsDTO;
import com.epmweb.server.service.dto.StockItemsCriteria;
import com.epmweb.server.service.StockItemsQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.epmweb.server.domain.StockItems}.
 */
@RestController
@RequestMapping("/api")
public class StockItemsResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsResource.class);

    private static final String ENTITY_NAME = "stockItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockItemsService stockItemsService;

    private final StockItemsQueryService stockItemsQueryService;

    public StockItemsResource(StockItemsService stockItemsService, StockItemsQueryService stockItemsQueryService) {
        this.stockItemsService = stockItemsService;
        this.stockItemsQueryService = stockItemsQueryService;
    }

    /**
     * {@code POST  /stock-items} : Create a new stockItems.
     *
     * @param stockItemsDTO the stockItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockItemsDTO, or with status {@code 400 (Bad Request)} if the stockItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-items")
    public ResponseEntity<StockItemsDTO> createStockItems(@Valid @RequestBody StockItemsDTO stockItemsDTO) throws URISyntaxException {
        log.debug("REST request to save StockItems : {}", stockItemsDTO);
        if (stockItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockItemsDTO result = stockItemsService.save(stockItemsDTO);
        return ResponseEntity.created(new URI("/api/stock-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-items} : Updates an existing stockItems.
     *
     * @param stockItemsDTO the stockItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockItemsDTO,
     * or with status {@code 400 (Bad Request)} if the stockItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-items")
    public ResponseEntity<StockItemsDTO> updateStockItems(@Valid @RequestBody StockItemsDTO stockItemsDTO) throws URISyntaxException {
        log.debug("REST request to update StockItems : {}", stockItemsDTO);
        if (stockItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockItemsDTO result = stockItemsService.save(stockItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-items} : get all the stockItems.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockItems in body.
     */
    @GetMapping("/stock-items")
    public ResponseEntity<List<StockItemsDTO>> getAllStockItems(StockItemsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StockItems by criteria: {}", criteria);
        Page<StockItemsDTO> page = stockItemsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /stock-items/count} : count all the stockItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/stock-items/count")
    public ResponseEntity<Long> countStockItems(StockItemsCriteria criteria) {
        log.debug("REST request to count StockItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(stockItemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /stock-items/:id} : get the "id" stockItems.
     *
     * @param id the id of the stockItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-items/{id}")
    public ResponseEntity<StockItemsDTO> getStockItems(@PathVariable Long id) {
        log.debug("REST request to get StockItems : {}", id);
        Optional<StockItemsDTO> stockItemsDTO = stockItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockItemsDTO);
    }

    /**
     * {@code DELETE  /stock-items/:id} : delete the "id" stockItems.
     *
     * @param id the id of the stockItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-items/{id}")
    public ResponseEntity<Void> deleteStockItems(@PathVariable Long id) {
        log.debug("REST request to delete StockItems : {}", id);
        stockItemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
