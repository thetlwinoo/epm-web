package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CompareProductsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompareProductsDTO.class);
        CompareProductsDTO compareProductsDTO1 = new CompareProductsDTO();
        compareProductsDTO1.setId(1L);
        CompareProductsDTO compareProductsDTO2 = new CompareProductsDTO();
        assertThat(compareProductsDTO1).isNotEqualTo(compareProductsDTO2);
        compareProductsDTO2.setId(compareProductsDTO1.getId());
        assertThat(compareProductsDTO1).isEqualTo(compareProductsDTO2);
        compareProductsDTO2.setId(2L);
        assertThat(compareProductsDTO1).isNotEqualTo(compareProductsDTO2);
        compareProductsDTO1.setId(null);
        assertThat(compareProductsDTO1).isNotEqualTo(compareProductsDTO2);
    }
}
