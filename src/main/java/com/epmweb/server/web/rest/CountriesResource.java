package com.epmweb.server.web.rest;

import com.epmweb.server.service.CountriesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CountriesDTO;
import com.epmweb.server.service.dto.CountriesCriteria;
import com.epmweb.server.service.CountriesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Countries}.
 */
@RestController
@RequestMapping("/api")
public class CountriesResource {

    private final Logger log = LoggerFactory.getLogger(CountriesResource.class);

    private static final String ENTITY_NAME = "countries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountriesService countriesService;

    private final CountriesQueryService countriesQueryService;

    public CountriesResource(CountriesService countriesService, CountriesQueryService countriesQueryService) {
        this.countriesService = countriesService;
        this.countriesQueryService = countriesQueryService;
    }

    /**
     * {@code POST  /countries} : Create a new countries.
     *
     * @param countriesDTO the countriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countriesDTO, or with status {@code 400 (Bad Request)} if the countries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/countries")
    public ResponseEntity<CountriesDTO> createCountries(@Valid @RequestBody CountriesDTO countriesDTO) throws URISyntaxException {
        log.debug("REST request to save Countries : {}", countriesDTO);
        if (countriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new countries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountriesDTO result = countriesService.save(countriesDTO);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /countries} : Updates an existing countries.
     *
     * @param countriesDTO the countriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countriesDTO,
     * or with status {@code 400 (Bad Request)} if the countriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/countries")
    public ResponseEntity<CountriesDTO> updateCountries(@Valid @RequestBody CountriesDTO countriesDTO) throws URISyntaxException {
        log.debug("REST request to update Countries : {}", countriesDTO);
        if (countriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountriesDTO result = countriesService.save(countriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /countries} : get all the countries.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
     */
    @GetMapping("/countries")
    public ResponseEntity<List<CountriesDTO>> getAllCountries(CountriesCriteria criteria) {
        log.debug("REST request to get Countries by criteria: {}", criteria);
        List<CountriesDTO> entityList = countriesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /countries/count} : count all the countries.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/countries/count")
    public ResponseEntity<Long> countCountries(CountriesCriteria criteria) {
        log.debug("REST request to count Countries by criteria: {}", criteria);
        return ResponseEntity.ok().body(countriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /countries/:id} : get the "id" countries.
     *
     * @param id the id of the countriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/countries/{id}")
    public ResponseEntity<CountriesDTO> getCountries(@PathVariable Long id) {
        log.debug("REST request to get Countries : {}", id);
        Optional<CountriesDTO> countriesDTO = countriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countriesDTO);
    }

    /**
     * {@code DELETE  /countries/:id} : delete the "id" countries.
     *
     * @param id the id of the countriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountries(@PathVariable Long id) {
        log.debug("REST request to delete Countries : {}", id);
        countriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
