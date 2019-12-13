package com.epmweb.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductOptionExtendResource controller
 */
@RestController
@RequestMapping("/api/product-option-extend")
public class ProductOptionExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
