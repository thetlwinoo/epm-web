package com.epmweb.server.service;

import com.epmweb.server.service.dto.DangerousGoodsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.DangerousGoods}.
 */
public interface DangerousGoodsService {

    /**
     * Save a dangerousGoods.
     *
     * @param dangerousGoodsDTO the entity to save.
     * @return the persisted entity.
     */
    DangerousGoodsDTO save(DangerousGoodsDTO dangerousGoodsDTO);

    /**
     * Get all the dangerousGoods.
     *
     * @return the list of entities.
     */
    List<DangerousGoodsDTO> findAll();


    /**
     * Get the "id" dangerousGoods.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DangerousGoodsDTO> findOne(Long id);

    /**
     * Delete the "id" dangerousGoods.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
