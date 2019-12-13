package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WarrantyTypesMapperTest {

    private WarrantyTypesMapper warrantyTypesMapper;

    @BeforeEach
    public void setUp() {
        warrantyTypesMapper = new WarrantyTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(warrantyTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(warrantyTypesMapper.fromId(null)).isNull();
    }
}
