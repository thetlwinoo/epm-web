package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class OrdersMapperTest {

    private OrdersMapper ordersMapper;

    @BeforeEach
    public void setUp() {
        ordersMapper = new OrdersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(ordersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ordersMapper.fromId(null)).isNull();
    }
}
