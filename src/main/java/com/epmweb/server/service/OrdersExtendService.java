package com.epmweb.server.service;

import com.epmweb.server.service.dto.OrdersDTO;

import java.security.Principal;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO);
}
