package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemTransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTransactionsDTO.class);
        StockItemTransactionsDTO stockItemTransactionsDTO1 = new StockItemTransactionsDTO();
        stockItemTransactionsDTO1.setId(1L);
        StockItemTransactionsDTO stockItemTransactionsDTO2 = new StockItemTransactionsDTO();
        assertThat(stockItemTransactionsDTO1).isNotEqualTo(stockItemTransactionsDTO2);
        stockItemTransactionsDTO2.setId(stockItemTransactionsDTO1.getId());
        assertThat(stockItemTransactionsDTO1).isEqualTo(stockItemTransactionsDTO2);
        stockItemTransactionsDTO2.setId(2L);
        assertThat(stockItemTransactionsDTO1).isNotEqualTo(stockItemTransactionsDTO2);
        stockItemTransactionsDTO1.setId(null);
        assertThat(stockItemTransactionsDTO1).isNotEqualTo(stockItemTransactionsDTO2);
    }
}
