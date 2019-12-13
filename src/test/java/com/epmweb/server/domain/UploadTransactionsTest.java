package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class UploadTransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadTransactions.class);
        UploadTransactions uploadTransactions1 = new UploadTransactions();
        uploadTransactions1.setId(1L);
        UploadTransactions uploadTransactions2 = new UploadTransactions();
        uploadTransactions2.setId(uploadTransactions1.getId());
        assertThat(uploadTransactions1).isEqualTo(uploadTransactions2);
        uploadTransactions2.setId(2L);
        assertThat(uploadTransactions1).isNotEqualTo(uploadTransactions2);
        uploadTransactions1.setId(null);
        assertThat(uploadTransactions1).isNotEqualTo(uploadTransactions2);
    }
}
