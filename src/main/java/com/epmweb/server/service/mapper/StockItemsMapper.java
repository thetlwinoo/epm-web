package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.StockItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockItems} and its DTO {@link StockItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReviewLinesMapper.class, UnitMeasureMapper.class, ProductAttributeMapper.class, ProductOptionMapper.class, MaterialsMapper.class, CurrencyMapper.class, BarcodeTypesMapper.class, ProductsMapper.class})
public interface StockItemsMapper extends EntityMapper<StockItemsDTO, StockItems> {

    @Mapping(source = "stockItemOnReviewLine.id", target = "stockItemOnReviewLineId")
    @Mapping(source = "itemLengthUnit.id", target = "itemLengthUnitId")
    @Mapping(source = "itemLengthUnit.code", target = "itemLengthUnitCode")
    @Mapping(source = "itemWidthUnit.id", target = "itemWidthUnitId")
    @Mapping(source = "itemWidthUnit.code", target = "itemWidthUnitCode")
    @Mapping(source = "itemHeightUnit.id", target = "itemHeightUnitId")
    @Mapping(source = "itemHeightUnit.code", target = "itemHeightUnitCode")
    @Mapping(source = "packageLengthUnit.id", target = "packageLengthUnitId")
    @Mapping(source = "packageLengthUnit.code", target = "packageLengthUnitCode")
    @Mapping(source = "packageWidthUnit.id", target = "packageWidthUnitId")
    @Mapping(source = "packageWidthUnit.code", target = "packageWidthUnitCode")
    @Mapping(source = "packageHeightUnit.id", target = "packageHeightUnitId")
    @Mapping(source = "packageHeightUnit.code", target = "packageHeightUnitCode")
    @Mapping(source = "itemPackageWeightUnit.id", target = "itemPackageWeightUnitId")
    @Mapping(source = "itemPackageWeightUnit.code", target = "itemPackageWeightUnitCode")
    @Mapping(source = "productAttribute.id", target = "productAttributeId")
    @Mapping(source = "productAttribute.value", target = "productAttributeValue")
    @Mapping(source = "productOption.id", target = "productOptionId")
    @Mapping(source = "productOption.value", target = "productOptionValue")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "material.name", target = "materialName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyCode")
    @Mapping(source = "barcodeType.id", target = "barcodeTypeId")
    @Mapping(source = "barcodeType.name", target = "barcodeTypeName")
    @Mapping(source = "product.id", target = "productId")
    StockItemsDTO toDto(StockItems stockItems);

    @Mapping(source = "stockItemOnReviewLineId", target = "stockItemOnReviewLine")
    @Mapping(target = "photoLists", ignore = true)
    @Mapping(target = "removePhotoList", ignore = true)
    @Mapping(target = "dangerousGoodLists", ignore = true)
    @Mapping(target = "removeDangerousGoodList", ignore = true)
    @Mapping(target = "specialDiscounts", ignore = true)
    @Mapping(target = "removeSpecialDiscount", ignore = true)
    @Mapping(source = "itemLengthUnitId", target = "itemLengthUnit")
    @Mapping(source = "itemWidthUnitId", target = "itemWidthUnit")
    @Mapping(source = "itemHeightUnitId", target = "itemHeightUnit")
    @Mapping(source = "packageLengthUnitId", target = "packageLengthUnit")
    @Mapping(source = "packageWidthUnitId", target = "packageWidthUnit")
    @Mapping(source = "packageHeightUnitId", target = "packageHeightUnit")
    @Mapping(source = "itemPackageWeightUnitId", target = "itemPackageWeightUnit")
    @Mapping(source = "productAttributeId", target = "productAttribute")
    @Mapping(source = "productOptionId", target = "productOption")
    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "barcodeTypeId", target = "barcodeType")
    @Mapping(target = "stockItemHolding", ignore = true)
    @Mapping(source = "productId", target = "product")
    StockItems toEntity(StockItemsDTO stockItemsDTO);

    default StockItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItems stockItems = new StockItems();
        stockItems.setId(id);
        return stockItems;
    }
}
