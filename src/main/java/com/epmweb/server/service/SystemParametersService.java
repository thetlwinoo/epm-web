package com.epmweb.server.service;

import com.epmweb.server.service.dto.SystemParametersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.SystemParameters}.
 */
public interface SystemParametersService {

    /**
     * Save a systemParameters.
     *
     * @param systemParametersDTO the entity to save.
     * @return the persisted entity.
     */
    SystemParametersDTO save(SystemParametersDTO systemParametersDTO);

    /**
     * Get all the systemParameters.
     *
     * @return the list of entities.
     */
    List<SystemParametersDTO> findAll();


    /**
     * Get the "id" systemParameters.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemParametersDTO> findOne(Long id);

    /**
     * Delete the "id" systemParameters.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
