package com.epmweb.server.service;

import com.epmweb.server.service.dto.StockItemHoldingsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.StockItemHoldings}.
 */
public interface StockItemHoldingsService {

    /**
     * Save a stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemHoldingsDTO save(StockItemHoldingsDTO stockItemHoldingsDTO);

    /**
     * Get all the stockItemHoldings.
     *
     * @return the list of entities.
     */
    List<StockItemHoldingsDTO> findAll();


    /**
     * Get the "id" stockItemHoldings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemHoldingsDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemHoldings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
