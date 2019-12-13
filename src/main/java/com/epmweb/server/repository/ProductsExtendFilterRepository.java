package com.epmweb.server.repository;

import com.epmweb.server.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsExtendFilterRepository extends JpaRepository<ProductCategory, Long> {
    @Query(value = "SELECT DISTINCT trim(tags) FROM products p WHERE p.tags <> '' AND p.tags LIKE %:keyword%", nativeQuery = true)
    List<String> getProductTags(@Param("keyword") String keyword);

    @Query(value = "SELECT DISTINCT A.* FROM product_category A LEFT JOIN products B ON A.id = B.product_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<ProductCategory> selectCategoriesByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT DISTINCT B.Color FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE B.Color IS NOT NULL AND B.Color <> '' AND (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<String> selectColorsByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT MIN(B.unit_price) as minprice,MAX(B.unit_price) as maxprice FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    Object selectPriceRangeByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "SELECT DISTINCT B.brand FROM product_sub_category A LEFT JOIN products B ON A.id = B.product_sub_category_id LEFT JOIN product_tags C ON B.id = C.product_id WHERE B.brand IS NOT NULL AND B.brand <> '' AND (:keyword = '' OR C.tag_name ILIKE :keyword) AND (:category = 0 OR A.product_category_id = :category)", nativeQuery = true)
    List<String> selectBrandsByTags(@Param("keyword") String keyword, @Param("category") Long category);

    @Query(value = "select id from product_sub_category where product_category_id = :categoryId", nativeQuery = true)
    List<Long> getSubCategoryIds(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT id FROM products p WHERE p.supplier_id = :supplierId", nativeQuery = true)
    List<Long> findIdsBySupplier(@Param("supplierId") Long supplierId);
}
