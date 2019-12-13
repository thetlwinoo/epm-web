package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ShipMethodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipMethodDTO.class);
        ShipMethodDTO shipMethodDTO1 = new ShipMethodDTO();
        shipMethodDTO1.setId(1L);
        ShipMethodDTO shipMethodDTO2 = new ShipMethodDTO();
        assertThat(shipMethodDTO1).isNotEqualTo(shipMethodDTO2);
        shipMethodDTO2.setId(shipMethodDTO1.getId());
        assertThat(shipMethodDTO1).isEqualTo(shipMethodDTO2);
        shipMethodDTO2.setId(2L);
        assertThat(shipMethodDTO1).isNotEqualTo(shipMethodDTO2);
        shipMethodDTO1.setId(null);
        assertThat(shipMethodDTO1).isNotEqualTo(shipMethodDTO2);
    }
}
