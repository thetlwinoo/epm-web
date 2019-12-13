package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductOptionMapperTest {

    private ProductOptionMapper productOptionMapper;

    @BeforeEach
    public void setUp() {
        productOptionMapper = new ProductOptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productOptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productOptionMapper.fromId(null)).isNull();
    }
}
