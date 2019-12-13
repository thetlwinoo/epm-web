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

import com.epmweb.server.domain.CompareProducts;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CompareProductsRepository;
import com.epmweb.server.service.dto.CompareProductsCriteria;
import com.epmweb.server.service.dto.CompareProductsDTO;
import com.epmweb.server.service.mapper.CompareProductsMapper;

/**
 * Service for executing complex queries for {@link CompareProducts} entities in the database.
 * The main input is a {@link CompareProductsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompareProductsDTO} or a {@link Page} of {@link CompareProductsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompareProductsQueryService extends QueryService<CompareProducts> {

    private final Logger log = LoggerFactory.getLogger(CompareProductsQueryService.class);

    private final CompareProductsRepository compareProductsRepository;

    private final CompareProductsMapper compareProductsMapper;

    public CompareProductsQueryService(CompareProductsRepository compareProductsRepository, CompareProductsMapper compareProductsMapper) {
        this.compareProductsRepository = compareProductsRepository;
        this.compareProductsMapper = compareProductsMapper;
    }

    /**
     * Return a {@link List} of {@link CompareProductsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompareProductsDTO> findByCriteria(CompareProductsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompareProducts> specification = createSpecification(criteria);
        return compareProductsMapper.toDto(compareProductsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompareProductsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompareProductsDTO> findByCriteria(CompareProductsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompareProducts> specification = createSpecification(criteria);
        return compareProductsRepository.findAll(specification, page)
            .map(compareProductsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompareProductsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompareProducts> specification = createSpecification(criteria);
        return compareProductsRepository.count(specification);
    }

    /**
     * Function to convert {@link CompareProductsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompareProducts> createSpecification(CompareProductsCriteria criteria) {
        Specification<CompareProducts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompareProducts_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(CompareProducts_.product, JoinType.LEFT).get(Products_.id)));
            }
            if (criteria.getCompareId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompareId(),
                    root -> root.join(CompareProducts_.compare, JoinType.LEFT).get(Compares_.id)));
            }
        }
        return specification;
    }
}
