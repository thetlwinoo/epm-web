package com.epmweb.server.service;

import com.epmweb.server.service.dto.CustomerTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.CustomerTransactions}.
 */
public interface CustomerTransactionsService {

    /**
     * Save a customerTransactions.
     *
     * @param customerTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerTransactionsDTO save(CustomerTransactionsDTO customerTransactionsDTO);

    /**
     * Get all the customerTransactions.
     *
     * @return the list of entities.
     */
    List<CustomerTransactionsDTO> findAll();


    /**
     * Get the "id" customerTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" customerTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
