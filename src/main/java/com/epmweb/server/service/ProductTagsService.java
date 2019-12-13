package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductTagsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.ProductTags}.
 */
public interface ProductTagsService {

    /**
     * Save a productTags.
     *
     * @param productTagsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductTagsDTO save(ProductTagsDTO productTagsDTO);

    /**
     * Get all the productTags.
     *
     * @return the list of entities.
     */
    List<ProductTagsDTO> findAll();


    /**
     * Get the "id" productTags.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductTagsDTO> findOne(Long id);

    /**
     * Delete the "id" productTags.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
