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

import com.epmweb.server.domain.Wishlists;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.WishlistsRepository;
import com.epmweb.server.service.dto.WishlistsCriteria;
import com.epmweb.server.service.dto.WishlistsDTO;
import com.epmweb.server.service.mapper.WishlistsMapper;

/**
 * Service for executing complex queries for {@link Wishlists} entities in the database.
 * The main input is a {@link WishlistsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WishlistsDTO} or a {@link Page} of {@link WishlistsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WishlistsQueryService extends QueryService<Wishlists> {

    private final Logger log = LoggerFactory.getLogger(WishlistsQueryService.class);

    private final WishlistsRepository wishlistsRepository;

    private final WishlistsMapper wishlistsMapper;

    public WishlistsQueryService(WishlistsRepository wishlistsRepository, WishlistsMapper wishlistsMapper) {
        this.wishlistsRepository = wishlistsRepository;
        this.wishlistsMapper = wishlistsMapper;
    }

    /**
     * Return a {@link List} of {@link WishlistsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WishlistsDTO> findByCriteria(WishlistsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Wishlists> specification = createSpecification(criteria);
        return wishlistsMapper.toDto(wishlistsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WishlistsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WishlistsDTO> findByCriteria(WishlistsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Wishlists> specification = createSpecification(criteria);
        return wishlistsRepository.findAll(specification, page)
            .map(wishlistsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WishlistsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Wishlists> specification = createSpecification(criteria);
        return wishlistsRepository.count(specification);
    }

    /**
     * Function to convert {@link WishlistsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Wishlists> createSpecification(WishlistsCriteria criteria) {
        Specification<Wishlists> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Wishlists_.id));
            }
            if (criteria.getWishlistUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getWishlistUserId(),
                    root -> root.join(Wishlists_.wishlistUser, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getWishlistListId() != null) {
                specification = specification.and(buildSpecification(criteria.getWishlistListId(),
                    root -> root.join(Wishlists_.wishlistLists, JoinType.LEFT).get(WishlistProducts_.id)));
            }
        }
        return specification;
    }
}
