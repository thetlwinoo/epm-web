package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductAttributeService;
import com.epmweb.server.domain.ProductAttribute;
import com.epmweb.server.repository.ProductAttributeRepository;
import com.epmweb.server.service.dto.ProductAttributeDTO;
import com.epmweb.server.service.mapper.ProductAttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductAttribute}.
 */
@Service
@Transactional
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);

    private final ProductAttributeRepository productAttributeRepository;

    private final ProductAttributeMapper productAttributeMapper;

    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository, ProductAttributeMapper productAttributeMapper) {
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeMapper = productAttributeMapper;
    }

    /**
     * Save a productAttribute.
     *
     * @param productAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductAttributeDTO save(ProductAttributeDTO productAttributeDTO) {
        log.debug("Request to save ProductAttribute : {}", productAttributeDTO);
        ProductAttribute productAttribute = productAttributeMapper.toEntity(productAttributeDTO);
        productAttribute = productAttributeRepository.save(productAttribute);
        return productAttributeMapper.toDto(productAttribute);
    }

    /**
     * Get all the productAttributes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeDTO> findAll() {
        log.debug("Request to get all ProductAttributes");
        return productAttributeRepository.findAll().stream()
            .map(productAttributeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productAttribute by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAttributeDTO> findOne(Long id) {
        log.debug("Request to get ProductAttribute : {}", id);
        return productAttributeRepository.findById(id)
            .map(productAttributeMapper::toDto);
    }

    /**
     * Delete the productAttribute by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAttribute : {}", id);
        productAttributeRepository.deleteById(id);
    }
}
