package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductSetDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDetailsDTO.class);
        ProductSetDetailsDTO productSetDetailsDTO1 = new ProductSetDetailsDTO();
        productSetDetailsDTO1.setId(1L);
        ProductSetDetailsDTO productSetDetailsDTO2 = new ProductSetDetailsDTO();
        assertThat(productSetDetailsDTO1).isNotEqualTo(productSetDetailsDTO2);
        productSetDetailsDTO2.setId(productSetDetailsDTO1.getId());
        assertThat(productSetDetailsDTO1).isEqualTo(productSetDetailsDTO2);
        productSetDetailsDTO2.setId(2L);
        assertThat(productSetDetailsDTO1).isNotEqualTo(productSetDetailsDTO2);
        productSetDetailsDTO1.setId(null);
        assertThat(productSetDetailsDTO1).isNotEqualTo(productSetDetailsDTO2);
    }
}
