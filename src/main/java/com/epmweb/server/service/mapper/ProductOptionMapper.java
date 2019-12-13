package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ProductOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOption} and its DTO {@link ProductOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductOptionSetMapper.class, SuppliersMapper.class})
public interface ProductOptionMapper extends EntityMapper<ProductOptionDTO, ProductOption> {

    @Mapping(source = "productOptionSet.id", target = "productOptionSetId")
    @Mapping(source = "productOptionSet.value", target = "productOptionSetValue")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    ProductOptionDTO toDto(ProductOption productOption);

    @Mapping(source = "productOptionSetId", target = "productOptionSet")
    @Mapping(source = "supplierId", target = "supplier")
    ProductOption toEntity(ProductOptionDTO productOptionDTO);

    default ProductOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductOption productOption = new ProductOption();
        productOption.setId(id);
        return productOption;
    }
}
