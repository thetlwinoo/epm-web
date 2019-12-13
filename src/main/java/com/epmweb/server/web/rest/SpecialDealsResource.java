package com.epmweb.server.web.rest;

import com.epmweb.server.service.SpecialDealsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.SpecialDealsDTO;
import com.epmweb.server.service.dto.SpecialDealsCriteria;
import com.epmweb.server.service.SpecialDealsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.SpecialDeals}.
 */
@RestController
@RequestMapping("/api")
public class SpecialDealsResource {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsResource.class);

    private static final String ENTITY_NAME = "specialDeals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialDealsService specialDealsService;

    private final SpecialDealsQueryService specialDealsQueryService;

    public SpecialDealsResource(SpecialDealsService specialDealsService, SpecialDealsQueryService specialDealsQueryService) {
        this.specialDealsService = specialDealsService;
        this.specialDealsQueryService = specialDealsQueryService;
    }

    /**
     * {@code POST  /special-deals} : Create a new specialDeals.
     *
     * @param specialDealsDTO the specialDealsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialDealsDTO, or with status {@code 400 (Bad Request)} if the specialDeals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/special-deals")
    public ResponseEntity<SpecialDealsDTO> createSpecialDeals(@Valid @RequestBody SpecialDealsDTO specialDealsDTO) throws URISyntaxException {
        log.debug("REST request to save SpecialDeals : {}", specialDealsDTO);
        if (specialDealsDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialDeals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialDealsDTO result = specialDealsService.save(specialDealsDTO);
        return ResponseEntity.created(new URI("/api/special-deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /special-deals} : Updates an existing specialDeals.
     *
     * @param specialDealsDTO the specialDealsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialDealsDTO,
     * or with status {@code 400 (Bad Request)} if the specialDealsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialDealsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/special-deals")
    public ResponseEntity<SpecialDealsDTO> updateSpecialDeals(@Valid @RequestBody SpecialDealsDTO specialDealsDTO) throws URISyntaxException {
        log.debug("REST request to update SpecialDeals : {}", specialDealsDTO);
        if (specialDealsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpecialDealsDTO result = specialDealsService.save(specialDealsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialDealsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /special-deals} : get all the specialDeals.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialDeals in body.
     */
    @GetMapping("/special-deals")
    public ResponseEntity<List<SpecialDealsDTO>> getAllSpecialDeals(SpecialDealsCriteria criteria) {
        log.debug("REST request to get SpecialDeals by criteria: {}", criteria);
        List<SpecialDealsDTO> entityList = specialDealsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /special-deals/count} : count all the specialDeals.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/special-deals/count")
    public ResponseEntity<Long> countSpecialDeals(SpecialDealsCriteria criteria) {
        log.debug("REST request to count SpecialDeals by criteria: {}", criteria);
        return ResponseEntity.ok().body(specialDealsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /special-deals/:id} : get the "id" specialDeals.
     *
     * @param id the id of the specialDealsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialDealsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/special-deals/{id}")
    public ResponseEntity<SpecialDealsDTO> getSpecialDeals(@PathVariable Long id) {
        log.debug("REST request to get SpecialDeals : {}", id);
        Optional<SpecialDealsDTO> specialDealsDTO = specialDealsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialDealsDTO);
    }

    /**
     * {@code DELETE  /special-deals/:id} : delete the "id" specialDeals.
     *
     * @param id the id of the specialDealsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/special-deals/{id}")
    public ResponseEntity<Void> deleteSpecialDeals(@PathVariable Long id) {
        log.debug("REST request to delete SpecialDeals : {}", id);
        specialDealsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
