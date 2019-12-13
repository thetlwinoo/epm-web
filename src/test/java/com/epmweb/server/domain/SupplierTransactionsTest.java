package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SupplierTransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierTransactions.class);
        SupplierTransactions supplierTransactions1 = new SupplierTransactions();
        supplierTransactions1.setId(1L);
        SupplierTransactions supplierTransactions2 = new SupplierTransactions();
        supplierTransactions2.setId(supplierTransactions1.getId());
        assertThat(supplierTransactions1).isEqualTo(supplierTransactions2);
        supplierTransactions2.setId(2L);
        assertThat(supplierTransactions1).isNotEqualTo(supplierTransactions2);
        supplierTransactions1.setId(null);
        assertThat(supplierTransactions1).isNotEqualTo(supplierTransactions2);
    }
}
