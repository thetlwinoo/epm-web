package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CitiesMapperTest {

    private CitiesMapper citiesMapper;

    @BeforeEach
    public void setUp() {
        citiesMapper = new CitiesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(citiesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(citiesMapper.fromId(null)).isNull();
    }
}
