package com.epmweb.server.service.impl;

import com.epmweb.server.service.StockItemTransactionsService;
import com.epmweb.server.domain.StockItemTransactions;
import com.epmweb.server.repository.StockItemTransactionsRepository;
import com.epmweb.server.service.dto.StockItemTransactionsDTO;
import com.epmweb.server.service.mapper.StockItemTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StockItemTransactions}.
 */
@Service
@Transactional
public class StockItemTransactionsServiceImpl implements StockItemTransactionsService {

    private final Logger log = LoggerFactory.getLogger(StockItemTransactionsServiceImpl.class);

    private final StockItemTransactionsRepository stockItemTransactionsRepository;

    private final StockItemTransactionsMapper stockItemTransactionsMapper;

    public StockItemTransactionsServiceImpl(StockItemTransactionsRepository stockItemTransactionsRepository, StockItemTransactionsMapper stockItemTransactionsMapper) {
        this.stockItemTransactionsRepository = stockItemTransactionsRepository;
        this.stockItemTransactionsMapper = stockItemTransactionsMapper;
    }

    /**
     * Save a stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemTransactionsDTO save(StockItemTransactionsDTO stockItemTransactionsDTO) {
        log.debug("Request to save StockItemTransactions : {}", stockItemTransactionsDTO);
        StockItemTransactions stockItemTransactions = stockItemTransactionsMapper.toEntity(stockItemTransactionsDTO);
        stockItemTransactions = stockItemTransactionsRepository.save(stockItemTransactions);
        return stockItemTransactionsMapper.toDto(stockItemTransactions);
    }

    /**
     * Get all the stockItemTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemTransactionsDTO> findAll() {
        log.debug("Request to get all StockItemTransactions");
        return stockItemTransactionsRepository.findAll().stream()
            .map(stockItemTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stockItemTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemTransactionsDTO> findOne(Long id) {
        log.debug("Request to get StockItemTransactions : {}", id);
        return stockItemTransactionsRepository.findById(id)
            .map(stockItemTransactionsMapper::toDto);
    }

    /**
     * Delete the stockItemTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemTransactions : {}", id);
        stockItemTransactionsRepository.deleteById(id);
    }
}
