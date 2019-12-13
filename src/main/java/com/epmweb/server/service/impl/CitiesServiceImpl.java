package com.epmweb.server.service.impl;

import com.epmweb.server.service.CitiesService;
import com.epmweb.server.domain.Cities;
import com.epmweb.server.repository.CitiesRepository;
import com.epmweb.server.service.dto.CitiesDTO;
import com.epmweb.server.service.mapper.CitiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Cities}.
 */
@Service
@Transactional
public class CitiesServiceImpl implements CitiesService {

    private final Logger log = LoggerFactory.getLogger(CitiesServiceImpl.class);

    private final CitiesRepository citiesRepository;

    private final CitiesMapper citiesMapper;

    public CitiesServiceImpl(CitiesRepository citiesRepository, CitiesMapper citiesMapper) {
        this.citiesRepository = citiesRepository;
        this.citiesMapper = citiesMapper;
    }

    /**
     * Save a cities.
     *
     * @param citiesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CitiesDTO save(CitiesDTO citiesDTO) {
        log.debug("Request to save Cities : {}", citiesDTO);
        Cities cities = citiesMapper.toEntity(citiesDTO);
        cities = citiesRepository.save(cities);
        return citiesMapper.toDto(cities);
    }

    /**
     * Get all the cities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitiesDTO> findAll() {
        log.debug("Request to get all Cities");
        return citiesRepository.findAll().stream()
            .map(citiesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitiesDTO> findOne(Long id) {
        log.debug("Request to get Cities : {}", id);
        return citiesRepository.findById(id)
            .map(citiesMapper::toDto);
    }

    /**
     * Delete the cities by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
    }
}
