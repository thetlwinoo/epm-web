package com.epmweb.server.web.rest;

import com.epmweb.server.service.PaypalClientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * PaypalClientResource controller
 */
@RestController
@RequestMapping("/api")
public class PaypalClientResource {

    private final Logger log = LoggerFactory.getLogger(PaypalClientResource.class);
    private final PaypalClientService paypalClientService;

    public PaypalClientResource(PaypalClientService paypalClientService) {
        this.paypalClientService = paypalClientService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/paypal-client/make/payment")
    public Map<String, Object> makePayment(@Valid @RequestBody String payload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("sum");
        JsonNode jsonNode2 = actualObj.get("returnUrl");
        JsonNode jsonNode3 = actualObj.get("cancelUrl");
        String totalPrice = jsonNode1.textValue();
        String returnUrl = jsonNode2.textValue();
        String cancelUrl = jsonNode3.textValue();
        return paypalClientService.createPayment(totalPrice, returnUrl, cancelUrl);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/paypal-client/complete/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> completePayment(@Valid @RequestBody String payload) throws PayPalRESTException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("paymentId");
        JsonNode jsonNode2 = actualObj.get("payerId");
        JsonNode jsonNode3 = actualObj.get("orderId");
        String paymentId = jsonNode1.textValue();
        String payerId = jsonNode2.textValue();
        Number orderId = jsonNode3.numberValue();
        Map<String, Object> result = paypalClientService.completePayment(paymentId, payerId, orderId);
        return result;
    }

}
