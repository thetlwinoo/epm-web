package com.epmweb.server.web.rest;

import com.epmweb.server.service.PackageTypesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PackageTypesDTO;
import com.epmweb.server.service.dto.PackageTypesCriteria;
import com.epmweb.server.service.PackageTypesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.PackageTypes}.
 */
@RestController
@RequestMapping("/api")
public class PackageTypesResource {

    private final Logger log = LoggerFactory.getLogger(PackageTypesResource.class);

    private static final String ENTITY_NAME = "packageTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackageTypesService packageTypesService;

    private final PackageTypesQueryService packageTypesQueryService;

    public PackageTypesResource(PackageTypesService packageTypesService, PackageTypesQueryService packageTypesQueryService) {
        this.packageTypesService = packageTypesService;
        this.packageTypesQueryService = packageTypesQueryService;
    }

    /**
     * {@code POST  /package-types} : Create a new packageTypes.
     *
     * @param packageTypesDTO the packageTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packageTypesDTO, or with status {@code 400 (Bad Request)} if the packageTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/package-types")
    public ResponseEntity<PackageTypesDTO> createPackageTypes(@Valid @RequestBody PackageTypesDTO packageTypesDTO) throws URISyntaxException {
        log.debug("REST request to save PackageTypes : {}", packageTypesDTO);
        if (packageTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new packageTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageTypesDTO result = packageTypesService.save(packageTypesDTO);
        return ResponseEntity.created(new URI("/api/package-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /package-types} : Updates an existing packageTypes.
     *
     * @param packageTypesDTO the packageTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packageTypesDTO,
     * or with status {@code 400 (Bad Request)} if the packageTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packageTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/package-types")
    public ResponseEntity<PackageTypesDTO> updatePackageTypes(@Valid @RequestBody PackageTypesDTO packageTypesDTO) throws URISyntaxException {
        log.debug("REST request to update PackageTypes : {}", packageTypesDTO);
        if (packageTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PackageTypesDTO result = packageTypesService.save(packageTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packageTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /package-types} : get all the packageTypes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packageTypes in body.
     */
    @GetMapping("/package-types")
    public ResponseEntity<List<PackageTypesDTO>> getAllPackageTypes(PackageTypesCriteria criteria) {
        log.debug("REST request to get PackageTypes by criteria: {}", criteria);
        List<PackageTypesDTO> entityList = packageTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /package-types/count} : count all the packageTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/package-types/count")
    public ResponseEntity<Long> countPackageTypes(PackageTypesCriteria criteria) {
        log.debug("REST request to count PackageTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(packageTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /package-types/:id} : get the "id" packageTypes.
     *
     * @param id the id of the packageTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packageTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/package-types/{id}")
    public ResponseEntity<PackageTypesDTO> getPackageTypes(@PathVariable Long id) {
        log.debug("REST request to get PackageTypes : {}", id);
        Optional<PackageTypesDTO> packageTypesDTO = packageTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packageTypesDTO);
    }

    /**
     * {@code DELETE  /package-types/:id} : delete the "id" packageTypes.
     *
     * @param id the id of the packageTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/package-types/{id}")
    public ResponseEntity<Void> deletePackageTypes(@PathVariable Long id) {
        log.debug("REST request to delete PackageTypes : {}", id);
        packageTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
