package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PhoneNumberTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberType.class);
        PhoneNumberType phoneNumberType1 = new PhoneNumberType();
        phoneNumberType1.setId(1L);
        PhoneNumberType phoneNumberType2 = new PhoneNumberType();
        phoneNumberType2.setId(phoneNumberType1.getId());
        assertThat(phoneNumberType1).isEqualTo(phoneNumberType2);
        phoneNumberType2.setId(2L);
        assertThat(phoneNumberType1).isNotEqualTo(phoneNumberType2);
        phoneNumberType1.setId(null);
        assertThat(phoneNumberType1).isNotEqualTo(phoneNumberType2);
    }
}
