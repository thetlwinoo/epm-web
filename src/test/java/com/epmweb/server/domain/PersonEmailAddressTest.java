package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PersonEmailAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEmailAddress.class);
        PersonEmailAddress personEmailAddress1 = new PersonEmailAddress();
        personEmailAddress1.setId(1L);
        PersonEmailAddress personEmailAddress2 = new PersonEmailAddress();
        personEmailAddress2.setId(personEmailAddress1.getId());
        assertThat(personEmailAddress1).isEqualTo(personEmailAddress2);
        personEmailAddress2.setId(2L);
        assertThat(personEmailAddress1).isNotEqualTo(personEmailAddress2);
        personEmailAddress1.setId(null);
        assertThat(personEmailAddress1).isNotEqualTo(personEmailAddress2);
    }
}
