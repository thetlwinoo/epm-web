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

import com.epmweb.server.domain.UploadActionTypes;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.UploadActionTypesRepository;
import com.epmweb.server.service.dto.UploadActionTypesCriteria;
import com.epmweb.server.service.dto.UploadActionTypesDTO;
import com.epmweb.server.service.mapper.UploadActionTypesMapper;

/**
 * Service for executing complex queries for {@link UploadActionTypes} entities in the database.
 * The main input is a {@link UploadActionTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UploadActionTypesDTO} or a {@link Page} of {@link UploadActionTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UploadActionTypesQueryService extends QueryService<UploadActionTypes> {

    private final Logger log = LoggerFactory.getLogger(UploadActionTypesQueryService.class);

    private final UploadActionTypesRepository uploadActionTypesRepository;

    private final UploadActionTypesMapper uploadActionTypesMapper;

    public UploadActionTypesQueryService(UploadActionTypesRepository uploadActionTypesRepository, UploadActionTypesMapper uploadActionTypesMapper) {
        this.uploadActionTypesRepository = uploadActionTypesRepository;
        this.uploadActionTypesMapper = uploadActionTypesMapper;
    }

    /**
     * Return a {@link List} of {@link UploadActionTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UploadActionTypesDTO> findByCriteria(UploadActionTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UploadActionTypes> specification = createSpecification(criteria);
        return uploadActionTypesMapper.toDto(uploadActionTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UploadActionTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadActionTypesDTO> findByCriteria(UploadActionTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UploadActionTypes> specification = createSpecification(criteria);
        return uploadActionTypesRepository.findAll(specification, page)
            .map(uploadActionTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UploadActionTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UploadActionTypes> specification = createSpecification(criteria);
        return uploadActionTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link UploadActionTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UploadActionTypes> createSpecification(UploadActionTypesCriteria criteria) {
        Specification<UploadActionTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UploadActionTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UploadActionTypes_.name));
            }
        }
        return specification;
    }
}
