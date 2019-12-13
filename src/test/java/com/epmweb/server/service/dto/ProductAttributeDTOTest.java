package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductAttributeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeDTO.class);
        ProductAttributeDTO productAttributeDTO1 = new ProductAttributeDTO();
        productAttributeDTO1.setId(1L);
        ProductAttributeDTO productAttributeDTO2 = new ProductAttributeDTO();
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
        productAttributeDTO2.setId(productAttributeDTO1.getId());
        assertThat(productAttributeDTO1).isEqualTo(productAttributeDTO2);
        productAttributeDTO2.setId(2L);
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
        productAttributeDTO1.setId(null);
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
    }
}
