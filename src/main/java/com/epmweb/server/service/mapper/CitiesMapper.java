package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.CitiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cities} and its DTO {@link CitiesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateProvincesMapper.class})
public interface CitiesMapper extends EntityMapper<CitiesDTO, Cities> {

    @Mapping(source = "stateProvince.id", target = "stateProvinceId")
    @Mapping(source = "stateProvince.name", target = "stateProvinceName")
    CitiesDTO toDto(Cities cities);

    @Mapping(source = "stateProvinceId", target = "stateProvince")
    Cities toEntity(CitiesDTO citiesDTO);

    default Cities fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cities cities = new Cities();
        cities.setId(id);
        return cities;
    }
}
