package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PhoneNumberTypeMapperTest {

    private PhoneNumberTypeMapper phoneNumberTypeMapper;

    @BeforeEach
    public void setUp() {
        phoneNumberTypeMapper = new PhoneNumberTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(phoneNumberTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(phoneNumberTypeMapper.fromId(null)).isNull();
    }
}
