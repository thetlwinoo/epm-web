package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.PurchaseOrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseOrders} and its DTO {@link PurchaseOrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, SuppliersMapper.class, DeliveryMethodsMapper.class})
public interface PurchaseOrdersMapper extends EntityMapper<PurchaseOrdersDTO, PurchaseOrders> {

    @Mapping(source = "contactPerson.id", target = "contactPersonId")
    @Mapping(source = "contactPerson.fullName", target = "contactPersonFullName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    PurchaseOrdersDTO toDto(PurchaseOrders purchaseOrders);

    @Mapping(target = "purchaseOrderLineLists", ignore = true)
    @Mapping(target = "removePurchaseOrderLineList", ignore = true)
    @Mapping(source = "contactPersonId", target = "contactPerson")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    PurchaseOrders toEntity(PurchaseOrdersDTO purchaseOrdersDTO);

    default PurchaseOrders fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrders purchaseOrders = new PurchaseOrders();
        purchaseOrders.setId(id);
        return purchaseOrders;
    }
}
