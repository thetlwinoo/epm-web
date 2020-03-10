package com.epmweb.server.web.rest;

import com.epmweb.server.service.ProductAttributeExtendService;
import com.epmweb.server.service.dto.ProductAttributeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * ProductAttributeExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ProductAttributeExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeExtendResource.class);
    private final ProductAttributeExtendService productAttributeExtendService;

    public ProductAttributeExtendResource(ProductAttributeExtendService productAttributeExtendService) {
        this.productAttributeExtendService = productAttributeExtendService;
    }

    @GetMapping("/product-attribute-extend/product-attribute-extend")
    public List<ProductAttributeDTO> getAllProductAttributes(@RequestParam(value = "attributeSetId", required = true) Long attributeSetId, Principal principal) {
        log.debug("REST request to get all ProductAttributes");
        return productAttributeExtendService.getAllProductAttributes(attributeSetId, principal);
    }
}
