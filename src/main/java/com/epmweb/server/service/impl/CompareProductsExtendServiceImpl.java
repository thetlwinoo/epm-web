package com.epmweb.server.service.impl;

import com.epmweb.server.domain.*;
import com.epmweb.server.repository.*;
import com.epmweb.server.service.CompareProductsExtendService;
import com.epmweb.server.service.dto.ComparesDTO;
import com.epmweb.server.service.mapper.CompareProductsMapper;
import com.epmweb.server.service.mapper.ComparesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CompareProductsExtendServiceImpl implements CompareProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(CompareProductsExtendServiceImpl.class);
    private final ComparesRepository comparesRepository;
    private final CompareProductsRepository compareProductsRepository;
    private final PeopleExtendRepository peopleExtendRepository;
    private final ProductsRepository productsRepository;
    private final UserRepository userRepository;
    private final ComparesMapper comparesMapper;

    public CompareProductsExtendServiceImpl(ComparesRepository comparesRepository, CompareProductsRepository compareProductsRepository, PeopleExtendRepository peopleExtendRepository, ProductsRepository productsRepository, UserRepository userRepository,  ComparesMapper comparesMapper) {
        this.comparesRepository = comparesRepository;
        this.compareProductsRepository = compareProductsRepository;
        this.peopleExtendRepository = peopleExtendRepository;
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;
        this.comparesMapper = comparesMapper;
    }

    @Override
    public ComparesDTO addToCompare(Principal principal, Long id) {
        try {
            People people = getUserFromPrinciple(principal);

            Compares compares = people.getCompare();

            if (compares == null) {
                compares = new Compares();
                compares.setCompareUser(people);
                comparesRepository.save(compares);
            } else if (compares.getCompareLists() != null || !compares.getCompareLists().isEmpty()) {
                for (CompareProducts i : compares.getCompareLists()) {
                    if (i.getProduct().getId().equals(id)) {
                        return comparesMapper.toDto(compares);
                    }
                }
            }

            Products product = productsRepository.getOne(id);
            CompareProducts compareProducts = new CompareProducts();
            compareProducts.setProduct(product);

            compareProducts.setCompare(compares);
            compares.getCompareLists().add(compareProducts);
            compareProductsRepository.save(compareProducts);

            return  comparesMapper.toDto(compares);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public Boolean isInCompare(Principal principal, Long productId) {
        People people = getUserFromPrinciple(principal);
        for (CompareProducts compareProducts : people.getCompare().getCompareLists()) {
            if (compareProducts.getProduct().getId().equals(productId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ComparesDTO fetchCompare(Principal principal) {
        People people = getUserFromPrinciple(principal);
        return comparesMapper.toDto(people.getCompare());
    }

    @Override
    public ComparesDTO removeFromCompare(Principal principal, Long id) {
        People people = getUserFromPrinciple(principal);
        Compares compares = people.getCompare();
        if (compares == null) {
            throw new IllegalArgumentException("Not found");
        }
        Set<CompareProducts> compareProductsList = compares.getCompareLists();
        CompareProducts compareProductToDelete = null;
        for (CompareProducts i : compareProductsList) {
            if (i.getProduct().getId().equals(id)) {
                compareProductToDelete = i;
                break;
            }
        }
        if (compareProductToDelete == null) {
            throw new IllegalArgumentException("Delete Item Not Found");
        }

        compareProductsList.remove(compareProductToDelete);

        if (compares.getCompareLists() == null || compares.getCompareLists().size() == 0) {
            people.setCompare(null);
            peopleExtendRepository.save(people);
            return null;
        }

        compares.setCompareLists(compareProductsList);
        compareProductsRepository.delete(compareProductToDelete);

        return comparesMapper.toDto(compares);
    }

    @Override
    public void emptyCompare(Principal principal) {
        People people = getUserFromPrinciple(principal);
        people.setCompare(null);
        peopleExtendRepository.save(people);
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
