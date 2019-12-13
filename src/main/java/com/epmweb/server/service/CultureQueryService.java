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

import com.epmweb.server.domain.Culture;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CultureRepository;
import com.epmweb.server.service.dto.CultureCriteria;
import com.epmweb.server.service.dto.CultureDTO;
import com.epmweb.server.service.mapper.CultureMapper;

/**
 * Service for executing complex queries for {@link Culture} entities in the database.
 * The main input is a {@link CultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CultureDTO} or a {@link Page} of {@link CultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CultureQueryService extends QueryService<Culture> {

    private final Logger log = LoggerFactory.getLogger(CultureQueryService.class);

    private final CultureRepository cultureRepository;

    private final CultureMapper cultureMapper;

    public CultureQueryService(CultureRepository cultureRepository, CultureMapper cultureMapper) {
        this.cultureRepository = cultureRepository;
        this.cultureMapper = cultureMapper;
    }

    /**
     * Return a {@link List} of {@link CultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CultureDTO> findByCriteria(CultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Culture> specification = createSpecification(criteria);
        return cultureMapper.toDto(cultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CultureDTO> findByCriteria(CultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Culture> specification = createSpecification(criteria);
        return cultureRepository.findAll(specification, page)
            .map(cultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Culture> specification = createSpecification(criteria);
        return cultureRepository.count(specification);
    }

    /**
     * Function to convert {@link CultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Culture> createSpecification(CultureCriteria criteria) {
        Specification<Culture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Culture_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Culture_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Culture_.name));
            }
        }
        return specification;
    }
}
