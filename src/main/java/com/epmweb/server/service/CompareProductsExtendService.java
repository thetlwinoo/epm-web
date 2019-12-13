package com.epmweb.server.service;

import com.epmweb.server.service.dto.ComparesDTO;

import java.security.Principal;

public interface CompareProductsExtendService {
    ComparesDTO addToCompare(Principal principal, Long id);

    ComparesDTO fetchCompare(Principal principal);

    ComparesDTO removeFromCompare(Principal principal, Long id);

    void emptyCompare(Principal principal);

    Boolean isInCompare(Principal principal, Long id);
}
