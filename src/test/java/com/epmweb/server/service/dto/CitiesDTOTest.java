package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class CitiesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitiesDTO.class);
        CitiesDTO citiesDTO1 = new CitiesDTO();
        citiesDTO1.setId(1L);
        CitiesDTO citiesDTO2 = new CitiesDTO();
        assertThat(citiesDTO1).isNotEqualTo(citiesDTO2);
        citiesDTO2.setId(citiesDTO1.getId());
        assertThat(citiesDTO1).isEqualTo(citiesDTO2);
        citiesDTO2.setId(2L);
        assertThat(citiesDTO1).isNotEqualTo(citiesDTO2);
        citiesDTO1.setId(null);
        assertThat(citiesDTO1).isNotEqualTo(citiesDTO2);
    }
}
