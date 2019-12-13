package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductDocumentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.ProductDocument}.
 */
public interface ProductDocumentService {

    /**
     * Save a productDocument.
     *
     * @param productDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDocumentDTO save(ProductDocumentDTO productDocumentDTO);

    /**
     * Get all the productDocuments.
     *
     * @return the list of entities.
     */
    List<ProductDocumentDTO> findAll();


    /**
     * Get the "id" productDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" productDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
