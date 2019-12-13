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

import com.epmweb.server.domain.ContactType;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ContactTypeRepository;
import com.epmweb.server.service.dto.ContactTypeCriteria;
import com.epmweb.server.service.dto.ContactTypeDTO;
import com.epmweb.server.service.mapper.ContactTypeMapper;

/**
 * Service for executing complex queries for {@link ContactType} entities in the database.
 * The main input is a {@link ContactTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactTypeDTO} or a {@link Page} of {@link ContactTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactTypeQueryService extends QueryService<ContactType> {

    private final Logger log = LoggerFactory.getLogger(ContactTypeQueryService.class);

    private final ContactTypeRepository contactTypeRepository;

    private final ContactTypeMapper contactTypeMapper;

    public ContactTypeQueryService(ContactTypeRepository contactTypeRepository, ContactTypeMapper contactTypeMapper) {
        this.contactTypeRepository = contactTypeRepository;
        this.contactTypeMapper = contactTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ContactTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactTypeDTO> findByCriteria(ContactTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactType> specification = createSpecification(criteria);
        return contactTypeMapper.toDto(contactTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactTypeDTO> findByCriteria(ContactTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactType> specification = createSpecification(criteria);
        return contactTypeRepository.findAll(specification, page)
            .map(contactTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContactType> specification = createSpecification(criteria);
        return contactTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContactType> createSpecification(ContactTypeCriteria criteria) {
        Specification<ContactType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContactType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContactType_.name));
            }
        }
        return specification;
    }
}
