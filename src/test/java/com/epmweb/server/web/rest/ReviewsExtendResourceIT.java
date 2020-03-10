package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.service.ReviewsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ReviewsExtendResource REST controller.
 *
 * @see ReviewsExtendResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class ReviewsExtendResourceIT {

    private MockMvc restMockMvc;
    private final ReviewsExtendService reviewsExtendService;

    public ReviewsExtendResourceIT(ReviewsExtendService reviewsExtendService) {
        this.reviewsExtendService = reviewsExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ReviewsExtendResource reviewsExtendResource = new ReviewsExtendResource(reviewsExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(reviewsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/reviews-extend/default-action"))
            .andExpect(status().isOk());
    }
}
