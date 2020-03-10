package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reviews.
 */
@Entity
@Table(name = "reviews")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "review_date")
    private Instant reviewDate;

    @Column(name = "over_all_seller_rating")
    private Integer overAllSellerRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "over_all_seller_review")
    private String overAllSellerReview;

    @Column(name = "over_all_delivery_rating")
    private Integer overAllDeliveryRating;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "over_all_delivery_review")
    private String overAllDeliveryReview;

    @Column(name = "review_as_anonymous")
    private Boolean reviewAsAnonymous;

    @Column(name = "completed_review")
    private Boolean completedReview;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private Instant lastEditedWhen;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReviewLines> reviewLineLists = new HashSet<>();

    @OneToOne(mappedBy = "orderOnReview")
    @JsonIgnore
    private Orders order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Reviews name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Reviews emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Instant getReviewDate() {
        return reviewDate;
    }

    public Reviews reviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getOverAllSellerRating() {
        return overAllSellerRating;
    }

    public Reviews overAllSellerRating(Integer overAllSellerRating) {
        this.overAllSellerRating = overAllSellerRating;
        return this;
    }

    public void setOverAllSellerRating(Integer overAllSellerRating) {
        this.overAllSellerRating = overAllSellerRating;
    }

    public String getOverAllSellerReview() {
        return overAllSellerReview;
    }

    public Reviews overAllSellerReview(String overAllSellerReview) {
        this.overAllSellerReview = overAllSellerReview;
        return this;
    }

    public void setOverAllSellerReview(String overAllSellerReview) {
        this.overAllSellerReview = overAllSellerReview;
    }

    public Integer getOverAllDeliveryRating() {
        return overAllDeliveryRating;
    }

    public Reviews overAllDeliveryRating(Integer overAllDeliveryRating) {
        this.overAllDeliveryRating = overAllDeliveryRating;
        return this;
    }

    public void setOverAllDeliveryRating(Integer overAllDeliveryRating) {
        this.overAllDeliveryRating = overAllDeliveryRating;
    }

    public String getOverAllDeliveryReview() {
        return overAllDeliveryReview;
    }

    public Reviews overAllDeliveryReview(String overAllDeliveryReview) {
        this.overAllDeliveryReview = overAllDeliveryReview;
        return this;
    }

    public void setOverAllDeliveryReview(String overAllDeliveryReview) {
        this.overAllDeliveryReview = overAllDeliveryReview;
    }

    public Boolean isReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public Reviews reviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
        return this;
    }

    public void setReviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public Boolean isCompletedReview() {
        return completedReview;
    }

    public Reviews completedReview(Boolean completedReview) {
        this.completedReview = completedReview;
        return this;
    }

    public void setCompletedReview(Boolean completedReview) {
        this.completedReview = completedReview;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Reviews lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public Reviews lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Set<ReviewLines> getReviewLineLists() {
        return reviewLineLists;
    }

    public Reviews reviewLineLists(Set<ReviewLines> reviewLines) {
        this.reviewLineLists = reviewLines;
        return this;
    }

    public Reviews addReviewLineList(ReviewLines reviewLines) {
        this.reviewLineLists.add(reviewLines);
        reviewLines.setReview(this);
        return this;
    }

    public Reviews removeReviewLineList(ReviewLines reviewLines) {
        this.reviewLineLists.remove(reviewLines);
        reviewLines.setReview(null);
        return this;
    }

    public void setReviewLineLists(Set<ReviewLines> reviewLines) {
        this.reviewLineLists = reviewLines;
    }

    public Orders getOrder() {
        return order;
    }

    public Reviews order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reviews)) {
            return false;
        }
        return id != null && id.equals(((Reviews) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reviews{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", overAllSellerRating=" + getOverAllSellerRating() +
            ", overAllSellerReview='" + getOverAllSellerReview() + "'" +
            ", overAllDeliveryRating=" + getOverAllDeliveryRating() +
            ", overAllDeliveryReview='" + getOverAllDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
