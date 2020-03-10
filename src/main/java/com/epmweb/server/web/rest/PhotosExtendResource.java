package com.epmweb.server.web.rest;

import com.epmweb.server.repository.PhotosExtendRepository;
import com.epmweb.server.service.PhotosExtendService;
import com.epmweb.server.service.dto.PhotosDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * PhotosExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class PhotosExtendResource {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendResource.class);
    private final PhotosExtendService photosExtendService;
    private final PhotosExtendRepository photosExtendRepository;
    private static final String ENTITY_NAME = "photos-extend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public PhotosExtendResource(PhotosExtendService photosExtendService, PhotosExtendRepository photosExtendRepository) {
        this.photosExtendService = photosExtendService;
        this.photosExtendRepository = photosExtendRepository;
    }

    @RequestMapping(value = "/photos-extend/stockitem/{id}/{handle}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable Long id, @PathVariable String handle) {
        Optional<PhotosDTO> photos = photosExtendService.getOneByStockItem(id);
        byte[] photo;
        HttpHeaders header = new HttpHeaders();
        switch (handle) {
            case "thumbnail":
                header.setContentType(MediaType.valueOf(photos.get().getThumbnailPhotoBlobContentType()));
                header.setContentLength(photos.get().getThumbnailPhotoBlob().length);
                photo = photos.get().getThumbnailPhotoBlob();
                break;
            case "original":
                header.setContentType(MediaType.valueOf(photos.get().getOriginalPhotoBlobContentType()));
                header.setContentLength(photos.get().getOriginalPhotoBlob().length);
                photo = photos.get().getOriginalPhotoBlob();
                break;
            default:
                header.setContentType(MediaType.valueOf(photos.get().getThumbnailPhotoBlobContentType()));
                header.setContentLength(photos.get().getThumbnailPhotoBlob().length);
                photo = photos.get().getThumbnailPhotoBlob();
        }

//        header.set("Content-Disposition", "attachment; filename=" + stockitem.toString() + ".png");

        return new ResponseEntity<>(photo, header, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/photos-extend/photos/{id}")
    public ResponseEntity<Void> deletePhotos(@PathVariable Long id) {
        photosExtendRepository.deletePhotos(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
