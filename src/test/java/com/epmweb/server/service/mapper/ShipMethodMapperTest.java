package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ShipMethodMapperTest {

    private ShipMethodMapper shipMethodMapper;

    @BeforeEach
    public void setUp() {
        shipMethodMapper = new ShipMethodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(shipMethodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shipMethodMapper.fromId(null)).isNull();
    }
}
