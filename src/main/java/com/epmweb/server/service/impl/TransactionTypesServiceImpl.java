package com.epmweb.server.service.impl;

import com.epmweb.server.service.TransactionTypesService;
import com.epmweb.server.domain.TransactionTypes;
import com.epmweb.server.repository.TransactionTypesRepository;
import com.epmweb.server.service.dto.TransactionTypesDTO;
import com.epmweb.server.service.mapper.TransactionTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TransactionTypes}.
 */
@Service
@Transactional
public class TransactionTypesServiceImpl implements TransactionTypesService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesServiceImpl.class);

    private final TransactionTypesRepository transactionTypesRepository;

    private final TransactionTypesMapper transactionTypesMapper;

    public TransactionTypesServiceImpl(TransactionTypesRepository transactionTypesRepository, TransactionTypesMapper transactionTypesMapper) {
        this.transactionTypesRepository = transactionTypesRepository;
        this.transactionTypesMapper = transactionTypesMapper;
    }

    /**
     * Save a transactionTypes.
     *
     * @param transactionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionTypesDTO save(TransactionTypesDTO transactionTypesDTO) {
        log.debug("Request to save TransactionTypes : {}", transactionTypesDTO);
        TransactionTypes transactionTypes = transactionTypesMapper.toEntity(transactionTypesDTO);
        transactionTypes = transactionTypesRepository.save(transactionTypes);
        return transactionTypesMapper.toDto(transactionTypes);
    }

    /**
     * Get all the transactionTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypesDTO> findAll() {
        log.debug("Request to get all TransactionTypes");
        return transactionTypesRepository.findAll().stream()
            .map(transactionTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionTypesDTO> findOne(Long id) {
        log.debug("Request to get TransactionTypes : {}", id);
        return transactionTypesRepository.findById(id)
            .map(transactionTypesMapper::toDto);
    }

    /**
     * Delete the transactionTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionTypes : {}", id);
        transactionTypesRepository.deleteById(id);
    }
}
