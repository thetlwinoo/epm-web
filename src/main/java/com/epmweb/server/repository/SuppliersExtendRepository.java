package com.epmweb.server.repository;

import com.epmweb.server.domain.Suppliers;

import java.util.Optional;

public interface SuppliersExtendRepository extends SuppliersRepository  {
    Optional<Suppliers> findSuppliersByUserId(String userId);
}
