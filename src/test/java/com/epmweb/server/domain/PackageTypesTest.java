package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PackageTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageTypes.class);
        PackageTypes packageTypes1 = new PackageTypes();
        packageTypes1.setId(1L);
        PackageTypes packageTypes2 = new PackageTypes();
        packageTypes2.setId(packageTypes1.getId());
        assertThat(packageTypes1).isEqualTo(packageTypes2);
        packageTypes2.setId(2L);
        assertThat(packageTypes1).isNotEqualTo(packageTypes2);
        packageTypes1.setId(null);
        assertThat(packageTypes1).isNotEqualTo(packageTypes2);
    }
}
