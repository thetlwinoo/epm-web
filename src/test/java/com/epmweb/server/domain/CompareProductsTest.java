package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CompareProductsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompareProducts.class);
        CompareProducts compareProducts1 = new CompareProducts();
        compareProducts1.setId(1L);
        CompareProducts compareProducts2 = new CompareProducts();
        compareProducts2.setId(compareProducts1.getId());
        assertThat(compareProducts1).isEqualTo(compareProducts2);
        compareProducts2.setId(2L);
        assertThat(compareProducts1).isNotEqualTo(compareProducts2);
        compareProducts1.setId(null);
        assertThat(compareProducts1).isNotEqualTo(compareProducts2);
    }
}
