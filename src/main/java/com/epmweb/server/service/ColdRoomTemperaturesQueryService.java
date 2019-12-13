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

import com.epmweb.server.domain.ColdRoomTemperatures;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ColdRoomTemperaturesRepository;
import com.epmweb.server.service.dto.ColdRoomTemperaturesCriteria;
import com.epmweb.server.service.dto.ColdRoomTemperaturesDTO;
import com.epmweb.server.service.mapper.ColdRoomTemperaturesMapper;

/**
 * Service for executing complex queries for {@link ColdRoomTemperatures} entities in the database.
 * The main input is a {@link ColdRoomTemperaturesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ColdRoomTemperaturesDTO} or a {@link Page} of {@link ColdRoomTemperaturesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ColdRoomTemperaturesQueryService extends QueryService<ColdRoomTemperatures> {

    private final Logger log = LoggerFactory.getLogger(ColdRoomTemperaturesQueryService.class);

    private final ColdRoomTemperaturesRepository coldRoomTemperaturesRepository;

    private final ColdRoomTemperaturesMapper coldRoomTemperaturesMapper;

    public ColdRoomTemperaturesQueryService(ColdRoomTemperaturesRepository coldRoomTemperaturesRepository, ColdRoomTemperaturesMapper coldRoomTemperaturesMapper) {
        this.coldRoomTemperaturesRepository = coldRoomTemperaturesRepository;
        this.coldRoomTemperaturesMapper = coldRoomTemperaturesMapper;
    }

    /**
     * Return a {@link List} of {@link ColdRoomTemperaturesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ColdRoomTemperaturesDTO> findByCriteria(ColdRoomTemperaturesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ColdRoomTemperatures> specification = createSpecification(criteria);
        return coldRoomTemperaturesMapper.toDto(coldRoomTemperaturesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ColdRoomTemperaturesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ColdRoomTemperaturesDTO> findByCriteria(ColdRoomTemperaturesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ColdRoomTemperatures> specification = createSpecification(criteria);
        return coldRoomTemperaturesRepository.findAll(specification, page)
            .map(coldRoomTemperaturesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ColdRoomTemperaturesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ColdRoomTemperatures> specification = createSpecification(criteria);
        return coldRoomTemperaturesRepository.count(specification);
    }

    /**
     * Function to convert {@link ColdRoomTemperaturesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ColdRoomTemperatures> createSpecification(ColdRoomTemperaturesCriteria criteria) {
        Specification<ColdRoomTemperatures> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ColdRoomTemperatures_.id));
            }
            if (criteria.getColdRoomSensorNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColdRoomSensorNumber(), ColdRoomTemperatures_.coldRoomSensorNumber));
            }
            if (criteria.getRecordedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecordedWhen(), ColdRoomTemperatures_.recordedWhen));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperature(), ColdRoomTemperatures_.temperature));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), ColdRoomTemperatures_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), ColdRoomTemperatures_.validTo));
            }
        }
        return specification;
    }
}
