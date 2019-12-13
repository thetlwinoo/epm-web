package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ReviewsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reviews.class);
        Reviews reviews1 = new Reviews();
        reviews1.setId(1L);
        Reviews reviews2 = new Reviews();
        reviews2.setId(reviews1.getId());
        assertThat(reviews1).isEqualTo(reviews2);
        reviews2.setId(2L);
        assertThat(reviews1).isNotEqualTo(reviews2);
        reviews1.setId(null);
        assertThat(reviews1).isNotEqualTo(reviews2);
    }
}
