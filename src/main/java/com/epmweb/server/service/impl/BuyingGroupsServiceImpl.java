package com.epmweb.server.service.impl;

import com.epmweb.server.service.BuyingGroupsService;
import com.epmweb.server.domain.BuyingGroups;
import com.epmweb.server.repository.BuyingGroupsRepository;
import com.epmweb.server.service.dto.BuyingGroupsDTO;
import com.epmweb.server.service.mapper.BuyingGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BuyingGroups}.
 */
@Service
@Transactional
public class BuyingGroupsServiceImpl implements BuyingGroupsService {

    private final Logger log = LoggerFactory.getLogger(BuyingGroupsServiceImpl.class);

    private final BuyingGroupsRepository buyingGroupsRepository;

    private final BuyingGroupsMapper buyingGroupsMapper;

    public BuyingGroupsServiceImpl(BuyingGroupsRepository buyingGroupsRepository, BuyingGroupsMapper buyingGroupsMapper) {
        this.buyingGroupsRepository = buyingGroupsRepository;
        this.buyingGroupsMapper = buyingGroupsMapper;
    }

    /**
     * Save a buyingGroups.
     *
     * @param buyingGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BuyingGroupsDTO save(BuyingGroupsDTO buyingGroupsDTO) {
        log.debug("Request to save BuyingGroups : {}", buyingGroupsDTO);
        BuyingGroups buyingGroups = buyingGroupsMapper.toEntity(buyingGroupsDTO);
        buyingGroups = buyingGroupsRepository.save(buyingGroups);
        return buyingGroupsMapper.toDto(buyingGroups);
    }

    /**
     * Get all the buyingGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuyingGroupsDTO> findAll() {
        log.debug("Request to get all BuyingGroups");
        return buyingGroupsRepository.findAll().stream()
            .map(buyingGroupsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one buyingGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuyingGroupsDTO> findOne(Long id) {
        log.debug("Request to get BuyingGroups : {}", id);
        return buyingGroupsRepository.findById(id)
            .map(buyingGroupsMapper::toDto);
    }

    /**
     * Delete the buyingGroups by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuyingGroups : {}", id);
        buyingGroupsRepository.deleteById(id);
    }
}
