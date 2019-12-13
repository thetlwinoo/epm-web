package com.epmweb.server.service.impl;

import com.epmweb.server.service.ReviewLinesService;
import com.epmweb.server.domain.ReviewLines;
import com.epmweb.server.repository.ReviewLinesRepository;
import com.epmweb.server.service.dto.ReviewLinesDTO;
import com.epmweb.server.service.mapper.ReviewLinesMapper;
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
 * Service Implementation for managing {@link ReviewLines}.
 */
@Service
@Transactional
public class ReviewLinesServiceImpl implements ReviewLinesService {

    private final Logger log = LoggerFactory.getLogger(ReviewLinesServiceImpl.class);

    private final ReviewLinesRepository reviewLinesRepository;

    private final ReviewLinesMapper reviewLinesMapper;

    public ReviewLinesServiceImpl(ReviewLinesRepository reviewLinesRepository, ReviewLinesMapper reviewLinesMapper) {
        this.reviewLinesRepository = reviewLinesRepository;
        this.reviewLinesMapper = reviewLinesMapper;
    }

    /**
     * Save a reviewLines.
     *
     * @param reviewLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReviewLinesDTO save(ReviewLinesDTO reviewLinesDTO) {
        log.debug("Request to save ReviewLines : {}", reviewLinesDTO);
        ReviewLines reviewLines = reviewLinesMapper.toEntity(reviewLinesDTO);
        reviewLines = reviewLinesRepository.save(reviewLines);
        return reviewLinesMapper.toDto(reviewLines);
    }

    /**
     * Get all the reviewLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReviewLinesDTO> findAll() {
        log.debug("Request to get all ReviewLines");
        return reviewLinesRepository.findAll().stream()
            .map(reviewLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the reviewLines where StockItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ReviewLinesDTO> findAllWhereStockItemIsNull() {
        log.debug("Request to get all reviewLines where StockItem is null");
        return StreamSupport
            .stream(reviewLinesRepository.findAll().spliterator(), false)
            .filter(reviewLines -> reviewLines.getStockItem() == null)
            .map(reviewLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reviewLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewLinesDTO> findOne(Long id) {
        log.debug("Request to get ReviewLines : {}", id);
        return reviewLinesRepository.findById(id)
            .map(reviewLinesMapper::toDto);
    }

    /**
     * Delete the reviewLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReviewLines : {}", id);
        reviewLinesRepository.deleteById(id);
    }
}
