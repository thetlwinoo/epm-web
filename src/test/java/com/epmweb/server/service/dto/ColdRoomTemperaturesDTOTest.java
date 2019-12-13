package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ColdRoomTemperaturesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColdRoomTemperaturesDTO.class);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO1 = new ColdRoomTemperaturesDTO();
        coldRoomTemperaturesDTO1.setId(1L);
        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO2 = new ColdRoomTemperaturesDTO();
        assertThat(coldRoomTemperaturesDTO1).isNotEqualTo(coldRoomTemperaturesDTO2);
        coldRoomTemperaturesDTO2.setId(coldRoomTemperaturesDTO1.getId());
        assertThat(coldRoomTemperaturesDTO1).isEqualTo(coldRoomTemperaturesDTO2);
        coldRoomTemperaturesDTO2.setId(2L);
        assertThat(coldRoomTemperaturesDTO1).isNotEqualTo(coldRoomTemperaturesDTO2);
        coldRoomTemperaturesDTO1.setId(null);
        assertThat(coldRoomTemperaturesDTO1).isNotEqualTo(coldRoomTemperaturesDTO2);
    }
}
