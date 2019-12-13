package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderLinesMapperTest {

    private OrderLinesMapper orderLinesMapper;

    @BeforeEach
    public void setUp() {
        orderLinesMapper = new OrderLinesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(orderLinesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderLinesMapper.fromId(null)).isNull();
    }
}
