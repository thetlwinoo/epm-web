package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ProductTagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTags} and its DTO {@link ProductTagsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class})
public interface ProductTagsMapper extends EntityMapper<ProductTagsDTO, ProductTags> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductTagsDTO toDto(ProductTags productTags);

    @Mapping(source = "productId", target = "product")
    ProductTags toEntity(ProductTagsDTO productTagsDTO);

    default ProductTags fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductTags productTags = new ProductTags();
        productTags.setId(id);
        return productTags;
    }
}
