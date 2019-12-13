package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductCategoryMapperTest {

    private ProductCategoryMapper productCategoryMapper;

    @BeforeEach
    public void setUp() {
        productCategoryMapper = new ProductCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productCategoryMapper.fromId(null)).isNull();
    }
}
