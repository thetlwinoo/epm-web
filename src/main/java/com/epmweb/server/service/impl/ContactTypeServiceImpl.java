package com.epmweb.server.service.impl;

import com.epmweb.server.service.ContactTypeService;
import com.epmweb.server.domain.ContactType;
import com.epmweb.server.repository.ContactTypeRepository;
import com.epmweb.server.service.dto.ContactTypeDTO;
import com.epmweb.server.service.mapper.ContactTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ContactType}.
 */
@Service
@Transactional
public class ContactTypeServiceImpl implements ContactTypeService {

    private final Logger log = LoggerFactory.getLogger(ContactTypeServiceImpl.class);

    private final ContactTypeRepository contactTypeRepository;

    private final ContactTypeMapper contactTypeMapper;

    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository, ContactTypeMapper contactTypeMapper) {
        this.contactTypeRepository = contactTypeRepository;
        this.contactTypeMapper = contactTypeMapper;
    }

    /**
     * Save a contactType.
     *
     * @param contactTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ContactTypeDTO save(ContactTypeDTO contactTypeDTO) {
        log.debug("Request to save ContactType : {}", contactTypeDTO);
        ContactType contactType = contactTypeMapper.toEntity(contactTypeDTO);
        contactType = contactTypeRepository.save(contactType);
        return contactTypeMapper.toDto(contactType);
    }

    /**
     * Get all the contactTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactTypeDTO> findAll() {
        log.debug("Request to get all ContactTypes");
        return contactTypeRepository.findAll().stream()
            .map(contactTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contactType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactTypeDTO> findOne(Long id) {
        log.debug("Request to get ContactType : {}", id);
        return contactTypeRepository.findById(id)
            .map(contactTypeMapper::toDto);
    }

    /**
     * Delete the contactType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactType : {}", id);
        contactTypeRepository.deleteById(id);
    }
}
