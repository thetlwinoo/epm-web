package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class WarrantyTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyTypesDTO.class);
        WarrantyTypesDTO warrantyTypesDTO1 = new WarrantyTypesDTO();
        warrantyTypesDTO1.setId(1L);
        WarrantyTypesDTO warrantyTypesDTO2 = new WarrantyTypesDTO();
        assertThat(warrantyTypesDTO1).isNotEqualTo(warrantyTypesDTO2);
        warrantyTypesDTO2.setId(warrantyTypesDTO1.getId());
        assertThat(warrantyTypesDTO1).isEqualTo(warrantyTypesDTO2);
        warrantyTypesDTO2.setId(2L);
        assertThat(warrantyTypesDTO1).isNotEqualTo(warrantyTypesDTO2);
        warrantyTypesDTO1.setId(null);
        assertThat(warrantyTypesDTO1).isNotEqualTo(warrantyTypesDTO2);
    }
}
