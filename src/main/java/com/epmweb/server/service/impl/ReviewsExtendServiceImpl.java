package com.epmweb.server.service.impl;

import com.epmweb.server.domain.*;
import com.epmweb.server.repository.*;
import com.epmweb.server.service.ReviewsExtendService;
import com.epmweb.server.service.dto.OrdersDTO;
import com.epmweb.server.service.dto.ReviewLinesDTO;
import com.epmweb.server.service.dto.ReviewsDTO;
import com.epmweb.server.service.mapper.OrdersMapper;
import com.epmweb.server.service.mapper.ReviewLinesMapper;
import com.epmweb.server.service.mapper.ReviewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewsExtendServiceImpl implements ReviewsExtendService {

    private final Logger log = LoggerFactory.getLogger(ReviewsExtendServiceImpl.class);
    private final ReviewsRepository reviewsRepository;
    private final PeopleExtendRepository peopleExtendRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final ReviewsMapper reviewsMapper;
    private final ReviewLinesMapper reviewLinesMapper;
    private final ReviewLinesExtendRepository reviewLinesExtendRepository;
    private final UserRepository userRepository;
    private final OrdersMapper ordersMapper;

    public ReviewsExtendServiceImpl(ReviewsRepository reviewsRepository, PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, ReviewsMapper reviewsMapper, ReviewLinesMapper reviewLinesMapper, ReviewLinesExtendRepository reviewLinesExtendRepository, UserRepository userRepository, OrdersMapper ordersMapper) {
        this.reviewsRepository = reviewsRepository;
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.reviewsMapper = reviewsMapper;
        this.reviewLinesMapper = reviewLinesMapper;
        this.reviewLinesExtendRepository = reviewLinesExtendRepository;
        this.userRepository = userRepository;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public ReviewsDTO save(Principal principal, ReviewsDTO reviewsDTO, Long orderId) {
        People people = getUserFromPrinciple(principal);
        reviewsDTO.setName(people.getFullName());
        reviewsDTO.setEmailAddress(people.getEmailAddress());
        reviewsDTO.setReviewDate(Instant.now());
        Reviews reviews = reviewsMapper.toEntity(reviewsDTO);
        reviews = reviewsRepository.save(reviews);

        if (reviewsDTO.getId() == null) {
            Optional<Orders> orders = ordersExtendRepository.findById(orderId);

            if (orders.isPresent()) {
                orders.get().setOrderOnReview(reviews);
            }
        }

        return reviewsMapper.toDto(reviews);
    }

    public ReviewsDTO completedReview(Long orderId) {
        Optional<Orders> orders = ordersExtendRepository.findById(orderId);
        Reviews reviews = new Reviews();
        if (orders.isPresent()) {
            reviews = orders.get().getOrderOnReview();
            reviews.setCompletedReview(true);
            reviewsRepository.save(reviews);
        }

        return reviewsMapper.toDto(reviews);
    }

    @Override
    public List<OrdersDTO> findAllOrderedProducts(Principal principal) {
        try {
            Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
            if (!userOptional.isPresent()) {
                throw new IllegalArgumentException("User not found");
            }

            Customers customer = customersExtendRepository.findCustomersByUserId(userOptional.get().getId());
            List<Orders> ordersList = ordersExtendRepository.findAllByCustomerIdOrderByLastEditedWhenDesc(customer.getId());

            List<Orders> paidOrders = new ArrayList<Orders>();
            for (Orders order : ordersList) {
                if (order.getPaymentStatus() > 0) {
                    paidOrders.add(order);
                }
            }
            return ordersMapper.toDto(paidOrders);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<ReviewLinesDTO> findReviewLinesByStockItemId(Long stockItemId) {
        return reviewLinesExtendRepository.findAllByStockItemId(stockItemId).stream()
            .map(reviewLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }
        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByEmailAddress(userOptional.get().getEmail());
        if (!people.isPresent()) {
            throw new IllegalArgumentException("People not found");
        }
        return people.get();
    }
}
