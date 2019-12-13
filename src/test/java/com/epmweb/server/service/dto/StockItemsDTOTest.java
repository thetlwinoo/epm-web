package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class StockItemsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemsDTO.class);
        StockItemsDTO stockItemsDTO1 = new StockItemsDTO();
        stockItemsDTO1.setId(1L);
        StockItemsDTO stockItemsDTO2 = new StockItemsDTO();
        assertThat(stockItemsDTO1).isNotEqualTo(stockItemsDTO2);
        stockItemsDTO2.setId(stockItemsDTO1.getId());
        assertThat(stockItemsDTO1).isEqualTo(stockItemsDTO2);
        stockItemsDTO2.setId(2L);
        assertThat(stockItemsDTO1).isNotEqualTo(stockItemsDTO2);
        stockItemsDTO1.setId(null);
        assertThat(stockItemsDTO1).isNotEqualTo(stockItemsDTO2);
    }
}
