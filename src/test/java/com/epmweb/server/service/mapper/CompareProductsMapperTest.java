package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompareProductsMapperTest {

    private CompareProductsMapper compareProductsMapper;

    @BeforeEach
    public void setUp() {
        compareProductsMapper = new CompareProductsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(compareProductsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(compareProductsMapper.fromId(null)).isNull();
    }
}
