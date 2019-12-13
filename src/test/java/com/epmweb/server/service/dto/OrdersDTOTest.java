package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class OrdersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersDTO.class);
        OrdersDTO ordersDTO1 = new OrdersDTO();
        ordersDTO1.setId(1L);
        OrdersDTO ordersDTO2 = new OrdersDTO();
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO2.setId(ordersDTO1.getId());
        assertThat(ordersDTO1).isEqualTo(ordersDTO2);
        ordersDTO2.setId(2L);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO1.setId(null);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
    }
}
