package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.service.ProductOptionExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ProductOptionExtendResource REST controller.
 *
 * @see ProductOptionExtendResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ProductOptionExtendResourceIT {

    private MockMvc restMockMvc;
    private final ProductOptionExtendService productOptionExtendService;

    public ProductOptionExtendResourceIT(ProductOptionExtendService productOptionExtendService) {
        this.productOptionExtendService = productOptionExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductOptionExtendResource productOptionExtendResource = new ProductOptionExtendResource(productOptionExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productOptionExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-option-extend/default-action"))
            .andExpect(status().isOk());
    }
}
