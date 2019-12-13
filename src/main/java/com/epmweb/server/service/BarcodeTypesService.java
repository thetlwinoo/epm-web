package com.epmweb.server.service;

import com.epmweb.server.service.dto.BarcodeTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.BarcodeTypes}.
 */
public interface BarcodeTypesService {

    /**
     * Save a barcodeTypes.
     *
     * @param barcodeTypesDTO the entity to save.
     * @return the persisted entity.
     */
    BarcodeTypesDTO save(BarcodeTypesDTO barcodeTypesDTO);

    /**
     * Get all the barcodeTypes.
     *
     * @return the list of entities.
     */
    List<BarcodeTypesDTO> findAll();


    /**
     * Get the "id" barcodeTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BarcodeTypesDTO> findOne(Long id);

    /**
     * Delete the "id" barcodeTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
