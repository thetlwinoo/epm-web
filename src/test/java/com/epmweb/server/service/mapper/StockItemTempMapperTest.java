package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockItemTempMapperTest {

    private StockItemTempMapper stockItemTempMapper;

    @BeforeEach
    public void setUp() {
        stockItemTempMapper = new StockItemTempMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockItemTempMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockItemTempMapper.fromId(null)).isNull();
    }
}
