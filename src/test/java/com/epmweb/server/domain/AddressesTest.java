package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class AddressesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Addresses.class);
        Addresses addresses1 = new Addresses();
        addresses1.setId(1L);
        Addresses addresses2 = new Addresses();
        addresses2.setId(addresses1.getId());
        assertThat(addresses1).isEqualTo(addresses2);
        addresses2.setId(2L);
        assertThat(addresses1).isNotEqualTo(addresses2);
        addresses1.setId(null);
        assertThat(addresses1).isNotEqualTo(addresses2);
    }
}
