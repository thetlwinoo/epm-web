package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductSetService;
import com.epmweb.server.domain.ProductSet;
import com.epmweb.server.repository.ProductSetRepository;
import com.epmweb.server.service.dto.ProductSetDTO;
import com.epmweb.server.service.mapper.ProductSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductSet}.
 */
@Service
@Transactional
public class ProductSetServiceImpl implements ProductSetService {

    private final Logger log = LoggerFactory.getLogger(ProductSetServiceImpl.class);

    private final ProductSetRepository productSetRepository;

    private final ProductSetMapper productSetMapper;

    public ProductSetServiceImpl(ProductSetRepository productSetRepository, ProductSetMapper productSetMapper) {
        this.productSetRepository = productSetRepository;
        this.productSetMapper = productSetMapper;
    }

    /**
     * Save a productSet.
     *
     * @param productSetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSetDTO save(ProductSetDTO productSetDTO) {
        log.debug("Request to save ProductSet : {}", productSetDTO);
        ProductSet productSet = productSetMapper.toEntity(productSetDTO);
        productSet = productSetRepository.save(productSet);
        return productSetMapper.toDto(productSet);
    }

    /**
     * Get all the productSets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSetDTO> findAll() {
        log.debug("Request to get all ProductSets");
        return productSetRepository.findAll().stream()
            .map(productSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSetDTO> findOne(Long id) {
        log.debug("Request to get ProductSet : {}", id);
        return productSetRepository.findById(id)
            .map(productSetMapper::toDto);
    }

    /**
     * Delete the productSet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSet : {}", id);
        productSetRepository.deleteById(id);
    }
}
