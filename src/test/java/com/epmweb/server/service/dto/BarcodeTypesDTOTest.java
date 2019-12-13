package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BarcodeTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeTypesDTO.class);
        BarcodeTypesDTO barcodeTypesDTO1 = new BarcodeTypesDTO();
        barcodeTypesDTO1.setId(1L);
        BarcodeTypesDTO barcodeTypesDTO2 = new BarcodeTypesDTO();
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO2.setId(barcodeTypesDTO1.getId());
        assertThat(barcodeTypesDTO1).isEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO2.setId(2L);
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO1.setId(null);
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
    }
}
