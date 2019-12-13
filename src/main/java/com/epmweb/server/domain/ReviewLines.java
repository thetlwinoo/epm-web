package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ReviewLines.
 */
@Entity
@Table(name = "review_lines")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReviewLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_rating")
    private Integer productRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "product_review")
    private String productReview;

    @Column(name = "seller_rating")
    private Integer sellerRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "seller_review")
    private String sellerReview;

    @Column(name = "delivery_rating")
    private Integer deliveryRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "delivery_review")
    private String deliveryReview;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToOne(mappedBy = "stockItemOnReviewLine")
    @JsonIgnore
    private StockItems stockItem;

    @ManyToOne
    @JsonIgnoreProperties("reviewLineLists")
    private Reviews review;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public ReviewLines productRating(Integer productRating) {
        this.productRating = productRating;
        return this;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public String getProductReview() {
        return productReview;
    }

    public ReviewLines productReview(String productReview) {
        this.productReview = productReview;
        return this;
    }

    public void setProductReview(String productReview) {
        this.productReview = productReview;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public ReviewLines sellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
        return this;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerReview() {
        return sellerReview;
    }

    public ReviewLines sellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
        return this;
    }

    public void setSellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
    }

    public Integer getDeliveryRating() {
        return deliveryRating;
    }

    public ReviewLines deliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
        return this;
    }

    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryReview() {
        return deliveryReview;
    }

    public ReviewLines deliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
        return this;
    }

    public void setDeliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ReviewLines thumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public ReviewLines lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public ReviewLines lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public ReviewLines stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public Reviews getReview() {
        return review;
    }

    public ReviewLines review(Reviews reviews) {
        this.review = reviews;
        return this;
    }

    public void setReview(Reviews reviews) {
        this.review = reviews;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReviewLines)) {
            return false;
        }
        return id != null && id.equals(((ReviewLines) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReviewLines{" +
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
            "}";
    }
}
