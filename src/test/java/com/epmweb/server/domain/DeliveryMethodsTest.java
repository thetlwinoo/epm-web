package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class DeliveryMethodsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryMethods.class);
        DeliveryMethods deliveryMethods1 = new DeliveryMethods();
        deliveryMethods1.setId(1L);
        DeliveryMethods deliveryMethods2 = new DeliveryMethods();
        deliveryMethods2.setId(deliveryMethods1.getId());
        assertThat(deliveryMethods1).isEqualTo(deliveryMethods2);
        deliveryMethods2.setId(2L);
        assertThat(deliveryMethods1).isNotEqualTo(deliveryMethods2);
        deliveryMethods1.setId(null);
        assertThat(deliveryMethods1).isNotEqualTo(deliveryMethods2);
    }
}
