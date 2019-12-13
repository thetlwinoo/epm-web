package com.epmweb.server.service;

import com.epmweb.server.service.dto.PhotosDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Photos}.
 */
public interface PhotosService {

    /**
     * Save a photos.
     *
     * @param photosDTO the entity to save.
     * @return the persisted entity.
     */
    PhotosDTO save(PhotosDTO photosDTO);

    /**
     * Get all the photos.
     *
     * @return the list of entities.
     */
    List<PhotosDTO> findAll();


    /**
     * Get the "id" photos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhotosDTO> findOne(Long id);

    /**
     * Delete the "id" photos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
