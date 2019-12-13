package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CurrencyRateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRateDTO.class);
        CurrencyRateDTO currencyRateDTO1 = new CurrencyRateDTO();
        currencyRateDTO1.setId(1L);
        CurrencyRateDTO currencyRateDTO2 = new CurrencyRateDTO();
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(currencyRateDTO1.getId());
        assertThat(currencyRateDTO1).isEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(2L);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO1.setId(null);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
    }
}
