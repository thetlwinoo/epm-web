package com.epmweb.server.service.impl;

import com.epmweb.server.service.InvoicesService;
import com.epmweb.server.domain.Invoices;
import com.epmweb.server.repository.InvoicesRepository;
import com.epmweb.server.service.dto.InvoicesDTO;
import com.epmweb.server.service.mapper.InvoicesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Invoices}.
 */
@Service
@Transactional
public class InvoicesServiceImpl implements InvoicesService {

    private final Logger log = LoggerFactory.getLogger(InvoicesServiceImpl.class);

    private final InvoicesRepository invoicesRepository;

    private final InvoicesMapper invoicesMapper;

    public InvoicesServiceImpl(InvoicesRepository invoicesRepository, InvoicesMapper invoicesMapper) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
    }

    /**
     * Save a invoices.
     *
     * @param invoicesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoicesDTO save(InvoicesDTO invoicesDTO) {
        log.debug("Request to save Invoices : {}", invoicesDTO);
        Invoices invoices = invoicesMapper.toEntity(invoicesDTO);
        invoices = invoicesRepository.save(invoices);
        return invoicesMapper.toDto(invoices);
    }

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoicesDTO> findAll() {
        log.debug("Request to get all Invoices");
        return invoicesRepository.findAll().stream()
            .map(invoicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one invoices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoicesDTO> findOne(Long id) {
        log.debug("Request to get Invoices : {}", id);
        return invoicesRepository.findById(id)
            .map(invoicesMapper::toDto);
    }

    /**
     * Delete the invoices by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invoices : {}", id);
        invoicesRepository.deleteById(id);
    }
}
