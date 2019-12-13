package com.epmweb.server.web.rest;

import com.epmweb.server.service.PhoneNumberTypeService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PhoneNumberTypeDTO;
import com.epmweb.server.service.dto.PhoneNumberTypeCriteria;
import com.epmweb.server.service.PhoneNumberTypeQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.PhoneNumberType}.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberTypeResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberTypeResource.class);

    private static final String ENTITY_NAME = "phoneNumberType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhoneNumberTypeService phoneNumberTypeService;

    private final PhoneNumberTypeQueryService phoneNumberTypeQueryService;

    public PhoneNumberTypeResource(PhoneNumberTypeService phoneNumberTypeService, PhoneNumberTypeQueryService phoneNumberTypeQueryService) {
        this.phoneNumberTypeService = phoneNumberTypeService;
        this.phoneNumberTypeQueryService = phoneNumberTypeQueryService;
    }

    /**
     * {@code POST  /phone-number-types} : Create a new phoneNumberType.
     *
     * @param phoneNumberTypeDTO the phoneNumberTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneNumberTypeDTO, or with status {@code 400 (Bad Request)} if the phoneNumberType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phone-number-types")
    public ResponseEntity<PhoneNumberTypeDTO> createPhoneNumberType(@Valid @RequestBody PhoneNumberTypeDTO phoneNumberTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PhoneNumberType : {}", phoneNumberTypeDTO);
        if (phoneNumberTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new phoneNumberType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhoneNumberTypeDTO result = phoneNumberTypeService.save(phoneNumberTypeDTO);
        return ResponseEntity.created(new URI("/api/phone-number-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phone-number-types} : Updates an existing phoneNumberType.
     *
     * @param phoneNumberTypeDTO the phoneNumberTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneNumberTypeDTO,
     * or with status {@code 400 (Bad Request)} if the phoneNumberTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneNumberTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phone-number-types")
    public ResponseEntity<PhoneNumberTypeDTO> updatePhoneNumberType(@Valid @RequestBody PhoneNumberTypeDTO phoneNumberTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PhoneNumberType : {}", phoneNumberTypeDTO);
        if (phoneNumberTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhoneNumberTypeDTO result = phoneNumberTypeService.save(phoneNumberTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneNumberTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phone-number-types} : get all the phoneNumberTypes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phoneNumberTypes in body.
     */
    @GetMapping("/phone-number-types")
    public ResponseEntity<List<PhoneNumberTypeDTO>> getAllPhoneNumberTypes(PhoneNumberTypeCriteria criteria) {
        log.debug("REST request to get PhoneNumberTypes by criteria: {}", criteria);
        List<PhoneNumberTypeDTO> entityList = phoneNumberTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /phone-number-types/count} : count all the phoneNumberTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/phone-number-types/count")
    public ResponseEntity<Long> countPhoneNumberTypes(PhoneNumberTypeCriteria criteria) {
        log.debug("REST request to count PhoneNumberTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(phoneNumberTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /phone-number-types/:id} : get the "id" phoneNumberType.
     *
     * @param id the id of the phoneNumberTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneNumberTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phone-number-types/{id}")
    public ResponseEntity<PhoneNumberTypeDTO> getPhoneNumberType(@PathVariable Long id) {
        log.debug("REST request to get PhoneNumberType : {}", id);
        Optional<PhoneNumberTypeDTO> phoneNumberTypeDTO = phoneNumberTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phoneNumberTypeDTO);
    }

    /**
     * {@code DELETE  /phone-number-types/:id} : delete the "id" phoneNumberType.
     *
     * @param id the id of the phoneNumberTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phone-number-types/{id}")
    public ResponseEntity<Void> deletePhoneNumberType(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumberType : {}", id);
        phoneNumberTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
