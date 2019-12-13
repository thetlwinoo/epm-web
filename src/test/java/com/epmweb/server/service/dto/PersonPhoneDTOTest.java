package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PersonPhoneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonPhoneDTO.class);
        PersonPhoneDTO personPhoneDTO1 = new PersonPhoneDTO();
        personPhoneDTO1.setId(1L);
        PersonPhoneDTO personPhoneDTO2 = new PersonPhoneDTO();
        assertThat(personPhoneDTO1).isNotEqualTo(personPhoneDTO2);
        personPhoneDTO2.setId(personPhoneDTO1.getId());
        assertThat(personPhoneDTO1).isEqualTo(personPhoneDTO2);
        personPhoneDTO2.setId(2L);
        assertThat(personPhoneDTO1).isNotEqualTo(personPhoneDTO2);
        personPhoneDTO1.setId(null);
        assertThat(personPhoneDTO1).isNotEqualTo(personPhoneDTO2);
    }
}
