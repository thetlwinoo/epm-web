package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class InvoiceLinesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLinesDTO.class);
        InvoiceLinesDTO invoiceLinesDTO1 = new InvoiceLinesDTO();
        invoiceLinesDTO1.setId(1L);
        InvoiceLinesDTO invoiceLinesDTO2 = new InvoiceLinesDTO();
        assertThat(invoiceLinesDTO1).isNotEqualTo(invoiceLinesDTO2);
        invoiceLinesDTO2.setId(invoiceLinesDTO1.getId());
        assertThat(invoiceLinesDTO1).isEqualTo(invoiceLinesDTO2);
        invoiceLinesDTO2.setId(2L);
        assertThat(invoiceLinesDTO1).isNotEqualTo(invoiceLinesDTO2);
        invoiceLinesDTO1.setId(null);
        assertThat(invoiceLinesDTO1).isNotEqualTo(invoiceLinesDTO2);
    }
}
