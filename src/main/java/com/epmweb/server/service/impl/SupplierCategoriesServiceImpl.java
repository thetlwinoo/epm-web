package com.epmweb.server.service.impl;

import com.epmweb.server.service.SupplierCategoriesService;
import com.epmweb.server.domain.SupplierCategories;
import com.epmweb.server.repository.SupplierCategoriesRepository;
import com.epmweb.server.service.dto.SupplierCategoriesDTO;
import com.epmweb.server.service.mapper.SupplierCategoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SupplierCategories}.
 */
@Service
@Transactional
public class SupplierCategoriesServiceImpl implements SupplierCategoriesService {

    private final Logger log = LoggerFactory.getLogger(SupplierCategoriesServiceImpl.class);

    private final SupplierCategoriesRepository supplierCategoriesRepository;

    private final SupplierCategoriesMapper supplierCategoriesMapper;

    public SupplierCategoriesServiceImpl(SupplierCategoriesRepository supplierCategoriesRepository, SupplierCategoriesMapper supplierCategoriesMapper) {
        this.supplierCategoriesRepository = supplierCategoriesRepository;
        this.supplierCategoriesMapper = supplierCategoriesMapper;
    }

    /**
     * Save a supplierCategories.
     *
     * @param supplierCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierCategoriesDTO save(SupplierCategoriesDTO supplierCategoriesDTO) {
        log.debug("Request to save SupplierCategories : {}", supplierCategoriesDTO);
        SupplierCategories supplierCategories = supplierCategoriesMapper.toEntity(supplierCategoriesDTO);
        supplierCategories = supplierCategoriesRepository.save(supplierCategories);
        return supplierCategoriesMapper.toDto(supplierCategories);
    }

    /**
     * Get all the supplierCategories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierCategoriesDTO> findAll() {
        log.debug("Request to get all SupplierCategories");
        return supplierCategoriesRepository.findAll().stream()
            .map(supplierCategoriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierCategoriesDTO> findOne(Long id) {
        log.debug("Request to get SupplierCategories : {}", id);
        return supplierCategoriesRepository.findById(id)
            .map(supplierCategoriesMapper::toDto);
    }

    /**
     * Delete the supplierCategories by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierCategories : {}", id);
        supplierCategoriesRepository.deleteById(id);
    }
}
