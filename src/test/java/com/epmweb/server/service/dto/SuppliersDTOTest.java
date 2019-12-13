package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SuppliersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuppliersDTO.class);
        SuppliersDTO suppliersDTO1 = new SuppliersDTO();
        suppliersDTO1.setId(1L);
        SuppliersDTO suppliersDTO2 = new SuppliersDTO();
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
        suppliersDTO2.setId(suppliersDTO1.getId());
        assertThat(suppliersDTO1).isEqualTo(suppliersDTO2);
        suppliersDTO2.setId(2L);
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
        suppliersDTO1.setId(null);
        assertThat(suppliersDTO1).isNotEqualTo(suppliersDTO2);
    }
}
