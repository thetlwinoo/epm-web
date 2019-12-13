package com.epmweb.server.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epmweb.server.web.rest.TestUtil;

public class SupplierImportedDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierImportedDocumentDTO.class);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO1 = new SupplierImportedDocumentDTO();
        supplierImportedDocumentDTO1.setId(1L);
        SupplierImportedDocumentDTO supplierImportedDocumentDTO2 = new SupplierImportedDocumentDTO();
        assertThat(supplierImportedDocumentDTO1).isNotEqualTo(supplierImportedDocumentDTO2);
        supplierImportedDocumentDTO2.setId(supplierImportedDocumentDTO1.getId());
        assertThat(supplierImportedDocumentDTO1).isEqualTo(supplierImportedDocumentDTO2);
        supplierImportedDocumentDTO2.setId(2L);
        assertThat(supplierImportedDocumentDTO1).isNotEqualTo(supplierImportedDocumentDTO2);
        supplierImportedDocumentDTO1.setId(null);
        assertThat(supplierImportedDocumentDTO1).isNotEqualTo(supplierImportedDocumentDTO2);
    }
}
