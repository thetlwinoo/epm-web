package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ComparesMapperTest {

    private ComparesMapper comparesMapper;

    @BeforeEach
    public void setUp() {
        comparesMapper = new ComparesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(comparesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(comparesMapper.fromId(null)).isNull();
    }
}
