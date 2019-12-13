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

import com.epmweb.server.domain.PhoneNumberType;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PhoneNumberTypeRepository;
import com.epmweb.server.service.dto.PhoneNumberTypeCriteria;
import com.epmweb.server.service.dto.PhoneNumberTypeDTO;
import com.epmweb.server.service.mapper.PhoneNumberTypeMapper;

/**
 * Service for executing complex queries for {@link PhoneNumberType} entities in the database.
 * The main input is a {@link PhoneNumberTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhoneNumberTypeDTO} or a {@link Page} of {@link PhoneNumberTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhoneNumberTypeQueryService extends QueryService<PhoneNumberType> {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberTypeQueryService.class);

    private final PhoneNumberTypeRepository phoneNumberTypeRepository;

    private final PhoneNumberTypeMapper phoneNumberTypeMapper;

    public PhoneNumberTypeQueryService(PhoneNumberTypeRepository phoneNumberTypeRepository, PhoneNumberTypeMapper phoneNumberTypeMapper) {
        this.phoneNumberTypeRepository = phoneNumberTypeRepository;
        this.phoneNumberTypeMapper = phoneNumberTypeMapper;
    }

    /**
     * Return a {@link List} of {@link PhoneNumberTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PhoneNumberTypeDTO> findByCriteria(PhoneNumberTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PhoneNumberType> specification = createSpecification(criteria);
        return phoneNumberTypeMapper.toDto(phoneNumberTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PhoneNumberTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PhoneNumberTypeDTO> findByCriteria(PhoneNumberTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PhoneNumberType> specification = createSpecification(criteria);
        return phoneNumberTypeRepository.findAll(specification, page)
            .map(phoneNumberTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhoneNumberTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PhoneNumberType> specification = createSpecification(criteria);
        return phoneNumberTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link PhoneNumberTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PhoneNumberType> createSpecification(PhoneNumberTypeCriteria criteria) {
        Specification<PhoneNumberType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PhoneNumberType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PhoneNumberType_.name));
            }
        }
        return specification;
    }
}
