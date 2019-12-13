package com.epmweb.server.service;

import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.service.dto.ShoppingCartsDTO;

import java.security.Principal;

public interface ShoppingCartsExtendService {
    ShoppingCartsDTO addToCart(Principal principal, Long id, Integer quantity);

    ShoppingCarts fetchCart(Principal principal);

    ShoppingCartsDTO removeFromCart(Principal principal, Long id);

    ShoppingCartsDTO reduceFromCart(Principal principal, Long id, Integer quantity);

    Boolean confirmCart(Principal principal, ShoppingCarts cart);

    void emptyCart(Principal principal);
}
