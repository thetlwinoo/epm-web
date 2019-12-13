package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BusinessEntityAddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityAddressDTO.class);
        BusinessEntityAddressDTO businessEntityAddressDTO1 = new BusinessEntityAddressDTO();
        businessEntityAddressDTO1.setId(1L);
        BusinessEntityAddressDTO businessEntityAddressDTO2 = new BusinessEntityAddressDTO();
        assertThat(businessEntityAddressDTO1).isNotEqualTo(businessEntityAddressDTO2);
        businessEntityAddressDTO2.setId(businessEntityAddressDTO1.getId());
        assertThat(businessEntityAddressDTO1).isEqualTo(businessEntityAddressDTO2);
        businessEntityAddressDTO2.setId(2L);
        assertThat(businessEntityAddressDTO1).isNotEqualTo(businessEntityAddressDTO2);
        businessEntityAddressDTO1.setId(null);
        assertThat(businessEntityAddressDTO1).isNotEqualTo(businessEntityAddressDTO2);
    }
}
