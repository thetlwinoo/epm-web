package com.epmweb.server.service;

import java.util.Map;

public interface PaypalClientService {
    Map<String, Object> createPayment(String sum, String returnUrl, String cancelUrl);

    Map<String, Object> completePayment(String paymentId, String payerId, Number orderId);
}
