package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductSetDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDetails.class);
        ProductSetDetails productSetDetails1 = new ProductSetDetails();
        productSetDetails1.setId(1L);
        ProductSetDetails productSetDetails2 = new ProductSetDetails();
        productSetDetails2.setId(productSetDetails1.getId());
        assertThat(productSetDetails1).isEqualTo(productSetDetails2);
        productSetDetails2.setId(2L);
        assertThat(productSetDetails1).isNotEqualTo(productSetDetails2);
        productSetDetails1.setId(null);
        assertThat(productSetDetails1).isNotEqualTo(productSetDetails2);
    }
}
