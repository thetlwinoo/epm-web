package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.OrderLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderLines} and its DTO {@link OrderLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class, PackageTypesMapper.class, OrdersMapper.class})
public interface OrderLinesMapper extends EntityMapper<OrderLinesDTO, OrderLines> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "packageType.name", target = "packageTypeName")
    @Mapping(source = "order.id", target = "orderId")
    OrderLinesDTO toDto(OrderLines orderLines);

    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "orderId", target = "order")
    OrderLines toEntity(OrderLinesDTO orderLinesDTO);

    default OrderLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderLines orderLines = new OrderLines();
        orderLines.setId(id);
        return orderLines;
    }
}
