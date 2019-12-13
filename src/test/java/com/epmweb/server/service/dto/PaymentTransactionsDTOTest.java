package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PaymentTransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTransactionsDTO.class);
        PaymentTransactionsDTO paymentTransactionsDTO1 = new PaymentTransactionsDTO();
        paymentTransactionsDTO1.setId(1L);
        PaymentTransactionsDTO paymentTransactionsDTO2 = new PaymentTransactionsDTO();
        assertThat(paymentTransactionsDTO1).isNotEqualTo(paymentTransactionsDTO2);
        paymentTransactionsDTO2.setId(paymentTransactionsDTO1.getId());
        assertThat(paymentTransactionsDTO1).isEqualTo(paymentTransactionsDTO2);
        paymentTransactionsDTO2.setId(2L);
        assertThat(paymentTransactionsDTO1).isNotEqualTo(paymentTransactionsDTO2);
        paymentTransactionsDTO1.setId(null);
        assertThat(paymentTransactionsDTO1).isNotEqualTo(paymentTransactionsDTO2);
    }
}
