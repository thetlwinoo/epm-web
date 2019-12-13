package com.epmweb.server.service;

import com.epmweb.server.service.dto.StockItemTempDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockItemTempExtendService {
    Page<StockItemTempDTO> getAllStockItemTempByTransactionId(Long transactionId, Pageable pageable);
}
