package com.epmweb.server.repository;

import com.epmweb.server.domain.Orders;

import java.util.List;

public interface OrdersExtendRepository extends OrdersRepository {
    List<Orders> findAllByCustomerIdOrderByLastEditedWhenDesc(Long id);
    Integer countAllByCustomerId(Long id);
}
