package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class OrderLinesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLinesDTO.class);
        OrderLinesDTO orderLinesDTO1 = new OrderLinesDTO();
        orderLinesDTO1.setId(1L);
        OrderLinesDTO orderLinesDTO2 = new OrderLinesDTO();
        assertThat(orderLinesDTO1).isNotEqualTo(orderLinesDTO2);
        orderLinesDTO2.setId(orderLinesDTO1.getId());
        assertThat(orderLinesDTO1).isEqualTo(orderLinesDTO2);
        orderLinesDTO2.setId(2L);
        assertThat(orderLinesDTO1).isNotEqualTo(orderLinesDTO2);
        orderLinesDTO1.setId(null);
        assertThat(orderLinesDTO1).isNotEqualTo(orderLinesDTO2);
    }
}
