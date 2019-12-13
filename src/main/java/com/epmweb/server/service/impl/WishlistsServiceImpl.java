package com.epmweb.server.service.impl;

import com.epmweb.server.service.WishlistsService;
import com.epmweb.server.domain.Wishlists;
import com.epmweb.server.repository.WishlistsRepository;
import com.epmweb.server.service.dto.WishlistsDTO;
import com.epmweb.server.service.mapper.WishlistsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Wishlists}.
 */
@Service
@Transactional
public class WishlistsServiceImpl implements WishlistsService {

    private final Logger log = LoggerFactory.getLogger(WishlistsServiceImpl.class);

    private final WishlistsRepository wishlistsRepository;

    private final WishlistsMapper wishlistsMapper;

    public WishlistsServiceImpl(WishlistsRepository wishlistsRepository, WishlistsMapper wishlistsMapper) {
        this.wishlistsRepository = wishlistsRepository;
        this.wishlistsMapper = wishlistsMapper;
    }

    /**
     * Save a wishlists.
     *
     * @param wishlistsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WishlistsDTO save(WishlistsDTO wishlistsDTO) {
        log.debug("Request to save Wishlists : {}", wishlistsDTO);
        Wishlists wishlists = wishlistsMapper.toEntity(wishlistsDTO);
        wishlists = wishlistsRepository.save(wishlists);
        return wishlistsMapper.toDto(wishlists);
    }

    /**
     * Get all the wishlists.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistsDTO> findAll() {
        log.debug("Request to get all Wishlists");
        return wishlistsRepository.findAll().stream()
            .map(wishlistsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wishlists by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WishlistsDTO> findOne(Long id) {
        log.debug("Request to get Wishlists : {}", id);
        return wishlistsRepository.findById(id)
            .map(wishlistsMapper::toDto);
    }

    /**
     * Delete the wishlists by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wishlists : {}", id);
        wishlistsRepository.deleteById(id);
    }
}
