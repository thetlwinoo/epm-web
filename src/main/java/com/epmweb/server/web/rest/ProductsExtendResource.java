package com.epmweb.server.web.rest;

import com.epmweb.server.domain.Products;
import com.epmweb.server.domain.StockItems;
import com.epmweb.server.repository.ProductsExtendRepository;
import com.epmweb.server.service.ProductsExtendService;
import com.epmweb.server.service.ProductsQueryService;
import com.epmweb.server.service.ProductsService;
import com.epmweb.server.service.StockItemsService;
import com.epmweb.server.service.dto.ProductCategoryDTO;
import com.epmweb.server.service.dto.ProductsCriteria;
import com.epmweb.server.service.dto.ProductsDTO;
import com.epmweb.server.service.dto.StockItemsDTO;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * ProductsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ProductsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendResource.class);
    private final ProductsExtendService productExtendService;
    private final ProductsService productsService;
    private final ProductsQueryService productsQueryService;
    private final ProductsExtendRepository productsExtendRepository;
    private final StockItemsService stockItemsService;
    private static final String ENTITY_NAME = "products-extend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    public ProductsExtendResource(ProductsExtendService productExtendService, ProductsService productsService, ProductsQueryService productsQueryService, ProductsExtendRepository productsExtendRepository, StockItemsService stockItemsService) {
        this.productExtendService = productExtendService;
        this.productsService = productsService;
        this.productsQueryService = productsQueryService;
        this.productsExtendRepository = productsExtendRepository;
        this.stockItemsService = stockItemsService;
    }

    @PostMapping("/products-extend/products")
    public ResponseEntity<ProductsDTO> createProducts(@Valid @RequestBody Products products, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Products : {}", products);
        if (products.getId() != null) {
            throw new BadRequestAlertException("A new products cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String _serverUrl = request.getRequestURL().toString().replace("/products-extend/products", "");
        try {
            ProductsDTO result = productExtendService.save(products, _serverUrl);

            return ResponseEntity.created(new URI("/products-extend/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }
//        return ResponseEntity.ok().body(result);

    }

    @PutMapping("/products-extend/products")
    public ResponseEntity<ProductsDTO> updateProducts(@Valid @RequestBody Products products, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Products : {}", products);
        if (products.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        String _serverUrl = request.getRequestURL().toString().replace("/products-extend/products", "");
        try {
            ProductsDTO result = productExtendService.save(products, _serverUrl);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }
    }

    @PutMapping("/products-extend/products/stock-item")
    public ResponseEntity updateStockItemActive(@RequestParam("stockItemId") Long stockItemId, @RequestParam("isActive") Boolean isActive) {
        try {

            Optional<StockItemsDTO> stockItemsDTOOptional = stockItemsService.findOne(stockItemId);
            Products saveProduct = new Products();
            if (stockItemsDTOOptional.isPresent()) {
                saveProduct = productsExtendRepository.findProductsById(stockItemsDTOOptional.get().getProductId());

                Boolean isActiveInd = false;
                for (StockItems stockItems : saveProduct.getStockItemLists()) {
                    if (stockItems.getId().equals(stockItemId)) {
                        stockItems.setActiveInd(isActive);
                    }

                    if (stockItems.isActiveInd() == null) {
                        stockItems.setActiveInd(false);
                    }

                    if (stockItems.isActiveInd() == true) {
                        isActiveInd = true;
                    }
                }

                saveProduct.setActiveInd(isActiveInd);
                saveProduct = productsExtendRepository.save(saveProduct);
            }

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saveProduct.getId().toString()))
                .body(saveProduct);
        } catch (Exception ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }
    }

    @RequestMapping(value = "/products-extend/{id}", method = RequestMethod.GET)
    public ResponseEntity getFullById(@PathVariable Long id) {
        Optional<ProductsDTO> productsDTO = productsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsDTO);
    }

    @RequestMapping(value = "/products-extend/related", method = RequestMethod.GET, params = "id")
    public ResponseEntity getByRelated(@RequestParam("id") Long id) {
        ProductsDTO productsDTO = productsService.findOne(id).get();
        if (productsDTO == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List>(productExtendService.getRelatedProducts(productsDTO.getProductCategoryId(), id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/related/categories", method = RequestMethod.GET)
    public ResponseEntity<List<ProductCategoryDTO>> getRelatedCategories(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<ProductCategoryDTO> entityList = this.productExtendService.getRelatedCategories(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/related/colors", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRelatedColors(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<String> entityList = this.productExtendService.getRelatedColors(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/related/brands", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getRelatedBrands(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        List<String> entityList = this.productExtendService.getRelatedBrands(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/related/pricerange", method = RequestMethod.GET)
    public ResponseEntity<Object> getRelatedPriceRange(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) Long category
    ) {
        Object entityList = this.productExtendService.getRelatedPriceRange(keyword, category);
        return ResponseEntity.ok().body(entityList);
    }

    @RequestMapping(value = "/products-extend/recent", method = RequestMethod.GET)
    public ResponseEntity getByNewlyAdded() {
        List returnList = productExtendService.findTop18ByOrderByLastEditedWhenDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/dailydiscover", method = RequestMethod.GET)
    public ResponseEntity getByDailyDiscover() {
        List returnList = productExtendService.findTop18ByOrderByLastEditedWhenDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/mostselling", method = RequestMethod.GET)
    public ResponseEntity getByMostSelling() {
        List returnList = productExtendService.findTop12ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/interested", method = RequestMethod.GET)
    public ResponseEntity getByInterested() {
        List returnList = productExtendService.findTop12ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/searchall", method = RequestMethod.GET, params = {"keyword"})
    public ResponseEntity searchAll(@RequestParam(value = "keyword", required = false) String keyword) {

        List returnList;

        returnList = productExtendService.searchProductsAll(keyword);

        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/products-extend/search", method = RequestMethod.GET)
    public ResponseEntity<List<ProductsDTO>> search(ProductsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);

        Page<ProductsDTO> page = productsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    private boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }

    private Sort getSort(String sort) {
        switch (sort) {
            case "lowest":
                return new Sort(Sort.Direction.ASC, "price");
            case "highest":
                return new Sort(Sort.Direction.DESC, "price");
            case "name":
                return new Sort(Sort.Direction.ASC, "name");
            default:
                return null;
        }
    }
}
