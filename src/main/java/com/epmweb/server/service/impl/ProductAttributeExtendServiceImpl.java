package com.epmweb.server.service.impl;

import com.epmweb.server.domain.People;
import com.epmweb.server.domain.User;
import com.epmweb.server.repository.PeopleExtendRepository;
import com.epmweb.server.repository.ProductAttributeExtendRepository;
import com.epmweb.server.repository.SuppliersExtendRepository;
import com.epmweb.server.repository.UserRepository;
import com.epmweb.server.service.ProductAttributeExtendService;
import com.epmweb.server.service.dto.ProductAttributeDTO;
import com.epmweb.server.service.dto.SuppliersDTO;
import com.epmweb.server.service.mapper.ProductAttributeMapper;
import com.epmweb.server.service.mapper.SuppliersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductAttributeExtendServiceImpl implements ProductAttributeExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeExtendServiceImpl.class);
    private final ProductAttributeExtendRepository productAttributeExtendRepository;
    private final ProductAttributeMapper productAttributeMapper;
    private final PeopleExtendRepository peopleExtendRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;
    private final UserRepository userRepository;

    public ProductAttributeExtendServiceImpl(ProductAttributeExtendRepository productAttributeExtendRepository, ProductAttributeMapper productAttributeMapper, PeopleExtendRepository peopleExtendRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper, UserRepository userRepository) {
        this.productAttributeExtendRepository = productAttributeExtendRepository;
        this.productAttributeMapper = productAttributeMapper;
        this.peopleExtendRepository = peopleExtendRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeDTO> getAllProductAttributes(Long attributeSetId, Principal principal) {
        log.debug("Request to get all ProductAttributes");
        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendRepository.findSuppliersByUserId(userOptional.get().getId())
            .map(suppliersMapper::toDto);

        return productAttributeExtendRepository.findAllByProductAttributeSetIdAndSupplierId(attributeSetId, suppliersDTOOptional.get().getId()).stream()
            .map(productAttributeMapper::toDto)
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
