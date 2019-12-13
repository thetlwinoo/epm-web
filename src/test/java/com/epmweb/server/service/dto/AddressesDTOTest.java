package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class AddressesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressesDTO.class);
        AddressesDTO addressesDTO1 = new AddressesDTO();
        addressesDTO1.setId(1L);
        AddressesDTO addressesDTO2 = new AddressesDTO();
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
        addressesDTO2.setId(addressesDTO1.getId());
        assertThat(addressesDTO1).isEqualTo(addressesDTO2);
        addressesDTO2.setId(2L);
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
        addressesDTO1.setId(null);
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
    }
}
