package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductAttributeSetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeSetDTO.class);
        ProductAttributeSetDTO productAttributeSetDTO1 = new ProductAttributeSetDTO();
        productAttributeSetDTO1.setId(1L);
        ProductAttributeSetDTO productAttributeSetDTO2 = new ProductAttributeSetDTO();
        assertThat(productAttributeSetDTO1).isNotEqualTo(productAttributeSetDTO2);
        productAttributeSetDTO2.setId(productAttributeSetDTO1.getId());
        assertThat(productAttributeSetDTO1).isEqualTo(productAttributeSetDTO2);
        productAttributeSetDTO2.setId(2L);
        assertThat(productAttributeSetDTO1).isNotEqualTo(productAttributeSetDTO2);
        productAttributeSetDTO1.setId(null);
        assertThat(productAttributeSetDTO1).isNotEqualTo(productAttributeSetDTO2);
    }
}
