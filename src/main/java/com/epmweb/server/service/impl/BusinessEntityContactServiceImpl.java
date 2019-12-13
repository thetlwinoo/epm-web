package com.epmweb.server.service.impl;

import com.epmweb.server.service.BusinessEntityContactService;
import com.epmweb.server.domain.BusinessEntityContact;
import com.epmweb.server.repository.BusinessEntityContactRepository;
import com.epmweb.server.service.dto.BusinessEntityContactDTO;
import com.epmweb.server.service.mapper.BusinessEntityContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BusinessEntityContact}.
 */
@Service
@Transactional
public class BusinessEntityContactServiceImpl implements BusinessEntityContactService {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityContactServiceImpl.class);

    private final BusinessEntityContactRepository businessEntityContactRepository;

    private final BusinessEntityContactMapper businessEntityContactMapper;

    public BusinessEntityContactServiceImpl(BusinessEntityContactRepository businessEntityContactRepository, BusinessEntityContactMapper businessEntityContactMapper) {
        this.businessEntityContactRepository = businessEntityContactRepository;
        this.businessEntityContactMapper = businessEntityContactMapper;
    }

    /**
     * Save a businessEntityContact.
     *
     * @param businessEntityContactDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BusinessEntityContactDTO save(BusinessEntityContactDTO businessEntityContactDTO) {
        log.debug("Request to save BusinessEntityContact : {}", businessEntityContactDTO);
        BusinessEntityContact businessEntityContact = businessEntityContactMapper.toEntity(businessEntityContactDTO);
        businessEntityContact = businessEntityContactRepository.save(businessEntityContact);
        return businessEntityContactMapper.toDto(businessEntityContact);
    }

    /**
     * Get all the businessEntityContacts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BusinessEntityContactDTO> findAll() {
        log.debug("Request to get all BusinessEntityContacts");
        return businessEntityContactRepository.findAll().stream()
            .map(businessEntityContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one businessEntityContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessEntityContactDTO> findOne(Long id) {
        log.debug("Request to get BusinessEntityContact : {}", id);
        return businessEntityContactRepository.findById(id)
            .map(businessEntityContactMapper::toDto);
    }

    /**
     * Delete the businessEntityContact by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessEntityContact : {}", id);
        businessEntityContactRepository.deleteById(id);
    }
}
