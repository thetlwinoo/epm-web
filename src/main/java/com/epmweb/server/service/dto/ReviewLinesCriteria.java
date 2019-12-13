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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.ReviewLines} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ReviewLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /review-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReviewLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter productRating;

    private IntegerFilter sellerRating;

    private IntegerFilter deliveryRating;

    private StringFilter thumbnailUrl;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter stockItemId;

    private LongFilter reviewId;

    public ReviewLinesCriteria(){
    }

    public ReviewLinesCriteria(ReviewLinesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productRating = other.productRating == null ? null : other.productRating.copy();
        this.sellerRating = other.sellerRating == null ? null : other.sellerRating.copy();
        this.deliveryRating = other.deliveryRating == null ? null : other.deliveryRating.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.reviewId = other.reviewId == null ? null : other.reviewId.copy();
    }

    @Override
    public ReviewLinesCriteria copy() {
        return new ReviewLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getProductRating() {
        return productRating;
    }

    public void setProductRating(IntegerFilter productRating) {
        this.productRating = productRating;
    }

    public IntegerFilter getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(IntegerFilter sellerRating) {
        this.sellerRating = sellerRating;
    }

    public IntegerFilter getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(IntegerFilter deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getReviewId() {
        return reviewId;
    }

    public void setReviewId(LongFilter reviewId) {
        this.reviewId = reviewId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReviewLinesCriteria that = (ReviewLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productRating, that.productRating) &&
            Objects.equals(sellerRating, that.sellerRating) &&
            Objects.equals(deliveryRating, that.deliveryRating) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(reviewId, that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productRating,
        sellerRating,
        deliveryRating,
        thumbnailUrl,
        lastEditedBy,
        lastEditedWhen,
        stockItemId,
        reviewId
        );
    }

    @Override
    public String toString() {
        return "ReviewLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productRating != null ? "productRating=" + productRating + ", " : "") +
                (sellerRating != null ? "sellerRating=" + sellerRating + ", " : "") +
                (deliveryRating != null ? "deliveryRating=" + deliveryRating + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (reviewId != null ? "reviewId=" + reviewId + ", " : "") +
            "}";
    }

}
