package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.repository.StockItemsExtendRepository;
import com.epmweb.server.service.ProductsExtendService;
import com.epmweb.server.service.StockItemsExtendService;
import com.epmweb.server.service.StockItemsQueryService;
import com.epmweb.server.service.SuppliersExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the StockItemsExtendResource REST controller.
 *
 * @see StockItemsExtendResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StockItemsExtendResourceIT {

    private MockMvc restMockMvc;
    private final StockItemsExtendService stockItemsExtendService;
    private final StockItemsQueryService stockItemsQueryService;
    private final SuppliersExtendService suppliersExtendService;
    private final ProductsExtendService productsExtendService;
    private final StockItemsExtendRepository stockItemsExtendRepository;

    public StockItemsExtendResourceIT(StockItemsExtendService stockItemsExtendService, StockItemsQueryService stockItemsQueryService, SuppliersExtendService suppliersExtendService, ProductsExtendService productsExtendService, StockItemsExtendRepository stockItemsExtendRepository) {
        this.stockItemsExtendService = stockItemsExtendService;
        this.stockItemsQueryService = stockItemsQueryService;
        this.suppliersExtendService = suppliersExtendService;
        this.productsExtendService = productsExtendService;
        this.stockItemsExtendRepository = stockItemsExtendRepository;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StockItemsExtendResource stockItemsExtendResource = new StockItemsExtendResource(stockItemsExtendService,stockItemsQueryService,suppliersExtendService,productsExtendService,stockItemsExtendRepository);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(stockItemsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/stock-items-extend/default-action"))
            .andExpect(status().isOk());
    }
}
