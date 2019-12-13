package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SupplierCategoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierCategories.class);
        SupplierCategories supplierCategories1 = new SupplierCategories();
        supplierCategories1.setId(1L);
        SupplierCategories supplierCategories2 = new SupplierCategories();
        supplierCategories2.setId(supplierCategories1.getId());
        assertThat(supplierCategories1).isEqualTo(supplierCategories2);
        supplierCategories2.setId(2L);
        assertThat(supplierCategories1).isNotEqualTo(supplierCategories2);
        supplierCategories1.setId(null);
        assertThat(supplierCategories1).isNotEqualTo(supplierCategories2);
    }
}
