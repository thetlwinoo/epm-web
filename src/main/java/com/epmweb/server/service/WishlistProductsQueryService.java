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

import com.epmweb.server.domain.WishlistProducts;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.WishlistProductsRepository;
import com.epmweb.server.service.dto.WishlistProductsCriteria;
import com.epmweb.server.service.dto.WishlistProductsDTO;
import com.epmweb.server.service.mapper.WishlistProductsMapper;

/**
 * Service for executing complex queries for {@link WishlistProducts} entities in the database.
 * The main input is a {@link WishlistProductsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WishlistProductsDTO} or a {@link Page} of {@link WishlistProductsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WishlistProductsQueryService extends QueryService<WishlistProducts> {

    private final Logger log = LoggerFactory.getLogger(WishlistProductsQueryService.class);

    private final WishlistProductsRepository wishlistProductsRepository;

    private final WishlistProductsMapper wishlistProductsMapper;

    public WishlistProductsQueryService(WishlistProductsRepository wishlistProductsRepository, WishlistProductsMapper wishlistProductsMapper) {
        this.wishlistProductsRepository = wishlistProductsRepository;
        this.wishlistProductsMapper = wishlistProductsMapper;
    }

    /**
     * Return a {@link List} of {@link WishlistProductsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WishlistProductsDTO> findByCriteria(WishlistProductsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WishlistProducts> specification = createSpecification(criteria);
        return wishlistProductsMapper.toDto(wishlistProductsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WishlistProductsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WishlistProductsDTO> findByCriteria(WishlistProductsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WishlistProducts> specification = createSpecification(criteria);
        return wishlistProductsRepository.findAll(specification, page)
            .map(wishlistProductsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WishlistProductsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WishlistProducts> specification = createSpecification(criteria);
        return wishlistProductsRepository.count(specification);
    }

    /**
     * Function to convert {@link WishlistProductsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WishlistProducts> createSpecification(WishlistProductsCriteria criteria) {
        Specification<WishlistProducts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WishlistProducts_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(WishlistProducts_.product, JoinType.LEFT).get(Products_.id)));
            }
            if (criteria.getWishlistId() != null) {
                specification = specification.and(buildSpecification(criteria.getWishlistId(),
                    root -> root.join(WishlistProducts_.wishlist, JoinType.LEFT).get(Wishlists_.id)));
            }
        }
        return specification;
    }
}
