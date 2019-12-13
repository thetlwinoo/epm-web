package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductChoiceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.ProductChoice}.
 */
public interface ProductChoiceService {

    /**
     * Save a productChoice.
     *
     * @param productChoiceDTO the entity to save.
     * @return the persisted entity.
     */
    ProductChoiceDTO save(ProductChoiceDTO productChoiceDTO);

    /**
     * Get all the productChoices.
     *
     * @return the list of entities.
     */
    List<ProductChoiceDTO> findAll();


    /**
     * Get the "id" productChoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductChoiceDTO> findOne(Long id);

    /**
     * Delete the "id" productChoice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
