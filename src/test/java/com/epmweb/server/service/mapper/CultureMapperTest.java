package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CultureMapperTest {

    private CultureMapper cultureMapper;

    @BeforeEach
    public void setUp() {
        cultureMapper = new CultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cultureMapper.fromId(null)).isNull();
    }
}
