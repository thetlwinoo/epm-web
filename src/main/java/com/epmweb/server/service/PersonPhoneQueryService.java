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

import com.epmweb.server.domain.PersonPhone;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PersonPhoneRepository;
import com.epmweb.server.service.dto.PersonPhoneCriteria;
import com.epmweb.server.service.dto.PersonPhoneDTO;
import com.epmweb.server.service.mapper.PersonPhoneMapper;

/**
 * Service for executing complex queries for {@link PersonPhone} entities in the database.
 * The main input is a {@link PersonPhoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonPhoneDTO} or a {@link Page} of {@link PersonPhoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonPhoneQueryService extends QueryService<PersonPhone> {

    private final Logger log = LoggerFactory.getLogger(PersonPhoneQueryService.class);

    private final PersonPhoneRepository personPhoneRepository;

    private final PersonPhoneMapper personPhoneMapper;

    public PersonPhoneQueryService(PersonPhoneRepository personPhoneRepository, PersonPhoneMapper personPhoneMapper) {
        this.personPhoneRepository = personPhoneRepository;
        this.personPhoneMapper = personPhoneMapper;
    }

    /**
     * Return a {@link List} of {@link PersonPhoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonPhoneDTO> findByCriteria(PersonPhoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonPhone> specification = createSpecification(criteria);
        return personPhoneMapper.toDto(personPhoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PersonPhoneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonPhoneDTO> findByCriteria(PersonPhoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonPhone> specification = createSpecification(criteria);
        return personPhoneRepository.findAll(specification, page)
            .map(personPhoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonPhoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonPhone> specification = createSpecification(criteria);
        return personPhoneRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonPhoneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PersonPhone> createSpecification(PersonPhoneCriteria criteria) {
        Specification<PersonPhone> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PersonPhone_.id));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), PersonPhone_.phoneNumber));
            }
            if (criteria.getDefaultInd() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultInd(), PersonPhone_.defaultInd));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), PersonPhone_.activeInd));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(PersonPhone_.person, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getPhoneNumberTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPhoneNumberTypeId(),
                    root -> root.join(PersonPhone_.phoneNumberType, JoinType.LEFT).get(PhoneNumberType_.id)));
            }
        }
        return specification;
    }
}
