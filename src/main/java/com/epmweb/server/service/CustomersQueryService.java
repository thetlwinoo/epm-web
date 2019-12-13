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

import com.epmweb.server.domain.Customers;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CustomersRepository;
import com.epmweb.server.service.dto.CustomersCriteria;
import com.epmweb.server.service.dto.CustomersDTO;
import com.epmweb.server.service.mapper.CustomersMapper;

/**
 * Service for executing complex queries for {@link Customers} entities in the database.
 * The main input is a {@link CustomersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomersDTO} or a {@link Page} of {@link CustomersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomersQueryService extends QueryService<Customers> {

    private final Logger log = LoggerFactory.getLogger(CustomersQueryService.class);

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    public CustomersQueryService(CustomersRepository customersRepository, CustomersMapper customersMapper) {
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
    }

    /**
     * Return a {@link List} of {@link CustomersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomersDTO> findByCriteria(CustomersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersMapper.toDto(customersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersDTO> findByCriteria(CustomersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.findAll(specification, page)
            .map(customersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customers> createSpecification(CustomersCriteria criteria) {
        Specification<Customers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customers_.id));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), Customers_.accountNumber));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Customers_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
