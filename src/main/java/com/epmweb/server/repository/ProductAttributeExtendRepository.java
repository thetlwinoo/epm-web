package com.epmweb.server.repository;

import com.epmweb.server.domain.ProductAttribute;

import java.util.List;

public interface ProductAttributeExtendRepository extends ProductAttributeRepository {
    List<ProductAttribute> findAllByProductAttributeSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
