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

import com.epmweb.server.domain.Products;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductsRepository;
import com.epmweb.server.service.dto.ProductsCriteria;
import com.epmweb.server.service.dto.ProductsDTO;
import com.epmweb.server.service.mapper.ProductsMapper;

/**
 * Service for executing complex queries for {@link Products} entities in the database.
 * The main input is a {@link ProductsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductsDTO} or a {@link Page} of {@link ProductsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductsQueryService extends QueryService<Products> {

    private final Logger log = LoggerFactory.getLogger(ProductsQueryService.class);

    private final ProductsRepository productsRepository;

    private final ProductsMapper productsMapper;

    public ProductsQueryService(ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    /**
     * Return a {@link List} of {@link ProductsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductsDTO> findByCriteria(ProductsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Products> specification = createSpecification(criteria);
        return productsMapper.toDto(productsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductsDTO> findByCriteria(ProductsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Products> specification = createSpecification(criteria);
        return productsRepository.findAll(specification, page)
            .map(productsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Products> specification = createSpecification(criteria);
        return productsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Products> createSpecification(ProductsCriteria criteria) {
        Specification<Products> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Products_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Products_.name));
            }
            if (criteria.getHandle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHandle(), Products_.handle));
            }
            if (criteria.getProductNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductNumber(), Products_.productNumber));
            }
            if (criteria.getSellCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellCount(), Products_.sellCount));
            }
            if (criteria.getThumbnailList() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailList(), Products_.thumbnailList));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), Products_.activeInd));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), Products_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), Products_.lastEditedWhen));
            }
            if (criteria.getProductDocumentId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductDocumentId(),
                    root -> root.join(Products_.productDocument, JoinType.LEFT).get(ProductDocument_.id)));
            }
            if (criteria.getStockItemListId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemListId(),
                    root -> root.join(Products_.stockItemLists, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Products_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(Products_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductBrandId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductBrandId(),
                    root -> root.join(Products_.productBrand, JoinType.LEFT).get(ProductBrand_.id)));
            }
        }
        return specification;
    }
}
