package com.epmweb.server.service.impl;

import com.epmweb.server.service.StockItemHoldingsService;
import com.epmweb.server.domain.StockItemHoldings;
import com.epmweb.server.repository.StockItemHoldingsRepository;
import com.epmweb.server.service.dto.StockItemHoldingsDTO;
import com.epmweb.server.service.mapper.StockItemHoldingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StockItemHoldings}.
 */
@Service
@Transactional
public class StockItemHoldingsServiceImpl implements StockItemHoldingsService {

    private final Logger log = LoggerFactory.getLogger(StockItemHoldingsServiceImpl.class);

    private final StockItemHoldingsRepository stockItemHoldingsRepository;

    private final StockItemHoldingsMapper stockItemHoldingsMapper;

    public StockItemHoldingsServiceImpl(StockItemHoldingsRepository stockItemHoldingsRepository, StockItemHoldingsMapper stockItemHoldingsMapper) {
        this.stockItemHoldingsRepository = stockItemHoldingsRepository;
        this.stockItemHoldingsMapper = stockItemHoldingsMapper;
    }

    /**
     * Save a stockItemHoldings.
     *
     * @param stockItemHoldingsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemHoldingsDTO save(StockItemHoldingsDTO stockItemHoldingsDTO) {
        log.debug("Request to save StockItemHoldings : {}", stockItemHoldingsDTO);
        StockItemHoldings stockItemHoldings = stockItemHoldingsMapper.toEntity(stockItemHoldingsDTO);
        stockItemHoldings = stockItemHoldingsRepository.save(stockItemHoldings);
        return stockItemHoldingsMapper.toDto(stockItemHoldings);
    }

    /**
     * Get all the stockItemHoldings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemHoldingsDTO> findAll() {
        log.debug("Request to get all StockItemHoldings");
        return stockItemHoldingsRepository.findAll().stream()
            .map(stockItemHoldingsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemHoldings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemHoldingsDTO> findOne(Long id) {
        log.debug("Request to get StockItemHoldings : {}", id);
        return stockItemHoldingsRepository.findById(id)
            .map(stockItemHoldingsMapper::toDto);
    }

    /**
     * Delete the stockItemHoldings by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemHoldings : {}", id);
        stockItemHoldingsRepository.deleteById(id);
    }
}
