package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.service.WishlistsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the WishlistsExtendResource REST controller.
 *
 * @see WishlistsExtendResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class WishlistsExtendResourceIT {

    private MockMvc restMockMvc;
    private final WishlistsExtendService wishlistsExtendService;

    public WishlistsExtendResourceIT(WishlistsExtendService wishlistsExtendService) {
        this.wishlistsExtendService = wishlistsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        WishlistsExtendResource wishlistsExtendResource = new WishlistsExtendResource(wishlistsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(wishlistsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/wishlists-extend/default-action"))
            .andExpect(status().isOk());
    }
}
