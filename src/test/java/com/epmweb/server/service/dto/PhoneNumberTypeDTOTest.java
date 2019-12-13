package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PhoneNumberTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberTypeDTO.class);
        PhoneNumberTypeDTO phoneNumberTypeDTO1 = new PhoneNumberTypeDTO();
        phoneNumberTypeDTO1.setId(1L);
        PhoneNumberTypeDTO phoneNumberTypeDTO2 = new PhoneNumberTypeDTO();
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO2.setId(phoneNumberTypeDTO1.getId());
        assertThat(phoneNumberTypeDTO1).isEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO2.setId(2L);
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO1.setId(null);
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
    }
}
