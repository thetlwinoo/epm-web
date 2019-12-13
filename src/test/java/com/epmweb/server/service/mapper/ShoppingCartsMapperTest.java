package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ShoppingCartsMapperTest {

    private ShoppingCartsMapper shoppingCartsMapper;

    @BeforeEach
    public void setUp() {
        shoppingCartsMapper = new ShoppingCartsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(shoppingCartsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shoppingCartsMapper.fromId(null)).isNull();
    }
}
