package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductTagsService;
import com.epmweb.server.domain.ProductTags;
import com.epmweb.server.repository.ProductTagsRepository;
import com.epmweb.server.service.dto.ProductTagsDTO;
import com.epmweb.server.service.mapper.ProductTagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductTags}.
 */
@Service
@Transactional
public class ProductTagsServiceImpl implements ProductTagsService {

    private final Logger log = LoggerFactory.getLogger(ProductTagsServiceImpl.class);

    private final ProductTagsRepository productTagsRepository;

    private final ProductTagsMapper productTagsMapper;

    public ProductTagsServiceImpl(ProductTagsRepository productTagsRepository, ProductTagsMapper productTagsMapper) {
        this.productTagsRepository = productTagsRepository;
        this.productTagsMapper = productTagsMapper;
    }

    /**
     * Save a productTags.
     *
     * @param productTagsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductTagsDTO save(ProductTagsDTO productTagsDTO) {
        log.debug("Request to save ProductTags : {}", productTagsDTO);
        ProductTags productTags = productTagsMapper.toEntity(productTagsDTO);
        productTags = productTagsRepository.save(productTags);
        return productTagsMapper.toDto(productTags);
    }

    /**
     * Get all the productTags.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductTagsDTO> findAll() {
        log.debug("Request to get all ProductTags");
        return productTagsRepository.findAll().stream()
            .map(productTagsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productTags by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductTagsDTO> findOne(Long id) {
        log.debug("Request to get ProductTags : {}", id);
        return productTagsRepository.findById(id)
            .map(productTagsMapper::toDto);
    }

    /**
     * Delete the productTags by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductTags : {}", id);
        productTagsRepository.deleteById(id);
    }
}
