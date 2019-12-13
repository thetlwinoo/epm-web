package com.epmweb.server.service;

import com.epmweb.server.service.dto.BuyingGroupsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.BuyingGroups}.
 */
public interface BuyingGroupsService {

    /**
     * Save a buyingGroups.
     *
     * @param buyingGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    BuyingGroupsDTO save(BuyingGroupsDTO buyingGroupsDTO);

    /**
     * Get all the buyingGroups.
     *
     * @return the list of entities.
     */
    List<BuyingGroupsDTO> findAll();


    /**
     * Get the "id" buyingGroups.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyingGroupsDTO> findOne(Long id);

    /**
     * Delete the "id" buyingGroups.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
