package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CustomerTransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTransactions.class);
        CustomerTransactions customerTransactions1 = new CustomerTransactions();
        customerTransactions1.setId(1L);
        CustomerTransactions customerTransactions2 = new CustomerTransactions();
        customerTransactions2.setId(customerTransactions1.getId());
        assertThat(customerTransactions1).isEqualTo(customerTransactions2);
        customerTransactions2.setId(2L);
        assertThat(customerTransactions1).isNotEqualTo(customerTransactions2);
        customerTransactions1.setId(null);
        assertThat(customerTransactions1).isNotEqualTo(customerTransactions2);
    }
}
