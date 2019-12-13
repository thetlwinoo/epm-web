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

import com.epmweb.server.domain.Currency;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CurrencyRepository;
import com.epmweb.server.service.dto.CurrencyCriteria;
import com.epmweb.server.service.dto.CurrencyDTO;
import com.epmweb.server.service.mapper.CurrencyMapper;

/**
 * Service for executing complex queries for {@link Currency} entities in the database.
 * The main input is a {@link CurrencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurrencyDTO} or a {@link Page} of {@link CurrencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyQueryService extends QueryService<Currency> {

    private final Logger log = LoggerFactory.getLogger(CurrencyQueryService.class);

    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyQueryService(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    /**
     * Return a {@link List} of {@link CurrencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyDTO> findByCriteria(CurrencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Currency> specification = createSpecification(criteria);
        return currencyMapper.toDto(currencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurrencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyDTO> findByCriteria(CurrencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Currency> specification = createSpecification(criteria);
        return currencyRepository.findAll(specification, page)
            .map(currencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CurrencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Currency> specification = createSpecification(criteria);
        return currencyRepository.count(specification);
    }

    /**
     * Function to convert {@link CurrencyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Currency> createSpecification(CurrencyCriteria criteria) {
        Specification<Currency> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Currency_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Currency_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Currency_.name));
            }
        }
        return specification;
    }
}
