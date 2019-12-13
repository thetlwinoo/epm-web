package com.epmweb.server.web.rest;

import com.epmweb.server.service.UploadActionTypesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.UploadActionTypesDTO;
import com.epmweb.server.service.dto.UploadActionTypesCriteria;
import com.epmweb.server.service.UploadActionTypesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epmweb.server.domain.UploadActionTypes}.
 */
@RestController
@RequestMapping("/api")
public class UploadActionTypesResource {

    private final Logger log = LoggerFactory.getLogger(UploadActionTypesResource.class);

    private static final String ENTITY_NAME = "uploadActionTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UploadActionTypesService uploadActionTypesService;

    private final UploadActionTypesQueryService uploadActionTypesQueryService;

    public UploadActionTypesResource(UploadActionTypesService uploadActionTypesService, UploadActionTypesQueryService uploadActionTypesQueryService) {
        this.uploadActionTypesService = uploadActionTypesService;
        this.uploadActionTypesQueryService = uploadActionTypesQueryService;
    }

    /**
     * {@code POST  /upload-action-types} : Create a new uploadActionTypes.
     *
     * @param uploadActionTypesDTO the uploadActionTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uploadActionTypesDTO, or with status {@code 400 (Bad Request)} if the uploadActionTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upload-action-types")
    public ResponseEntity<UploadActionTypesDTO> createUploadActionTypes(@RequestBody UploadActionTypesDTO uploadActionTypesDTO) throws URISyntaxException {
        log.debug("REST request to save UploadActionTypes : {}", uploadActionTypesDTO);
        if (uploadActionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploadActionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadActionTypesDTO result = uploadActionTypesService.save(uploadActionTypesDTO);
        return ResponseEntity.created(new URI("/api/upload-action-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upload-action-types} : Updates an existing uploadActionTypes.
     *
     * @param uploadActionTypesDTO the uploadActionTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadActionTypesDTO,
     * or with status {@code 400 (Bad Request)} if the uploadActionTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadActionTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upload-action-types")
    public ResponseEntity<UploadActionTypesDTO> updateUploadActionTypes(@RequestBody UploadActionTypesDTO uploadActionTypesDTO) throws URISyntaxException {
        log.debug("REST request to update UploadActionTypes : {}", uploadActionTypesDTO);
        if (uploadActionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadActionTypesDTO result = uploadActionTypesService.save(uploadActionTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uploadActionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /upload-action-types} : get all the uploadActionTypes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uploadActionTypes in body.
     */
    @GetMapping("/upload-action-types")
    public ResponseEntity<List<UploadActionTypesDTO>> getAllUploadActionTypes(UploadActionTypesCriteria criteria) {
        log.debug("REST request to get UploadActionTypes by criteria: {}", criteria);
        List<UploadActionTypesDTO> entityList = uploadActionTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /upload-action-types/count} : count all the uploadActionTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/upload-action-types/count")
    public ResponseEntity<Long> countUploadActionTypes(UploadActionTypesCriteria criteria) {
        log.debug("REST request to count UploadActionTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(uploadActionTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /upload-action-types/:id} : get the "id" uploadActionTypes.
     *
     * @param id the id of the uploadActionTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uploadActionTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/upload-action-types/{id}")
    public ResponseEntity<UploadActionTypesDTO> getUploadActionTypes(@PathVariable Long id) {
        log.debug("REST request to get UploadActionTypes : {}", id);
        Optional<UploadActionTypesDTO> uploadActionTypesDTO = uploadActionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadActionTypesDTO);
    }

    /**
     * {@code DELETE  /upload-action-types/:id} : delete the "id" uploadActionTypes.
     *
     * @param id the id of the uploadActionTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/upload-action-types/{id}")
    public ResponseEntity<Void> deleteUploadActionTypes(@PathVariable Long id) {
        log.debug("REST request to delete UploadActionTypes : {}", id);
        uploadActionTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
