package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class UploadTransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadTransactionsDTO.class);
        UploadTransactionsDTO uploadTransactionsDTO1 = new UploadTransactionsDTO();
        uploadTransactionsDTO1.setId(1L);
        UploadTransactionsDTO uploadTransactionsDTO2 = new UploadTransactionsDTO();
        assertThat(uploadTransactionsDTO1).isNotEqualTo(uploadTransactionsDTO2);
        uploadTransactionsDTO2.setId(uploadTransactionsDTO1.getId());
        assertThat(uploadTransactionsDTO1).isEqualTo(uploadTransactionsDTO2);
        uploadTransactionsDTO2.setId(2L);
        assertThat(uploadTransactionsDTO1).isNotEqualTo(uploadTransactionsDTO2);
        uploadTransactionsDTO1.setId(null);
        assertThat(uploadTransactionsDTO1).isNotEqualTo(uploadTransactionsDTO2);
    }
}
