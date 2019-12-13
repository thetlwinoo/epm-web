package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductTagsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTagsDTO.class);
        ProductTagsDTO productTagsDTO1 = new ProductTagsDTO();
        productTagsDTO1.setId(1L);
        ProductTagsDTO productTagsDTO2 = new ProductTagsDTO();
        assertThat(productTagsDTO1).isNotEqualTo(productTagsDTO2);
        productTagsDTO2.setId(productTagsDTO1.getId());
        assertThat(productTagsDTO1).isEqualTo(productTagsDTO2);
        productTagsDTO2.setId(2L);
        assertThat(productTagsDTO1).isNotEqualTo(productTagsDTO2);
        productTagsDTO1.setId(null);
        assertThat(productTagsDTO1).isNotEqualTo(productTagsDTO2);
    }
}
