package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductBrandTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrand.class);
        ProductBrand productBrand1 = new ProductBrand();
        productBrand1.setId(1L);
        ProductBrand productBrand2 = new ProductBrand();
        productBrand2.setId(productBrand1.getId());
        assertThat(productBrand1).isEqualTo(productBrand2);
        productBrand2.setId(2L);
        assertThat(productBrand1).isNotEqualTo(productBrand2);
        productBrand1.setId(null);
        assertThat(productBrand1).isNotEqualTo(productBrand2);
    }
}
