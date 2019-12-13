package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SupplierTransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierTransactionsDTO.class);
        SupplierTransactionsDTO supplierTransactionsDTO1 = new SupplierTransactionsDTO();
        supplierTransactionsDTO1.setId(1L);
        SupplierTransactionsDTO supplierTransactionsDTO2 = new SupplierTransactionsDTO();
        assertThat(supplierTransactionsDTO1).isNotEqualTo(supplierTransactionsDTO2);
        supplierTransactionsDTO2.setId(supplierTransactionsDTO1.getId());
        assertThat(supplierTransactionsDTO1).isEqualTo(supplierTransactionsDTO2);
        supplierTransactionsDTO2.setId(2L);
        assertThat(supplierTransactionsDTO1).isNotEqualTo(supplierTransactionsDTO2);
        supplierTransactionsDTO1.setId(null);
        assertThat(supplierTransactionsDTO1).isNotEqualTo(supplierTransactionsDTO2);
    }
}
