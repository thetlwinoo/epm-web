package com.epmweb.server.web.rest;

import com.epmweb.server.service.ReviewsExtendService;
import com.epmweb.server.service.dto.ReviewsDTO;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

/**
 * ReviewsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ReviewsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsExtendResource.class);
    private final ReviewsExtendService reviewsExtendService;
    private static final String ENTITY_NAME = "reviews-extend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ReviewsExtendResource(ReviewsExtendService reviewsExtendService) {
        this.reviewsExtendService = reviewsExtendService;
    }

    @RequestMapping(value = "/reviews-extend/save", method = RequestMethod.POST, params = "orderId")
    public ResponseEntity<ReviewsDTO> createReviews(@RequestBody ReviewsDTO reviewsDTO, @RequestParam("orderId") Long orderId, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewsDTO result = reviewsExtendService.save(principal, reviewsDTO, orderId);
        return ResponseEntity.created(new URI("/api/reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/reviews-extend/completed", method = RequestMethod.POST, params = "orderId")
    public ResponseEntity<ReviewsDTO> completedReviews(@RequestParam("orderId") Long orderId) {
        ReviewsDTO result = reviewsExtendService.completedReview(orderId);
        return ResponseEntity.ok()
            .body(result);
    }

    @RequestMapping(value = "/reviews-extend/save", method = RequestMethod.PUT, params = "orderId")
    public ResponseEntity<ReviewsDTO> updateReviews(@RequestBody ReviewsDTO reviewsDTO, @RequestParam("orderId") Long orderId, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Reviews : {}", reviewsDTO);
        if (reviewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewsDTO result = reviewsExtendService.save(principal, reviewsDTO, orderId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reviewsDTO.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/reviews-extend/ordered-products", method = RequestMethod.GET)
    public ResponseEntity getAllOrderedProducts(Principal principal) {
        List returnList = reviewsExtendService.findAllOrderedProducts(principal);
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/reviews-extend/reviewed", method = RequestMethod.GET, params = "stockItemId")
    public ResponseEntity getReviewLinesByProductId(@RequestParam("stockItemId") Long stockItemId) {
        List returnList = reviewsExtendService.findReviewLinesByStockItemId(stockItemId);
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }
}
