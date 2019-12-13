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

import com.epmweb.server.domain.ProductChoice;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductChoiceRepository;
import com.epmweb.server.service.dto.ProductChoiceCriteria;
import com.epmweb.server.service.dto.ProductChoiceDTO;
import com.epmweb.server.service.mapper.ProductChoiceMapper;

/**
 * Service for executing complex queries for {@link ProductChoice} entities in the database.
 * The main input is a {@link ProductChoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductChoiceDTO} or a {@link Page} of {@link ProductChoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductChoiceQueryService extends QueryService<ProductChoice> {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceQueryService.class);

    private final ProductChoiceRepository productChoiceRepository;

    private final ProductChoiceMapper productChoiceMapper;

    public ProductChoiceQueryService(ProductChoiceRepository productChoiceRepository, ProductChoiceMapper productChoiceMapper) {
        this.productChoiceRepository = productChoiceRepository;
        this.productChoiceMapper = productChoiceMapper;
    }

    /**
     * Return a {@link List} of {@link ProductChoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductChoiceDTO> findByCriteria(ProductChoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductChoice> specification = createSpecification(criteria);
        return productChoiceMapper.toDto(productChoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductChoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductChoiceDTO> findByCriteria(ProductChoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductChoice> specification = createSpecification(criteria);
        return productChoiceRepository.findAll(specification, page)
            .map(productChoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductChoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductChoice> specification = createSpecification(criteria);
        return productChoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductChoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductChoice> createSpecification(ProductChoiceCriteria criteria) {
        Specification<ProductChoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductChoice_.id));
            }
            if (criteria.getIsMultiply() != null) {
                specification = specification.and(buildSpecification(criteria.getIsMultiply(), ProductChoice_.isMultiply));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(ProductChoice_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductAttributeSetId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductAttributeSetId(),
                    root -> root.join(ProductChoice_.productAttributeSet, JoinType.LEFT).get(ProductAttributeSet_.id)));
            }
            if (criteria.getProductOptionSetId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOptionSetId(),
                    root -> root.join(ProductChoice_.productOptionSet, JoinType.LEFT).get(ProductOptionSet_.id)));
            }
        }
        return specification;
    }
}
