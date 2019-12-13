package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

public class CustomersMapperTest {

    private CustomersMapper customersMapper;

    @BeforeEach
    public void setUp() {
        customersMapper = new CustomersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(customersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customersMapper.fromId(null)).isNull();
    }
}
