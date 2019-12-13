package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductSetDetailsService;
import com.epmweb.server.domain.ProductSetDetails;
import com.epmweb.server.repository.ProductSetDetailsRepository;
import com.epmweb.server.service.dto.ProductSetDetailsDTO;
import com.epmweb.server.service.mapper.ProductSetDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductSetDetails}.
 */
@Service
@Transactional
public class ProductSetDetailsServiceImpl implements ProductSetDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailsServiceImpl.class);

    private final ProductSetDetailsRepository productSetDetailsRepository;

    private final ProductSetDetailsMapper productSetDetailsMapper;

    public ProductSetDetailsServiceImpl(ProductSetDetailsRepository productSetDetailsRepository, ProductSetDetailsMapper productSetDetailsMapper) {
        this.productSetDetailsRepository = productSetDetailsRepository;
        this.productSetDetailsMapper = productSetDetailsMapper;
    }

    /**
     * Save a productSetDetails.
     *
     * @param productSetDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSetDetailsDTO save(ProductSetDetailsDTO productSetDetailsDTO) {
        log.debug("Request to save ProductSetDetails : {}", productSetDetailsDTO);
        ProductSetDetails productSetDetails = productSetDetailsMapper.toEntity(productSetDetailsDTO);
        productSetDetails = productSetDetailsRepository.save(productSetDetails);
        return productSetDetailsMapper.toDto(productSetDetails);
    }

    /**
     * Get all the productSetDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDetailsDTO> findAll() {
        log.debug("Request to get all ProductSetDetails");
        return productSetDetailsRepository.findAll().stream()
            .map(productSetDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productSetDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSetDetailsDTO> findOne(Long id) {
        log.debug("Request to get ProductSetDetails : {}", id);
        return productSetDetailsRepository.findById(id)
            .map(productSetDetailsMapper::toDto);
    }

    /**
     * Delete the productSetDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSetDetails : {}", id);
        productSetDetailsRepository.deleteById(id);
    }
}
