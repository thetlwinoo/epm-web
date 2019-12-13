package com.epmweb.server.service.impl;

import com.epmweb.server.service.ProductChoiceService;
import com.epmweb.server.domain.ProductChoice;
import com.epmweb.server.repository.ProductChoiceRepository;
import com.epmweb.server.service.dto.ProductChoiceDTO;
import com.epmweb.server.service.mapper.ProductChoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductChoice}.
 */
@Service
@Transactional
public class ProductChoiceServiceImpl implements ProductChoiceService {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceServiceImpl.class);

    private final ProductChoiceRepository productChoiceRepository;

    private final ProductChoiceMapper productChoiceMapper;

    public ProductChoiceServiceImpl(ProductChoiceRepository productChoiceRepository, ProductChoiceMapper productChoiceMapper) {
        this.productChoiceRepository = productChoiceRepository;
        this.productChoiceMapper = productChoiceMapper;
    }

    /**
     * Save a productChoice.
     *
     * @param productChoiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductChoiceDTO save(ProductChoiceDTO productChoiceDTO) {
        log.debug("Request to save ProductChoice : {}", productChoiceDTO);
        ProductChoice productChoice = productChoiceMapper.toEntity(productChoiceDTO);
        productChoice = productChoiceRepository.save(productChoice);
        return productChoiceMapper.toDto(productChoice);
    }

    /**
     * Get all the productChoices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductChoiceDTO> findAll() {
        log.debug("Request to get all ProductChoices");
        return productChoiceRepository.findAll().stream()
            .map(productChoiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one productChoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductChoiceDTO> findOne(Long id) {
        log.debug("Request to get ProductChoice : {}", id);
        return productChoiceRepository.findById(id)
            .map(productChoiceMapper::toDto);
    }

    /**
     * Delete the productChoice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductChoice : {}", id);
        productChoiceRepository.deleteById(id);
    }
}
