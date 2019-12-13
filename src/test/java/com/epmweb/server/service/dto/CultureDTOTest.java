package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CultureDTO.class);
        CultureDTO cultureDTO1 = new CultureDTO();
        cultureDTO1.setId(1L);
        CultureDTO cultureDTO2 = new CultureDTO();
        assertThat(cultureDTO1).isNotEqualTo(cultureDTO2);
        cultureDTO2.setId(cultureDTO1.getId());
        assertThat(cultureDTO1).isEqualTo(cultureDTO2);
        cultureDTO2.setId(2L);
        assertThat(cultureDTO1).isNotEqualTo(cultureDTO2);
        cultureDTO1.setId(null);
        assertThat(cultureDTO1).isNotEqualTo(cultureDTO2);
    }
}
