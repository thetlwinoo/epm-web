package com.epmweb.server.service.impl;

import com.epmweb.server.service.BarcodeTypesService;
import com.epmweb.server.domain.BarcodeTypes;
import com.epmweb.server.repository.BarcodeTypesRepository;
import com.epmweb.server.service.dto.BarcodeTypesDTO;
import com.epmweb.server.service.mapper.BarcodeTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BarcodeTypes}.
 */
@Service
@Transactional
public class BarcodeTypesServiceImpl implements BarcodeTypesService {

    private final Logger log = LoggerFactory.getLogger(BarcodeTypesServiceImpl.class);

    private final BarcodeTypesRepository barcodeTypesRepository;

    private final BarcodeTypesMapper barcodeTypesMapper;

    public BarcodeTypesServiceImpl(BarcodeTypesRepository barcodeTypesRepository, BarcodeTypesMapper barcodeTypesMapper) {
        this.barcodeTypesRepository = barcodeTypesRepository;
        this.barcodeTypesMapper = barcodeTypesMapper;
    }

    /**
     * Save a barcodeTypes.
     *
     * @param barcodeTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BarcodeTypesDTO save(BarcodeTypesDTO barcodeTypesDTO) {
        log.debug("Request to save BarcodeTypes : {}", barcodeTypesDTO);
        BarcodeTypes barcodeTypes = barcodeTypesMapper.toEntity(barcodeTypesDTO);
        barcodeTypes = barcodeTypesRepository.save(barcodeTypes);
        return barcodeTypesMapper.toDto(barcodeTypes);
    }

    /**
     * Get all the barcodeTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BarcodeTypesDTO> findAll() {
        log.debug("Request to get all BarcodeTypes");
        return barcodeTypesRepository.findAll().stream()
            .map(barcodeTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one barcodeTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BarcodeTypesDTO> findOne(Long id) {
        log.debug("Request to get BarcodeTypes : {}", id);
        return barcodeTypesRepository.findById(id)
            .map(barcodeTypesMapper::toDto);
    }

    /**
     * Delete the barcodeTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BarcodeTypes : {}", id);
        barcodeTypesRepository.deleteById(id);
    }
}
