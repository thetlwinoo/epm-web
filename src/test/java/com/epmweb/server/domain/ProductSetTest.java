package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSet.class);
        ProductSet productSet1 = new ProductSet();
        productSet1.setId(1L);
        ProductSet productSet2 = new ProductSet();
        productSet2.setId(productSet1.getId());
        assertThat(productSet1).isEqualTo(productSet2);
        productSet2.setId(2L);
        assertThat(productSet1).isNotEqualTo(productSet2);
        productSet1.setId(null);
        assertThat(productSet1).isNotEqualTo(productSet2);
    }
}
