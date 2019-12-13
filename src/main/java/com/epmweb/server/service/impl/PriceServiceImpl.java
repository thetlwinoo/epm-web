package com.epmweb.server.service.impl;

import com.epmweb.server.domain.ShoppingCartItems;
import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.service.PriceService;
import com.epmweb.server.service.dto.ShoppingCartsDTO;
import com.epmweb.server.service.mapper.ShoppingCartsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
private final ShoppingCartsMapper shoppingCartsMapper;

    public PriceServiceImpl(ShoppingCartsMapper shoppingCartsMapper) {
        this.shoppingCartsMapper = shoppingCartsMapper;
    }

    @Override
    public ShoppingCarts calculatePrice(ShoppingCarts cart) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalCargoPrice = BigDecimal.ZERO;

        for (ShoppingCartItems i : cart.getCartItemLists()) {
            System.out.println("amount " + i.getQuantity());
            totalPrice = ((i.getStockItem().getUnitPrice().add(i.getStockItem().getRecommendedRetailPrice())).multiply(BigDecimal.valueOf(i.getQuantity()))).add(totalCargoPrice).add(totalPrice);
            totalCargoPrice = (i.getStockItem().getRecommendedRetailPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
        }

        //Applying discount percent if exists
        if (cart.getSpecialDeals() != null) {
            totalPrice = totalPrice.subtract((totalPrice.multiply(cart.getSpecialDeals().getDiscountPercentage())).divide(BigDecimal.valueOf(100)));
        }

        cart.setTotalPrice(totalPrice.setScale(2, RoundingMode.CEILING));
        cart.setTotalCargoPrice(totalCargoPrice.setScale(2,RoundingMode.CEILING));
        return cart;
    }
}
