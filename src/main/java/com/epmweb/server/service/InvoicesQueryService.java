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

import com.epmweb.server.domain.Invoices;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.InvoicesRepository;
import com.epmweb.server.service.dto.InvoicesCriteria;
import com.epmweb.server.service.dto.InvoicesDTO;
import com.epmweb.server.service.mapper.InvoicesMapper;

/**
 * Service for executing complex queries for {@link Invoices} entities in the database.
 * The main input is a {@link InvoicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoicesDTO} or a {@link Page} of {@link InvoicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoicesQueryService extends QueryService<Invoices> {

    private final Logger log = LoggerFactory.getLogger(InvoicesQueryService.class);

    private final InvoicesRepository invoicesRepository;

    private final InvoicesMapper invoicesMapper;

    public InvoicesQueryService(InvoicesRepository invoicesRepository, InvoicesMapper invoicesMapper) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
    }

    /**
     * Return a {@link List} of {@link InvoicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoicesDTO> findByCriteria(InvoicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoices> specification = createSpecification(criteria);
        return invoicesMapper.toDto(invoicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoicesDTO> findByCriteria(InvoicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoices> specification = createSpecification(criteria);
        return invoicesRepository.findAll(specification, page)
            .map(invoicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoices> specification = createSpecification(criteria);
        return invoicesRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoicesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Invoices> createSpecification(InvoicesCriteria criteria) {
        Specification<Invoices> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Invoices_.id));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), Invoices_.invoiceDate));
            }
            if (criteria.getCustomerPurchaseOrderNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerPurchaseOrderNumber(), Invoices_.customerPurchaseOrderNumber));
            }
            if (criteria.getIsCreditNote() != null) {
                specification = specification.and(buildSpecification(criteria.getIsCreditNote(), Invoices_.isCreditNote));
            }
            if (criteria.getCreditNoteReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditNoteReason(), Invoices_.creditNoteReason));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Invoices_.comments));
            }
            if (criteria.getDeliveryInstructions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryInstructions(), Invoices_.deliveryInstructions));
            }
            if (criteria.getInternalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalComments(), Invoices_.internalComments));
            }
            if (criteria.getTotalDryItems() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDryItems(), Invoices_.totalDryItems));
            }
            if (criteria.getTotalChillerItems() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalChillerItems(), Invoices_.totalChillerItems));
            }
            if (criteria.getDeliveryRun() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryRun(), Invoices_.deliveryRun));
            }
            if (criteria.getRunPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRunPosition(), Invoices_.runPosition));
            }
            if (criteria.getReturnedDeliveryData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReturnedDeliveryData(), Invoices_.returnedDeliveryData));
            }
            if (criteria.getConfirmedDeliveryTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConfirmedDeliveryTime(), Invoices_.confirmedDeliveryTime));
            }
            if (criteria.getConfirmedReceivedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfirmedReceivedBy(), Invoices_.confirmedReceivedBy));
            }
            if (criteria.getPaymentMethod() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethod(), Invoices_.paymentMethod));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Invoices_.status));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), Invoices_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), Invoices_.lastEditedWhen));
            }
            if (criteria.getInvoiceLineListId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceLineListId(),
                    root -> root.join(Invoices_.invoiceLineLists, JoinType.LEFT).get(InvoiceLines_.id)));
            }
            if (criteria.getContactPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactPersonId(),
                    root -> root.join(Invoices_.contactPerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getSalespersonPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getSalespersonPersonId(),
                    root -> root.join(Invoices_.salespersonPerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getPackedByPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackedByPersonId(),
                    root -> root.join(Invoices_.packedByPerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getAccountsPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountsPersonId(),
                    root -> root.join(Invoices_.accountsPerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Invoices_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getBillToCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getBillToCustomerId(),
                    root -> root.join(Invoices_.billToCustomer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getDeliveryMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryMethodId(),
                    root -> root.join(Invoices_.deliveryMethod, JoinType.LEFT).get(DeliveryMethods_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Invoices_.order, JoinType.LEFT).get(Orders_.id)));
            }
        }
        return specification;
    }
}
