package com.epmweb.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductChoiceExtendResource controller
 */
@RestController
@RequestMapping("/api/product-choice-extend")
public class ProductChoiceExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
