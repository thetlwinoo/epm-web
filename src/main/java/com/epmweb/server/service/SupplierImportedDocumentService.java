package com.epmweb.server.service;

import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.SupplierImportedDocument}.
 */
public interface SupplierImportedDocumentService {

    /**
     * Save a supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierImportedDocumentDTO save(SupplierImportedDocumentDTO supplierImportedDocumentDTO);

    /**
     * Get all the supplierImportedDocuments.
     *
     * @return the list of entities.
     */
    List<SupplierImportedDocumentDTO> findAll();


    /**
     * Get the "id" supplierImportedDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierImportedDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" supplierImportedDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
