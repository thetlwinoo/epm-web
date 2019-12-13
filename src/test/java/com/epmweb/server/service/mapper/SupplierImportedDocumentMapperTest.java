package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SupplierImportedDocumentMapperTest {

    private SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    @BeforeEach
    public void setUp() {
        supplierImportedDocumentMapper = new SupplierImportedDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(supplierImportedDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supplierImportedDocumentMapper.fromId(null)).isNull();
    }
}
