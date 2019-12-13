package com.epmweb.server.service;

import com.epmweb.server.service.dto.MaterialsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Materials}.
 */
public interface MaterialsService {

    /**
     * Save a materials.
     *
     * @param materialsDTO the entity to save.
     * @return the persisted entity.
     */
    MaterialsDTO save(MaterialsDTO materialsDTO);

    /**
     * Get all the materials.
     *
     * @return the list of entities.
     */
    List<MaterialsDTO> findAll();


    /**
     * Get the "id" materials.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialsDTO> findOne(Long id);

    /**
     * Delete the "id" materials.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
