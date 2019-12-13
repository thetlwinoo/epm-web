package com.epmweb.server.service.impl;

import com.epmweb.server.service.SupplierImportedDocumentService;
import com.epmweb.server.domain.SupplierImportedDocument;
import com.epmweb.server.repository.SupplierImportedDocumentRepository;
import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;
import com.epmweb.server.service.mapper.SupplierImportedDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SupplierImportedDocument}.
 */
@Service
@Transactional
public class SupplierImportedDocumentServiceImpl implements SupplierImportedDocumentService {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentServiceImpl.class);

    private final SupplierImportedDocumentRepository supplierImportedDocumentRepository;

    private final SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    public SupplierImportedDocumentServiceImpl(SupplierImportedDocumentRepository supplierImportedDocumentRepository, SupplierImportedDocumentMapper supplierImportedDocumentMapper) {
        this.supplierImportedDocumentRepository = supplierImportedDocumentRepository;
        this.supplierImportedDocumentMapper = supplierImportedDocumentMapper;
    }

    /**
     * Save a supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierImportedDocumentDTO save(SupplierImportedDocumentDTO supplierImportedDocumentDTO) {
        log.debug("Request to save SupplierImportedDocument : {}", supplierImportedDocumentDTO);
        SupplierImportedDocument supplierImportedDocument = supplierImportedDocumentMapper.toEntity(supplierImportedDocumentDTO);
        supplierImportedDocument = supplierImportedDocumentRepository.save(supplierImportedDocument);
        return supplierImportedDocumentMapper.toDto(supplierImportedDocument);
    }

    /**
     * Get all the supplierImportedDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierImportedDocumentDTO> findAll() {
        log.debug("Request to get all SupplierImportedDocuments");
        return supplierImportedDocumentRepository.findAll().stream()
            .map(supplierImportedDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierImportedDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierImportedDocumentDTO> findOne(Long id) {
        log.debug("Request to get SupplierImportedDocument : {}", id);
        return supplierImportedDocumentRepository.findById(id)
            .map(supplierImportedDocumentMapper::toDto);
    }

    /**
     * Delete the supplierImportedDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierImportedDocument : {}", id);
        supplierImportedDocumentRepository.deleteById(id);
    }
}
