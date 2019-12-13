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

import com.epmweb.server.domain.StockItems;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.StockItemsRepository;
import com.epmweb.server.service.dto.StockItemsCriteria;
import com.epmweb.server.service.dto.StockItemsDTO;
import com.epmweb.server.service.mapper.StockItemsMapper;

/**
 * Service for executing complex queries for {@link StockItems} entities in the database.
 * The main input is a {@link StockItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItemsDTO} or a {@link Page} of {@link StockItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemsQueryService extends QueryService<StockItems> {

    private final Logger log = LoggerFactory.getLogger(StockItemsQueryService.class);

    private final StockItemsRepository stockItemsRepository;

    private final StockItemsMapper stockItemsMapper;

    public StockItemsQueryService(StockItemsRepository stockItemsRepository, StockItemsMapper stockItemsMapper) {
        this.stockItemsRepository = stockItemsRepository;
        this.stockItemsMapper = stockItemsMapper;
    }

    /**
     * Return a {@link List} of {@link StockItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItemsDTO> findByCriteria(StockItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItems> specification = createSpecification(criteria);
        return stockItemsMapper.toDto(stockItemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItemsDTO> findByCriteria(StockItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItems> specification = createSpecification(criteria);
        return stockItemsRepository.findAll(specification, page)
            .map(stockItemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItems> specification = createSpecification(criteria);
        return stockItemsRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItems> createSpecification(StockItemsCriteria criteria) {
        Specification<StockItems> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItems_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StockItems_.name));
            }
            if (criteria.getVendorCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorCode(), StockItems_.vendorCode));
            }
            if (criteria.getVendorSKU() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorSKU(), StockItems_.vendorSKU));
            }
            if (criteria.getGeneratedSKU() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGeneratedSKU(), StockItems_.generatedSKU));
            }
            if (criteria.getBarcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarcode(), StockItems_.barcode));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), StockItems_.unitPrice));
            }
            if (criteria.getRecommendedRetailPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecommendedRetailPrice(), StockItems_.recommendedRetailPrice));
            }
            if (criteria.getQuantityOnHand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOnHand(), StockItems_.quantityOnHand));
            }
            if (criteria.getItemLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemLength(), StockItems_.itemLength));
            }
            if (criteria.getItemWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemWidth(), StockItems_.itemWidth));
            }
            if (criteria.getItemHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemHeight(), StockItems_.itemHeight));
            }
            if (criteria.getItemWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemWeight(), StockItems_.itemWeight));
            }
            if (criteria.getItemPackageLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageLength(), StockItems_.itemPackageLength));
            }
            if (criteria.getItemPackageWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageWidth(), StockItems_.itemPackageWidth));
            }
            if (criteria.getItemPackageHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageHeight(), StockItems_.itemPackageHeight));
            }
            if (criteria.getItemPackageWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageWeight(), StockItems_.itemPackageWeight));
            }
            if (criteria.getNoOfPieces() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfPieces(), StockItems_.noOfPieces));
            }
            if (criteria.getNoOfItems() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfItems(), StockItems_.noOfItems));
            }
            if (criteria.getManufacture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManufacture(), StockItems_.manufacture));
            }
            if (criteria.getMarketingComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarketingComments(), StockItems_.marketingComments));
            }
            if (criteria.getInternalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalComments(), StockItems_.internalComments));
            }
            if (criteria.getSellStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellStartDate(), StockItems_.sellStartDate));
            }
            if (criteria.getSellEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellEndDate(), StockItems_.sellEndDate));
            }
            if (criteria.getSellCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellCount(), StockItems_.sellCount));
            }
            if (criteria.getCustomFields() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomFields(), StockItems_.customFields));
            }
            if (criteria.getThumbnailUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThumbnailUrl(), StockItems_.thumbnailUrl));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), StockItems_.activeInd));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), StockItems_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), StockItems_.lastEditedWhen));
            }
            if (criteria.getStockItemOnReviewLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemOnReviewLineId(),
                    root -> root.join(StockItems_.stockItemOnReviewLine, JoinType.LEFT).get(ReviewLines_.id)));
            }
            if (criteria.getPhotoListId() != null) {
                specification = specification.and(buildSpecification(criteria.getPhotoListId(),
                    root -> root.join(StockItems_.photoLists, JoinType.LEFT).get(Photos_.id)));
            }
            if (criteria.getDangerousGoodListId() != null) {
                specification = specification.and(buildSpecification(criteria.getDangerousGoodListId(),
                    root -> root.join(StockItems_.dangerousGoodLists, JoinType.LEFT).get(DangerousGoods_.id)));
            }
            if (criteria.getSpecialDiscountId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialDiscountId(),
                    root -> root.join(StockItems_.specialDiscounts, JoinType.LEFT).get(SpecialDeals_.id)));
            }
            if (criteria.getItemLengthUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemLengthUnitId(),
                    root -> root.join(StockItems_.itemLengthUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getItemWidthUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemWidthUnitId(),
                    root -> root.join(StockItems_.itemWidthUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getItemHeightUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemHeightUnitId(),
                    root -> root.join(StockItems_.itemHeightUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getPackageLengthUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackageLengthUnitId(),
                    root -> root.join(StockItems_.packageLengthUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getPackageWidthUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackageWidthUnitId(),
                    root -> root.join(StockItems_.packageWidthUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getPackageHeightUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackageHeightUnitId(),
                    root -> root.join(StockItems_.packageHeightUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getItemPackageWeightUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemPackageWeightUnitId(),
                    root -> root.join(StockItems_.itemPackageWeightUnit, JoinType.LEFT).get(UnitMeasure_.id)));
            }
            if (criteria.getProductAttributeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductAttributeId(),
                    root -> root.join(StockItems_.productAttribute, JoinType.LEFT).get(ProductAttribute_.id)));
            }
            if (criteria.getProductOptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOptionId(),
                    root -> root.join(StockItems_.productOption, JoinType.LEFT).get(ProductOption_.id)));
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(buildSpecification(criteria.getMaterialId(),
                    root -> root.join(StockItems_.material, JoinType.LEFT).get(Materials_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(StockItems_.currency, JoinType.LEFT).get(Currency_.id)));
            }
            if (criteria.getBarcodeTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getBarcodeTypeId(),
                    root -> root.join(StockItems_.barcodeType, JoinType.LEFT).get(BarcodeTypes_.id)));
            }
            if (criteria.getStockItemHoldingId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemHoldingId(),
                    root -> root.join(StockItems_.stockItemHolding, JoinType.LEFT).get(StockItemHoldings_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(StockItems_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
