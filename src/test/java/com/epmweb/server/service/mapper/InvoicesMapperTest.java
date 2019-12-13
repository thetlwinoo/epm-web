package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InvoicesMapperTest {

    private InvoicesMapper invoicesMapper;

    @BeforeEach
    public void setUp() {
        invoicesMapper = new InvoicesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(invoicesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(invoicesMapper.fromId(null)).isNull();
    }
}
