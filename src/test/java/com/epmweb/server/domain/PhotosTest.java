package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PhotosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Photos.class);
        Photos photos1 = new Photos();
        photos1.setId(1L);
        Photos photos2 = new Photos();
        photos2.setId(photos1.getId());
        assertThat(photos1).isEqualTo(photos2);
        photos2.setId(2L);
        assertThat(photos1).isNotEqualTo(photos2);
        photos1.setId(null);
        assertThat(photos1).isNotEqualTo(photos2);
    }
}
