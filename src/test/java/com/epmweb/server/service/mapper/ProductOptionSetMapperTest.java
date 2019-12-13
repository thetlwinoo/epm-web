package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductOptionSetMapperTest {

    private ProductOptionSetMapper productOptionSetMapper;

    @BeforeEach
    public void setUp() {
        productOptionSetMapper = new ProductOptionSetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productOptionSetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productOptionSetMapper.fromId(null)).isNull();
    }
}
