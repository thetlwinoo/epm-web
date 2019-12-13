package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CurrencyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyDTO.class);
        CurrencyDTO currencyDTO1 = new CurrencyDTO();
        currencyDTO1.setId(1L);
        CurrencyDTO currencyDTO2 = new CurrencyDTO();
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO2.setId(currencyDTO1.getId());
        assertThat(currencyDTO1).isEqualTo(currencyDTO2);
        currencyDTO2.setId(2L);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO1.setId(null);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
    }
}
