package com.epmweb.server.web.rest;

import com.epmweb.server.service.PhotosService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.PhotosDTO;
import com.epmweb.server.service.dto.PhotosCriteria;
import com.epmweb.server.service.PhotosQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Photos}.
 */
@RestController
@RequestMapping("/api")
public class PhotosResource {

    private final Logger log = LoggerFactory.getLogger(PhotosResource.class);

    private static final String ENTITY_NAME = "photos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhotosService photosService;

    private final PhotosQueryService photosQueryService;

    public PhotosResource(PhotosService photosService, PhotosQueryService photosQueryService) {
        this.photosService = photosService;
        this.photosQueryService = photosQueryService;
    }

    /**
     * {@code POST  /photos} : Create a new photos.
     *
     * @param photosDTO the photosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new photosDTO, or with status {@code 400 (Bad Request)} if the photos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/photos")
    public ResponseEntity<PhotosDTO> createPhotos(@RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photosDTO);
        if (photosDTO.getId() != null) {
            throw new BadRequestAlertException("A new photos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotosDTO result = photosService.save(photosDTO);
        return ResponseEntity.created(new URI("/api/photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /photos} : Updates an existing photos.
     *
     * @param photosDTO the photosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated photosDTO,
     * or with status {@code 400 (Bad Request)} if the photosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the photosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/photos")
    public ResponseEntity<PhotosDTO> updatePhotos(@RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update Photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = photosService.save(photosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /photos} : get all the photos.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of photos in body.
     */
    @GetMapping("/photos")
    public ResponseEntity<List<PhotosDTO>> getAllPhotos(PhotosCriteria criteria) {
        log.debug("REST request to get Photos by criteria: {}", criteria);
        List<PhotosDTO> entityList = photosQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /photos/count} : count all the photos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/photos/count")
    public ResponseEntity<Long> countPhotos(PhotosCriteria criteria) {
        log.debug("REST request to count Photos by criteria: {}", criteria);
        return ResponseEntity.ok().body(photosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /photos/:id} : get the "id" photos.
     *
     * @param id the id of the photosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the photosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/photos/{id}")
    public ResponseEntity<PhotosDTO> getPhotos(@PathVariable Long id) {
        log.debug("REST request to get Photos : {}", id);
        Optional<PhotosDTO> photosDTO = photosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(photosDTO);
    }

    /**
     * {@code DELETE  /photos/:id} : delete the "id" photos.
     *
     * @param id the id of the photosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable Long id) {
        log.debug("REST request to delete Photos : {}", id);
        photosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
