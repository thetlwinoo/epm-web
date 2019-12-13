package com.epmweb.server.service.impl;

import com.epmweb.server.service.PersonPhoneService;
import com.epmweb.server.domain.PersonPhone;
import com.epmweb.server.repository.PersonPhoneRepository;
import com.epmweb.server.service.dto.PersonPhoneDTO;
import com.epmweb.server.service.mapper.PersonPhoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PersonPhone}.
 */
@Service
@Transactional
public class PersonPhoneServiceImpl implements PersonPhoneService {

    private final Logger log = LoggerFactory.getLogger(PersonPhoneServiceImpl.class);

    private final PersonPhoneRepository personPhoneRepository;

    private final PersonPhoneMapper personPhoneMapper;

    public PersonPhoneServiceImpl(PersonPhoneRepository personPhoneRepository, PersonPhoneMapper personPhoneMapper) {
        this.personPhoneRepository = personPhoneRepository;
        this.personPhoneMapper = personPhoneMapper;
    }

    /**
     * Save a personPhone.
     *
     * @param personPhoneDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonPhoneDTO save(PersonPhoneDTO personPhoneDTO) {
        log.debug("Request to save PersonPhone : {}", personPhoneDTO);
        PersonPhone personPhone = personPhoneMapper.toEntity(personPhoneDTO);
        personPhone = personPhoneRepository.save(personPhone);
        return personPhoneMapper.toDto(personPhone);
    }

    /**
     * Get all the personPhones.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonPhoneDTO> findAll() {
        log.debug("Request to get all PersonPhones");
        return personPhoneRepository.findAll().stream()
            .map(personPhoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personPhone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonPhoneDTO> findOne(Long id) {
        log.debug("Request to get PersonPhone : {}", id);
        return personPhoneRepository.findById(id)
            .map(personPhoneMapper::toDto);
    }

    /**
     * Delete the personPhone by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonPhone : {}", id);
        personPhoneRepository.deleteById(id);
    }
}
