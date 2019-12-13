package com.epmweb.server.service.impl;

import com.epmweb.server.service.PurchaseOrdersService;
import com.epmweb.server.domain.PurchaseOrders;
import com.epmweb.server.repository.PurchaseOrdersRepository;
import com.epmweb.server.service.dto.PurchaseOrdersDTO;
import com.epmweb.server.service.mapper.PurchaseOrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PurchaseOrders}.
 */
@Service
@Transactional
public class PurchaseOrdersServiceImpl implements PurchaseOrdersService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrdersServiceImpl.class);

    private final PurchaseOrdersRepository purchaseOrdersRepository;

    private final PurchaseOrdersMapper purchaseOrdersMapper;

    public PurchaseOrdersServiceImpl(PurchaseOrdersRepository purchaseOrdersRepository, PurchaseOrdersMapper purchaseOrdersMapper) {
        this.purchaseOrdersRepository = purchaseOrdersRepository;
        this.purchaseOrdersMapper = purchaseOrdersMapper;
    }

    /**
     * Save a purchaseOrders.
     *
     * @param purchaseOrdersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseOrdersDTO save(PurchaseOrdersDTO purchaseOrdersDTO) {
        log.debug("Request to save PurchaseOrders : {}", purchaseOrdersDTO);
        PurchaseOrders purchaseOrders = purchaseOrdersMapper.toEntity(purchaseOrdersDTO);
        purchaseOrders = purchaseOrdersRepository.save(purchaseOrders);
        return purchaseOrdersMapper.toDto(purchaseOrders);
    }

    /**
     * Get all the purchaseOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrdersDTO> findAll() {
        log.debug("Request to get all PurchaseOrders");
        return purchaseOrdersRepository.findAll().stream()
            .map(purchaseOrdersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one purchaseOrders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrdersDTO> findOne(Long id) {
        log.debug("Request to get PurchaseOrders : {}", id);
        return purchaseOrdersRepository.findById(id)
            .map(purchaseOrdersMapper::toDto);
    }

    /**
     * Delete the purchaseOrders by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrders : {}", id);
        purchaseOrdersRepository.deleteById(id);
    }
}
