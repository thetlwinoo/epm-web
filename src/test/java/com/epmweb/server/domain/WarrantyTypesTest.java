package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class WarrantyTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyTypes.class);
        WarrantyTypes warrantyTypes1 = new WarrantyTypes();
        warrantyTypes1.setId(1L);
        WarrantyTypes warrantyTypes2 = new WarrantyTypes();
        warrantyTypes2.setId(warrantyTypes1.getId());
        assertThat(warrantyTypes1).isEqualTo(warrantyTypes2);
        warrantyTypes2.setId(2L);
        assertThat(warrantyTypes1).isNotEqualTo(warrantyTypes2);
        warrantyTypes1.setId(null);
        assertThat(warrantyTypes1).isNotEqualTo(warrantyTypes2);
    }
}
