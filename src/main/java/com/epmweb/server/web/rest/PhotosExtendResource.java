package com.epmweb.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PhotosExtendResource controller
 */
@RestController
@RequestMapping("/api/photos-extend")
public class PhotosExtendResource {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
