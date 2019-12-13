package com.epmweb.server.service.impl;

import com.epmweb.server.service.InvoiceLinesService;
import com.epmweb.server.domain.InvoiceLines;
import com.epmweb.server.repository.InvoiceLinesRepository;
import com.epmweb.server.service.dto.InvoiceLinesDTO;
import com.epmweb.server.service.mapper.InvoiceLinesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InvoiceLines}.
 */
@Service
@Transactional
public class InvoiceLinesServiceImpl implements InvoiceLinesService {

    private final Logger log = LoggerFactory.getLogger(InvoiceLinesServiceImpl.class);

    private final InvoiceLinesRepository invoiceLinesRepository;

    private final InvoiceLinesMapper invoiceLinesMapper;

    public InvoiceLinesServiceImpl(InvoiceLinesRepository invoiceLinesRepository, InvoiceLinesMapper invoiceLinesMapper) {
        this.invoiceLinesRepository = invoiceLinesRepository;
        this.invoiceLinesMapper = invoiceLinesMapper;
    }

    /**
     * Save a invoiceLines.
     *
     * @param invoiceLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceLinesDTO save(InvoiceLinesDTO invoiceLinesDTO) {
        log.debug("Request to save InvoiceLines : {}", invoiceLinesDTO);
        InvoiceLines invoiceLines = invoiceLinesMapper.toEntity(invoiceLinesDTO);
        invoiceLines = invoiceLinesRepository.save(invoiceLines);
        return invoiceLinesMapper.toDto(invoiceLines);
    }

    /**
     * Get all the invoiceLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceLinesDTO> findAll() {
        log.debug("Request to get all InvoiceLines");
        return invoiceLinesRepository.findAll().stream()
            .map(invoiceLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one invoiceLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceLinesDTO> findOne(Long id) {
        log.debug("Request to get InvoiceLines : {}", id);
        return invoiceLinesRepository.findById(id)
            .map(invoiceLinesMapper::toDto);
    }

    /**
     * Delete the invoiceLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceLines : {}", id);
        invoiceLinesRepository.deleteById(id);
    }
}
