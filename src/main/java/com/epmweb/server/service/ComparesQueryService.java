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

import com.epmweb.server.domain.Compares;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ComparesRepository;
import com.epmweb.server.service.dto.ComparesCriteria;
import com.epmweb.server.service.dto.ComparesDTO;
import com.epmweb.server.service.mapper.ComparesMapper;

/**
 * Service for executing complex queries for {@link Compares} entities in the database.
 * The main input is a {@link ComparesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComparesDTO} or a {@link Page} of {@link ComparesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComparesQueryService extends QueryService<Compares> {

    private final Logger log = LoggerFactory.getLogger(ComparesQueryService.class);

    private final ComparesRepository comparesRepository;

    private final ComparesMapper comparesMapper;

    public ComparesQueryService(ComparesRepository comparesRepository, ComparesMapper comparesMapper) {
        this.comparesRepository = comparesRepository;
        this.comparesMapper = comparesMapper;
    }

    /**
     * Return a {@link List} of {@link ComparesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComparesDTO> findByCriteria(ComparesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Compares> specification = createSpecification(criteria);
        return comparesMapper.toDto(comparesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComparesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComparesDTO> findByCriteria(ComparesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Compares> specification = createSpecification(criteria);
        return comparesRepository.findAll(specification, page)
            .map(comparesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComparesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Compares> specification = createSpecification(criteria);
        return comparesRepository.count(specification);
    }

    /**
     * Function to convert {@link ComparesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Compares> createSpecification(ComparesCriteria criteria) {
        Specification<Compares> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Compares_.id));
            }
            if (criteria.getCompareUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompareUserId(),
                    root -> root.join(Compares_.compareUser, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getCompareListId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompareListId(),
                    root -> root.join(Compares_.compareLists, JoinType.LEFT).get(CompareProducts_.id)));
            }
        }
        return specification;
    }
}
