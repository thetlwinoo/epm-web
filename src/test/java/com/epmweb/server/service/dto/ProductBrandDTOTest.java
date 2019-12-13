package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductBrandDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrandDTO.class);
        ProductBrandDTO productBrandDTO1 = new ProductBrandDTO();
        productBrandDTO1.setId(1L);
        ProductBrandDTO productBrandDTO2 = new ProductBrandDTO();
        assertThat(productBrandDTO1).isNotEqualTo(productBrandDTO2);
        productBrandDTO2.setId(productBrandDTO1.getId());
        assertThat(productBrandDTO1).isEqualTo(productBrandDTO2);
        productBrandDTO2.setId(2L);
        assertThat(productBrandDTO1).isNotEqualTo(productBrandDTO2);
        productBrandDTO1.setId(null);
        assertThat(productBrandDTO1).isNotEqualTo(productBrandDTO2);
    }
}
