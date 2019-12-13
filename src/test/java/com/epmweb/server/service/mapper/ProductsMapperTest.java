package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductsMapperTest {

    private ProductsMapper productsMapper;

    @BeforeEach
    public void setUp() {
        productsMapper = new ProductsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productsMapper.fromId(null)).isNull();
    }
}
