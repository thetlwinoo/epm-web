package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.repository.PhotosExtendRepository;
import com.epmweb.server.service.PhotosExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the PhotosExtendResource REST controller.
 *
 * @see PhotosExtendResource
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class PhotosExtendResourceIT {

    private MockMvc restMockMvc;
    private final PhotosExtendService photosExtendService;
    private final PhotosExtendRepository photosExtendRepository;

    public PhotosExtendResourceIT(PhotosExtendService photosExtendService, PhotosExtendRepository photosExtendRepository) {
        this.photosExtendService = photosExtendService;
        this.photosExtendRepository = photosExtendRepository;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PhotosExtendResource photosExtendResource = new PhotosExtendResource(photosExtendService,photosExtendRepository);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(photosExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/photos-extend/default-action"))
            .andExpect(status().isOk());
    }
}
