package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductOptionSetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionSetDTO.class);
        ProductOptionSetDTO productOptionSetDTO1 = new ProductOptionSetDTO();
        productOptionSetDTO1.setId(1L);
        ProductOptionSetDTO productOptionSetDTO2 = new ProductOptionSetDTO();
        assertThat(productOptionSetDTO1).isNotEqualTo(productOptionSetDTO2);
        productOptionSetDTO2.setId(productOptionSetDTO1.getId());
        assertThat(productOptionSetDTO1).isEqualTo(productOptionSetDTO2);
        productOptionSetDTO2.setId(2L);
        assertThat(productOptionSetDTO1).isNotEqualTo(productOptionSetDTO2);
        productOptionSetDTO1.setId(null);
        assertThat(productOptionSetDTO1).isNotEqualTo(productOptionSetDTO2);
    }
}
