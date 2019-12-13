package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class VehicleTemperaturesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTemperaturesDTO.class);
        VehicleTemperaturesDTO vehicleTemperaturesDTO1 = new VehicleTemperaturesDTO();
        vehicleTemperaturesDTO1.setId(1L);
        VehicleTemperaturesDTO vehicleTemperaturesDTO2 = new VehicleTemperaturesDTO();
        assertThat(vehicleTemperaturesDTO1).isNotEqualTo(vehicleTemperaturesDTO2);
        vehicleTemperaturesDTO2.setId(vehicleTemperaturesDTO1.getId());
        assertThat(vehicleTemperaturesDTO1).isEqualTo(vehicleTemperaturesDTO2);
        vehicleTemperaturesDTO2.setId(2L);
        assertThat(vehicleTemperaturesDTO1).isNotEqualTo(vehicleTemperaturesDTO2);
        vehicleTemperaturesDTO1.setId(null);
        assertThat(vehicleTemperaturesDTO1).isNotEqualTo(vehicleTemperaturesDTO2);
    }
}
