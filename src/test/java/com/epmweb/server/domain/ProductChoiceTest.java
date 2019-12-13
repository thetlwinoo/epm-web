package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductChoiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductChoice.class);
        ProductChoice productChoice1 = new ProductChoice();
        productChoice1.setId(1L);
        ProductChoice productChoice2 = new ProductChoice();
        productChoice2.setId(productChoice1.getId());
        assertThat(productChoice1).isEqualTo(productChoice2);
        productChoice2.setId(2L);
        assertThat(productChoice1).isNotEqualTo(productChoice2);
        productChoice1.setId(null);
        assertThat(productChoice1).isNotEqualTo(productChoice2);
    }
}
