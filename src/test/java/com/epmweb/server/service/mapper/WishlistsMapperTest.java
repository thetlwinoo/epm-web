package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WishlistsMapperTest {

    private WishlistsMapper wishlistsMapper;

    @BeforeEach
    public void setUp() {
        wishlistsMapper = new WishlistsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(wishlistsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wishlistsMapper.fromId(null)).isNull();
    }
}
