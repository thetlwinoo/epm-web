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
 * Criteria class for the {@link com.epmweb.server.domain.Wishlists} entity. This class is used
 * in {@link com.epmweb.server.web.rest.WishlistsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wishlists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WishlistsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter wishlistUserId;

    private LongFilter wishlistListId;

    public WishlistsCriteria(){
    }

    public WishlistsCriteria(WishlistsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.wishlistUserId = other.wishlistUserId == null ? null : other.wishlistUserId.copy();
        this.wishlistListId = other.wishlistListId == null ? null : other.wishlistListId.copy();
    }

    @Override
    public WishlistsCriteria copy() {
        return new WishlistsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getWishlistUserId() {
        return wishlistUserId;
    }

    public void setWishlistUserId(LongFilter wishlistUserId) {
        this.wishlistUserId = wishlistUserId;
    }

    public LongFilter getWishlistListId() {
        return wishlistListId;
    }

    public void setWishlistListId(LongFilter wishlistListId) {
        this.wishlistListId = wishlistListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WishlistsCriteria that = (WishlistsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(wishlistUserId, that.wishlistUserId) &&
            Objects.equals(wishlistListId, that.wishlistListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        wishlistUserId,
        wishlistListId
        );
    }

    @Override
    public String toString() {
        return "WishlistsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (wishlistUserId != null ? "wishlistUserId=" + wishlistUserId + ", " : "") +
                (wishlistListId != null ? "wishlistListId=" + wishlistListId + ", " : "") +
            "}";
    }

}
