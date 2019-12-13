package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ComparesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compares} and its DTO {@link ComparesDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface ComparesMapper extends EntityMapper<ComparesDTO, Compares> {

    @Mapping(source = "compareUser.id", target = "compareUserId")
    ComparesDTO toDto(Compares compares);

    @Mapping(source = "compareUserId", target = "compareUser")
    @Mapping(target = "compareLists", ignore = true)
    @Mapping(target = "removeCompareList", ignore = true)
    Compares toEntity(ComparesDTO comparesDTO);

    default Compares fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compares compares = new Compares();
        compares.setId(id);
        return compares;
    }
}
