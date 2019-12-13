package com.epmweb.server.service.impl;

import com.epmweb.server.service.ReviewsService;
import com.epmweb.server.domain.Reviews;
import com.epmweb.server.repository.ReviewsRepository;
import com.epmweb.server.service.dto.ReviewsDTO;
import com.epmweb.server.service.mapper.ReviewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Reviews}.
 */
@Service
@Transactional
public class ReviewsServiceImpl implements ReviewsService {

    private final Logger log = LoggerFactory.getLogger(ReviewsServiceImpl.class);

    private final ReviewsRepository reviewsRepository;

    private final ReviewsMapper reviewsMapper;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ReviewsMapper reviewsMapper) {
        this.reviewsRepository = reviewsRepository;
        this.reviewsMapper = reviewsMapper;
    }

    /**
     * Save a reviews.
     *
     * @param reviewsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReviewsDTO save(ReviewsDTO reviewsDTO) {
        log.debug("Request to save Reviews : {}", reviewsDTO);
        Reviews reviews = reviewsMapper.toEntity(reviewsDTO);
        reviews = reviewsRepository.save(reviews);
        return reviewsMapper.toDto(reviews);
    }

    /**
     * Get all the reviews.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReviewsDTO> findAll() {
        log.debug("Request to get all Reviews");
        return reviewsRepository.findAll().stream()
            .map(reviewsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the reviews where Order is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ReviewsDTO> findAllWhereOrderIsNull() {
        log.debug("Request to get all reviews where Order is null");
        return StreamSupport
            .stream(reviewsRepository.findAll().spliterator(), false)
            .filter(reviews -> reviews.getOrder() == null)
            .map(reviewsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reviews by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewsDTO> findOne(Long id) {
        log.debug("Request to get Reviews : {}", id);
        return reviewsRepository.findById(id)
            .map(reviewsMapper::toDto);
    }

    /**
     * Delete the reviews by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reviews : {}", id);
        reviewsRepository.deleteById(id);
    }
}
