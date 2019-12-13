package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ShoppingCartItemsMapperTest {

    private ShoppingCartItemsMapper shoppingCartItemsMapper;

    @BeforeEach
    public void setUp() {
        shoppingCartItemsMapper = new ShoppingCartItemsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(shoppingCartItemsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shoppingCartItemsMapper.fromId(null)).isNull();
    }
}
