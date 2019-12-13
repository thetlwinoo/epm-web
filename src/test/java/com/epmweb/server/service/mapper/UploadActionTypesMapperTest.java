package com.epmweb.server.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UploadActionTypesMapperTest {

    private UploadActionTypesMapper uploadActionTypesMapper;

    @BeforeEach
    public void setUp() {
        uploadActionTypesMapper = new UploadActionTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(uploadActionTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uploadActionTypesMapper.fromId(null)).isNull();
    }
}
