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
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.ProductSetDetails} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ProductSetDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-set-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductSetDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter subGroupNo;

    private IntegerFilter subGroupMinCount;

    private BigDecimalFilter subGroupMinTotal;

    private IntegerFilter minCount;

    private IntegerFilter maxCount;

    private BooleanFilter isOptional;

    public ProductSetDetailsCriteria(){
    }

    public ProductSetDetailsCriteria(ProductSetDetailsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.subGroupNo = other.subGroupNo == null ? null : other.subGroupNo.copy();
        this.subGroupMinCount = other.subGroupMinCount == null ? null : other.subGroupMinCount.copy();
        this.subGroupMinTotal = other.subGroupMinTotal == null ? null : other.subGroupMinTotal.copy();
        this.minCount = other.minCount == null ? null : other.minCount.copy();
        this.maxCount = other.maxCount == null ? null : other.maxCount.copy();
        this.isOptional = other.isOptional == null ? null : other.isOptional.copy();
    }

    @Override
    public ProductSetDetailsCriteria copy() {
        return new ProductSetDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSubGroupNo() {
        return subGroupNo;
    }

    public void setSubGroupNo(IntegerFilter subGroupNo) {
        this.subGroupNo = subGroupNo;
    }

    public IntegerFilter getSubGroupMinCount() {
        return subGroupMinCount;
    }

    public void setSubGroupMinCount(IntegerFilter subGroupMinCount) {
        this.subGroupMinCount = subGroupMinCount;
    }

    public BigDecimalFilter getSubGroupMinTotal() {
        return subGroupMinTotal;
    }

    public void setSubGroupMinTotal(BigDecimalFilter subGroupMinTotal) {
        this.subGroupMinTotal = subGroupMinTotal;
    }

    public IntegerFilter getMinCount() {
        return minCount;
    }

    public void setMinCount(IntegerFilter minCount) {
        this.minCount = minCount;
    }

    public IntegerFilter getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(IntegerFilter maxCount) {
        this.maxCount = maxCount;
    }

    public BooleanFilter getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(BooleanFilter isOptional) {
        this.isOptional = isOptional;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductSetDetailsCriteria that = (ProductSetDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subGroupNo, that.subGroupNo) &&
            Objects.equals(subGroupMinCount, that.subGroupMinCount) &&
            Objects.equals(subGroupMinTotal, that.subGroupMinTotal) &&
            Objects.equals(minCount, that.minCount) &&
            Objects.equals(maxCount, that.maxCount) &&
            Objects.equals(isOptional, that.isOptional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subGroupNo,
        subGroupMinCount,
        subGroupMinTotal,
        minCount,
        maxCount,
        isOptional
        );
    }

    @Override
    public String toString() {
        return "ProductSetDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subGroupNo != null ? "subGroupNo=" + subGroupNo + ", " : "") +
                (subGroupMinCount != null ? "subGroupMinCount=" + subGroupMinCount + ", " : "") +
                (subGroupMinTotal != null ? "subGroupMinTotal=" + subGroupMinTotal + ", " : "") +
                (minCount != null ? "minCount=" + minCount + ", " : "") +
                (maxCount != null ? "maxCount=" + maxCount + ", " : "") +
                (isOptional != null ? "isOptional=" + isOptional + ", " : "") +
            "}";
    }

}
