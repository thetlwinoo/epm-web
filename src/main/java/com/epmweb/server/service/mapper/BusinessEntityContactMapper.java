package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.BusinessEntityContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessEntityContact} and its DTO {@link BusinessEntityContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, ContactTypeMapper.class})
public interface BusinessEntityContactMapper extends EntityMapper<BusinessEntityContactDTO, BusinessEntityContact> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "contactType.id", target = "contactTypeId")
    @Mapping(source = "contactType.name", target = "contactTypeName")
    BusinessEntityContactDTO toDto(BusinessEntityContact businessEntityContact);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "contactTypeId", target = "contactType")
    BusinessEntityContact toEntity(BusinessEntityContactDTO businessEntityContactDTO);

    default BusinessEntityContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessEntityContact businessEntityContact = new BusinessEntityContact();
        businessEntityContact.setId(id);
        return businessEntityContact;
    }
}
