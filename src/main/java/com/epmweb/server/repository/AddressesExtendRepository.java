package com.epmweb.server.repository;

import com.epmweb.server.domain.Addresses;

import java.util.List;

public interface AddressesExtendRepository extends AddressesRepository {
    List<Addresses> findAllByPersonId(Long id);
}
