package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductAttributeSetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.ProductAttributeSet}.
 */
public interface ProductAttributeSetService {

    /**
     * Save a productAttributeSet.
     *
     * @param productAttributeSetDTO the entity to save.
     * @return the persisted entity.
     */
    ProductAttributeSetDTO save(ProductAttributeSetDTO productAttributeSetDTO);

    /**
     * Get all the productAttributeSets.
     *
     * @return the list of entities.
     */
    List<ProductAttributeSetDTO> findAll();


    /**
     * Get the "id" productAttributeSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAttributeSetDTO> findOne(Long id);

    /**
     * Delete the "id" productAttributeSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
