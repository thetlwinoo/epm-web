package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class VehicleTemperaturesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTemperatures.class);
        VehicleTemperatures vehicleTemperatures1 = new VehicleTemperatures();
        vehicleTemperatures1.setId(1L);
        VehicleTemperatures vehicleTemperatures2 = new VehicleTemperatures();
        vehicleTemperatures2.setId(vehicleTemperatures1.getId());
        assertThat(vehicleTemperatures1).isEqualTo(vehicleTemperatures2);
        vehicleTemperatures2.setId(2L);
        assertThat(vehicleTemperatures1).isNotEqualTo(vehicleTemperatures2);
        vehicleTemperatures1.setId(null);
        assertThat(vehicleTemperatures1).isNotEqualTo(vehicleTemperatures2);
    }
}
