package com.epmweb.server.service.impl;

import com.epmweb.server.service.OrderLinesService;
import com.epmweb.server.domain.OrderLines;
import com.epmweb.server.repository.OrderLinesRepository;
import com.epmweb.server.service.dto.OrderLinesDTO;
import com.epmweb.server.service.mapper.OrderLinesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderLines}.
 */
@Service
@Transactional
public class OrderLinesServiceImpl implements OrderLinesService {

    private final Logger log = LoggerFactory.getLogger(OrderLinesServiceImpl.class);

    private final OrderLinesRepository orderLinesRepository;

    private final OrderLinesMapper orderLinesMapper;

    public OrderLinesServiceImpl(OrderLinesRepository orderLinesRepository, OrderLinesMapper orderLinesMapper) {
        this.orderLinesRepository = orderLinesRepository;
        this.orderLinesMapper = orderLinesMapper;
    }

    /**
     * Save a orderLines.
     *
     * @param orderLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderLinesDTO save(OrderLinesDTO orderLinesDTO) {
        log.debug("Request to save OrderLines : {}", orderLinesDTO);
        OrderLines orderLines = orderLinesMapper.toEntity(orderLinesDTO);
        orderLines = orderLinesRepository.save(orderLines);
        return orderLinesMapper.toDto(orderLines);
    }

    /**
     * Get all the orderLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderLinesDTO> findAll() {
        log.debug("Request to get all OrderLines");
        return orderLinesRepository.findAll().stream()
            .map(orderLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderLinesDTO> findOne(Long id) {
        log.debug("Request to get OrderLines : {}", id);
        return orderLinesRepository.findById(id)
            .map(orderLinesMapper::toDto);
    }

    /**
     * Delete the orderLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderLines : {}", id);
        orderLinesRepository.deleteById(id);
    }
}
