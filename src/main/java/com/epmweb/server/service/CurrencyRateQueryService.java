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

import com.epmweb.server.domain.CurrencyRate;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CurrencyRateRepository;
import com.epmweb.server.service.dto.CurrencyRateCriteria;
import com.epmweb.server.service.dto.CurrencyRateDTO;
import com.epmweb.server.service.mapper.CurrencyRateMapper;

/**
 * Service for executing complex queries for {@link CurrencyRate} entities in the database.
 * The main input is a {@link CurrencyRateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurrencyRateDTO} or a {@link Page} of {@link CurrencyRateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyRateQueryService extends QueryService<CurrencyRate> {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateQueryService.class);

    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyRateMapper currencyRateMapper;

    public CurrencyRateQueryService(CurrencyRateRepository currencyRateRepository, CurrencyRateMapper currencyRateMapper) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyRateMapper = currencyRateMapper;
    }

    /**
     * Return a {@link List} of {@link CurrencyRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyRateDTO> findByCriteria(CurrencyRateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CurrencyRate> specification = createSpecification(criteria);
        return currencyRateMapper.toDto(currencyRateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurrencyRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyRateDTO> findByCriteria(CurrencyRateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CurrencyRate> specification = createSpecification(criteria);
        return currencyRateRepository.findAll(specification, page)
            .map(currencyRateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CurrencyRateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CurrencyRate> specification = createSpecification(criteria);
        return currencyRateRepository.count(specification);
    }

    /**
     * Function to convert {@link CurrencyRateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CurrencyRate> createSpecification(CurrencyRateCriteria criteria) {
        Specification<CurrencyRate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CurrencyRate_.id));
            }
            if (criteria.getCurrencyRateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrencyRateDate(), CurrencyRate_.currencyRateDate));
            }
            if (criteria.getFromcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFromcode(), CurrencyRate_.fromcode));
            }
            if (criteria.getTocode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTocode(), CurrencyRate_.tocode));
            }
            if (criteria.getAverageRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAverageRate(), CurrencyRate_.averageRate));
            }
            if (criteria.getEndOfDayRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndOfDayRate(), CurrencyRate_.endOfDayRate));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), CurrencyRate_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CurrencyRate_.lastEditedWhen));
            }
        }
        return specification;
    }
}
