package com.epmweb.server.service.impl;

import com.epmweb.server.service.CustomerTransactionsService;
import com.epmweb.server.domain.CustomerTransactions;
import com.epmweb.server.repository.CustomerTransactionsRepository;
import com.epmweb.server.service.dto.CustomerTransactionsDTO;
import com.epmweb.server.service.mapper.CustomerTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerTransactions}.
 */
@Service
@Transactional
public class CustomerTransactionsServiceImpl implements CustomerTransactionsService {

    private final Logger log = LoggerFactory.getLogger(CustomerTransactionsServiceImpl.class);

    private final CustomerTransactionsRepository customerTransactionsRepository;

    private final CustomerTransactionsMapper customerTransactionsMapper;

    public CustomerTransactionsServiceImpl(CustomerTransactionsRepository customerTransactionsRepository, CustomerTransactionsMapper customerTransactionsMapper) {
        this.customerTransactionsRepository = customerTransactionsRepository;
        this.customerTransactionsMapper = customerTransactionsMapper;
    }

    /**
     * Save a customerTransactions.
     *
     * @param customerTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerTransactionsDTO save(CustomerTransactionsDTO customerTransactionsDTO) {
        log.debug("Request to save CustomerTransactions : {}", customerTransactionsDTO);
        CustomerTransactions customerTransactions = customerTransactionsMapper.toEntity(customerTransactionsDTO);
        customerTransactions = customerTransactionsRepository.save(customerTransactions);
        return customerTransactionsMapper.toDto(customerTransactions);
    }

    /**
     * Get all the customerTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerTransactionsDTO> findAll() {
        log.debug("Request to get all CustomerTransactions");
        return customerTransactionsRepository.findAll().stream()
            .map(customerTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerTransactionsDTO> findOne(Long id) {
        log.debug("Request to get CustomerTransactions : {}", id);
        return customerTransactionsRepository.findById(id)
            .map(customerTransactionsMapper::toDto);
    }

    /**
     * Delete the customerTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerTransactions : {}", id);
        customerTransactionsRepository.deleteById(id);
    }
}
