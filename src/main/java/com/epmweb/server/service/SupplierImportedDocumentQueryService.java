package com.epmweb.server.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.epmweb.server.domain.SupplierImportedDocument;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.SupplierImportedDocumentRepository;
import com.epmweb.server.service.dto.SupplierImportedDocumentCriteria;
import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;
import com.epmweb.server.service.mapper.SupplierImportedDocumentMapper;

/**
 * Service for executing complex queries for {@link SupplierImportedDocument} entities in the database.
 * The main input is a {@link SupplierImportedDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierImportedDocumentDTO} or a {@link Page} of {@link SupplierImportedDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierImportedDocumentQueryService extends QueryService<SupplierImportedDocument> {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentQueryService.class);

    private final SupplierImportedDocumentRepository supplierImportedDocumentRepository;

    private final SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    public SupplierImportedDocumentQueryService(SupplierImportedDocumentRepository supplierImportedDocumentRepository, SupplierImportedDocumentMapper supplierImportedDocumentMapper) {
        this.supplierImportedDocumentRepository = supplierImportedDocumentRepository;
        this.supplierImportedDocumentMapper = supplierImportedDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierImportedDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierImportedDocumentDTO> findByCriteria(SupplierImportedDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierImportedDocument> specification = createSpecification(criteria);
        return supplierImportedDocumentMapper.toDto(supplierImportedDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierImportedDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierImportedDocumentDTO> findByCriteria(SupplierImportedDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierImportedDocument> specification = createSpecification(criteria);
        return supplierImportedDocumentRepository.findAll(specification, page)
            .map(supplierImportedDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierImportedDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierImportedDocument> specification = createSpecification(criteria);
        return supplierImportedDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierImportedDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierImportedDocument> createSpecification(SupplierImportedDocumentCriteria criteria) {
        Specification<SupplierImportedDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierImportedDocument_.id));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), SupplierImportedDocument_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), SupplierImportedDocument_.lastEditedWhen));
            }
            if (criteria.getUploadTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadTransactionId(),
                    root -> root.join(SupplierImportedDocument_.uploadTransaction, JoinType.LEFT).get(UploadTransactions_.id)));
            }
        }
        return specification;
    }
}
