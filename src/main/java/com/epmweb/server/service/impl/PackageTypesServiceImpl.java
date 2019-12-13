package com.epmweb.server.service.impl;

import com.epmweb.server.service.PackageTypesService;
import com.epmweb.server.domain.PackageTypes;
import com.epmweb.server.repository.PackageTypesRepository;
import com.epmweb.server.service.dto.PackageTypesDTO;
import com.epmweb.server.service.mapper.PackageTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PackageTypes}.
 */
@Service
@Transactional
public class PackageTypesServiceImpl implements PackageTypesService {

    private final Logger log = LoggerFactory.getLogger(PackageTypesServiceImpl.class);

    private final PackageTypesRepository packageTypesRepository;

    private final PackageTypesMapper packageTypesMapper;

    public PackageTypesServiceImpl(PackageTypesRepository packageTypesRepository, PackageTypesMapper packageTypesMapper) {
        this.packageTypesRepository = packageTypesRepository;
        this.packageTypesMapper = packageTypesMapper;
    }

    /**
     * Save a packageTypes.
     *
     * @param packageTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PackageTypesDTO save(PackageTypesDTO packageTypesDTO) {
        log.debug("Request to save PackageTypes : {}", packageTypesDTO);
        PackageTypes packageTypes = packageTypesMapper.toEntity(packageTypesDTO);
        packageTypes = packageTypesRepository.save(packageTypes);
        return packageTypesMapper.toDto(packageTypes);
    }

    /**
     * Get all the packageTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PackageTypesDTO> findAll() {
        log.debug("Request to get all PackageTypes");
        return packageTypesRepository.findAll().stream()
            .map(packageTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one packageTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PackageTypesDTO> findOne(Long id) {
        log.debug("Request to get PackageTypes : {}", id);
        return packageTypesRepository.findById(id)
            .map(packageTypesMapper::toDto);
    }

    /**
     * Delete the packageTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PackageTypes : {}", id);
        packageTypesRepository.deleteById(id);
    }
}
