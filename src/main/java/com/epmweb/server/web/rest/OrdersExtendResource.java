package com.epmweb.server.web.rest;

import com.epmweb.server.service.OrdersExtendService;
import com.epmweb.server.service.dto.OrdersDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * OrdersExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class OrdersExtendResource {

    private final Logger log = LoggerFactory.getLogger(OrdersExtendResource.class);
    private final OrdersExtendService ordersExtendService;

    public OrdersExtendResource(OrdersExtendService ordersExtendService) {
        this.ordersExtendService = ordersExtendService;
    }

    @RequestMapping(value = "/orders-extend/order/count", method = RequestMethod.GET)
    public ResponseEntity getAllOrdersCount(Principal principal) {
        Integer orderCount = ordersExtendService.getAllOrdersCount(principal);
        return new ResponseEntity<Integer>(orderCount, HttpStatus.OK);
    }


    @RequestMapping(value = "/orders-extend/order", method = RequestMethod.POST)
    public ResponseEntity postOrder(@Valid @RequestBody OrdersDTO ordersDTO, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        OrdersDTO saveOrder = ordersExtendService.postOrder(principal, ordersDTO);
        return new ResponseEntity<OrdersDTO>(saveOrder, HttpStatus.OK);
    }
}
