package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PersonEmailAddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEmailAddressDTO.class);
        PersonEmailAddressDTO personEmailAddressDTO1 = new PersonEmailAddressDTO();
        personEmailAddressDTO1.setId(1L);
        PersonEmailAddressDTO personEmailAddressDTO2 = new PersonEmailAddressDTO();
        assertThat(personEmailAddressDTO1).isNotEqualTo(personEmailAddressDTO2);
        personEmailAddressDTO2.setId(personEmailAddressDTO1.getId());
        assertThat(personEmailAddressDTO1).isEqualTo(personEmailAddressDTO2);
        personEmailAddressDTO2.setId(2L);
        assertThat(personEmailAddressDTO1).isNotEqualTo(personEmailAddressDTO2);
        personEmailAddressDTO1.setId(null);
        assertThat(personEmailAddressDTO1).isNotEqualTo(personEmailAddressDTO2);
    }
}
