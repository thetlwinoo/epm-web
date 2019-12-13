package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ReviewLinesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewLines.class);
        ReviewLines reviewLines1 = new ReviewLines();
        reviewLines1.setId(1L);
        ReviewLines reviewLines2 = new ReviewLines();
        reviewLines2.setId(reviewLines1.getId());
        assertThat(reviewLines1).isEqualTo(reviewLines2);
        reviewLines2.setId(2L);
        assertThat(reviewLines1).isNotEqualTo(reviewLines2);
        reviewLines1.setId(null);
        assertThat(reviewLines1).isNotEqualTo(reviewLines2);
    }
}
