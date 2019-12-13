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
 * Criteria class for the {@link com.epmweb.server.domain.ProductCategory} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ProductCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter shortLabel;

    private StringFilter thumbnailUrl;

    private LongFilter photoListId;

    private LongFilter parentId;

    public ProductCategoryCriteria(){
    }

    public ProductCategoryCriteria(ProductCategoryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortLabel = other.shortLabel == null ? null : other.shortLabel.copy();
        this.thumbnailUrl = other.thumbnailUrl == null ? null : other.thumbnailUrl.copy();
        this.photoListId = other.photoListId == null ? null : other.photoListId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public ProductCategoryCriteria copy() {
        return new ProductCategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(StringFilter shortLabel) {
        this.shortLabel = shortLabel;
    }

    public StringFilter getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(StringFilter thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public LongFilter getPhotoListId() {
        return photoListId;
    }

    public void setPhotoListId(LongFilter photoListId) {
        this.photoListId = photoListId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCategoryCriteria that = (ProductCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortLabel, that.shortLabel) &&
            Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
            Objects.equals(photoListId, that.photoListId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        shortLabel,
        thumbnailUrl,
        photoListId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "ProductCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortLabel != null ? "shortLabel=" + shortLabel + ", " : "") +
                (thumbnailUrl != null ? "thumbnailUrl=" + thumbnailUrl + ", " : "") +
                (photoListId != null ? "photoListId=" + photoListId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
