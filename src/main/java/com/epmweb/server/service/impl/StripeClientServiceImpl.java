package com.epmweb.server.service.impl;

import com.epmweb.server.domain.Orders;
import com.epmweb.server.repository.OrdersRepository;
import com.epmweb.server.repository.PaymentTransactionsRepository;
import com.epmweb.server.service.PaymentTransactionsService;
import com.epmweb.server.service.StripeClientService;
import com.epmweb.server.service.dto.PaymentTransactionsDTO;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class StripeClientServiceImpl implements StripeClientService {

    private final Logger log = LoggerFactory.getLogger(StripeClientServiceImpl.class);
    private final PaymentTransactionsRepository paymentTransactionsRepository;
    private final PaymentTransactionsService paymentTransactionsService;
    private final OrdersRepository ordersRepository;

    public StripeClientServiceImpl(PaymentTransactionsRepository paymentTransactionsRepository, PaymentTransactionsService paymentTransactionsService, OrdersRepository ordersRepository) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
        this.paymentTransactionsService = paymentTransactionsService;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

    @Override
    @Transactional
    public Map<String, Object> chargeNewCard(String token, double amount, Number orderId) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        Map<String, Object> response = new HashMap<String, Object>();

        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "SGD");
        chargeParams.put("source", token);
        try {
            Charge charge = Charge.create(chargeParams);
            String createdPaymentJSON = charge.toJson();

            if (charge != null) {
                PaymentTransactionsDTO paymentTransactionsDTO = new PaymentTransactionsDTO();
                paymentTransactionsDTO.setReturnedCompletedPaymentData(createdPaymentJSON);
                paymentTransactionsDTO.setPaymentOnOrderId(orderId.longValue());
                paymentTransactionsService.save(paymentTransactionsDTO);

                Orders orders = ordersRepository.getOne(orderId.longValue());
                orders.setPaymentStatus(1);
                ordersRepository.save(orders);

                response.put("status", charge.getStatus());
//                response.put("payment", createdPaymentJSON);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return response;
    }

    @Override
    public Map<String, Object> chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        Map<String, Object> response = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "SGD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);

        try {
            Charge charge = Charge.create(chargeParams);
            String obj = charge.toJson();

            if (charge != null) {
                response.put("status", "success");
                response.put("payment", obj);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }


        return response;
    }
}
