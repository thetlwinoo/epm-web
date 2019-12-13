package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CustomerTransactionsMapperTest {

    private CustomerTransactionsMapper customerTransactionsMapper;

    @BeforeEach
    public void setUp() {
        customerTransactionsMapper = new CustomerTransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(customerTransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerTransactionsMapper.fromId(null)).isNull();
    }
}
