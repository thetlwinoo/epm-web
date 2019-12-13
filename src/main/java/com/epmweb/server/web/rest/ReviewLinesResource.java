package com.epmweb.server.web.rest;

import com.epmweb.server.service.ReviewLinesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ReviewLinesDTO;
import com.epmweb.server.service.dto.ReviewLinesCriteria;
import com.epmweb.server.service.ReviewLinesQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.epmweb.server.domain.ReviewLines}.
 */
@RestController
@RequestMapping("/api")
public class ReviewLinesResource {

    private final Logger log = LoggerFactory.getLogger(ReviewLinesResource.class);

    private static final String ENTITY_NAME = "reviewLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewLinesService reviewLinesService;

    private final ReviewLinesQueryService reviewLinesQueryService;

    public ReviewLinesResource(ReviewLinesService reviewLinesService, ReviewLinesQueryService reviewLinesQueryService) {
        this.reviewLinesService = reviewLinesService;
        this.reviewLinesQueryService = reviewLinesQueryService;
    }

    /**
     * {@code POST  /review-lines} : Create a new reviewLines.
     *
     * @param reviewLinesDTO the reviewLinesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviewLinesDTO, or with status {@code 400 (Bad Request)} if the reviewLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/review-lines")
    public ResponseEntity<ReviewLinesDTO> createReviewLines(@RequestBody ReviewLinesDTO reviewLinesDTO) throws URISyntaxException {
        log.debug("REST request to save ReviewLines : {}", reviewLinesDTO);
        if (reviewLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviewLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewLinesDTO result = reviewLinesService.save(reviewLinesDTO);
        return ResponseEntity.created(new URI("/api/review-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /review-lines} : Updates an existing reviewLines.
     *
     * @param reviewLinesDTO the reviewLinesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviewLinesDTO,
     * or with status {@code 400 (Bad Request)} if the reviewLinesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviewLinesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/review-lines")
    public ResponseEntity<ReviewLinesDTO> updateReviewLines(@RequestBody ReviewLinesDTO reviewLinesDTO) throws URISyntaxException {
        log.debug("REST request to update ReviewLines : {}", reviewLinesDTO);
        if (reviewLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewLinesDTO result = reviewLinesService.save(reviewLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reviewLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /review-lines} : get all the reviewLines.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviewLines in body.
     */
    @GetMapping("/review-lines")
    public ResponseEntity<List<ReviewLinesDTO>> getAllReviewLines(ReviewLinesCriteria criteria) {
        log.debug("REST request to get ReviewLines by criteria: {}", criteria);
        List<ReviewLinesDTO> entityList = reviewLinesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /review-lines/count} : count all the reviewLines.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/review-lines/count")
    public ResponseEntity<Long> countReviewLines(ReviewLinesCriteria criteria) {
        log.debug("REST request to count ReviewLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(reviewLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /review-lines/:id} : get the "id" reviewLines.
     *
     * @param id the id of the reviewLinesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviewLinesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/review-lines/{id}")
    public ResponseEntity<ReviewLinesDTO> getReviewLines(@PathVariable Long id) {
        log.debug("REST request to get ReviewLines : {}", id);
        Optional<ReviewLinesDTO> reviewLinesDTO = reviewLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewLinesDTO);
    }

    /**
     * {@code DELETE  /review-lines/:id} : delete the "id" reviewLines.
     *
     * @param id the id of the reviewLinesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/review-lines/{id}")
    public ResponseEntity<Void> deleteReviewLines(@PathVariable Long id) {
        log.debug("REST request to delete ReviewLines : {}", id);
        reviewLinesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
