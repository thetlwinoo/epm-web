package com.epmweb.server.service;

import com.epmweb.server.domain.ShoppingCarts;

public interface PriceService {
    ShoppingCarts calculatePrice(ShoppingCarts cart);
}
