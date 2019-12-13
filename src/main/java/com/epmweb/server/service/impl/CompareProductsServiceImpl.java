package com.epmweb.server.service.impl;

import com.epmweb.server.service.CompareProductsService;
import com.epmweb.server.domain.CompareProducts;
import com.epmweb.server.repository.CompareProductsRepository;
import com.epmweb.server.service.dto.CompareProductsDTO;
import com.epmweb.server.service.mapper.CompareProductsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompareProducts}.
 */
@Service
@Transactional
public class CompareProductsServiceImpl implements CompareProductsService {

    private final Logger log = LoggerFactory.getLogger(CompareProductsServiceImpl.class);

    private final CompareProductsRepository compareProductsRepository;

    private final CompareProductsMapper compareProductsMapper;

    public CompareProductsServiceImpl(CompareProductsRepository compareProductsRepository, CompareProductsMapper compareProductsMapper) {
        this.compareProductsRepository = compareProductsRepository;
        this.compareProductsMapper = compareProductsMapper;
    }

    /**
     * Save a compareProducts.
     *
     * @param compareProductsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompareProductsDTO save(CompareProductsDTO compareProductsDTO) {
        log.debug("Request to save CompareProducts : {}", compareProductsDTO);
        CompareProducts compareProducts = compareProductsMapper.toEntity(compareProductsDTO);
        compareProducts = compareProductsRepository.save(compareProducts);
        return compareProductsMapper.toDto(compareProducts);
    }

    /**
     * Get all the compareProducts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompareProductsDTO> findAll() {
        log.debug("Request to get all CompareProducts");
        return compareProductsRepository.findAll().stream()
            .map(compareProductsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one compareProducts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompareProductsDTO> findOne(Long id) {
        log.debug("Request to get CompareProducts : {}", id);
        return compareProductsRepository.findById(id)
            .map(compareProductsMapper::toDto);
    }

    /**
     * Delete the compareProducts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompareProducts : {}", id);
        compareProductsRepository.deleteById(id);
    }
}
