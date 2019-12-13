package com.epmweb.server.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.epmweb.server.domain.ReviewLines;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ReviewLinesRepository;
import com.epmweb.server.service.dto.ReviewLinesCriteria;
import com.epmweb.server.service.dto.ReviewLinesDTO;
import com.epmweb.server.service.mapper.ReviewLinesMapper;

/**
 * Service for executing complex queries for {@link ReviewLines} entities in the database.
 * The main input is a {@link ReviewLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReviewLinesDTO} or a {@link Page} of {@link ReviewLinesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReviewLinesQueryService extends QueryService<ReviewLines> {

    private final Logger log = LoggerFactory.getLogger(ReviewLinesQueryService.class);

    private final ReviewLinesRepository reviewLinesRepository;

    private final ReviewLinesMapper reviewLinesMapper;

    public ReviewLinesQueryService(ReviewLinesRepository reviewLinesRepository, ReviewLinesMapper reviewLinesMapper) {
        this.reviewLinesRepository = reviewLinesRepository;
        this.reviewLinesMapper = reviewLinesMapper;
    }

    /**
     * Return a {@link List} of {@link ReviewLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReviewLinesDTO> findByCriteria(ReviewLinesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReviewLines> specification = createSpecification(criteria);
        return reviewLinesMapper.toDto(reviewLinesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReviewLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReviewLinesDTO> findByCriteria(ReviewLinesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReviewLines> specification = createSpecification(criteria);
        return reviewLinesRepository.findAll(specification, page)
            .map(reviewLinesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReviewLinesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReviewLines> specification = createSpecification(criteria);
        return reviewLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link ReviewLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReviewLines> createSpecification(ReviewLinesCriteria criteria) {
        Specification<ReviewLines> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReviewLines_.id));
            }
            if (criteria.getProductRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductRating(), ReviewLines_.productRating));
            }
            if (criteria.getSellerRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellerRating(), ReviewLines_.sellerRating));
            }
            if (criteria.getDeliveryRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryRating(), ReviewLines_.deliveryRating));
            }
            if (criteria.getThumbnailUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailUrl(), ReviewLines_.thumbnailUrl));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), ReviewLines_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), ReviewLines_.lastEditedWhen));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(ReviewLines_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getReviewId() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewId(),
                    root -> root.join(ReviewLines_.review, JoinType.LEFT).get(Reviews_.id)));
            }
        }
        return specification;
    }
}
