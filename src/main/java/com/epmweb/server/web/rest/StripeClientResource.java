package com.epmweb.server.web.rest;

import com.epmweb.server.service.StripeClientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * StripeClientResource controller
 */
@RestController
@RequestMapping("/api")
public class StripeClientResource {

    private final Logger log = LoggerFactory.getLogger(StripeClientResource.class);
    private final StripeClientService stripeClientService;

    public StripeClientResource(StripeClientService stripeClientService) {
        this.stripeClientService = stripeClientService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/stripe-client/charge", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> chargeCard(@Valid @RequestBody String payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("token");
        JsonNode jsonNode2 = actualObj.get("amount");
        JsonNode jsonNode3 = actualObj.get("orderId");
        String token = jsonNode1.textValue();
        Double amount = jsonNode2.doubleValue();
        Number orderId = jsonNode3.numberValue();

        return this.stripeClientService.chargeNewCard(token, amount, orderId);
    }
}
