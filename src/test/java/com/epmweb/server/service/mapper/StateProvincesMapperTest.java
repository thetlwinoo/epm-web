package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StateProvincesMapperTest {

    private StateProvincesMapper stateProvincesMapper;

    @BeforeEach
    public void setUp() {
        stateProvincesMapper = new StateProvincesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stateProvincesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stateProvincesMapper.fromId(null)).isNull();
    }
}
