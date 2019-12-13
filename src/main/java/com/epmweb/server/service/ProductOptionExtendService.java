package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductOptionDTO;

import java.security.Principal;
import java.util.List;

public interface ProductOptionExtendService {
    List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal);
}
