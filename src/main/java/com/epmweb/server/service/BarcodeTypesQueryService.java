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

import com.epmweb.server.domain.BarcodeTypes;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.BarcodeTypesRepository;
import com.epmweb.server.service.dto.BarcodeTypesCriteria;
import com.epmweb.server.service.dto.BarcodeTypesDTO;
import com.epmweb.server.service.mapper.BarcodeTypesMapper;

/**
 * Service for executing complex queries for {@link BarcodeTypes} entities in the database.
 * The main input is a {@link BarcodeTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BarcodeTypesDTO} or a {@link Page} of {@link BarcodeTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BarcodeTypesQueryService extends QueryService<BarcodeTypes> {

    private final Logger log = LoggerFactory.getLogger(BarcodeTypesQueryService.class);

    private final BarcodeTypesRepository barcodeTypesRepository;

    private final BarcodeTypesMapper barcodeTypesMapper;

    public BarcodeTypesQueryService(BarcodeTypesRepository barcodeTypesRepository, BarcodeTypesMapper barcodeTypesMapper) {
        this.barcodeTypesRepository = barcodeTypesRepository;
        this.barcodeTypesMapper = barcodeTypesMapper;
    }

    /**
     * Return a {@link List} of {@link BarcodeTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BarcodeTypesDTO> findByCriteria(BarcodeTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BarcodeTypes> specification = createSpecification(criteria);
        return barcodeTypesMapper.toDto(barcodeTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BarcodeTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BarcodeTypesDTO> findByCriteria(BarcodeTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BarcodeTypes> specification = createSpecification(criteria);
        return barcodeTypesRepository.findAll(specification, page)
            .map(barcodeTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BarcodeTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BarcodeTypes> specification = createSpecification(criteria);
        return barcodeTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link BarcodeTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BarcodeTypes> createSpecification(BarcodeTypesCriteria criteria) {
        Specification<BarcodeTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BarcodeTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BarcodeTypes_.name));
            }
        }
        return specification;
    }
}
