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

import com.epmweb.server.domain.ShipMethod;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.ShipMethodRepository;
import com.epmweb.server.service.dto.ShipMethodCriteria;
import com.epmweb.server.service.dto.ShipMethodDTO;
import com.epmweb.server.service.mapper.ShipMethodMapper;

/**
 * Service for executing complex queries for {@link ShipMethod} entities in the database.
 * The main input is a {@link ShipMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShipMethodDTO} or a {@link Page} of {@link ShipMethodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShipMethodQueryService extends QueryService<ShipMethod> {

    private final Logger log = LoggerFactory.getLogger(ShipMethodQueryService.class);

    private final ShipMethodRepository shipMethodRepository;

    private final ShipMethodMapper shipMethodMapper;

    public ShipMethodQueryService(ShipMethodRepository shipMethodRepository, ShipMethodMapper shipMethodMapper) {
        this.shipMethodRepository = shipMethodRepository;
        this.shipMethodMapper = shipMethodMapper;
    }

    /**
     * Return a {@link List} of {@link ShipMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShipMethodDTO> findByCriteria(ShipMethodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShipMethod> specification = createSpecification(criteria);
        return shipMethodMapper.toDto(shipMethodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShipMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShipMethodDTO> findByCriteria(ShipMethodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShipMethod> specification = createSpecification(criteria);
        return shipMethodRepository.findAll(specification, page)
            .map(shipMethodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShipMethodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShipMethod> specification = createSpecification(criteria);
        return shipMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link ShipMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShipMethod> createSpecification(ShipMethodCriteria criteria) {
        Specification<ShipMethod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShipMethod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ShipMethod_.name));
            }
        }
        return specification;
    }
}
