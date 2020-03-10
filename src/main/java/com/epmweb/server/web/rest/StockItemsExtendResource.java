package com.epmweb.server.web.rest;

import com.epmweb.server.repository.StockItemsExtendRepository;
import com.epmweb.server.service.ProductsExtendService;
import com.epmweb.server.service.StockItemsExtendService;
import com.epmweb.server.service.StockItemsQueryService;
import com.epmweb.server.service.SuppliersExtendService;
import com.epmweb.server.service.dto.PhotosDTO;
import com.epmweb.server.service.dto.StockItemsCriteria;
import com.epmweb.server.service.dto.StockItemsDTO;
import com.epmweb.server.service.dto.SuppliersDTO;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * StockItemsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class StockItemsExtendResource {

    private final Logger log = LoggerFactory.getLogger(StockItemsExtendResource.class);
    private final StockItemsExtendService stockItemsExtendService;
    private final StockItemsQueryService stockItemsQueryService;
    private final SuppliersExtendService suppliersExtendService;
    private final ProductsExtendService productsExtendService;
    private final StockItemsExtendRepository stockItemsExtendRepository;
    private static final String ENTITY_NAME = "stockItemsExtend";

    public StockItemsExtendResource(StockItemsExtendService stockItemsExtendService, StockItemsQueryService stockItemsQueryService, SuppliersExtendService suppliersExtendService, ProductsExtendService productsExtendService, StockItemsExtendRepository stockItemsExtendRepository) {
        this.stockItemsExtendService = stockItemsExtendService;
        this.stockItemsQueryService = stockItemsQueryService;
        this.suppliersExtendService = suppliersExtendService;
        this.productsExtendService = productsExtendService;
        this.stockItemsExtendRepository = stockItemsExtendRepository;
    }

    @PostMapping("/stock-items-extend/photos")
    public ResponseEntity<PhotosDTO> addPhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to save Photos : {}", photosDTO);
        if (photosDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhotosDTO result = stockItemsExtendService.addPhotos(photosDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/stock-items-extend/photos")
    public ResponseEntity<PhotosDTO> updatePhotos(@Valid @RequestBody PhotosDTO photosDTO) throws URISyntaxException {
        log.debug("REST request to update photos : {}", photosDTO);
        if (photosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhotosDTO result = stockItemsExtendService.updatePhotos(photosDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/stock-items-extend/filter/vendor")
    public ResponseEntity<List<StockItemsDTO>> getAllStockItems(StockItemsCriteria criteria, Pageable pageable, Principal principal) {
        log.debug("REST request to get StockItems by criteria: {}", criteria);
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);
        List<Long> productIds = productsExtendService.getProductIdsBySupplier(suppliersDTOOptional.get().getId());
        if (productIds.size() > 0) {
            LongFilter productIdsFilter = new LongFilter();
            productIdsFilter.setIn(productIds);
            criteria.setProductId(productIdsFilter);
        }


        Page<StockItemsDTO> page = stockItemsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/stock-items-extend/count")
    public Map<String, Long> getAllCount(Principal principal) {
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);

        if (!suppliersDTOOptional.isPresent()) {
            throw new IllegalArgumentException("Supplier not found");
        }

        Long allCount = stockItemsExtendRepository.findAllCount(suppliersDTOOptional.get().getId());
        Long activeCount = stockItemsExtendRepository.findAllCountByActive(suppliersDTOOptional.get().getId(), true);
        Long inActiveCount = stockItemsExtendRepository.findAllCountByActive(suppliersDTOOptional.get().getId(), false);
        Long imageMissingCount = Long.parseLong("0");
        Long soldOutCount = stockItemsExtendRepository.findAllCountSoldOut(suppliersDTOOptional.get().getId());

        Map<String, Long> response = new HashMap<String, Long>();
        response.put("all", allCount);
        response.put("active", activeCount);
        response.put("inactive", inActiveCount);
//        response.put("imagemissing", imageMissingCount);
        response.put("soldout", soldOutCount);

        return response;
    }

    @GetMapping("/stock-items-extend/count/vendor/active")
    public Long getAllCountByActive(@RequestParam("activeInd") Boolean activeInd, Principal principal) {
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);
        Long count = stockItemsExtendRepository.findAllCountByActive(suppliersDTOOptional.get().getId(), activeInd);

        return count;
    }
}
