package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.service.UploadTransactionsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the UploadTransactionsExtendResource REST controller.
 *
 * @see UploadTransactionsExtendResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class UploadTransactionsExtendResourceIT {

    private MockMvc restMockMvc;
    private final UploadTransactionsExtendService uploadTransactionsExtendService;

    public UploadTransactionsExtendResourceIT(UploadTransactionsExtendService uploadTransactionsExtendService) {
        this.uploadTransactionsExtendService = uploadTransactionsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        UploadTransactionsExtendResource uploadTransactionsExtendResource = new UploadTransactionsExtendResource(uploadTransactionsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(uploadTransactionsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/upload-transactions-extend/default-action"))
            .andExpect(status().isOk());
    }
}
