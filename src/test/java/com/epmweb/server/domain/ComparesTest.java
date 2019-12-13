package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ComparesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compares.class);
        Compares compares1 = new Compares();
        compares1.setId(1L);
        Compares compares2 = new Compares();
        compares2.setId(compares1.getId());
        assertThat(compares1).isEqualTo(compares2);
        compares2.setId(2L);
        assertThat(compares1).isNotEqualTo(compares2);
        compares1.setId(null);
        assertThat(compares1).isNotEqualTo(compares2);
    }
}
