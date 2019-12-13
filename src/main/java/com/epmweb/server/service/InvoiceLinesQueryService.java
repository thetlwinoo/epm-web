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

import com.epmweb.server.domain.InvoiceLines;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.InvoiceLinesRepository;
import com.epmweb.server.service.dto.InvoiceLinesCriteria;
import com.epmweb.server.service.dto.InvoiceLinesDTO;
import com.epmweb.server.service.mapper.InvoiceLinesMapper;

/**
 * Service for executing complex queries for {@link InvoiceLines} entities in the database.
 * The main input is a {@link InvoiceLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceLinesDTO} or a {@link Page} of {@link InvoiceLinesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceLinesQueryService extends QueryService<InvoiceLines> {

    private final Logger log = LoggerFactory.getLogger(InvoiceLinesQueryService.class);

    private final InvoiceLinesRepository invoiceLinesRepository;

    private final InvoiceLinesMapper invoiceLinesMapper;

    public InvoiceLinesQueryService(InvoiceLinesRepository invoiceLinesRepository, InvoiceLinesMapper invoiceLinesMapper) {
        this.invoiceLinesRepository = invoiceLinesRepository;
        this.invoiceLinesMapper = invoiceLinesMapper;
    }

    /**
     * Return a {@link List} of {@link InvoiceLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceLinesDTO> findByCriteria(InvoiceLinesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvoiceLines> specification = createSpecification(criteria);
        return invoiceLinesMapper.toDto(invoiceLinesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoiceLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceLinesDTO> findByCriteria(InvoiceLinesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceLines> specification = createSpecification(criteria);
        return invoiceLinesRepository.findAll(specification, page)
            .map(invoiceLinesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceLinesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvoiceLines> specification = createSpecification(criteria);
        return invoiceLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceLines> createSpecification(InvoiceLinesCriteria criteria) {
        Specification<InvoiceLines> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceLines_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InvoiceLines_.description));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), InvoiceLines_.quantity));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), InvoiceLines_.unitPrice));
            }
            if (criteria.getTaxRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxRate(), InvoiceLines_.taxRate));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), InvoiceLines_.taxAmount));
            }
            if (criteria.getLineProfit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineProfit(), InvoiceLines_.lineProfit));
            }
            if (criteria.getExtendedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtendedPrice(), InvoiceLines_.extendedPrice));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), InvoiceLines_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), InvoiceLines_.lastEditedWhen));
            }
            if (criteria.getPackageTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackageTypeId(),
                    root -> root.join(InvoiceLines_.packageType, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(InvoiceLines_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(InvoiceLines_.invoice, JoinType.LEFT).get(Invoices_.id)));
            }
        }
        return specification;
    }
}
