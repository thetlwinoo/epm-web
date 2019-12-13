package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.CompareProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompareProducts} and its DTO {@link CompareProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, ComparesMapper.class})
public interface CompareProductsMapper extends EntityMapper<CompareProductsDTO, CompareProducts> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "compare.id", target = "compareId")
    CompareProductsDTO toDto(CompareProducts compareProducts);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "compareId", target = "compare")
    CompareProducts toEntity(CompareProductsDTO compareProductsDTO);

    default CompareProducts fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompareProducts compareProducts = new CompareProducts();
        compareProducts.setId(id);
        return compareProducts;
    }
}
