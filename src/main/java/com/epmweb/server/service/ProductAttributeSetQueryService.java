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

import com.epmweb.server.domain.ProductAttributeSet;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductAttributeSetRepository;
import com.epmweb.server.service.dto.ProductAttributeSetCriteria;
import com.epmweb.server.service.dto.ProductAttributeSetDTO;
import com.epmweb.server.service.mapper.ProductAttributeSetMapper;

/**
 * Service for executing complex queries for {@link ProductAttributeSet} entities in the database.
 * The main input is a {@link ProductAttributeSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductAttributeSetDTO} or a {@link Page} of {@link ProductAttributeSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductAttributeSetQueryService extends QueryService<ProductAttributeSet> {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeSetQueryService.class);

    private final ProductAttributeSetRepository productAttributeSetRepository;

    private final ProductAttributeSetMapper productAttributeSetMapper;

    public ProductAttributeSetQueryService(ProductAttributeSetRepository productAttributeSetRepository, ProductAttributeSetMapper productAttributeSetMapper) {
        this.productAttributeSetRepository = productAttributeSetRepository;
        this.productAttributeSetMapper = productAttributeSetMapper;
    }

    /**
     * Return a {@link List} of {@link ProductAttributeSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductAttributeSetDTO> findByCriteria(ProductAttributeSetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductAttributeSet> specification = createSpecification(criteria);
        return productAttributeSetMapper.toDto(productAttributeSetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductAttributeSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductAttributeSetDTO> findByCriteria(ProductAttributeSetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductAttributeSet> specification = createSpecification(criteria);
        return productAttributeSetRepository.findAll(specification, page)
            .map(productAttributeSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductAttributeSetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductAttributeSet> specification = createSpecification(criteria);
        return productAttributeSetRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductAttributeSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductAttributeSet> createSpecification(ProductAttributeSetCriteria criteria) {
        Specification<ProductAttributeSet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductAttributeSet_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductAttributeSet_.name));
            }
            if (criteria.getProductOptionSetId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOptionSetId(),
                    root -> root.join(ProductAttributeSet_.productOptionSet, JoinType.LEFT).get(ProductOptionSet_.id)));
            }
        }
        return specification;
    }
}
