package com.epmweb.server.web.rest;

import com.epmweb.server.service.PersonPhoneService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PersonPhoneDTO;
import com.epmweb.server.service.dto.PersonPhoneCriteria;
import com.epmweb.server.service.PersonPhoneQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.PersonPhone}.
 */
@RestController
@RequestMapping("/api")
public class PersonPhoneResource {

    private final Logger log = LoggerFactory.getLogger(PersonPhoneResource.class);

    private static final String ENTITY_NAME = "personPhone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonPhoneService personPhoneService;

    private final PersonPhoneQueryService personPhoneQueryService;

    public PersonPhoneResource(PersonPhoneService personPhoneService, PersonPhoneQueryService personPhoneQueryService) {
        this.personPhoneService = personPhoneService;
        this.personPhoneQueryService = personPhoneQueryService;
    }

    /**
     * {@code POST  /person-phones} : Create a new personPhone.
     *
     * @param personPhoneDTO the personPhoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personPhoneDTO, or with status {@code 400 (Bad Request)} if the personPhone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-phones")
    public ResponseEntity<PersonPhoneDTO> createPersonPhone(@Valid @RequestBody PersonPhoneDTO personPhoneDTO) throws URISyntaxException {
        log.debug("REST request to save PersonPhone : {}", personPhoneDTO);
        if (personPhoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new personPhone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonPhoneDTO result = personPhoneService.save(personPhoneDTO);
        return ResponseEntity.created(new URI("/api/person-phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-phones} : Updates an existing personPhone.
     *
     * @param personPhoneDTO the personPhoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personPhoneDTO,
     * or with status {@code 400 (Bad Request)} if the personPhoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personPhoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-phones")
    public ResponseEntity<PersonPhoneDTO> updatePersonPhone(@Valid @RequestBody PersonPhoneDTO personPhoneDTO) throws URISyntaxException {
        log.debug("REST request to update PersonPhone : {}", personPhoneDTO);
        if (personPhoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonPhoneDTO result = personPhoneService.save(personPhoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personPhoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-phones} : get all the personPhones.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personPhones in body.
     */
    @GetMapping("/person-phones")
    public ResponseEntity<List<PersonPhoneDTO>> getAllPersonPhones(PersonPhoneCriteria criteria) {
        log.debug("REST request to get PersonPhones by criteria: {}", criteria);
        List<PersonPhoneDTO> entityList = personPhoneQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /person-phones/count} : count all the personPhones.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/person-phones/count")
    public ResponseEntity<Long> countPersonPhones(PersonPhoneCriteria criteria) {
        log.debug("REST request to count PersonPhones by criteria: {}", criteria);
        return ResponseEntity.ok().body(personPhoneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /person-phones/:id} : get the "id" personPhone.
     *
     * @param id the id of the personPhoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personPhoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-phones/{id}")
    public ResponseEntity<PersonPhoneDTO> getPersonPhone(@PathVariable Long id) {
        log.debug("REST request to get PersonPhone : {}", id);
        Optional<PersonPhoneDTO> personPhoneDTO = personPhoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personPhoneDTO);
    }

    /**
     * {@code DELETE  /person-phones/:id} : delete the "id" personPhone.
     *
     * @param id the id of the personPhoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-phones/{id}")
    public ResponseEntity<Void> deletePersonPhone(@PathVariable Long id) {
        log.debug("REST request to delete PersonPhone : {}", id);
        personPhoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
