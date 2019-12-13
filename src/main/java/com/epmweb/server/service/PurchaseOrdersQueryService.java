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

import com.epmweb.server.domain.PurchaseOrders;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.PurchaseOrdersRepository;
import com.epmweb.server.service.dto.PurchaseOrdersCriteria;
import com.epmweb.server.service.dto.PurchaseOrdersDTO;
import com.epmweb.server.service.mapper.PurchaseOrdersMapper;

/**
 * Service for executing complex queries for {@link PurchaseOrders} entities in the database.
 * The main input is a {@link PurchaseOrdersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrdersDTO} or a {@link Page} of {@link PurchaseOrdersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrdersQueryService extends QueryService<PurchaseOrders> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrdersQueryService.class);

    private final PurchaseOrdersRepository purchaseOrdersRepository;

    private final PurchaseOrdersMapper purchaseOrdersMapper;

    public PurchaseOrdersQueryService(PurchaseOrdersRepository purchaseOrdersRepository, PurchaseOrdersMapper purchaseOrdersMapper) {
        this.purchaseOrdersRepository = purchaseOrdersRepository;
        this.purchaseOrdersMapper = purchaseOrdersMapper;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrdersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrdersDTO> findByCriteria(PurchaseOrdersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrders> specification = createSpecification(criteria);
        return purchaseOrdersMapper.toDto(purchaseOrdersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrdersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrdersDTO> findByCriteria(PurchaseOrdersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrders> specification = createSpecification(criteria);
        return purchaseOrdersRepository.findAll(specification, page)
            .map(purchaseOrdersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrdersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrders> specification = createSpecification(criteria);
        return purchaseOrdersRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseOrdersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseOrders> createSpecification(PurchaseOrdersCriteria criteria) {
        Specification<PurchaseOrders> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurchaseOrders_.id));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), PurchaseOrders_.orderDate));
            }
            if (criteria.getExpectedDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedDeliveryDate(), PurchaseOrders_.expectedDeliveryDate));
            }
            if (criteria.getSupplierReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierReference(), PurchaseOrders_.supplierReference));
            }
            if (criteria.getIsOrderFinalized() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsOrderFinalized(), PurchaseOrders_.isOrderFinalized));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), PurchaseOrders_.comments));
            }
            if (criteria.getInternalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalComments(), PurchaseOrders_.internalComments));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), PurchaseOrders_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), PurchaseOrders_.lastEditedWhen));
            }
            if (criteria.getPurchaseOrderLineListId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderLineListId(),
                    root -> root.join(PurchaseOrders_.purchaseOrderLineLists, JoinType.LEFT).get(PurchaseOrderLines_.id)));
            }
            if (criteria.getContactPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactPersonId(),
                    root -> root.join(PurchaseOrders_.contactPerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(PurchaseOrders_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getDeliveryMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryMethodId(),
                    root -> root.join(PurchaseOrders_.deliveryMethod, JoinType.LEFT).get(DeliveryMethods_.id)));
            }
        }
        return specification;
    }
}
