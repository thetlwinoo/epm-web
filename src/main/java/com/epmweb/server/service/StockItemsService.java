package com.epmweb.server.service;

import com.epmweb.server.service.dto.StockItemsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.StockItems}.
 */
public interface StockItemsService {

    /**
     * Save a stockItems.
     *
     * @param stockItemsDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemsDTO save(StockItemsDTO stockItemsDTO);

    /**
     * Get all the stockItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockItemsDTO> findAll(Pageable pageable);
    /**
     * Get all the StockItemsDTO where StockItemHolding is {@code null}.
     *
     * @return the list of entities.
     */
    List<StockItemsDTO> findAllWhereStockItemHoldingIsNull();


    /**
     * Get the "id" stockItems.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemsDTO> findOne(Long id);

    /**
     * Delete the "id" stockItems.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
