package com.epmweb.server.service.impl;

import com.epmweb.server.service.AddressTypesService;
import com.epmweb.server.domain.AddressTypes;
import com.epmweb.server.repository.AddressTypesRepository;
import com.epmweb.server.service.dto.AddressTypesDTO;
import com.epmweb.server.service.mapper.AddressTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AddressTypes}.
 */
@Service
@Transactional
public class AddressTypesServiceImpl implements AddressTypesService {

    private final Logger log = LoggerFactory.getLogger(AddressTypesServiceImpl.class);

    private final AddressTypesRepository addressTypesRepository;

    private final AddressTypesMapper addressTypesMapper;

    public AddressTypesServiceImpl(AddressTypesRepository addressTypesRepository, AddressTypesMapper addressTypesMapper) {
        this.addressTypesRepository = addressTypesRepository;
        this.addressTypesMapper = addressTypesMapper;
    }

    /**
     * Save a addressTypes.
     *
     * @param addressTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressTypesDTO save(AddressTypesDTO addressTypesDTO) {
        log.debug("Request to save AddressTypes : {}", addressTypesDTO);
        AddressTypes addressTypes = addressTypesMapper.toEntity(addressTypesDTO);
        addressTypes = addressTypesRepository.save(addressTypes);
        return addressTypesMapper.toDto(addressTypes);
    }

    /**
     * Get all the addressTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressTypesDTO> findAll() {
        log.debug("Request to get all AddressTypes");
        return addressTypesRepository.findAll().stream()
            .map(addressTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one addressTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressTypesDTO> findOne(Long id) {
        log.debug("Request to get AddressTypes : {}", id);
        return addressTypesRepository.findById(id)
            .map(addressTypesMapper::toDto);
    }

    /**
     * Delete the addressTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressTypes : {}", id);
        addressTypesRepository.deleteById(id);
    }
}
