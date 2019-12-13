package com.epmweb.server.service;

import com.epmweb.server.service.dto.ComparesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Compares}.
 */
public interface ComparesService {

    /**
     * Save a compares.
     *
     * @param comparesDTO the entity to save.
     * @return the persisted entity.
     */
    ComparesDTO save(ComparesDTO comparesDTO);

    /**
     * Get all the compares.
     *
     * @return the list of entities.
     */
    List<ComparesDTO> findAll();


    /**
     * Get the "id" compares.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComparesDTO> findOne(Long id);

    /**
     * Delete the "id" compares.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
