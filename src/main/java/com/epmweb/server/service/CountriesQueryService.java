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

import com.epmweb.server.domain.Countries;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CountriesRepository;
import com.epmweb.server.service.dto.CountriesCriteria;
import com.epmweb.server.service.dto.CountriesDTO;
import com.epmweb.server.service.mapper.CountriesMapper;

/**
 * Service for executing complex queries for {@link Countries} entities in the database.
 * The main input is a {@link CountriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountriesDTO} or a {@link Page} of {@link CountriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountriesQueryService extends QueryService<Countries> {

    private final Logger log = LoggerFactory.getLogger(CountriesQueryService.class);

    private final CountriesRepository countriesRepository;

    private final CountriesMapper countriesMapper;

    public CountriesQueryService(CountriesRepository countriesRepository, CountriesMapper countriesMapper) {
        this.countriesRepository = countriesRepository;
        this.countriesMapper = countriesMapper;
    }

    /**
     * Return a {@link List} of {@link CountriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountriesDTO> findByCriteria(CountriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesMapper.toDto(countriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CountriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountriesDTO> findByCriteria(CountriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.findAll(specification, page)
            .map(countriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.count(specification);
    }

    /**
     * Function to convert {@link CountriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Countries> createSpecification(CountriesCriteria criteria) {
        Specification<Countries> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Countries_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Countries_.name));
            }
            if (criteria.getFormalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormalName(), Countries_.formalName));
            }
            if (criteria.getIsoAplha3Code() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsoAplha3Code(), Countries_.isoAplha3Code));
            }
            if (criteria.getIsoNumericCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsoNumericCode(), Countries_.isoNumericCode));
            }
            if (criteria.getCountryType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryType(), Countries_.countryType));
            }
            if (criteria.getLatestRecordedPopulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatestRecordedPopulation(), Countries_.latestRecordedPopulation));
            }
            if (criteria.getContinent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContinent(), Countries_.continent));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), Countries_.region));
            }
            if (criteria.getSubregion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubregion(), Countries_.subregion));
            }
            if (criteria.getBorder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBorder(), Countries_.border));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Countries_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Countries_.validTo));
            }
        }
        return specification;
    }
}
