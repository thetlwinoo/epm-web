package com.epmweb.server.service;

import com.epmweb.server.service.dto.BusinessEntityAddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.BusinessEntityAddress}.
 */
public interface BusinessEntityAddressService {

    /**
     * Save a businessEntityAddress.
     *
     * @param businessEntityAddressDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessEntityAddressDTO save(BusinessEntityAddressDTO businessEntityAddressDTO);

    /**
     * Get all the businessEntityAddresses.
     *
     * @return the list of entities.
     */
    List<BusinessEntityAddressDTO> findAll();


    /**
     * Get the "id" businessEntityAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessEntityAddressDTO> findOne(Long id);

    /**
     * Delete the "id" businessEntityAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
