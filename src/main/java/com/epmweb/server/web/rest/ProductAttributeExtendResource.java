package com.epmweb.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductAttributeExtendResource controller
 */
@RestController
@RequestMapping("/api/product-attribute-extend")
public class ProductAttributeExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
