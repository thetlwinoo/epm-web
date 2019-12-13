package com.epmweb.server.service.impl;

import com.epmweb.server.service.ShoppingCartItemsService;
import com.epmweb.server.domain.ShoppingCartItems;
import com.epmweb.server.repository.ShoppingCartItemsRepository;
import com.epmweb.server.service.dto.ShoppingCartItemsDTO;
import com.epmweb.server.service.mapper.ShoppingCartItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShoppingCartItems}.
 */
@Service
@Transactional
public class ShoppingCartItemsServiceImpl implements ShoppingCartItemsService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemsServiceImpl.class);

    private final ShoppingCartItemsRepository shoppingCartItemsRepository;

    private final ShoppingCartItemsMapper shoppingCartItemsMapper;

    public ShoppingCartItemsServiceImpl(ShoppingCartItemsRepository shoppingCartItemsRepository, ShoppingCartItemsMapper shoppingCartItemsMapper) {
        this.shoppingCartItemsRepository = shoppingCartItemsRepository;
        this.shoppingCartItemsMapper = shoppingCartItemsMapper;
    }

    /**
     * Save a shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShoppingCartItemsDTO save(ShoppingCartItemsDTO shoppingCartItemsDTO) {
        log.debug("Request to save ShoppingCartItems : {}", shoppingCartItemsDTO);
        ShoppingCartItems shoppingCartItems = shoppingCartItemsMapper.toEntity(shoppingCartItemsDTO);
        shoppingCartItems = shoppingCartItemsRepository.save(shoppingCartItems);
        return shoppingCartItemsMapper.toDto(shoppingCartItems);
    }

    /**
     * Get all the shoppingCartItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartItemsDTO> findAll() {
        log.debug("Request to get all ShoppingCartItems");
        return shoppingCartItemsRepository.findAll().stream()
            .map(shoppingCartItemsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shoppingCartItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingCartItemsDTO> findOne(Long id) {
        log.debug("Request to get ShoppingCartItems : {}", id);
        return shoppingCartItemsRepository.findById(id)
            .map(shoppingCartItemsMapper::toDto);
    }

    /**
     * Delete the shoppingCartItems by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCartItems : {}", id);
        shoppingCartItemsRepository.deleteById(id);
    }
}
