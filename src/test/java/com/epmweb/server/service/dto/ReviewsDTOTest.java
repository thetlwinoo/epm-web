package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ReviewsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewsDTO.class);
        ReviewsDTO reviewsDTO1 = new ReviewsDTO();
        reviewsDTO1.setId(1L);
        ReviewsDTO reviewsDTO2 = new ReviewsDTO();
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
        reviewsDTO2.setId(reviewsDTO1.getId());
        assertThat(reviewsDTO1).isEqualTo(reviewsDTO2);
        reviewsDTO2.setId(2L);
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
        reviewsDTO1.setId(null);
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
    }
}
