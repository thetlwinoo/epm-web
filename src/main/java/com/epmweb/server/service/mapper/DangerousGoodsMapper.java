package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.DangerousGoodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DangerousGoods} and its DTO {@link DangerousGoodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class})
public interface DangerousGoodsMapper extends EntityMapper<DangerousGoodsDTO, DangerousGoods> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    DangerousGoodsDTO toDto(DangerousGoods dangerousGoods);

    @Mapping(source = "stockItemId", target = "stockItem")
    DangerousGoods toEntity(DangerousGoodsDTO dangerousGoodsDTO);

    default DangerousGoods fromId(Long id) {
        if (id == null) {
            return null;
        }
        DangerousGoods dangerousGoods = new DangerousGoods();
        dangerousGoods.setId(id);
        return dangerousGoods;
    }
}
