package com.epmweb.server.service;

import com.epmweb.server.service.dto.CultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Culture}.
 */
public interface CultureService {

    /**
     * Save a culture.
     *
     * @param cultureDTO the entity to save.
     * @return the persisted entity.
     */
    CultureDTO save(CultureDTO cultureDTO);

    /**
     * Get all the cultures.
     *
     * @return the list of entities.
     */
    List<CultureDTO> findAll();


    /**
     * Get the "id" culture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CultureDTO> findOne(Long id);

    /**
     * Delete the "id" culture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
