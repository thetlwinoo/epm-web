package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PeopleMapperTest {

    private PeopleMapper peopleMapper;

    @BeforeEach
    public void setUp() {
        peopleMapper = new PeopleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(peopleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(peopleMapper.fromId(null)).isNull();
    }
}
