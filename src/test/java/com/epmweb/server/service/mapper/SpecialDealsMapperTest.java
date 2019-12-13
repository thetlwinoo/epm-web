package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SpecialDealsMapperTest {

    private SpecialDealsMapper specialDealsMapper;

    @BeforeEach
    public void setUp() {
        specialDealsMapper = new SpecialDealsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(specialDealsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(specialDealsMapper.fromId(null)).isNull();
    }
}
