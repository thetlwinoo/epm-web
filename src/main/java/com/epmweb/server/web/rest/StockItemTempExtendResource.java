package com.epmweb.server.web.rest;

import com.epmweb.server.service.StockItemTempExtendService;
import com.epmweb.server.service.dto.StockItemTempDTO;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * StockItemTempExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class StockItemTempExtendResource {

    private final Logger log = LoggerFactory.getLogger(StockItemTempExtendResource.class);
    private final StockItemTempExtendService stockItemTempExtendService;

    public StockItemTempExtendResource(StockItemTempExtendService stockItemTempExtendService) {
        this.stockItemTempExtendService = stockItemTempExtendService;
    }

    @RequestMapping(value = "/stock-item-temp-extend", method = RequestMethod.GET, params = {"page", "size", "id"})
    public ResponseEntity<List<StockItemTempDTO>> getAllStockItemTemps(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize, @RequestParam("id") Long id) {
        log.debug("REST request to get all StockItemTemps");
        Page<StockItemTempDTO> pageStockItemTemp = stockItemTempExtendService.getAllStockItemTempByTransactionId(id, PageRequest.of(page, pageSize));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageStockItemTemp);
        return ResponseEntity.ok().headers(headers).body(pageStockItemTemp.getContent());
    }
}
