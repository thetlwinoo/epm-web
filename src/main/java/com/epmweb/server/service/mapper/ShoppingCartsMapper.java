package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ShoppingCartsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingCarts} and its DTO {@link ShoppingCartsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, CustomersMapper.class, SpecialDealsMapper.class})
public interface ShoppingCartsMapper extends EntityMapper<ShoppingCartsDTO, ShoppingCarts> {

    @Mapping(source = "cartUser.id", target = "cartUserId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "specialDeals.id", target = "specialDealsId")
    ShoppingCartsDTO toDto(ShoppingCarts shoppingCarts);

    @Mapping(source = "cartUserId", target = "cartUser")
    @Mapping(target = "cartItemLists", ignore = true)
    @Mapping(target = "removeCartItemList", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "specialDealsId", target = "specialDeals")
    ShoppingCarts toEntity(ShoppingCartsDTO shoppingCartsDTO);

    default ShoppingCarts fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCarts shoppingCarts = new ShoppingCarts();
        shoppingCarts.setId(id);
        return shoppingCarts;
    }
}
