package com.epmweb.server.web.rest;

import com.epmweb.server.service.PersonEmailAddressService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PersonEmailAddressDTO;
import com.epmweb.server.service.dto.PersonEmailAddressCriteria;
import com.epmweb.server.service.PersonEmailAddressQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.PersonEmailAddress}.
 */
@RestController
@RequestMapping("/api")
public class PersonEmailAddressResource {

    private final Logger log = LoggerFactory.getLogger(PersonEmailAddressResource.class);

    private static final String ENTITY_NAME = "personEmailAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonEmailAddressService personEmailAddressService;

    private final PersonEmailAddressQueryService personEmailAddressQueryService;

    public PersonEmailAddressResource(PersonEmailAddressService personEmailAddressService, PersonEmailAddressQueryService personEmailAddressQueryService) {
        this.personEmailAddressService = personEmailAddressService;
        this.personEmailAddressQueryService = personEmailAddressQueryService;
    }

    /**
     * {@code POST  /person-email-addresses} : Create a new personEmailAddress.
     *
     * @param personEmailAddressDTO the personEmailAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personEmailAddressDTO, or with status {@code 400 (Bad Request)} if the personEmailAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-email-addresses")
    public ResponseEntity<PersonEmailAddressDTO> createPersonEmailAddress(@Valid @RequestBody PersonEmailAddressDTO personEmailAddressDTO) throws URISyntaxException {
        log.debug("REST request to save PersonEmailAddress : {}", personEmailAddressDTO);
        if (personEmailAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new personEmailAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonEmailAddressDTO result = personEmailAddressService.save(personEmailAddressDTO);
        return ResponseEntity.created(new URI("/api/person-email-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-email-addresses} : Updates an existing personEmailAddress.
     *
     * @param personEmailAddressDTO the personEmailAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personEmailAddressDTO,
     * or with status {@code 400 (Bad Request)} if the personEmailAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personEmailAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-email-addresses")
    public ResponseEntity<PersonEmailAddressDTO> updatePersonEmailAddress(@Valid @RequestBody PersonEmailAddressDTO personEmailAddressDTO) throws URISyntaxException {
        log.debug("REST request to update PersonEmailAddress : {}", personEmailAddressDTO);
        if (personEmailAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonEmailAddressDTO result = personEmailAddressService.save(personEmailAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personEmailAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-email-addresses} : get all the personEmailAddresses.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personEmailAddresses in body.
     */
    @GetMapping("/person-email-addresses")
    public ResponseEntity<List<PersonEmailAddressDTO>> getAllPersonEmailAddresses(PersonEmailAddressCriteria criteria) {
        log.debug("REST request to get PersonEmailAddresses by criteria: {}", criteria);
        List<PersonEmailAddressDTO> entityList = personEmailAddressQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /person-email-addresses/count} : count all the personEmailAddresses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/person-email-addresses/count")
    public ResponseEntity<Long> countPersonEmailAddresses(PersonEmailAddressCriteria criteria) {
        log.debug("REST request to count PersonEmailAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(personEmailAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /person-email-addresses/:id} : get the "id" personEmailAddress.
     *
     * @param id the id of the personEmailAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personEmailAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-email-addresses/{id}")
    public ResponseEntity<PersonEmailAddressDTO> getPersonEmailAddress(@PathVariable Long id) {
        log.debug("REST request to get PersonEmailAddress : {}", id);
        Optional<PersonEmailAddressDTO> personEmailAddressDTO = personEmailAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personEmailAddressDTO);
    }

    /**
     * {@code DELETE  /person-email-addresses/:id} : delete the "id" personEmailAddress.
     *
     * @param id the id of the personEmailAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-email-addresses/{id}")
    public ResponseEntity<Void> deletePersonEmailAddress(@PathVariable Long id) {
        log.debug("REST request to delete PersonEmailAddress : {}", id);
        personEmailAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
