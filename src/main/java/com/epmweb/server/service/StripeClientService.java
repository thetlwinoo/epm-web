package com.epmweb.server.service;

import com.stripe.model.Customer;

import java.util.Map;

public interface StripeClientService {
    Customer createCustomer(String token, String email) throws Exception;

    Map<String, Object> chargeNewCard(String token, double amount, Number orderId) throws Exception;

    Map<String, Object> chargeCustomerCard(String customerId, int amount) throws Exception;
}
