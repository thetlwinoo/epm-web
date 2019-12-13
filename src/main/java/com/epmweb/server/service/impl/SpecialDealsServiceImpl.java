package com.epmweb.server.service.impl;

import com.epmweb.server.service.SpecialDealsService;
import com.epmweb.server.domain.SpecialDeals;
import com.epmweb.server.repository.SpecialDealsRepository;
import com.epmweb.server.service.dto.SpecialDealsDTO;
import com.epmweb.server.service.mapper.SpecialDealsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SpecialDeals}.
 */
@Service
@Transactional
public class SpecialDealsServiceImpl implements SpecialDealsService {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsServiceImpl.class);

    private final SpecialDealsRepository specialDealsRepository;

    private final SpecialDealsMapper specialDealsMapper;

    public SpecialDealsServiceImpl(SpecialDealsRepository specialDealsRepository, SpecialDealsMapper specialDealsMapper) {
        this.specialDealsRepository = specialDealsRepository;
        this.specialDealsMapper = specialDealsMapper;
    }

    /**
     * Save a specialDeals.
     *
     * @param specialDealsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpecialDealsDTO save(SpecialDealsDTO specialDealsDTO) {
        log.debug("Request to save SpecialDeals : {}", specialDealsDTO);
        SpecialDeals specialDeals = specialDealsMapper.toEntity(specialDealsDTO);
        specialDeals = specialDealsRepository.save(specialDeals);
        return specialDealsMapper.toDto(specialDeals);
    }

    /**
     * Get all the specialDeals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpecialDealsDTO> findAll() {
        log.debug("Request to get all SpecialDeals");
        return specialDealsRepository.findAll().stream()
            .map(specialDealsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one specialDeals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpecialDealsDTO> findOne(Long id) {
        log.debug("Request to get SpecialDeals : {}", id);
        return specialDealsRepository.findById(id)
            .map(specialDealsMapper::toDto);
    }

    /**
     * Delete the specialDeals by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpecialDeals : {}", id);
        specialDealsRepository.deleteById(id);
    }
}
