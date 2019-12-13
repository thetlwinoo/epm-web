package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductDocumentMapperTest {

    private ProductDocumentMapper productDocumentMapper;

    @BeforeEach
    public void setUp() {
        productDocumentMapper = new ProductDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(productDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productDocumentMapper.fromId(null)).isNull();
    }
}
