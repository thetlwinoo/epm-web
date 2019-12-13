package com.epmweb.server.service;

import com.epmweb.server.domain.Products;
import com.epmweb.server.service.dto.ProductCategoryDTO;
import com.epmweb.server.service.dto.ProductsDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsExtendService {
    List<ProductsDTO> findAllByProductCategory(Pageable pageable, Long productSubCategoryId);

    List<ProductsDTO> findTop18ByOrderByLastEditedWhenDesc();

    List<ProductsDTO> findTop12ByOrderBySellCountDesc();

    List<ProductsDTO> findTop12ByOrderBySellCountDescCacheRefresh();

    List<ProductsDTO> getRelatedProducts(Long productSubCategoryId, Long id);

    List<ProductsDTO> searchProducts(String keyword, Integer page, Integer size);

    List<ProductsDTO> searchProductsAll(String keyword);

    List<Long> getSubCategoryList(Long categoryId);

    List<ProductCategoryDTO> getRelatedCategories(String keyword, Long category);

    List<String> getRelatedColors(String keyword, Long category);

    Object getRelatedPriceRange(String keyword, Long category);

    List<String> getRelatedBrands(String keyword, Long category);

    ProductsDTO save(Products products, String serverUrl);

    List<Long> getProductIdsBySupplier(Long supplierId);
}
