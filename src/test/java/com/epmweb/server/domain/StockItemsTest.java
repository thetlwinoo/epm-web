package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItems.class);
        StockItems stockItems1 = new StockItems();
        stockItems1.setId(1L);
        StockItems stockItems2 = new StockItems();
        stockItems2.setId(stockItems1.getId());
        assertThat(stockItems1).isEqualTo(stockItems2);
        stockItems2.setId(2L);
        assertThat(stockItems1).isNotEqualTo(stockItems2);
        stockItems1.setId(null);
        assertThat(stockItems1).isNotEqualTo(stockItems2);
    }
}
