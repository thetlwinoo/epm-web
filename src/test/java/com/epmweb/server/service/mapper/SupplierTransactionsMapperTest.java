package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SupplierTransactionsMapperTest {

    private SupplierTransactionsMapper supplierTransactionsMapper;

    @BeforeEach
    public void setUp() {
        supplierTransactionsMapper = new SupplierTransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(supplierTransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supplierTransactionsMapper.fromId(null)).isNull();
    }
}
