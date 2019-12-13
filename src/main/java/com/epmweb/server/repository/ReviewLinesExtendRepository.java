package com.epmweb.server.repository;

import com.epmweb.server.domain.ReviewLines;

import java.util.List;

public interface ReviewLinesExtendRepository extends ReviewLinesRepository {
    List<ReviewLines> findAllByStockItemId(Long stockItemId);
}
