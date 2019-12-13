package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StockItemsMapperTest {

    private StockItemsMapper stockItemsMapper;

    @BeforeEach
    public void setUp() {
        stockItemsMapper = new StockItemsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(stockItemsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockItemsMapper.fromId(null)).isNull();
    }
}
