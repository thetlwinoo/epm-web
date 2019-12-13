package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CustomerCategoriesMapperTest {

    private CustomerCategoriesMapper customerCategoriesMapper;

    @BeforeEach
    public void setUp() {
        customerCategoriesMapper = new CustomerCategoriesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(customerCategoriesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerCategoriesMapper.fromId(null)).isNull();
    }
}
