package com.epmweb.server.service.impl;

import com.epmweb.server.service.UploadTransactionsService;
import com.epmweb.server.domain.UploadTransactions;
import com.epmweb.server.repository.UploadTransactionsRepository;
import com.epmweb.server.service.dto.UploadTransactionsDTO;
import com.epmweb.server.service.mapper.UploadTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UploadTransactions}.
 */
@Service
@Transactional
public class UploadTransactionsServiceImpl implements UploadTransactionsService {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsServiceImpl.class);

    private final UploadTransactionsRepository uploadTransactionsRepository;

    private final UploadTransactionsMapper uploadTransactionsMapper;

    public UploadTransactionsServiceImpl(UploadTransactionsRepository uploadTransactionsRepository, UploadTransactionsMapper uploadTransactionsMapper) {
        this.uploadTransactionsRepository = uploadTransactionsRepository;
        this.uploadTransactionsMapper = uploadTransactionsMapper;
    }

    /**
     * Save a uploadTransactions.
     *
     * @param uploadTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UploadTransactionsDTO save(UploadTransactionsDTO uploadTransactionsDTO) {
        log.debug("Request to save UploadTransactions : {}", uploadTransactionsDTO);
        UploadTransactions uploadTransactions = uploadTransactionsMapper.toEntity(uploadTransactionsDTO);
        uploadTransactions = uploadTransactionsRepository.save(uploadTransactions);
        return uploadTransactionsMapper.toDto(uploadTransactions);
    }

    /**
     * Get all the uploadTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadTransactionsDTO> findAll() {
        log.debug("Request to get all UploadTransactions");
        return uploadTransactionsRepository.findAll().stream()
            .map(uploadTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one uploadTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UploadTransactionsDTO> findOne(Long id) {
        log.debug("Request to get UploadTransactions : {}", id);
        return uploadTransactionsRepository.findById(id)
            .map(uploadTransactionsMapper::toDto);
    }

    /**
     * Delete the uploadTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UploadTransactions : {}", id);
        uploadTransactionsRepository.deleteById(id);
    }
}
