package com.epmweb.server.service.impl;

import com.epmweb.server.service.StockItemsService;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.repository.StockItemsRepository;
import com.epmweb.server.service.dto.StockItemsDTO;
import com.epmweb.server.service.mapper.StockItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link StockItems}.
 */
@Service
@Transactional
public class StockItemsServiceImpl implements StockItemsService {

    private final Logger log = LoggerFactory.getLogger(StockItemsServiceImpl.class);

    private final StockItemsRepository stockItemsRepository;

    private final StockItemsMapper stockItemsMapper;

    public StockItemsServiceImpl(StockItemsRepository stockItemsRepository, StockItemsMapper stockItemsMapper) {
        this.stockItemsRepository = stockItemsRepository;
        this.stockItemsMapper = stockItemsMapper;
    }

    /**
     * Save a stockItems.
     *
     * @param stockItemsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemsDTO save(StockItemsDTO stockItemsDTO) {
        log.debug("Request to save StockItems : {}", stockItemsDTO);
        StockItems stockItems = stockItemsMapper.toEntity(stockItemsDTO);
        stockItems = stockItemsRepository.save(stockItems);
        return stockItemsMapper.toDto(stockItems);
    }

    /**
     * Get all the stockItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockItems");
        return stockItemsRepository.findAll(pageable)
            .map(stockItemsMapper::toDto);
    }



    /**
    *  Get all the stockItems where StockItemHolding is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<StockItemsDTO> findAllWhereStockItemHoldingIsNull() {
        log.debug("Request to get all stockItems where StockItemHolding is null");
        return StreamSupport
            .stream(stockItemsRepository.findAll().spliterator(), false)
            .filter(stockItems -> stockItems.getStockItemHolding() == null)
            .map(stockItemsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one stockItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemsDTO> findOne(Long id) {
        log.debug("Request to get StockItems : {}", id);
        return stockItemsRepository.findById(id)
            .map(stockItemsMapper::toDto);
    }

    /**
     * Delete the stockItems by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItems : {}", id);
        stockItemsRepository.deleteById(id);
    }
}
