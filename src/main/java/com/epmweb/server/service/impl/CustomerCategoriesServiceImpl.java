package com.epmweb.server.service.impl;

import com.epmweb.server.service.CustomerCategoriesService;
import com.epmweb.server.domain.CustomerCategories;
import com.epmweb.server.repository.CustomerCategoriesRepository;
import com.epmweb.server.service.dto.CustomerCategoriesDTO;
import com.epmweb.server.service.mapper.CustomerCategoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerCategories}.
 */
@Service
@Transactional
public class CustomerCategoriesServiceImpl implements CustomerCategoriesService {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoriesServiceImpl.class);

    private final CustomerCategoriesRepository customerCategoriesRepository;

    private final CustomerCategoriesMapper customerCategoriesMapper;

    public CustomerCategoriesServiceImpl(CustomerCategoriesRepository customerCategoriesRepository, CustomerCategoriesMapper customerCategoriesMapper) {
        this.customerCategoriesRepository = customerCategoriesRepository;
        this.customerCategoriesMapper = customerCategoriesMapper;
    }

    /**
     * Save a customerCategories.
     *
     * @param customerCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerCategoriesDTO save(CustomerCategoriesDTO customerCategoriesDTO) {
        log.debug("Request to save CustomerCategories : {}", customerCategoriesDTO);
        CustomerCategories customerCategories = customerCategoriesMapper.toEntity(customerCategoriesDTO);
        customerCategories = customerCategoriesRepository.save(customerCategories);
        return customerCategoriesMapper.toDto(customerCategories);
    }

    /**
     * Get all the customerCategories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerCategoriesDTO> findAll() {
        log.debug("Request to get all CustomerCategories");
        return customerCategoriesRepository.findAll().stream()
            .map(customerCategoriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerCategoriesDTO> findOne(Long id) {
        log.debug("Request to get CustomerCategories : {}", id);
        return customerCategoriesRepository.findById(id)
            .map(customerCategoriesMapper::toDto);
    }

    /**
     * Delete the customerCategories by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCategories : {}", id);
        customerCategoriesRepository.deleteById(id);
    }
}
