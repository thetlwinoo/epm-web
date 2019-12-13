package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ProductAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAttribute} and its DTO {@link ProductAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductAttributeSetMapper.class, SuppliersMapper.class})
public interface ProductAttributeMapper extends EntityMapper<ProductAttributeDTO, ProductAttribute> {

    @Mapping(source = "productAttributeSet.id", target = "productAttributeSetId")
    @Mapping(source = "productAttributeSet.name", target = "productAttributeSetName")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    ProductAttributeDTO toDto(ProductAttribute productAttribute);

    @Mapping(source = "productAttributeSetId", target = "productAttributeSet")
    @Mapping(source = "supplierId", target = "supplier")
    ProductAttribute toEntity(ProductAttributeDTO productAttributeDTO);

    default ProductAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(id);
        return productAttribute;
    }
}
