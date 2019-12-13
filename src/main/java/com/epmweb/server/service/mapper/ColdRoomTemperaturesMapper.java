package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ColdRoomTemperaturesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ColdRoomTemperatures} and its DTO {@link ColdRoomTemperaturesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ColdRoomTemperaturesMapper extends EntityMapper<ColdRoomTemperaturesDTO, ColdRoomTemperatures> {



    default ColdRoomTemperatures fromId(Long id) {
        if (id == null) {
            return null;
        }
        ColdRoomTemperatures coldRoomTemperatures = new ColdRoomTemperatures();
        coldRoomTemperatures.setId(id);
        return coldRoomTemperatures;
    }
}
