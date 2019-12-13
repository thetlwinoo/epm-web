package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SpecialDealsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialDeals.class);
        SpecialDeals specialDeals1 = new SpecialDeals();
        specialDeals1.setId(1L);
        SpecialDeals specialDeals2 = new SpecialDeals();
        specialDeals2.setId(specialDeals1.getId());
        assertThat(specialDeals1).isEqualTo(specialDeals2);
        specialDeals2.setId(2L);
        assertThat(specialDeals1).isNotEqualTo(specialDeals2);
        specialDeals1.setId(null);
        assertThat(specialDeals1).isNotEqualTo(specialDeals2);
    }
}
