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

import com.epmweb.server.domain.SystemParameters;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.SystemParametersRepository;
import com.epmweb.server.service.dto.SystemParametersCriteria;
import com.epmweb.server.service.dto.SystemParametersDTO;
import com.epmweb.server.service.mapper.SystemParametersMapper;

/**
 * Service for executing complex queries for {@link SystemParameters} entities in the database.
 * The main input is a {@link SystemParametersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemParametersDTO} or a {@link Page} of {@link SystemParametersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemParametersQueryService extends QueryService<SystemParameters> {

    private final Logger log = LoggerFactory.getLogger(SystemParametersQueryService.class);

    private final SystemParametersRepository systemParametersRepository;

    private final SystemParametersMapper systemParametersMapper;

    public SystemParametersQueryService(SystemParametersRepository systemParametersRepository, SystemParametersMapper systemParametersMapper) {
        this.systemParametersRepository = systemParametersRepository;
        this.systemParametersMapper = systemParametersMapper;
    }

    /**
     * Return a {@link List} of {@link SystemParametersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemParametersDTO> findByCriteria(SystemParametersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemParameters> specification = createSpecification(criteria);
        return systemParametersMapper.toDto(systemParametersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemParametersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemParametersDTO> findByCriteria(SystemParametersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemParameters> specification = createSpecification(criteria);
        return systemParametersRepository.findAll(specification, page)
            .map(systemParametersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemParametersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemParameters> specification = createSpecification(criteria);
        return systemParametersRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemParametersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SystemParameters> createSpecification(SystemParametersCriteria criteria) {
        Specification<SystemParameters> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SystemParameters_.id));
            }
            if (criteria.getApplicationSettings() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicationSettings(), SystemParameters_.applicationSettings));
            }
            if (criteria.getDeliveryCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryCityId(),
                    root -> root.join(SystemParameters_.deliveryCity, JoinType.LEFT).get(Cities_.id)));
            }
            if (criteria.getPostalCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostalCityId(),
                    root -> root.join(SystemParameters_.postalCity, JoinType.LEFT).get(Cities_.id)));
            }
        }
        return specification;
    }
}
