package com.epmweb.server.service.impl;

import com.epmweb.server.service.SystemParametersService;
import com.epmweb.server.domain.SystemParameters;
import com.epmweb.server.repository.SystemParametersRepository;
import com.epmweb.server.service.dto.SystemParametersDTO;
import com.epmweb.server.service.mapper.SystemParametersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SystemParameters}.
 */
@Service
@Transactional
public class SystemParametersServiceImpl implements SystemParametersService {

    private final Logger log = LoggerFactory.getLogger(SystemParametersServiceImpl.class);

    private final SystemParametersRepository systemParametersRepository;

    private final SystemParametersMapper systemParametersMapper;

    public SystemParametersServiceImpl(SystemParametersRepository systemParametersRepository, SystemParametersMapper systemParametersMapper) {
        this.systemParametersRepository = systemParametersRepository;
        this.systemParametersMapper = systemParametersMapper;
    }

    /**
     * Save a systemParameters.
     *
     * @param systemParametersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SystemParametersDTO save(SystemParametersDTO systemParametersDTO) {
        log.debug("Request to save SystemParameters : {}", systemParametersDTO);
        SystemParameters systemParameters = systemParametersMapper.toEntity(systemParametersDTO);
        systemParameters = systemParametersRepository.save(systemParameters);
        return systemParametersMapper.toDto(systemParameters);
    }

    /**
     * Get all the systemParameters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemParametersDTO> findAll() {
        log.debug("Request to get all SystemParameters");
        return systemParametersRepository.findAll().stream()
            .map(systemParametersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one systemParameters by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SystemParametersDTO> findOne(Long id) {
        log.debug("Request to get SystemParameters : {}", id);
        return systemParametersRepository.findById(id)
            .map(systemParametersMapper::toDto);
    }

    /**
     * Delete the systemParameters by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemParameters : {}", id);
        systemParametersRepository.deleteById(id);
    }
}
