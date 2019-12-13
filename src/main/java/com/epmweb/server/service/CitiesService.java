package com.epmweb.server.service;

import com.epmweb.server.service.dto.CitiesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Cities}.
 */
public interface CitiesService {

    /**
     * Save a cities.
     *
     * @param citiesDTO the entity to save.
     * @return the persisted entity.
     */
    CitiesDTO save(CitiesDTO citiesDTO);

    /**
     * Get all the cities.
     *
     * @return the list of entities.
     */
    List<CitiesDTO> findAll();


    /**
     * Get the "id" cities.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitiesDTO> findOne(Long id);

    /**
     * Delete the "id" cities.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
