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

import com.epmweb.server.domain.StockItemHoldings;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.StockItemHoldingsRepository;
import com.epmweb.server.service.dto.StockItemHoldingsCriteria;
import com.epmweb.server.service.dto.StockItemHoldingsDTO;
import com.epmweb.server.service.mapper.StockItemHoldingsMapper;

/**
 * Service for executing complex queries for {@link StockItemHoldings} entities in the database.
 * The main input is a {@link StockItemHoldingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItemHoldingsDTO} or a {@link Page} of {@link StockItemHoldingsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemHoldingsQueryService extends QueryService<StockItemHoldings> {

    private final Logger log = LoggerFactory.getLogger(StockItemHoldingsQueryService.class);

    private final StockItemHoldingsRepository stockItemHoldingsRepository;

    private final StockItemHoldingsMapper stockItemHoldingsMapper;

    public StockItemHoldingsQueryService(StockItemHoldingsRepository stockItemHoldingsRepository, StockItemHoldingsMapper stockItemHoldingsMapper) {
        this.stockItemHoldingsRepository = stockItemHoldingsRepository;
        this.stockItemHoldingsMapper = stockItemHoldingsMapper;
    }

    /**
     * Return a {@link List} of {@link StockItemHoldingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItemHoldingsDTO> findByCriteria(StockItemHoldingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItemHoldings> specification = createSpecification(criteria);
        return stockItemHoldingsMapper.toDto(stockItemHoldingsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockItemHoldingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItemHoldingsDTO> findByCriteria(StockItemHoldingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItemHoldings> specification = createSpecification(criteria);
        return stockItemHoldingsRepository.findAll(specification, page)
            .map(stockItemHoldingsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemHoldingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItemHoldings> specification = createSpecification(criteria);
        return stockItemHoldingsRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemHoldingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItemHoldings> createSpecification(StockItemHoldingsCriteria criteria) {
        Specification<StockItemHoldings> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItemHoldings_.id));
            }
            if (criteria.getQuantityOnHand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOnHand(), StockItemHoldings_.quantityOnHand));
            }
            if (criteria.getBinLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBinLocation(), StockItemHoldings_.binLocation));
            }
            if (criteria.getLastStocktakeQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastStocktakeQuantity(), StockItemHoldings_.lastStocktakeQuantity));
            }
            if (criteria.getLastCostPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastCostPrice(), StockItemHoldings_.lastCostPrice));
            }
            if (criteria.getReorderLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReorderLevel(), StockItemHoldings_.reorderLevel));
            }
            if (criteria.getTargerStockLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTargerStockLevel(), StockItemHoldings_.targerStockLevel));
            }
            if (criteria.getStockItemHoldingOnStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemHoldingOnStockItemId(),
                    root -> root.join(StockItemHoldings_.stockItemHoldingOnStockItem, JoinType.LEFT).get(StockItems_.id)));
            }
        }
        return specification;
    }
}
