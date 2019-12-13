package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class BarcodeTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeTypes.class);
        BarcodeTypes barcodeTypes1 = new BarcodeTypes();
        barcodeTypes1.setId(1L);
        BarcodeTypes barcodeTypes2 = new BarcodeTypes();
        barcodeTypes2.setId(barcodeTypes1.getId());
        assertThat(barcodeTypes1).isEqualTo(barcodeTypes2);
        barcodeTypes2.setId(2L);
        assertThat(barcodeTypes1).isNotEqualTo(barcodeTypes2);
        barcodeTypes1.setId(null);
        assertThat(barcodeTypes1).isNotEqualTo(barcodeTypes2);
    }
}
