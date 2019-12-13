package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductAttributeMapperTest {

    private ProductAttributeMapper productAttributeMapper;

    @BeforeEach
    public void setUp() {
        productAttributeMapper = new ProductAttributeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productAttributeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productAttributeMapper.fromId(null)).isNull();
    }
}
