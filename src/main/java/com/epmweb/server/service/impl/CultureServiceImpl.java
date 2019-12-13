package com.epmweb.server.service.impl;

import com.epmweb.server.service.CultureService;
import com.epmweb.server.domain.Culture;
import com.epmweb.server.repository.CultureRepository;
import com.epmweb.server.service.dto.CultureDTO;
import com.epmweb.server.service.mapper.CultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Culture}.
 */
@Service
@Transactional
public class CultureServiceImpl implements CultureService {

    private final Logger log = LoggerFactory.getLogger(CultureServiceImpl.class);

    private final CultureRepository cultureRepository;

    private final CultureMapper cultureMapper;

    public CultureServiceImpl(CultureRepository cultureRepository, CultureMapper cultureMapper) {
        this.cultureRepository = cultureRepository;
        this.cultureMapper = cultureMapper;
    }

    /**
     * Save a culture.
     *
     * @param cultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CultureDTO save(CultureDTO cultureDTO) {
        log.debug("Request to save Culture : {}", cultureDTO);
        Culture culture = cultureMapper.toEntity(cultureDTO);
        culture = cultureRepository.save(culture);
        return cultureMapper.toDto(culture);
    }

    /**
     * Get all the cultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CultureDTO> findAll() {
        log.debug("Request to get all Cultures");
        return cultureRepository.findAll().stream()
            .map(cultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one culture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CultureDTO> findOne(Long id) {
        log.debug("Request to get Culture : {}", id);
        return cultureRepository.findById(id)
            .map(cultureMapper::toDto);
    }

    /**
     * Delete the culture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Culture : {}", id);
        cultureRepository.deleteById(id);
    }
}
