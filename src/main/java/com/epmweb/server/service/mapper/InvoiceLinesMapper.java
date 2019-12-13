package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.InvoiceLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InvoiceLines} and its DTO {@link InvoiceLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {PackageTypesMapper.class, StockItemsMapper.class, InvoicesMapper.class})
public interface InvoiceLinesMapper extends EntityMapper<InvoiceLinesDTO, InvoiceLines> {

    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "packageType.name", target = "packageTypeName")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "invoice.id", target = "invoiceId")
    InvoiceLinesDTO toDto(InvoiceLines invoiceLines);

    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "invoiceId", target = "invoice")
    InvoiceLines toEntity(InvoiceLinesDTO invoiceLinesDTO);

    default InvoiceLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceLines invoiceLines = new InvoiceLines();
        invoiceLines.setId(id);
        return invoiceLines;
    }
}
