package com.epmweb.server.service;

import com.epmweb.server.service.dto.InvoicesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.Invoices}.
 */
public interface InvoicesService {

    /**
     * Save a invoices.
     *
     * @param invoicesDTO the entity to save.
     * @return the persisted entity.
     */
    InvoicesDTO save(InvoicesDTO invoicesDTO);

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    List<InvoicesDTO> findAll();


    /**
     * Get the "id" invoices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvoicesDTO> findOne(Long id);

    /**
     * Delete the "id" invoices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
