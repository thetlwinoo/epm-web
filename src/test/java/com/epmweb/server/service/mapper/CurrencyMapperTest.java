package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CurrencyMapperTest {

    private CurrencyMapper currencyMapper;

    @BeforeEach
    public void setUp() {
        currencyMapper = new CurrencyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(currencyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(currencyMapper.fromId(null)).isNull();
    }
}
