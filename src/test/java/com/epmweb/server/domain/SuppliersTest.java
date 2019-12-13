package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SuppliersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suppliers.class);
        Suppliers suppliers1 = new Suppliers();
        suppliers1.setId(1L);
        Suppliers suppliers2 = new Suppliers();
        suppliers2.setId(suppliers1.getId());
        assertThat(suppliers1).isEqualTo(suppliers2);
        suppliers2.setId(2L);
        assertThat(suppliers1).isNotEqualTo(suppliers2);
        suppliers1.setId(null);
        assertThat(suppliers1).isNotEqualTo(suppliers2);
    }
}
