package com.epmweb.server.service.impl;

import com.epmweb.server.service.PhoneNumberTypeService;
import com.epmweb.server.domain.PhoneNumberType;
import com.epmweb.server.repository.PhoneNumberTypeRepository;
import com.epmweb.server.service.dto.PhoneNumberTypeDTO;
import com.epmweb.server.service.mapper.PhoneNumberTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PhoneNumberType}.
 */
@Service
@Transactional
public class PhoneNumberTypeServiceImpl implements PhoneNumberTypeService {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberTypeServiceImpl.class);

    private final PhoneNumberTypeRepository phoneNumberTypeRepository;

    private final PhoneNumberTypeMapper phoneNumberTypeMapper;

    public PhoneNumberTypeServiceImpl(PhoneNumberTypeRepository phoneNumberTypeRepository, PhoneNumberTypeMapper phoneNumberTypeMapper) {
        this.phoneNumberTypeRepository = phoneNumberTypeRepository;
        this.phoneNumberTypeMapper = phoneNumberTypeMapper;
    }

    /**
     * Save a phoneNumberType.
     *
     * @param phoneNumberTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PhoneNumberTypeDTO save(PhoneNumberTypeDTO phoneNumberTypeDTO) {
        log.debug("Request to save PhoneNumberType : {}", phoneNumberTypeDTO);
        PhoneNumberType phoneNumberType = phoneNumberTypeMapper.toEntity(phoneNumberTypeDTO);
        phoneNumberType = phoneNumberTypeRepository.save(phoneNumberType);
        return phoneNumberTypeMapper.toDto(phoneNumberType);
    }

    /**
     * Get all the phoneNumberTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhoneNumberTypeDTO> findAll() {
        log.debug("Request to get all PhoneNumberTypes");
        return phoneNumberTypeRepository.findAll().stream()
            .map(phoneNumberTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one phoneNumberType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneNumberTypeDTO> findOne(Long id) {
        log.debug("Request to get PhoneNumberType : {}", id);
        return phoneNumberTypeRepository.findById(id)
            .map(phoneNumberTypeMapper::toDto);
    }

    /**
     * Delete the phoneNumberType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhoneNumberType : {}", id);
        phoneNumberTypeRepository.deleteById(id);
    }
}
