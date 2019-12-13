package com.epmweb.server.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.epmweb.server.domain.ReviewLines} entity.
 */
public class ReviewLinesDTO implements Serializable {

    private Long id;

    private Integer productRating;

    @Lob
    private String productReview;

    private Integer sellerRating;

    @Lob
    private String sellerReview;

    private Integer deliveryRating;

    @Lob
    private String deliveryReview;

    private String thumbnailUrl;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long reviewId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public String getProductReview() {
        return productReview;
    }

    public void setProductReview(String productReview) {
        this.productReview = productReview;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerReview() {
        return sellerReview;
    }

    public void setSellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
    }

    public Integer getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryReview() {
        return deliveryReview;
    }

    public void setDeliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewsId) {
        this.reviewId = reviewsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewLinesDTO reviewLinesDTO = (ReviewLinesDTO) o;
        if (reviewLinesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviewLinesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReviewLinesDTO{" +
            "id=" + getId() +
            ", productRating=" + getProductRating() +
            ", productReview='" + getProductReview() + "'" +
            ", sellerRating=" + getSellerRating() +
            ", sellerReview='" + getSellerReview() + "'" +
            ", deliveryRating=" + getDeliveryRating() +
            ", deliveryReview='" + getDeliveryReview() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", review=" + getReviewId() +
            "}";
    }
}
