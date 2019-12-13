package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemTempDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemTempDTO.class);
        StockItemTempDTO stockItemTempDTO1 = new StockItemTempDTO();
        stockItemTempDTO1.setId(1L);
        StockItemTempDTO stockItemTempDTO2 = new StockItemTempDTO();
        assertThat(stockItemTempDTO1).isNotEqualTo(stockItemTempDTO2);
        stockItemTempDTO2.setId(stockItemTempDTO1.getId());
        assertThat(stockItemTempDTO1).isEqualTo(stockItemTempDTO2);
        stockItemTempDTO2.setId(2L);
        assertThat(stockItemTempDTO1).isNotEqualTo(stockItemTempDTO2);
        stockItemTempDTO1.setId(null);
        assertThat(stockItemTempDTO1).isNotEqualTo(stockItemTempDTO2);
    }
}
