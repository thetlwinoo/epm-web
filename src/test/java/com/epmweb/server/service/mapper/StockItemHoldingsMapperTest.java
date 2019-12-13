package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockItemHoldingsMapperTest {

    private StockItemHoldingsMapper stockItemHoldingsMapper;

    @BeforeEach
    public void setUp() {
        stockItemHoldingsMapper = new StockItemHoldingsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockItemHoldingsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockItemHoldingsMapper.fromId(null)).isNull();
    }
}
