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

import com.epmweb.server.domain.StockItemTemp;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.StockItemTempRepository;
import com.epmweb.server.service.dto.StockItemTempCriteria;
import com.epmweb.server.service.dto.StockItemTempDTO;
import com.epmweb.server.service.mapper.StockItemTempMapper;

/**
 * Service for executing complex queries for {@link StockItemTemp} entities in the database.
 * The main input is a {@link StockItemTempCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItemTempDTO} or a {@link Page} of {@link StockItemTempDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemTempQueryService extends QueryService<StockItemTemp> {

    private final Logger log = LoggerFactory.getLogger(StockItemTempQueryService.class);

    private final StockItemTempRepository stockItemTempRepository;

    private final StockItemTempMapper stockItemTempMapper;

    public StockItemTempQueryService(StockItemTempRepository stockItemTempRepository, StockItemTempMapper stockItemTempMapper) {
        this.stockItemTempRepository = stockItemTempRepository;
        this.stockItemTempMapper = stockItemTempMapper;
    }

    /**
     * Return a {@link List} of {@link StockItemTempDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItemTempDTO> findByCriteria(StockItemTempCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItemTemp> specification = createSpecification(criteria);
        return stockItemTempMapper.toDto(stockItemTempRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockItemTempDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItemTempDTO> findByCriteria(StockItemTempCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItemTemp> specification = createSpecification(criteria);
        return stockItemTempRepository.findAll(specification, page)
            .map(stockItemTempMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemTempCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItemTemp> specification = createSpecification(criteria);
        return stockItemTempRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemTempCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItemTemp> createSpecification(StockItemTempCriteria criteria) {
        Specification<StockItemTemp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItemTemp_.id));
            }
            if (criteria.getStockItemName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockItemName(), StockItemTemp_.stockItemName));
            }
            if (criteria.getVendorCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorCode(), StockItemTemp_.vendorCode));
            }
            if (criteria.getVendorSKU() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorSKU(), StockItemTemp_.vendorSKU));
            }
            if (criteria.getBarcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarcode(), StockItemTemp_.barcode));
            }
            if (criteria.getBarcodeTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBarcodeTypeId(), StockItemTemp_.barcodeTypeId));
            }
            if (criteria.getBarcodeTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarcodeTypeName(), StockItemTemp_.barcodeTypeName));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductType(), StockItemTemp_.productType));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductCategoryId(), StockItemTemp_.productCategoryId));
            }
            if (criteria.getProductCategoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductCategoryName(), StockItemTemp_.productCategoryName));
            }
            if (criteria.getProductAttributeSetId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductAttributeSetId(), StockItemTemp_.productAttributeSetId));
            }
            if (criteria.getProductAttributeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductAttributeId(), StockItemTemp_.productAttributeId));
            }
            if (criteria.getProductAttributeValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductAttributeValue(), StockItemTemp_.productAttributeValue));
            }
            if (criteria.getProductOptionSetId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductOptionSetId(), StockItemTemp_.productOptionSetId));
            }
            if (criteria.getProductOptionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductOptionId(), StockItemTemp_.productOptionId));
            }
            if (criteria.getProductOptionValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductOptionValue(), StockItemTemp_.productOptionValue));
            }
            if (criteria.getModelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelName(), StockItemTemp_.modelName));
            }
            if (criteria.getModelNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelNumber(), StockItemTemp_.modelNumber));
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaterialId(), StockItemTemp_.materialId));
            }
            if (criteria.getMaterialName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialName(), StockItemTemp_.materialName));
            }
            if (criteria.getProductBrandId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductBrandId(), StockItemTemp_.productBrandId));
            }
            if (criteria.getProductBrandName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductBrandName(), StockItemTemp_.productBrandName));
            }
            if (criteria.getDangerousGoods() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDangerousGoods(), StockItemTemp_.dangerousGoods));
            }
            if (criteria.getVideoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideoUrl(), StockItemTemp_.videoUrl));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), StockItemTemp_.unitPrice));
            }
            if (criteria.getRemommendedRetailPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRemommendedRetailPrice(), StockItemTemp_.remommendedRetailPrice));
            }
            if (criteria.getCurrencyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyCode(), StockItemTemp_.currencyCode));
            }
            if (criteria.getQuantityOnHand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityOnHand(), StockItemTemp_.quantityOnHand));
            }
            if (criteria.getWarrantyPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPeriod(), StockItemTemp_.warrantyPeriod));
            }
            if (criteria.getWarrantyPolicy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPolicy(), StockItemTemp_.warrantyPolicy));
            }
            if (criteria.getWarrantyTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWarrantyTypeId(), StockItemTemp_.warrantyTypeId));
            }
            if (criteria.getWarrantyTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyTypeName(), StockItemTemp_.warrantyTypeName));
            }
            if (criteria.getItemLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemLength(), StockItemTemp_.itemLength));
            }
            if (criteria.getItemWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemWidth(), StockItemTemp_.itemWidth));
            }
            if (criteria.getItemHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemHeight(), StockItemTemp_.itemHeight));
            }
            if (criteria.getItemWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemWeight(), StockItemTemp_.itemWeight));
            }
            if (criteria.getItemPackageLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageLength(), StockItemTemp_.itemPackageLength));
            }
            if (criteria.getItemPackageWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageWidth(), StockItemTemp_.itemPackageWidth));
            }
            if (criteria.getItemPackageHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageHeight(), StockItemTemp_.itemPackageHeight));
            }
            if (criteria.getItemPackageWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageWeight(), StockItemTemp_.itemPackageWeight));
            }
            if (criteria.getItemLengthUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemLengthUnitMeasureId(), StockItemTemp_.itemLengthUnitMeasureId));
            }
            if (criteria.getItemLengthUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemLengthUnitMeasureCode(), StockItemTemp_.itemLengthUnitMeasureCode));
            }
            if (criteria.getItemWidthUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemWidthUnitMeasureId(), StockItemTemp_.itemWidthUnitMeasureId));
            }
            if (criteria.getItemWidthUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemWidthUnitMeasureCode(), StockItemTemp_.itemWidthUnitMeasureCode));
            }
            if (criteria.getItemHeightUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemHeightUnitMeasureId(), StockItemTemp_.itemHeightUnitMeasureId));
            }
            if (criteria.getItemHeightUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemHeightUnitMeasureCode(), StockItemTemp_.itemHeightUnitMeasureCode));
            }
            if (criteria.getItemWeightUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemWeightUnitMeasureId(), StockItemTemp_.itemWeightUnitMeasureId));
            }
            if (criteria.getItemWeightUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemWeightUnitMeasureCode(), StockItemTemp_.itemWeightUnitMeasureCode));
            }
            if (criteria.getItemPackageLengthUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageLengthUnitMeasureId(), StockItemTemp_.itemPackageLengthUnitMeasureId));
            }
            if (criteria.getItemPackageLengthUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemPackageLengthUnitMeasureCode(), StockItemTemp_.itemPackageLengthUnitMeasureCode));
            }
            if (criteria.getItemPackageWidthUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageWidthUnitMeasureId(), StockItemTemp_.itemPackageWidthUnitMeasureId));
            }
            if (criteria.getItemPackageWidthUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemPackageWidthUnitMeasureCode(), StockItemTemp_.itemPackageWidthUnitMeasureCode));
            }
            if (criteria.getItemPackageHeightUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageHeightUnitMeasureId(), StockItemTemp_.itemPackageHeightUnitMeasureId));
            }
            if (criteria.getItemPackageHeightUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemPackageHeightUnitMeasureCode(), StockItemTemp_.itemPackageHeightUnitMeasureCode));
            }
            if (criteria.getItemPackageWeightUnitMeasureId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPackageWeightUnitMeasureId(), StockItemTemp_.itemPackageWeightUnitMeasureId));
            }
            if (criteria.getItemPackageWeightUnitMeasureCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemPackageWeightUnitMeasureCode(), StockItemTemp_.itemPackageWeightUnitMeasureCode));
            }
            if (criteria.getNoOfPieces() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfPieces(), StockItemTemp_.noOfPieces));
            }
            if (criteria.getNoOfItems() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfItems(), StockItemTemp_.noOfItems));
            }
            if (criteria.getManufacture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManufacture(), StockItemTemp_.manufacture));
            }
            if (criteria.getProductComplianceCertificate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductComplianceCertificate(), StockItemTemp_.productComplianceCertificate));
            }
            if (criteria.getGenuineAndLegal() != null) {
                specification = specification.and(buildSpecification(criteria.getGenuineAndLegal(), StockItemTemp_.genuineAndLegal));
            }
            if (criteria.getCountryOfOrigin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryOfOrigin(), StockItemTemp_.countryOfOrigin));
            }
            if (criteria.getSellStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellStartDate(), StockItemTemp_.sellStartDate));
            }
            if (criteria.getSellEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellEndDate(), StockItemTemp_.sellEndDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), StockItemTemp_.status));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), StockItemTemp_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), StockItemTemp_.lastEditedWhen));
            }
            if (criteria.getUploadTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadTransactionId(),
                    root -> root.join(StockItemTemp_.uploadTransaction, JoinType.LEFT).get(UploadTransactions_.id)));
            }
        }
        return specification;
    }
}
