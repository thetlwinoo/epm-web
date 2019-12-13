package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductSetDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.ProductSetDetails}.
 */
public interface ProductSetDetailsService {

    /**
     * Save a productSetDetails.
     *
     * @param productSetDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSetDetailsDTO save(ProductSetDetailsDTO productSetDetailsDTO);

    /**
     * Get all the productSetDetails.
     *
     * @return the list of entities.
     */
    List<ProductSetDetailsDTO> findAll();


    /**
     * Get the "id" productSetDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSetDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" productSetDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
