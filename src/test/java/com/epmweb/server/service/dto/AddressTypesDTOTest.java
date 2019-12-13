package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class AddressTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypesDTO.class);
        AddressTypesDTO addressTypesDTO1 = new AddressTypesDTO();
        addressTypesDTO1.setId(1L);
        AddressTypesDTO addressTypesDTO2 = new AddressTypesDTO();
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
        addressTypesDTO2.setId(addressTypesDTO1.getId());
        assertThat(addressTypesDTO1).isEqualTo(addressTypesDTO2);
        addressTypesDTO2.setId(2L);
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
        addressTypesDTO1.setId(null);
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
    }
}
