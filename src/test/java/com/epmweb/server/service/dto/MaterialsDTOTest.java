package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class MaterialsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialsDTO.class);
        MaterialsDTO materialsDTO1 = new MaterialsDTO();
        materialsDTO1.setId(1L);
        MaterialsDTO materialsDTO2 = new MaterialsDTO();
        assertThat(materialsDTO1).isNotEqualTo(materialsDTO2);
        materialsDTO2.setId(materialsDTO1.getId());
        assertThat(materialsDTO1).isEqualTo(materialsDTO2);
        materialsDTO2.setId(2L);
        assertThat(materialsDTO1).isNotEqualTo(materialsDTO2);
        materialsDTO1.setId(null);
        assertThat(materialsDTO1).isNotEqualTo(materialsDTO2);
    }
}
