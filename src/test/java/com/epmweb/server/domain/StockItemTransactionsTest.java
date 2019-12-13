package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemTransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTransactions.class);
        StockItemTransactions stockItemTransactions1 = new StockItemTransactions();
        stockItemTransactions1.setId(1L);
        StockItemTransactions stockItemTransactions2 = new StockItemTransactions();
        stockItemTransactions2.setId(stockItemTransactions1.getId());
        assertThat(stockItemTransactions1).isEqualTo(stockItemTransactions2);
        stockItemTransactions2.setId(2L);
        assertThat(stockItemTransactions1).isNotEqualTo(stockItemTransactions2);
        stockItemTransactions1.setId(null);
        assertThat(stockItemTransactions1).isNotEqualTo(stockItemTransactions2);
    }
}
