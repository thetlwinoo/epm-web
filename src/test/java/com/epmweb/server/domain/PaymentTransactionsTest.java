package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PaymentTransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTransactions.class);
        PaymentTransactions paymentTransactions1 = new PaymentTransactions();
        paymentTransactions1.setId(1L);
        PaymentTransactions paymentTransactions2 = new PaymentTransactions();
        paymentTransactions2.setId(paymentTransactions1.getId());
        assertThat(paymentTransactions1).isEqualTo(paymentTransactions2);
        paymentTransactions2.setId(2L);
        assertThat(paymentTransactions1).isNotEqualTo(paymentTransactions2);
        paymentTransactions1.setId(null);
        assertThat(paymentTransactions1).isNotEqualTo(paymentTransactions2);
    }
}
