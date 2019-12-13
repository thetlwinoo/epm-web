package com.epmweb.server.service.impl;

import com.epmweb.server.service.SupplierTransactionsService;
import com.epmweb.server.domain.SupplierTransactions;
import com.epmweb.server.repository.SupplierTransactionsRepository;
import com.epmweb.server.service.dto.SupplierTransactionsDTO;
import com.epmweb.server.service.mapper.SupplierTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SupplierTransactions}.
 */
@Service
@Transactional
public class SupplierTransactionsServiceImpl implements SupplierTransactionsService {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionsServiceImpl.class);

    private final SupplierTransactionsRepository supplierTransactionsRepository;

    private final SupplierTransactionsMapper supplierTransactionsMapper;

    public SupplierTransactionsServiceImpl(SupplierTransactionsRepository supplierTransactionsRepository, SupplierTransactionsMapper supplierTransactionsMapper) {
        this.supplierTransactionsRepository = supplierTransactionsRepository;
        this.supplierTransactionsMapper = supplierTransactionsMapper;
    }

    /**
     * Save a supplierTransactions.
     *
     * @param supplierTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierTransactionsDTO save(SupplierTransactionsDTO supplierTransactionsDTO) {
        log.debug("Request to save SupplierTransactions : {}", supplierTransactionsDTO);
        SupplierTransactions supplierTransactions = supplierTransactionsMapper.toEntity(supplierTransactionsDTO);
        supplierTransactions = supplierTransactionsRepository.save(supplierTransactions);
        return supplierTransactionsMapper.toDto(supplierTransactions);
    }

    /**
     * Get all the supplierTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierTransactionsDTO> findAll() {
        log.debug("Request to get all SupplierTransactions");
        return supplierTransactionsRepository.findAll().stream()
            .map(supplierTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierTransactionsDTO> findOne(Long id) {
        log.debug("Request to get SupplierTransactions : {}", id);
        return supplierTransactionsRepository.findById(id)
            .map(supplierTransactionsMapper::toDto);
    }

    /**
     * Delete the supplierTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierTransactions : {}", id);
        supplierTransactionsRepository.deleteById(id);
    }
}
