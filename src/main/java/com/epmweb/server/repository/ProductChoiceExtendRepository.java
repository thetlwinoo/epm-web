package com.epmweb.server.repository;

import com.epmweb.server.domain.ProductChoice;

import java.util.List;

public interface ProductChoiceExtendRepository extends ProductChoiceRepository {
    List<ProductChoice> findAllByProductCategoryId(Long categoryId);
}
