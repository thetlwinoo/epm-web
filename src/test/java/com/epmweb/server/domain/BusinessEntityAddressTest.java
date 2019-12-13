package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BusinessEntityAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityAddress.class);
        BusinessEntityAddress businessEntityAddress1 = new BusinessEntityAddress();
        businessEntityAddress1.setId(1L);
        BusinessEntityAddress businessEntityAddress2 = new BusinessEntityAddress();
        businessEntityAddress2.setId(businessEntityAddress1.getId());
        assertThat(businessEntityAddress1).isEqualTo(businessEntityAddress2);
        businessEntityAddress2.setId(2L);
        assertThat(businessEntityAddress1).isNotEqualTo(businessEntityAddress2);
        businessEntityAddress1.setId(null);
        assertThat(businessEntityAddress1).isNotEqualTo(businessEntityAddress2);
    }
}
