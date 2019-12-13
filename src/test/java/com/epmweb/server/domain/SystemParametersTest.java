package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SystemParametersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemParameters.class);
        SystemParameters systemParameters1 = new SystemParameters();
        systemParameters1.setId(1L);
        SystemParameters systemParameters2 = new SystemParameters();
        systemParameters2.setId(systemParameters1.getId());
        assertThat(systemParameters1).isEqualTo(systemParameters2);
        systemParameters2.setId(2L);
        assertThat(systemParameters1).isNotEqualTo(systemParameters2);
        systemParameters1.setId(null);
        assertThat(systemParameters1).isNotEqualTo(systemParameters2);
    }
}
