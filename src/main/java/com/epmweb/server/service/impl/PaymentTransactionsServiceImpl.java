package com.epmweb.server.service.impl;

import com.epmweb.server.service.PaymentTransactionsService;
import com.epmweb.server.domain.PaymentTransactions;
import com.epmweb.server.repository.PaymentTransactionsRepository;
import com.epmweb.server.service.dto.PaymentTransactionsDTO;
import com.epmweb.server.service.mapper.PaymentTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PaymentTransactions}.
 */
@Service
@Transactional
public class PaymentTransactionsServiceImpl implements PaymentTransactionsService {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionsServiceImpl.class);

    private final PaymentTransactionsRepository paymentTransactionsRepository;

    private final PaymentTransactionsMapper paymentTransactionsMapper;

    public PaymentTransactionsServiceImpl(PaymentTransactionsRepository paymentTransactionsRepository, PaymentTransactionsMapper paymentTransactionsMapper) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
        this.paymentTransactionsMapper = paymentTransactionsMapper;
    }

    /**
     * Save a paymentTransactions.
     *
     * @param paymentTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentTransactionsDTO save(PaymentTransactionsDTO paymentTransactionsDTO) {
        log.debug("Request to save PaymentTransactions : {}", paymentTransactionsDTO);
        PaymentTransactions paymentTransactions = paymentTransactionsMapper.toEntity(paymentTransactionsDTO);
        paymentTransactions = paymentTransactionsRepository.save(paymentTransactions);
        return paymentTransactionsMapper.toDto(paymentTransactions);
    }

    /**
     * Get all the paymentTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionsDTO> findAll() {
        log.debug("Request to get all PaymentTransactions");
        return paymentTransactionsRepository.findAll().stream()
            .map(paymentTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one paymentTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTransactionsDTO> findOne(Long id) {
        log.debug("Request to get PaymentTransactions : {}", id);
        return paymentTransactionsRepository.findById(id)
            .map(paymentTransactionsMapper::toDto);
    }

    /**
     * Delete the paymentTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentTransactions : {}", id);
        paymentTransactionsRepository.deleteById(id);
    }
}
