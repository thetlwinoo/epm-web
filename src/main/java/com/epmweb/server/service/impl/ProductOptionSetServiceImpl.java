package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductOptionSetService;
import com.epmweb.server.domain.ProductOptionSet;
import com.epmweb.server.repository.ProductOptionSetRepository;
import com.epmweb.server.service.dto.ProductOptionSetDTO;
import com.epmweb.server.service.mapper.ProductOptionSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductOptionSet}.
 */
@Service
@Transactional
public class ProductOptionSetServiceImpl implements ProductOptionSetService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionSetServiceImpl.class);

    private final ProductOptionSetRepository productOptionSetRepository;

    private final ProductOptionSetMapper productOptionSetMapper;

    public ProductOptionSetServiceImpl(ProductOptionSetRepository productOptionSetRepository, ProductOptionSetMapper productOptionSetMapper) {
        this.productOptionSetRepository = productOptionSetRepository;
        this.productOptionSetMapper = productOptionSetMapper;
    }

    /**
     * Save a productOptionSet.
     *
     * @param productOptionSetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductOptionSetDTO save(ProductOptionSetDTO productOptionSetDTO) {
        log.debug("Request to save ProductOptionSet : {}", productOptionSetDTO);
        ProductOptionSet productOptionSet = productOptionSetMapper.toEntity(productOptionSetDTO);
        productOptionSet = productOptionSetRepository.save(productOptionSet);
        return productOptionSetMapper.toDto(productOptionSet);
    }

    /**
     * Get all the productOptionSets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOptionSetDTO> findAll() {
        log.debug("Request to get all ProductOptionSets");
        return productOptionSetRepository.findAll().stream()
            .map(productOptionSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productOptionSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOptionSetDTO> findOne(Long id) {
        log.debug("Request to get ProductOptionSet : {}", id);
        return productOptionSetRepository.findById(id)
            .map(productOptionSetMapper::toDto);
    }

    /**
     * Delete the productOptionSet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOptionSet : {}", id);
        productOptionSetRepository.deleteById(id);
    }
}
