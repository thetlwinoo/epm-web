package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CustomerTransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTransactionsDTO.class);
        CustomerTransactionsDTO customerTransactionsDTO1 = new CustomerTransactionsDTO();
        customerTransactionsDTO1.setId(1L);
        CustomerTransactionsDTO customerTransactionsDTO2 = new CustomerTransactionsDTO();
        assertThat(customerTransactionsDTO1).isNotEqualTo(customerTransactionsDTO2);
        customerTransactionsDTO2.setId(customerTransactionsDTO1.getId());
        assertThat(customerTransactionsDTO1).isEqualTo(customerTransactionsDTO2);
        customerTransactionsDTO2.setId(2L);
        assertThat(customerTransactionsDTO1).isNotEqualTo(customerTransactionsDTO2);
        customerTransactionsDTO1.setId(null);
        assertThat(customerTransactionsDTO1).isNotEqualTo(customerTransactionsDTO2);
    }
}
