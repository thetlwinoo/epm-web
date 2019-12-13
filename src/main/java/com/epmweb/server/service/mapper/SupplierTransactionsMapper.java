package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.SupplierTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierTransactions} and its DTO {@link SupplierTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, TransactionTypesMapper.class, PurchaseOrdersMapper.class})
public interface SupplierTransactionsMapper extends EntityMapper<SupplierTransactionsDTO, SupplierTransactions> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "transactionType.name", target = "transactionTypeName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    SupplierTransactionsDTO toDto(SupplierTransactions supplierTransactions);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    SupplierTransactions toEntity(SupplierTransactionsDTO supplierTransactionsDTO);

    default SupplierTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierTransactions supplierTransactions = new SupplierTransactions();
        supplierTransactions.setId(id);
        return supplierTransactions;
    }
}
