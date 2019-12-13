package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SystemParametersMapperTest {

    private SystemParametersMapper systemParametersMapper;

    @BeforeEach
    public void setUp() {
        systemParametersMapper = new SystemParametersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(systemParametersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemParametersMapper.fromId(null)).isNull();
    }
}
