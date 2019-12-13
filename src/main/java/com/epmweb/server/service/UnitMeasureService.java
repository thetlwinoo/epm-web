package com.epmweb.server.service;

import com.epmweb.server.service.dto.UnitMeasureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.UnitMeasure}.
 */
public interface UnitMeasureService {

    /**
     * Save a unitMeasure.
     *
     * @param unitMeasureDTO the entity to save.
     * @return the persisted entity.
     */
    UnitMeasureDTO save(UnitMeasureDTO unitMeasureDTO);

    /**
     * Get all the unitMeasures.
     *
     * @return the list of entities.
     */
    List<UnitMeasureDTO> findAll();


    /**
     * Get the "id" unitMeasure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitMeasureDTO> findOne(Long id);

    /**
     * Delete the "id" unitMeasure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
