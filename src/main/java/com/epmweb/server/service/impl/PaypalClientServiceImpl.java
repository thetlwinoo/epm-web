package com.epmweb.server.service.impl;

import com.epmweb.server.domain.Orders;
import com.epmweb.server.repository.OrdersRepository;
import com.epmweb.server.repository.PaymentTransactionsRepository;
import com.epmweb.server.service.PaymentTransactionsService;
import com.epmweb.server.service.PaypalClientService;
import com.epmweb.server.service.dto.PaymentTransactionsDTO;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epmweb.server.config.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaypalClientServiceImpl implements PaypalClientService {

    private final Logger log = LoggerFactory.getLogger(PaypalClientServiceImpl.class);
    private final PaymentTransactionsRepository paymentTransactionsRepository;
    private final PaymentTransactionsService paymentTransactionsService;
    private final OrdersRepository ordersRepository;


    public PaypalClientServiceImpl(PaymentTransactionsRepository paymentTransactionsRepository, PaymentTransactionsService paymentTransactionsService, OrdersRepository ordersRepository) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
        this.paymentTransactionsService = paymentTransactionsService;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Map<String, Object> createPayment(String sum, String returnUrl, String cancelUrl) {
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("SGD");
        amount.setTotal(sum);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(Constants.PAYPAL_CLIENT_ID, Constants.PAYPAL_CLIENT_SECRET, "sandbox");
            createdPayment = payment.create(context);
            if (createdPayment != null) {
                List<Links> links = createdPayment.getLinks();
                for (Links link : links) {
                    if (link.getRel().equals("approval_url")) {
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    @Override
    @Transactional
    public Map<String, Object> completePayment(String paymentId, String payerId, Number orderId) {
        Map<String, Object> response = new HashMap<String, Object>();
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            APIContext context = new APIContext(Constants.PAYPAL_CLIENT_ID, Constants.PAYPAL_CLIENT_SECRET, "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            String createdPaymentJSON = createdPayment.toJSON();
            if (createdPayment != null) {

                PaymentTransactionsDTO paymentTransactionsDTO = new PaymentTransactionsDTO();
                paymentTransactionsDTO.setReturnedCompletedPaymentData(createdPaymentJSON);
                paymentTransactionsDTO.setPaymentOnOrderId(orderId.longValue());
                paymentTransactionsService.save(paymentTransactionsDTO);

                Orders orders = ordersRepository.getOne(orderId.longValue());
                orders.setPaymentStatus(1);
                ordersRepository.save(orders);

                response.put("status", createdPayment.getState());
                //response.put("payment", createdPaymentJSON);
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }

        return response;
    }
}
