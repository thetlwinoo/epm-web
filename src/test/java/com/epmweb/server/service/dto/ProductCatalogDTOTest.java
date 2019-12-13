package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ProductCatalogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCatalogDTO.class);
        ProductCatalogDTO productCatalogDTO1 = new ProductCatalogDTO();
        productCatalogDTO1.setId(1L);
        ProductCatalogDTO productCatalogDTO2 = new ProductCatalogDTO();
        assertThat(productCatalogDTO1).isNotEqualTo(productCatalogDTO2);
        productCatalogDTO2.setId(productCatalogDTO1.getId());
        assertThat(productCatalogDTO1).isEqualTo(productCatalogDTO2);
        productCatalogDTO2.setId(2L);
        assertThat(productCatalogDTO1).isNotEqualTo(productCatalogDTO2);
        productCatalogDTO1.setId(null);
        assertThat(productCatalogDTO1).isNotEqualTo(productCatalogDTO2);
    }
}
