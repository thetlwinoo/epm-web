package com.epmweb.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wishlists.
 */
@Entity
@Table(name = "wishlists")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wishlists implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private People wishlistUser;

    @OneToMany(mappedBy = "wishlist")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WishlistProducts> wishlistLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public People getWishlistUser() {
        return wishlistUser;
    }

    public Wishlists wishlistUser(People people) {
        this.wishlistUser = people;
        return this;
    }

    public void setWishlistUser(People people) {
        this.wishlistUser = people;
    }

    public Set<WishlistProducts> getWishlistLists() {
        return wishlistLists;
    }

    public Wishlists wishlistLists(Set<WishlistProducts> wishlistProducts) {
        this.wishlistLists = wishlistProducts;
        return this;
    }

    public Wishlists addWishlistList(WishlistProducts wishlistProducts) {
        this.wishlistLists.add(wishlistProducts);
        wishlistProducts.setWishlist(this);
        return this;
    }

    public Wishlists removeWishlistList(WishlistProducts wishlistProducts) {
        this.wishlistLists.remove(wishlistProducts);
        wishlistProducts.setWishlist(null);
        return this;
    }

    public void setWishlistLists(Set<WishlistProducts> wishlistProducts) {
        this.wishlistLists = wishlistProducts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wishlists)) {
            return false;
        }
        return id != null && id.equals(((Wishlists) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Wishlists{" +
            "id=" + getId() +
            "}";
    }
}
