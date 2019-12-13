package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductCatalogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCatalog.class);
        ProductCatalog productCatalog1 = new ProductCatalog();
        productCatalog1.setId(1L);
        ProductCatalog productCatalog2 = new ProductCatalog();
        productCatalog2.setId(productCatalog1.getId());
        assertThat(productCatalog1).isEqualTo(productCatalog2);
        productCatalog2.setId(2L);
        assertThat(productCatalog1).isNotEqualTo(productCatalog2);
        productCatalog1.setId(null);
        assertThat(productCatalog1).isNotEqualTo(productCatalog2);
    }
}
