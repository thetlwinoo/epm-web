package com.epmweb.server.service.impl;

import com.epmweb.server.service.BusinessEntityAddressService;
import com.epmweb.server.domain.BusinessEntityAddress;
import com.epmweb.server.repository.BusinessEntityAddressRepository;
import com.epmweb.server.service.dto.BusinessEntityAddressDTO;
import com.epmweb.server.service.mapper.BusinessEntityAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BusinessEntityAddress}.
 */
@Service
@Transactional
public class BusinessEntityAddressServiceImpl implements BusinessEntityAddressService {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityAddressServiceImpl.class);

    private final BusinessEntityAddressRepository businessEntityAddressRepository;

    private final BusinessEntityAddressMapper businessEntityAddressMapper;

    public BusinessEntityAddressServiceImpl(BusinessEntityAddressRepository businessEntityAddressRepository, BusinessEntityAddressMapper businessEntityAddressMapper) {
        this.businessEntityAddressRepository = businessEntityAddressRepository;
        this.businessEntityAddressMapper = businessEntityAddressMapper;
    }

    /**
     * Save a businessEntityAddress.
     *
     * @param businessEntityAddressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BusinessEntityAddressDTO save(BusinessEntityAddressDTO businessEntityAddressDTO) {
        log.debug("Request to save BusinessEntityAddress : {}", businessEntityAddressDTO);
        BusinessEntityAddress businessEntityAddress = businessEntityAddressMapper.toEntity(businessEntityAddressDTO);
        businessEntityAddress = businessEntityAddressRepository.save(businessEntityAddress);
        return businessEntityAddressMapper.toDto(businessEntityAddress);
    }

    /**
     * Get all the businessEntityAddresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessEntityAddressDTO> findAll() {
        log.debug("Request to get all BusinessEntityAddresses");
        return businessEntityAddressRepository.findAll().stream()
            .map(businessEntityAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one businessEntityAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessEntityAddressDTO> findOne(Long id) {
        log.debug("Request to get BusinessEntityAddress : {}", id);
        return businessEntityAddressRepository.findById(id)
            .map(businessEntityAddressMapper::toDto);
    }

    /**
     * Delete the businessEntityAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessEntityAddress : {}", id);
        businessEntityAddressRepository.deleteById(id);
    }
}
