package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocument.class);
        ProductDocument productDocument1 = new ProductDocument();
        productDocument1.setId(1L);
        ProductDocument productDocument2 = new ProductDocument();
        productDocument2.setId(productDocument1.getId());
        assertThat(productDocument1).isEqualTo(productDocument2);
        productDocument2.setId(2L);
        assertThat(productDocument1).isNotEqualTo(productDocument2);
        productDocument1.setId(null);
        assertThat(productDocument1).isNotEqualTo(productDocument2);
    }
}
