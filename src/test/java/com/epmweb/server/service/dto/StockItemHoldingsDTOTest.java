package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemHoldingsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemHoldingsDTO.class);
        StockItemHoldingsDTO stockItemHoldingsDTO1 = new StockItemHoldingsDTO();
        stockItemHoldingsDTO1.setId(1L);
        StockItemHoldingsDTO stockItemHoldingsDTO2 = new StockItemHoldingsDTO();
        assertThat(stockItemHoldingsDTO1).isNotEqualTo(stockItemHoldingsDTO2);
        stockItemHoldingsDTO2.setId(stockItemHoldingsDTO1.getId());
        assertThat(stockItemHoldingsDTO1).isEqualTo(stockItemHoldingsDTO2);
        stockItemHoldingsDTO2.setId(2L);
        assertThat(stockItemHoldingsDTO1).isNotEqualTo(stockItemHoldingsDTO2);
        stockItemHoldingsDTO1.setId(null);
        assertThat(stockItemHoldingsDTO1).isNotEqualTo(stockItemHoldingsDTO2);
    }
}
