package com.epmweb.server.service;

import com.epmweb.server.domain.Wishlists;
import com.epmweb.server.service.dto.ProductsDTO;
import com.epmweb.server.service.dto.WishlistsDTO;

import java.security.Principal;
import java.util.List;

public interface WishlistsExtendService {
    WishlistsDTO addToWishlist(Principal principal, Long id);

    Wishlists fetchWishlist(Principal principal);

    List<ProductsDTO> fetchWishlistProducts(Principal principal);

    WishlistsDTO removeFromWishlist(Principal principal, Long id);

    void emptyWishlist(Principal principal);

    Boolean isInWishlist(Principal principal, Long id);
}
