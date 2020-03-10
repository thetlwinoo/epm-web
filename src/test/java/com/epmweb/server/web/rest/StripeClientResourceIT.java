package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.service.StripeClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the StripeClientResource REST controller.
 *
 * @see StripeClientResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StripeClientResourceIT {

    private MockMvc restMockMvc;
    private final StripeClientService stripeClientService;

    public StripeClientResourceIT(StripeClientService stripeClientService) {
        this.stripeClientService = stripeClientService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StripeClientResource stripeClientResource = new StripeClientResource(stripeClientService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(stripeClientResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/stripe-client/default-action"))
            .andExpect(status().isOk());
    }
}
