package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.PhotosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photos} and its DTO {@link PhotosDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class, ProductCategoryMapper.class})
public interface PhotosMapper extends EntityMapper<PhotosDTO, Photos> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    PhotosDTO toDto(Photos photos);

    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "productCategoryId", target = "productCategory")
    Photos toEntity(PhotosDTO photosDTO);

    default Photos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photos photos = new Photos();
        photos.setId(id);
        return photos;
    }
}
