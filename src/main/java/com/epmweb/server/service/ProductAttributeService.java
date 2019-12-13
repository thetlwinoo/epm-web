package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductAttributeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.ProductAttribute}.
 */
public interface ProductAttributeService {

    /**
     * Save a productAttribute.
     *
     * @param productAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAttributeDTO save(ProductAttributeDTO productAttributeDTO);

    /**
     * Get all the productAttributes.
     *
     * @return the list of entities.
     */
    List<ProductAttributeDTO> findAll();


    /**
     * Get the "id" productAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" productAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
