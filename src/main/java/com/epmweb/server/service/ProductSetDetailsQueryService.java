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

import com.epmweb.server.domain.ProductSetDetails;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductSetDetailsRepository;
import com.epmweb.server.service.dto.ProductSetDetailsCriteria;
import com.epmweb.server.service.dto.ProductSetDetailsDTO;
import com.epmweb.server.service.mapper.ProductSetDetailsMapper;

/**
 * Service for executing complex queries for {@link ProductSetDetails} entities in the database.
 * The main input is a {@link ProductSetDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductSetDetailsDTO} or a {@link Page} of {@link ProductSetDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductSetDetailsQueryService extends QueryService<ProductSetDetails> {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailsQueryService.class);

    private final ProductSetDetailsRepository productSetDetailsRepository;

    private final ProductSetDetailsMapper productSetDetailsMapper;

    public ProductSetDetailsQueryService(ProductSetDetailsRepository productSetDetailsRepository, ProductSetDetailsMapper productSetDetailsMapper) {
        this.productSetDetailsRepository = productSetDetailsRepository;
        this.productSetDetailsMapper = productSetDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link ProductSetDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductSetDetailsDTO> findByCriteria(ProductSetDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductSetDetails> specification = createSpecification(criteria);
        return productSetDetailsMapper.toDto(productSetDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductSetDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductSetDetailsDTO> findByCriteria(ProductSetDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductSetDetails> specification = createSpecification(criteria);
        return productSetDetailsRepository.findAll(specification, page)
            .map(productSetDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductSetDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductSetDetails> specification = createSpecification(criteria);
        return productSetDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductSetDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductSetDetails> createSpecification(ProductSetDetailsCriteria criteria) {
        Specification<ProductSetDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductSetDetails_.id));
            }
            if (criteria.getSubGroupNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubGroupNo(), ProductSetDetails_.subGroupNo));
            }
            if (criteria.getSubGroupMinCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubGroupMinCount(), ProductSetDetails_.subGroupMinCount));
            }
            if (criteria.getSubGroupMinTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubGroupMinTotal(), ProductSetDetails_.subGroupMinTotal));
            }
            if (criteria.getMinCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinCount(), ProductSetDetails_.minCount));
            }
            if (criteria.getMaxCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxCount(), ProductSetDetails_.maxCount));
            }
            if (criteria.getIsOptional() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOptional(), ProductSetDetails_.isOptional));
            }
        }
        return specification;
    }
}
