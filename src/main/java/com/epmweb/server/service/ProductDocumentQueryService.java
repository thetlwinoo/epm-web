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

import com.epmweb.server.domain.ProductDocument;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ProductDocumentRepository;
import com.epmweb.server.service.dto.ProductDocumentCriteria;
import com.epmweb.server.service.dto.ProductDocumentDTO;
import com.epmweb.server.service.mapper.ProductDocumentMapper;

/**
 * Service for executing complex queries for {@link ProductDocument} entities in the database.
 * The main input is a {@link ProductDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDocumentDTO} or a {@link Page} of {@link ProductDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductDocumentQueryService extends QueryService<ProductDocument> {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentQueryService.class);

    private final ProductDocumentRepository productDocumentRepository;

    private final ProductDocumentMapper productDocumentMapper;

    public ProductDocumentQueryService(ProductDocumentRepository productDocumentRepository, ProductDocumentMapper productDocumentMapper) {
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDocumentDTO> findByCriteria(ProductDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductDocument> specification = createSpecification(criteria);
        return productDocumentMapper.toDto(productDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDocumentDTO> findByCriteria(ProductDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductDocument> specification = createSpecification(criteria);
        return productDocumentRepository.findAll(specification, page)
            .map(productDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductDocument> specification = createSpecification(criteria);
        return productDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductDocument> createSpecification(ProductDocumentCriteria criteria) {
        Specification<ProductDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductDocument_.id));
            }
            if (criteria.getVideoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideoUrl(), ProductDocument_.videoUrl));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductType(), ProductDocument_.productType));
            }
            if (criteria.getModelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelName(), ProductDocument_.modelName));
            }
            if (criteria.getModelNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelNumber(), ProductDocument_.modelNumber));
            }
            if (criteria.getFabricType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFabricType(), ProductDocument_.fabricType));
            }
            if (criteria.getProductComplianceCertificate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductComplianceCertificate(), ProductDocument_.productComplianceCertificate));
            }
            if (criteria.getGenuineAndLegal() != null) {
                specification = specification.and(buildSpecification(criteria.getGenuineAndLegal(), ProductDocument_.genuineAndLegal));
            }
            if (criteria.getCountryOfOrigin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryOfOrigin(), ProductDocument_.countryOfOrigin));
            }
            if (criteria.getWarrantyPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPeriod(), ProductDocument_.warrantyPeriod));
            }
            if (criteria.getWarrantyPolicy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPolicy(), ProductDocument_.warrantyPolicy));
            }
            if (criteria.getWarrantyTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarrantyTypeId(),
                    root -> root.join(ProductDocument_.warrantyType, JoinType.LEFT).get(WarrantyTypes_.id)));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductDocument_.culture, JoinType.LEFT).get(Culture_.id)));
            }
        }
        return specification;
    }
}
