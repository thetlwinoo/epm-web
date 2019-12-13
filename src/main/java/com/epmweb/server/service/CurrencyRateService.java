package com.epmweb.server.service;

import com.epmweb.server.service.dto.CurrencyRateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epmweb.server.domain.CurrencyRate}.
 */
public interface CurrencyRateService {

    /**
     * Save a currencyRate.
     *
     * @param currencyRateDTO the entity to save.
     * @return the persisted entity.
     */
    CurrencyRateDTO save(CurrencyRateDTO currencyRateDTO);

    /**
     * Get all the currencyRates.
     *
     * @return the list of entities.
     */
    List<CurrencyRateDTO> findAll();


    /**
     * Get the "id" currencyRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurrencyRateDTO> findOne(Long id);

    /**
     * Delete the "id" currencyRate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
