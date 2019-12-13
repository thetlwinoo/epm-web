package com.epmweb.server.repository;

import com.epmweb.server.domain.SupplierImportedDocument;

import java.util.Optional;

public interface SupplierImportedDocumentExtendRepository extends SupplierImportedDocumentRepository {
    Optional<SupplierImportedDocument> findFirstByUploadTransactionId(Long id);
}
