package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PackageTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageTypesDTO.class);
        PackageTypesDTO packageTypesDTO1 = new PackageTypesDTO();
        packageTypesDTO1.setId(1L);
        PackageTypesDTO packageTypesDTO2 = new PackageTypesDTO();
        assertThat(packageTypesDTO1).isNotEqualTo(packageTypesDTO2);
        packageTypesDTO2.setId(packageTypesDTO1.getId());
        assertThat(packageTypesDTO1).isEqualTo(packageTypesDTO2);
        packageTypesDTO2.setId(2L);
        assertThat(packageTypesDTO1).isNotEqualTo(packageTypesDTO2);
        packageTypesDTO1.setId(null);
        assertThat(packageTypesDTO1).isNotEqualTo(packageTypesDTO2);
    }
}
