package com.epmweb.server.service.impl;

import com.epmweb.server.service.CurrencyRateService;
import com.epmweb.server.domain.CurrencyRate;
import com.epmweb.server.repository.CurrencyRateRepository;
import com.epmweb.server.service.dto.CurrencyRateDTO;
import com.epmweb.server.service.mapper.CurrencyRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CurrencyRate}.
 */
@Service
@Transactional
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateServiceImpl.class);

    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyRateMapper currencyRateMapper;

    public CurrencyRateServiceImpl(CurrencyRateRepository currencyRateRepository, CurrencyRateMapper currencyRateMapper) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyRateMapper = currencyRateMapper;
    }

    /**
     * Save a currencyRate.
     *
     * @param currencyRateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurrencyRateDTO save(CurrencyRateDTO currencyRateDTO) {
        log.debug("Request to save CurrencyRate : {}", currencyRateDTO);
        CurrencyRate currencyRate = currencyRateMapper.toEntity(currencyRateDTO);
        currencyRate = currencyRateRepository.save(currencyRate);
        return currencyRateMapper.toDto(currencyRate);
    }

    /**
     * Get all the currencyRates.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurrencyRateDTO> findAll() {
        log.debug("Request to get all CurrencyRates");
        return currencyRateRepository.findAll().stream()
            .map(currencyRateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one currencyRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyRateDTO> findOne(Long id) {
        log.debug("Request to get CurrencyRate : {}", id);
        return currencyRateRepository.findById(id)
            .map(currencyRateMapper::toDto);
    }

    /**
     * Delete the currencyRate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrencyRate : {}", id);
        currencyRateRepository.deleteById(id);
    }
}
