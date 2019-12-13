package com.epmweb.server.service;

import com.epmweb.server.service.dto.UploadTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.UploadTransactions}.
 */
public interface UploadTransactionsService {

    /**
     * Save a uploadTransactions.
     *
     * @param uploadTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    UploadTransactionsDTO save(UploadTransactionsDTO uploadTransactionsDTO);

    /**
     * Get all the uploadTransactions.
     *
     * @return the list of entities.
     */
    List<UploadTransactionsDTO> findAll();


    /**
     * Get the "id" uploadTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UploadTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" uploadTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
