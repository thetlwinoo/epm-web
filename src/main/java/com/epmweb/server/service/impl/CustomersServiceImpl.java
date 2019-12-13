package com.epmweb.server.service.impl;

import com.epmweb.server.service.CustomersService;
import com.epmweb.server.domain.Customers;
import com.epmweb.server.repository.CustomersRepository;
import com.epmweb.server.service.dto.CustomersDTO;
import com.epmweb.server.service.mapper.CustomersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Customers}.
 */
@Service
@Transactional
public class CustomersServiceImpl implements CustomersService {

    private final Logger log = LoggerFactory.getLogger(CustomersServiceImpl.class);

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    public CustomersServiceImpl(CustomersRepository customersRepository, CustomersMapper customersMapper) {
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
    }

    /**
     * Save a customers.
     *
     * @param customersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomersDTO save(CustomersDTO customersDTO) {
        log.debug("Request to save Customers : {}", customersDTO);
        Customers customers = customersMapper.toEntity(customersDTO);
        customers = customersRepository.save(customers);
        return customersMapper.toDto(customers);
    }

    /**
     * Get all the customers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomersDTO> findAll() {
        log.debug("Request to get all Customers");
        return customersRepository.findAll().stream()
            .map(customersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomersDTO> findOne(Long id) {
        log.debug("Request to get Customers : {}", id);
        return customersRepository.findById(id)
            .map(customersMapper::toDto);
    }

    /**
     * Delete the customers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customers : {}", id);
        customersRepository.deleteById(id);
    }
}
