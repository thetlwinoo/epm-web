package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductChoiceDTO;

import java.util.List;

public interface ProductChoiceExtendService {
    List<ProductChoiceDTO> getAllProductChoice(Long categoryId);
}
