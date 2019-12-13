package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PackageTypesMapperTest {

    private PackageTypesMapper packageTypesMapper;

    @BeforeEach
    public void setUp() {
        packageTypesMapper = new PackageTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(packageTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(packageTypesMapper.fromId(null)).isNull();
    }
}
