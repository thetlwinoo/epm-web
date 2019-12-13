package com.epmweb.server.web.rest;

import com.epmweb.server.service.AddressesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.AddressesDTO;
import com.epmweb.server.service.dto.AddressesCriteria;
import com.epmweb.server.service.AddressesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Addresses}.
 */
@RestController
@RequestMapping("/api")
public class AddressesResource {

    private final Logger log = LoggerFactory.getLogger(AddressesResource.class);

    private static final String ENTITY_NAME = "addresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressesService addressesService;

    private final AddressesQueryService addressesQueryService;

    public AddressesResource(AddressesService addressesService, AddressesQueryService addressesQueryService) {
        this.addressesService = addressesService;
        this.addressesQueryService = addressesQueryService;
    }

    /**
     * {@code POST  /addresses} : Create a new addresses.
     *
     * @param addressesDTO the addressesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressesDTO, or with status {@code 400 (Bad Request)} if the addresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/addresses")
    public ResponseEntity<AddressesDTO> createAddresses(@Valid @RequestBody AddressesDTO addressesDTO) throws URISyntaxException {
        log.debug("REST request to save Addresses : {}", addressesDTO);
        if (addressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressesDTO result = addressesService.save(addressesDTO);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /addresses} : Updates an existing addresses.
     *
     * @param addressesDTO the addressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressesDTO,
     * or with status {@code 400 (Bad Request)} if the addressesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/addresses")
    public ResponseEntity<AddressesDTO> updateAddresses(@Valid @RequestBody AddressesDTO addressesDTO) throws URISyntaxException {
        log.debug("REST request to update Addresses : {}", addressesDTO);
        if (addressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressesDTO result = addressesService.save(addressesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /addresses} : get all the addresses.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addresses in body.
     */
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressesDTO>> getAllAddresses(AddressesCriteria criteria) {
        log.debug("REST request to get Addresses by criteria: {}", criteria);
        List<AddressesDTO> entityList = addressesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /addresses/count} : count all the addresses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/addresses/count")
    public ResponseEntity<Long> countAddresses(AddressesCriteria criteria) {
        log.debug("REST request to count Addresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(addressesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /addresses/:id} : get the "id" addresses.
     *
     * @param id the id of the addressesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressesDTO> getAddresses(@PathVariable Long id) {
        log.debug("REST request to get Addresses : {}", id);
        Optional<AddressesDTO> addressesDTO = addressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressesDTO);
    }

    /**
     * {@code DELETE  /addresses/:id} : delete the "id" addresses.
     *
     * @param id the id of the addressesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddresses(@PathVariable Long id) {
        log.debug("REST request to delete Addresses : {}", id);
        addressesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
