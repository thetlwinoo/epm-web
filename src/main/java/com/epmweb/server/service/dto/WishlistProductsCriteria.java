package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.WishlistProducts} entity. This class is used
 * in {@link com.epmweb.server.web.rest.WishlistProductsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wishlist-products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WishlistProductsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter productId;

    private LongFilter wishlistId;

    public WishlistProductsCriteria(){
    }

    public WishlistProductsCriteria(WishlistProductsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.wishlistId = other.wishlistId == null ? null : other.wishlistId.copy();
    }

    @Override
    public WishlistProductsCriteria copy() {
        return new WishlistProductsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(LongFilter wishlistId) {
        this.wishlistId = wishlistId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WishlistProductsCriteria that = (WishlistProductsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(wishlistId, that.wishlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productId,
        wishlistId
        );
    }

    @Override
    public String toString() {
        return "WishlistProductsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (wishlistId != null ? "wishlistId=" + wishlistId + ", " : "") +
            "}";
    }

}
