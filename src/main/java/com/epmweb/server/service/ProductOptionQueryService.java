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

import com.epmweb.server.domain.ProductOption;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductOptionRepository;
import com.epmweb.server.service.dto.ProductOptionCriteria;
import com.epmweb.server.service.dto.ProductOptionDTO;
import com.epmweb.server.service.mapper.ProductOptionMapper;

/**
 * Service for executing complex queries for {@link ProductOption} entities in the database.
 * The main input is a {@link ProductOptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOptionDTO} or a {@link Page} of {@link ProductOptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOptionQueryService extends QueryService<ProductOption> {

    private final Logger log = LoggerFactory.getLogger(ProductOptionQueryService.class);

    private final ProductOptionRepository productOptionRepository;

    private final ProductOptionMapper productOptionMapper;

    public ProductOptionQueryService(ProductOptionRepository productOptionRepository, ProductOptionMapper productOptionMapper) {
        this.productOptionRepository = productOptionRepository;
        this.productOptionMapper = productOptionMapper;
    }

    /**
     * Return a {@link List} of {@link ProductOptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionDTO> findByCriteria(ProductOptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOption> specification = createSpecification(criteria);
        return productOptionMapper.toDto(productOptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductOptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionDTO> findByCriteria(ProductOptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOption> specification = createSpecification(criteria);
        return productOptionRepository.findAll(specification, page)
            .map(productOptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOption> specification = createSpecification(criteria);
        return productOptionRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductOptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductOption> createSpecification(ProductOptionCriteria criteria) {
        Specification<ProductOption> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductOption_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ProductOption_.value));
            }
            if (criteria.getProductOptionSetId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOptionSetId(),
                    root -> root.join(ProductOption_.productOptionSet, JoinType.LEFT).get(ProductOptionSet_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(ProductOption_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
        }
        return specification;
    }
}
