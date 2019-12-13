package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UploadTransactionsMapperTest {

    private UploadTransactionsMapper uploadTransactionsMapper;

    @BeforeEach
    public void setUp() {
        uploadTransactionsMapper = new UploadTransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(uploadTransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uploadTransactionsMapper.fromId(null)).isNull();
    }
}
