package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.PersonEmailAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonEmailAddress} and its DTO {@link PersonEmailAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface PersonEmailAddressMapper extends EntityMapper<PersonEmailAddressDTO, PersonEmailAddress> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.fullName", target = "personFullName")
    PersonEmailAddressDTO toDto(PersonEmailAddress personEmailAddress);

    @Mapping(source = "personId", target = "person")
    PersonEmailAddress toEntity(PersonEmailAddressDTO personEmailAddressDTO);

    default PersonEmailAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonEmailAddress personEmailAddress = new PersonEmailAddress();
        personEmailAddress.setId(id);
        return personEmailAddress;
    }
}
