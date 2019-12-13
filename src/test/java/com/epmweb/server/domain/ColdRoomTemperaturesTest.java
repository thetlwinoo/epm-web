package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ColdRoomTemperaturesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColdRoomTemperatures.class);
        ColdRoomTemperatures coldRoomTemperatures1 = new ColdRoomTemperatures();
        coldRoomTemperatures1.setId(1L);
        ColdRoomTemperatures coldRoomTemperatures2 = new ColdRoomTemperatures();
        coldRoomTemperatures2.setId(coldRoomTemperatures1.getId());
        assertThat(coldRoomTemperatures1).isEqualTo(coldRoomTemperatures2);
        coldRoomTemperatures2.setId(2L);
        assertThat(coldRoomTemperatures1).isNotEqualTo(coldRoomTemperatures2);
        coldRoomTemperatures1.setId(null);
        assertThat(coldRoomTemperatures1).isNotEqualTo(coldRoomTemperatures2);
    }
}
