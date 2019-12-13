package com.epmweb.server.service.impl;

import com.epmweb.server.service.ShipMethodService;
import com.epmweb.server.domain.ShipMethod;
import com.epmweb.server.repository.ShipMethodRepository;
import com.epmweb.server.service.dto.ShipMethodDTO;
import com.epmweb.server.service.mapper.ShipMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShipMethod}.
 */
@Service
@Transactional
public class ShipMethodServiceImpl implements ShipMethodService {

    private final Logger log = LoggerFactory.getLogger(ShipMethodServiceImpl.class);

    private final ShipMethodRepository shipMethodRepository;

    private final ShipMethodMapper shipMethodMapper;

    public ShipMethodServiceImpl(ShipMethodRepository shipMethodRepository, ShipMethodMapper shipMethodMapper) {
        this.shipMethodRepository = shipMethodRepository;
        this.shipMethodMapper = shipMethodMapper;
    }

    /**
     * Save a shipMethod.
     *
     * @param shipMethodDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShipMethodDTO save(ShipMethodDTO shipMethodDTO) {
        log.debug("Request to save ShipMethod : {}", shipMethodDTO);
        ShipMethod shipMethod = shipMethodMapper.toEntity(shipMethodDTO);
        shipMethod = shipMethodRepository.save(shipMethod);
        return shipMethodMapper.toDto(shipMethod);
    }

    /**
     * Get all the shipMethods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipMethodDTO> findAll() {
        log.debug("Request to get all ShipMethods");
        return shipMethodRepository.findAll().stream()
            .map(shipMethodMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shipMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipMethodDTO> findOne(Long id) {
        log.debug("Request to get ShipMethod : {}", id);
        return shipMethodRepository.findById(id)
            .map(shipMethodMapper::toDto);
    }

    /**
     * Delete the shipMethod by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipMethod : {}", id);
        shipMethodRepository.deleteById(id);
    }
}
