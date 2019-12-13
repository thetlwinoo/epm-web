package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class UploadActionTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadActionTypesDTO.class);
        UploadActionTypesDTO uploadActionTypesDTO1 = new UploadActionTypesDTO();
        uploadActionTypesDTO1.setId(1L);
        UploadActionTypesDTO uploadActionTypesDTO2 = new UploadActionTypesDTO();
        assertThat(uploadActionTypesDTO1).isNotEqualTo(uploadActionTypesDTO2);
        uploadActionTypesDTO2.setId(uploadActionTypesDTO1.getId());
        assertThat(uploadActionTypesDTO1).isEqualTo(uploadActionTypesDTO2);
        uploadActionTypesDTO2.setId(2L);
        assertThat(uploadActionTypesDTO1).isNotEqualTo(uploadActionTypesDTO2);
        uploadActionTypesDTO1.setId(null);
        assertThat(uploadActionTypesDTO1).isNotEqualTo(uploadActionTypesDTO2);
    }
}
