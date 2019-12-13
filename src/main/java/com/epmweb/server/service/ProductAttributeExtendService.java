package com.epmweb.server.service;

import com.epmweb.server.service.dto.ProductAttributeDTO;

import java.security.Principal;
import java.util.List;

public interface ProductAttributeExtendService {
    List<ProductAttributeDTO> getAllProductAttributes(Long attributeSetId, Principal principal);
}
