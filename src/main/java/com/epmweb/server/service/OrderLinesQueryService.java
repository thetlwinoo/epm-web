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

import com.epmweb.server.domain.OrderLines;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.OrderLinesRepository;
import com.epmweb.server.service.dto.OrderLinesCriteria;
import com.epmweb.server.service.dto.OrderLinesDTO;
import com.epmweb.server.service.mapper.OrderLinesMapper;

/**
 * Service for executing complex queries for {@link OrderLines} entities in the database.
 * The main input is a {@link OrderLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderLinesDTO} or a {@link Page} of {@link OrderLinesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderLinesQueryService extends QueryService<OrderLines> {

    private final Logger log = LoggerFactory.getLogger(OrderLinesQueryService.class);

    private final OrderLinesRepository orderLinesRepository;

    private final OrderLinesMapper orderLinesMapper;

    public OrderLinesQueryService(OrderLinesRepository orderLinesRepository, OrderLinesMapper orderLinesMapper) {
        this.orderLinesRepository = orderLinesRepository;
        this.orderLinesMapper = orderLinesMapper;
    }

    /**
     * Return a {@link List} of {@link OrderLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderLinesDTO> findByCriteria(OrderLinesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderLines> specification = createSpecification(criteria);
        return orderLinesMapper.toDto(orderLinesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrderLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderLinesDTO> findByCriteria(OrderLinesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderLines> specification = createSpecification(criteria);
        return orderLinesRepository.findAll(specification, page)
            .map(orderLinesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderLinesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderLines> specification = createSpecification(criteria);
        return orderLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderLines> createSpecification(OrderLinesCriteria criteria) {
        Specification<OrderLines> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderLines_.id));
            }
            if (criteria.getCarrierTrackingNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCarrierTrackingNumber(), OrderLines_.carrierTrackingNumber));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), OrderLines_.quantity));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), OrderLines_.unitPrice));
            }
            if (criteria.getUnitPriceDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPriceDiscount(), OrderLines_.unitPriceDiscount));
            }
            if (criteria.getLineTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineTotal(), OrderLines_.lineTotal));
            }
            if (criteria.getTaxRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxRate(), OrderLines_.taxRate));
            }
            if (criteria.getPickedQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPickedQuantity(), OrderLines_.pickedQuantity));
            }
            if (criteria.getPickingCompletedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPickingCompletedWhen(), OrderLines_.pickingCompletedWhen));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), OrderLines_.status));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), OrderLines_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), OrderLines_.lastEditedWhen));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(OrderLines_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getPackageTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackageTypeId(),
                    root -> root.join(OrderLines_.packageType, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(OrderLines_.order, JoinType.LEFT).get(Orders_.id)));
            }
        }
        return specification;
    }
}
