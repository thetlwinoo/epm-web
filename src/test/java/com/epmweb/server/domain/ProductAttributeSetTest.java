package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductAttributeSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeSet.class);
        ProductAttributeSet productAttributeSet1 = new ProductAttributeSet();
        productAttributeSet1.setId(1L);
        ProductAttributeSet productAttributeSet2 = new ProductAttributeSet();
        productAttributeSet2.setId(productAttributeSet1.getId());
        assertThat(productAttributeSet1).isEqualTo(productAttributeSet2);
        productAttributeSet2.setId(2L);
        assertThat(productAttributeSet1).isNotEqualTo(productAttributeSet2);
        productAttributeSet1.setId(null);
        assertThat(productAttributeSet1).isNotEqualTo(productAttributeSet2);
    }
}
