package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class InvoiceLinesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLines.class);
        InvoiceLines invoiceLines1 = new InvoiceLines();
        invoiceLines1.setId(1L);
        InvoiceLines invoiceLines2 = new InvoiceLines();
        invoiceLines2.setId(invoiceLines1.getId());
        assertThat(invoiceLines1).isEqualTo(invoiceLines2);
        invoiceLines2.setId(2L);
        assertThat(invoiceLines1).isNotEqualTo(invoiceLines2);
        invoiceLines1.setId(null);
        assertThat(invoiceLines1).isNotEqualTo(invoiceLines2);
    }
}
