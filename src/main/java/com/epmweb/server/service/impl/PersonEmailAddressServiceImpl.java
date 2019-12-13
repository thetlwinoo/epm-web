package com.epmweb.server.service.impl;

import com.epmweb.server.service.PersonEmailAddressService;
import com.epmweb.server.domain.PersonEmailAddress;
import com.epmweb.server.repository.PersonEmailAddressRepository;
import com.epmweb.server.service.dto.PersonEmailAddressDTO;
import com.epmweb.server.service.mapper.PersonEmailAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PersonEmailAddress}.
 */
@Service
@Transactional
public class PersonEmailAddressServiceImpl implements PersonEmailAddressService {

    private final Logger log = LoggerFactory.getLogger(PersonEmailAddressServiceImpl.class);

    private final PersonEmailAddressRepository personEmailAddressRepository;

    private final PersonEmailAddressMapper personEmailAddressMapper;

    public PersonEmailAddressServiceImpl(PersonEmailAddressRepository personEmailAddressRepository, PersonEmailAddressMapper personEmailAddressMapper) {
        this.personEmailAddressRepository = personEmailAddressRepository;
        this.personEmailAddressMapper = personEmailAddressMapper;
    }

    /**
     * Save a personEmailAddress.
     *
     * @param personEmailAddressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonEmailAddressDTO save(PersonEmailAddressDTO personEmailAddressDTO) {
        log.debug("Request to save PersonEmailAddress : {}", personEmailAddressDTO);
        PersonEmailAddress personEmailAddress = personEmailAddressMapper.toEntity(personEmailAddressDTO);
        personEmailAddress = personEmailAddressRepository.save(personEmailAddress);
        return personEmailAddressMapper.toDto(personEmailAddress);
    }

    /**
     * Get all the personEmailAddresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonEmailAddressDTO> findAll() {
        log.debug("Request to get all PersonEmailAddresses");
        return personEmailAddressRepository.findAll().stream()
            .map(personEmailAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personEmailAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEmailAddressDTO> findOne(Long id) {
        log.debug("Request to get PersonEmailAddress : {}", id);
        return personEmailAddressRepository.findById(id)
            .map(personEmailAddressMapper::toDto);
    }

    /**
     * Delete the personEmailAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonEmailAddress : {}", id);
        personEmailAddressRepository.deleteById(id);
    }
}
