package com.epmweb.server.service.impl;

import com.epmweb.server.domain.*;
import com.epmweb.server.repository.*;
import com.epmweb.server.service.PriceService;
import com.epmweb.server.service.ShoppingCartsExtendService;
import com.epmweb.server.service.dto.ShoppingCartsDTO;
import com.epmweb.server.service.mapper.ShoppingCartsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartsExtendServiceImpl implements ShoppingCartsExtendService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsExtendServiceImpl.class);
    private final ShoppingCartsRepository shoppingCartsRepository;
    private final ShoppingCartItemsRepository shoppingCartItemsRepository;
    private final PriceService priceService;
    private final UserRepository userRepository;
    private final PeopleExtendRepository peopleExtendRepository;
    private final PeopleRepository peopleRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final StockItemsRepository stockItemsRepository;
private final ShoppingCartsMapper shoppingCartsMapper;


    public ShoppingCartsExtendServiceImpl(ShoppingCartsRepository shoppingCartsRepository, ShoppingCartItemsRepository shoppingCartItemsRepository, PriceService priceService, UserRepository userRepository, PeopleExtendRepository peopleExtendRepository, PeopleRepository peopleRepository, CustomersExtendRepository customersExtendRepository, StockItemsRepository stockItemsRepository, ShoppingCartsMapper shoppingCartsMapper) {
        this.shoppingCartsRepository = shoppingCartsRepository;
        this.shoppingCartItemsRepository = shoppingCartItemsRepository;
        this.priceService = priceService;
        this.userRepository = userRepository;
        this.peopleExtendRepository = peopleExtendRepository;
        this.peopleRepository = peopleRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.shoppingCartsMapper = shoppingCartsMapper;
    }

    @Override
    public ShoppingCartsDTO addToCart(Principal principal, Long id, Integer quantity) {
        try {
            People people = getUserFromPrinciple(principal);
            if (quantity <= 0 || id <= 0) {
                throw new IllegalArgumentException("Invalid parameters");
            }

            ShoppingCarts cart = people.getCart();

            Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
            if (!userOptional.isPresent()) {
                throw new IllegalArgumentException("User not found");
            }

            Customers customers = customersExtendRepository.findCustomersByUserId(userOptional.get().getId());

            if (cart == null) {
                cart = new ShoppingCarts();
                cart.setCartUser(people);
                cart.setCustomer(customers);
                shoppingCartsRepository.save(cart);
                //usersRepository.save(user); deleted because it caused the cart to be saved twice
            } else if (cart.getCartItemLists() != null || !cart.getCartItemLists().isEmpty()) {
                for (ShoppingCartItems i : cart.getCartItemLists()) {
                    if (i.getStockItem().getId().equals(id)) {
                        i.setQuantity(i.getQuantity() + quantity);
//                CartItem ct = shoppingCartItemsRepository.save(i);
                        ShoppingCarts returnCart = priceService.calculatePrice(cart);
                        shoppingCartsRepository.save(returnCart);
//                    return new ResponseEntity<Cart>(returnCart,HttpStatus.OK);
                        return shoppingCartsMapper.toDto(returnCart) ;
                    }
                }
            }

            StockItems stockItems = stockItemsRepository.getOne(id);
            ShoppingCartItems cartItem = new ShoppingCartItems();
            cartItem.setQuantity(quantity);
            cartItem.setStockItem(stockItems);

            //this will save the cart object as well because there is cascading from cartItem
            cartItem.setCart(cart);
            if (cart.getCartItemLists() == null) {
//            cart.setShoppingCartItems(new ArrayList<>())
            }
            cart.getCartItemLists().add(cartItem);
            cart = priceService.calculatePrice(cart);

            shoppingCartItemsRepository.save(cartItem);

            return shoppingCartsMapper.toDto(cart) ;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public ShoppingCarts fetchCart(Principal principal) {
        System.out.println("FETCH CART");
        People people = getUserFromPrinciple(principal);
        return people.getCart();
    }

    @Override
    public ShoppingCartsDTO removeFromCart(Principal principal, Long id) {
        System.out.println("Remove CartItem id " + id);
        People people = getUserFromPrinciple(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();
        ShoppingCartItems cartItemToDelete = null;

        for (ShoppingCartItems i : cartItemsList) {
            if (i.getId().equals(id)) {
                cartItemToDelete = i;
                break;
            }
        }
        if (cartItemToDelete == null) {
            throw new IllegalArgumentException("CartItem not found");
        }

        cartItemsList.remove(cartItemToDelete);

        if (cart.getCartItemLists() == null || cart.getCartItemLists().size() == 0) {
            //TODO make it so it can be deleted with online shoppingCartsRepository.delete method
//            shoppingCartsRepository.delete(cart);
            people.setCart(null);
            //setting it to null will delete it
            //because of the orphanRemove mark on the field
            peopleExtendRepository.save(people);
            return null;
        }

        cart.setCartItemLists(cartItemsList);
        cart = priceService.calculatePrice(cart);
        shoppingCartItemsRepository.delete(cartItemToDelete);

        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public ShoppingCartsDTO reduceFromCart(Principal principal, Long id, Integer quantity) {
        System.out.println("Remove CartItem id " + id);
        People people = getUserFromPrinciple(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();
        ShoppingCartItems cartItemToEdit = null;

        for (ShoppingCartItems i : cartItemsList) {
            if (i.getId().equals(id)) {
                cartItemToEdit = i;
                break;
            }
        }
        if (cartItemToEdit == null) {
            throw new IllegalArgumentException("CartItem not found");
        }

        if (cart.getCartItemLists() == null || cart.getCartItemLists().size() == 0) {
            people.setCart(null);
            peopleExtendRepository.save(people);
            return null;
        }

        if (cartItemToEdit.getQuantity() > quantity) {
            cartItemToEdit.setQuantity(cartItemToEdit.getQuantity() - quantity);

            cart.setCartItemLists(cartItemsList);
            cart = priceService.calculatePrice(cart);
            shoppingCartItemsRepository.save(cartItemToEdit);

        } else {
            cartItemsList.remove(cartItemToEdit);
            shoppingCartItemsRepository.delete(cartItemToEdit);
        }

        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public Boolean confirmCart(Principal principal, ShoppingCarts cart) {
        People people = getUserFromPrinciple(principal);
        ShoppingCarts dbCart = people.getCart();
        if (dbCart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> dbCartItemsList = dbCart.getCartItemLists();
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();

        if (dbCartItemsList.size() != cartItemsList.size()) {
            return false;
        }

//        for (int i = 0; i < dbCartItemsList.size(); i++) {
//            if (!dbCartItemsList.get(i).getId().equals(cartItemsList.get(i).getId()) &&
//                !dbCartItemsList.get(i).getAmount().equals(cartItemsList.get(i).getAmount()) &&
//                !dbCartItemsList.get(i).getCartProduct().getId().equals(cartItemsList.get(i).getCartProduct().getId())) {
//                return false;
//            }
//        }
        if (
            dbCart.getTotalPrice().equals(cart.getTotalPrice())
                && dbCart.getTotalCargoPrice().equals(cart.getTotalCargoPrice())
                && dbCart.getId().equals(cart.getId())) {
            if (dbCart.getSpecialDeals() != null && cart.getSpecialDeals() != null) {
                if (dbCart.getSpecialDeals().getDiscountPercentage().equals(cart.getSpecialDeals().getDiscountPercentage())
                    && dbCart.getSpecialDeals().getDiscountCode().equals(cart.getSpecialDeals().getDiscountCode())) {
                    System.out.println("equals");
                    return true;
                }
            } else if (dbCart.getSpecialDeals() == null && cart.getSpecialDeals() == null) {
                System.out.println("equals");
                return true;
            }

        }
        System.out.println("no u");
        System.out.println(dbCart.getCartItemLists().equals(cart.getCartItemLists()));
        return false;
    }

    @Override
    public void emptyCart(Principal principal) {
        People people = getUserFromPrinciple(principal);
        people.setCart(null);
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
