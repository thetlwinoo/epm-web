package com.epmweb.server.service.impl;

import com.epmweb.server.service.UnitMeasureService;
import com.epmweb.server.domain.UnitMeasure;
import com.epmweb.server.repository.UnitMeasureRepository;
import com.epmweb.server.service.dto.UnitMeasureDTO;
import com.epmweb.server.service.mapper.UnitMeasureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UnitMeasure}.
 */
@Service
@Transactional
public class UnitMeasureServiceImpl implements UnitMeasureService {

    private final Logger log = LoggerFactory.getLogger(UnitMeasureServiceImpl.class);

    private final UnitMeasureRepository unitMeasureRepository;

    private final UnitMeasureMapper unitMeasureMapper;

    public UnitMeasureServiceImpl(UnitMeasureRepository unitMeasureRepository, UnitMeasureMapper unitMeasureMapper) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.unitMeasureMapper = unitMeasureMapper;
    }

    /**
     * Save a unitMeasure.
     *
     * @param unitMeasureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnitMeasureDTO save(UnitMeasureDTO unitMeasureDTO) {
        log.debug("Request to save UnitMeasure : {}", unitMeasureDTO);
        UnitMeasure unitMeasure = unitMeasureMapper.toEntity(unitMeasureDTO);
        unitMeasure = unitMeasureRepository.save(unitMeasure);
        return unitMeasureMapper.toDto(unitMeasure);
    }

    /**
     * Get all the unitMeasures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnitMeasureDTO> findAll() {
        log.debug("Request to get all UnitMeasures");
        return unitMeasureRepository.findAll().stream()
            .map(unitMeasureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one unitMeasure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnitMeasureDTO> findOne(Long id) {
        log.debug("Request to get UnitMeasure : {}", id);
        return unitMeasureRepository.findById(id)
            .map(unitMeasureMapper::toDto);
    }

    /**
     * Delete the unitMeasure by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnitMeasure : {}", id);
        unitMeasureRepository.deleteById(id);
    }
}
