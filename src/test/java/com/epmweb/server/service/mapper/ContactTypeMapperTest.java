package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContactTypeMapperTest {

    private ContactTypeMapper contactTypeMapper;

    @BeforeEach
    public void setUp() {
        contactTypeMapper = new ContactTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contactTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contactTypeMapper.fromId(null)).isNull();
    }
}
