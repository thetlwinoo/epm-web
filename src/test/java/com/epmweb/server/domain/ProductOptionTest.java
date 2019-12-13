package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductOptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOption.class);
        ProductOption productOption1 = new ProductOption();
        productOption1.setId(1L);
        ProductOption productOption2 = new ProductOption();
        productOption2.setId(productOption1.getId());
        assertThat(productOption1).isEqualTo(productOption2);
        productOption2.setId(2L);
        assertThat(productOption1).isNotEqualTo(productOption2);
        productOption1.setId(null);
        assertThat(productOption1).isNotEqualTo(productOption2);
    }
}
