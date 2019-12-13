package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WishlistProducts.
 */
@Entity
@Table(name = "wishlist_products")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WishlistProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("wishlistProducts")
    private Products product;

    @ManyToOne
    @JsonIgnoreProperties("wishlistLists")
    private Wishlists wishlist;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public WishlistProducts product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }

    public Wishlists getWishlist() {
        return wishlist;
    }

    public WishlistProducts wishlist(Wishlists wishlists) {
        this.wishlist = wishlists;
        return this;
    }

    public void setWishlist(Wishlists wishlists) {
        this.wishlist = wishlists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WishlistProducts)) {
            return false;
        }
        return id != null && id.equals(((WishlistProducts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WishlistProducts{" +
            "id=" + getId() +
            "}";
    }
}
