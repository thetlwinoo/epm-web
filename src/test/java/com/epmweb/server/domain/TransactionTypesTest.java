package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class TransactionTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypes.class);
        TransactionTypes transactionTypes1 = new TransactionTypes();
        transactionTypes1.setId(1L);
        TransactionTypes transactionTypes2 = new TransactionTypes();
        transactionTypes2.setId(transactionTypes1.getId());
        assertThat(transactionTypes1).isEqualTo(transactionTypes2);
        transactionTypes2.setId(2L);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
        transactionTypes1.setId(null);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
    }
}
