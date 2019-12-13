package com.epmweb.server.repository;

import com.epmweb.server.domain.ProductOption;

import java.util.List;

public interface ProductOptionExtendRepository extends ProductOptionRepository {
    List<ProductOption> findAllByProductOptionSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
