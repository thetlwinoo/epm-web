package com.epmweb.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockItemsExtendRepository extends StockItemsRepository {
    @Query(value = "SELECT COUNT(B.*) FROM products A INNER JOIN stock_items B ON A.id = B.product_id WHERE A.supplier_id = :supplierId", nativeQuery = true)
    Long findAllCount(@Param("supplierId") Long supplierId);

    @Query(value = "SELECT COUNT(B.*) FROM products A INNER JOIN stock_items B ON A.id = B.product_id WHERE A.supplier_id = :supplierId AND B.active_ind = :activeInd", nativeQuery = true)
    Long findAllCountByActive(@Param("supplierId") Long supplierId, @Param("activeInd") Boolean activeInd);

    @Query(value = "SELECT COUNT(B.*) FROM products A INNER JOIN stock_items B ON A.id = B.product_id WHERE A.supplier_id = :supplierId AND B.quantity_on_hand = 0", nativeQuery = true)
    Long findAllCountSoldOut(@Param("supplierId") Long supplierId);
}
