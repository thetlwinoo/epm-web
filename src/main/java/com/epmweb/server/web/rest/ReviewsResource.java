package com.epmweb.server.web.rest;

import com.epmweb.server.service.ReviewsService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.ReviewsDTO;
import com.epmweb.server.service.dto.ReviewsCriteria;
import com.epmweb.server.service.ReviewsQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.epmweb.server.domain.Reviews}.
 */
@RestController
@RequestMapping("/api")
public class ReviewsResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsResource.class);

    private static final String ENTITY_NAME = "reviews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewsService reviewsService;

    private final ReviewsQueryService reviewsQueryService;

    public ReviewsResource(ReviewsService reviewsService, ReviewsQueryService reviewsQueryService) {
        this.reviewsService = reviewsService;
        this.reviewsQueryService = reviewsQueryService;
    }

    /**
     * {@code POST  /reviews} : Create a new reviews.
     *
     * @param reviewsDTO the reviewsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviewsDTO, or with status {@code 400 (Bad Request)} if the reviews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reviews")
    public ResponseEntity<ReviewsDTO> createReviews(@Valid @RequestBody ReviewsDTO reviewsDTO) throws URISyntaxException {
        log.debug("REST request to save Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewsDTO result = reviewsService.save(reviewsDTO);
        return ResponseEntity.created(new URI("/api/reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reviews} : Updates an existing reviews.
     *
     * @param reviewsDTO the reviewsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviewsDTO,
     * or with status {@code 400 (Bad Request)} if the reviewsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviewsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reviews")
    public ResponseEntity<ReviewsDTO> updateReviews(@Valid @RequestBody ReviewsDTO reviewsDTO) throws URISyntaxException {
        log.debug("REST request to update Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewsDTO result = reviewsService.save(reviewsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reviewsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reviews} : get all the reviews.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviews in body.
     */
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewsDTO>> getAllReviews(ReviewsCriteria criteria) {
        log.debug("REST request to get Reviews by criteria: {}", criteria);
        List<ReviewsDTO> entityList = reviewsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /reviews/count} : count all the reviews.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/reviews/count")
    public ResponseEntity<Long> countReviews(ReviewsCriteria criteria) {
        log.debug("REST request to count Reviews by criteria: {}", criteria);
        return ResponseEntity.ok().body(reviewsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reviews/:id} : get the "id" reviews.
     *
     * @param id the id of the reviewsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviewsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewsDTO> getReviews(@PathVariable Long id) {
        log.debug("REST request to get Reviews : {}", id);
        Optional<ReviewsDTO> reviewsDTO = reviewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewsDTO);
    }

    /**
     * {@code DELETE  /reviews/:id} : delete the "id" reviews.
     *
     * @param id the id of the reviewsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReviews(@PathVariable Long id) {
        log.debug("REST request to delete Reviews : {}", id);
        reviewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
