package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.PurchaseOrderLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseOrderLines} and its DTO {@link PurchaseOrderLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {PackageTypesMapper.class, StockItemsMapper.class, PurchaseOrdersMapper.class})
public interface PurchaseOrderLinesMapper extends EntityMapper<PurchaseOrderLinesDTO, PurchaseOrderLines> {

    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "packageType.name", target = "packageTypeName")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    PurchaseOrderLinesDTO toDto(PurchaseOrderLines purchaseOrderLines);

    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PurchaseOrderLines toEntity(PurchaseOrderLinesDTO purchaseOrderLinesDTO);

    default PurchaseOrderLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrderLines purchaseOrderLines = new PurchaseOrderLines();
        purchaseOrderLines.setId(id);
        return purchaseOrderLines;
    }
}
