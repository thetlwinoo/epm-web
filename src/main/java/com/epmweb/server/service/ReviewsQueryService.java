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

import com.epmweb.server.domain.Reviews;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ReviewsRepository;
import com.epmweb.server.service.dto.ReviewsCriteria;
import com.epmweb.server.service.dto.ReviewsDTO;
import com.epmweb.server.service.mapper.ReviewsMapper;

/**
 * Service for executing complex queries for {@link Reviews} entities in the database.
 * The main input is a {@link ReviewsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReviewsDTO} or a {@link Page} of {@link ReviewsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReviewsQueryService extends QueryService<Reviews> {

    private final Logger log = LoggerFactory.getLogger(ReviewsQueryService.class);

    private final ReviewsRepository reviewsRepository;

    private final ReviewsMapper reviewsMapper;

    public ReviewsQueryService(ReviewsRepository reviewsRepository, ReviewsMapper reviewsMapper) {
        this.reviewsRepository = reviewsRepository;
        this.reviewsMapper = reviewsMapper;
    }

    /**
     * Return a {@link List} of {@link ReviewsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReviewsDTO> findByCriteria(ReviewsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reviews> specification = createSpecification(criteria);
        return reviewsMapper.toDto(reviewsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReviewsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReviewsDTO> findByCriteria(ReviewsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reviews> specification = createSpecification(criteria);
        return reviewsRepository.findAll(specification, page)
            .map(reviewsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReviewsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reviews> specification = createSpecification(criteria);
        return reviewsRepository.count(specification);
    }

    /**
     * Function to convert {@link ReviewsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reviews> createSpecification(ReviewsCriteria criteria) {
        Specification<Reviews> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reviews_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Reviews_.name));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), Reviews_.emailAddress));
            }
            if (criteria.getReviewDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReviewDate(), Reviews_.reviewDate));
            }
            if (criteria.getOverAllSellerRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverAllSellerRating(), Reviews_.overAllSellerRating));
            }
            if (criteria.getOverAllDeliveryRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverAllDeliveryRating(), Reviews_.overAllDeliveryRating));
            }
            if (criteria.getReviewAsAnonymous() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewAsAnonymous(), Reviews_.reviewAsAnonymous));
            }
            if (criteria.getCompletedReview() != null) {
                specification = specification.and(buildSpecification(criteria.getCompletedReview(), Reviews_.completedReview));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), Reviews_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), Reviews_.lastEditedWhen));
            }
            if (criteria.getReviewLineListId() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewLineListId(),
                    root -> root.join(Reviews_.reviewLineLists, JoinType.LEFT).get(ReviewLines_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Reviews_.order, JoinType.LEFT).get(Orders_.id)));
            }
        }
        return specification;
    }
}
