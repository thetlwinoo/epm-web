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
 * Criteria class for the {@link com.epmweb.server.domain.ProductAttribute} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ProductAttributeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-attributes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductAttributeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private LongFilter productAttributeSetId;

    private LongFilter supplierId;

    public ProductAttributeCriteria(){
    }

    public ProductAttributeCriteria(ProductAttributeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.productAttributeSetId = other.productAttributeSetId == null ? null : other.productAttributeSetId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
    }

    @Override
    public ProductAttributeCriteria copy() {
        return new ProductAttributeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public LongFilter getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(LongFilter productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductAttributeCriteria that = (ProductAttributeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(productAttributeSetId, that.productAttributeSetId) &&
            Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        productAttributeSetId,
        supplierId
        );
    }

    @Override
    public String toString() {
        return "ProductAttributeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (productAttributeSetId != null ? "productAttributeSetId=" + productAttributeSetId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            "}";
    }

}
