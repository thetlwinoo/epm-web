package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class OrderLinesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLines.class);
        OrderLines orderLines1 = new OrderLines();
        orderLines1.setId(1L);
        OrderLines orderLines2 = new OrderLines();
        orderLines2.setId(orderLines1.getId());
        assertThat(orderLines1).isEqualTo(orderLines2);
        orderLines2.setId(2L);
        assertThat(orderLines1).isNotEqualTo(orderLines2);
        orderLines1.setId(null);
        assertThat(orderLines1).isNotEqualTo(orderLines2);
    }
}
