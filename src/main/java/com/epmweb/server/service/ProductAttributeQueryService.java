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

import com.epmweb.server.domain.ProductAttribute;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductAttributeRepository;
import com.epmweb.server.service.dto.ProductAttributeCriteria;
import com.epmweb.server.service.dto.ProductAttributeDTO;
import com.epmweb.server.service.mapper.ProductAttributeMapper;

/**
 * Service for executing complex queries for {@link ProductAttribute} entities in the database.
 * The main input is a {@link ProductAttributeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductAttributeDTO} or a {@link Page} of {@link ProductAttributeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductAttributeQueryService extends QueryService<ProductAttribute> {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeQueryService.class);

    private final ProductAttributeRepository productAttributeRepository;

    private final ProductAttributeMapper productAttributeMapper;

    public ProductAttributeQueryService(ProductAttributeRepository productAttributeRepository, ProductAttributeMapper productAttributeMapper) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeMapper = productAttributeMapper;
    }

    /**
     * Return a {@link List} of {@link ProductAttributeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductAttributeDTO> findByCriteria(ProductAttributeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductAttribute> specification = createSpecification(criteria);
        return productAttributeMapper.toDto(productAttributeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductAttributeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductAttributeDTO> findByCriteria(ProductAttributeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductAttribute> specification = createSpecification(criteria);
        return productAttributeRepository.findAll(specification, page)
            .map(productAttributeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductAttributeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductAttribute> specification = createSpecification(criteria);
        return productAttributeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductAttributeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductAttribute> createSpecification(ProductAttributeCriteria criteria) {
        Specification<ProductAttribute> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductAttribute_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ProductAttribute_.value));
            }
            if (criteria.getProductAttributeSetId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductAttributeSetId(),
                    root -> root.join(ProductAttribute_.productAttributeSet, JoinType.LEFT).get(ProductAttributeSet_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(ProductAttribute_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
        }
        return specification;
    }
}
