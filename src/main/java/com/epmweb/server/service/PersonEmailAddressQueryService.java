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

import com.epmweb.server.domain.PersonEmailAddress;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PersonEmailAddressRepository;
import com.epmweb.server.service.dto.PersonEmailAddressCriteria;
import com.epmweb.server.service.dto.PersonEmailAddressDTO;
import com.epmweb.server.service.mapper.PersonEmailAddressMapper;

/**
 * Service for executing complex queries for {@link PersonEmailAddress} entities in the database.
 * The main input is a {@link PersonEmailAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonEmailAddressDTO} or a {@link Page} of {@link PersonEmailAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonEmailAddressQueryService extends QueryService<PersonEmailAddress> {

    private final Logger log = LoggerFactory.getLogger(PersonEmailAddressQueryService.class);

    private final PersonEmailAddressRepository personEmailAddressRepository;

    private final PersonEmailAddressMapper personEmailAddressMapper;

    public PersonEmailAddressQueryService(PersonEmailAddressRepository personEmailAddressRepository, PersonEmailAddressMapper personEmailAddressMapper) {
        this.personEmailAddressRepository = personEmailAddressRepository;
        this.personEmailAddressMapper = personEmailAddressMapper;
    }

    /**
     * Return a {@link List} of {@link PersonEmailAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonEmailAddressDTO> findByCriteria(PersonEmailAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonEmailAddress> specification = createSpecification(criteria);
        return personEmailAddressMapper.toDto(personEmailAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PersonEmailAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonEmailAddressDTO> findByCriteria(PersonEmailAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonEmailAddress> specification = createSpecification(criteria);
        return personEmailAddressRepository.findAll(specification, page)
            .map(personEmailAddressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonEmailAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonEmailAddress> specification = createSpecification(criteria);
        return personEmailAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonEmailAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PersonEmailAddress> createSpecification(PersonEmailAddressCriteria criteria) {
        Specification<PersonEmailAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PersonEmailAddress_.id));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), PersonEmailAddress_.emailAddress));
            }
            if (criteria.getDefaultInd() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultInd(), PersonEmailAddress_.defaultInd));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), PersonEmailAddress_.activeInd));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(PersonEmailAddress_.person, JoinType.LEFT).get(People_.id)));
            }
        }
        return specification;
    }
}
