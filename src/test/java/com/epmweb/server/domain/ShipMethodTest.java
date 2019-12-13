package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ShipMethodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipMethod.class);
        ShipMethod shipMethod1 = new ShipMethod();
        shipMethod1.setId(1L);
        ShipMethod shipMethod2 = new ShipMethod();
        shipMethod2.setId(shipMethod1.getId());
        assertThat(shipMethod1).isEqualTo(shipMethod2);
        shipMethod2.setId(2L);
        assertThat(shipMethod1).isNotEqualTo(shipMethod2);
        shipMethod1.setId(null);
        assertThat(shipMethod1).isNotEqualTo(shipMethod2);
    }
}
