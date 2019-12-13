package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.UploadTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UploadTransactions} and its DTO {@link UploadTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, UploadActionTypesMapper.class})
public interface UploadTransactionsMapper extends EntityMapper<UploadTransactionsDTO, UploadTransactions> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "actionType.id", target = "actionTypeId")
    @Mapping(source = "actionType.name", target = "actionTypeName")
    UploadTransactionsDTO toDto(UploadTransactions uploadTransactions);

    @Mapping(target = "importDocumentLists", ignore = true)
    @Mapping(target = "removeImportDocumentList", ignore = true)
    @Mapping(target = "stockItemTempLists", ignore = true)
    @Mapping(target = "removeStockItemTempList", ignore = true)
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "actionTypeId", target = "actionType")
    UploadTransactions toEntity(UploadTransactionsDTO uploadTransactionsDTO);

    default UploadTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        UploadTransactions uploadTransactions = new UploadTransactions();
        uploadTransactions.setId(id);
        return uploadTransactions;
    }
}
