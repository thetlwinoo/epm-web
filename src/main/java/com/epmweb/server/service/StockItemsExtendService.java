package com.epmweb.server.service;

import com.epmweb.server.service.dto.PhotosDTO;

public interface StockItemsExtendService {
    PhotosDTO addPhotos(PhotosDTO photosDTO);

    PhotosDTO updatePhotos(PhotosDTO photosDTO);
}
