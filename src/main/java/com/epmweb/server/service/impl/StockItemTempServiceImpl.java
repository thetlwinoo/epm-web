package com.epmweb.server.service.impl;

import com.epmweb.server.service.StockItemTempService;
import com.epmweb.server.domain.StockItemTemp;
import com.epmweb.server.repository.StockItemTempRepository;
import com.epmweb.server.service.dto.StockItemTempDTO;
import com.epmweb.server.service.mapper.StockItemTempMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StockItemTemp}.
 */
@Service
@Transactional
public class StockItemTempServiceImpl implements StockItemTempService {

    private final Logger log = LoggerFactory.getLogger(StockItemTempServiceImpl.class);

    private final StockItemTempRepository stockItemTempRepository;

    private final StockItemTempMapper stockItemTempMapper;

    public StockItemTempServiceImpl(StockItemTempRepository stockItemTempRepository, StockItemTempMapper stockItemTempMapper) {
        this.stockItemTempRepository = stockItemTempRepository;
        this.stockItemTempMapper = stockItemTempMapper;
    }

    /**
     * Save a stockItemTemp.
     *
     * @param stockItemTempDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockItemTempDTO save(StockItemTempDTO stockItemTempDTO) {
        log.debug("Request to save StockItemTemp : {}", stockItemTempDTO);
        StockItemTemp stockItemTemp = stockItemTempMapper.toEntity(stockItemTempDTO);
        stockItemTemp = stockItemTempRepository.save(stockItemTemp);
        return stockItemTempMapper.toDto(stockItemTemp);
    }

    /**
     * Get all the stockItemTemps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockItemTempDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockItemTemps");
        return stockItemTempRepository.findAll(pageable)
            .map(stockItemTempMapper::toDto);
    }


    /**
     * Get one stockItemTemp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockItemTempDTO> findOne(Long id) {
        log.debug("Request to get StockItemTemp : {}", id);
        return stockItemTempRepository.findById(id)
            .map(stockItemTempMapper::toDto);
    }

    /**
     * Delete the stockItemTemp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItemTemp : {}", id);
        stockItemTempRepository.deleteById(id);
    }
}
