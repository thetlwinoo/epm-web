package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SpecialDealsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialDealsDTO.class);
        SpecialDealsDTO specialDealsDTO1 = new SpecialDealsDTO();
        specialDealsDTO1.setId(1L);
        SpecialDealsDTO specialDealsDTO2 = new SpecialDealsDTO();
        assertThat(specialDealsDTO1).isNotEqualTo(specialDealsDTO2);
        specialDealsDTO2.setId(specialDealsDTO1.getId());
        assertThat(specialDealsDTO1).isEqualTo(specialDealsDTO2);
        specialDealsDTO2.setId(2L);
        assertThat(specialDealsDTO1).isNotEqualTo(specialDealsDTO2);
        specialDealsDTO1.setId(null);
        assertThat(specialDealsDTO1).isNotEqualTo(specialDealsDTO2);
    }
}
