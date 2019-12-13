package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class UploadActionTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadActionTypes.class);
        UploadActionTypes uploadActionTypes1 = new UploadActionTypes();
        uploadActionTypes1.setId(1L);
        UploadActionTypes uploadActionTypes2 = new UploadActionTypes();
        uploadActionTypes2.setId(uploadActionTypes1.getId());
        assertThat(uploadActionTypes1).isEqualTo(uploadActionTypes2);
        uploadActionTypes2.setId(2L);
        assertThat(uploadActionTypes1).isNotEqualTo(uploadActionTypes2);
        uploadActionTypes1.setId(null);
        assertThat(uploadActionTypes1).isNotEqualTo(uploadActionTypes2);
    }
}
