package com.epmweb.server.service;

import com.epmweb.server.service.dto.SpecialDealsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.SpecialDeals}.
 */
public interface SpecialDealsService {

    /**
     * Save a specialDeals.
     *
     * @param specialDealsDTO the entity to save.
     * @return the persisted entity.
     */
    SpecialDealsDTO save(SpecialDealsDTO specialDealsDTO);

    /**
     * Get all the specialDeals.
     *
     * @return the list of entities.
     */
    List<SpecialDealsDTO> findAll();


    /**
     * Get the "id" specialDeals.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpecialDealsDTO> findOne(Long id);

    /**
     * Delete the "id" specialDeals.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
