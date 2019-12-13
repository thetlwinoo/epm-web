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

import com.epmweb.server.domain.Addresses;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.AddressesRepository;
import com.epmweb.server.service.dto.AddressesCriteria;
import com.epmweb.server.service.dto.AddressesDTO;
import com.epmweb.server.service.mapper.AddressesMapper;

/**
 * Service for executing complex queries for {@link Addresses} entities in the database.
 * The main input is a {@link AddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressesDTO} or a {@link Page} of {@link AddressesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressesQueryService extends QueryService<Addresses> {

    private final Logger log = LoggerFactory.getLogger(AddressesQueryService.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    public AddressesQueryService(AddressesRepository addressesRepository, AddressesMapper addressesMapper) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
    }

    /**
     * Return a {@link List} of {@link AddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressesDTO> findByCriteria(AddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesMapper.toDto(addressesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressesDTO> findByCriteria(AddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification, page)
            .map(addressesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Addresses> createSpecification(AddressesCriteria criteria) {
        Specification<Addresses> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Addresses_.id));
            }
            if (criteria.getContactPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPerson(), Addresses_.contactPerson));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), Addresses_.contactNumber));
            }
            if (criteria.getContactEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmailAddress(), Addresses_.contactEmailAddress));
            }
            if (criteria.getAddressLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine1(), Addresses_.addressLine1));
            }
            if (criteria.getAddressLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine2(), Addresses_.addressLine2));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Addresses_.city));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Addresses_.postalCode));
            }
            if (criteria.getDefaultInd() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultInd(), Addresses_.defaultInd));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), Addresses_.activeInd));
            }
            if (criteria.getStateProvinceId() != null) {
                specification = specification.and(buildSpecification(criteria.getStateProvinceId(),
                    root -> root.join(Addresses_.stateProvince, JoinType.LEFT).get(StateProvinces_.id)));
            }
            if (criteria.getAddressTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressTypeId(),
                    root -> root.join(Addresses_.addressType, JoinType.LEFT).get(AddressTypes_.id)));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(Addresses_.person, JoinType.LEFT).get(People_.id)));
            }
        }
        return specification;
    }
}
