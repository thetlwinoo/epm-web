package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PurchaseOrdersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrdersDTO.class);
        PurchaseOrdersDTO purchaseOrdersDTO1 = new PurchaseOrdersDTO();
        purchaseOrdersDTO1.setId(1L);
        PurchaseOrdersDTO purchaseOrdersDTO2 = new PurchaseOrdersDTO();
        assertThat(purchaseOrdersDTO1).isNotEqualTo(purchaseOrdersDTO2);
        purchaseOrdersDTO2.setId(purchaseOrdersDTO1.getId());
        assertThat(purchaseOrdersDTO1).isEqualTo(purchaseOrdersDTO2);
        purchaseOrdersDTO2.setId(2L);
        assertThat(purchaseOrdersDTO1).isNotEqualTo(purchaseOrdersDTO2);
        purchaseOrdersDTO1.setId(null);
        assertThat(purchaseOrdersDTO1).isNotEqualTo(purchaseOrdersDTO2);
    }
}
