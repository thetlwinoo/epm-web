package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ColdRoomTemperaturesMapperTest {

    private ColdRoomTemperaturesMapper coldRoomTemperaturesMapper;

    @BeforeEach
    public void setUp() {
        coldRoomTemperaturesMapper = new ColdRoomTemperaturesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(coldRoomTemperaturesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coldRoomTemperaturesMapper.fromId(null)).isNull();
    }
}
