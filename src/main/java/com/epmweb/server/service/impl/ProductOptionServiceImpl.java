package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductOptionService;
import com.epmweb.server.domain.ProductOption;
import com.epmweb.server.repository.ProductOptionRepository;
import com.epmweb.server.service.dto.ProductOptionDTO;
import com.epmweb.server.service.mapper.ProductOptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductOption}.
 */
@Service
@Transactional
public class ProductOptionServiceImpl implements ProductOptionService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionServiceImpl.class);

    private final ProductOptionRepository productOptionRepository;

    private final ProductOptionMapper productOptionMapper;

    public ProductOptionServiceImpl(ProductOptionRepository productOptionRepository, ProductOptionMapper productOptionMapper) {
        this.productOptionRepository = productOptionRepository;
        this.productOptionMapper = productOptionMapper;
    }

    /**
     * Save a productOption.
     *
     * @param productOptionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductOptionDTO save(ProductOptionDTO productOptionDTO) {
        log.debug("Request to save ProductOption : {}", productOptionDTO);
        ProductOption productOption = productOptionMapper.toEntity(productOptionDTO);
        productOption = productOptionRepository.save(productOption);
        return productOptionMapper.toDto(productOption);
    }

    /**
     * Get all the productOptions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOptionDTO> findAll() {
        log.debug("Request to get all ProductOptions");
        return productOptionRepository.findAll().stream()
            .map(productOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productOption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOptionDTO> findOne(Long id) {
        log.debug("Request to get ProductOption : {}", id);
        return productOptionRepository.findById(id)
            .map(productOptionMapper::toDto);
    }

    /**
     * Delete the productOption by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOption : {}", id);
        productOptionRepository.deleteById(id);
    }
}
