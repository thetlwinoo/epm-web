package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PaymentTransactionsMapperTest {

    private PaymentTransactionsMapper paymentTransactionsMapper;

    @BeforeEach
    public void setUp() {
        paymentTransactionsMapper = new PaymentTransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(paymentTransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentTransactionsMapper.fromId(null)).isNull();
    }
}
