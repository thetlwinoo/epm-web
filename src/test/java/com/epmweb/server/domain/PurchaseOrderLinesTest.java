package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PurchaseOrderLinesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderLines.class);
        PurchaseOrderLines purchaseOrderLines1 = new PurchaseOrderLines();
        purchaseOrderLines1.setId(1L);
        PurchaseOrderLines purchaseOrderLines2 = new PurchaseOrderLines();
        purchaseOrderLines2.setId(purchaseOrderLines1.getId());
        assertThat(purchaseOrderLines1).isEqualTo(purchaseOrderLines2);
        purchaseOrderLines2.setId(2L);
        assertThat(purchaseOrderLines1).isNotEqualTo(purchaseOrderLines2);
        purchaseOrderLines1.setId(null);
        assertThat(purchaseOrderLines1).isNotEqualTo(purchaseOrderLines2);
    }
}
