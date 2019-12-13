package com.epmweb.server.service.impl;

import com.epmweb.server.service.MaterialsService;
import com.epmweb.server.domain.Materials;
import com.epmweb.server.repository.MaterialsRepository;
import com.epmweb.server.service.dto.MaterialsDTO;
import com.epmweb.server.service.mapper.MaterialsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Materials}.
 */
@Service
@Transactional
public class MaterialsServiceImpl implements MaterialsService {

    private final Logger log = LoggerFactory.getLogger(MaterialsServiceImpl.class);

    private final MaterialsRepository materialsRepository;

    private final MaterialsMapper materialsMapper;

    public MaterialsServiceImpl(MaterialsRepository materialsRepository, MaterialsMapper materialsMapper) {
        this.materialsRepository = materialsRepository;
        this.materialsMapper = materialsMapper;
    }

    /**
     * Save a materials.
     *
     * @param materialsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MaterialsDTO save(MaterialsDTO materialsDTO) {
        log.debug("Request to save Materials : {}", materialsDTO);
        Materials materials = materialsMapper.toEntity(materialsDTO);
        materials = materialsRepository.save(materials);
        return materialsMapper.toDto(materials);
    }

    /**
     * Get all the materials.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MaterialsDTO> findAll() {
        log.debug("Request to get all Materials");
        return materialsRepository.findAll().stream()
            .map(materialsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one materials by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialsDTO> findOne(Long id) {
        log.debug("Request to get Materials : {}", id);
        return materialsRepository.findById(id)
            .map(materialsMapper::toDto);
    }

    /**
     * Delete the materials by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materials : {}", id);
        materialsRepository.deleteById(id);
    }
}
