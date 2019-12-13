package com.epmweb.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductCategoryExtendResource controller
 */
@RestController
@RequestMapping("/api/product-category-extend")
public class ProductCategoryExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
