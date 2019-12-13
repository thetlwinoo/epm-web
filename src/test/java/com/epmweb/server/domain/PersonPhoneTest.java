package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PersonPhoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonPhone.class);
        PersonPhone personPhone1 = new PersonPhone();
        personPhone1.setId(1L);
        PersonPhone personPhone2 = new PersonPhone();
        personPhone2.setId(personPhone1.getId());
        assertThat(personPhone1).isEqualTo(personPhone2);
        personPhone2.setId(2L);
        assertThat(personPhone1).isNotEqualTo(personPhone2);
        personPhone1.setId(null);
        assertThat(personPhone1).isNotEqualTo(personPhone2);
    }
}
