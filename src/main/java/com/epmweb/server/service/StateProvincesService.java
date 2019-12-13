package com.epmweb.server.service;

import com.epmweb.server.service.dto.StateProvincesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.StateProvinces}.
 */
public interface StateProvincesService {

    /**
     * Save a stateProvinces.
     *
     * @param stateProvincesDTO the entity to save.
     * @return the persisted entity.
     */
    StateProvincesDTO save(StateProvincesDTO stateProvincesDTO);

    /**
     * Get all the stateProvinces.
     *
     * @return the list of entities.
     */
    List<StateProvincesDTO> findAll();


    /**
     * Get the "id" stateProvinces.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateProvincesDTO> findOne(Long id);

    /**
     * Delete the "id" stateProvinces.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
