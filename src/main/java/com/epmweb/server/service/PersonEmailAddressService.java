package com.epmweb.server.service;

import com.epmweb.server.service.dto.PersonEmailAddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.PersonEmailAddress}.
 */
public interface PersonEmailAddressService {

    /**
     * Save a personEmailAddress.
     *
     * @param personEmailAddressDTO the entity to save.
     * @return the persisted entity.
     */
    PersonEmailAddressDTO save(PersonEmailAddressDTO personEmailAddressDTO);

    /**
     * Get all the personEmailAddresses.
     *
     * @return the list of entities.
     */
    List<PersonEmailAddressDTO> findAll();


    /**
     * Get the "id" personEmailAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonEmailAddressDTO> findOne(Long id);

    /**
     * Delete the "id" personEmailAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
