package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CustomerCategoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategories.class);
        CustomerCategories customerCategories1 = new CustomerCategories();
        customerCategories1.setId(1L);
        CustomerCategories customerCategories2 = new CustomerCategories();
        customerCategories2.setId(customerCategories1.getId());
        assertThat(customerCategories1).isEqualTo(customerCategories2);
        customerCategories2.setId(2L);
        assertThat(customerCategories1).isNotEqualTo(customerCategories2);
        customerCategories1.setId(null);
        assertThat(customerCategories1).isNotEqualTo(customerCategories2);
    }
}
