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

import com.epmweb.server.domain.PackageTypes;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PackageTypesRepository;
import com.epmweb.server.service.dto.PackageTypesCriteria;
import com.epmweb.server.service.dto.PackageTypesDTO;
import com.epmweb.server.service.mapper.PackageTypesMapper;

/**
 * Service for executing complex queries for {@link PackageTypes} entities in the database.
 * The main input is a {@link PackageTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PackageTypesDTO} or a {@link Page} of {@link PackageTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PackageTypesQueryService extends QueryService<PackageTypes> {

    private final Logger log = LoggerFactory.getLogger(PackageTypesQueryService.class);

    private final PackageTypesRepository packageTypesRepository;

    private final PackageTypesMapper packageTypesMapper;

    public PackageTypesQueryService(PackageTypesRepository packageTypesRepository, PackageTypesMapper packageTypesMapper) {
        this.packageTypesRepository = packageTypesRepository;
        this.packageTypesMapper = packageTypesMapper;
    }

    /**
     * Return a {@link List} of {@link PackageTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PackageTypesDTO> findByCriteria(PackageTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PackageTypes> specification = createSpecification(criteria);
        return packageTypesMapper.toDto(packageTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PackageTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PackageTypesDTO> findByCriteria(PackageTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PackageTypes> specification = createSpecification(criteria);
        return packageTypesRepository.findAll(specification, page)
            .map(packageTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PackageTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PackageTypes> specification = createSpecification(criteria);
        return packageTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link PackageTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PackageTypes> createSpecification(PackageTypesCriteria criteria) {
        Specification<PackageTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PackageTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PackageTypes_.name));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), PackageTypes_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), PackageTypes_.validTo));
            }
        }
        return specification;
    }
}
