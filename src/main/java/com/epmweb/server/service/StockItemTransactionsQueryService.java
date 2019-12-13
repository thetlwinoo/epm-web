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

import com.epmweb.server.domain.StockItemTransactions;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.StockItemTransactionsRepository;
import com.epmweb.server.service.dto.StockItemTransactionsCriteria;
import com.epmweb.server.service.dto.StockItemTransactionsDTO;
import com.epmweb.server.service.mapper.StockItemTransactionsMapper;

/**
 * Service for executing complex queries for {@link StockItemTransactions} entities in the database.
 * The main input is a {@link StockItemTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItemTransactionsDTO} or a {@link Page} of {@link StockItemTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemTransactionsQueryService extends QueryService<StockItemTransactions> {

    private final Logger log = LoggerFactory.getLogger(StockItemTransactionsQueryService.class);

    private final StockItemTransactionsRepository stockItemTransactionsRepository;

    private final StockItemTransactionsMapper stockItemTransactionsMapper;

    public StockItemTransactionsQueryService(StockItemTransactionsRepository stockItemTransactionsRepository, StockItemTransactionsMapper stockItemTransactionsMapper) {
        this.stockItemTransactionsRepository = stockItemTransactionsRepository;
        this.stockItemTransactionsMapper = stockItemTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link StockItemTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItemTransactionsDTO> findByCriteria(StockItemTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItemTransactions> specification = createSpecification(criteria);
        return stockItemTransactionsMapper.toDto(stockItemTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockItemTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItemTransactionsDTO> findByCriteria(StockItemTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItemTransactions> specification = createSpecification(criteria);
        return stockItemTransactionsRepository.findAll(specification, page)
            .map(stockItemTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItemTransactions> specification = createSpecification(criteria);
        return stockItemTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItemTransactions> createSpecification(StockItemTransactionsCriteria criteria) {
        Specification<StockItemTransactions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItemTransactions_.id));
            }
            if (criteria.getTransactionOccuredWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionOccuredWhen(), StockItemTransactions_.transactionOccuredWhen));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), StockItemTransactions_.quantity));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), StockItemTransactions_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), StockItemTransactions_.lastEditedWhen));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(StockItemTransactions_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(StockItemTransactions_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(StockItemTransactions_.invoice, JoinType.LEFT).get(Invoices_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(StockItemTransactions_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getTransactionTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionTypeId(),
                    root -> root.join(StockItemTransactions_.transactionType, JoinType.LEFT).get(TransactionTypes_.id)));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(StockItemTransactions_.purchaseOrder, JoinType.LEFT).get(PurchaseOrders_.id)));
            }
        }
        return specification;
    }
}
