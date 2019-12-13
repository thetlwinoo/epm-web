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

import com.epmweb.server.domain.ShoppingCartItems;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ShoppingCartItemsRepository;
import com.epmweb.server.service.dto.ShoppingCartItemsCriteria;
import com.epmweb.server.service.dto.ShoppingCartItemsDTO;
import com.epmweb.server.service.mapper.ShoppingCartItemsMapper;

/**
 * Service for executing complex queries for {@link ShoppingCartItems} entities in the database.
 * The main input is a {@link ShoppingCartItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShoppingCartItemsDTO} or a {@link Page} of {@link ShoppingCartItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShoppingCartItemsQueryService extends QueryService<ShoppingCartItems> {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemsQueryService.class);

    private final ShoppingCartItemsRepository shoppingCartItemsRepository;

    private final ShoppingCartItemsMapper shoppingCartItemsMapper;

    public ShoppingCartItemsQueryService(ShoppingCartItemsRepository shoppingCartItemsRepository, ShoppingCartItemsMapper shoppingCartItemsMapper) {
        this.shoppingCartItemsRepository = shoppingCartItemsRepository;
        this.shoppingCartItemsMapper = shoppingCartItemsMapper;
    }

    /**
     * Return a {@link List} of {@link ShoppingCartItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShoppingCartItemsDTO> findByCriteria(ShoppingCartItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShoppingCartItems> specification = createSpecification(criteria);
        return shoppingCartItemsMapper.toDto(shoppingCartItemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShoppingCartItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShoppingCartItemsDTO> findByCriteria(ShoppingCartItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShoppingCartItems> specification = createSpecification(criteria);
        return shoppingCartItemsRepository.findAll(specification, page)
            .map(shoppingCartItemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShoppingCartItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShoppingCartItems> specification = createSpecification(criteria);
        return shoppingCartItemsRepository.count(specification);
    }

    /**
     * Function to convert {@link ShoppingCartItemsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShoppingCartItems> createSpecification(ShoppingCartItemsCriteria criteria) {
        Specification<ShoppingCartItems> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShoppingCartItems_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), ShoppingCartItems_.quantity));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), ShoppingCartItems_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), ShoppingCartItems_.lastEditedWhen));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(ShoppingCartItems_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartId(),
                    root -> root.join(ShoppingCartItems_.cart, JoinType.LEFT).get(ShoppingCarts_.id)));
            }
        }
        return specification;
    }
}
