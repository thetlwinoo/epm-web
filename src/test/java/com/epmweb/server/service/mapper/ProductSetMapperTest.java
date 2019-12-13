package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductSetMapperTest {

    private ProductSetMapper productSetMapper;

    @BeforeEach
    public void setUp() {
        productSetMapper = new ProductSetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productSetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productSetMapper.fromId(null)).isNull();
    }
}
