package com.epmweb.server.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SupplierImportedDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierImportedDocument.class);
        SupplierImportedDocument supplierImportedDocument1 = new SupplierImportedDocument();
        supplierImportedDocument1.setId(1L);
        SupplierImportedDocument supplierImportedDocument2 = new SupplierImportedDocument();
        supplierImportedDocument2.setId(supplierImportedDocument1.getId());
        assertThat(supplierImportedDocument1).isEqualTo(supplierImportedDocument2);
        supplierImportedDocument2.setId(2L);
        assertThat(supplierImportedDocument1).isNotEqualTo(supplierImportedDocument2);
        supplierImportedDocument1.setId(null);
        assertThat(supplierImportedDocument1).isNotEqualTo(supplierImportedDocument2);
    }
}
