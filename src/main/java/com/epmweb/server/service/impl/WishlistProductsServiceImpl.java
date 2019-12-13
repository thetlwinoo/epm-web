package com.epmweb.server.service.impl;

import com.epmweb.server.service.WishlistProductsService;
import com.epmweb.server.domain.WishlistProducts;
import com.epmweb.server.repository.WishlistProductsRepository;
import com.epmweb.server.service.dto.WishlistProductsDTO;
import com.epmweb.server.service.mapper.WishlistProductsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link WishlistProducts}.
 */
@Service
@Transactional
public class WishlistProductsServiceImpl implements WishlistProductsService {

    private final Logger log = LoggerFactory.getLogger(WishlistProductsServiceImpl.class);

    private final WishlistProductsRepository wishlistProductsRepository;

    private final WishlistProductsMapper wishlistProductsMapper;

    public WishlistProductsServiceImpl(WishlistProductsRepository wishlistProductsRepository, WishlistProductsMapper wishlistProductsMapper) {
        this.wishlistProductsRepository = wishlistProductsRepository;
        this.wishlistProductsMapper = wishlistProductsMapper;
    }

    /**
     * Save a wishlistProducts.
     *
     * @param wishlistProductsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WishlistProductsDTO save(WishlistProductsDTO wishlistProductsDTO) {
        log.debug("Request to save WishlistProducts : {}", wishlistProductsDTO);
        WishlistProducts wishlistProducts = wishlistProductsMapper.toEntity(wishlistProductsDTO);
        wishlistProducts = wishlistProductsRepository.save(wishlistProducts);
        return wishlistProductsMapper.toDto(wishlistProducts);
    }

    /**
     * Get all the wishlistProducts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistProductsDTO> findAll() {
        log.debug("Request to get all WishlistProducts");
        return wishlistProductsRepository.findAll().stream()
            .map(wishlistProductsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wishlistProducts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WishlistProductsDTO> findOne(Long id) {
        log.debug("Request to get WishlistProducts : {}", id);
        return wishlistProductsRepository.findById(id)
            .map(wishlistProductsMapper::toDto);
    }

    /**
     * Delete the wishlistProducts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WishlistProducts : {}", id);
        wishlistProductsRepository.deleteById(id);
    }
}
