package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TransactionTypesMapperTest {

    private TransactionTypesMapper transactionTypesMapper;

    @BeforeEach
    public void setUp() {
        transactionTypesMapper = new TransactionTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(transactionTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionTypesMapper.fromId(null)).isNull();
    }
}
