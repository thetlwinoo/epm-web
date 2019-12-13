package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ComparesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComparesDTO.class);
        ComparesDTO comparesDTO1 = new ComparesDTO();
        comparesDTO1.setId(1L);
        ComparesDTO comparesDTO2 = new ComparesDTO();
        assertThat(comparesDTO1).isNotEqualTo(comparesDTO2);
        comparesDTO2.setId(comparesDTO1.getId());
        assertThat(comparesDTO1).isEqualTo(comparesDTO2);
        comparesDTO2.setId(2L);
        assertThat(comparesDTO1).isNotEqualTo(comparesDTO2);
        comparesDTO1.setId(null);
        assertThat(comparesDTO1).isNotEqualTo(comparesDTO2);
    }
}
