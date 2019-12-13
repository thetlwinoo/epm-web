package com.epmweb.server.service;

import com.epmweb.server.service.dto.WishlistsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Wishlists}.
 */
public interface WishlistsService {

    /**
     * Save a wishlists.
     *
     * @param wishlistsDTO the entity to save.
     * @return the persisted entity.
     */
    WishlistsDTO save(WishlistsDTO wishlistsDTO);

    /**
     * Get all the wishlists.
     *
     * @return the list of entities.
     */
    List<WishlistsDTO> findAll();


    /**
     * Get the "id" wishlists.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WishlistsDTO> findOne(Long id);

    /**
     * Delete the "id" wishlists.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
