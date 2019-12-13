package com.epmweb.server.service;

import com.epmweb.server.service.dto.OrdersDTO;
import com.epmweb.server.service.dto.ReviewLinesDTO;
import com.epmweb.server.service.dto.ReviewsDTO;

import java.security.Principal;
import java.util.List;

public interface ReviewsExtendService {
    ReviewsDTO save(Principal principal, ReviewsDTO reviewsDTO, Long orderId);

    List<OrdersDTO> findAllOrderedProducts(Principal principal);

    List<ReviewLinesDTO> findReviewLinesByStockItemId(Long stockItemId);

    ReviewsDTO completedReview(Long orderId);
}
