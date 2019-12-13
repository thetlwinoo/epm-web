package com.epmweb.server.service.mapper;

import com.epmweb.server.domain.*;
import com.epmweb.server.service.dto.ReviewLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReviewLines} and its DTO {@link ReviewLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReviewsMapper.class})
public interface ReviewLinesMapper extends EntityMapper<ReviewLinesDTO, ReviewLines> {

    @Mapping(source = "review.id", target = "reviewId")
    ReviewLinesDTO toDto(ReviewLines reviewLines);

    @Mapping(target = "stockItem", ignore = true)
    @Mapping(source = "reviewId", target = "review")
    ReviewLines toEntity(ReviewLinesDTO reviewLinesDTO);

    default ReviewLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReviewLines reviewLines = new ReviewLines();
        reviewLines.setId(id);
        return reviewLines;
    }
}
