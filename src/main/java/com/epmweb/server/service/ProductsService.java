package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Products}.
 */
public interface ProductsService {

    /**
     * Save a products.
     *
     * @param productsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductsDTO save(ProductsDTO productsDTO);

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" products.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductsDTO> findOne(Long id);

    /**
     * Delete the "id" products.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
