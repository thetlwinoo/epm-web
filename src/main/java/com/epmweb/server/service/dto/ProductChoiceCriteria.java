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
 * Criteria class for the {@link com.epmweb.server.domain.ProductChoice} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ProductChoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-choices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductChoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isMultiply;

    private LongFilter productCategoryId;

    private LongFilter productAttributeSetId;

    private LongFilter productOptionSetId;

    public ProductChoiceCriteria(){
    }

    public ProductChoiceCriteria(ProductChoiceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.isMultiply = other.isMultiply == null ? null : other.isMultiply.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productAttributeSetId = other.productAttributeSetId == null ? null : other.productAttributeSetId.copy();
        this.productOptionSetId = other.productOptionSetId == null ? null : other.productOptionSetId.copy();
    }

    @Override
    public ProductChoiceCriteria copy() {
        return new ProductChoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getIsMultiply() {
        return isMultiply;
    }

    public void setIsMultiply(BooleanFilter isMultiply) {
        this.isMultiply = isMultiply;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(LongFilter productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public LongFilter getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(LongFilter productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductChoiceCriteria that = (ProductChoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isMultiply, that.isMultiply) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productAttributeSetId, that.productAttributeSetId) &&
            Objects.equals(productOptionSetId, that.productOptionSetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isMultiply,
        productCategoryId,
        productAttributeSetId,
        productOptionSetId
        );
    }

    @Override
    public String toString() {
        return "ProductChoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isMultiply != null ? "isMultiply=" + isMultiply + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productAttributeSetId != null ? "productAttributeSetId=" + productAttributeSetId + ", " : "") +
                (productOptionSetId != null ? "productOptionSetId=" + productOptionSetId + ", " : "") +
            "}";
    }

}
