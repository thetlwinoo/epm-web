package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MaterialsMapperTest {

    private MaterialsMapper materialsMapper;

    @BeforeEach
    public void setUp() {
        materialsMapper = new MaterialsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(materialsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materialsMapper.fromId(null)).isNull();
    }
}
