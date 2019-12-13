package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InvoiceLinesMapperTest {

    private InvoiceLinesMapper invoiceLinesMapper;

    @BeforeEach
    public void setUp() {
        invoiceLinesMapper = new InvoiceLinesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(invoiceLinesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(invoiceLinesMapper.fromId(null)).isNull();
    }
}
