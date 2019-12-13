package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WishlistProductsMapperTest {

    private WishlistProductsMapper wishlistProductsMapper;

    @BeforeEach
    public void setUp() {
        wishlistProductsMapper = new WishlistProductsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(wishlistProductsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wishlistProductsMapper.fromId(null)).isNull();
    }
}
