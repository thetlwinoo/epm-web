package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SystemParametersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemParametersDTO.class);
        SystemParametersDTO systemParametersDTO1 = new SystemParametersDTO();
        systemParametersDTO1.setId(1L);
        SystemParametersDTO systemParametersDTO2 = new SystemParametersDTO();
        assertThat(systemParametersDTO1).isNotEqualTo(systemParametersDTO2);
        systemParametersDTO2.setId(systemParametersDTO1.getId());
        assertThat(systemParametersDTO1).isEqualTo(systemParametersDTO2);
        systemParametersDTO2.setId(2L);
        assertThat(systemParametersDTO1).isNotEqualTo(systemParametersDTO2);
        systemParametersDTO1.setId(null);
        assertThat(systemParametersDTO1).isNotEqualTo(systemParametersDTO2);
    }
}
