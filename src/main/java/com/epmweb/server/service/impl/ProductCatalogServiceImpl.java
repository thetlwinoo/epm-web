package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductCatalogService;
import com.epmweb.server.domain.ProductCatalog;
import com.epmweb.server.repository.ProductCatalogRepository;
import com.epmweb.server.service.dto.ProductCatalogDTO;
import com.epmweb.server.service.mapper.ProductCatalogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductCatalog}.
 */
@Service
@Transactional
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final Logger log = LoggerFactory.getLogger(ProductCatalogServiceImpl.class);

    private final ProductCatalogRepository productCatalogRepository;

    private final ProductCatalogMapper productCatalogMapper;

    public ProductCatalogServiceImpl(ProductCatalogRepository productCatalogRepository, ProductCatalogMapper productCatalogMapper) {
        this.productCatalogRepository = productCatalogRepository;
        this.productCatalogMapper = productCatalogMapper;
    }

    /**
     * Save a productCatalog.
     *
     * @param productCatalogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductCatalogDTO save(ProductCatalogDTO productCatalogDTO) {
        log.debug("Request to save ProductCatalog : {}", productCatalogDTO);
        ProductCatalog productCatalog = productCatalogMapper.toEntity(productCatalogDTO);
        productCatalog = productCatalogRepository.save(productCatalog);
        return productCatalogMapper.toDto(productCatalog);
    }

    /**
     * Get all the productCatalogs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductCatalogDTO> findAll() {
        log.debug("Request to get all ProductCatalogs");
        return productCatalogRepository.findAll().stream()
            .map(productCatalogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productCatalog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductCatalogDTO> findOne(Long id) {
        log.debug("Request to get ProductCatalog : {}", id);
        return productCatalogRepository.findById(id)
            .map(productCatalogMapper::toDto);
    }

    /**
     * Delete the productCatalog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductCatalog : {}", id);
        productCatalogRepository.deleteById(id);
    }
}
