package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ReviewLinesMapperTest {

    private ReviewLinesMapper reviewLinesMapper;

    @BeforeEach
    public void setUp() {
        reviewLinesMapper = new ReviewLinesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(reviewLinesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reviewLinesMapper.fromId(null)).isNull();
    }
}
