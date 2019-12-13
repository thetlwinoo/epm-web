package com.epmweb.server.service;

import com.epmweb.server.service.dto.PackageTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.PackageTypes}.
 */
public interface PackageTypesService {

    /**
     * Save a packageTypes.
     *
     * @param packageTypesDTO the entity to save.
     * @return the persisted entity.
     */
    PackageTypesDTO save(PackageTypesDTO packageTypesDTO);

    /**
     * Get all the packageTypes.
     *
     * @return the list of entities.
     */
    List<PackageTypesDTO> findAll();


    /**
     * Get the "id" packageTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PackageTypesDTO> findOne(Long id);

    /**
     * Delete the "id" packageTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
