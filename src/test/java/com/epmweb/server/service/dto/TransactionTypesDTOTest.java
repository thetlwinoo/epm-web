package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class TransactionTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypesDTO.class);
        TransactionTypesDTO transactionTypesDTO1 = new TransactionTypesDTO();
        transactionTypesDTO1.setId(1L);
        TransactionTypesDTO transactionTypesDTO2 = new TransactionTypesDTO();
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
        transactionTypesDTO2.setId(transactionTypesDTO1.getId());
        assertThat(transactionTypesDTO1).isEqualTo(transactionTypesDTO2);
        transactionTypesDTO2.setId(2L);
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
        transactionTypesDTO1.setId(null);
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
    }
}
