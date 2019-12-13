package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CurrencyRateMapperTest {

    private CurrencyRateMapper currencyRateMapper;

    @BeforeEach
    public void setUp() {
        currencyRateMapper = new CurrencyRateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(currencyRateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(currencyRateMapper.fromId(null)).isNull();
    }
}
