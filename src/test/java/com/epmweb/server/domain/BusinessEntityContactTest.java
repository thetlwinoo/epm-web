package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BusinessEntityContactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityContact.class);
        BusinessEntityContact businessEntityContact1 = new BusinessEntityContact();
        businessEntityContact1.setId(1L);
        BusinessEntityContact businessEntityContact2 = new BusinessEntityContact();
        businessEntityContact2.setId(businessEntityContact1.getId());
        assertThat(businessEntityContact1).isEqualTo(businessEntityContact2);
        businessEntityContact2.setId(2L);
        assertThat(businessEntityContact1).isNotEqualTo(businessEntityContact2);
        businessEntityContact1.setId(null);
        assertThat(businessEntityContact1).isNotEqualTo(businessEntityContact2);
    }
}
