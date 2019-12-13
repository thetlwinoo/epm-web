package com.epmweb.server.service;

import com.epmweb.server.service.dto.PhotosDTO;

import java.util.List;
import java.util.Optional;

public interface PhotosExtendService {
    Optional<PhotosDTO> findByStockItemsAndAndDefaultIndIsTrue(Long stockItemId);

    Optional<PhotosDTO> getOneByStockItem(Long stockItemId);

    Optional<PhotosDTO> setDefault(Long photoId);

    List<PhotosDTO> getPhotosByStockItem(Long stockItemId);
}
