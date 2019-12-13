package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemTempTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTemp.class);
        StockItemTemp stockItemTemp1 = new StockItemTemp();
        stockItemTemp1.setId(1L);
        StockItemTemp stockItemTemp2 = new StockItemTemp();
        stockItemTemp2.setId(stockItemTemp1.getId());
        assertThat(stockItemTemp1).isEqualTo(stockItemTemp2);
        stockItemTemp2.setId(2L);
        assertThat(stockItemTemp1).isNotEqualTo(stockItemTemp2);
        stockItemTemp1.setId(null);
        assertThat(stockItemTemp1).isNotEqualTo(stockItemTemp2);
    }
}
