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

import com.epmweb.server.domain.CustomerTransactions;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.CustomerTransactionsRepository;
import com.epmweb.server.service.dto.CustomerTransactionsCriteria;
import com.epmweb.server.service.dto.CustomerTransactionsDTO;
import com.epmweb.server.service.mapper.CustomerTransactionsMapper;

/**
 * Service for executing complex queries for {@link CustomerTransactions} entities in the database.
 * The main input is a {@link CustomerTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerTransactionsDTO} or a {@link Page} of {@link CustomerTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerTransactionsQueryService extends QueryService<CustomerTransactions> {

    private final Logger log = LoggerFactory.getLogger(CustomerTransactionsQueryService.class);

    private final CustomerTransactionsRepository customerTransactionsRepository;

    private final CustomerTransactionsMapper customerTransactionsMapper;

    public CustomerTransactionsQueryService(CustomerTransactionsRepository customerTransactionsRepository, CustomerTransactionsMapper customerTransactionsMapper) {
        this.customerTransactionsRepository = customerTransactionsRepository;
        this.customerTransactionsMapper = customerTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerTransactionsDTO> findByCriteria(CustomerTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerTransactions> specification = createSpecification(criteria);
        return customerTransactionsMapper.toDto(customerTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerTransactionsDTO> findByCriteria(CustomerTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerTransactions> specification = createSpecification(criteria);
        return customerTransactionsRepository.findAll(specification, page)
            .map(customerTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerTransactions> specification = createSpecification(criteria);
        return customerTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerTransactions> createSpecification(CustomerTransactionsCriteria criteria) {
        Specification<CustomerTransactions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerTransactions_.id));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), CustomerTransactions_.transactionDate));
            }
            if (criteria.getAmountExcludingTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountExcludingTax(), CustomerTransactions_.amountExcludingTax));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), CustomerTransactions_.taxAmount));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), CustomerTransactions_.transactionAmount));
            }
            if (criteria.getOutstandingBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutstandingBalance(), CustomerTransactions_.outstandingBalance));
            }
            if (criteria.getFinalizationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinalizationDate(), CustomerTransactions_.finalizationDate));
            }
            if (criteria.getIsFinalized() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFinalized(), CustomerTransactions_.isFinalized));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), CustomerTransactions_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CustomerTransactions_.lastEditedWhen));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(CustomerTransactions_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getPaymentTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentTransactionId(),
                    root -> root.join(CustomerTransactions_.paymentTransaction, JoinType.LEFT).get(PaymentTransactions_.id)));
            }
            if (criteria.getTransactionTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionTypeId(),
                    root -> root.join(CustomerTransactions_.transactionType, JoinType.LEFT).get(TransactionTypes_.id)));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(CustomerTransactions_.invoice, JoinType.LEFT).get(Invoices_.id)));
            }
        }
        return specification;
    }
}
