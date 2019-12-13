package com.epmweb.server.service.impl;

import com.epmweb.server.service.PurchaseOrderLinesService;
import com.epmweb.server.domain.PurchaseOrderLines;
import com.epmweb.server.repository.PurchaseOrderLinesRepository;
import com.epmweb.server.service.dto.PurchaseOrderLinesDTO;
import com.epmweb.server.service.mapper.PurchaseOrderLinesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PurchaseOrderLines}.
 */
@Service
@Transactional
public class PurchaseOrderLinesServiceImpl implements PurchaseOrderLinesService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderLinesServiceImpl.class);

    private final PurchaseOrderLinesRepository purchaseOrderLinesRepository;

    private final PurchaseOrderLinesMapper purchaseOrderLinesMapper;

    public PurchaseOrderLinesServiceImpl(PurchaseOrderLinesRepository purchaseOrderLinesRepository, PurchaseOrderLinesMapper purchaseOrderLinesMapper) {
        this.purchaseOrderLinesRepository = purchaseOrderLinesRepository;
        this.purchaseOrderLinesMapper = purchaseOrderLinesMapper;
    }

    /**
     * Save a purchaseOrderLines.
     *
     * @param purchaseOrderLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseOrderLinesDTO save(PurchaseOrderLinesDTO purchaseOrderLinesDTO) {
        log.debug("Request to save PurchaseOrderLines : {}", purchaseOrderLinesDTO);
        PurchaseOrderLines purchaseOrderLines = purchaseOrderLinesMapper.toEntity(purchaseOrderLinesDTO);
        purchaseOrderLines = purchaseOrderLinesRepository.save(purchaseOrderLines);
        return purchaseOrderLinesMapper.toDto(purchaseOrderLines);
    }

    /**
     * Get all the purchaseOrderLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderLinesDTO> findAll() {
        log.debug("Request to get all PurchaseOrderLines");
        return purchaseOrderLinesRepository.findAll().stream()
            .map(purchaseOrderLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one purchaseOrderLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrderLinesDTO> findOne(Long id) {
        log.debug("Request to get PurchaseOrderLines : {}", id);
        return purchaseOrderLinesRepository.findById(id)
            .map(purchaseOrderLinesMapper::toDto);
    }

    /**
     * Delete the purchaseOrderLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrderLines : {}", id);
        purchaseOrderLinesRepository.deleteById(id);
    }
}
