package com.epmweb.server.service.impl;

import com.epmweb.server.domain.*;
import com.epmweb.server.repository.*;
import com.epmweb.server.service.OrdersExtendService;
import com.epmweb.server.service.dto.OrdersDTO;
import com.epmweb.server.service.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class OrdersExtendServiceImpl implements OrdersExtendService {

    private final Logger log = LoggerFactory.getLogger(OrdersExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final AddressesRepository addressesRepository;
    private final OrdersMapper ordersMapper;
    private final UserRepository userRepository;

    public OrdersExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, AddressesRepository addressesRepository, OrdersMapper ordersMapper, UserRepository userRepository) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.addressesRepository = addressesRepository;
        this.ordersMapper = ordersMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Integer getAllOrdersCount(Principal principal) {
        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        Customers customer = customersExtendRepository.findCustomersByUserId(userOptional.get().getId());
        return ordersExtendRepository.countAllByCustomerId(customer.getId());
    }

    @Override
    public OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO) {
        People people = getUserFromPrinciple(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Customers customer = customersExtendRepository.findCustomersByUserId(userOptional.get().getId());

        Set<ShoppingCartItems> cartItems = cart.getCartItemLists();

        Orders saveOrder = new Orders();

        Calendar calendar = Calendar.getInstance();
        saveOrder.setOrderDate(Instant.now());
        saveOrder.setDueDate(Instant.now());
        saveOrder.setShipDate(Instant.now());
        Addresses billToAddress = addressesRepository.getOne(ordersDTO.getBillToAddressId());
        saveOrder.setBillToAddress(billToAddress);
        Addresses shipToAddress = addressesRepository.getOne(ordersDTO.getShipToAddressId());
        saveOrder.setShipToAddress(shipToAddress);

        for (ShoppingCartItems i : cartItems) {
            //increase sell count on the product
            i.getStockItem().setSellCount(i.getStockItem().getSellCount() + i.getQuantity());

            OrderLines orderLines = new OrderLines();
            orderLines.setQuantity(i.getQuantity());
            orderLines.setOrder(saveOrder);
            orderLines.setStockItem(i.getStockItem());
            orderLines.setUnitPrice(i.getStockItem().getUnitPrice());
            saveOrder.getOrderLineLists().add(orderLines);
        }

        saveOrder.setPaymentStatus(0);
        saveOrder.setOrderFlag(0);
        saveOrder.setFrieight(cart.getTotalCargoPrice());
        saveOrder.setCustomer(customer);
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
        saveOrder.setTotalDue(cart.getTotalPrice());
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
//        Long nextSerial = ordersExtendRepository.getNextSeriesId();

        saveOrder = ordersExtendRepository.save(saveOrder);
        saveOrder.setOrderNumber("SO" + saveOrder.getId());
        ordersExtendRepository.save(saveOrder);
        return ordersMapper.toDto(saveOrder);
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }
        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByEmailAddress(userOptional.get().getEmail());
        if (!people.isPresent()) {
            throw new IllegalArgumentException("People not found");
        }
        return people.get();
    }
}
