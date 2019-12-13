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

import com.epmweb.server.domain.PaymentTransactions;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PaymentTransactionsRepository;
import com.epmweb.server.service.dto.PaymentTransactionsCriteria;
import com.epmweb.server.service.dto.PaymentTransactionsDTO;
import com.epmweb.server.service.mapper.PaymentTransactionsMapper;

/**
 * Service for executing complex queries for {@link PaymentTransactions} entities in the database.
 * The main input is a {@link PaymentTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentTransactionsDTO} or a {@link Page} of {@link PaymentTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentTransactionsQueryService extends QueryService<PaymentTransactions> {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionsQueryService.class);

    private final PaymentTransactionsRepository paymentTransactionsRepository;

    private final PaymentTransactionsMapper paymentTransactionsMapper;

    public PaymentTransactionsQueryService(PaymentTransactionsRepository paymentTransactionsRepository, PaymentTransactionsMapper paymentTransactionsMapper) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
        this.paymentTransactionsMapper = paymentTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentTransactionsDTO> findByCriteria(PaymentTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentTransactions> specification = createSpecification(criteria);
        return paymentTransactionsMapper.toDto(paymentTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentTransactionsDTO> findByCriteria(PaymentTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentTransactions> specification = createSpecification(criteria);
        return paymentTransactionsRepository.findAll(specification, page)
            .map(paymentTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentTransactions> specification = createSpecification(criteria);
        return paymentTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentTransactions> createSpecification(PaymentTransactionsCriteria criteria) {
        Specification<PaymentTransactions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentTransactions_.id));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), PaymentTransactions_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), PaymentTransactions_.lastEditedWhen));
            }
            if (criteria.getPaymentOnOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentOnOrderId(),
                    root -> root.join(PaymentTransactions_.paymentOnOrder, JoinType.LEFT).get(Orders_.id)));
            }
        }
        return specification;
    }
}
