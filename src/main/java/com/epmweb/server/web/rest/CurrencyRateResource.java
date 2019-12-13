package com.epmweb.server.web.rest;

import com.epmweb.server.service.CurrencyRateService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CurrencyRateDTO;
import com.epmweb.server.service.dto.CurrencyRateCriteria;
import com.epmweb.server.service.CurrencyRateQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.CurrencyRate}.
 */
@RestController
@RequestMapping("/api")
public class CurrencyRateResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateResource.class);

    private static final String ENTITY_NAME = "currencyRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencyRateService currencyRateService;

    private final CurrencyRateQueryService currencyRateQueryService;

    public CurrencyRateResource(CurrencyRateService currencyRateService, CurrencyRateQueryService currencyRateQueryService) {
        this.currencyRateService = currencyRateService;
        this.currencyRateQueryService = currencyRateQueryService;
    }

    /**
     * {@code POST  /currency-rates} : Create a new currencyRate.
     *
     * @param currencyRateDTO the currencyRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyRateDTO, or with status {@code 400 (Bad Request)} if the currencyRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currency-rates")
    public ResponseEntity<CurrencyRateDTO> createCurrencyRate(@Valid @RequestBody CurrencyRateDTO currencyRateDTO) throws URISyntaxException {
        log.debug("REST request to save CurrencyRate : {}", currencyRateDTO);
        if (currencyRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new currencyRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyRateDTO result = currencyRateService.save(currencyRateDTO);
        return ResponseEntity.created(new URI("/api/currency-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currency-rates} : Updates an existing currencyRate.
     *
     * @param currencyRateDTO the currencyRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyRateDTO,
     * or with status {@code 400 (Bad Request)} if the currencyRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currency-rates")
    public ResponseEntity<CurrencyRateDTO> updateCurrencyRate(@Valid @RequestBody CurrencyRateDTO currencyRateDTO) throws URISyntaxException {
        log.debug("REST request to update CurrencyRate : {}", currencyRateDTO);
        if (currencyRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrencyRateDTO result = currencyRateService.save(currencyRateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currencyRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /currency-rates} : get all the currencyRates.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencyRates in body.
     */
    @GetMapping("/currency-rates")
    public ResponseEntity<List<CurrencyRateDTO>> getAllCurrencyRates(CurrencyRateCriteria criteria) {
        log.debug("REST request to get CurrencyRates by criteria: {}", criteria);
        List<CurrencyRateDTO> entityList = currencyRateQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /currency-rates/count} : count all the currencyRates.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/currency-rates/count")
    public ResponseEntity<Long> countCurrencyRates(CurrencyRateCriteria criteria) {
        log.debug("REST request to count CurrencyRates by criteria: {}", criteria);
        return ResponseEntity.ok().body(currencyRateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /currency-rates/:id} : get the "id" currencyRate.
     *
     * @param id the id of the currencyRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currency-rates/{id}")
    public ResponseEntity<CurrencyRateDTO> getCurrencyRate(@PathVariable Long id) {
        log.debug("REST request to get CurrencyRate : {}", id);
        Optional<CurrencyRateDTO> currencyRateDTO = currencyRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyRateDTO);
    }

    /**
     * {@code DELETE  /currency-rates/:id} : delete the "id" currencyRate.
     *
     * @param id the id of the currencyRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currency-rates/{id}")
    public ResponseEntity<Void> deleteCurrencyRate(@PathVariable Long id) {
        log.debug("REST request to delete CurrencyRate : {}", id);
        currencyRateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
