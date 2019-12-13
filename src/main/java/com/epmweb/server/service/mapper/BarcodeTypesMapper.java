package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.BarcodeTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BarcodeTypes} and its DTO {@link BarcodeTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BarcodeTypesMapper extends EntityMapper<BarcodeTypesDTO, BarcodeTypes> {



    default BarcodeTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        BarcodeTypes barcodeTypes = new BarcodeTypes();
        barcodeTypes.setId(id);
        return barcodeTypes;
    }
}
