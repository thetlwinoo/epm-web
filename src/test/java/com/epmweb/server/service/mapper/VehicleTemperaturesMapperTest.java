package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class VehicleTemperaturesMapperTest {

    private VehicleTemperaturesMapper vehicleTemperaturesMapper;

    @BeforeEach
    public void setUp() {
        vehicleTemperaturesMapper = new VehicleTemperaturesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(vehicleTemperaturesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vehicleTemperaturesMapper.fromId(null)).isNull();
    }
}
