package com.epmweb.server.service;

import com.epmweb.server.service.dto.AddressesDTO;

import java.security.Principal;
import java.util.List;

public interface AddressesExtendService {
    List<AddressesDTO> fetchAddresses(Principal principal);

    void clearDefaultAddress(Principal principal);

    void setDefaultAddress(Principal principal, Long addressId);

    AddressesDTO crateAddresses(AddressesDTO addressesDTO, Principal principal);

    AddressesDTO updateAddresses(AddressesDTO addressesDTO, Principal principal);
}
