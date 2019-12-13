package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UnitMeasureMapperTest {

    private UnitMeasureMapper unitMeasureMapper;

    @BeforeEach
    public void setUp() {
        unitMeasureMapper = new UnitMeasureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(unitMeasureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(unitMeasureMapper.fromId(null)).isNull();
    }
}
