package com.epmweb.server.service;

import com.epmweb.server.service.dto.ReviewsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Reviews}.
 */
public interface ReviewsService {

    /**
     * Save a reviews.
     *
     * @param reviewsDTO the entity to save.
     * @return the persisted entity.
     */
    ReviewsDTO save(ReviewsDTO reviewsDTO);

    /**
     * Get all the reviews.
     *
     * @return the list of entities.
     */
    List<ReviewsDTO> findAll();
    /**
     * Get all the ReviewsDTO where Order is {@code null}.
     *
     * @return the list of entities.
     */
    List<ReviewsDTO> findAllWhereOrderIsNull();


    /**
     * Get the "id" reviews.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReviewsDTO> findOne(Long id);

    /**
     * Delete the "id" reviews.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
