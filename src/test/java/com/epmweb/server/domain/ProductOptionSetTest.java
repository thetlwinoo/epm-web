package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductOptionSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionSet.class);
        ProductOptionSet productOptionSet1 = new ProductOptionSet();
        productOptionSet1.setId(1L);
        ProductOptionSet productOptionSet2 = new ProductOptionSet();
        productOptionSet2.setId(productOptionSet1.getId());
        assertThat(productOptionSet1).isEqualTo(productOptionSet2);
        productOptionSet2.setId(2L);
        assertThat(productOptionSet1).isNotEqualTo(productOptionSet2);
        productOptionSet1.setId(null);
        assertThat(productOptionSet1).isNotEqualTo(productOptionSet2);
    }
}
