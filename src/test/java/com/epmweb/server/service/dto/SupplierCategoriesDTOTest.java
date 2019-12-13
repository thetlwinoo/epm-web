package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SupplierCategoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierCategoriesDTO.class);
        SupplierCategoriesDTO supplierCategoriesDTO1 = new SupplierCategoriesDTO();
        supplierCategoriesDTO1.setId(1L);
        SupplierCategoriesDTO supplierCategoriesDTO2 = new SupplierCategoriesDTO();
        assertThat(supplierCategoriesDTO1).isNotEqualTo(supplierCategoriesDTO2);
        supplierCategoriesDTO2.setId(supplierCategoriesDTO1.getId());
        assertThat(supplierCategoriesDTO1).isEqualTo(supplierCategoriesDTO2);
        supplierCategoriesDTO2.setId(2L);
        assertThat(supplierCategoriesDTO1).isNotEqualTo(supplierCategoriesDTO2);
        supplierCategoriesDTO1.setId(null);
        assertThat(supplierCategoriesDTO1).isNotEqualTo(supplierCategoriesDTO2);
    }
}
