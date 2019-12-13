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

import com.epmweb.server.domain.ProductSet;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductSetRepository;
import com.epmweb.server.service.dto.ProductSetCriteria;
import com.epmweb.server.service.dto.ProductSetDTO;
import com.epmweb.server.service.mapper.ProductSetMapper;

/**
 * Service for executing complex queries for {@link ProductSet} entities in the database.
 * The main input is a {@link ProductSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductSetDTO} or a {@link Page} of {@link ProductSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductSetQueryService extends QueryService<ProductSet> {

    private final Logger log = LoggerFactory.getLogger(ProductSetQueryService.class);

    private final ProductSetRepository productSetRepository;

    private final ProductSetMapper productSetMapper;

    public ProductSetQueryService(ProductSetRepository productSetRepository, ProductSetMapper productSetMapper) {
        this.productSetRepository = productSetRepository;
        this.productSetMapper = productSetMapper;
    }

    /**
     * Return a {@link List} of {@link ProductSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductSetDTO> findByCriteria(ProductSetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductSet> specification = createSpecification(criteria);
        return productSetMapper.toDto(productSetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductSetDTO> findByCriteria(ProductSetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductSet> specification = createSpecification(criteria);
        return productSetRepository.findAll(specification, page)
            .map(productSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductSetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductSet> specification = createSpecification(criteria);
        return productSetRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductSet> createSpecification(ProductSetCriteria criteria) {
        Specification<ProductSet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductSet_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductSet_.name));
            }
            if (criteria.getNoOfPerson() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfPerson(), ProductSet_.noOfPerson));
            }
            if (criteria.getIsExclusive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsExclusive(), ProductSet_.isExclusive));
            }
        }
        return specification;
    }
}
