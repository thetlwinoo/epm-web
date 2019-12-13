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

import com.epmweb.server.domain.SupplierCategories;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.SupplierCategoriesRepository;
import com.epmweb.server.service.dto.SupplierCategoriesCriteria;
import com.epmweb.server.service.dto.SupplierCategoriesDTO;
import com.epmweb.server.service.mapper.SupplierCategoriesMapper;

/**
 * Service for executing complex queries for {@link SupplierCategories} entities in the database.
 * The main input is a {@link SupplierCategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierCategoriesDTO} or a {@link Page} of {@link SupplierCategoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierCategoriesQueryService extends QueryService<SupplierCategories> {

    private final Logger log = LoggerFactory.getLogger(SupplierCategoriesQueryService.class);

    private final SupplierCategoriesRepository supplierCategoriesRepository;

    private final SupplierCategoriesMapper supplierCategoriesMapper;

    public SupplierCategoriesQueryService(SupplierCategoriesRepository supplierCategoriesRepository, SupplierCategoriesMapper supplierCategoriesMapper) {
        this.supplierCategoriesRepository = supplierCategoriesRepository;
        this.supplierCategoriesMapper = supplierCategoriesMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierCategoriesDTO> findByCriteria(SupplierCategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierCategories> specification = createSpecification(criteria);
        return supplierCategoriesMapper.toDto(supplierCategoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierCategoriesDTO> findByCriteria(SupplierCategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierCategories> specification = createSpecification(criteria);
        return supplierCategoriesRepository.findAll(specification, page)
            .map(supplierCategoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierCategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierCategories> specification = createSpecification(criteria);
        return supplierCategoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierCategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierCategories> createSpecification(SupplierCategoriesCriteria criteria) {
        Specification<SupplierCategories> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierCategories_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SupplierCategories_.name));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), SupplierCategories_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), SupplierCategories_.validTo));
            }
        }
        return specification;
    }
}
