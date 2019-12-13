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

import com.epmweb.server.domain.SpecialDeals;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.SpecialDealsRepository;
import com.epmweb.server.service.dto.SpecialDealsCriteria;
import com.epmweb.server.service.dto.SpecialDealsDTO;
import com.epmweb.server.service.mapper.SpecialDealsMapper;

/**
 * Service for executing complex queries for {@link SpecialDeals} entities in the database.
 * The main input is a {@link SpecialDealsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpecialDealsDTO} or a {@link Page} of {@link SpecialDealsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpecialDealsQueryService extends QueryService<SpecialDeals> {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsQueryService.class);

    private final SpecialDealsRepository specialDealsRepository;

    private final SpecialDealsMapper specialDealsMapper;

    public SpecialDealsQueryService(SpecialDealsRepository specialDealsRepository, SpecialDealsMapper specialDealsMapper) {
        this.specialDealsRepository = specialDealsRepository;
        this.specialDealsMapper = specialDealsMapper;
    }

    /**
     * Return a {@link List} of {@link SpecialDealsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpecialDealsDTO> findByCriteria(SpecialDealsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpecialDeals> specification = createSpecification(criteria);
        return specialDealsMapper.toDto(specialDealsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpecialDealsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecialDealsDTO> findByCriteria(SpecialDealsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpecialDeals> specification = createSpecification(criteria);
        return specialDealsRepository.findAll(specification, page)
            .map(specialDealsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpecialDealsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpecialDeals> specification = createSpecification(criteria);
        return specialDealsRepository.count(specification);
    }

    /**
     * Function to convert {@link SpecialDealsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SpecialDeals> createSpecification(SpecialDealsCriteria criteria) {
        Specification<SpecialDeals> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SpecialDeals_.id));
            }
            if (criteria.getDealDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealDescription(), SpecialDeals_.dealDescription));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), SpecialDeals_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), SpecialDeals_.endDate));
            }
            if (criteria.getDiscountAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountAmount(), SpecialDeals_.discountAmount));
            }
            if (criteria.getDiscountPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountPercentage(), SpecialDeals_.discountPercentage));
            }
            if (criteria.getDiscountCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountCode(), SpecialDeals_.discountCode));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), SpecialDeals_.unitPrice));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), SpecialDeals_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), SpecialDeals_.lastEditedWhen));
            }
            if (criteria.getCartDiscountId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartDiscountId(),
                    root -> root.join(SpecialDeals_.cartDiscounts, JoinType.LEFT).get(ShoppingCarts_.id)));
            }
            if (criteria.getOrderDiscountId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderDiscountId(),
                    root -> root.join(SpecialDeals_.orderDiscounts, JoinType.LEFT).get(Orders_.id)));
            }
            if (criteria.getBuyingGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getBuyingGroupId(),
                    root -> root.join(SpecialDeals_.buyingGroup, JoinType.LEFT).get(BuyingGroups_.id)));
            }
            if (criteria.getCustomerCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerCategoryId(),
                    root -> root.join(SpecialDeals_.customerCategory, JoinType.LEFT).get(CustomerCategories_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(SpecialDeals_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(SpecialDeals_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(SpecialDeals_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
        }
        return specification;
    }
}
