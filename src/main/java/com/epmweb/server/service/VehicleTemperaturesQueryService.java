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

import com.epmweb.server.domain.VehicleTemperatures;
import com.epmweb.server.domain.*; // for static metamodels
import com.epmweb.server.repository.VehicleTemperaturesRepository;
import com.epmweb.server.service.dto.VehicleTemperaturesCriteria;
import com.epmweb.server.service.dto.VehicleTemperaturesDTO;
import com.epmweb.server.service.mapper.VehicleTemperaturesMapper;

/**
 * Service for executing complex queries for {@link VehicleTemperatures} entities in the database.
 * The main input is a {@link VehicleTemperaturesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleTemperaturesDTO} or a {@link Page} of {@link VehicleTemperaturesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleTemperaturesQueryService extends QueryService<VehicleTemperatures> {

    private final Logger log = LoggerFactory.getLogger(VehicleTemperaturesQueryService.class);

    private final VehicleTemperaturesRepository vehicleTemperaturesRepository;

    private final VehicleTemperaturesMapper vehicleTemperaturesMapper;

    public VehicleTemperaturesQueryService(VehicleTemperaturesRepository vehicleTemperaturesRepository, VehicleTemperaturesMapper vehicleTemperaturesMapper) {
        this.vehicleTemperaturesRepository = vehicleTemperaturesRepository;
        this.vehicleTemperaturesMapper = vehicleTemperaturesMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleTemperaturesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleTemperaturesDTO> findByCriteria(VehicleTemperaturesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleTemperatures> specification = createSpecification(criteria);
        return vehicleTemperaturesMapper.toDto(vehicleTemperaturesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleTemperaturesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleTemperaturesDTO> findByCriteria(VehicleTemperaturesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleTemperatures> specification = createSpecification(criteria);
        return vehicleTemperaturesRepository.findAll(specification, page)
            .map(vehicleTemperaturesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleTemperaturesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleTemperatures> specification = createSpecification(criteria);
        return vehicleTemperaturesRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleTemperaturesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleTemperatures> createSpecification(VehicleTemperaturesCriteria criteria) {
        Specification<VehicleTemperatures> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleTemperatures_.id));
            }
            if (criteria.getVehicleRegistration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVehicleRegistration(), VehicleTemperatures_.vehicleRegistration));
            }
            if (criteria.getChillerSensorNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChillerSensorNumber(), VehicleTemperatures_.chillerSensorNumber));
            }
            if (criteria.getRecordedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecordedWhen(), VehicleTemperatures_.recordedWhen));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperature(), VehicleTemperatures_.temperature));
            }
            if (criteria.getIsCompressed() != null) {
                specification = specification.and(buildSpecification(criteria.getIsCompressed(), VehicleTemperatures_.isCompressed));
            }
            if (criteria.getFullSensorData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullSensorData(), VehicleTemperatures_.fullSensorData));
            }
            if (criteria.getCompressedSensorData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompressedSensorData(), VehicleTemperatures_.compressedSensorData));
            }
        }
        return specification;
    }
}
