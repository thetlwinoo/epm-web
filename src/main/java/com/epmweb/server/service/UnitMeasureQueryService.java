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

import com.epmweb.server.domain.UnitMeasure;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.UnitMeasureRepository;
import com.epmweb.server.service.dto.UnitMeasureCriteria;
import com.epmweb.server.service.dto.UnitMeasureDTO;
import com.epmweb.server.service.mapper.UnitMeasureMapper;

/**
 * Service for executing complex queries for {@link UnitMeasure} entities in the database.
 * The main input is a {@link UnitMeasureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UnitMeasureDTO} or a {@link Page} of {@link UnitMeasureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnitMeasureQueryService extends QueryService<UnitMeasure> {

    private final Logger log = LoggerFactory.getLogger(UnitMeasureQueryService.class);

    private final UnitMeasureRepository unitMeasureRepository;

    private final UnitMeasureMapper unitMeasureMapper;

    public UnitMeasureQueryService(UnitMeasureRepository unitMeasureRepository, UnitMeasureMapper unitMeasureMapper) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.unitMeasureMapper = unitMeasureMapper;
    }

    /**
     * Return a {@link List} of {@link UnitMeasureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UnitMeasureDTO> findByCriteria(UnitMeasureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UnitMeasure> specification = createSpecification(criteria);
        return unitMeasureMapper.toDto(unitMeasureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UnitMeasureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UnitMeasureDTO> findByCriteria(UnitMeasureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UnitMeasure> specification = createSpecification(criteria);
        return unitMeasureRepository.findAll(specification, page)
            .map(unitMeasureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UnitMeasureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UnitMeasure> specification = createSpecification(criteria);
        return unitMeasureRepository.count(specification);
    }

    /**
     * Function to convert {@link UnitMeasureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UnitMeasure> createSpecification(UnitMeasureCriteria criteria) {
        Specification<UnitMeasure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UnitMeasure_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), UnitMeasure_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UnitMeasure_.name));
            }
        }
        return specification;
    }
}
