package com.epmweb.server.service.impl;

import com.epmweb.server.service.StateProvincesService;
import com.epmweb.server.domain.StateProvinces;
import com.epmweb.server.repository.StateProvincesRepository;
import com.epmweb.server.service.dto.StateProvincesDTO;
import com.epmweb.server.service.mapper.StateProvincesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StateProvinces}.
 */
@Service
@Transactional
public class StateProvincesServiceImpl implements StateProvincesService {

    private final Logger log = LoggerFactory.getLogger(StateProvincesServiceImpl.class);

    private final StateProvincesRepository stateProvincesRepository;

    private final StateProvincesMapper stateProvincesMapper;

    public StateProvincesServiceImpl(StateProvincesRepository stateProvincesRepository, StateProvincesMapper stateProvincesMapper) {
        this.stateProvincesRepository = stateProvincesRepository;
        this.stateProvincesMapper = stateProvincesMapper;
    }

    /**
     * Save a stateProvinces.
     *
     * @param stateProvincesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StateProvincesDTO save(StateProvincesDTO stateProvincesDTO) {
        log.debug("Request to save StateProvinces : {}", stateProvincesDTO);
        StateProvinces stateProvinces = stateProvincesMapper.toEntity(stateProvincesDTO);
        stateProvinces = stateProvincesRepository.save(stateProvinces);
        return stateProvincesMapper.toDto(stateProvinces);
    }

    /**
     * Get all the stateProvinces.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateProvincesDTO> findAll() {
        log.debug("Request to get all StateProvinces");
        return stateProvincesRepository.findAll().stream()
            .map(stateProvincesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stateProvinces by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateProvincesDTO> findOne(Long id) {
        log.debug("Request to get StateProvinces : {}", id);
        return stateProvincesRepository.findById(id)
            .map(stateProvincesMapper::toDto);
    }

    /**
     * Delete the stateProvinces by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateProvinces : {}", id);
        stateProvincesRepository.deleteById(id);
    }
}
