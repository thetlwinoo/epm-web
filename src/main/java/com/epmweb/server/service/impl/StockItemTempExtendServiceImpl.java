package com.epmweb.server.service.impl;

import com.epmweb.server.repository.StockItemTempExtendRepository;
import com.epmweb.server.service.StockItemTempExtendService;
import com.epmweb.server.service.dto.StockItemTempDTO;
import com.epmweb.server.service.mapper.StockItemTempMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockItemTempExtendServiceImpl implements StockItemTempExtendService {

    private final Logger log = LoggerFactory.getLogger(StockItemTempExtendServiceImpl.class);
    private final StockItemTempExtendRepository stockItemTempExtendRepository;
    private final StockItemTempMapper stockItemTempMapper;


    public StockItemTempExtendServiceImpl(StockItemTempExtendRepository stockItemTempExtendRepository, StockItemTempMapper stockItemTempMapper) {
        this.stockItemTempExtendRepository = stockItemTempExtendRepository;
        this.stockItemTempMapper = stockItemTempMapper;
    }

    @Override
    public Page<StockItemTempDTO> getAllStockItemTempByTransactionId(Long transactionId, Pageable pageable) {
        return stockItemTempExtendRepository.findAllByUploadTransactionId(transactionId, pageable)
            .map(stockItemTempMapper::toDto);
    }
}
