package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.SuppliersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suppliers} and its DTO {@link SuppliersDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SupplierCategoriesMapper.class, DeliveryMethodsMapper.class, CitiesMapper.class})
public interface SuppliersMapper extends EntityMapper<SuppliersDTO, Suppliers> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "supplierCategory.id", target = "supplierCategoryId")
    @Mapping(source = "supplierCategory.name", target = "supplierCategoryName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "deliveryCity.id", target = "deliveryCityId")
    @Mapping(source = "deliveryCity.name", target = "deliveryCityName")
    @Mapping(source = "postalCity.id", target = "postalCityId")
    @Mapping(source = "postalCity.name", target = "postalCityName")
    SuppliersDTO toDto(Suppliers suppliers);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "supplierCategoryId", target = "supplierCategory")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "deliveryCityId", target = "deliveryCity")
    @Mapping(source = "postalCityId", target = "postalCity")
    Suppliers toEntity(SuppliersDTO suppliersDTO);

    default Suppliers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suppliers suppliers = new Suppliers();
        suppliers.setId(id);
        return suppliers;
    }
}
