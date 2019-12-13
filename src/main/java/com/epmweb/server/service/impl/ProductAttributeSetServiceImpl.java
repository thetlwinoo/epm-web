package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductAttributeSetService;
import com.epmweb.server.domain.ProductAttributeSet;
import com.epmweb.server.repository.ProductAttributeSetRepository;
import com.epmweb.server.service.dto.ProductAttributeSetDTO;
import com.epmweb.server.service.mapper.ProductAttributeSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductAttributeSet}.
 */
@Service
@Transactional
public class ProductAttributeSetServiceImpl implements ProductAttributeSetService {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeSetServiceImpl.class);

    private final ProductAttributeSetRepository productAttributeSetRepository;

    private final ProductAttributeSetMapper productAttributeSetMapper;

    public ProductAttributeSetServiceImpl(ProductAttributeSetRepository productAttributeSetRepository, ProductAttributeSetMapper productAttributeSetMapper) {
        this.productAttributeSetRepository = productAttributeSetRepository;
        this.productAttributeSetMapper = productAttributeSetMapper;
    }

    /**
     * Save a productAttributeSet.
     *
     * @param productAttributeSetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductAttributeSetDTO save(ProductAttributeSetDTO productAttributeSetDTO) {
        log.debug("Request to save ProductAttributeSet : {}", productAttributeSetDTO);
        ProductAttributeSet productAttributeSet = productAttributeSetMapper.toEntity(productAttributeSetDTO);
        productAttributeSet = productAttributeSetRepository.save(productAttributeSet);
        return productAttributeSetMapper.toDto(productAttributeSet);
    }

    /**
     * Get all the productAttributeSets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeSetDTO> findAll() {
        log.debug("Request to get all ProductAttributeSets");
        return productAttributeSetRepository.findAll().stream()
            .map(productAttributeSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productAttributeSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAttributeSetDTO> findOne(Long id) {
        log.debug("Request to get ProductAttributeSet : {}", id);
        return productAttributeSetRepository.findById(id)
            .map(productAttributeSetMapper::toDto);
    }

    /**
     * Delete the productAttributeSet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAttributeSet : {}", id);
        productAttributeSetRepository.deleteById(id);
    }
}
