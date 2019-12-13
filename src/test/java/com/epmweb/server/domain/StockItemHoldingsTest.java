package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemHoldingsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemHoldings.class);
        StockItemHoldings stockItemHoldings1 = new StockItemHoldings();
        stockItemHoldings1.setId(1L);
        StockItemHoldings stockItemHoldings2 = new StockItemHoldings();
        stockItemHoldings2.setId(stockItemHoldings1.getId());
        assertThat(stockItemHoldings1).isEqualTo(stockItemHoldings2);
        stockItemHoldings2.setId(2L);
        assertThat(stockItemHoldings1).isNotEqualTo(stockItemHoldings2);
        stockItemHoldings1.setId(null);
        assertThat(stockItemHoldings1).isNotEqualTo(stockItemHoldings2);
    }
}
