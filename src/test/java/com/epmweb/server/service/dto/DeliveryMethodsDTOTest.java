package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class DeliveryMethodsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryMethodsDTO.class);
        DeliveryMethodsDTO deliveryMethodsDTO1 = new DeliveryMethodsDTO();
        deliveryMethodsDTO1.setId(1L);
        DeliveryMethodsDTO deliveryMethodsDTO2 = new DeliveryMethodsDTO();
        assertThat(deliveryMethodsDTO1).isNotEqualTo(deliveryMethodsDTO2);
        deliveryMethodsDTO2.setId(deliveryMethodsDTO1.getId());
        assertThat(deliveryMethodsDTO1).isEqualTo(deliveryMethodsDTO2);
        deliveryMethodsDTO2.setId(2L);
        assertThat(deliveryMethodsDTO1).isNotEqualTo(deliveryMethodsDTO2);
        deliveryMethodsDTO1.setId(null);
        assertThat(deliveryMethodsDTO1).isNotEqualTo(deliveryMethodsDTO2);
    }
}
