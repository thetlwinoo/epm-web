package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.StockItemHoldingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockItemHoldings} and its DTO {@link StockItemHoldingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class})
public interface StockItemHoldingsMapper extends EntityMapper<StockItemHoldingsDTO, StockItemHoldings> {

    @Mapping(source = "stockItemHoldingOnStockItem.id", target = "stockItemHoldingOnStockItemId")
    StockItemHoldingsDTO toDto(StockItemHoldings stockItemHoldings);

    @Mapping(source = "stockItemHoldingOnStockItemId", target = "stockItemHoldingOnStockItem")
    StockItemHoldings toEntity(StockItemHoldingsDTO stockItemHoldingsDTO);

    default StockItemHoldings fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemHoldings stockItemHoldings = new StockItemHoldings();
        stockItemHoldings.setId(id);
        return stockItemHoldings;
    }
}
