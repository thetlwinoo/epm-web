package com.epmweb.server.service.impl;

import com.epmweb.server.service.VehicleTemperaturesService;
import com.epmweb.server.domain.VehicleTemperatures;
import com.epmweb.server.repository.VehicleTemperaturesRepository;
import com.epmweb.server.service.dto.VehicleTemperaturesDTO;
import com.epmweb.server.service.mapper.VehicleTemperaturesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link VehicleTemperatures}.
 */
@Service
@Transactional
public class VehicleTemperaturesServiceImpl implements VehicleTemperaturesService {

    private final Logger log = LoggerFactory.getLogger(VehicleTemperaturesServiceImpl.class);

    private final VehicleTemperaturesRepository vehicleTemperaturesRepository;

    private final VehicleTemperaturesMapper vehicleTemperaturesMapper;

    public VehicleTemperaturesServiceImpl(VehicleTemperaturesRepository vehicleTemperaturesRepository, VehicleTemperaturesMapper vehicleTemperaturesMapper) {
        this.vehicleTemperaturesRepository = vehicleTemperaturesRepository;
        this.vehicleTemperaturesMapper = vehicleTemperaturesMapper;
    }

    /**
     * Save a vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VehicleTemperaturesDTO save(VehicleTemperaturesDTO vehicleTemperaturesDTO) {
        log.debug("Request to save VehicleTemperatures : {}", vehicleTemperaturesDTO);
        VehicleTemperatures vehicleTemperatures = vehicleTemperaturesMapper.toEntity(vehicleTemperaturesDTO);
        vehicleTemperatures = vehicleTemperaturesRepository.save(vehicleTemperatures);
        return vehicleTemperaturesMapper.toDto(vehicleTemperatures);
    }

    /**
     * Get all the vehicleTemperatures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleTemperaturesDTO> findAll() {
        log.debug("Request to get all VehicleTemperatures");
        return vehicleTemperaturesRepository.findAll().stream()
            .map(vehicleTemperaturesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleTemperatures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleTemperaturesDTO> findOne(Long id) {
        log.debug("Request to get VehicleTemperatures : {}", id);
        return vehicleTemperaturesRepository.findById(id)
            .map(vehicleTemperaturesMapper::toDto);
    }

    /**
     * Delete the vehicleTemperatures by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleTemperatures : {}", id);
        vehicleTemperaturesRepository.deleteById(id);
    }
}
