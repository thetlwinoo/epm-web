package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.PeopleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link People} and its DTO {@link PeopleDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PeopleMapper extends EntityMapper<PeopleDTO, People> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PeopleDTO toDto(People people);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "wishlist", ignore = true)
    @Mapping(target = "compare", ignore = true)
    People toEntity(PeopleDTO peopleDTO);

    default People fromId(Long id) {
        if (id == null) {
            return null;
        }
        People people = new People();
        people.setId(id);
        return people;
    }
}
