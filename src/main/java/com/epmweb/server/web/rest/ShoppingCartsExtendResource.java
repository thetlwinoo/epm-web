package com.epmweb.server.web.rest;

import com.epmweb.server.domain.ShoppingCarts;
import com.epmweb.server.service.ShoppingCartsExtendService;
import com.epmweb.server.service.dto.ShoppingCartsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * ShoppingCartsExtendResource controller
 */
@RestController
@RequestMapping("/api/shopping-carts-extend")
public class ShoppingCartsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsExtendResource.class);
    private final ShoppingCartsExtendService cartService;

    public ShoppingCartsExtendResource(ShoppingCartsExtendService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public ResponseEntity addToCart(@RequestBody String payload, Principal principal) throws IOException {
        System.out.println("Add to cart");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("productId");
        JsonNode jsonNode2 = actualObj.get("amount");
        Long requestProductId = jsonNode1.longValue();
        Integer requestProductQuantity = jsonNode2.intValue();
        ShoppingCartsDTO cart = cartService.addToCart(principal, requestProductId, requestProductQuantity);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/cart/reduce", method = RequestMethod.POST)
    public ResponseEntity reduceFormCart(@RequestBody String payload, Principal principal) throws IOException {
        System.out.println("reduce from cart");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("id");
        JsonNode jsonNode2 = actualObj.get("quantity");
        Long requestProductId = jsonNode1.longValue();
        Integer requestProductQuantity = jsonNode2.intValue();
        ShoppingCartsDTO cart = cartService.reduceFromCart(principal, requestProductId, requestProductQuantity);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public ResponseEntity fetchCart(Principal principal) {
        ShoppingCarts cart = cartService.fetchCart(principal);
        return new ResponseEntity<ShoppingCarts>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity removeFromCart(@RequestParam("id") Long id, Principal principal) {
        ShoppingCartsDTO cart = cartService.removeFromCart(principal, id);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/cart/confirm", method = RequestMethod.POST)
    public ResponseEntity confirmCart(@Valid @RequestBody ShoppingCarts cart, BindingResult bindingResult, Principal principal) {
        System.out.println("RequestBody -> " + cart.toString());
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Boolean result = cartService.confirmCart(principal, cart);
        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(Principal principal) {
        cartService.emptyCart(principal);
        return new ResponseEntity(HttpStatus.OK);
    }
}
