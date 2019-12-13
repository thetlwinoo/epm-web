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

import com.epmweb.server.domain.StateProvinces;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.StateProvincesRepository;
import com.epmweb.server.service.dto.StateProvincesCriteria;
import com.epmweb.server.service.dto.StateProvincesDTO;
import com.epmweb.server.service.mapper.StateProvincesMapper;

/**
 * Service for executing complex queries for {@link StateProvinces} entities in the database.
 * The main input is a {@link StateProvincesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateProvincesDTO} or a {@link Page} of {@link StateProvincesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateProvincesQueryService extends QueryService<StateProvinces> {

    private final Logger log = LoggerFactory.getLogger(StateProvincesQueryService.class);

    private final StateProvincesRepository stateProvincesRepository;

    private final StateProvincesMapper stateProvincesMapper;

    public StateProvincesQueryService(StateProvincesRepository stateProvincesRepository, StateProvincesMapper stateProvincesMapper) {
        this.stateProvincesRepository = stateProvincesRepository;
        this.stateProvincesMapper = stateProvincesMapper;
    }

    /**
     * Return a {@link List} of {@link StateProvincesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateProvincesDTO> findByCriteria(StateProvincesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StateProvinces> specification = createSpecification(criteria);
        return stateProvincesMapper.toDto(stateProvincesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StateProvincesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateProvincesDTO> findByCriteria(StateProvincesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StateProvinces> specification = createSpecification(criteria);
        return stateProvincesRepository.findAll(specification, page)
            .map(stateProvincesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StateProvincesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StateProvinces> specification = createSpecification(criteria);
        return stateProvincesRepository.count(specification);
    }

    /**
     * Function to convert {@link StateProvincesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StateProvinces> createSpecification(StateProvincesCriteria criteria) {
        Specification<StateProvinces> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StateProvinces_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), StateProvinces_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StateProvinces_.name));
            }
            if (criteria.getSalesTerritory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalesTerritory(), StateProvinces_.salesTerritory));
            }
            if (criteria.getBorder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBorder(), StateProvinces_.border));
            }
            if (criteria.getLatestRecordedPopulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatestRecordedPopulation(), StateProvinces_.latestRecordedPopulation));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), StateProvinces_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), StateProvinces_.validTo));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(StateProvinces_.country, JoinType.LEFT).get(Countries_.id)));
            }
        }
        return specification;
    }
}
