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

import com.epmweb.server.domain.People;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PeopleRepository;
import com.epmweb.server.service.dto.PeopleCriteria;
import com.epmweb.server.service.dto.PeopleDTO;
import com.epmweb.server.service.mapper.PeopleMapper;

/**
 * Service for executing complex queries for {@link People} entities in the database.
 * The main input is a {@link PeopleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PeopleDTO} or a {@link Page} of {@link PeopleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeopleQueryService extends QueryService<People> {

    private final Logger log = LoggerFactory.getLogger(PeopleQueryService.class);

    private final PeopleRepository peopleRepository;

    private final PeopleMapper peopleMapper;

    public PeopleQueryService(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    /**
     * Return a {@link List} of {@link PeopleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PeopleDTO> findByCriteria(PeopleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<People> specification = createSpecification(criteria);
        return peopleMapper.toDto(peopleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PeopleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeopleDTO> findByCriteria(PeopleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<People> specification = createSpecification(criteria);
        return peopleRepository.findAll(specification, page)
            .map(peopleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeopleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<People> specification = createSpecification(criteria);
        return peopleRepository.count(specification);
    }

    /**
     * Function to convert {@link PeopleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<People> createSpecification(PeopleCriteria criteria) {
        Specification<People> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), People_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), People_.fullName));
            }
            if (criteria.getPreferredName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreferredName(), People_.preferredName));
            }
            if (criteria.getSearchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSearchName(), People_.searchName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), People_.gender));
            }
            if (criteria.getIsPermittedToLogon() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPermittedToLogon(), People_.isPermittedToLogon));
            }
            if (criteria.getLogonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogonName(), People_.logonName));
            }
            if (criteria.getIsExternalLogonProvider() != null) {
                specification = specification.and(buildSpecification(criteria.getIsExternalLogonProvider(), People_.isExternalLogonProvider));
            }
            if (criteria.getIsSystemUser() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSystemUser(), People_.isSystemUser));
            }
            if (criteria.getIsEmployee() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEmployee(), People_.isEmployee));
            }
            if (criteria.getIsSalesPerson() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSalesPerson(), People_.isSalesPerson));
            }
            if (criteria.getIsGuestUser() != null) {
                specification = specification.and(buildSpecification(criteria.getIsGuestUser(), People_.isGuestUser));
            }
            if (criteria.getEmailPromotion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmailPromotion(), People_.emailPromotion));
            }
            if (criteria.getUserPreferences() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserPreferences(), People_.userPreferences));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), People_.phoneNumber));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), People_.emailAddress));
            }
            if (criteria.getPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoto(), People_.photo));
            }
            if (criteria.getCustomFields() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomFields(), People_.customFields));
            }
            if (criteria.getOtherLanguages() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherLanguages(), People_.otherLanguages));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), People_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), People_.validTo));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(People_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartId(),
                    root -> root.join(People_.cart, JoinType.LEFT).get(ShoppingCarts_.id)));
            }
            if (criteria.getWishlistId() != null) {
                specification = specification.and(buildSpecification(criteria.getWishlistId(),
                    root -> root.join(People_.wishlist, JoinType.LEFT).get(Wishlists_.id)));
            }
            if (criteria.getCompareId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompareId(),
                    root -> root.join(People_.compare, JoinType.LEFT).get(Compares_.id)));
            }
        }
        return specification;
    }
}
