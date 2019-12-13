package com.epmweb.server.web.rest;

import com.epmweb.server.service.PeopleService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PeopleDTO;
import com.epmweb.server.service.dto.PeopleCriteria;
import com.epmweb.server.service.PeopleQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.epmweb.server.domain.People}.
 */
@RestController
@RequestMapping("/api")
public class PeopleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private static final String ENTITY_NAME = "people";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeopleService peopleService;

    private final PeopleQueryService peopleQueryService;

    public PeopleResource(PeopleService peopleService, PeopleQueryService peopleQueryService) {
        this.peopleService = peopleService;
        this.peopleQueryService = peopleQueryService;
    }

    /**
     * {@code POST  /people} : Create a new people.
     *
     * @param peopleDTO the peopleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new peopleDTO, or with status {@code 400 (Bad Request)} if the people has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/people")
    public ResponseEntity<PeopleDTO> createPeople(@Valid @RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        log.debug("REST request to save People : {}", peopleDTO);
        if (peopleDTO.getId() != null) {
            throw new BadRequestAlertException("A new people cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeopleDTO result = peopleService.save(peopleDTO);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /people} : Updates an existing people.
     *
     * @param peopleDTO the peopleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peopleDTO,
     * or with status {@code 400 (Bad Request)} if the peopleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the peopleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/people")
    public ResponseEntity<PeopleDTO> updatePeople(@Valid @RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        log.debug("REST request to update People : {}", peopleDTO);
        if (peopleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeopleDTO result = peopleService.save(peopleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, peopleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /people} : get all the people.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("/people")
    public ResponseEntity<List<PeopleDTO>> getAllPeople(PeopleCriteria criteria) {
        log.debug("REST request to get People by criteria: {}", criteria);
        List<PeopleDTO> entityList = peopleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /people/count} : count all the people.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/people/count")
    public ResponseEntity<Long> countPeople(PeopleCriteria criteria) {
        log.debug("REST request to count People by criteria: {}", criteria);
        return ResponseEntity.ok().body(peopleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /people/:id} : get the "id" people.
     *
     * @param id the id of the peopleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the peopleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<PeopleDTO> getPeople(@PathVariable Long id) {
        log.debug("REST request to get People : {}", id);
        Optional<PeopleDTO> peopleDTO = peopleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(peopleDTO);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" people.
     *
     * @param id the id of the peopleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePeople(@PathVariable Long id) {
        log.debug("REST request to delete People : {}", id);
        peopleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
