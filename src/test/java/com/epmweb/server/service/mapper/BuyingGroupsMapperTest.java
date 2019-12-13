package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BuyingGroupsMapperTest {

    private BuyingGroupsMapper buyingGroupsMapper;

    @BeforeEach
    public void setUp() {
        buyingGroupsMapper = new BuyingGroupsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(buyingGroupsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(buyingGroupsMapper.fromId(null)).isNull();
    }
}
