package com.epmweb.server.web.rest;

import com.epmweb.server.service.AddressTypesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.AddressTypesDTO;
import com.epmweb.server.service.dto.AddressTypesCriteria;
import com.epmweb.server.service.AddressTypesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.AddressTypes}.
 */
@RestController
@RequestMapping("/api")
public class AddressTypesResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypesResource.class);

    private static final String ENTITY_NAME = "addressTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressTypesService addressTypesService;

    private final AddressTypesQueryService addressTypesQueryService;

    public AddressTypesResource(AddressTypesService addressTypesService, AddressTypesQueryService addressTypesQueryService) {
        this.addressTypesService = addressTypesService;
        this.addressTypesQueryService = addressTypesQueryService;
    }

    /**
     * {@code POST  /address-types} : Create a new addressTypes.
     *
     * @param addressTypesDTO the addressTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressTypesDTO, or with status {@code 400 (Bad Request)} if the addressTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-types")
    public ResponseEntity<AddressTypesDTO> createAddressTypes(@Valid @RequestBody AddressTypesDTO addressTypesDTO) throws URISyntaxException {
        log.debug("REST request to save AddressTypes : {}", addressTypesDTO);
        if (addressTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressTypesDTO result = addressTypesService.save(addressTypesDTO);
        return ResponseEntity.created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /address-types} : Updates an existing addressTypes.
     *
     * @param addressTypesDTO the addressTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressTypesDTO,
     * or with status {@code 400 (Bad Request)} if the addressTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-types")
    public ResponseEntity<AddressTypesDTO> updateAddressTypes(@Valid @RequestBody AddressTypesDTO addressTypesDTO) throws URISyntaxException {
        log.debug("REST request to update AddressTypes : {}", addressTypesDTO);
        if (addressTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressTypesDTO result = addressTypesService.save(addressTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /address-types} : get all the addressTypes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressTypes in body.
     */
    @GetMapping("/address-types")
    public ResponseEntity<List<AddressTypesDTO>> getAllAddressTypes(AddressTypesCriteria criteria) {
        log.debug("REST request to get AddressTypes by criteria: {}", criteria);
        List<AddressTypesDTO> entityList = addressTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /address-types/count} : count all the addressTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/address-types/count")
    public ResponseEntity<Long> countAddressTypes(AddressTypesCriteria criteria) {
        log.debug("REST request to count AddressTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(addressTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /address-types/:id} : get the "id" addressTypes.
     *
     * @param id the id of the addressTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-types/{id}")
    public ResponseEntity<AddressTypesDTO> getAddressTypes(@PathVariable Long id) {
        log.debug("REST request to get AddressTypes : {}", id);
        Optional<AddressTypesDTO> addressTypesDTO = addressTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressTypesDTO);
    }

    /**
     * {@code DELETE  /address-types/:id} : delete the "id" addressTypes.
     *
     * @param id the id of the addressTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-types/{id}")
    public ResponseEntity<Void> deleteAddressTypes(@PathVariable Long id) {
        log.debug("REST request to delete AddressTypes : {}", id);
        addressTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
