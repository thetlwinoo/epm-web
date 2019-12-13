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

import com.epmweb.server.domain.WarrantyTypes;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.WarrantyTypesRepository;
import com.epmweb.server.service.dto.WarrantyTypesCriteria;
import com.epmweb.server.service.dto.WarrantyTypesDTO;
import com.epmweb.server.service.mapper.WarrantyTypesMapper;

/**
 * Service for executing complex queries for {@link WarrantyTypes} entities in the database.
 * The main input is a {@link WarrantyTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WarrantyTypesDTO} or a {@link Page} of {@link WarrantyTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WarrantyTypesQueryService extends QueryService<WarrantyTypes> {

    private final Logger log = LoggerFactory.getLogger(WarrantyTypesQueryService.class);

    private final WarrantyTypesRepository warrantyTypesRepository;

    private final WarrantyTypesMapper warrantyTypesMapper;

    public WarrantyTypesQueryService(WarrantyTypesRepository warrantyTypesRepository, WarrantyTypesMapper warrantyTypesMapper) {
        this.warrantyTypesRepository = warrantyTypesRepository;
        this.warrantyTypesMapper = warrantyTypesMapper;
    }

    /**
     * Return a {@link List} of {@link WarrantyTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WarrantyTypesDTO> findByCriteria(WarrantyTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WarrantyTypes> specification = createSpecification(criteria);
        return warrantyTypesMapper.toDto(warrantyTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WarrantyTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WarrantyTypesDTO> findByCriteria(WarrantyTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WarrantyTypes> specification = createSpecification(criteria);
        return warrantyTypesRepository.findAll(specification, page)
            .map(warrantyTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WarrantyTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WarrantyTypes> specification = createSpecification(criteria);
        return warrantyTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link WarrantyTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WarrantyTypes> createSpecification(WarrantyTypesCriteria criteria) {
        Specification<WarrantyTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WarrantyTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), WarrantyTypes_.name));
            }
        }
        return specification;
    }
}
