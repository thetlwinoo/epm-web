package com.epmweb.server.service;

import com.epmweb.server.service.dto.CustomerCategoriesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.CustomerCategories}.
 */
public interface CustomerCategoriesService {

    /**
     * Save a customerCategories.
     *
     * @param customerCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerCategoriesDTO save(CustomerCategoriesDTO customerCategoriesDTO);

    /**
     * Get all the customerCategories.
     *
     * @return the list of entities.
     */
    List<CustomerCategoriesDTO> findAll();


    /**
     * Get the "id" customerCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" customerCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
