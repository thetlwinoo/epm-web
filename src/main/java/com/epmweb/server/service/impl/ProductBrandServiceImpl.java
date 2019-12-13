package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductBrandService;
import com.epmweb.server.domain.ProductBrand;
import com.epmweb.server.repository.ProductBrandRepository;
import com.epmweb.server.service.dto.ProductBrandDTO;
import com.epmweb.server.service.mapper.ProductBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductBrand}.
 */
@Service
@Transactional
public class ProductBrandServiceImpl implements ProductBrandService {

    private final Logger log = LoggerFactory.getLogger(ProductBrandServiceImpl.class);

    private final ProductBrandRepository productBrandRepository;

    private final ProductBrandMapper productBrandMapper;

    public ProductBrandServiceImpl(ProductBrandRepository productBrandRepository, ProductBrandMapper productBrandMapper) {
        this.productBrandRepository = productBrandRepository;
        this.productBrandMapper = productBrandMapper;
    }

    /**
     * Save a productBrand.
     *
     * @param productBrandDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductBrandDTO save(ProductBrandDTO productBrandDTO) {
        log.debug("Request to save ProductBrand : {}", productBrandDTO);
        ProductBrand productBrand = productBrandMapper.toEntity(productBrandDTO);
        productBrand = productBrandRepository.save(productBrand);
        return productBrandMapper.toDto(productBrand);
    }

    /**
     * Get all the productBrands.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductBrandDTO> findAll() {
        log.debug("Request to get all ProductBrands");
        return productBrandRepository.findAll().stream()
            .map(productBrandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productBrand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductBrandDTO> findOne(Long id) {
        log.debug("Request to get ProductBrand : {}", id);
        return productBrandRepository.findById(id)
            .map(productBrandMapper::toDto);
    }

    /**
     * Delete the productBrand by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductBrand : {}", id);
        productBrandRepository.deleteById(id);
    }
}
