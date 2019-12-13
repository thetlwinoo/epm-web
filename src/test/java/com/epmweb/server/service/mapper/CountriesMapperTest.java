package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CountriesMapperTest {

    private CountriesMapper countriesMapper;

    @BeforeEach
    public void setUp() {
        countriesMapper = new CountriesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(countriesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(countriesMapper.fromId(null)).isNull();
    }
}
