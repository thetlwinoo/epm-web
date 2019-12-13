package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PersonPhoneMapperTest {

    private PersonPhoneMapper personPhoneMapper;

    @BeforeEach
    public void setUp() {
        personPhoneMapper = new PersonPhoneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(personPhoneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(personPhoneMapper.fromId(null)).isNull();
    }
}
