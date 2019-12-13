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

import com.epmweb.server.domain.UploadTransactions;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.UploadTransactionsRepository;
import com.epmweb.server.service.dto.UploadTransactionsCriteria;
import com.epmweb.server.service.dto.UploadTransactionsDTO;
import com.epmweb.server.service.mapper.UploadTransactionsMapper;

/**
 * Service for executing complex queries for {@link UploadTransactions} entities in the database.
 * The main input is a {@link UploadTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UploadTransactionsDTO} or a {@link Page} of {@link UploadTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UploadTransactionsQueryService extends QueryService<UploadTransactions> {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsQueryService.class);

    private final UploadTransactionsRepository uploadTransactionsRepository;

    private final UploadTransactionsMapper uploadTransactionsMapper;

    public UploadTransactionsQueryService(UploadTransactionsRepository uploadTransactionsRepository, UploadTransactionsMapper uploadTransactionsMapper) {
        this.uploadTransactionsRepository = uploadTransactionsRepository;
        this.uploadTransactionsMapper = uploadTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link UploadTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UploadTransactionsDTO> findByCriteria(UploadTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UploadTransactions> specification = createSpecification(criteria);
        return uploadTransactionsMapper.toDto(uploadTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UploadTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadTransactionsDTO> findByCriteria(UploadTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UploadTransactions> specification = createSpecification(criteria);
        return uploadTransactionsRepository.findAll(specification, page)
            .map(uploadTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UploadTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UploadTransactions> specification = createSpecification(criteria);
        return uploadTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link UploadTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UploadTransactions> createSpecification(UploadTransactionsCriteria criteria) {
        Specification<UploadTransactions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UploadTransactions_.id));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), UploadTransactions_.fileName));
            }
            if (criteria.getTemplateUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemplateUrl(), UploadTransactions_.templateUrl));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), UploadTransactions_.status));
            }
            if (criteria.getGeneratedCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGeneratedCode(), UploadTransactions_.generatedCode));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), UploadTransactions_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), UploadTransactions_.lastEditedWhen));
            }
            if (criteria.getImportDocumentListId() != null) {
                specification = specification.and(buildSpecification(criteria.getImportDocumentListId(),
                    root -> root.join(UploadTransactions_.importDocumentLists, JoinType.LEFT).get(SupplierImportedDocument_.id)));
            }
            if (criteria.getStockItemTempListId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemTempListId(),
                    root -> root.join(UploadTransactions_.stockItemTempLists, JoinType.LEFT).get(StockItemTemp_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(UploadTransactions_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getActionTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getActionTypeId(),
                    root -> root.join(UploadTransactions_.actionType, JoinType.LEFT).get(UploadActionTypes_.id)));
            }
        }
        return specification;
    }
}
