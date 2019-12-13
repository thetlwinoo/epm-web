package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SupplierCategoriesMapperTest {

    private SupplierCategoriesMapper supplierCategoriesMapper;

    @BeforeEach
    public void setUp() {
        supplierCategoriesMapper = new SupplierCategoriesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(supplierCategoriesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supplierCategoriesMapper.fromId(null)).isNull();
    }
}
