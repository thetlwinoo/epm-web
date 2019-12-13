package com.epmweb.server.service.impl;

import com.epmweb.server.service.UploadActionTypesService;
import com.epmweb.server.domain.UploadActionTypes;
import com.epmweb.server.repository.UploadActionTypesRepository;
import com.epmweb.server.service.dto.UploadActionTypesDTO;
import com.epmweb.server.service.mapper.UploadActionTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UploadActionTypes}.
 */
@Service
@Transactional
public class UploadActionTypesServiceImpl implements UploadActionTypesService {

    private final Logger log = LoggerFactory.getLogger(UploadActionTypesServiceImpl.class);

    private final UploadActionTypesRepository uploadActionTypesRepository;

    private final UploadActionTypesMapper uploadActionTypesMapper;

    public UploadActionTypesServiceImpl(UploadActionTypesRepository uploadActionTypesRepository, UploadActionTypesMapper uploadActionTypesMapper) {
        this.uploadActionTypesRepository = uploadActionTypesRepository;
        this.uploadActionTypesMapper = uploadActionTypesMapper;
    }

    /**
     * Save a uploadActionTypes.
     *
     * @param uploadActionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UploadActionTypesDTO save(UploadActionTypesDTO uploadActionTypesDTO) {
        log.debug("Request to save UploadActionTypes : {}", uploadActionTypesDTO);
        UploadActionTypes uploadActionTypes = uploadActionTypesMapper.toEntity(uploadActionTypesDTO);
        uploadActionTypes = uploadActionTypesRepository.save(uploadActionTypes);
        return uploadActionTypesMapper.toDto(uploadActionTypes);
    }

    /**
     * Get all the uploadActionTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadActionTypesDTO> findAll() {
        log.debug("Request to get all UploadActionTypes");
        return uploadActionTypesRepository.findAll().stream()
            .map(uploadActionTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one uploadActionTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UploadActionTypesDTO> findOne(Long id) {
        log.debug("Request to get UploadActionTypes : {}", id);
        return uploadActionTypesRepository.findById(id)
            .map(uploadActionTypesMapper::toDto);
    }

    /**
     * Delete the uploadActionTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UploadActionTypes : {}", id);
        uploadActionTypesRepository.deleteById(id);
    }
}
