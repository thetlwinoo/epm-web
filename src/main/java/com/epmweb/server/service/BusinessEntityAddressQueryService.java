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

import com.epmweb.server.domain.BusinessEntityAddress;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.BusinessEntityAddressRepository;
import com.epmweb.server.service.dto.BusinessEntityAddressCriteria;
import com.epmweb.server.service.dto.BusinessEntityAddressDTO;
import com.epmweb.server.service.mapper.BusinessEntityAddressMapper;

/**
 * Service for executing complex queries for {@link BusinessEntityAddress} entities in the database.
 * The main input is a {@link BusinessEntityAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessEntityAddressDTO} or a {@link Page} of {@link BusinessEntityAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessEntityAddressQueryService extends QueryService<BusinessEntityAddress> {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityAddressQueryService.class);

    private final BusinessEntityAddressRepository businessEntityAddressRepository;

    private final BusinessEntityAddressMapper businessEntityAddressMapper;

    public BusinessEntityAddressQueryService(BusinessEntityAddressRepository businessEntityAddressRepository, BusinessEntityAddressMapper businessEntityAddressMapper) {
        this.businessEntityAddressRepository = businessEntityAddressRepository;
        this.businessEntityAddressMapper = businessEntityAddressMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessEntityAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessEntityAddressDTO> findByCriteria(BusinessEntityAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessEntityAddress> specification = createSpecification(criteria);
        return businessEntityAddressMapper.toDto(businessEntityAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessEntityAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessEntityAddressDTO> findByCriteria(BusinessEntityAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessEntityAddress> specification = createSpecification(criteria);
        return businessEntityAddressRepository.findAll(specification, page)
            .map(businessEntityAddressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessEntityAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessEntityAddress> specification = createSpecification(criteria);
        return businessEntityAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessEntityAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessEntityAddress> createSpecification(BusinessEntityAddressCriteria criteria) {
        Specification<BusinessEntityAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BusinessEntityAddress_.id));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(BusinessEntityAddress_.address, JoinType.LEFT).get(Addresses_.id)));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(BusinessEntityAddress_.person, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getAddressTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressTypeId(),
                    root -> root.join(BusinessEntityAddress_.addressType, JoinType.LEFT).get(AddressTypes_.id)));
            }
        }
        return specification;
    }
}
