package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DangerousGoodsMapperTest {

    private DangerousGoodsMapper dangerousGoodsMapper;

    @BeforeEach
    public void setUp() {
        dangerousGoodsMapper = new DangerousGoodsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(dangerousGoodsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dangerousGoodsMapper.fromId(null)).isNull();
    }
}
