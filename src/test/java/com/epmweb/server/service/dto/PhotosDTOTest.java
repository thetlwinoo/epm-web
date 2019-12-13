package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class PhotosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotosDTO.class);
        PhotosDTO photosDTO1 = new PhotosDTO();
        photosDTO1.setId(1L);
        PhotosDTO photosDTO2 = new PhotosDTO();
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
        photosDTO2.setId(photosDTO1.getId());
        assertThat(photosDTO1).isEqualTo(photosDTO2);
        photosDTO2.setId(2L);
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
        photosDTO1.setId(null);
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
    }
}
