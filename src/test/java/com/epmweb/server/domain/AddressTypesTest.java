package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class AddressTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypes.class);
        AddressTypes addressTypes1 = new AddressTypes();
        addressTypes1.setId(1L);
        AddressTypes addressTypes2 = new AddressTypes();
        addressTypes2.setId(addressTypes1.getId());
        assertThat(addressTypes1).isEqualTo(addressTypes2);
        addressTypes2.setId(2L);
        assertThat(addressTypes1).isNotEqualTo(addressTypes2);
        addressTypes1.setId(null);
        assertThat(addressTypes1).isNotEqualTo(addressTypes2);
    }
}
