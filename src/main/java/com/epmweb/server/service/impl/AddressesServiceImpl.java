package com.epmweb.server.service.impl;

import com.epmweb.server.service.AddressesService;
import com.epmweb.server.domain.Addresses;
import com.epmweb.server.repository.AddressesRepository;
import com.epmweb.server.service.dto.AddressesDTO;
import com.epmweb.server.service.mapper.AddressesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Addresses}.
 */
@Service
@Transactional
public class AddressesServiceImpl implements AddressesService {

    private final Logger log = LoggerFactory.getLogger(AddressesServiceImpl.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    public AddressesServiceImpl(AddressesRepository addressesRepository, AddressesMapper addressesMapper) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
    }

    /**
     * Save a addresses.
     *
     * @param addressesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressesDTO save(AddressesDTO addressesDTO) {
        log.debug("Request to save Addresses : {}", addressesDTO);
        Addresses addresses = addressesMapper.toEntity(addressesDTO);
        addresses = addressesRepository.save(addresses);
        return addressesMapper.toDto(addresses);
    }

    /**
     * Get all the addresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressesDTO> findAll() {
        log.debug("Request to get all Addresses");
        return addressesRepository.findAll().stream()
            .map(addressesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one addresses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressesDTO> findOne(Long id) {
        log.debug("Request to get Addresses : {}", id);
        return addressesRepository.findById(id)
            .map(addressesMapper::toDto);
    }

    /**
     * Delete the addresses by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Addresses : {}", id);
        addressesRepository.deleteById(id);
    }
}
