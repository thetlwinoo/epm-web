package com.epmweb.server.service.impl;

import com.epmweb.server.service.DeliveryMethodsService;
import com.epmweb.server.domain.DeliveryMethods;
import com.epmweb.server.repository.DeliveryMethodsRepository;
import com.epmweb.server.service.dto.DeliveryMethodsDTO;
import com.epmweb.server.service.mapper.DeliveryMethodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DeliveryMethods}.
 */
@Service
@Transactional
public class DeliveryMethodsServiceImpl implements DeliveryMethodsService {

    private final Logger log = LoggerFactory.getLogger(DeliveryMethodsServiceImpl.class);

    private final DeliveryMethodsRepository deliveryMethodsRepository;

    private final DeliveryMethodsMapper deliveryMethodsMapper;

    public DeliveryMethodsServiceImpl(DeliveryMethodsRepository deliveryMethodsRepository, DeliveryMethodsMapper deliveryMethodsMapper) {
        this.deliveryMethodsRepository = deliveryMethodsRepository;
        this.deliveryMethodsMapper = deliveryMethodsMapper;
    }

    /**
     * Save a deliveryMethods.
     *
     * @param deliveryMethodsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeliveryMethodsDTO save(DeliveryMethodsDTO deliveryMethodsDTO) {
        log.debug("Request to save DeliveryMethods : {}", deliveryMethodsDTO);
        DeliveryMethods deliveryMethods = deliveryMethodsMapper.toEntity(deliveryMethodsDTO);
        deliveryMethods = deliveryMethodsRepository.save(deliveryMethods);
        return deliveryMethodsMapper.toDto(deliveryMethods);
    }

    /**
     * Get all the deliveryMethods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryMethodsDTO> findAll() {
        log.debug("Request to get all DeliveryMethods");
        return deliveryMethodsRepository.findAll().stream()
            .map(deliveryMethodsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one deliveryMethods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryMethodsDTO> findOne(Long id) {
        log.debug("Request to get DeliveryMethods : {}", id);
        return deliveryMethodsRepository.findById(id)
            .map(deliveryMethodsMapper::toDto);
    }

    /**
     * Delete the deliveryMethods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryMethods : {}", id);
        deliveryMethodsRepository.deleteById(id);
    }
}
