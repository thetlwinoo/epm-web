package com.epmweb.server.service;

import com.epmweb.server.service.dto.PurchaseOrderLinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.PurchaseOrderLines}.
 */
public interface PurchaseOrderLinesService {

    /**
     * Save a purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the entity to save.
     * @return the persisted entity.
     */
    PurchaseOrderLinesDTO save(PurchaseOrderLinesDTO purchaseOrderLinesDTO);

    /**
     * Get all the purchaseOrderLines.
     *
     * @return the list of entities.
     */
    List<PurchaseOrderLinesDTO> findAll();


    /**
     * Get the "id" purchaseOrderLines.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrderLinesDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseOrderLines.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
