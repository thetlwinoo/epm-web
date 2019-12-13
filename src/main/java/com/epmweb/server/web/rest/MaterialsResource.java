package com.epmweb.server.web.rest;

import com.epmweb.server.service.MaterialsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.MaterialsDTO;
import com.epmweb.server.service.dto.MaterialsCriteria;
import com.epmweb.server.service.MaterialsQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Materials}.
 */
@RestController
@RequestMapping("/api")
public class MaterialsResource {

    private final Logger log = LoggerFactory.getLogger(MaterialsResource.class);

    private static final String ENTITY_NAME = "materials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialsService materialsService;

    private final MaterialsQueryService materialsQueryService;

    public MaterialsResource(MaterialsService materialsService, MaterialsQueryService materialsQueryService) {
        this.materialsService = materialsService;
        this.materialsQueryService = materialsQueryService;
    }

    /**
     * {@code POST  /materials} : Create a new materials.
     *
     * @param materialsDTO the materialsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialsDTO, or with status {@code 400 (Bad Request)} if the materials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materials")
    public ResponseEntity<MaterialsDTO> createMaterials(@Valid @RequestBody MaterialsDTO materialsDTO) throws URISyntaxException {
        log.debug("REST request to save Materials : {}", materialsDTO);
        if (materialsDTO.getId() != null) {
            throw new BadRequestAlertException("A new materials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialsDTO result = materialsService.save(materialsDTO);
        return ResponseEntity.created(new URI("/api/materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materials} : Updates an existing materials.
     *
     * @param materialsDTO the materialsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialsDTO,
     * or with status {@code 400 (Bad Request)} if the materialsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materials")
    public ResponseEntity<MaterialsDTO> updateMaterials(@Valid @RequestBody MaterialsDTO materialsDTO) throws URISyntaxException {
        log.debug("REST request to update Materials : {}", materialsDTO);
        if (materialsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialsDTO result = materialsService.save(materialsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /materials} : get all the materials.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materials in body.
     */
    @GetMapping("/materials")
    public ResponseEntity<List<MaterialsDTO>> getAllMaterials(MaterialsCriteria criteria) {
        log.debug("REST request to get Materials by criteria: {}", criteria);
        List<MaterialsDTO> entityList = materialsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /materials/count} : count all the materials.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/materials/count")
    public ResponseEntity<Long> countMaterials(MaterialsCriteria criteria) {
        log.debug("REST request to count Materials by criteria: {}", criteria);
        return ResponseEntity.ok().body(materialsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /materials/:id} : get the "id" materials.
     *
     * @param id the id of the materialsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materials/{id}")
    public ResponseEntity<MaterialsDTO> getMaterials(@PathVariable Long id) {
        log.debug("REST request to get Materials : {}", id);
        Optional<MaterialsDTO> materialsDTO = materialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialsDTO);
    }

    /**
     * {@code DELETE  /materials/:id} : delete the "id" materials.
     *
     * @param id the id of the materialsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void> deleteMaterials(@PathVariable Long id) {
        log.debug("REST request to delete Materials : {}", id);
        materialsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
