package com.epmweb.server.service;

import com.epmweb.server.service.dto.SuppliersDTO;

import java.security.Principal;
import java.util.Optional;

public interface SuppliersExtendService {
    Optional<SuppliersDTO> getSupplierByPrincipal(Principal principal);
}
