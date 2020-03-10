package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ShoppingCarts.
 */
@Entity
@Table(name = "shopping_carts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShoppingCarts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "total_cargo_price", precision = 21, scale = 2)
    private BigDecimal totalCargoPrice;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private People cartUser;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShoppingCartItems> cartItemLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("shoppingCarts")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("cartDiscounts")
    private SpecialDeals specialDeals;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCarts totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalCargoPrice() {
        return totalCargoPrice;
    }

    public ShoppingCarts totalCargoPrice(BigDecimal totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
        return this;
    }

    public void setTotalCargoPrice(BigDecimal totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public ShoppingCarts lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public ShoppingCarts lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public People getCartUser() {
        return cartUser;
    }

    public ShoppingCarts cartUser(People people) {
        this.cartUser = people;
        return this;
    }

    public void setCartUser(People people) {
        this.cartUser = people;
    }

    public Set<ShoppingCartItems> getCartItemLists() {
        return cartItemLists;
    }

    public ShoppingCarts cartItemLists(Set<ShoppingCartItems> shoppingCartItems) {
        this.cartItemLists = shoppingCartItems;
        return this;
    }

    public ShoppingCarts addCartItemList(ShoppingCartItems shoppingCartItems) {
        this.cartItemLists.add(shoppingCartItems);
        shoppingCartItems.setCart(this);
        return this;
    }

    public ShoppingCarts removeCartItemList(ShoppingCartItems shoppingCartItems) {
        this.cartItemLists.remove(shoppingCartItems);
        shoppingCartItems.setCart(null);
        return this;
    }

    public void setCartItemLists(Set<ShoppingCartItems> shoppingCartItems) {
        this.cartItemLists = shoppingCartItems;
    }

    public Customers getCustomer() {
        return customer;
    }

    public ShoppingCarts customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public SpecialDeals getSpecialDeals() {
        return specialDeals;
    }

    public ShoppingCarts specialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
        return this;
    }

    public void setSpecialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCarts)) {
            return false;
        }
        return id != null && id.equals(((ShoppingCarts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShoppingCarts{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", totalCargoPrice=" + getTotalCargoPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
