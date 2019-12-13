package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PurchaseOrdersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrders.class);
        PurchaseOrders purchaseOrders1 = new PurchaseOrders();
        purchaseOrders1.setId(1L);
        PurchaseOrders purchaseOrders2 = new PurchaseOrders();
        purchaseOrders2.setId(purchaseOrders1.getId());
        assertThat(purchaseOrders1).isEqualTo(purchaseOrders2);
        purchaseOrders2.setId(2L);
        assertThat(purchaseOrders1).isNotEqualTo(purchaseOrders2);
        purchaseOrders1.setId(null);
        assertThat(purchaseOrders1).isNotEqualTo(purchaseOrders2);
    }
}
