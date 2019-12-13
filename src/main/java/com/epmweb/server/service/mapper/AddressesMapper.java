package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.AddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateProvincesMapper.class, AddressTypesMapper.class, PeopleMapper.class})
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {

    @Mapping(source = "stateProvince.id", target = "stateProvinceId")
    @Mapping(source = "stateProvince.name", target = "stateProvinceName")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.name", target = "addressTypeName")
    @Mapping(source = "person.id", target = "personId")
    AddressesDTO toDto(Addresses addresses);

    @Mapping(source = "stateProvinceId", target = "stateProvince")
    @Mapping(source = "addressTypeId", target = "addressType")
    @Mapping(source = "personId", target = "person")
    Addresses toEntity(AddressesDTO addressesDTO);

    default Addresses fromId(Long id) {
        if (id == null) {
            return null;
        }
        Addresses addresses = new Addresses();
        addresses.setId(id);
        return addresses;
    }
}
