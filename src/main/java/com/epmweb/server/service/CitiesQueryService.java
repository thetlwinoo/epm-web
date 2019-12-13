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

import com.epmweb.server.domain.Cities;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CitiesRepository;
import com.epmweb.server.service.dto.CitiesCriteria;
import com.epmweb.server.service.dto.CitiesDTO;
import com.epmweb.server.service.mapper.CitiesMapper;

/**
 * Service for executing complex queries for {@link Cities} entities in the database.
 * The main input is a {@link CitiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitiesDTO} or a {@link Page} of {@link CitiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitiesQueryService extends QueryService<Cities> {

    private final Logger log = LoggerFactory.getLogger(CitiesQueryService.class);

    private final CitiesRepository citiesRepository;

    private final CitiesMapper citiesMapper;

    public CitiesQueryService(CitiesRepository citiesRepository, CitiesMapper citiesMapper) {
        this.citiesRepository = citiesRepository;
        this.citiesMapper = citiesMapper;
    }

    /**
     * Return a {@link List} of {@link CitiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitiesDTO> findByCriteria(CitiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesMapper.toDto(citiesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitiesDTO> findByCriteria(CitiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.findAll(specification, page)
            .map(citiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CitiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cities> createSpecification(CitiesCriteria criteria) {
        Specification<Cities> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cities_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Cities_.name));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Cities_.location));
            }
            if (criteria.getLatestRecordedPopulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatestRecordedPopulation(), Cities_.latestRecordedPopulation));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Cities_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Cities_.validTo));
            }
            if (criteria.getStateProvinceId() != null) {
                specification = specification.and(buildSpecification(criteria.getStateProvinceId(),
                    root -> root.join(Cities_.stateProvince, JoinType.LEFT).get(StateProvinces_.id)));
            }
        }
        return specification;
    }
}
