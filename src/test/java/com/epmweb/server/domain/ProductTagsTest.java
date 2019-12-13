package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductTagsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTags.class);
        ProductTags productTags1 = new ProductTags();
        productTags1.setId(1L);
        ProductTags productTags2 = new ProductTags();
        productTags2.setId(productTags1.getId());
        assertThat(productTags1).isEqualTo(productTags2);
        productTags2.setId(2L);
        assertThat(productTags1).isNotEqualTo(productTags2);
        productTags1.setId(null);
        assertThat(productTags1).isNotEqualTo(productTags2);
    }
}
