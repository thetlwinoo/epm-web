package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductChoiceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductChoiceDTO.class);
        ProductChoiceDTO productChoiceDTO1 = new ProductChoiceDTO();
        productChoiceDTO1.setId(1L);
        ProductChoiceDTO productChoiceDTO2 = new ProductChoiceDTO();
        assertThat(productChoiceDTO1).isNotEqualTo(productChoiceDTO2);
        productChoiceDTO2.setId(productChoiceDTO1.getId());
        assertThat(productChoiceDTO1).isEqualTo(productChoiceDTO2);
        productChoiceDTO2.setId(2L);
        assertThat(productChoiceDTO1).isNotEqualTo(productChoiceDTO2);
        productChoiceDTO1.setId(null);
        assertThat(productChoiceDTO1).isNotEqualTo(productChoiceDTO2);
    }
}
