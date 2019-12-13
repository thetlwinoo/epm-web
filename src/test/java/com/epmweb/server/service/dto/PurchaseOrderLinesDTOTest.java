package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PurchaseOrderLinesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderLinesDTO.class);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO1 = new PurchaseOrderLinesDTO();
        purchaseOrderLinesDTO1.setId(1L);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO2 = new PurchaseOrderLinesDTO();
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO2.setId(purchaseOrderLinesDTO1.getId());
        assertThat(purchaseOrderLinesDTO1).isEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO2.setId(2L);
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO1.setId(null);
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
    }
}
