package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class ContactTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactTypeDTO.class);
        ContactTypeDTO contactTypeDTO1 = new ContactTypeDTO();
        contactTypeDTO1.setId(1L);
        ContactTypeDTO contactTypeDTO2 = new ContactTypeDTO();
        assertThat(contactTypeDTO1).isNotEqualTo(contactTypeDTO2);
        contactTypeDTO2.setId(contactTypeDTO1.getId());
        assertThat(contactTypeDTO1).isEqualTo(contactTypeDTO2);
        contactTypeDTO2.setId(2L);
        assertThat(contactTypeDTO1).isNotEqualTo(contactTypeDTO2);
        contactTypeDTO1.setId(null);
        assertThat(contactTypeDTO1).isNotEqualTo(contactTypeDTO2);
    }
}
