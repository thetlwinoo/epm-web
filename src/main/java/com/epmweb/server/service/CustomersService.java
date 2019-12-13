package com.epmweb.server.service;

import com.epmweb.server.service.dto.CustomersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Customers}.
 */
public interface CustomersService {

    /**
     * Save a customers.
     *
     * @param customersDTO the entity to save.
     * @return the persisted entity.
     */
    CustomersDTO save(CustomersDTO customersDTO);

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    List<CustomersDTO> findAll();


    /**
     * Get the "id" customers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomersDTO> findOne(Long id);

    /**
     * Delete the "id" customers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
