package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.PackageTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PackageTypes} and its DTO {@link PackageTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PackageTypesMapper extends EntityMapper<PackageTypesDTO, PackageTypes> {



    default PackageTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        PackageTypes packageTypes = new PackageTypes();
        packageTypes.setId(id);
        return packageTypes;
    }
}
