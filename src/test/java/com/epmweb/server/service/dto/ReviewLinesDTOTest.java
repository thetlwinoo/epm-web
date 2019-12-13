package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ReviewLinesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewLinesDTO.class);
        ReviewLinesDTO reviewLinesDTO1 = new ReviewLinesDTO();
        reviewLinesDTO1.setId(1L);
        ReviewLinesDTO reviewLinesDTO2 = new ReviewLinesDTO();
        assertThat(reviewLinesDTO1).isNotEqualTo(reviewLinesDTO2);
        reviewLinesDTO2.setId(reviewLinesDTO1.getId());
        assertThat(reviewLinesDTO1).isEqualTo(reviewLinesDTO2);
        reviewLinesDTO2.setId(2L);
        assertThat(reviewLinesDTO1).isNotEqualTo(reviewLinesDTO2);
        reviewLinesDTO1.setId(null);
        assertThat(reviewLinesDTO1).isNotEqualTo(reviewLinesDTO2);
    }
}
