package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductSetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDTO.class);
        ProductSetDTO productSetDTO1 = new ProductSetDTO();
        productSetDTO1.setId(1L);
        ProductSetDTO productSetDTO2 = new ProductSetDTO();
        assertThat(productSetDTO1).isNotEqualTo(productSetDTO2);
        productSetDTO2.setId(productSetDTO1.getId());
        assertThat(productSetDTO1).isEqualTo(productSetDTO2);
        productSetDTO2.setId(2L);
        assertThat(productSetDTO1).isNotEqualTo(productSetDTO2);
        productSetDTO1.setId(null);
        assertThat(productSetDTO1).isNotEqualTo(productSetDTO2);
    }
}
