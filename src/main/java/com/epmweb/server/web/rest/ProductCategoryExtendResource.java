package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductCategoryExtendService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductCategoryExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendResource.class);
    private final ProductCategoryExtendService productCategoryExtendService;

    public ProductCategoryExtendResource(ProductCategoryExtendService productCategoryExtendService) {
        this.productCategoryExtendService = productCategoryExtendService;
    }

    @GetMapping("/product-categories-extend")
    public ResponseEntity getAllProductCategories() {
        JsonNode returnValue = productCategoryExtendService.getAllProductCategories();
        return ResponseEntity.ok().body(returnValue);
    }
}
