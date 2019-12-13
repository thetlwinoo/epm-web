package com.epmweb.server.service;

import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;

import java.util.Optional;

public interface SupplierImportedDocumentExtendService {
    Optional<SupplierImportedDocumentDTO> getOneByUploadTransactionId(Long transactionId);
}
