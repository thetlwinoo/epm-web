package com.epmweb.server.service.impl;

import com.epmweb.server.service.ColdRoomTemperaturesService;
import com.epmweb.server.domain.ColdRoomTemperatures;
import com.epmweb.server.repository.ColdRoomTemperaturesRepository;
import com.epmweb.server.service.dto.ColdRoomTemperaturesDTO;
import com.epmweb.server.service.mapper.ColdRoomTemperaturesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ColdRoomTemperatures}.
 */
@Service
@Transactional
public class ColdRoomTemperaturesServiceImpl implements ColdRoomTemperaturesService {

    private final Logger log = LoggerFactory.getLogger(ColdRoomTemperaturesServiceImpl.class);

    private final ColdRoomTemperaturesRepository coldRoomTemperaturesRepository;

    private final ColdRoomTemperaturesMapper coldRoomTemperaturesMapper;

    public ColdRoomTemperaturesServiceImpl(ColdRoomTemperaturesRepository coldRoomTemperaturesRepository, ColdRoomTemperaturesMapper coldRoomTemperaturesMapper) {
        this.coldRoomTemperaturesRepository = coldRoomTemperaturesRepository;
        this.coldRoomTemperaturesMapper = coldRoomTemperaturesMapper;
    }

    /**
     * Save a coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ColdRoomTemperaturesDTO save(ColdRoomTemperaturesDTO coldRoomTemperaturesDTO) {
        log.debug("Request to save ColdRoomTemperatures : {}", coldRoomTemperaturesDTO);
        ColdRoomTemperatures coldRoomTemperatures = coldRoomTemperaturesMapper.toEntity(coldRoomTemperaturesDTO);
        coldRoomTemperatures = coldRoomTemperaturesRepository.save(coldRoomTemperatures);
        return coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);
    }

    /**
     * Get all the coldRoomTemperatures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ColdRoomTemperaturesDTO> findAll() {
        log.debug("Request to get all ColdRoomTemperatures");
        return coldRoomTemperaturesRepository.findAll().stream()
            .map(coldRoomTemperaturesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one coldRoomTemperatures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ColdRoomTemperaturesDTO> findOne(Long id) {
        log.debug("Request to get ColdRoomTemperatures : {}", id);
        return coldRoomTemperaturesRepository.findById(id)
            .map(coldRoomTemperaturesMapper::toDto);
    }

    /**
     * Delete the coldRoomTemperatures by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColdRoomTemperatures : {}", id);
        coldRoomTemperaturesRepository.deleteById(id);
    }
}
