package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.CustomerTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerTransactions} and its DTO {@link CustomerTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomersMapper.class, PaymentTransactionsMapper.class, TransactionTypesMapper.class, InvoicesMapper.class})
public interface CustomerTransactionsMapper extends EntityMapper<CustomerTransactionsDTO, CustomerTransactions> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "paymentTransaction.id", target = "paymentTransactionId")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "transactionType.name", target = "transactionTypeName")
    @Mapping(source = "invoice.id", target = "invoiceId")
    CustomerTransactionsDTO toDto(CustomerTransactions customerTransactions);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "paymentTransactionId", target = "paymentTransaction")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    @Mapping(source = "invoiceId", target = "invoice")
    CustomerTransactions toEntity(CustomerTransactionsDTO customerTransactionsDTO);

    default CustomerTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerTransactions customerTransactions = new CustomerTransactions();
        customerTransactions.setId(id);
        return customerTransactions;
    }
}
