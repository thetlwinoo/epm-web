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

import com.epmweb.server.domain.Materials;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.MaterialsRepository;
import com.epmweb.server.service.dto.MaterialsCriteria;
import com.epmweb.server.service.dto.MaterialsDTO;
import com.epmweb.server.service.mapper.MaterialsMapper;

/**
 * Service for executing complex queries for {@link Materials} entities in the database.
 * The main input is a {@link MaterialsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaterialsDTO} or a {@link Page} of {@link MaterialsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialsQueryService extends QueryService<Materials> {

    private final Logger log = LoggerFactory.getLogger(MaterialsQueryService.class);

    private final MaterialsRepository materialsRepository;

    private final MaterialsMapper materialsMapper;

    public MaterialsQueryService(MaterialsRepository materialsRepository, MaterialsMapper materialsMapper) {
        this.materialsRepository = materialsRepository;
        this.materialsMapper = materialsMapper;
    }

    /**
     * Return a {@link List} of {@link MaterialsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaterialsDTO> findByCriteria(MaterialsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Materials> specification = createSpecification(criteria);
        return materialsMapper.toDto(materialsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MaterialsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialsDTO> findByCriteria(MaterialsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Materials> specification = createSpecification(criteria);
        return materialsRepository.findAll(specification, page)
            .map(materialsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Materials> specification = createSpecification(criteria);
        return materialsRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Materials> createSpecification(MaterialsCriteria criteria) {
        Specification<Materials> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Materials_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Materials_.name));
            }
        }
        return specification;
    }
}
