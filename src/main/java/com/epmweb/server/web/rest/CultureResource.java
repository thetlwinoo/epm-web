package com.epmweb.server.web.rest;

import com.epmweb.server.service.CultureService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.CultureDTO;
import com.epmweb.server.service.dto.CultureCriteria;
import com.epmweb.server.service.CultureQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Culture}.
 */
@RestController
@RequestMapping("/api")
public class CultureResource {

    private final Logger log = LoggerFactory.getLogger(CultureResource.class);

    private static final String ENTITY_NAME = "culture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CultureService cultureService;

    private final CultureQueryService cultureQueryService;

    public CultureResource(CultureService cultureService, CultureQueryService cultureQueryService) {
        this.cultureService = cultureService;
        this.cultureQueryService = cultureQueryService;
    }

    /**
     * {@code POST  /cultures} : Create a new culture.
     *
     * @param cultureDTO the cultureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cultureDTO, or with status {@code 400 (Bad Request)} if the culture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cultures")
    public ResponseEntity<CultureDTO> createCulture(@Valid @RequestBody CultureDTO cultureDTO) throws URISyntaxException {
        log.debug("REST request to save Culture : {}", cultureDTO);
        if (cultureDTO.getId() != null) {
            throw new BadRequestAlertException("A new culture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CultureDTO result = cultureService.save(cultureDTO);
        return ResponseEntity.created(new URI("/api/cultures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cultures} : Updates an existing culture.
     *
     * @param cultureDTO the cultureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultureDTO,
     * or with status {@code 400 (Bad Request)} if the cultureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cultureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cultures")
    public ResponseEntity<CultureDTO> updateCulture(@Valid @RequestBody CultureDTO cultureDTO) throws URISyntaxException {
        log.debug("REST request to update Culture : {}", cultureDTO);
        if (cultureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CultureDTO result = cultureService.save(cultureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cultureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cultures} : get all the cultures.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cultures in body.
     */
    @GetMapping("/cultures")
    public ResponseEntity<List<CultureDTO>> getAllCultures(CultureCriteria criteria) {
        log.debug("REST request to get Cultures by criteria: {}", criteria);
        List<CultureDTO> entityList = cultureQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /cultures/count} : count all the cultures.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cultures/count")
    public ResponseEntity<Long> countCultures(CultureCriteria criteria) {
        log.debug("REST request to count Cultures by criteria: {}", criteria);
        return ResponseEntity.ok().body(cultureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cultures/:id} : get the "id" culture.
     *
     * @param id the id of the cultureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cultureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cultures/{id}")
    public ResponseEntity<CultureDTO> getCulture(@PathVariable Long id) {
        log.debug("REST request to get Culture : {}", id);
        Optional<CultureDTO> cultureDTO = cultureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cultureDTO);
    }

    /**
     * {@code DELETE  /cultures/:id} : delete the "id" culture.
     *
     * @param id the id of the cultureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cultures/{id}")
    public ResponseEntity<Void> deleteCulture(@PathVariable Long id) {
        log.debug("REST request to delete Culture : {}", id);
        cultureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
