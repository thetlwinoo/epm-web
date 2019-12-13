package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CustomerCategoriesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategoriesDTO.class);
        CustomerCategoriesDTO customerCategoriesDTO1 = new CustomerCategoriesDTO();
        customerCategoriesDTO1.setId(1L);
        CustomerCategoriesDTO customerCategoriesDTO2 = new CustomerCategoriesDTO();
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO2.setId(customerCategoriesDTO1.getId());
        assertThat(customerCategoriesDTO1).isEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO2.setId(2L);
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO1.setId(null);
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
    }
}
