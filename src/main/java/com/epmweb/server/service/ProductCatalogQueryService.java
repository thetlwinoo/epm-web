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

import com.epmweb.server.domain.ProductCatalog;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductCatalogRepository;
import com.epmweb.server.service.dto.ProductCatalogCriteria;
import com.epmweb.server.service.dto.ProductCatalogDTO;
import com.epmweb.server.service.mapper.ProductCatalogMapper;

/**
 * Service for executing complex queries for {@link ProductCatalog} entities in the database.
 * The main input is a {@link ProductCatalogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductCatalogDTO} or a {@link Page} of {@link ProductCatalogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductCatalogQueryService extends QueryService<ProductCatalog> {

    private final Logger log = LoggerFactory.getLogger(ProductCatalogQueryService.class);

    private final ProductCatalogRepository productCatalogRepository;

    private final ProductCatalogMapper productCatalogMapper;

    public ProductCatalogQueryService(ProductCatalogRepository productCatalogRepository, ProductCatalogMapper productCatalogMapper) {
        this.productCatalogRepository = productCatalogRepository;
        this.productCatalogMapper = productCatalogMapper;
    }

    /**
     * Return a {@link List} of {@link ProductCatalogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductCatalogDTO> findByCriteria(ProductCatalogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductCatalog> specification = createSpecification(criteria);
        return productCatalogMapper.toDto(productCatalogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductCatalogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCatalogDTO> findByCriteria(ProductCatalogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductCatalog> specification = createSpecification(criteria);
        return productCatalogRepository.findAll(specification, page)
            .map(productCatalogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCatalogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductCatalog> specification = createSpecification(criteria);
        return productCatalogRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCatalogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductCatalog> createSpecification(ProductCatalogCriteria criteria) {
        Specification<ProductCatalog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductCatalog_.id));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(ProductCatalog_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductCatalog_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
